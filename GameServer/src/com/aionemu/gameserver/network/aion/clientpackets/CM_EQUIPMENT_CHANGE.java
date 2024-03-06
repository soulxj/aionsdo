package com.aionemu.gameserver.network.aion.clientpackets;

import java.util.ArrayList;
import java.util.List;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.PlayerEquipmentSettingDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.equipmentset.EquipmentSetting;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;

public class CM_EQUIPMENT_CHANGE extends AionClientPacket {

	private int macrossId;
	private List<Integer> objIds;

    public CM_EQUIPMENT_CHANGE(int opcode, AionConnection.State state, AionConnection.State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
    	macrossId = readD();
    	objIds = new ArrayList<>(20);
    	for (int i = 0; i < 20; i++) {
    		objIds.add(readD());
    	}
    }

    @Override
    protected void runImpl() {
    	Player player = getConnection().getActivePlayer();
		if (player == null)
			return;
		
		if (objIds.stream().anyMatch((p) -> p > 0)) {
			EquipmentSetting macross = new EquipmentSetting(player.getObjectId(), macrossId, objIds);
			player.putPlayerEquipSettingList(macross);
			DAOManager.getDAO(PlayerEquipmentSettingDAO.class).updateEquipmentSetting(macross);
		}
		else {
			EquipmentSetting macross = player.removePlayerEquipMacross(macrossId);
			if(macross != null)
				DAOManager.getDAO(PlayerEquipmentSettingDAO.class).deleteEquipmentSetting(macross);
		}
    }
}