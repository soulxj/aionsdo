/*
 * This file is part of Encom. **ENCOM FUCK OTHER SVN**
 *
 *  Encom is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Encom is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with Encom.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.chatserver.network.gameserver.serverpackets;

import com.aionemu.chatserver.model.ChatClient;
import com.aionemu.chatserver.network.gameserver.GsConnection;
import com.aionemu.chatserver.network.gameserver.GsServerPacket;

/**
 * @author ATracer
 */
public class SM_PLAYER_AUTH_RESPONSE extends GsServerPacket {

    private int playerId;
    private byte[] token;

    public SM_PLAYER_AUTH_RESPONSE(ChatClient chatClient) {
        this.playerId = chatClient.getClientId();
        token = chatClient.getToken();
    }

    @Override
    protected void writeImpl(GsConnection con) {
        writeC(1);
        writeD(playerId);
        writeC(token.length);
        writeB(token);
    }
}
