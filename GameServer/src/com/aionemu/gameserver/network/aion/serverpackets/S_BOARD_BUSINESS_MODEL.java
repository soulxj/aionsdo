package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.EventsConfig;
import com.aionemu.gameserver.model.gameobjects.player.PlayerSweep;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class S_BOARD_BUSINESS_MODEL extends AionServerPacket {


    private int action;
    private int diceLeft;
    private int roll;
    private long rewardShift;
    private int newStep;

    private PlayerSweep playerSweep;

    public S_BOARD_BUSINESS_MODEL(PlayerSweep playerSweep){
        this.action = 101;
        this.playerSweep = playerSweep;
    }

    public S_BOARD_BUSINESS_MODEL(int diceLeft,int roll){
        this.action = 102;
        this.roll = roll;
        this.diceLeft = diceLeft;
    }

    public S_BOARD_BUSINESS_MODEL(long rewardShift, int newStep, int roll){
        this.action = 103;
        this.rewardShift = rewardShift;
        this.newStep = newStep;
        this.roll = roll;
    }

    @Override
    protected void writeImpl(AionConnection con) {
        writeC(this.action);
        switch (this.action) {
            case 101: //open the board
                writeD(EventsConfig.EVENT_LUCK_DICE_SEASON); //seasonId
                writeD(0);//maybe reset count (max 15)
                writeD(this.playerSweep.getBoardId());
                writeD(this.playerSweep.getFreeDice());
                writeQ(this.playerSweep.getLastDiceReward());
                writeD(this.playerSweep.getStep());
                writeH(0);
                break;
            case 102://roll
                writeD(this.diceLeft);
                writeC(this.roll);
                break;
            case 103://accept roll
                writeQ(this.rewardShift);
                writeD(this.newStep);
                writeH(this.roll);
                break;
        }
    }
}
