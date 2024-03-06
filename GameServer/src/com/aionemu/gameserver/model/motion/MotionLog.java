package com.aionemu.gameserver.model.motion;

import java.util.ArrayList;
import java.util.List;

import javolution.util.FastMap;

import com.aionemu.gameserver.model.Gender;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.skillengine.model.WeaponTypeWrapper;

public class MotionLog {
	private FastMap<WeaponTypeWrapper, List<SkillTime>> motionsForWeapons = new FastMap<WeaponTypeWrapper, List<SkillTime>>();
	 
	 public FastMap<WeaponTypeWrapper, List<SkillTime>> getMotionLog() {
		 return this.motionsForWeapons;
	 }
	 public boolean addSkillTime(WeaponTypeWrapper weapon, SkillTime skillTime) {
		 if (motionsForWeapons.containsKey(weapon)) {
			 if (!motionsForWeapons.containsValue(skillTime)) {
				 motionsForWeapons.get(weapon).add(skillTime);
				 return true;
			 }
		 } else {
			 List<SkillTime> list = new ArrayList<SkillTime>();
			 list.add(skillTime);
			 motionsForWeapons.put(weapon, list);
			 return true;
		 }
		 
		 return false;
	 }
	 
	 public int getTime(WeaponTypeWrapper weapon, int skillId, int currentAttackSpeed, Race race, Gender gender) {
			 if (motionsForWeapons.containsKey(weapon)) {
				 for (SkillTime st : motionsForWeapons.get(weapon)) {
					 if(st.getSkillId() == skillId && st.getAttackSpeed() == currentAttackSpeed
						 && st.getRace() == race && st.getGender() == gender )
						 return st.getClientTime();
				 }
			 }
		 
		 return 0;
	 }
	 
	 public boolean isPresent(WeaponTypeWrapper weapon, int skillId, int currentAttackSpeed, Race race, Gender gender) {
			 if (motionsForWeapons.containsKey(weapon)) {
				 for (SkillTime st : motionsForWeapons.get(weapon)) {
					 if(st.getSkillId() == skillId && st.getAttackSpeed() == currentAttackSpeed
						  && st.getRace() == race && st.getGender() == gender)
						 return true;
				 }
			 }
		 
		 return false;
	 }
}
