package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerEventWindow;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public abstract class PlayerEventsWindowDAO implements DAO {

    public abstract Map<Integer, PlayerEventWindow> load(Player accountId);

    public abstract void insert(int accountId, PlayerEventWindow eventWindow);
    public abstract boolean update(int accountId, PlayerEventWindow eventWindow);
    public abstract boolean delete();

    public final String getClassName() {
        return PlayerEventsWindowDAO.class.getName();
    }
}
