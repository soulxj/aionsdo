/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.ai2.handler;

import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.NpcAI2;

public class SpawnEventHandler
{
	public static void onSpawn(NpcAI2 npcAI) {
        if (npcAI.setStateIfNot(AIState.IDLE) && npcAI.getOwner().getPosition().isMapRegionActive()) {
            npcAI.think();
        }
    }
	
	public static void onDespawn(NpcAI2 npcAI) {
		npcAI.setStateIfNot(AIState.DESPAWNED);
	}
	
	public static void onRespawn(NpcAI2 npcAI) {
		npcAI.getOwner().getMoveController().resetMove();
	}
}