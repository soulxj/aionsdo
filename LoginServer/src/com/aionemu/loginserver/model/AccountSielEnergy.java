package com.aionemu.loginserver.model;

import com.aionemu.loginserver.configs.Config;
import com.aionemu.loginserver.dao.AccountSielEnergyDAO;
import com.aionemu.loginserver.network.gameserver.serverpackets.SM_ACCOUNT_SIELENERY_UPDATE;
import com.aionemu.loginserver.taskmanager.IExpirable;
import com.aionemu.loginserver.utils.ThreadPoolManager;
import com.aionemu.loginserver.utils.Util;

import java.sql.Timestamp;

/**
 * @author soulxj
 */
public class AccountSielEnergy implements IExpirable {


    private SielEnergyType type;
    private Timestamp initTime;
    private Timestamp endTime;
    private long remainSecond;
    private long chargeTime;

    public AccountSielEnergy(SielEnergyType type, Timestamp initTime, Timestamp endTime, long remainSecond) {
        this.type = type;
        this.initTime = initTime;
        this.endTime = endTime;
        this.remainSecond = remainSecond;
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

    /**
     * 如乱如何都保存数据
     */
    public void onUpdate(final Account account) {
        final long hour9 = Util.getCurrentDay().getTime();
        final long now = System.currentTimeMillis();

        switch (type) {
            case NONE:
                if (now >= hour9 && initTime.getTime() < hour9) {
                    //需要重置
                    initTime = new Timestamp(now);
                    remainSecond = Config.TRIAL_SECONDS;
                    type = SielEnergyType.TRIAL;
                }
                break;
            case TRIAL:
                if (remainSecond > 0) {
                    long use = now - chargeTime;
                    long remain = remainSecond * 1000 - use;
                    remainSecond = remain/1000;
                    if (remainSecond < 0) {
                        remainSecond = 0;
                        type = SielEnergyType.NONE;
                    }
                }
                break;
            case MEMBERSHIP:
                if (now > endTime.getTime()) {
                    remainSecond = 0;
                    type = SielEnergyType.NONE;
                    endTime = null;
                }
                break;
        }

        ThreadPoolManager.getInstance().executeLongRunning(() -> AccountSielEnergyDAO.replaceInsert(account.getId(), this));
    }

    /**
     * 有变化才会保存加通知
     *
     * @param now
     */
    public void onBeat(long now, final Account account) {
        final long hour9 = Util.getCurrentDay().getTime();

        switch (type) {
            case NONE:
                if (now >= hour9 && initTime.getTime() < hour9) {
                    //需要重置
                    initTime = new Timestamp(now);
                    remainSecond = Config.TRIAL_SECONDS;
                    type = SielEnergyType.TRIAL;
                    ThreadPoolManager.getInstance().executeLongRunning(() -> AccountSielEnergyDAO.replaceInsert(account.getId(), this));
                    account.getGsConnection().sendPacket(new SM_ACCOUNT_SIELENERY_UPDATE(account.getId(), this));
                }
                break;
            case TRIAL:
                if (remainSecond > 0) {
                    long use = now - chargeTime;
                    long remain = remainSecond * 1000 - use;
                    if (remain < 0) {
                        remainSecond = 0;
                        type = SielEnergyType.NONE;
                        ThreadPoolManager.getInstance().executeLongRunning(() -> AccountSielEnergyDAO.replaceInsert(account.getId(), this));
                        account.getGsConnection().sendPacket(new SM_ACCOUNT_SIELENERY_UPDATE(account.getId(), this));
                    }
                }
                break;
            case MEMBERSHIP:
                if (now > endTime.getTime()) {
                    remainSecond = 0;
                    type = SielEnergyType.NONE;
                    endTime = null;
                    ThreadPoolManager.getInstance().executeLongRunning(() -> AccountSielEnergyDAO.replaceInsert(account.getId(), this));
                    account.getGsConnection().sendPacket(new SM_ACCOUNT_SIELENERY_UPDATE(account.getId(), this));
                }
                break;
        }
    }
}


