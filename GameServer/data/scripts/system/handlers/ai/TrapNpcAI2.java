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
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.network.aion.serverpackets.S_INVISIBLE_LEVEL;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.concurrent.Future;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("trap")
public class TrapNpcAI2 extends NpcAI2
{
	private Future<?> despawnTask;
	
	@Override
	protected void handleCreatureMoved(Creature creature) {
		super.handleCreatureMoved(creature);
		tryActivateTrap(creature);
	}
	
	@Override
	protected void handleSpawned() {
		getKnownList().doUpdate();
		getKnownList().doOnAllObjects(new Visitor<VisibleObject>() {
			@Override
			public void visit(VisibleObject object) {
				if (!(object instanceof Creature)) {
					return;
				}
				Creature creature = (Creature) object;
				tryActivateTrap(creature);
			}
		});
		super.handleSpawned();
	}
	
	private void tryActivateTrap(Creature creature) {
		if (despawnTask != null) {
			return;
		} if (!creature.getLifeStats().isAlreadyDead() &&
		      !creature.isInVisualState(CreatureVisualState.BLINKING) && MathUtil.isIn3dRangeLimited(getOwner(), creature, 1, 10)) {
			Creature creator = (Creature) getCreator();
			if (!creator.isEnemy(creature)) {
				return;
			}
			explode(creature);
		}
	}
	
	private void explode(Creature creature) {
		if (setStateIfNot(AIState.FIGHT)) {
			getOwner().getKnownList().doUpdate();
			getOwner().unsetVisualState(CreatureVisualState.HIDE1);
			PacketSendUtility.broadcastPacket(getOwner(), new S_INVISIBLE_LEVEL(getOwner()));
			AI2Actions.targetCreature(TrapNpcAI2.this, creature);
			AI2Actions.useSkill(this, getSkillList().getRandomSkill().getSkillId());
			despawnTask = ThreadPoolManager.getInstance().schedule(new TrapDelete(this), 3000);
		}
	}
	
	@Override
	public boolean isMoveSupported() {
		return false;
	}
	
	@Override
	protected AIAnswer pollInstance(AIQuestion question) {
		switch (question) {
			case CAN_ATTACK_PLAYER:
                return AIAnswers.POSITIVE;
			default:
				return null;
		}
	}
	
	private static final class TrapDelete implements Runnable {
		private TrapNpcAI2 ai;
		
		TrapDelete(TrapNpcAI2 ai) {
			this.ai = ai;
		}
		
		@Override
		public void run() {
			AI2Actions.deleteOwner(ai);
			ai = null;
		}
	}
}