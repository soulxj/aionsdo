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
package quest.heiron;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/****/
/** Author Rinzler (Encom)
/****/

public class _1535The_Cold_Cold_Ground extends QuestHandler
{
	private final static int questId = 1535;
	
	public _1535The_Cold_Cold_Ground() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(204580).addOnQuestStart(questId);
		qe.registerQuestNpc(204580).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 204580) {
				switch (env.getDialog()) {
                    case START_DIALOG: {
						return sendQuestDialog(env, 4762);
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
			if (targetId == 204580) {
				boolean abexSkin = player.getInventory().getItemCountByItemId(182201818) > 4;
				boolean worgSkin = player.getInventory().getItemCountByItemId(182201819) > 2;
				boolean karnifSkin = player.getInventory().getItemCountByItemId(182201820) > 0;
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (abexSkin || worgSkin || karnifSkin) {
						    return sendQuestDialog(env, 1352);
						} else {
							return sendQuestDialog(env, 1693);
						}
					} case STEP_TO_1: {
						if (abexSkin) {
						    qs.setQuestVar(1);
						    qs.setStatus(QuestStatus.REWARD);
						    updateQuestStatus(env);
						    return sendQuestDialog(env, 5);
						}
					} case STEP_TO_2: {
						if (worgSkin) {
							qs.setQuestVar(2);
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							return sendQuestDialog(env, 6);
						}
					} case STEP_TO_3: {
						if (karnifSkin) {
							qs.setQuestVar(3);
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							return sendQuestDialog(env, 7);
						}
					} case FINISH_DIALOG: {
						return sendQuestSelectionDialog(env);
					}
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 204580) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}