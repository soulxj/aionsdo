package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.legion.GuildSearchService;
import com.aionemu.gameserver.services.legion.LegionService;

public class CM_GUILD_JOIN_REQUEST extends AionClientPacket {

    private String legionName;
    private String joinRequestMsg;
    private int legionId;
    private int joinType;

    public CM_GUILD_JOIN_REQUEST(int opcode, AionConnection.State state, AionConnection.State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        legionId = readD();
        legionName = readS();
        joinType = readC();
        joinRequestMsg = readS();
    }

    @Override
    protected void runImpl() {
        final Player player = getConnection().getActivePlayer();
        if (player == null || !player.isSpawned()) {
            return;
        } if (player.isProtectionActive()) {
            player.getController().stopProtectionActiveTask();
        } if (player.isCasting()) {
            player.getController().cancelCurrentSkill();
        } if (player.getController().isInShutdownProgress()) {
            return;
        }
        GuildSearchService.getInstance().handleLegionJoinRequest(player, legionId, joinType, joinRequestMsg);
    }
}