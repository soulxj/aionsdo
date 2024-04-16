/*
 *  Aion Classic Emu based on Aion Encom Source Files
 *
 *  ENCOM Team based on Aion-Lighting Open Source
 *  All Copyrights : "Data/Copyrights/AEmu-Copyrights.text
 *
 *  iMPERIVM.FUN - AION DEVELOPMENT FORUM
 *  Forum: <http://https://imperivm.fun/>
 *
 */
package com.aionemu.loginserver.network.gameserver;

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
	 *          packet opcode.
	 */
	protected GsServerPacket() {
		super(0);
	}

	/**
	 * Write this packet data for given connection, to given buffer.
	 * 
	 * @param con
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
	 */
	protected abstract void writeImpl(GsConnection con);
}
