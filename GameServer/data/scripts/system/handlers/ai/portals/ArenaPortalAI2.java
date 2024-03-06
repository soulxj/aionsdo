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
package ai.portals;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.autogroup.AutoGroupType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.S_MATCHMAKER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.S_NPC_HTML_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("arena_portal")
public class ArenaPortalAI2 extends PortalDialogAI2
{
	@Override
	public boolean onDialogSelect(Player player, int dialogId, int questId, int extendedRewardIndex) {
		if (questId != 0) {
			super.onDialogSelect(player, dialogId, questId, extendedRewardIndex);
			return true;
		}
		int worldId = 0;
		switch (dialogId) {
			case 10000:
				worldId = 300430000;
			break;
			case 10001:
				worldId = 300420000;
			break;
			case 10002:
				worldId = 300570000;
			break;
		}
		AutoGroupType agt = AutoGroupType.getAutoGroupByWorld(player.getLevel(), worldId);
		if (agt != null) {
			PacketSendUtility.sendPacket(player, new S_MATCHMAKER_INFO(agt.getInstanceMaskId(), 0));
		}
		PacketSendUtility.sendPacket(player, new S_NPC_HTML_MESSAGE(getObjectId(), 0));
		return true;
	}
}