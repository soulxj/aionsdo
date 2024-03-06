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
package com.aionemu.chatserver.network.gameserver.clientpackets;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.chatserver.network.gameserver.GsClientPacket;
import com.aionemu.chatserver.network.gameserver.GsConnection;
import com.aionemu.chatserver.service.ChatService;

public class CM_PLAYER_GAG extends GsClientPacket
{
    private static final Logger log = LoggerFactory.getLogger(CM_PLAYER_LOGOUT.class);
    private int playerId;
    private long gagTime;
	
    public CM_PLAYER_GAG(ByteBuffer buf, GsConnection connection) {
        super(buf, connection, 0x03);
    }
	
    @Override
    protected void readImpl() {
        playerId = readD();
        gagTime = readQ();
    }
	
    @Override
    protected void runImpl() {
        ChatService.getInstance().gagPlayer(playerId, gagTime);
    }
}