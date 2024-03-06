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
import com.aionemu.gameserver.services.QuestService;

/****/
/** Author Rinzler (Encom)
/****/

public class _2114The_Insect_Problem extends QuestHandler
{
	private final static int questId = 2114;
	
	public _2114The_Insect_Problem() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(203533).addOnQuestStart(questId);
		qe.registerQuestNpc(203533).addOnTalkEvent(questId);
		qe.registerQuestNpc(210380).addOnKillEvent(questId);
		qe.registerQuestNpc(210381).addOnKillEvent(questId);
		qe.registerQuestNpc(210734).addOnKillEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 203533) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						return sendQuestDialog(env, 1011);
					} case STEP_TO_1: {
						if (QuestService.startQuest(env)) {
							qs.setQuestVar(1);
							updateQuestStatus(env);
							return closeDialogWindow(env);
						}
					} case STEP_TO_2: {
						if (QuestService.startQuest(env)) {
							qs.setQuestVar(11);
							updateQuestStatus(env);
							return closeDialogWindow(env);
						}
					}
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			int var = qs.getQuestVarById(0);
			if (targetId == 203533) {
				switch (env.getDialog()) {
					case USE_OBJECT: {
						if (var == 10) {
							return sendQuestDialog(env, 5);
						} else if (var == 20) {
							return sendQuestDialog(env, 6);
						}
					} case SELECT_NO_REWARD: {
						if (QuestService.finishQuest(env, var / 10 - 1)) {
							return closeDialogWindow(env);
						}
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onKillEvent(QuestEnv env) {
		int[] groveSparkie = {210734};
		int[] gooeySylphen = {210380, 210381};
		if (defaultOnKillEvent(env, groveSparkie, 1, 10) ||
		    defaultOnKillEvent(env, groveSparkie, 10, true) ||
			defaultOnKillEvent(env, gooeySylphen, 11, 20) ||
			defaultOnKillEvent(env, gooeySylphen, 20, true)) {
			return true;
		} else {
			return false;
		}
	}
}