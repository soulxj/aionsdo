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

import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.handler.TargetEventHandler;

public class MoveEventHandler
{
	public static void onMoveValidate(NpcAI2 npcAI) {
        npcAI.getOwner().getController().onMove();
        TargetEventHandler.onTargetTooFar(npcAI);
    }
	
	public static void onMoveArrived(NpcAI2 npcAI) {
        npcAI.getOwner().getController().onMove();
        TargetEventHandler.onTargetReached(npcAI);
    }
}