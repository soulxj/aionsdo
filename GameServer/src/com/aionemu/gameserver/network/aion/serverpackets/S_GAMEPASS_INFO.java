package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.account.AccountSielEnergy;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author soulxj
 */
public class S_GAMEPASS_INFO extends AionServerPacket {

    private AccountSielEnergy accountSielEnergy;

    public S_GAMEPASS_INFO(AccountSielEnergy accountSielEnergy) {
        this.accountSielEnergy = accountSielEnergy;
    }


    @Override
    protected void writeImpl(AionConnection con) {
        writeD(accountSielEnergy.getType().getId());
        switch (accountSielEnergy.getType().getId()) {
            case 1:
                writeQ(-accountSielEnergy.getChargeTime().getTime() / 1000);
                writeQ(0);
                writeQ(0);
                break;
            case 2:
                writeQ(-accountSielEnergy.getChargeTime().getTime() / 1000);
                writeQ(accountSielEnergy.getRemain());
                writeQ(0);
                break;
            case 3:
                writeQ((accountSielEnergy.getEnd().getTime() - System.currentTimeMillis()) / 1000);
                writeQ(0);
                writeQ(0);
        }
    }
}
