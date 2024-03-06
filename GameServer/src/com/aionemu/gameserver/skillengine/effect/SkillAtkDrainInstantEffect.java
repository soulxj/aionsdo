/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.S_HIT_POINT_OTHER.LOG;
import com.aionemu.gameserver.network.aion.serverpackets.S_HIT_POINT_OTHER.TYPE;
import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.Effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillAtkDrainInstantEffect")
public class SkillAtkDrainInstantEffect extends DamageEffect {

	@XmlAttribute(name = "hp_percent")
	protected int hp_percent;
	@XmlAttribute(name = "mp_percent")
	protected int mp_percent;
	@XmlAttribute(name = "sp")
	protected int sp;

	@Override
	public void applyEffect(Effect effect) {
		super.applyEffect(effect);
		Creature effector = effect.getEffector();
		if (hp_percent != 0) {
			effect.getEffector().getLifeStats().increaseHp(TYPE.ABSORBED_HP, effect.getReserved1() * hp_percent / 100, effect.getSkillId(), LOG.SKILLLATKDRAININSTANT);
		}
		if (mp_percent != 0) {
			effect.getEffector().getLifeStats().increaseMp(TYPE.MP, effect.getReserved1() * mp_percent / 100, effect.getSkillId(), LOG.SKILLLATKDRAININSTANT);
		}
		if (sp != 0) {
			((Player)effector).getCommonData().addSp(sp);
		}
	}

	@Override
	public void calculate(Effect effect) {
		super.calculate(effect, DamageType.PHYSICAL);
	}
}
