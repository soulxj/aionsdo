package com.aionemu.gameserver.services.player;

import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.AccountSielEnergy;
import com.aionemu.gameserver.model.account.SielEnergyType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.S_GAMEPASS_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.S_GAMEPASS_OTHER_UPDATED;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_ACCOUNT_SIELENERY;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author soulxj
 */
public class SielEnergyService {
    //private Logger log = LoggerFactory.getLogger(SielEnergyService.class);

    public void onLogout(Player player) {
        Account account = player.getPlayerAccount();
        if (account != null) {
            SM_ACCOUNT_SIELENERY req = new SM_ACCOUNT_SIELENERY(account.getId(), 0);
            LoginServer.getInstance().sendPacket(req);
        }
    }

    public void onLogin(Player player) {
        Account account = player.getPlayerAccount();
        if (account != null) {
            SM_ACCOUNT_SIELENERY req = new SM_ACCOUNT_SIELENERY(account.getId(), 1);
            LoginServer.getInstance().sendPacket(req);
        }

//        AccountSielEnergy accountSielEnergy = account.getAccountSielEnergy();
//        if (accountSielEnergy != null) {
//            if (accountSielEnergy.getType() == SielEnergyType.TRIAL) {
//                PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(1, accountSielEnergy));
//                PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(1, accountSielEnergy));
//                PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(2, accountSielEnergy));
//            }
//        }


//        if (account.getMembership() == 2) {
//            PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(3, player));
//            PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_MSG_ALARM_PLAYTIME_CLASSIC_ACCOUNT_MEMBERSHIP((int) account.getMembershipExpire().getTime()));
//        } else {
//            if (player.getAccountSielEnergy() == null) {
//                Calendar cal = Calendar.getInstance();
//                cal.setTimeInMillis(System.currentTimeMillis());
//                cal.add(Calendar.HOUR, 24);
//                Timestamp start = new Timestamp(System.currentTimeMillis());
//                Timestamp end = new Timestamp(cal.getTimeInMillis());
//                AccountSielEnergy sielEnergy = new AccountSielEnergy(SielEnergyType.TRIAL, start, end);
//                player.setAccountSielEnergy(sielEnergy);
//                PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(1, player));
//                PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(1, player));
//                PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(2, player));
//
//            }
//        }
//
//        DAOManager.getDAO(AccountSielEnergyDAO.class).load(player);
//        if (player.getAccountSielEnergy() == null) {
//            Calendar cal = Calendar.getInstance();
//            cal.setTimeInMillis(System.currentTimeMillis());
//            cal.add(Calendar.HOUR, 1);
//            Timestamp start = new Timestamp(System.currentTimeMillis());
//            Timestamp end = new Timestamp(cal.getTimeInMillis());
//            AccountSielEnergy sielEnergy = new AccountSielEnergy(SielEnergyType.TRIAL, start, end);
//            player.setAccountSielEnergy(sielEnergy);
//            DAOManager.getDAO(AccountSielEnergyDAO.class).add(player);
//        }
//        PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(1, player));
//        PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(1, player));
//        //if (player.getAccountSielEnergy().getType() == SielEnergyType.TRIAL && player.getAccountSielEnergy().isUnderSielEnergy()) {
//            PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(2, player));
//            PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_MSG_ALARM_PLAYTIME_CLASSIC_ACCOUNT_TRIAL((int) player.getAccountSielEnergy().getTime()));
        /// player.getAccountSielEnergy().apply(player);
        ////player.getController().addTask(TaskId.SIEL_UPDATE, ThreadPoolManager.getInstance().scheduleAtFixedRate(new SielEnergyUpdateTask(player.getObjectId()), 1000, 1000));
        //// } else if (player.getAccountSielEnergy().getType() == SielEnergyType.MEMBERSHIP && player.getAccountSielEnergy().isUnderSielEnergy()) {
        //player.getAccountSielEnergy().apply(player);
        ///////   playerTask.put(player.getObjectId(), player);

        //// }
    }

    public void onUpdate(boolean isPush, final Player player, final AccountSielEnergy old, final AccountSielEnergy accountSielEnergy) {

        if (isPush)
            PacketSendUtility.broadcastPacketAndReceive(player, new S_GAMEPASS_OTHER_UPDATED(player.getObjectId(), accountSielEnergy.getType()));
        PacketSendUtility.sendPacket(player, new S_GAMEPASS_INFO(accountSielEnergy));

        if (old != null && old.getType() != SielEnergyType.NONE) {
            old.end(player);
        }

        if (accountSielEnergy.getType() != SielEnergyType.NONE) {
            accountSielEnergy.apply(player);
        }

    }

    public void EndEffect(Player player) {
        AccountSielEnergy sielEnergy = player.getAccountSielEnergy();
        sielEnergy.end(player);
    }


    public static SielEnergyService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final SielEnergyService instance = new SielEnergyService();
    }
}
