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
package com.aionemu.gameserver.model.autogroup;

import com.aionemu.gameserver.dataholders.DataManager;

import java.util.List;

/**
 * @author Rinzler (Encom)
 */

public enum AutoGroupType
{
	//DREDGION.
	BARANATH_DREDGION(1, 600000, 4) { @Override AutoInstance newAutoInstance() { return new AutoDredgionInstance(); } },
	CHANTRA_DREDGION(2, 600000, 4) { @Override AutoInstance newAutoInstance() { return new AutoDredgionInstance(); } },
	TIAK_RESEARCH_BASE(33, 600000, 6) { @Override AutoInstance newAutoInstance() { return new AutoTiakResearchBaseInstance(); } },
	
	//ARENA PVP 46-60
	ARENA_OF_CHAOS_46_50_1(21, 600000, 2, 1) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	ARENA_OF_CHAOS_51_55_2(22, 600000, 2, 2) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	ARENA_OF_CHAOS_56_60_3(23, 600000, 2, 3) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	ARENA_OF_DISCIPLINE_46_50_1(24, 600000, 2, 1) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	ARENA_OF_DISCIPLINE_51_55_2(25, 600000, 2, 2) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	ARENA_OF_DISCIPLINE_56_60_3(26, 600000, 2, 3) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	CHAOS_TRAINING_GROUNDS_46_50_1(27, 600000, 2, 1) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	CHAOS_TRAINING_GROUNDS_51_55_2(28, 600000, 2, 2) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	CHAOS_TRAINING_GROUNDS_56_60_3(29, 600000, 2, 3) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	DISCIPLINE_TRAINING_GROUNDS_46_50_1(30, 600000, 2, 1) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	DISCIPLINE_TRAINING_GROUNDS_51_55_2(31, 600000, 2, 2) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	DISCIPLINE_TRAINING_GROUNDS_56_60_3(32, 600000, 2, 3) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	ARENA_OF_GLORY(34, 600000, 1) { @Override AutoInstance newAutoInstance() { return new AutoPvPFFAInstance(); } },
	//INSTANCE.
	FIRE_TEMPLE(1001, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	AETHEROGENETICS_LAB(1002, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	DRAUPNIR_CAVE(1003, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	ALQUIMIA_RESEARCH_CENTER(1004, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	INDRATU_FORTRESS(1005, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	AZOTURAN_FORTRESS(1006, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	THEOBOMOS_LAB(1007, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	ADMA_STRONGHOLD(1008, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	DARK_POETA(1009, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	ESOTERRACE(1010, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	UDAS_TEMPLE(1011, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	LOWER_UDAS_TEMPLE(1012, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	BESHMUNDIR_TEMPLE_NORMAL(1013, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	BESHMUNDIR_TEMPLE_HARD(1014, 600000, 2) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	PADMARASHKA_CAVE(1016, 600000, 6) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	IDABRE_LOW_WCIEL(1019, 600000, 6) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	CHAMBER_OF_ROAH(1023, 600000, 6) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	ASTERIA_CHAMBER(1025, 600000, 6) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	ABYSSAL_SPLINTER(1033, 600000, 6) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	EMPYREAN_CRUCIBLE(1036, 600000, 6) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	IDLDF1(1037, 600000, 6) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } },
	IDLDF1_H(1038, 600000, 6) { @Override AutoInstance newAutoInstance() { return new AutoGeneralInstance(); } };
	
	private int instanceMaskId;
	private int time;
	private byte playerSize;
	private byte difficultId;
	private AutoGroup template;
	
	private AutoGroupType(int instanceMaskId, int time, int playerSize, int difficultId) {
        this(instanceMaskId, time, playerSize);
        this.difficultId = (byte) difficultId;
    }
	
	private AutoGroupType(int instanceMaskId, int time, int playerSize) {
        this.instanceMaskId = instanceMaskId;
        this.time = time;
        this.playerSize = (byte) playerSize;
        template = DataManager.AUTO_GROUP.getTemplateByInstaceMaskId(this.instanceMaskId);
    }
	
	public int getInstanceMapId() {
		return template.getInstanceId();
	}
	
	public byte getPlayerSize() {
		return playerSize;
	}
	
	public int getInstanceMaskId() {
		return instanceMaskId;
	}
	
	public int getNameId() {
		return template.getNameId();
	}
	
	public int getTitleId() {
		return template.getTitleId();
	}
	
	public int getTime() {
		return time;
	}
	
	public int getMinLevel() {
		return template.getMinLvl();
	}
	
	public int getMaxLevel() {
		return template.getMaxLvl();
	}
	
	public boolean hasRegisterGroup() {
		return template.hasRegisterGroup();
	}
	
	public boolean hasRegisterFast() {
		return template.hasRegisterFast();
	}
	
	public boolean hasRegisterNew() {
		return template.hasRegisterNew();
	}
	
	public boolean containNpcId(int npcId) {
		return template.getNpcIds().contains(npcId);
	}
	
	public List<Integer> getNpcIds() {
		return template.getNpcIds();
	}
	
	public boolean isDredgion() {
		switch (this) {
			case BARANATH_DREDGION:
			case CHANTRA_DREDGION:
				return true;
		}
		return false;
	}
	public boolean isTiakBase() {
		switch (this) {
			case TIAK_RESEARCH_BASE:
				return true;
		}
		return false;
	}
	public boolean isPvPSoloArena() {
		switch (this) {
			case ARENA_OF_DISCIPLINE_46_50_1:
			case ARENA_OF_DISCIPLINE_51_55_2:
			case ARENA_OF_DISCIPLINE_56_60_3:
				return true;
		}
		return false;
	}
	public boolean isPvPFFAArena() {
		switch (this) {
			case ARENA_OF_CHAOS_46_50_1:
			case ARENA_OF_CHAOS_51_55_2:
			case ARENA_OF_CHAOS_56_60_3:
				return true;
		}
		return false;
	}
	public boolean isTrainingPvPFFAArena() {
		switch (this) {
			case CHAOS_TRAINING_GROUNDS_46_50_1:
			case CHAOS_TRAINING_GROUNDS_51_55_2:
			case CHAOS_TRAINING_GROUNDS_56_60_3:
				return true;
		}
		return false;
	}
	public boolean isTrainingPvPSoloArena() {
		switch (this) {
			case DISCIPLINE_TRAINING_GROUNDS_46_50_1:
			case DISCIPLINE_TRAINING_GROUNDS_51_55_2:
			case DISCIPLINE_TRAINING_GROUNDS_56_60_3:
				return true;
		}
		return false;
	}
	public boolean isGloryArena() {
		switch (this) {
			case ARENA_OF_GLORY:
				return true;
		}
		return false;
	}
	
	public static AutoGroupType getAGTByMaskId(int instanceMaskId) {
		for (AutoGroupType autoGroupsType : values()) {
			if (autoGroupsType.getInstanceMaskId() == instanceMaskId) {
				return autoGroupsType;
			}
		}
		return null;
	}
	
	public static AutoGroupType getAutoGroup(int level, int npcId) {
		for (AutoGroupType agt : values()) {
			if (agt.hasLevelPermit(level) && agt.containNpcId(npcId)) {
				return agt;
			}
		}
		return null;
	}
	
	public static AutoGroupType getAutoGroupByWorld(int level, int worldId) {
		for (AutoGroupType agt : values()) {
			if (agt.getInstanceMapId() == worldId && agt.hasLevelPermit(level)) {
				return agt;
			}
		}
		return null;
	}
	
	public static AutoGroupType getAutoGroup(int npcId) {
		for (AutoGroupType agt : values()) {
			if (agt.containNpcId(npcId)) {
				return agt;
			}
		}
		return null;
	}
	
	public boolean isPvpArena() {
		return isPvPFFAArena() || isPvPSoloArena() || isTrainingPvPFFAArena() || isTrainingPvPSoloArena();
	}
	
	public boolean hasLevelPermit(int level) {
		return level >= getMinLevel() && level <= getMaxLevel();
	}
	
	public byte getDifficultId() {
		return difficultId;
	}
	
	public AutoInstance getAutoInstance() {
		return newAutoInstance();
	}
	
	abstract AutoInstance newAutoInstance();
}