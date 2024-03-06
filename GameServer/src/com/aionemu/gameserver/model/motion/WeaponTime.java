package com.aionemu.gameserver.model.motion;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.Gender;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.services.MotionLoggingService;
import com.aionemu.gameserver.skillengine.model.WeaponTypeWrapper;

public class WeaponTime {
	private static Logger log = LoggerFactory.getLogger(WeaponTime.class);
	private TreeMap<WeaponTypeWrapper,List<Integer>> values = new TreeMap<WeaponTypeWrapper,List<Integer>>();
	private Race race;
	private Gender gender;
	 
	public WeaponTime(Race race, Gender gender) {
		this.race = race;
		this.gender = gender;
	}
	 
	/**
	 * @return the race
	 */
	public Race getRace() {
		return race;
	}

	/**
	 * @param race the race to set
	 */
	public void setRace(Race race) {
		this.race = race;
	}
	
	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}
	
	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void add(WeaponTypeWrapper weapon, int value) {
		 if (values.containsKey(weapon))
			values.get(weapon).add(value);
		else {
			List<Integer> list = new ArrayList<Integer>();
			list.add(value);
			values.put(weapon, list);
		}
	 }
	 
		public TreeMap<WeaponTypeWrapper, Integer> process() {
			TreeMap<WeaponTypeWrapper, Integer> weaponMap = new TreeMap<WeaponTypeWrapper, Integer>();
			
			for (Entry<WeaponTypeWrapper, List<Integer>> entry2 : values.entrySet()) {
				//logic to calculate one value per weaponType
				//count the element with the most occurencies
				int finalValue = 0;
				int maxFrequency = 0;
				int value = 0;
				int total = 0;
				for (Integer i : entry2.getValue()) {
					total += i;
					if (MotionLoggingService.getInstance().calculateFrequency(entry2.getValue(), i) > maxFrequency) {
						maxFrequency = MotionLoggingService.getInstance().calculateFrequency(entry2.getValue(), i);
						value = i;
					}
				}
				log.info("maxFrequency: "+maxFrequency+" value: "+value+" size: "+entry2.getValue().size());
				//if frequency of given value is higher than 70% take it, otherwise do Arithmetic mean
				if (Math.round((float)entry2.getValue().size() * 0.7f) <= maxFrequency)
					finalValue = value;
				else
					finalValue = total / entry2.getValue().size();
			
				
				log.info("weaponTime.process() finalValue: "+finalValue);
				weaponMap.put(entry2.getKey(), finalValue);
			}

			return weaponMap;
		}
}
