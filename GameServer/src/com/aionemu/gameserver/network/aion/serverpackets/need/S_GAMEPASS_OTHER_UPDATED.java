package com.aionemu.gameserver.network.aion.serverpackets.need;

import com.aionemu.gameserver.model.account.SielEnergyType;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author soulxj
 */
public class S_GAMEPASS_OTHER_UPDATED extends AionServerPacket {

    private int playerId;
    private SielEnergyType sielEnergyType;

    public S_GAMEPASS_OTHER_UPDATED(int playerId, SielEnergyType sielEnergyType) {
        this.playerId = playerId;
        this.sielEnergyType = sielEnergyType;
    }

    @Override
    protected void writeImpl(AionConnection con) {

        writeD(playerId);
        writeD(sielEnergyType.getId());

    }
}
