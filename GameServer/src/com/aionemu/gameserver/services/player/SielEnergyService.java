package com.aionemu.gameserver.services.player;

import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.AccountSielEnergy;
import com.aionemu.gameserver.model.account.SielEnergyType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.S_GAMEPASS_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.S_GAMEPASS_OTHER_UPDATED;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_ACCOUNT_CHARGE;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author soulxj
 */
public class SielEnergyService {

    public void onLogout(Player player) {
        Account account = player.getPlayerAccount();
        if (account != null) {
            account.getAccountSielEnergy().end(player);
            SM_ACCOUNT_CHARGE req = new SM_ACCOUNT_CHARGE(account.getId(), 0);
            LoginServer.getInstance().sendPacket(req);
        }
    }

    public void onLogin(Player player) {
        Account account = player.getPlayerAccount();
        if (account != null && account.getAccountSielEnergy() != null) {
            SM_ACCOUNT_CHARGE req = new SM_ACCOUNT_CHARGE(account.getId(), 1);
            LoginServer.getInstance().sendPacket(req);
        }
    }

    public void onUpdate(final Player player, final AccountSielEnergy old, final AccountSielEnergy accountSielEnergy) {

        PacketSendUtility.broadcastPacketAndReceive(player, new S_GAMEPASS_OTHER_UPDATED(player.getObjectId(), accountSielEnergy.getType()));
        PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(accountSielEnergy));

        if (old != null && old.getType() != SielEnergyType.NONE) {
            old.end(player);
        }

        accountSielEnergy.apply(player);

    }

    public static SielEnergyService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final SielEnergyService instance = new SielEnergyService();
    }
}
