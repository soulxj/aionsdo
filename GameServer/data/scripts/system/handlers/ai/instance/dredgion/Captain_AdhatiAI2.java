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
package ai.instance.dredgion;

import ai.AggressiveNpcAI2;

import com.aionemu.commons.utils.Rnd;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.world.WorldMapInstance;

import java.util.*;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("Captain_Adhati")
public class Captain_AdhatiAI2 extends AggressiveNpcAI2
{
	private boolean canThink = true;
	private int curentPercent = 100;
	private List<Integer> percents = new ArrayList<Integer>();
	
	@Override
	public boolean canThink() {
		return canThink;
	}
	
	@Override
	public void handleAttack(Creature creature) {
		super.handleAttack(creature);
		checkPercentage(getLifeStats().getHpPercentage());
	}
	
	private void addPercent() {
		percents.clear();
		Collections.addAll(percents, new Integer[]{100, 80, 65, 45, 20});
	}
	
	private synchronized void checkPercentage(int hpPercentage) {
		curentPercent = hpPercentage;
		for (Integer percent: percents) {
			if (hpPercentage <= percent) {
				switch (percent) {
					case 100:
						rndSpawn(281344, 2);
						///A wretched Daeva dared to come here?!
						sendMsg(341866, getObjectId(), false, 0);
					break;
					case 80:
					    rndSpawn(281344, 4);
						///Be gone, forever!
						sendMsg(341867, getObjectId(), false, 0);
						///Ye shall know the greatness of the Drakan!
						sendMsg(341868, getObjectId(), false, 5000);
					break;
					case 65:
					    rndSpawn(281344, 1);
					    rndSpawn(281346, 1);
						///Be gone, forever!
						sendMsg(341867, getObjectId(), false, 0);
						///Ye shall know the greatness of the Drakan!
						sendMsg(341868, getObjectId(), false, 5000);
					break;
					case 45:
					    rndSpawn(281344, 3);
					    rndSpawn(281345, 1);
						///Be gone, forever!
						sendMsg(341867, getObjectId(), false, 0);
						///Ye shall know the greatness of the Drakan!
						sendMsg(341868, getObjectId(), false, 5000);
					break;
					case 20:
					    rndSpawn(281344, 4);
					    rndSpawn(281345, 1);
					    rndSpawn(281346, 1);
						///Be gone, forever!
						sendMsg(341867, getObjectId(), false, 0);
						///Ye shall know the greatness of the Drakan!
						sendMsg(341868, getObjectId(), false, 5000);
					break;
				}
				percents.remove(percent);
				break;
			}
		}
	}
	
	private void rndSpawn(int npcId, int count) {
		for (int i = 0; i < count; i++) {
			SpawnTemplate template = rndSpawnInRange(npcId, 5);
			SpawnEngine.spawnObject(template, getPosition().getInstanceId());
		}
	}
	
	protected SpawnTemplate rndSpawnInRange(int npcId, float distance) {
		float direction = Rnd.get(0, 199) / 100f;
		float x = (float) (Math.cos(Math.PI * direction) * distance);
        float y = (float) (Math.sin(Math.PI * direction) * distance);
		return SpawnEngine.addNewSingleTimeSpawn(getPosition().getMapId(), npcId, getPosition().getX() + x, getPosition().getY() + y, getPosition().getZ(), getPosition().getHeading());
	}
	
	private void killNpc(List<Npc> npcs) {
		for (Npc npc: npcs) {
			AI2Actions.killSilently(this, npc);
		}
	}
	
	@Override
	protected void handleDespawned() {
		super.handleDespawned();
		percents.clear();
	}
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		addPercent();
	}
	
	@Override
	protected void handleBackHome() {
		super.handleBackHome();
		addPercent();
		canThink = true;
		curentPercent = 100;
		WorldMapInstance instance = getPosition().getWorldMapInstance();
		killNpc(instance.getNpcs(281344));
		killNpc(instance.getNpcs(281345));
		killNpc(instance.getNpcs(281346));
	}
	
	@Override
	protected void handleDied() {
		super.handleDied();
		percents.clear();
		///The time of Balaur... is near...
		sendMsg(341869, getObjectId(), false, 0);
		getOwner().getEffectController().removeAllEffects();
		WorldMapInstance instance = getPosition().getWorldMapInstance();
		killNpc(instance.getNpcs(281344));
		killNpc(instance.getNpcs(281345));
		killNpc(instance.getNpcs(281346));
	}
	
	private void sendMsg(int msg, int Obj, boolean isShout, int time) {
		NpcShoutsService.getInstance().sendMsg(getPosition().getWorldMapInstance(), msg, Obj, isShout, 0, time);
	}
}