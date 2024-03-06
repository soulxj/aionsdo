package com.aionemu.gameserver.model.gameobjects.player.equipmentset;

import java.util.List;

public class EquipmentSetting {

	private int playerId;
	private int macrossId;
	private List<Integer> objIds;

    public EquipmentSetting(int playerId, int macrossId, List<Integer> objIds) {
    	this.playerId = playerId;
		this.macrossId = macrossId;
		this.objIds = objIds;
    }

    public int getPlayerId() {
		return playerId;
	}
	
	public int getMacrossId() {
		return macrossId;
	}
	
	public List<Integer> getObjIds() {
		return objIds;
	}
}
