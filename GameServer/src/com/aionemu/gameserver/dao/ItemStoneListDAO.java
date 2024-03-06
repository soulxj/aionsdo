package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.ManaStone;

import java.util.*;

public abstract class ItemStoneListDAO implements DAO
{
	public abstract void load(Collection<Item> items);
	public abstract void storeManaStones(Set<ManaStone> manaStones);
	public abstract void storeFusionStones(Set<ManaStone> fusionStones);
	
	public void save(Player player) {
		save(player.getAllItems());
	}
	
	public abstract void save(List<Item> items);
	
	@Override
	public String getClassName() {
		return ItemStoneListDAO.class.getName();
	}
}