package mysql5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import com.aionemu.gameserver.dao.PlayerEquipmentSettingDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.equipmentset.EquipmentSetting;

public class MySQL5PlayerEquipmentSettingDAO extends PlayerEquipmentSettingDAO
{
	private static final Logger log = LoggerFactory.getLogger(MySQL5PlayerEquipmentSettingDAO.class);
	private static final String SELECT_QUERY = "SELECT * FROM player_equipment_setting WHERE player_id = ?";
	private static final String REPLACE_QUERY = "REPLACE INTO player_equipment_setting (`player_id`, `macross_id`, `item_unique_id_1`, `item_unique_id_2`, `item_unique_id_3`, `item_unique_id_4`, `item_unique_id_5`, `item_unique_id_6`, `item_unique_id_7`, `item_unique_id_8`, `item_unique_id_9`, `item_unique_id_10`, `item_unique_id_11`, `item_unique_id_12`, `item_unique_id_13`, `item_unique_id_14`, `item_unique_id_15`, `item_unique_id_16`, `item_unique_id_17`, `item_unique_id_18`, `item_unique_id_19`, `item_unique_id_20`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE_QUERY = "DELETE FROM player_equipment_setting WHERE player_id = ? AND macross_id = ?";

    public void loadEquipmentSetting(Player player) {
    	try(Connection conn = DatabaseFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_QUERY)) {
			stmt.setInt(1, player.getObjectId());
			try(ResultSet rset = stmt.executeQuery()) {
				while(rset.next()) {
					int macrossId = rset.getInt("macross_id");
					List<Integer>objIds = new ArrayList<>(20);
			    	for (int i = 1; i < 21; i++) {
			    		objIds.add(rset.getInt("item_unique_id_"+i));
			    	}
			    	player.putPlayerEquipSettingList(new EquipmentSetting(player.getObjectId(), macrossId, objIds));
				}
			}
		}
		catch(Exception e) {
			log.error("Error in MySQL5PlayerEquipmentMacrossesDAO.loadEquipmentMacrosses", e);
		}
    }

    @Override
	public void updateEquipmentSetting(EquipmentSetting macross) {
		try(Connection conn = DatabaseFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(REPLACE_QUERY)) {
			stmt.setInt(1, macross.getPlayerId());
			stmt.setInt(2, macross.getMacrossId());
			stmt.setInt(3, macross.getObjIds().get(0));
			stmt.setInt(4, macross.getObjIds().get(1));
			stmt.setInt(5, macross.getObjIds().get(2));
			stmt.setInt(6, macross.getObjIds().get(3));
			stmt.setInt(7, macross.getObjIds().get(4));
			stmt.setInt(8, macross.getObjIds().get(5));
			stmt.setInt(9, macross.getObjIds().get(6));
			stmt.setInt(10, macross.getObjIds().get(7));
			stmt.setInt(11, macross.getObjIds().get(8));
			stmt.setInt(12, macross.getObjIds().get(9));
			stmt.setInt(13, macross.getObjIds().get(10));
			stmt.setInt(14, macross.getObjIds().get(11));
			stmt.setInt(15, macross.getObjIds().get(12));
			stmt.setInt(16, macross.getObjIds().get(13));
			stmt.setInt(17, macross.getObjIds().get(14));
			stmt.setInt(18, macross.getObjIds().get(15));
			stmt.setInt(19, macross.getObjIds().get(16));
			stmt.setInt(20, macross.getObjIds().get(17));
			stmt.setInt(21, macross.getObjIds().get(18));
			stmt.setInt(22, macross.getObjIds().get(19));
			stmt.executeUpdate();
		}
		catch(Exception e) {
			log.error("Error in MySQL5PlayerEquipmentMacrossesDAO.updateEquipmentMacross", e);
		}
	}

    @Override
    public void deleteEquipmentSetting(EquipmentSetting macross) {
    	try(Connection conn = DatabaseFactory.getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_QUERY)) {
			stmt.setInt(1, macross.getPlayerId());
			stmt.setInt(2, macross.getMacrossId());
			stmt.executeUpdate();
		}
		catch(Exception e) {
			log.error("Error in MySQL5PlayerEquipmentMacrossesDAO.deleteEquipmentMacross", e);
		}
    }

    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}
