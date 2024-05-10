/*
 * This file is part of Encom. **ENCOM FUCK OTHER SVN**
 *
 *  Encom is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Encom is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with Encom.  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.telos;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/****/
/** Author Rinzler (Encom)
/****/

public class QUEST_Q10101 extends QuestHandler
{
	private final static int questId = 10101;
	private final static int[] npcs = {701413, 800649, 800653};
	
	public QUEST_Q10101() {
		super(questId);
	}
	
	@Override
	public void register() {
		for (int npc: npcs) {
			qe.registerQuestNpc(npc).addOnTalkEvent(questId);
		}
		qe.registerOnLevelUp(questId);
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerOnEquipItem(102400002, questId); //í‹°ì•„ë§ˆíŠ¸ í›ˆë ¨ë³‘ì�˜ ì‚¬ìŠ¬ê²€.
	}
	
	@Override
	public boolean onZoneMissionEndEvent(QuestEnv env) {
		return defaultOnZoneMissionEndEvent(env);
	}
	
	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (player.getLevel() >= 2 && player.getLevel() <= 55 && (qs == null || qs.getStatus() == QuestStatus.NONE)) {
			return QuestService.startQuest(env);
		}
		return false;
	}
	
	@Override
	public boolean onEquipItemEvent(final QuestEnv env, int itemId) {
		changeQuestStep(env, 1, 2, false);
		return closeDialogWindow(env);
	}
	
	@Override
    public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();

		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (targetId == 800653) {
                switch (env.getDialog()) {
                    case START_DIALOG: {
						if (var == 0) {
							return sendQuestDialog(env, 1011);
						} else if (var == 2) {
							return sendQuestDialog(env, 1353);
						} else if (var == 3) {
							return sendQuestDialog(env, 1694);
						}
					} case STEP_TO_1: {
						changeQuestStep(env, 0, 1, false);
						PacketSendUtility.sendWhiteMessage(player, "Unequip and equip your weapon!!!");
						return closeDialogWindow(env);
					} case STEP_TO_2: {
						changeQuestStep(env, 2, 3, false);
						return closeDialogWindow(env);
					} case STEP_TO_3: {
						if (player.getInventory().getItemCountByItemId(182209901) < 3 && player.getInventory().getItemCountByItemId(182209902) < 3) {
							return closeDialogWindow(env);
						}
						qs.setQuestVar(4);
                        qs.setStatus(QuestStatus.REWARD);
						updateQuestStatus(env);
						PacketSendUtility.sendWhiteMessage(player, "Please do not use the skills books now, please wait for the next mission to learn them!!!");
						return closeDialogWindow(env);
					}
                }
            } if (targetId == 701413) {
                switch (env.getDialog()) {
                    case USE_OBJECT: {
						if (var == 3) {
							return closeDialogWindow(env);
						}
					}
                }
            }
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 800653) {
				return sendQuestSelectionDialog(env);
			}
            if (targetId == 800649) {
                if (env.getDialog() == QuestDialog.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
				} else if (env.getDialog() == QuestDialog.SELECT_REWARD) {
					if (player.getInventory().getItemsByItemId(182209901) != null && player.getInventory().getItemsByItemId(182209902) != null) {
						removeQuestItem(env, 182209901, 3);
						removeQuestItem(env, 182209902, 5);
					}
					return sendQuestDialog(env, 5);
				} else {
					if (player.getInventory().getItemsByItemId(182209901) != null && player.getInventory().getItemsByItemId(182209902) != null) {
						removeQuestItem(env, 182209901, 3);
						removeQuestItem(env, 182209902, 5);
					}
					return sendQuestEndDialog(env);
				}
			}
		}
        return false;
    }
}