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
package ai;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AI2Request;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.S_ASK;
import com.aionemu.gameserver.network.aion.serverpackets.S_MESSAGE_CODE;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("Illusion_Gate")
public class Illusion_GateAI2 extends NpcAI2
{
	@Override
	protected void handleDialogStart(Player player) {
		boolean isMember = false;
		int creatorId = getCreatorId();
		if (player.getObjectId().equals(creatorId)) {
			isMember = true;
		} else if (player.isInGroup2()) {
			isMember = player.getPlayerGroup2().hasMember(creatorId);
		} if (isMember && player.getLevel() >= 10) {
			AI2Actions.addRequest(this, player, S_ASK.STR_ASK_GROUP_GATE_DO_YOU_ACCEPT_MOVE, 0, 9, new AI2Request() {
				private boolean decided = false;
				@Override
				public void acceptRequest(Creature requester, Player responder) {
					if (!decided) {
						switch (getNpcId()) {
							case 749017:
								TeleportService2.teleportTo(responder, 110010000, 1444.0000f, 1577.0000f, 572.0000f, (byte) 0);
							break;
							case 749083:
								TeleportService2.teleportTo(responder, 120010000, 1657.0000f, 1398.0000f, 194.0000f, (byte) 0);
							break;
						}
						decided = true;
					}
				}
				@Override
				public void denyRequest(Creature requester, Player responder) {
					decided = true;
				}
			});
		} else {
			PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_SKILL_CAN_NOT_USE_GROUPGATE_NO_RIGHT);
		}
	}
}