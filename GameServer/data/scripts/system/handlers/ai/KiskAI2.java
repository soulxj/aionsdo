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
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.S_ACTION;
import com.aionemu.gameserver.network.aion.serverpackets.S_ASK;
import com.aionemu.gameserver.network.aion.serverpackets.S_MESSAGE_CODE;
import com.aionemu.gameserver.services.KiskService;
import com.aionemu.gameserver.services.SerialKillerService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("kisk")
public class KiskAI2 extends NpcAI2
{
    private final int CANCEL_DIALOG_METERS = 9;
	
	@Override
	public Kisk getOwner() {
		return (Kisk) super.getOwner();
	}
	
	@Override
	protected void handleAttack(Creature creature) {
		if (getLifeStats().isFullyRestoredHp()) {
		    for (Player member: getOwner().getCurrentMemberList()) {
		       PacketSendUtility.sendPacket(member, S_MESSAGE_CODE.STR_BINDSTONE_IS_ATTACKED);
			}
		}
	}
	
	@Override
	protected void handleDied() {
		if (isAlreadyDead()) {
			PacketSendUtility.broadcastPacket(getOwner(), new S_ACTION(getOwner(), EmotionType.DIE, 0, 0));
			getOwner().broadcastPacket(S_MESSAGE_CODE.STR_BINDSTONE_IS_DESTROYED);
		}
		super.handleDied();
	}
	
	@Override
	protected void handleDespawned() {
		KiskService.getInstance().removeKisk(getOwner());
		if (!isAlreadyDead()) {
			getOwner().broadcastPacket(S_MESSAGE_CODE.STR_BINDSTONE_IS_REMOVED);
		}
		super.handleDespawned();
	}
	
	@Override
	protected void handleDialogStart(Player player) {
		if (SerialKillerService.getInstance().isRestrictDynamicBindstone(player)) {
			//You cannot bind from here.
			PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_CANNOT_REGISTER_RESURRECT_POINT_FAR_FROM_NPC);
			return;
		} if (player.getSKInfo().getRank() > 0) {
            return;
        } if (player.getKisk() == getOwner()) {
			PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_BINDSTONE_ALREADY_REGISTERED);
			return;
		} if (getOwner().canBind(player)) {
			AI2Actions.addRequest(this, player, S_ASK.STR_ASK_REGISTER_BINDSTONE, getOwner().getObjectId(), CANCEL_DIALOG_METERS, new AI2Request() {
                private boolean decisionTaken = false;
				@Override
				public void acceptRequest(Creature requester, Player responder) {
                    if (!decisionTaken) {
                        if (!getOwner().canBind(responder)) {
                            PacketSendUtility.sendPacket(responder, S_MESSAGE_CODE.STR_CANNOT_REGISTER_BINDSTONE_HAVE_NO_AUTHORITY);
                            return;
                        }
                        KiskService.getInstance().onBind(getOwner(), responder);
                    }
                }
                @Override
                public void denyRequest(Creature requester, Player responder) {
                    decisionTaken = true;
                }
			});
		} else if (getOwner().getCurrentMemberCount() >= getOwner().getMaxMembers()) {
			PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_CANNOT_REGISTER_BINDSTONE_FULL);
		} else {
			PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_CANNOT_REGISTER_BINDSTONE_HAVE_NO_AUTHORITY);
		}
	}
	
	@Override
	public int modifyOwnerDamage(int damage) {
		return 1;
	}
	
	@Override
	public int modifyDamage(int damage) {
		return 1;
	}
	
    @Override
	public boolean isMoveSupported() {
		return false;
	}
}