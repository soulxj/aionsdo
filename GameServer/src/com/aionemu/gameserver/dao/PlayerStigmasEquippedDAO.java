package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class PlayerStigmasEquippedDAO implements DAO {

	@Override
	public final String getClassName() {
		return PlayerStigmasEquippedDAO.class.getName();
	}

	public abstract boolean storeItems(Player player);

}
