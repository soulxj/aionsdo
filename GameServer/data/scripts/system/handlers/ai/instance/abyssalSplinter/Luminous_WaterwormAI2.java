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
package ai.instance.abyssalSplinter;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.concurrent.Future;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("Luminous_Waterworm")
public class Luminous_WaterwormAI2 extends NpcAI2
{
	private Npc pazuzu;
	private Future<?> replenishmentTask;
	
	@Override
	public void think() {
	}
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		pazuzu = getPosition().getWorldMapInstance().getNpc(216951);
		replenishment();
	}
	
	private void replenishment() {
		replenishmentTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (pazuzu != null && !NpcActions.isAlreadyDead(pazuzu)) {
					SkillEngine.getInstance().applyEffectDirectly(19291, getOwner(), pazuzu, 0); //Replenishment.
				}
			}
		}, 3000, 10000);
	}
	
	@Override
	protected void handleDespawned() {
		super.handleDespawned();
		replenishmentTask.cancel(true);
	}
	
    @Override
	public boolean isMoveSupported() {
		return false;
	}
}