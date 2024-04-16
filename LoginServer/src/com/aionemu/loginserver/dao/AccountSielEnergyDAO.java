package com.aionemu.loginserver.dao;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.loginserver.model.Account;
import com.aionemu.loginserver.model.AccountSielEnergy;
import com.aionemu.loginserver.model.SielEnergyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author soulxj
 */
public class AccountSielEnergyDAO {

    private static final Logger log = LoggerFactory.getLogger(AccountSielEnergyDAO.class);

    public static final String INSERT_SIEL = "REPLACE INTO accounts_siel (account_id, id, init, end,remain) VALUES (?, ?, ?, ?,?)";
    public static final String LOAD_SIEL = "SELECT * FROM `accounts_siel` WHERE `account_id`=?";

    public static void load(final Account account) {
        DB.select(LOAD_SIEL, new ParamReadStH() {
            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setInt(1, account.getId());
            }

            @Override
            public void handleRead(ResultSet rset) throws SQLException {
                while (rset.next()) {
                    AccountSielEnergy accountSielEnergy = new AccountSielEnergy(SielEnergyType.getSielTypeById(rset.getInt("id")), rset.getTimestamp("init"), rset.getTimestamp("end"), rset.getLong("remain"));
                    account.setAccountSielEnergy(accountSielEnergy);
                }
            }
        });
    }

    public static boolean replaceInsert(final int accId,final AccountSielEnergy accountSielEnergy) {
        Connection con = null;
        try {
            con = DatabaseFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(INSERT_SIEL);
            stmt.setInt(1, accId);
            stmt.setInt(2, accountSielEnergy.getType().getId());
            stmt.setTimestamp(3, accountSielEnergy.getInitTime());
            stmt.setTimestamp(4, accountSielEnergy.getEndTime());
            stmt.setLong(5, accountSielEnergy.getRemainSecond());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            log.error("Store Siel Energy", e);
            return false;
        } finally {
            DatabaseFactory.close(con);
        }
    }

}
