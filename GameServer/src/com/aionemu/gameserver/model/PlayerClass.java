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
package com.aionemu.gameserver.model;

import com.aionemu.gameserver.model.gameobjects.player.Player;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum PlayerClass
{
	WARRIOR(0, true),
	GLADIATOR(1),
	TEMPLAR(2),
	SCOUT(3, true),
	ASSASSIN(4),
	RANGER(5),
	MAGE(6, true),
	SORCERER(7),
	SPIRIT_MASTER(8),
	PRIEST(9, true),
	CLERIC(10),
	CHANTER(11),
	MONK(12, true),
	THUNDERER(13),
	MARTIAL_ARTIST(14, true),
	BATTLE_MASTER(15),
	ALL(16);
	
	private byte classId;
	private int idMask;
	private boolean startingClass;

	private PlayerClass(int classId) {
		this(classId, false);
	}

	private PlayerClass(int classId, boolean startingClass) {
		this.classId = (byte) classId;
		this.startingClass = startingClass;
		this.idMask = (int) Math.pow(2, classId);
	}

	public byte getClassId() {
		return classId;
	}

	public static PlayerClass getPlayerClassById(byte classId) {
		for (PlayerClass pc : values()) {
			if (pc.getClassId() == classId)
				return pc;
		}
		throw new IllegalArgumentException("There is no player class with id " + classId);
	}

	public boolean isStartingClass() {
		return startingClass;
	}

	public static PlayerClass getStartingClassFor(PlayerClass pc) {
		switch (pc) {
			case ASSASSIN:
			case RANGER:
				return SCOUT;
			case GLADIATOR:
			case TEMPLAR:
				return WARRIOR;
			case CHANTER:
			case CLERIC:
				return PRIEST;
			case SORCERER:
			case SPIRIT_MASTER:
				return MAGE;
			case THUNDERER:
				return MONK;
			case BATTLE_MASTER:
				return MARTIAL_ARTIST;
			case SCOUT:
			case WARRIOR:
			case PRIEST:
			case MAGE:
			case MONK:
				return pc;
			default:
				throw new IllegalArgumentException("Given player class is starting class: " + pc);
		}
	}

	public static PlayerClass getPlayerClassByString(String fieldName) {
		for (PlayerClass pc: values()) {
			if (pc.toString().equals(fieldName))
				return pc;
		}
		return null;
	}

	public int getMask() {
		return idMask;
	}

	public String getClassType(Player player) {
		String type = null;
		switch (player.getPlayerClass()) {
			case RANGER:
			case CHANTER:
			case TEMPLAR:
			case ASSASSIN:
			case GLADIATOR:
			case THUNDERER:
			case BATTLE_MASTER:
				type = "PHYSICAL";
			break;
			case CLERIC:
			case SORCERER:
			case SPIRIT_MASTER:
				type = "MAGICAL";
			break;
			default: break;
		}
		return type;
	}
}