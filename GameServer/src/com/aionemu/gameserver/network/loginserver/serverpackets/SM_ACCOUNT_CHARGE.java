/**
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
package com.aionemu.gameserver.network.loginserver.serverpackets;

import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;

/**
 * In this packet Gameserver is asking if given account sessionKey is valid at Loginserver side. [if user that is
 * authenticating on Gameserver is already authenticated on Loginserver]
 * 
 * @author -SOULXJ-
 */
public class SM_ACCOUNT_CHARGE extends LsServerPacket {

	/**
	 * accountId [part of session key]
	 */
	private final int accountId;

	private final int type;
	/**

	/**
	 * Constructs new instance of <tt>SM_ACCOUNT_AUTH </tt> packet.
	 *
	 * @param accountId
	 *          account identifier.
	 */
	public SM_ACCOUNT_CHARGE(int accountId, int type) {
		super(0x0F);
		this.accountId = accountId;
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void writeImpl(LoginServerConnection con) {
		writeD(accountId);
		writeD(type);
	}
}
