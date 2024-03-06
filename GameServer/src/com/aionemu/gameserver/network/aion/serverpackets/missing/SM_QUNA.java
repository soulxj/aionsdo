package com.aionemu.gameserver.network.aion.serverpackets.missing;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class SM_QUNA extends AionServerPacket {

    @Override
    protected void writeImpl(AionConnection con) {
        writeH(254); //old 254
        writeB("2A01");
        writeQ(500);
    }
}
