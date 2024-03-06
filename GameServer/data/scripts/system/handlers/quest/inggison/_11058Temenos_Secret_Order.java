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

/****/
/** Author Rinzler (Encom)
/****/

public class _11058Temenos_Secret_Order extends QuestHandler
{
	private final static int questId = 11058;
	private final static int[] LF4_Gateway_Da_Guard_Boss_Q03 = {296493};
	private final static int[] LF4_Gateway_Da_Guard_Boss_Q04 = {296494};
	private final static int[] LF4_Gateway_Da_Guard_Boss_Q05 = {296495};
	
	public _11058Temenos_Secret_Order() {
		super(questId);
	}
	
	public void register() {
		qe.registerQuestItem(182206844, questId);
		qe.registerQuestNpc(799049).addOnTalkEvent(questId);
		for (int mob: LF4_Gateway_Da_Guard_Boss_Q03) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        } for (int mob: LF4_Gateway_Da_Guard_Boss_Q04) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        } for (int mob: LF4_Gateway_Da_Guard_Boss_Q05) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        }
	}
	
	@Override
	public HandlerResult onItemUseEvent(final QuestEnv env, final Item item) {
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
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
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
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			long kinah = player.getInventory().getKinah();
            if (targetId == 799049) {
                if (env.getDialog() == QuestDialog.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
				} else if (env.getDialog() == QuestDialog.SELECT_ACTION_2034) {
                    return sendQuestDialog(env, 2034);
				} else if (env.getDialog() == QuestDialog.SELECT_REWARD) {
					if (kinah >= 20000000) {
						player.getInventory().decreaseKinah(20000000);
						return sendQuestDialog(env, 5);
					} else {
						return sendQuestDialog(env, 3739);
					}
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onKillEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (var == 0) {
				return defaultOnKillEvent(env, LF4_Gateway_Da_Guard_Boss_Q03, 0, 1);
			} else if (var == 1) {
				return defaultOnKillEvent(env, LF4_Gateway_Da_Guard_Boss_Q04, 1, 2);
			} else if (var == 2) {
				return defaultOnKillEvent(env, LF4_Gateway_Da_Guard_Boss_Q05, 2, true);
			}
		}
		return false;
	}
}