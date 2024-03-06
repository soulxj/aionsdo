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
package com.aionemu.chatserver.network.gameserver;

import java.nio.ByteBuffer;

import com.aionemu.commons.network.packet.BaseServerPacket;

/**
 * Base class for every LS -> GameServer Server Packet.
 *
 * @author -Nemesiss-
 */
public abstract class GsServerPacket extends BaseServerPacket {

    /**
     * Constructs a new server packet with specified id.
     *
     * @param opcode packet opcode.
     */
    protected GsServerPacket() {
        super(0);
    }

    /**
     * Write this packet data for given connection, to given buffer.
     *
     * @param con
     * @param buf
     */
    public final void write(GsConnection con, ByteBuffer buffer) {
        setBuf(buffer);
        buf.putShort((short) 0);
        writeImpl(con);
        buf.flip();
        buf.putShort((short) buf.limit());
        buf.position(0);
    }

    /**
     * Write data that this packet represents to given byte buffer.
     *
     * @param con
     * @param buf
     */
    protected abstract void writeImpl(GsConnection con);
}
