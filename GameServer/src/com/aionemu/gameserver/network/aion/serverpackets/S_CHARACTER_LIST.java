package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.dao.MailDAO;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.CharacterBanInfo;
import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.PlayerInfo;
import com.aionemu.gameserver.services.BrokerService;
import com.aionemu.gameserver.services.player.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class S_CHARACTER_LIST extends PlayerInfo {
    private static Logger log = LoggerFactory.getLogger(S_CHARACTER_LIST.class);

    private final int playOk2;
    private final int unk;

    public S_CHARACTER_LIST(int unk, int playOk2) {
        this.playOk2 = playOk2;
        this.unk = unk;
    }

    @Override
    protected void writeImpl(AionConnection con) {
        writeC(unk);
        writeD(playOk2);
        if (unk == 0) {
            writeC(0);
        } else if (unk == 2) {
            Account account = con.getAccount();
            removeDeletedCharacters(account);
            writeC(account.size());

            for (PlayerAccountData playerData : account.getSortedAccountsList()) {
                PlayerCommonData pcd = playerData.getPlayerCommonData();
                CharacterBanInfo cbi = playerData.getCharBanInfo();
                Player player = PlayerService.getPlayer(pcd.getPlayerObjId(), account);
                writePlayerInfo(playerData);
                writeH(player.getPlayerSettings().getDisplay());
                writeH(player.getPlayerSettings().getDeny());
                writeD(0); //wtf ?
                writeD(0); //wtf ?
                writeD(DAOManager.getDAO(MailDAO.class).unreadedMails(pcd.getPlayerObjId()) != 0 ? 1 : 0); // mail
                writeB(new byte[108]);
                writeD(0);   // todo BrokerService.getInstance().getCollectedMoney(pcd)); // collected money from broker
                writeD(0);
                //sanction here
                if (cbi != null && cbi.getEnd() > System.currentTimeMillis() / 1000) {
                    writeD((int) cbi.getStart()); // startPunishDate
                    writeD((int) cbi.getEnd()); // endPunishDate
                    writeS(cbi.getReason());
                } else {
                    writeD(0);
                    writeD(0);
                    writeH(0);
                }
            }
        }

    }

    public void removeDeletedCharacters(Account account) {
        Iterator<PlayerAccountData> it = account.iterator();
        while (it.hasNext()) {
            PlayerAccountData pad = it.next();
            Race race = pad.getPlayerCommonData().getRace();
            long deletionTime = (long) pad.getDeletionTimeInSeconds() * (long) 1000;
            if (deletionTime != 0 && deletionTime <= System.currentTimeMillis()) {
                it.remove();
                account.decrementCountOf(race);
                PlayerService.deletePlayerFromDB(pad.getPlayerCommonData().getPlayerObjId());
            }
        }
        if (account.isEmpty()) {
            removeAccountWH(account.getId());
            account.getAccountWarehouse().clear();
        }
    }

    private static void removeAccountWH(int accountId) {
        DAOManager.getDAO(InventoryDAO.class).deleteAccountWH(accountId);
    }
}