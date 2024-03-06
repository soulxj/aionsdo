package com.aionemu.gameserver.services.legion;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.LegionDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.team.legion.Legion;
import com.aionemu.gameserver.model.team.legion.LegionJoinRequest;
import com.aionemu.gameserver.model.team.legion.LegionJoinRequestState;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.network.aion.serverpackets.need.S_USER_APPLY_JOIN_GUILD_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuildSearchService {

    private static final Logger log = LoggerFactory.getLogger(GuildSearchService.class);

    public void onStart() {
        log.info("[GuildSearchService] Started");
    }

    public void onPlayerEnterWorld(Player player) {
        if (player.isLegionMember()) {
            LegionService.getInstance().onLogin(player);
            if (player.getLegionMember().isBrigadeGeneral() && !player.getLegion().getJoinRequestMap().isEmpty()) {
                PacketSendUtility.sendPacket(player, new S_GUILD_JOIN_APPILCANT_LIST(player.getLegion().getJoinRequestMap().values()));
            }
        } else {
            DAOManager.getDAO(PlayerDAO.class).getJoinRequestState(player);
            handleJoinRequestGetAnswer(player);
            if (player.getCommonData().getJoinRequestLegionId() <= 0) {
                PacketSendUtility.sendPacket(player, new S_USER_APPLY_JOIN_GUILD_INFO(0, ""));
            } else {
                Legion legion = LegionService.getInstance().getLegion(player.getCommonData().getJoinRequestLegionId());
                PacketSendUtility.sendPacket(player, new S_USER_APPLY_JOIN_GUILD_INFO(legion.getLegionId(), legion.getLegionName()));
            }
        }
    }

    public void handleLegionSearch(Player player, int type, String legionName) {
        FastList<Legion> matchingLegions = new FastList<Legion>();
        switch (type) {
            case 0:
                matchingLegions = LegionService.getInstance().getAllCachedLegions().getAllLegions();
                break;
            case 1:
                for (Legion legion : LegionService.getInstance().getAllCachedLegions().getAllLegions()) {
                    if (legion.getLegionName().toLowerCase().contains(legionName.toLowerCase())) {
                        matchingLegions.add(legion);
                    }
                }
                break;
        }
        PacketSendUtility.sendPacket(player, new S_GUILD_LIST(matchingLegions));
    }

    public void updateLegionJoinSettings(Player player, String description, int joinType, int minLevel, int mainActivity) {
        Legion legion = player.getLegion();
        if (legion == null) {
            return;
        } if (LegionService.getInstance().getLegionRestrictions().canChangeLegionJoinSetting(player)) {
            legion.setDescription(description);
            legion.setJoinType(joinType);
            legion.setMinJoinLevel(minLevel);
            legion.setActivity(mainActivity);
            PacketSendUtility.sendPacket(player, new S_CHANGE_GUILD_INFO(0x0B, legion));
            DAOManager.getDAO(LegionDAO.class).storeLegion(legion);
        }
    }


    public void sendLegionJoinRequestPacket(Player player, int legionId) {
        if (legionId <= 0) {
            PacketSendUtility.sendPacket(player, new S_USER_APPLY_JOIN_GUILD_INFO(0, ""));
        } else {
            Legion legion = LegionService.getInstance().getLegion(legionId);
            PacketSendUtility.sendPacket(player, new S_USER_APPLY_JOIN_GUILD_INFO(legion.getLegionId(), legion.getLegionName()));
        }
    }

    public void handleLegionJoinRequest(Player player, int legionId, int joinType, String joinRequestMsg) {
        Legion legion = LegionService.getInstance().getLegion(legionId);
        if (legion == null) {
            return;
        } switch (joinType) {
            case 0:
                player.getCommonData().setJoinRequestLegionId(legionId);
                sendLegionJoinRequestPacket(player,legionId);
                LegionJoinRequest ljr = new LegionJoinRequest(legionId, player, joinRequestMsg);
                legion.addJoinRequest(ljr);
                DAOManager.getDAO(LegionDAO.class).storeLegionJoinRequest(ljr);
                player.getCommonData().setJoinRequestLegionId(legionId);
                Player brigadeGeneral = LegionService.getInstance().getBrigadeGeneral(legion);
                if (brigadeGeneral != null) {
                    PacketSendUtility.sendPacket(brigadeGeneral, new S_ADD_GUILD_JOIN_APPLICANT(ljr));
                }
                break;
            case 1:
                LegionService.getInstance().directAddPlayer(legion, player);
                break;
            default:
                PacketSendUtility.sendMessage(player, "This Legion isn't recruiting new members..");
                break;
        }
    }

    public void handleJoinRequestCancel(Player player, int legionId){
        Legion legion = LegionService.getInstance().getLegion(legionId);
        player.clearJoinRequest();
        sendLegionJoinRequestPacket(player, 0);
        legion.deleteJoinRequest(player.getObjectId());
        Player bg = LegionService.getInstance().getBrigadeGeneral(legion);
        if (bg != null) {
            PacketSendUtility.sendPacket(bg, new S_DELETE_GUILD_JOIN_APPLICANT(player.getObjectId(), false));
        }
    }

    public void handleJoinRequestGetAnswer(Player player) {
        PlayerCommonData pcd = player.getCommonData();
        switch (pcd.getJoinRequestState()) {
            case ACCEPTED:
                LegionService.getInstance().directAddPlayer(pcd.getJoinRequestLegionId(), player);
                handleJoinRequestCancel(player, player.getCommonData().getJoinRequestLegionId());
                PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_GUILD_JOIN_APPORVE);
            case DENIED:
                handleJoinRequestCancel(player, player.getCommonData().getJoinRequestLegionId());
                PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_GUILD_JOIN_REFUSAL);
                break;
            default:
                break;
        }
    }

    public void handleJoinRequestAcceptAnswer(Player brigadeGeneral, int playerId) {
        LegionJoinRequestState state = LegionJoinRequestState.ACCEPTED;
        Legion legion = brigadeGeneral.getLegion();
        if (legion == null) {
            return;
        }
        Player player = World.getInstance().findPlayer(playerId);
        if (player == null) {
            DAOManager.getDAO(PlayerDAO.class).updateLegionJoinRequestState(playerId, state);
            legion.deleteJoinRequest(playerId);
        } else {
            player.getCommonData().setJoinRequestState(state);
            legion.deleteJoinRequest(playerId);
            DAOManager.getDAO(LegionDAO.class).deleteLegionJoinRequest(legion.getLegionId(), playerId);
            handleJoinRequestGetAnswer(player);
        }
        PacketSendUtility.sendPacket(brigadeGeneral, new S_DELETE_GUILD_JOIN_APPLICANT(playerId, true));
    }

    public void handleJoinRequesDeclineAnswer(Player brigadeGeneral, int playerId) {
        LegionJoinRequestState state = LegionJoinRequestState.DENIED;
        Legion legion = brigadeGeneral.getLegion();
        if (legion == null) {
            return;
        }
        Player player = World.getInstance().findPlayer(playerId);
        if (player == null) {
            DAOManager.getDAO(PlayerDAO.class).updateLegionJoinRequestState(playerId, state);
            legion.deleteJoinRequest(playerId);
        } else {
            player.getCommonData().setJoinRequestState(state);
            legion.deleteJoinRequest(playerId);
            DAOManager.getDAO(LegionDAO.class).deleteLegionJoinRequest(legion.getLegionId(), playerId);
            handleJoinRequestGetAnswer(player);
        }
        PacketSendUtility.sendPacket(brigadeGeneral, new S_DELETE_GUILD_JOIN_APPLICANT(playerId, false));
    }

    public static GuildSearchService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final GuildSearchService instance = new GuildSearchService();
    }
}
