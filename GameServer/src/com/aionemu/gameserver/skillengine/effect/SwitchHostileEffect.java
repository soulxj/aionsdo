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
package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.controllers.attack.AggroList;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SwitchHostileEffect")
public class SwitchHostileEffect extends EffectTemplate {

	@Override
	public void applyEffect(Effect effect) {

		Creature effected = effect.getEffected();
		Creature effector = effect.getEffector();
		AggroList aggroList = effected.getAggroList();

		if (((Player) effector).getSummon() != null) {
			Creature summon = ((Player) effector).getSummon();
			int playerHate = aggroList.getAggroInfo(effector).getHate();
			int summonHate = aggroList.getAggroInfo(((Player) effector).getSummon()).getHate();

			aggroList.stopHating(summon);
			aggroList.stopHating(effector);
			aggroList.addHate(effector, summonHate);
			aggroList.addHate(summon, playerHate);
		}
	}
}
