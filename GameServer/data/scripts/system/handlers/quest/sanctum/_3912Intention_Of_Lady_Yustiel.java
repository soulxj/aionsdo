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
package quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.questEngine.model.QuestDialog;

/****/
/** Author Rinzler (Encom)
/****/

public class _3912Intention_Of_Lady_Yustiel extends QuestHandler
{
	private final static int questId = 3912;
	
	public _3912Intention_Of_Lady_Yustiel() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestSkill(972, questId);
		qe.registerQuestSkill(973, questId);
		qe.registerQuestSkill(974, questId);
		qe.registerQuestSkill(990, questId);
		qe.registerQuestSkill(1051, questId);
		qe.registerQuestSkill(1057, questId);
		qe.registerQuestSkill(1128, questId);
		qe.registerQuestSkill(2139, questId);
		qe.registerQuestNpc(203707).addOnQuestStart(questId);
        qe.registerQuestNpc(203707).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 203707) {
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
			if (targetId == 203707) {
				switch (env.getDialog()) {
                    case START_DIALOG: {
						return sendQuestDialog(env, 10002);
					} case SELECT_REWARD: {
						changeQuestStep(env, var, var, true);
				        return sendQuestDialog(env, 5);
					}
                }
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203707) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
	
	@Override
	public boolean onUseSkillEvent(QuestEnv env, int skillUsedId) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		VisibleObject target = player.getTarget();
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (((Creature) target).getLifeStats().isAlreadyDead() &&
			    skillUsedId == 972 || skillUsedId == 973 || skillUsedId == 974 || skillUsedId == 990 ||
				skillUsedId == 1051 || skillUsedId == 1057 || skillUsedId == 1128 || skillUsedId == 2139) {
				int var = qs.getQuestVarById(0);
				if (var >= 0 && var < 19) {
					changeQuestStep(env, var, var + 1, false);
					return true;
				} else if (var == 19) {
					qs.setQuestVar(20);
					updateQuestStatus(env);
					return true;
				}
			}
		}
		return false;
	}
}