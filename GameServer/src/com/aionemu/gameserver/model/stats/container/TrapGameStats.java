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
package com.aionemu.gameserver.model.stats.container;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.stats.calc.Stat2;
import com.aionemu.gameserver.model.stats.container.NpcGameStats;
import com.aionemu.gameserver.model.stats.container.StatEnum;

public class TrapGameStats extends NpcGameStats
{
	public TrapGameStats(Npc owner) {
		super(owner);
	}
	
	@Override
    public Stat2 getStat(StatEnum statEnum, int base) {
        Stat2 stat = super.getStat(statEnum, base);
        if (((Npc)this.owner).getMaster() == null) {
            return stat;
        } switch (statEnum) {
            case BOOST_MAGICAL_SKILL: 
            case MAGICAL_ACCURACY: {
                stat.setBonusRate(0.7f);
                return ((Npc)this.owner).getMaster().getGameStats().getItemStatBoost(statEnum, stat);
            }
        }
        return stat;
    }
	
	@Override
    public Stat2 getAttackRange() {
        return this.getStat(StatEnum.ATTACK_RANGE, 7000);
    }
	
    public int getEffectiveRange() {
        int range = 10;
        return range;
    }
	
	@Override
    public Stat2 getMAccuracy() {
        int value = 2500;
        value = (int)((float)value * 1.2f);
        return this.getStat(StatEnum.MAGICAL_ACCURACY, value);
    }
}