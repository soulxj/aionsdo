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
package com.aionemu.gameserver.model.stats.calc;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.stats.calc.Stat2;
import com.aionemu.gameserver.model.stats.container.StatEnum;

public class ReverseStat extends Stat2
{
    public ReverseStat(StatEnum stat, int base, Creature owner) {
        super(stat, base, owner);
    }

    public ReverseStat(StatEnum stat, int base, Creature owner, float bonusRate) {
        super(stat, base, owner, bonusRate);
    }

    @Override
    public void addToBase(int base) {
        this.base -= base;
        if (this.base < 0) {
            this.base = 0;
        }
    }

    @Override
    public void addToBonus(int bonus) {
        this.bonus = (int)((float)this.bonus - this.bonusRate * (float)bonus);
    }

    @Override
    public float calculatePercent(int delta) {
        float percent = (float)(100 - delta) / 100.0f;
        return percent < 0.0f ? 0.0f : percent;
    }
}