package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.configs.main.EventBoostConfig;
import com.aionemu.gameserver.configs.main.EventsConfig;
import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_SERVER_ENV extends AionServerPacket {
    @Override
    protected void writeImpl(AionConnection con) {
        writeH(350);//0x015e
        writeH(2561);//0x0a01
        writeH(2561);//0x0a01
        writeH(1285);
        writeC(0x02);
        writeC(0x00);
        writeC(GSConfig.CHARACTER_REENTRY_TIME);
        writeC(EventsConfig.ENABLE_DECOR);
        writeD(0); //BOOST EVENT ID
        writeD(EventBoostConfig.BOOST_EVENT_ENABLE ? EventBoostConfig.BOOST_EVENT_VALUE : 0); //BOOST EVENT VALUE
        writeC(1);
        writeD(0x3A);
        writeD(0x3A);
        writeD(0);
        writeD(0x64);

    }
}