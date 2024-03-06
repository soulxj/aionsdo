package com.aionemu.gameserver.network.aion.serverpackets.need;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_USER_STORY_BOOK_FINISHED_LIST_LOAD extends AionServerPacket {

    private final Player player;

    public S_USER_STORY_BOOK_FINISHED_LIST_LOAD(Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(AionConnection con) {
        writeD(player.getObjectId());
        writeH(0); //size
    }
}