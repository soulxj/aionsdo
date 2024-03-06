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
package quest.inggison;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

/****/
/** Author Rinzler (Encom)
/****/

public class _11216How_Many_Draks_Does_It_Take_To_Map extends QuestHandler
{
	private final static int questId = 11216;
	
	public _11216How_Many_Draks_Does_It_Take_To_Map() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestItem(182206825, questId);
		qe.registerQuestNpc(799017).addOnTalkEvent(questId);
		qe.registerQuestNpc(700624).addOnTalkEvent(questId);
		qe.registerQuestNpc(700625).addOnTalkEvent(questId);
		qe.registerQuestNpc(700626).addOnTalkEvent(questId);
		qe.registerQuestNpc(700627).addOnTalkEvent(questId);
	}
	
	@Override
	public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			return HandlerResult.fromBoolean(sendQuestDialog(env, 4));
		}
		return HandlerResult.FAILED;
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		if (targetId == 0) {
			switch (env.getDialog()) {
				case ASK_ACCEPTION: {
					return sendQuestDialog(env, 4);
				} case ACCEPT_QUEST: {
					return sendQuestStartDialog(env);
				} case REFUSE_QUEST: {
					return closeDialogWindow(env);
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (targetId == 799017) {
			    switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 0) {
							return sendQuestDialog(env, 1011);
						} else if (var == 1) {
							return sendQuestDialog(env, 1352);
						}
                    } case CHECK_COLLECTED_ITEMS: {
						if (QuestService.collectItemCheck(env, true)) {
							qs.setQuestVar(2);
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							return sendQuestDialog(env, 10000);
						} else {
							return sendQuestDialog(env, 10001);
						}
					} case STEP_TO_1: {
						giveQuestItem(env, 182206826, 1);
                        changeQuestStep(env, 0, 1, false);
						return closeDialogWindow(env);
					} case FINISH_DIALOG: {
						return closeDialogWindow(env);
					}
				}
			} if (targetId == 700624) {
                switch (env.getDialog()) {
                    case USE_OBJECT: {
                        if (var == 1) {
							return closeDialogWindow(env);
                        }
					}
                }
            } if (targetId == 700625) {
                switch (env.getDialog()) {
                    case USE_OBJECT: {
                        if (var == 1) {
							return closeDialogWindow(env);
                        }
					}
                }
            } if (targetId == 700626) {
                switch (env.getDialog()) {
                    case USE_OBJECT: {
                        if (var == 1) {
							return closeDialogWindow(env);
                        }
					}
                }
            } if (targetId == 700627) {
                switch (env.getDialog()) {
                    case USE_OBJECT: {
                        if (var == 1) {
							return closeDialogWindow(env);
                        }
					}
                }
            }
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799017) {
                if (env.getDialog() == QuestDialog.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
				} else if (env.getDialog() == QuestDialog.SELECT_REWARD) {
					removeQuestItem(env, 182206825, 1);
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
}