package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * Created by wanke on 03/03/2017.
 */

public abstract class EventItemsDAO implements DAO
{
    @Override
    public final String getClassName() {
        return EventItemsDAO.class.getName();
    }
    public abstract void loadItems(Player player);
    public abstract void storeItems(Player player);
    public abstract void deleteItems(final int itemId);
}