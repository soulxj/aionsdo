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

package com.aionemu.loginserver.network.gameserver.serverpackets;

import com.aionemu.loginserver.model.AccountSielEnergy;
import com.aionemu.loginserver.network.gameserver.GsConnection;
import com.aionemu.loginserver.network.gameserver.GsServerPacket;

/**
 * In this packet LoginServer is answering on GameServer request about valid authentication data and also sends account
 * name of user that is authenticating on GameServer.
 *
 * @author -Nemesiss-
 */
public class SM_ACCOUNT_SIELENERY_NOTITY extends GsServerPacket {


    private final AccountSielEnergy accountSielEnergy;
    private boolean isPush;


    public SM_ACCOUNT_SIELENERY_NOTITY(boolean isPush, AccountSielEnergy accountSielEnergy) {

        this.accountSielEnergy = accountSielEnergy;
        this.isPush = isPush;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(GsConnection con) {
        writeC(13);
        writeD(isPush ? 1 : 0);
        writeD(accountSielEnergy.getAccount().getId());
        writeD(accountSielEnergy.getType().getId());
        writeQ(accountSielEnergy.getChargeTime());
        writeQ(accountSielEnergy.getEndTime() != null ? accountSielEnergy.getEndTime().getTime() : 0);
        writeQ(accountSielEnergy.getRemainSecond());
    }
}
