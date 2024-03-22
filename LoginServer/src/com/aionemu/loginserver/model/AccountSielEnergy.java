package com.aionemu.loginserver.model;

import com.aionemu.loginserver.configs.Config;
import com.aionemu.loginserver.dao.AccountSielEnergyDAO;
import com.aionemu.loginserver.network.gameserver.serverpackets.SM_ACCOUNT_SIELENERY_NOTITY;
import com.aionemu.loginserver.taskmanager.IExpirable;
import com.aionemu.loginserver.utils.ThreadPoolManager;
import com.aionemu.loginserver.utils.Util;

import java.sql.Timestamp;

/**
 * @author soulxj
 */
public class AccountSielEnergy implements IExpirable {

    private final Account account;
    private SielEnergyType type;
    private Timestamp initTime;
    private Timestamp endTime;
    private long remainSecond;

    private long chargeTime;

    public AccountSielEnergy(Account account, SielEnergyType type, Timestamp initTime, Timestamp endTime, long remainSecond) {
        this.account = account;
        this.type = type;
        this.initTime = initTime;
        this.endTime = endTime;
        this.remainSecond = remainSecond;
    }

    public void setRemainSecond(long remainSecond) {
        this.remainSecond = remainSecond;
    }

    public Account getAccount() {
        return account;
    }

    public SielEnergyType getType() {
        return type;
    }

    public Timestamp getInitTime() {
        return initTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public long getRemainSecond() {
        return remainSecond;
    }


    public void setChargeTime(long chargeTime) {
        this.chargeTime = chargeTime;
    }

    public long getChargeTime() {
        return chargeTime;
    }

    @Override
    public long getExpireTime() {
        return 0;
    }

    @Override
    public void expireEnd(Account account) {

    }

    @Override
    public boolean canExpireNow() {
        return false;
    }

    @Override
    public void expireMessage(Account account, long time) {

    }

    public void onBeat(long now) {
        final long hour9 = Util.getCurrentDay().getTime();

        switch (type) {
            case NONE:
                if (now >= hour9 && initTime.getTime() < hour9) {
                    //需要重置
                    initTime = new Timestamp(now);
                    remainSecond = Config.TRIAL_SECONDS;
                    type = SielEnergyType.TRIAL;
                    ThreadPoolManager.getInstance().executeLongRunning(() -> {
                        AccountSielEnergyDAO.replaceInsert(this);
                    });
                    account.getGsConnection().sendPacket(new SM_ACCOUNT_SIELENERY_NOTITY(true, this));
                }
                break;
            case TRIAL:
                if (remainSecond > 0) {
                    long use = now - chargeTime;
                    long remain = remainSecond * 1000 - use;
                    if (remain < 0) {
                        remainSecond = 0;
                        type = SielEnergyType.NONE;
                        ThreadPoolManager.getInstance().executeLongRunning(() -> {
                            AccountSielEnergyDAO.replaceInsert(this);
                        });
                        account.getGsConnection().sendPacket(new SM_ACCOUNT_SIELENERY_NOTITY(true, this));
                    }
                }
                break;
            case MEMBERSHIP:
                if (now > endTime.getTime()) {
                    remainSecond = 0;
                    type = SielEnergyType.NONE;
                    endTime = null;
                    ThreadPoolManager.getInstance().executeLongRunning(() -> {
                        AccountSielEnergyDAO.replaceInsert(this);
                    });
                    account.getGsConnection().sendPacket(new SM_ACCOUNT_SIELENERY_NOTITY(true, this));
                }
                break;
        }
    }
}


