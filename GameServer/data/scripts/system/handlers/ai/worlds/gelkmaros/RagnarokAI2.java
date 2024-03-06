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
package ai.worlds.gelkmaros;

import ai.AggressiveNpcAI2;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.commons.network.util.ThreadPoolManager;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("Ragnarok")
public class RagnarokAI2 extends AggressiveNpcAI2
{
	private Future<?> skillTask;
	private AtomicBoolean isHome = new AtomicBoolean(true);
	
	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		if (isHome.compareAndSet(true, false)) {
			startSkillTask();
		}
	}
	
	private void startSkillTask() {
		skillTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (isAlreadyDead()) {
					cancelTask();
				} else {
					chooseAttack();
				}
			}
		}, 5000, 40000);
	}
	
	private void chooseAttack() {
		switch (Rnd.get(1, 5)) {
			case 1:
			    selfHarm();
			break;
			case 2:
			    mindLash();
			break;
			case 3:
			    survivalInstinct();
			break;
			case 4:
			    ragnarokParasite();
			break;
			case 5:
			    ragnarokSlime();
			break;
		}
	}
	
	private void selfHarm() {
		DF4RaidShowTimePhase1();
		SkillEngine.getInstance().getSkill(getOwner(), 18679, 60, getTarget()).useNoAnimationSkill(); //Self Harm.
	}
	private void mindLash() {
		DF4RaidShowTimePhase2();
		SkillEngine.getInstance().getSkill(getOwner(), 18989, 60, getTarget()).useNoAnimationSkill(); //Mind Lash.
	}
	private void survivalInstinct() {
		DF4RaidShowTimePhase4();
		SkillEngine.getInstance().getSkill(getOwner(), 18992, 60, getOwner()).useNoAnimationSkill(); //Survival Instinct.
	}
	
	private void ragnarokParasite() {
		DF4RaidShowTimePhase3();
		SkillEngine.getInstance().getSkill(getOwner(), 19124, 60, getTarget()).useNoAnimationSkill(); //Ragnarok's Scream.
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				rndSpawn(281950, 3);
			}
		}, 5000);
	}
	
	private void ragnarokSlime() {
		DF4RaidShowTimePhase3();
		SkillEngine.getInstance().getSkill(getOwner(), 19124, 60, getTarget()).useNoAnimationSkill(); //Ragnarok's Scream.
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				rndSpawn(281951, 3);
			}
		}, 5000);
	}
	
	private void cancelTask() {
		if (skillTask != null && !skillTask.isCancelled()) {
			skillTask.cancel(true);
		}
	}
	
	private void rndSpawn(int npcId, int count) {
		for (int i = 0; i < count; i++) {
			SpawnTemplate template = rndSpawnInRange(npcId, 10);
			SpawnEngine.spawnObject(template, getPosition().getInstanceId());
		}
	}
	
	protected SpawnTemplate rndSpawnInRange(int npcId, float distance) {
		float direction = Rnd.get(0, 199) / 100f;
		float x = (float) (Math.cos(Math.PI * direction) * distance);
        float y = (float) (Math.sin(Math.PI * direction) * distance);
		return SpawnEngine.addNewSingleTimeSpawn(getPosition().getMapId(), npcId, getPosition().getX() + x, getPosition().getY() + y, getPosition().getZ(), getPosition().getHeading());
	}
	
	private void DF4RaidShowTimePhase1() {
		getPosition().getWorldMapInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				if (player.isOnline()) {
					//Attack of poison and paralysis begins.
					PacketSendUtility.npcSendPacketTime(getOwner(), S_MESSAGE_CODE.STR_MSG_DF4_RaidShowTime_Phase1, 0);
				}
			}
		});
	}
	private void DF4RaidShowTimePhase2() {
		getPosition().getWorldMapInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				if (player.isOnline()) {
					//Attack that restricts physical and magical assaults begins.
					PacketSendUtility.npcSendPacketTime(getOwner(), S_MESSAGE_CODE.STR_MSG_DF4_RaidShowTime_Phase2, 0);
				}
			}
		});
	}
	private void DF4RaidShowTimePhase3() {
		getPosition().getWorldMapInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				if (player.isOnline()) {
					//Ragnarok's acidic fluid appears.
					PacketSendUtility.npcSendPacketTime(getOwner(), S_MESSAGE_CODE.STR_MSG_DF4_RaidShowTime_Phase3, 0);
				}
			}
		});
	}
	private void DF4RaidShowTimePhase4() {
		getPosition().getWorldMapInstance().doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				if (player.isOnline()) {
					//Powerful continuous attacks and reflections begin.
					PacketSendUtility.npcSendPacketTime(getOwner(), S_MESSAGE_CODE.STR_MSG_DF4_RaidShowTime_Phase4, 0);
				}
			}
		});
	}
	
	private void killNpc(List<Npc> npcs) {
		for (Npc npc: npcs) {
			AI2Actions.killSilently(this, npc);
		}
	}
	
	@Override
    protected void handleSpawned() {
        super.handleSpawned();
    }
	
	@Override
	protected void handleDespawned() {
		super.handleDespawned();
		cancelTask();
	}
	
	@Override
	protected void handleBackHome() {
		super.handleBackHome();
		cancelTask();
		isHome.set(true);
		WorldMapInstance instance = getPosition().getWorldMapInstance();
		killNpc(instance.getNpcs(281950));
		killNpc(instance.getNpcs(281951));
	}
	
	@Override
	protected void handleDied() {
		super.handleDied();
		cancelTask();
		///Heh heh heh, I will destroy everything....
		sendMsg(1500177, getObjectId(), false, 0);
		getOwner().getEffectController().removeAllEffects();
		WorldMapInstance instance = getPosition().getWorldMapInstance();
		killNpc(instance.getNpcs(281950));
		killNpc(instance.getNpcs(281951));
	}
	
	private void sendMsg(int msg, int Obj, boolean isShout, int time) {
		NpcShoutsService.getInstance().sendMsg(getPosition().getWorldMapInstance(), msg, Obj, isShout, 0, time);
	}
}