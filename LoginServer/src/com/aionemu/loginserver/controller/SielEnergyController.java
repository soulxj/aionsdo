package com.aionemu.loginserver.controller;

import com.aionemu.loginserver.configs.Config;
import com.aionemu.loginserver.dao.AccountSielEnergyDAO;
import com.aionemu.loginserver.model.Account;
import com.aionemu.loginserver.model.AccountSielEnergy;
import com.aionemu.loginserver.model.SielEnergyType;

import java.sql.Timestamp;

/**
 * @author soulxj
 */
public class SielEnergyController {

    /**
     * 初始化账号类型
     *
     * @param account
     * @return
     */
    public static boolean onCreateAccount(Account account) {
        long initTime = System.currentTimeMillis();
        AccountSielEnergy sielEnergy = new AccountSielEnergy(SielEnergyType.TRIAL, new Timestamp(initTime), null, Config.TRIAL_SECONDS);
        account.setAccountSielEnergy(sielEnergy);
        return AccountSielEnergyDAO.replaceInsert(account.getId(), sielEnergy);
    }

}
