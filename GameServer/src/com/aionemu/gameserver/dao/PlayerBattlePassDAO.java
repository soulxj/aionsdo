package com.aionemu.gameserver.dao;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.battlePass.BattlePassQuest;
import com.aionemu.gameserver.model.gameobjects.player.battlePass.BattlePassReward;
import com.aionemu.gameserver.model.gameobjects.player.battlePass.BattlePassSeason;
import com.aionemu.gameserver.model.templates.battle_pass.BattleQuestType;

import java.util.Map;

public abstract class PlayerBattlePassDAO implements IDFactoryAwareDAO {

    @Override
    public String getClassName() {
        return PlayerBattlePassDAO.class.getName();
    }

    public abstract boolean storeSeason(Player player, BattlePassSeason season);
    public abstract boolean updateSeason(Player player, BattlePassSeason season);
    public abstract boolean storeReward(Player player, BattlePassReward reward, int seasonId);
    public abstract boolean updateReward(Player player, BattlePassReward reward);
    public abstract boolean storeQuest(Player player, BattlePassQuest quest);
    public abstract boolean updateQuest(Player player, BattlePassQuest quest);
    public abstract Map<Integer, BattlePassSeason> loadPlayerBattlePass(Player player);
    public abstract Map<Integer, BattlePassReward> loadPlayerBattleReward(int season, Player player);
    public abstract  Map<Integer, BattlePassQuest> loadPlayerBattleQuest(Player player);

    public abstract void deleteQuest(BattleQuestType type);
    public abstract void deleteSeason();
    public abstract void deleteReward();
}
