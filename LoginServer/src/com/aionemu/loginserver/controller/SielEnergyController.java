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
        long remainTimeInSeconds = Config.TRIAL_SECONDS;
        AccountSielEnergy sielEnergy = new AccountSielEnergy(account, SielEnergyType.TRIAL, new Timestamp(initTime), null, remainTimeInSeconds);
        account.setAccountSielEnergy(sielEnergy);
        return AccountSielEnergyDAO.replaceInsert(sielEnergy);
    }

}
