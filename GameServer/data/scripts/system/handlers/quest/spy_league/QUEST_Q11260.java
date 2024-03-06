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
package quest.spy_league;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.questEngine.model.QuestDialog;

/****/
/** Author Rinzler (Encom)
/****/

public class QUEST_Q11260 extends QuestHandler
{
	private final static int questId = 11260;
	private final static int[] DF4_3011_Boss = {257605, 257610};
	private final static int[] DF4_3021_Boss = {257905, 257910};
	
	public QUEST_Q11260() {
		super(questId);
	}
	
	@Override
	public void register() {
		for (int mob: DF4_3011_Boss) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        } for (int mob: DF4_3021_Boss) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        }
		qe.registerQuestNpc(799038).addOnQuestStart(questId);
		qe.registerQuestNpc(799038).addOnTalkEvent(questId);
	}
	
	@Override
    public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
			if (targetId == 799038) {
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
			int var = qs.getQuestVarById(0);
			if (targetId == 799038) {
                switch (env.getDialog()) {
                    case START_DIALOG: {
						if (var == 1) {
							return sendQuestDialog(env, 1693);
						}
					} case STEP_TO_3: {
						qs.setStatus(QuestStatus.REWARD);
						updateQuestStatus(env);
						return sendQuestDialog(env, 2375);
					}
                }
            }
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799038) {
				if (env.getDialog() == QuestDialog.SELECT_REWARD) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
        return false;
    }
	
	@Override
	public boolean onKillEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (var == 0) {
				int targetId = env.getTargetId();
				int var1 = qs.getQuestVarById(1);
				int var2 = qs.getQuestVarById(2);
				switch (targetId) {
					case 257605:
					case 257610:
						if (var1 < 0) {
							return defaultOnKillEvent(env, DF4_3011_Boss, 0, 0, 1);
						} else if (var1 == 0) {
							if (var2 == 1) {
								qs.setQuestVar(1);
								updateQuestStatus(env);
								return true;
							} else {
								return defaultOnKillEvent(env, DF4_3011_Boss, 0, 1, 1);
							}
						}
					break;
					case 257905:
					case 257910:
						if (var2 < 0) {
							return defaultOnKillEvent(env, DF4_3021_Boss, 0, 0, 2);
						} else if (var2 == 0) {
							if (var1 == 1) {
								qs.setQuestVar(1);
								updateQuestStatus(env);
								return true;
							} else {
								return defaultOnKillEvent(env, DF4_3021_Boss, 0, 1, 2);
							}
						}
					break;
				}
			}
		}
		return false;
	}
}