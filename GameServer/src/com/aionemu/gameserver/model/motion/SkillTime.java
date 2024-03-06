package com.aionemu.gameserver.model.motion;

import com.aionemu.gameserver.model.Gender;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.services.MotionLoggingService;

public class SkillTime implements Comparable<SkillTime> {
	private int skillId;
	private int attackSpeed;
	private int clientTime;
	private Race race;
	private Gender gender;

	public SkillTime(int skillId, int attackSpeed, Race race, Gender gender, int clientTime) {
		this.skillId = skillId;
		this.attackSpeed = attackSpeed;
		this.clientTime = clientTime;
		this.race = race;
		this.gender = gender;
	}

	@Override
	public int compareTo(SkillTime o) {
		if (skillId < o.getSkillId())
			return -1;
		else if (skillId > o.getSkillId())
			return 1;
		else
			return 0;
	}

		
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getOuterType().hashCode();
		result = prime * result + attackSpeed;
		result = prime * result + clientTime;
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((race == null) ? 0 : race.hashCode());
		result = prime * result + skillId;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SkillTime other = (SkillTime) obj;
		if (!getOuterType().equals(other.getOuterType()))
			return false;
		if (attackSpeed != other.attackSpeed)
			return false;
		if (clientTime != other.clientTime)
			return false;
		if (gender != other.gender)
			return false;
		if (race != other.race)
			return false;
		if (skillId != other.skillId)
			return false;
		return true;
	}

	public int getSkillId() {
		return this.skillId;
	}
	
	public int getAttackSpeed() {
		return this.attackSpeed;
	}
		
	public int getClientTime() {
		return this.clientTime;
	}

	public Race getRace() {
		return race;
	}

	public Gender getGender() {
		return gender;
	}

	private MotionLoggingService getOuterType() {
		return MotionLoggingService.getInstance();
	}
}
