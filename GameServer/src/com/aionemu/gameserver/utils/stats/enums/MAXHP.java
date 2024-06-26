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
package com.aionemu.gameserver.utils.stats.enums;

public enum MAXHP
{
	WARRIOR(1.1688f, 1.1688f, 284),
	GLADIATOR(1.3393f, 48.246f, 342),
	TEMPLAR(1.3288f, 51.878f, 281),
	SCOUT(1.0297f, 40.823f, 219),
	ASSASSIN(1.0488f, 40.38f, 222),
	RANGER(0.5f, 38.5f, 133),
	MAGE(0.7554f, 29.457f, 132),
	SORCERER(0.6352f, 24.852f, 112),
	SPIRIT_MASTER(1, 20.6f, 157),
	PRIEST(1.0303f, 40.824f, 201),
	CLERIC(0.9277f, 35.988f, 229),
	CHANTER(0.9277f, 35.988f, 229),
	MONK(1.1688f, 1.1688f, 284),
	THUNDERER(1.3393f, 48.246f, 342);
	
	private float a;
	private float b;
	private float c;
	
	private MAXHP(float a, float b, float c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public int getMaxHpFor(int level) {
		return Math.round(a * (level - 1) * (level - 1) + b * (level - 1) + c);
	}
}