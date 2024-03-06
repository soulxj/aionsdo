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
package com.aionemu.chatserver.network.factories;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.chatserver.network.gameserver.GsClientPacket;
import com.aionemu.chatserver.network.gameserver.GsConnection;
import com.aionemu.chatserver.network.gameserver.GsConnection.State;
import com.aionemu.chatserver.network.gameserver.clientpackets.CM_CS_AUTH;
import com.aionemu.chatserver.network.gameserver.clientpackets.CM_PLAYER_AUTH;
import com.aionemu.chatserver.network.gameserver.clientpackets.CM_PLAYER_GAG;
import com.aionemu.chatserver.network.gameserver.clientpackets.CM_PLAYER_LOGOUT;

/**
 * @author -Nemesiss-
 */
public class GsPacketHandlerFactory {

    /**
     * logger for this class
     */
    private static final Logger log = LoggerFactory.getLogger(GsPacketHandlerFactory.class);

    /**
     * Reads one packet from given ByteBuffer
     *
     * @param data
     * @param client
     * @return GsClientPacket object from binary data
     */
    public static GsClientPacket handle(ByteBuffer data, GsConnection client) {
        GsClientPacket msg = null;
        State state = client.getState();
        int id = data.get() & 0xff;

        switch (state) {
            case CONNECTED: {
                switch (id) {
                    case 0x00:
                        msg = new CM_CS_AUTH(data, client);
                        break;
                    default:
                        unknownPacket(state, id);
                }
                break;
            }
            case AUTHED: {
                switch (id) {
                    case 0x01:
                        msg = new CM_PLAYER_AUTH(data, client);
                        break;
                    case 0x02:
                        msg = new CM_PLAYER_LOGOUT(data, client);
                        break;
                    case 0x03:
                        msg = new CM_PLAYER_GAG(data, client);
                        break;
                    default:
                        unknownPacket(state, id);
                }
                break;
            }
        }

        if (msg != null) {
            msg.setConnection(client);
            msg.setBuffer(data);
        }

        return msg;
    }

    /**
     * Logs unknown packet.
     *
     * @param state
     * @param id
     */
    private static void unknownPacket(State state, int id) {
        log.warn(String.format("Unknown packet recived from Game Server: 0x%02X state=%s", id, state.toString()));
    }
}
