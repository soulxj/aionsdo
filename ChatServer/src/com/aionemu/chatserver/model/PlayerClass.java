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
package com.aionemu.chatserver.model;

public enum PlayerClass
{
	WARRIOR(0),
	GLADIATOR(1),
	TEMPLAR(2),
	SCOUT(3),
	ASSASSIN(4),
	RANGER(5),
	MAGE(6),
	SORCERER(7),
	SPIRIT_MASTER(8),
	PRIEST(9),
	CLERIC(10),
	CHANTER(11),
	MONK(12),
	THUNDERER(13),
	ALL(14);
	
	private byte classId;
	
	private PlayerClass(int classId) {
		this.classId = (byte) classId;
	}
	
	public byte getClassId() {
		return classId;
	}
}