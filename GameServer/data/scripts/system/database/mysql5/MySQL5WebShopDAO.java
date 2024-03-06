package mysql5;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.MySQL5DAOUtils;
import com.aionemu.gameserver.dao.WebShopDAO;
import com.aionemu.gameserver.model.templates.rewards.RewardEntryItem;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL5WebShopDAO extends WebShopDAO
{
    private static final Logger log = LoggerFactory.getLogger(MySQL5WebShopDAO.class);
    public static final String SELECT_QUERY = "SELECT * FROM `web_shop` WHERE `item_owner`=?";
	
    @Override
    public boolean supports(String arg0, int arg1, int arg2) {
        return MySQL5DAOUtils.supports(arg0, arg1, arg2);
    }
    @Override
    public FastList<RewardEntryItem> getAvailable(int accountId) {
        FastList<RewardEntryItem> list = FastList.newInstance();
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(SELECT_QUERY);
            stmt.setInt(1, accountId);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                int unique = rset.getInt("object_id");
                int item_id = rset.getInt("item_id");
                int count = rset.getInt("item_count");
                list.add(new RewardEntryItem(unique, item_id, count));
            }
            rset.close();
            stmt.close();
        } catch (Exception e) {
        } finally {
            DatabaseFactory.close(con);
        }
        return list;
    }

    @Override
    public void delete(int unique) {
        PreparedStatement statement = DB.prepareStatement("DELETE FROM web_shop WHERE object_id = ?");
        try {
            statement.setInt(1, unique);
        }
        catch (SQLException e) {
            log.error("Some crap, can't set int parameter to PreparedStatement", e);
        }
        DB.executeUpdateAndClose(statement);
    }
}