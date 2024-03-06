package com.aionemu.gameserver.dao;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.WardrobeEntry;

public abstract class PlayerWardrobeDAO implements IDFactoryAwareDAO {

    public abstract void loadWardrobe(Player player);
    public abstract boolean addItem(Player player, WardrobeEntry entry);
    public abstract boolean updateItem(Player player, WardrobeEntry entry);
    public abstract void deleteItem(Player player, WardrobeEntry entry);

    @Override
    public final String getClassName() {
        return PlayerWardrobeDAO.class.getName();
    }
}
