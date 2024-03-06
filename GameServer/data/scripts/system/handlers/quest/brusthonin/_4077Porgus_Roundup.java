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

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.MathUtil;

/****/
/** Author Rinzler (Encom)
/****/

public class _4077Porgus_Roundup extends QuestHandler
{
	private final static int questId = 4077;
	
	public _4077Porgus_Roundup() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(205158).addOnQuestStart(questId);
		qe.registerQuestNpc(205158).addOnTalkEvent(questId);
		qe.registerQuestNpc(214732).addOnAttackEvent(questId);
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 205158) {
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
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205158) {
                if (env.getDialog() == QuestDialog.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
				} else if (env.getDialog() == QuestDialog.SELECT_REWARD) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean onAttackEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int var = qs.getQuestVars().getQuestVars();
		int targetId = env.getTargetId();
		if (targetId != 214732) {
			return false;
		}
		Npc npc = (Npc) env.getVisibleObject();
		if (qs != null && qs.getStatus() == QuestStatus.START && var == 0) {
			if (MathUtil.getDistance(1356.0000f, 1901.0000f, 46.0000f, npc.getX(), npc.getY(), npc.getZ()) > 10) {
				return false;
			}
			qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
			updateQuestStatus(env);
			npc.getController().scheduleRespawn();
			npc.getController().onDelete();
			return true;
		} else if (qs != null && qs.getStatus() == QuestStatus.START && var == 1) {
			if (MathUtil.getDistance(1356.0000f, 1901.0000f, 46.0000f, npc.getX(), npc.getY(), npc.getZ()) > 10) {
				return false;
			}
			qs.setStatus(QuestStatus.REWARD);
			updateQuestStatus(env);
			npc.getController().scheduleRespawn();
			npc.getController().onDelete();
			return true;
		}
		return false;
	}
}