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
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_BUDDY_LIST extends AionServerPacket
{
	@Override
	protected void writeImpl(AionConnection con) {
		FriendList list = con.getActivePlayer().getFriendList();
		writeH((0 - list.getSize()));
		writeC(0); //status
		for (Friend friend : list) {
			writeD(friend.getOid());
			writeS(friend.getName());
			writeD(friend.getLevel());
			writeD(friend.getPlayerClass().getClassId());
			writeC(friend.isOnline() ? 1 : 0);
			writeD(friend.getMapId());
			writeD(friend.getLastOnlineTime());
			writeS(friend.getNote());
			writeC(friend.getStatus().getId());
			writeS(friend.getFriendNote());
		}
	}
}