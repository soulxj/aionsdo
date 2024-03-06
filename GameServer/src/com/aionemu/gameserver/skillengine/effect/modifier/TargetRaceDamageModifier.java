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
package com.aionemu.gameserver.skillengine.effect.modifier;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.effect.modifier.ActionModifier;
import com.aionemu.gameserver.skillengine.model.Effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetRaceDamageModifier")
public class TargetRaceDamageModifier extends ActionModifier
{
	@XmlAttribute(name = "race")
	private Race skillTargetRace;

	@Override
    public int analyze(Effect effect) {
        Npc npc;
        Creature effected = effect.getEffected();
        int newValue = this.value + effect.getSkillLevel() * this.delta;
        if (effected instanceof Player) {
            Player player = (Player)effected;
            switch (this.skillTargetRace) {
                case ASMODIANS: {
                    if (player.getRace() == Race.ASMODIANS) {
                        return newValue;
                    }
                } case ELYOS: {
                    if (player.getRace() == Race.ELYOS) {
						return newValue;
					}
                }
            }
        } else if (effected instanceof Npc && (npc = (Npc)effected).getObjectTemplate().getRace().toString().equals(this.skillTargetRace.toString())) {
            return newValue;
        }
        return 0;
    }

    @Override
    public boolean check(Effect effect) {
        Creature effected = effect.getEffected();
        if (effected instanceof Player) {
            Player player = (Player)effected;
            switch (this.skillTargetRace) {
                case ASMODIANS: {
                    if (player.getRace() == Race.ASMODIANS) {
                        return true;
                    }
                } case ELYOS: {
                    if (player.getRace() == Race.ELYOS) {
                        return true;
                    }
                }
            }
        } else if (effected instanceof Npc) {
            Npc npc = (Npc)effected;
            Race race = npc.getObjectTemplate().getRace();
            if (race == null) {
                return false;
            }
            return race.toString().equals(this.skillTargetRace.toString());
        }
        return false;
    }
}