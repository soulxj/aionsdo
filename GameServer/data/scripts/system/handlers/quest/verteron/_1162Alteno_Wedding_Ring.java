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
package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/****/
/** Author Rinzler (Encom)
/****/

public class _1162Alteno_Wedding_Ring extends QuestHandler
{
	private final static int questId = 1162;
	
	public _1162Alteno_Wedding_Ring() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(203095).addOnQuestStart(questId);
		qe.registerQuestNpc(203095).addOnTalkEvent(questId);
		qe.registerQuestNpc(700005).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 203095) {
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
			if (targetId == 700005) {
                switch (env.getDialog()) {
                    case USE_OBJECT: {
						if (var == 0) {
							return sendQuestDialog(env, 3739);
						}
					} case STEP_TO_1: {
						giveQuestItem(env, 182200563, 1);
						changeQuestStep(env, 0, 1, false);
						return closeDialogWindow(env);
					}
                }
            } if (targetId == 203095) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 1) {
							return sendQuestDialog(env, 2034);
						}
					} case CHECK_COLLECTED_ITEMS: {
						if (var == 1) {
							long altenosRing = player.getInventory().getItemCountByItemId(182200563);
							if (altenosRing == 0) {
								if (env.getDialogId() == 34) {
									return sendQuestDialog(env, 2375);
								}
							}
						}
					} case STEP_TO_2: {
						if (var == 1) {
							long altenosRing = player.getInventory().getItemCountByItemId(182200563);
							if (altenosRing == 1) {
								qs.setQuestVar(1);
						        qs.setStatus(QuestStatus.REWARD);
						        updateQuestStatus(env);
								removeQuestItem(env, 182200563, 1);
								return closeDialogWindow(env);
							} else {
								return sendQuestDialog(env, 2375);
							}
						}
					}
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203095) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}