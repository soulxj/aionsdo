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
package quest.poeta;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.services.QuestService;

/****/
/** Author Rinzler (Encom)
/****/

public class _1007A_Ceremony_In_Sanctum extends QuestHandler
{
	private final static int questId = 1007;
	
	public _1007A_Ceremony_In_Sanctum() {
		super(questId);
	}
	
	@Override
	public void register() {
		if (CustomConfig.ENABLE_SIMPLE_2NDCLASS) {
			return;
		}
		qe.registerOnLevelUp(questId);
		qe.registerQuestNpc(790001).addOnTalkEvent(questId);
		qe.registerQuestNpc(203725).addOnTalkEvent(questId);
		qe.registerQuestNpc(203752).addOnTalkEvent(questId);
		qe.registerQuestNpc(203758).addOnTalkEvent(questId);
		qe.registerQuestNpc(203759).addOnTalkEvent(questId);
		qe.registerQuestNpc(203760).addOnTalkEvent(questId);
		qe.registerQuestNpc(203761).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env);
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int var = qs.getQuestVarById(0);
		int targetId = env.getTargetId();
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (targetId == 790001) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 0) {
							return sendQuestDialog(env, 1011);
						}
					} case STEP_TO_1: {
						changeQuestStep(env, 0, 1, false);
						TeleportService2.teleportTo(env.getPlayer(), 110010000, 1313.0000f, 1512.0000f, 568.0000f, (byte) 0);
						return closeDialogWindow(env);
					}
				}
			} if (targetId == 203725) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 1) {
							return sendQuestDialog(env, 1352);
						}
					} case SELECT_ACTION_1353: {
						return playQuestMovie(env, 92);
					} case STEP_TO_2: {
						changeQuestStep(env, 1, 2, false);
						return closeDialogWindow(env);
					}
				}
			} if (targetId == 203752) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 2) {
							return sendQuestDialog(env, 1693);
						}
					} case SELECT_ACTION_1694: {
						return playQuestMovie(env, 91);
					} case STEP_TO_3: {
						if (var == 2) {
							PlayerClass playerClass = PlayerClass.getStartingClassFor(player.getCommonData().getPlayerClass());
							switch (playerClass) {
								case WARRIOR:
									qs.setQuestVar(10);
								break;
								case SCOUT:
									qs.setQuestVar(20);
								break;
								case MAGE:
									qs.setQuestVar(30);
								break;
								case PRIEST:
									qs.setQuestVar(40);
								break;
							}
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							return sendQuestSelectionDialog(env);
						}
					}
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203758 && var == 10) {
				switch (env.getDialogId()) {
					case -1:
						return sendQuestDialog(env, 2034);
					case 1009:
						return sendQuestDialog(env, 5);
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 18:
					if (QuestService.finishQuest(env, 0)) {
						player.getCommonData().setLevel(10);
						return sendQuestSelectionDialog(env);
					}
				}
			} else if (targetId == 203759 && var == 20) {
				switch (env.getDialogId()) {
					case -1:
						return sendQuestDialog(env, 2375);
					case 1009:
						return sendQuestDialog(env, 6);
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 18:
					if (QuestService.finishQuest(env, 0)) {
						player.getCommonData().setLevel(10);
						return sendQuestSelectionDialog(env);
					}
				}
			} else if (targetId == 203760 && var == 30) {
				switch (env.getDialogId()) {
					case -1:
						return sendQuestDialog(env, 2716);
					case 1009:
						return sendQuestDialog(env, 7);
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 18:
					if (QuestService.finishQuest(env, 0)) {
						player.getCommonData().setLevel(10);
						return sendQuestSelectionDialog(env);
					}
				}
			} else if (targetId == 203761 && var == 40) {
				switch (env.getDialogId()) {
					case -1:
						return sendQuestDialog(env, 3057);
					case 1009:
						return sendQuestDialog(env, 8);
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 18:
					if (QuestService.finishQuest(env, 0)) {
						player.getCommonData().setLevel(10);
						return sendQuestSelectionDialog(env);
					}
				}
			}
		}
		return false;
	}
}