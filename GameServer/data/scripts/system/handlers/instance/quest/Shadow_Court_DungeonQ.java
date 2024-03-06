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
package instance.quest;

import com.aionemu.commons.utils.Rnd;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.ClassChangeService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.*;

/****/
/** Author Rinzler (Encom)
/****/

@InstanceID(320120000)
public class Shadow_Court_DungeonQ extends GeneralInstanceHandler
{
	private Map<Integer, StaticDoor> doors;
	
	@Override
	public void onDropRegistered(Npc npc) {
		Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
		int npcId = npc.getNpcId();
		switch (npcId) {
			case 214347: //arena_3f_graveknightkey_44_an.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000014, 1, true));
		    break;
			case 214349: //arena_3f_kalnifkey_45_an.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000011, 1, true));
		    break;
			case 214351: //arena_3f_direbeastkey_45_an.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000012, 1, true));
		    break;
			case 214353: //arena_3f_mosbearrekey_45_an.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000013, 1, true));
		    break;
			case 214357: //arena_3f_undeadlightpr_46_an.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000009, 1, true));
		    break;
			case 214360: //arena_3f_undeaddarkra_46_an.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000010, 1, true));
		    break;
			case 214531: //arena_3f_guardda_46_an.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000008, 1, true));
		    break;
		}
	}
	
	@Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        doors = instance.getDoors();
		instance.doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				player.getController().updateZone();
				player.getController().updateNearbyQuests();
			}
		});
		///Unfest Guard Captain.
		switch (Rnd.get(1, 2)) {
		    case 1:
				spawn(214347, 548.73553f, 450.79490f, 202.98270f, (byte) 0);
			break;
			case 2:
				spawn(214347, 634.19696f, 451.10135f, 202.96854f, (byte) 60);
			break;
		}
    }
	
	@Override
	public void onEnterInstance(final Player player) {
		super.onInstanceCreate(instance);
		ClassChangeService.onUpdateMission2076A(player);
		instance.doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				if (player.isOnline()) {
					PacketSendUtility.sendPacket(player, new S_PLAY_CUTSCENE(0, 423));
				}
			}
		});
	}
	
	@Override
	public void handleUseItemFinish(final Player player, final Npc npc) {
		switch (npc.getNpcId()) {
			case 700369: //Shadow Court Dungeon Exit.
				shadowExit(player, 982.0000f, 1552.0000f, 210.0000f, (byte) 105);
			break;
		}
	}
	
	protected void shadowExit(Player player, float x, float y, float z, byte h) {
		ClassChangeService.onUpdateMission2076B(player);
		TeleportService2.teleportTo(player, 120010000, 1, x, y, z, h);
	}
	
	@Override
    public void onInstanceDestroy() {
        doors.clear();
    }
}