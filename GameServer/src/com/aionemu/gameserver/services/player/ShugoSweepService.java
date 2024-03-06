package com.aionemu.gameserver.services.player;

import com.aionemu.commons.services.CronService;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.player.PlayerSweep;
import com.aionemu.gameserver.model.templates.shugosweep.ShugoSweepReward;
import com.aionemu.gameserver.network.aion.serverpackets.S_BOARD_BUSINESS_MODEL;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShugoSweepService {

    private static final Logger log = LoggerFactory.getLogger(ShugoSweepService.class);

    public void init() {
        String sweepDaily = "0 0 9 ? * * *";
        CronService.getInstance().schedule(new Runnable() {
            public void run() {
                updateFreeDice();
                log.info("Shugo Sweep Daily Dice");
            }
        }, sweepDaily);
    }

    private void onLogin() {
        //load dao
    }

    private void updateFreeDice() {
        //list of all player dao and update dice if player is online reload sweep data
    }

    public void onOpenBoard(Player player) {
        PlayerSweep playerSweep = getPlayerSweep(player);
        PacketSendUtility.sendPacket(player, new S_BOARD_BUSINESS_MODEL(playerSweep));
    }

    public void onLaunchDice(Player player) {
        int move = Rnd.get(1, 6);
        int dice = getPlayerSweep(player).getFreeDice();

        PlayerSweep playerSweep = getPlayerSweep(player);

        playerSweep.setFreeDice(dice - 1);
        playerSweep.setRoll(move);

        PacketSendUtility.sendPacket(player, new S_BOARD_BUSINESS_MODEL(playerSweep.getFreeDice(), move));
    }

    public void onAcceptMove(Player player) {
        PlayerSweep playerSweep = getPlayerSweep(player);
        int move = playerSweep.getRoll();

        playerSweep.setStep(playerSweep.getStep() + move);

        int diceRewarded = (1 << playerSweep.getStep());
        playerSweep.setLastDiceReward(playerSweep.getLastDiceReward() + diceRewarded);


        int lastDiceSlot = playerSweep.getStep();

        if (lastDiceSlot > 30) {
            lastDiceSlot -= 30;
            playerSweep.setStep(lastDiceSlot);
        }
        if(!playerSweep.isCellRewarded(playerSweep.getStep())){
            playerSweep.getRewardCell().add(playerSweep.getStep());
            //reward
            rewardPlayer(player, playerSweep.getBoardId(), playerSweep.getStep());
        }
        playerSweep.setRoll(0);
        PacketSendUtility.sendPacket(player, new S_BOARD_BUSINESS_MODEL(playerSweep.getLastDiceReward(), playerSweep.getStep(), move));
    }

    public void rewardPlayer(Player player, int boardid, int step) {
        ShugoSweepReward reward = getRewardForBoard(boardid, step);
        ItemService.addItem(player, reward.getItemId(), reward.getCount());
    }

    public PlayerCommonData getCommonData(Player player) {
        return player.getCommonData();
    }

    public PlayerSweep getPlayerSweep(Player player) {
        if(player.getPlayerShugoSweep() == null) {
            //int boardId = Rnd.get(1, 22);
            int boardId = 4;
            PlayerSweep ps = new PlayerSweep(0, 10, boardId, new FastList<Integer>());
            player.setPlayerShugoSweep(ps);
        }
        return player.getPlayerShugoSweep();
    }

    public static ShugoSweepReward getRewardForBoard(int boardId, int step) {
        return DataManager.SHUGO_SWEEP_REWARD_DATA.getRewardBoard(boardId, step);
    }

    public static final ShugoSweepService getInstance() {
        return SingletonHolder.instance;
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {
        protected static final ShugoSweepService instance = new ShugoSweepService();
    }
}
