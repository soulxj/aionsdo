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
package com.aionemu.gameserver.model.templates.item;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rinzler (Encom)
 */

@XmlType(name = "weapon_type")
@XmlEnum
public enum WeaponType
{
	SWORD_1H(new int[] {1, 8, 336, 342, 368}, 1),
	DAGGER_1H(new int[] {2, 9, 30, 337, 343, 369}, 1),
	MACE_1H(new int[] {3, 10, 338, 344, 370}, 1),
	BOOK_2H(new int[] {64, 1551, 1552, 1580}, 2),
	ORB_2H(new int[] {64, 1743, 1745, 1777}, 2),
	SWORD_2H(new int[] {15, 339, 345, 371}, 2),
	POLEARM_2H(new int[] {16, 340, 346, 372}, 2),
	STAFF_2H(new int[] {53, 1157, 1159, 1175}, 2),
	BOW(new int[] {17, 341, 347, 373}, 2),
	BLADE_2H(new int[] {2520}, 2),
	TOOLHOE_1H(new int[] {}, 1),
	TOOLPICK_2H(new int[] {}, 2),
	TOOLROD_2H(new int[] {}, 2);
	
	private int slots;
	private int[] requiredSkill;
	
	private WeaponType(int[] requiredSkills, int slots) {
		this.requiredSkill = requiredSkills;
		this.slots = slots;
	}
	
	public int[] getRequiredSkills() {
		return requiredSkill;
	}
	
	public int getRequiredSlots() {
		return slots;
	}
	
	public int getMask() {
		return 1 << this.ordinal();
	}
}