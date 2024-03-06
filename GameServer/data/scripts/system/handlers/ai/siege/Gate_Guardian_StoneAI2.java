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
package ai.siege;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.Trap;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.network.aion.serverpackets.S_ASK;
import com.aionemu.gameserver.network.aion.serverpackets.S_MESSAGE_CODE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("Gate_Guardian_Stone")
public class Gate_Guardian_StoneAI2 extends NpcAI2
{
	@Override
	protected void handleDialogStart(final Player player) {
		RequestResponseHandler gateGuardianStone = new RequestResponseHandler(player) {
			@Override
			public void acceptRequest(Creature requester, Player responder) {
				RequestResponseHandler repairStone = new RequestResponseHandler(player) {
					@Override
					public void acceptRequest(Creature requester, Player responder) {
						onActivate(player);
					}
					@Override
					public void denyRequest(Creature requester, Player responder) {
					}
				};
				if (player.getResponseRequester().putRequest(S_ASK.STR_ASK_DOOR_REPAIR_DO_YOU_ACCEPT_REPAIR, repairStone)) {
					PacketSendUtility.sendPacket(player, new S_ASK(S_ASK.STR_ASK_DOOR_REPAIR_DO_YOU_ACCEPT_REPAIR, player.getObjectId(), 5, new DescriptionId(2 * 716568 + 1)));
				}
			}
			@Override
			public void denyRequest(Creature requester, Player responder) {
			}
		};
		if (player.getResponseRequester().putRequest(S_ASK.STR_ASK_DOOR_REPAIR_POPUPDIALOG, gateGuardianStone)) {
			PacketSendUtility.sendPacket(player, new S_ASK(S_ASK.STR_ASK_DOOR_REPAIR_POPUPDIALOG, player.getObjectId(), 5));
		}
	}
	
	@Override
	protected void handleDied() {
		AI2Actions.deleteOwner(Gate_Guardian_StoneAI2.this);
		World.getInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				AionObject winner = getAggroList().getMostDamage();
				if (winner instanceof Creature) {
					final Creature kill = (Creature) winner;
					//"Player Name" of the "Race" destroyed the Gate Guardian Stone.
					PacketSendUtility.sendPacket(player, new S_MESSAGE_CODE(1301054, kill.getRace().getRaceDescriptionId(), kill.getName()));
				}
			}
		});
	}
	
	@Override
	public boolean isMoveSupported() {
		return false;
	}
	
	@Override
	protected void handleDialogFinish(Player player) {
	}
	
	public void onActivate(Player player) {
	}
}