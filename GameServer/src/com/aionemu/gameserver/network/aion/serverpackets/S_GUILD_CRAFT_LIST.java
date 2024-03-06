package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_GUILD_CRAFT_LIST extends AionServerPacket {

    @Override
    protected void writeImpl(AionConnection con) {
        //1D 00 //29 (size)
        //06 00 00 00 //id
        //00 00 00 00
        //00 00 00 00
        //01 00 //mat count
        //01 34 DF 0A 00 00 00 00 //itemId
    }
}