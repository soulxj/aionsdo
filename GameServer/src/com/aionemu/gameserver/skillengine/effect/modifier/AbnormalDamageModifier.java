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
package com.aionemu.gameserver.skillengine.effect.modifier;

import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.skillengine.model.Effect;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author kecimis
 */
public class AbnormalDamageModifier extends ActionModifier {

	@XmlAttribute(required = true)
	protected AbnormalState state;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.aionemu.gameserver.skillengine.effect.modifier.ActionModifier#analyze(com.aionemu.gameserver.skillengine.model
	 * .Effect)
	 */
	@Override
	public int analyze(Effect effect) {
		return (value + effect.getSkillLevel() * delta);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.aionemu.gameserver.skillengine.effect.modifier.ActionModifier#check(com.aionemu.gameserver.skillengine.model
	 * .Effect)
	 */
	@Override
	public boolean check(Effect effect) {
		return effect.getEffected().getEffectController().isAbnormalSet(state);
	}

}
