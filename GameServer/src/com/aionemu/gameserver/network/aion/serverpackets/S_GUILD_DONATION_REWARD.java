package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_GUILD_DONATION_REWARD extends AionServerPacket {

    @Override
    protected void writeImpl(AionConnection con) {
        writeD(2); //my donation step
        writeD(0); //guild donation step
    }
}