/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.GameServer;
import com.aionemu.gameserver.configs.main.CacheConfig;
import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.dao.*;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.account.*;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.player.PlayerSettings;
import com.aionemu.gameserver.model.items.storage.PlayerStorage;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.model.items.storage.StorageType;
import com.aionemu.gameserver.model.team.legion.LegionMember;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.services.player.PlayerService;
import com.aionemu.gameserver.utils.collections.cachemap.CacheMap;
import com.aionemu.gameserver.utils.collections.cachemap.CacheMapFactory;
import com.aionemu.gameserver.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * This class is a front-end for daos and it's responsibility is to retrieve the Account objects
 *
 * @author Luno
 * @modified cura
 */
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    private static CacheMap<Integer, Account> accountsMap = CacheMapFactory.createSoftCacheMap("Account", "account");

    /**
     * Returns {@link Account} object that has given id.
     *
     * @param accountId
     * @param accountTime
     * @param accountName
     * @param accessLevel
     * @param membership
     * @return Account
     */
    public static Account getAccount(int accountId, String accountName, AccountTime accountTime, byte accessLevel, byte membership, long toll, int sielenergy_type) {
        log.debug("[AS] request for account: " + accountId);

        Account account = accountsMap.get(accountId);
        if (account == null) {
            account = loadAccount(accountId);
            if (CacheConfig.CACHE_ACCOUNTS)
                accountsMap.put(accountId, account);
        }
        account.setName(accountName);
        account.setAccountTime(accountTime);
        account.setAccessLevel(accessLevel);
        account.setMembership(membership);
        account.setToll(toll);
        account.setAccountSielEnergy(new AccountSielEnergy(SielEnergyType.getSielTypeById(sielenergy_type), null, null, 0));
        removeDeletedCharacters(account);
        if (account.isEmpty()) {
            removeAccountWH(accountId);
        }

        return account;
    }

    /**
     * Removes from db characters that should be deleted (their deletion time has passed).
     *
     * @param account
     */
    private static void removeDeletedCharacters(Account account) {
        /* Removes chars that should be removed */
        Iterator<PlayerAccountData> it = account.iterator();
        while (it.hasNext()) {
            PlayerAccountData pad = it.next();
            Race race = pad.getPlayerCommonData().getRace();
            int deletionTime = pad.getDeletionTimeInSeconds() * 1000;
            if (deletionTime != 0 && deletionTime <= System.currentTimeMillis()) {
                it.remove();
                account.decrementCountOf(race);
                PlayerService.deletePlayerFromDB(pad.getPlayerCommonData().getPlayerObjId());
                if (GSConfig.ENABLE_RATIO_LIMITATION && pad.getPlayerCommonData().getLevel() >= GSConfig.RATIO_MIN_REQUIRED_LEVEL) {
                    if (account.getNumberOf(race) == 0) {
                        GameServer.updateRatio(pad.getPlayerCommonData().getRace(), -1);
                    }
                }
            }
        }
    }

    private static void removeAccountWH(int accountId) {
        DAOManager.getDAO(InventoryDAO.class).deleteAccountWH(accountId);
    }

    /**
     * Loads account data and returns.
     *
     * @param accountId
     * @return
     */
    public static Account loadAccount(int accountId) {
        Account account = new Account(accountId);

        PlayerDAO playerDAO = DAOManager.getDAO(PlayerDAO.class);
        PlayerAppearanceDAO appereanceDAO = DAOManager.getDAO(PlayerAppearanceDAO.class);

        List<Integer> playerIdList = playerDAO.getPlayerOidsOnAccount(accountId);

        for (int playerId : playerIdList) {
            PlayerCommonData playerCommonData = playerDAO.loadPlayerCommonData(playerId);
            CharacterBanInfo cbi = DAOManager.getDAO(PlayerPunishmentsDAO.class).getCharBanInfo(playerId);

            if (playerCommonData.isOnline()) {
                if (World.getInstance().findPlayer(playerId) == null) {
                    playerCommonData.setOnline(false);
                    log.warn(playerCommonData.getName() + " has online status, but I cant find it in World. Skip online status");
                }
            }
            PlayerAppearance appereance = appereanceDAO.load(playerId);
            PlayerSettings playerSettings = DAOManager.getDAO(PlayerSettingsDAO.class).loadSettings(playerId);
            LegionMember legionMember = DAOManager.getDAO(LegionMemberDAO.class).loadLegionMember(playerId);

            int unreadLetters = DAOManager.getDAO(MailDAO.class).unreadedMails(playerId);
            /**
             * Load only equipment and its stones to display on character selection screen
             */
            List<Item> equipment = DAOManager.getDAO(InventoryDAO.class).loadEquipment(playerId);

            PlayerAccountData acData = new PlayerAccountData(playerCommonData, cbi, appereance, equipment, legionMember, playerSettings, unreadLetters);
            playerDAO.setCreationDeletionTime(acData);

            account.addPlayerAccountData(acData);

            if (account.getAccountWarehouse() == null) {
                Storage accWarehouse = DAOManager.getDAO(InventoryDAO.class).loadStorage(playerId, StorageType.ACCOUNT_WAREHOUSE);
                ItemService.loadItemStones(accWarehouse.getItems());
                account.setAccountWarehouse(accWarehouse);
            }
        }

        // For new accounts - create empty account warehouse
        if (account.getAccountWarehouse() == null) {
            account.setAccountWarehouse(new PlayerStorage(StorageType.ACCOUNT_WAREHOUSE));
        }
        return account;
    }
}
