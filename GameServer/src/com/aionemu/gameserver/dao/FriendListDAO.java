package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class FriendListDAO implements DAO
{
	@Override
	public String getClassName() {
		return FriendListDAO.class.getName();
	}
	
	public abstract FriendList load(final Player player);
	public abstract boolean addFriends(final Player player, final Player friend);
	public abstract boolean delFriends(final int playerOid, final int friendOid);
	public abstract void setFriendNote(final int playerId, final int friendId, final String notice);
}