package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_GUILD_DONATION_LIST extends AionServerPacket {

    @Override
    protected void writeImpl(AionConnection con) {
        //07 00 //size
        //07 00 00 00 //id
        //0C 00 00 00 //index
    }
}