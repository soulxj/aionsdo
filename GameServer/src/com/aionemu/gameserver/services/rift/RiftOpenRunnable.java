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
package com.aionemu.gameserver.services.rift;

import com.aionemu.gameserver.model.rift.RiftLocation;
import com.aionemu.gameserver.services.RiftService;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.Map;

/****/
/** Author Rinzler (Encom)
/****/

public class RiftOpenRunnable implements Runnable
{
	private final int worldId;
	private final boolean guards;
	
	public RiftOpenRunnable(int worldId, boolean guards) {
		this.worldId = worldId;
		this.guards = guards;
	}
	
	@Override
    public void run() {
        Map<Integer, RiftLocation> locations = RiftService.getInstance().getRiftLocations();
        for (final RiftLocation loc: locations.values()) {
            if (loc.getWorldId() == worldId) {
                RiftService.getInstance().openRifts(loc, guards);
            }
        }
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                RiftService.getInstance().closeRifts();
            }
        }, RiftService.getInstance().getDuration() * 3540 * 1000);
        RiftInformer.sendRiftsInfo(worldId);
    }
}