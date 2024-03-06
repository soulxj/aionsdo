package com.aionemu.gameserver.network.aion.serverpackets.need;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_USER_STORY_BOOK_REGISTERED_ITEM_LIST_ extends AionServerPacket {

    private final Player player;

    public S_USER_STORY_BOOK_REGISTERED_ITEM_LIST_(Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(AionConnection con) {
        writeD(player.getObjectId());
        writeH(0); //size
    }
}