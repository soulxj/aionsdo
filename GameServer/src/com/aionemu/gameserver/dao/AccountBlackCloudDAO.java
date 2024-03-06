package com.aionemu.gameserver.dao;


import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.gameobjects.blackcloud.BlackcloudItem;
import com.aionemu.gameserver.model.gameobjects.blackcloud.BlackcloudLetter;

import java.util.List;
import java.util.Map;

public abstract class AccountBlackCloudDAO implements IDFactoryAwareDAO {

    public abstract Map<Integer, BlackcloudLetter> loadAccountBlackcloud(Account account);
    public abstract boolean addBlackcloud(Account account, BlackcloudLetter letter);
    public abstract boolean updateBlackcloud(int id);
    public abstract void deleteBlackcloud(int id);

    public abstract List<BlackcloudItem> loadBlackcloudItem(BlackcloudLetter letter);
    public abstract boolean addBlackcloudItem(BlackcloudItem letter);
    public abstract void deleteBlackcloudItem(int id);


    @Override
    public final String getClassName() {
        return AccountBlackCloudDAO.class.getName();
    }
}
