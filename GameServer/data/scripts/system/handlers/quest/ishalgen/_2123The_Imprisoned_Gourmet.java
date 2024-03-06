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
package quest.ishalgen;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/****/
/** Author Rinzler (Encom)
/****/

public class _2123The_Imprisoned_Gourmet extends QuestHandler
{
	private final static int questId = 2123;
	
	public _2123The_Imprisoned_Gourmet() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(203550).addOnQuestStart(questId);
		qe.registerQuestNpc(203550).addOnTalkEvent(questId);
		qe.registerQuestNpc(700128).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 203550) {
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
			long ginsengRoot = player.getInventory().getItemCountByItemId(182203121);
			long methuEgg = player.getInventory().getItemCountByItemId(182203122);
			long sparkieEggs = player.getInventory().getItemCountByItemId(182203123);
			if (targetId == 203550) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 0) {
							return sendQuestDialog(env, 1352);
						}
					} case STEP_TO_1: {
						if (ginsengRoot == 1) {
							qs.setQuestVar(5);
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							removeQuestItem(env, 182203121, 1);
							return sendQuestDialog(env, 5);
						} else {
							return sendQuestDialog(env, 1693);
						}
					} case STEP_TO_2: {
						if (methuEgg == 1) {
							qs.setQuestVar(6);
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							removeQuestItem(env, 182203122, 1);
							return sendQuestDialog(env, 6);
						} else {
							return sendQuestDialog(env, 1693);
						}
					} case STEP_TO_3: {
						if (sparkieEggs == 1) {
							qs.setQuestVar(7);
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							removeQuestItem(env, 182203123, 1);
							return sendQuestDialog(env, 7);
						} else {
							return sendQuestDialog(env, 1693);
						}
					}
				}
			} if (targetId == 700128) {
                switch (env.getDialog()) {
                    case USE_OBJECT: {
                        return closeDialogWindow(env);
					}
                }
            }
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203550) {
				giveQuestItem(env, 123000864, 1);
				giveQuestItem(env, 125001757, 1);
				return sendQuestEndDialog(env, qs.getQuestVarById(0) - 5);
			}
		}
		return false;
	}
}