/*
 *  Aion Classic Emu based on Aion Encom Source Files
 *
 *  ENCOM Team based on Aion-Lighting Open Source
 *  All Copyrights : "Data/Copyrights/AEmu-Copyrights.text
 *
 *  iMPERIVM.FUN - AION DEVELOPMENT FORUM
 *  Forum: <http://https://imperivm.fun/>
 *
 */
package com.aionemu.loginserver.network.gameserver.clientpackets;


import com.aionemu.loginserver.dao.AccountSielEnergyDAO;
import com.aionemu.loginserver.model.Account;
import com.aionemu.loginserver.model.AccountSielEnergy;
import com.aionemu.loginserver.model.SielEnergyType;
import com.aionemu.loginserver.network.gameserver.GsClientPacket;
import com.aionemu.loginserver.network.gameserver.serverpackets.SM_ACCOUNT_SIELENERY_NOTITY;
import com.aionemu.loginserver.taskmanager.ExpireTimerTask;
import com.aionemu.loginserver.utils.ThreadPoolManager;

/**
 * In this packet Gameserver is asking if given account sessionKey is valid at Loginserver side. [if user that is
 * authenticating on Gameserver is already authenticated on Loginserver]
 *
 * @author -Nemesiss-
 */
public class CM_ACCOUNT_SIELENERY extends GsClientPacket {

    /**
     * SessionKey that GameServer needs to check if is valid at Loginserver side.
     */
    private int accId;
    private int type;

    @Override
    protected void readImpl() {
        accId = readD();
        type = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        Account account = getConnection().getGameServerInfo().getAccountFromGameServer(accId);
        if (account != null) {
            if (account.getGsConnection() == null)
                account.setGsConnection(getConnection());

            AccountSielEnergy accountSielEnergy = account.getAccountSielEnergy();
            if (type == 1) {
                accountSielEnergy.setChargeTime(System.currentTimeMillis());
                getConnection().sendPacket(new SM_ACCOUNT_SIELENERY_NOTITY(false, account.getAccountSielEnergy()));
                ExpireTimerTask.getInstance().addTask(account.getAccountSielEnergy(), account);
            } else {
                ExpireTimerTask.getInstance().removeAccount(account);
                long now = System.currentTimeMillis();
                long use = now - accountSielEnergy.getChargeTime();
                long remain = accountSielEnergy.getRemainSecond() * 1000 - use;
                if (remain < 0) {
                    remain = 0;
                }
                accountSielEnergy.setRemainSecond(remain / 1000);
                ThreadPoolManager.getInstance().executeLongRunning(() -> AccountSielEnergyDAO.replaceInsert(accountSielEnergy));

            }
        }
    }
}
