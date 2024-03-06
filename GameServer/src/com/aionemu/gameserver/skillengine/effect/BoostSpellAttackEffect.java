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

import com.aionemu.gameserver.controllers.observer.AttackCalcObserver;
import com.aionemu.gameserver.skillengine.effect.BuffEffect;
import com.aionemu.gameserver.skillengine.model.Effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BoostSpellAttackEffect")
public class BoostSpellAttackEffect extends BuffEffect
{
	/*
	@Override
    public void startEffect(Effect effect) {
        super.startEffect(effect);
        final float percent = 1.0f + (float)this.value / 100.0f;
        AttackCalcObserver observer = null;
        observer = new AttackCalcObserver() {
            @Override
            public float getBaseMagicalDamageMultiplier() {
                return percent;
            }
        };
        effect.getEffected().getObserveController().addAttackCalcObserver(observer);
        effect.setAttackStatusObserver(observer, this.position);
    }
	
    @Override
    public void endEffect(Effect effect) {
        super.endEffect(effect);
        AttackCalcObserver observer = effect.getAttackStatusObserver(this.position);
        effect.getEffected().getObserveController().removeAttackCalcObserver(observer);
    }*/
}