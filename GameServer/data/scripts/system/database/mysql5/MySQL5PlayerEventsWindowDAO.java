package mysql5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aionemu.gameserver.model.gameobjects.player.PlayerEventWindow;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.gameserver.dao.PlayerEventsWindowDAO;

import com.aionemu.gameserver.model.gameobjects.player.Player;

public class MySQL5PlayerEventsWindowDAO extends PlayerEventsWindowDAO {

    private static final Logger log = LoggerFactory.getLogger(MySQL5PlayerEventsWindowDAO.class);

    public static final String DELETE_QUERY = "DELETE FROM `player_events_window`";
    public static final String INSERT_QUERY = "INSERT INTO `player_events_window` (`account_id`, `event_id`) VALUES (?,?)";
    public static final String SELECT_QUERY = "SELECT * FROM `player_events_window` WHERE `account_id`=?";
    public static final String UPDATE_QUERY = "UPDATE `player_events_window` set elapsed=?, recived_count=? WHERE `account_id`=? AND `event_id`=?";

    @Override
    public Map<Integer, PlayerEventWindow> load(Player player) {
        Map<Integer, PlayerEventWindow> eventWindow = new FastMap<Integer, PlayerEventWindow>();
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(SELECT_QUERY);
            stmt.setInt(1, player.getPlayerAccount().getId());
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                int id = rset.getInt("event_id");
                int elapsed = rset.getInt("elapsed");
                int receivedCount = rset.getInt("recived_count");
                eventWindow.put(id, new PlayerEventWindow(id, elapsed, receivedCount));
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {
            log.error("Could not restore Event Window account: " +player.getPlayerAccount().getId() + " from DB: " + e.getMessage(), e);
        } finally {
            DatabaseFactory.close(con);
        }
        return eventWindow;
    }

    @Override
    public boolean update(int accountId, PlayerEventWindow eventWindow) {
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(UPDATE_QUERY);
            stmt.setInt(1, eventWindow.getElapsed());
            stmt.setInt(2, eventWindow.getReceivedCount());
            stmt.setInt(3, accountId);
            stmt.setInt(4, eventWindow.getId());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            log.error("Could not store event window for account " + accountId + " from DB: " + e.getMessage(), e);
            return false;
        } finally {
            DatabaseFactory.close(con);
        }
        return true;
    }

    @Override
    public void insert(int accountId, PlayerEventWindow eventWindow) {
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(INSERT_QUERY);
            stmt.setInt(1, accountId);
            stmt.setInt(2, eventWindow.getId());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            log.error("Can't insert into events window: " + e.getMessage());
        }
        finally {
            DatabaseFactory.close(con);
        }
    }

    @Override
    public boolean delete() {
        return DB.insertUpdate(DELETE_QUERY, new IUStH() {
            @Override
            public void handleInsertUpdate(PreparedStatement ps) throws SQLException {
                ps.execute();
                ps.close();
            }
        });
    }


    /**
     * {@inheritDoc}
     */
    public boolean supports(String databaseName, int majorVersion, int minorVersion) {
        return MySQL5DAOUtils.supports(databaseName, majorVersion, minorVersion);
    }
}