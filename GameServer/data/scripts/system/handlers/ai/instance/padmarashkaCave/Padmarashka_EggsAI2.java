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
package ai.instance.padmarashkaCave;

import com.aionemu.commons.utils.Rnd;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.skillengine.SkillEngine;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("Padmarashka_Eggs")
public class Padmarashka_EggsAI2 extends NpcAI2
{
	private void applySoldierWrath(final Npc npc) {
		SkillEngine.getInstance().getSkill(npc, 20176, 60, npc).useNoAnimationSkill(); //Hatching Soldier's Wrath.
	}
	
	@Override
	protected void handleDied() {
		//Neonate Drakan.
		switch (Rnd.get(1, 5)) {
			case 1:
			    applySoldierWrath((Npc) spawn(282615, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) getOwner().getHeading()));
			break;
			case 2:
			    applySoldierWrath((Npc) spawn(282616, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) getOwner().getHeading()));
			break;
			case 3:
			    applySoldierWrath((Npc) spawn(282617, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) getOwner().getHeading()));
			break;
			case 4:
			    applySoldierWrath((Npc) spawn(282618, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) getOwner().getHeading()));
			break;
			case 5:
			    applySoldierWrath((Npc) spawn(282619, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) getOwner().getHeading()));
			break;
		}
		super.handleDied();
		AI2Actions.deleteOwner(this);
	}
}