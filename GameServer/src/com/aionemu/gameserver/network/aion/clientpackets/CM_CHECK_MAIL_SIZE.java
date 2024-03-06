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
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.serverpackets.S_MAIL;
import com.aionemu.gameserver.utils.PacketSendUtility;

/****/
/** Author Rinzler (Encom)
/****/

public class CM_CHECK_MAIL_SIZE extends AionClientPacket
{
	public int onlyExpress;
	
	public CM_CHECK_MAIL_SIZE(int opcode, AionConnection.State state, AionConnection.State ... restStates) {
        super(opcode, state, restStates);
    }
	
    protected void readImpl() {
        this.onlyExpress = this.readC();
    }
	
    protected void runImpl() {
        Player player = ((AionConnection)this.getConnection()).getActivePlayer();
        PacketSendUtility.sendPacket(player, new S_MAIL(player, player.getMailbox().getLetters()));
    }
}