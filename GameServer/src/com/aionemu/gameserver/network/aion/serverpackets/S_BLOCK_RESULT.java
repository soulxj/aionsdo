/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.serverpackets;


import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * Responses to block list related requests
 * 
 * @author Ben
 */
public class S_BLOCK_RESULT extends AionServerPacket {

	/**
	 * You have blocked %0
	 */
	public static final int BLOCK_SUCCESSFUL = 0;
	/**
	 * You have unblocked %0
	 */
	public static final int UNBLOCK_SUCCESSFUL = 1;
	/**
	 * That character does not exist.
	 */
	public static final int TARGET_NOT_FOUND = 2;
	/**
	 * Your Block List is full.
	 */
	public static final int LIST_FULL = 3;
	/**
	 * You cannot block yourself.
	 */
	public static final int CANT_BLOCK_SELF = 4;

	private int code;
	private String playerName;

	/**
	 * Constructs a new block request response packet
	 * 
	 * @param code
	 *          Message code to use - see class constants
	 * @param playerName
	 *          Parameters inserted into message. Usually the target player's name
	 */
	public S_BLOCK_RESULT(int code, String playerName) {
		this.code = code;
		this.playerName = playerName;
	}

	@Override
	protected void writeImpl(AionConnection con) {
		writeS(playerName);
		writeD(code);

	}
}
