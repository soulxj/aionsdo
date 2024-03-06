package com.aionemu.gameserver.network.aion.serverpackets.need;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_RANKING_BADGE_OTHER extends AionServerPacket {

    @Override
    protected void writeImpl(AionConnection con) {
        writeD(1);
    }
}