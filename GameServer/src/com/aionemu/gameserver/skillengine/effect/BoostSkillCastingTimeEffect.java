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

import java.util.concurrent.Future;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * 
 * @author Metaverse
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BoostSkillCastingTimeEffect")
public class BoostSkillCastingTimeEffect extends BuffEffect
{
	
	@XmlAttribute
	private int count = 0;
	
	@Override
	public void startEffect(final Effect effect) {
		super.startEffect(effect);
		final int stopCount = count;
		if (stopCount != 0) {
			if (effect.getEffected().getSkillNumber() > 0) {
				effect.getEffected().setSkillNumber(0);
			}
		}
		
		Future<?> task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				onPeriodicAction(effect);
			}
		}, 500, 500);
		
		effect.setPeriodicTask(task, position);
	}
	
	@Override
    public void calculate(Effect effect) {
        if (effect.getEffected().isEnemy(effect.getEffector()) && this.value < 0) {
            super.calculate(effect, StatEnum.SLOW_RESISTANCE, null);
        } else {
            super.calculate(effect);
        } 
    }
	
	@Override
	public void onPeriodicAction(Effect effect) {
		final int stopCount = count;
		if (stopCount != 0) {
			if (effect.getEffected().getSkillNumber() == stopCount) {
				effect.getEffected().getEffectController().removeEffect(effect.getSkillId());
			}
		}
	}
	
	@Override
	public void endEffect(Effect effect) {
		if (effect.getEffected().getSkillNumber() > 0)
			effect.getEffected().setSkillNumber(0);
	}
}