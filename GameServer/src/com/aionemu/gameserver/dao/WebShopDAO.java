package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.templates.rewards.RewardEntryItem;
import javolution.util.FastList;

public abstract class WebShopDAO implements DAO
{
	@Override
	public final String getClassName() {
		return WebShopDAO.class.getName();
	}
	
	public abstract FastList<RewardEntryItem> getAvailable(int accountId);
	public abstract void delete(int id);
}