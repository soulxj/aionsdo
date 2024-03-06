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
package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/****/
/** Author Rinzler (Encom)
/****/

public class _1463Message_To_A_Spy extends QuestHandler
{
	private int choice = 0;
	private final static int questId = 1463;
	
	public _1463Message_To_A_Spy() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(203940).addOnQuestStart(questId);
		qe.registerQuestNpc(203940).addOnTalkEvent(questId);
		qe.registerQuestNpc(203903).addOnTalkEvent(questId);
		qe.registerQuestNpc(204424).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 203940) {
				switch (env.getDialog()) {
                    case START_DIALOG: {
						return sendQuestDialog(env, 1011);
					} case ASK_ACCEPTION: {
						return sendQuestDialog(env, 4);
					} case ACCEPT_QUEST: {
						return sendQuestStartDialog(env);
					} case REFUSE_QUEST: {
				        return closeDialogWindow(env);
					}
                }
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (targetId == 203903) {
                switch (env.getDialog()) {
                    case START_DIALOG: {
						if (var == 0) {
							return sendQuestDialog(env, 1352);
						} else if (var == 2) {
							return sendQuestDialog(env, 2375);
						}
					} case SELECT_ACTION_1353: {
						choice = 1;
						return sendQuestDialog(env, 1353);
					} case SELECT_ACTION_1438: {
						choice = 2;
						return sendQuestDialog(env, 1438);
					} case STEP_TO_1: {
						if (choice == 1) {
							QuestService.abandonQuest(player, questId);
							player.getController().updateNearbyQuests();
							return closeDialogWindow(env);
						} else if (choice == 2) {
							giveQuestItem(env, 182201382, 1);
							changeQuestStep(env, 0, 1, false);
							TeleportService2.teleportTo(env.getPlayer(), 220020000, 679.0000f, 2681.0000f, 317.0000f, (byte) 10);
							return closeDialogWindow(env);
						}
					} case SELECT_REWARD: {
						qs.setQuestVar(3);
						qs.setStatus(QuestStatus.REWARD);
						updateQuestStatus(env);
						removeQuestItem(env, 182201382, 1);
						removeQuestItem(env, 182201383, 1);
						return sendQuestEndDialog(env);
					}
                }
            } if (targetId == 204424) {
                switch (env.getDialog()) {
                    case START_DIALOG: {
						if (var == 1) {
							return sendQuestDialog(env, 1693);
						}
					} case STEP_TO_2: {
						giveQuestItem(env, 182201383, 1);
						changeQuestStep(env, 1, 2, false);
						TeleportService2.teleportTo(env.getPlayer(), 210020000, 269.0000f, 2791.0000f, 272.0000f, (byte) 36);
						return closeDialogWindow(env);
					}
                }
            }
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203903) {
				if (env.getDialog() == QuestDialog.SELECT_REWARD) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
}