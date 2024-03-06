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
package com.aionemu.gameserver.controllers.effect;

/**
 * @author cheatkiller
 *
 */
public class EffectsContainer {
	
	private int skillId;
	private int skillLvl;
	private int noRemoveType;
	
	
	public EffectsContainer(int skillId, int skillLvl, int noRemoveType) {
		this.skillId = skillId;
		this.skillLvl = skillLvl;
		this.noRemoveType = noRemoveType;
	}


	public int getSkillId() {
		return skillId;
	}


	public int getSkillLvl() {
		return skillLvl;
	}


	public int getNoRemoveType() {
		return noRemoveType;
	}

}
