package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class AccountSielEnergyDAO implements DAO {

    public abstract void load(Player player);
    public abstract boolean add(Player player);
    public abstract void remove();


    @Override
    public String getClassName() {
        return AccountSielEnergyDAO.class.getName();
    }
}
