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
package quest.pandaemonium;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.*;

/****/
/** Author Rinzler (Encom)
/****/

public class _2938Secret_Library_Access extends QuestHandler
{
	public static final int questId = 2938;
	private final static int[] npcs = {203557, 204267};
	
	public _2938Secret_Library_Access() {
		super(questId);
	}
	
	@Override
	public void register() {
		qe.registerQuestNpc(204267).addOnQuestStart(questId);
		qe.registerQuestNpc(204267).addOnTalkEvent(questId);
		qe.registerQuestNpc(203557).addOnTalkEvent(questId);
	}
	
	private boolean crushingTheConspiracy(Player player) {
		final QuestState qs = player.getQuestStateList().getQuestState(2022);
		PacketSendUtility.sendWhiteMessage(player, "You must complete <Crushing The Conspiracy> first");
		return ((qs == null) || (qs.getStatus() != QuestStatus.COMPLETE && qs.getStatus() != QuestStatus.NONE)) ? false : true;
	}
	
	@Override
    public boolean onDialogEvent(final QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 204267) {
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
			if (targetId == 203557) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						PlayerClass playerClass = player.getCommonData().getPlayerClass();
						if (playerClass == PlayerClass.THUNDERER) {
							return sendQuestDialog(env, 1011);
						} else if (crushingTheConspiracy(player) && playerClass != PlayerClass.THUNDERER) {
							return sendQuestDialog(env, 1011);
						} else {
							return sendQuestDialog(env, 1097);
						}
					} case SET_REWARD: {
						giveQuestItem(env, 182207026, 1);
						qs.setStatus(QuestStatus.REWARD);
						updateQuestStatus(env);
						return closeDialogWindow(env);
					}
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 204267) {
                if (env.getDialog() == QuestDialog.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
				} else if (env.getDialog() == QuestDialog.SELECT_REWARD) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
		///Teleport every time, once quest is completed!!!
		else if (qs.getStatus() == QuestStatus.COMPLETE) {
			if (targetId == 204267) {
				ThreadPoolManager.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						TeleportService2.teleportTo(player, 120010000, 1403.0000f, 1063.0000f, 206.0000f, (byte) 89);
					}
				}, 3000);
			}
        }
        return false;
    }
}