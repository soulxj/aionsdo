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
package ai.instance.kromedesTrial;

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

@AIName("Stone_Of_Vitality")
public class Stone_Of_VitalityAI2 extends NpcAI2
{
	private Npc divineHisen;
	private Future<?> stoneOfVitalityTask;
	
	@Override
	public void think() {
	}
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		divineHisen = getPosition().getWorldMapInstance().getNpc(216968); //Divine Hisen.
		SkillEngine.getInstance().getSkill(getOwner(), 19255, 60, getOwner()).useNoAnimationSkill(); //Crimson Vitality.
		SkillEngine.getInstance().getSkill(getOwner(), 19256, 60, getOwner()).useNoAnimationSkill(); //Restoring Orison.
		stoneOfVitality();
	}
	
	private void stoneOfVitality() {
		stoneOfVitalityTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				SkillEngine.getInstance().getSkill(getOwner(), 19255, 60, getOwner()).useNoAnimationSkill(); //Crimson Vitality.
		        SkillEngine.getInstance().getSkill(getOwner(), 19256, 60, getOwner()).useNoAnimationSkill(); //Restoring Orison.
				if (divineHisen != null && !NpcActions.isAlreadyDead(divineHisen)) {
					SkillEngine.getInstance().applyEffectDirectly(19255, getOwner(), divineHisen, 0); //Crimson Vitality.
					SkillEngine.getInstance().applyEffectDirectly(19256, getOwner(), divineHisen, 0); //Restoring Orison.
				}
			}
		}, 3000, 10000);
	}
	
    @Override
	protected void handleDespawned() {
		super.handleDespawned();
		stoneOfVitalityTask.cancel(true);
	}
	
    @Override
	public boolean isMoveSupported() {
		return false;
	}
}