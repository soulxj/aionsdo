package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_PLAYER_GUILD_COINS extends AionServerPacket {

    private final Player player;

    public S_PLAYER_GUILD_COINS(Player player) {
        this.player = player;
    }

    @Override
    protected void writeImpl(AionConnection con) {
        writeD(player.getCommonData().getGuildCoins()); //coin count
    }
}
