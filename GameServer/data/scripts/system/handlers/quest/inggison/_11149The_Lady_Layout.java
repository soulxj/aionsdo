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

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.zone.ZoneName;

/****/
/** Author Rinzler (Encom)
/****/

public class _11149The_Lady_Layout extends QuestHandler
{
	private final static int questId = 11149;

	public _11149The_Lady_Layout() {
		super(questId);
	}
	
	public void register() {
		qe.registerQuestNpc(296491).addOnQuestStart(questId);
		qe.registerQuestNpc(296491).addOnTalkEvent(questId);
		qe.registerOnEnterZone(ZoneName.get("LF4_SENSORY_AREA_Q11149_A_210050000"), questId);
		qe.registerOnEnterZone(ZoneName.get("LF4_SENSORY_AREA_Q11149_B_210050000"), questId);
		qe.registerOnEnterZone(ZoneName.get("LF4_SENSORY_AREA_Q11149_C_210050000"), questId);
	}
	
	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == 296491) {
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
			int var1 = qs.getQuestVarById(1);
			int var2 = qs.getQuestVarById(2);
			int var3 = qs.getQuestVarById(3);
			if (targetId == 296491) {
				switch (env.getDialog()) {
					case START_DIALOG: {
						if (var == 0 && var1 == 0 && var2 == 0 && var3 == 0) {
							return sendQuestDialog(env, 10002);
						}
					} case SELECT_REWARD: {
						changeQuestStep(env, 0, 0, true);
						return sendQuestDialog(env, 5);
					}
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 296491) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
	
	@Override
	public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			int var1 = qs.getQuestVarById(1);
			int var2 = qs.getQuestVarById(2);
			int var3 = qs.getQuestVarById(3);
			if (var == 0) {
				if (zoneName == ZoneName.get("LF4_SENSORY_AREA_Q11149_A_210050000")) {
					if (var1 == 0) {
						changeQuestStep(env, 0, 1, false, 1);
						///You investigated the Sulfur Geyser!
						PacketSendUtility.playerSendPacketTime(player, S_MESSAGE_CODE.STR_QUEST_SAY_LF4_15, 0);
						return true;
					}
				} else if (zoneName == ZoneName.get("LF4_SENSORY_AREA_Q11149_B_210050000")) {
					if (var2 == 0) {
						changeQuestStep(env, 0, 1, false, 2);
						///You investigated the Pool Geyser!
						PacketSendUtility.playerSendPacketTime(player, S_MESSAGE_CODE.STR_QUEST_SAY_LF4_16, 0);
						return true;
					}
				} else if (zoneName == ZoneName.get("LF4_SENSORY_AREA_Q11149_C_210050000")) {
					if (var3 == 0) {
						changeQuestStep(env, 0, 1, false, 3);
						///You investigated the Rock Geyser!
						PacketSendUtility.playerSendPacketTime(player, S_MESSAGE_CODE.STR_QUEST_SAY_LF4_17, 0);
						return true;
					}
				}
			}
		}
		return false;
	}
}