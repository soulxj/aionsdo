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


import com.aionemu.loginserver.model.Account;
import com.aionemu.loginserver.model.AccountSielEnergy;
import com.aionemu.loginserver.network.gameserver.GsClientPacket;
import com.aionemu.loginserver.taskmanager.ExpireTimerTask;

/**
 *
 * @author -soulxj-
 */
public class CM_ACCOUNT_CHARGE extends GsClientPacket {

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
                ExpireTimerTask.getInstance().addTask(account.getAccountSielEnergy(), account);
            } else {
                ExpireTimerTask.getInstance().removeAccount(account);
                accountSielEnergy.onSave();
            }
        }
    }
}
