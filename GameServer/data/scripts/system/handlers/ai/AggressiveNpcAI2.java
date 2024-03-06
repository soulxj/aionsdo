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

import com.aionemu.commons.network.util.ThreadPoolManager;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.event.AIEventType;
import com.aionemu.gameserver.ai2.handler.*;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.skillengine.SkillEngine;

@AIName("aggressive")
public class AggressiveNpcAI2 extends GeneralNpcAI2
{
	@Override
    protected void handleCreatureSee(Creature creature) {
        CreatureEventHandler.onCreatureSee(this, creature);
    }
	
    @Override
    protected void handleCreatureMoved(Creature creature) {
        CreatureEventHandler.onCreatureMoved(this, creature);
    }
	
	@Override
	protected void handleCreatureAggro(Creature creature) {
		if (canThink()) {
		    AggroEventHandler.onAggro(this, creature);
		}
	}
	
	@Override
	protected boolean handleGuardAgainstAttacker(Creature attacker) {
		return AggroEventHandler.onGuardAgainstAttacker(this, attacker);
	}
	
	protected void creatureNeedHelp(int distance) {
		Creature firstTarget = getAggroList().getMostHated();
		for (VisibleObject object : getKnownList().getKnownObjects().values()) {
			if (object instanceof Npc && isInRange(object, distance)) {
				Npc npc = (Npc) object;
				if ((npc != null) && !npc.getLifeStats().isAlreadyDead()) {
					npc.getAi2().onCreatureEvent(AIEventType.CREATURE_AGGRO, firstTarget);
				}
			}
		}
	}
	
	@Override
    protected void handleBackHome() {
        super.handleBackHome();
        if (getSkillList().getUseInSpawnedSkill() != null) {
            final int skillId = getSkillList().getUseInSpawnedSkill().getSkillId();
            final int skillLevel = getSkillList().getSkillLevel(skillId);
            ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					SkillEngine.getInstance().getSkill(getOwner(), skillId, skillLevel, getOwner()).useNoAnimationSkill();
				}
			}, 3000);
        }
    }
	
    @Override
    protected void handleRespawned() {
        super.handleRespawned();
        if (getSkillList().getUseInSpawnedSkill() != null) {
            final int skillId = getSkillList().getUseInSpawnedSkill().getSkillId();
            final int skillLevel = getSkillList().getSkillLevel(skillId);
			ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					SkillEngine.getInstance().getSkill(getOwner(), skillId, skillLevel, getOwner()).useNoAnimationSkill();
				}
			}, 3000);
        }
    }
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		if (getSkillList().getUseInSpawnedSkill() != null) {
            final int skillId = getSkillList().getUseInSpawnedSkill().getSkillId();
            final int skillLevel = getSkillList().getSkillLevel(skillId);
            ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					SkillEngine.getInstance().getSkill(getOwner(), skillId, skillLevel, getOwner()).useNoAnimationSkill();
				}
			}, 3000);
        }
	}
	
	@Override
	protected AIAnswer pollInstance(AIQuestion question) {
		switch (question) {
	        case CAN_SPAWN_ON_DAYTIME_CHANGE:
			    return AIAnswers.POSITIVE;
			case SHOULD_DECAY:
			    return AIAnswers.POSITIVE;
			case SHOULD_RESPAWN:
			    return AIAnswers.POSITIVE;
			case SHOULD_REWARD:
			    return AIAnswers.POSITIVE;
			case SHOULD_REWARD_AP:
			    return AIAnswers.POSITIVE;
			case CAN_RESIST_ABNORMAL:
			    return AIAnswers.POSITIVE;
			case CAN_ATTACK_PLAYER:
			    return AIAnswers.POSITIVE;
			default:
				return null;
		}
	}
}