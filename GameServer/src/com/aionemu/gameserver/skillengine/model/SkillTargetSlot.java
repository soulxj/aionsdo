/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.skillengine.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TargetSlot")
@XmlEnum
public enum SkillTargetSlot
{
	BUFF(1),
	DEBUFF(2),
	CHANT(4),
	SPEC(8),
	SPEC2(16),
	BOOST(32),
	NOSHOW(64),
	NONE(128);
	
	private int id;
	
	public static final int FULLSLOTS = 127;

	private SkillTargetSlot(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}