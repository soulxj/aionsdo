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
package instance;

import com.aionemu.commons.utils.Rnd;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.*;

/****/
/** Author Rinzler (Encom)
/****/

@InstanceID(320110000)
public class Alquimia_Research_Center extends GeneralInstanceHandler
{
	private Map<Integer, StaticDoor> doors;
	
	@Override
	public void onDropRegistered(Npc npc) {
		Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
		int npcId = npc.getNpcId();
		switch (npcId) {
			case 214027: //iddf3lp_lehparknresearchnmddq_43_an.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000006, 1, true));
		    break;
			case 214034: //iddf3lp2_lehparwikeynamedd_44_ae.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000007, 1, true));
		    break;
		}
	}
	
	@Override
	public void onEnterInstance(final Player player) {
		super.onInstanceCreate(instance);
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
		///If a boss is not spawned, then it is replaced by a mob or npc!!!
		switch (Rnd.get(1, 2)) {
			case 1:
				spawn(213951, 476.5010f, 484.3260f, 204.6906f, (byte) 30); //Special Infantry.
			break;
			case 2:
				spawn(214099, 476.5010f, 484.3260f, 204.6906f, (byte) 30); //Lab Director Odetu.
			break;
		} switch (Rnd.get(1, 2)) {
			case 1:
				spawn(204810, 455.5193f, 488.1331f, 206.7347f, (byte) 54); //Remodeled Test Subject.
			break;
			case 2:
				spawn(214100, 455.5193f, 488.1331f, 206.7347f, (byte) 54); //RA-121 Ex.
			break;
		}
	}
	
	public void removeItems(Player player) {
        Storage storage = player.getInventory();
        storage.decreaseByItemId(185000006, storage.getItemCountByItemId(185000006));
		storage.decreaseByItemId(185000007, storage.getItemCountByItemId(185000007));
    }
	
	@Override
	public void onPlayerLogOut(Player player) {
		removeItems(player);
	}
	
	@Override
	public void onLeaveInstance(Player player) {
		removeItems(player);
	}
	
    @Override
    public void onInstanceDestroy() {
        doors.clear();
    }
}