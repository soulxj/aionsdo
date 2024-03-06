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
package ai.instance.beshmundirTemple;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AI2Request;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.model.autogroup.AutoGroupType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.portal.PortalPath;
import com.aionemu.gameserver.model.templates.portal.PortalUse;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.services.teleport.PortalService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("beshmundirswalk")
public class Beshmundirs_WalkAI2 extends ActionItemNpcAI2
{
	@Override
	protected void handleDialogStart(Player player) {
		if (player.getLevel() >= 55) {
		    PacketSendUtility.sendPacket(player, new S_NPC_HTML_MESSAGE(getObjectId(), 10));
		} else {
            PacketSendUtility.sendPacket(player, new S_NPC_HTML_MESSAGE(getObjectId(), 27));
			PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_CANNOT_USE_DIRECT_PORTAL_LEVEL_LIMIT);
        }
	}
	
	@Override
	public boolean onDialogSelect(Player player, final int dialogId, int questId, int extendedRewardIndex) {
		AI2Request request = new AI2Request() {
			@Override
			public void acceptRequest(Creature requester, Player responder) {
				moveToInstance(responder);
			}
		};
		switch (dialogId) {
			case 60:
				if (player.getPlayerGroup2() == null) {
					//This area is only accessible to groups.
					PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_MSG_ENTER_ONLY_PARTY_DON);
					return true;
				} if (player.isInGroup2() && player.getPlayerGroup2().isLeader(player)) {
					PacketSendUtility.sendPacket(player, new S_NPC_HTML_MESSAGE(getObjectId(), 4762));
				} else {
					if (!isAGroupMemberInInstance(player)) {
						///You can only enter after the Group Leader has created the instance.
						PacketSendUtility.sendPacket(player, new S_MESSAGE_CODE(1400361));
						return true;
					}
					moveToInstance(player);
				}
			break;
			case 75:
				AutoGroupType agt = AutoGroupType.getAutoGroup(player.getLevel(), getNpcId());
				if (agt != null) {
					PacketSendUtility.sendPacket(player, new S_PARTY_MATCH(0x1A, agt.getInstanceMapId()));
				}
			break;
			case 4763:
				AI2Actions.addRequest(this, player, S_ASK.STR_INSTANCE_DUNGEON_WITH_DIFFICULTY_ENTER_CONFIRM, getObjectId(), request, new DescriptionId(1804103));
				PacketSendUtility.sendPacket(player, new S_NPC_HTML_MESSAGE(getObjectId(), 4762));
			break;
			case 4848:
				AI2Actions.addRequest(this, player, S_ASK.STR_INSTANCE_DUNGEON_WITH_DIFFICULTY_ENTER_CONFIRM, getObjectId(), request, new DescriptionId(1804105));
				PacketSendUtility.sendPacket(player, new S_NPC_HTML_MESSAGE(getObjectId(), 4762));
			break;
		}
		return true;
	}
	
	private boolean isAGroupMemberInInstance(Player player) {
		if (player.isInGroup2()) {
			for (Player member: player.getPlayerGroup2().getMembers()) {
				if (member.getWorldId() == 300170000) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void moveToInstance(Player player) {
		PortalUse portalUse = DataManager.PORTAL2_DATA.getPortalUse(getNpcId());
		if (portalUse != null) {
			PortalPath portalPath = portalUse.getPortalPath(player.getRace());
			if (portalPath != null) {
				PortalService.port(portalPath, player, getObjectId());
			}
		}
	}
}