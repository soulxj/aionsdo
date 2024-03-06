package com.aionemu.gameserver.network.aion.serverpackets.need;

import java.util.Map.Entry;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.equipmentset.EquipmentSetting;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_LOAD_EQUIPMENT_CHANGE extends AionServerPacket {

	private Player player;

    public S_LOAD_EQUIPMENT_CHANGE(Player player) {
    	this.player = player;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	writeH(player.getPlayerEquipSettingList().size());
		for (Entry<Integer, EquipmentSetting> entry : player.getPlayerEquipSettingList().entrySet()) {
			writeD(entry.getKey());
			entry.getValue().getObjIds().stream().forEachOrdered((p) -> writeD(p));
		}
    }
}