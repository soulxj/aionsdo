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
package quest.brusthonin;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/****/
/** Author Rinzler (Encom)
/****/

public class _4082Gathering_The_Herb_Pouches extends QuestHandler
{
	private final static int questId = 4082;
	
	public _4082Gathering_The_Herb_Pouches() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(205190).addOnQuestStart(questId);
		qe.registerQuestNpc(205190).addOnTalkEvent(questId);
		qe.registerQuestNpc(700430).addOnTalkEvent(questId);
		qe.registerQuestNpc(700431).addOnTalkEvent(questId);
		qe.registerQuestNpc(700432).addOnTalkEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 205190) {
				switch (env.getDialog()) {
                    case START_DIALOG: {
						return sendQuestDialog(env, 1011);
					} case ASK_ACCEPTION: {
						return sendQuestDialog(env, 4);
					} case ACCEPT_QUEST: {
						return sendQuestStartDialog(env, 182209058, 1);
					} case REFUSE_QUEST: {
				        return closeDialogWindow(env);
					}
                }
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (targetId == 205190) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						return sendQuestDialog(env, 2375);
					} case CHECK_COLLECTED_ITEMS: {
						removeQuestItem(env, 182209058, 1);
						return checkQuestItems(env, 0, 1, true, 5, 2716);
					}
				}
			} if (targetId == 700430) {
				switch (env.getDialog()) {
                    case USE_OBJECT: {
						if (var == 0) {
							giveQuestItem(env, 182209055, 1);
							return closeDialogWindow(env);
						}
					}
				}
			} if (targetId == 700431) {
				switch (env.getDialog()) {
                    case USE_OBJECT: {
						if (var == 0) {
							giveQuestItem(env, 182209056, 1);
							return closeDialogWindow(env);
						}
					}
				}
			} if (targetId == 700432) {
				switch (env.getDialog()) {
                    case USE_OBJECT: {
						if (var == 0) {
							giveQuestItem(env, 182209057, 1);
							return closeDialogWindow(env);
						}
					}
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 205190) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
}