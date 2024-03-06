/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.ai2.handler;

import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.AISubState;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.S_NPC_HTML_MESSAGE;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

import java.util.*;

public class TalkEventHandler
{
	public static void onTalk(NpcAI2 npcAI, Creature creature) {
		onSimpleTalk(npcAI, creature);
		if (creature instanceof Player) {
			Player player = (Player) creature;
			List<Integer> relatedQuests = QuestEngine.getInstance().getQuestNpc(npcAI.getOwner().getNpcId()).getOnTalkEvent();
			if (QuestEngine.getInstance().onDialog(new QuestEnv(npcAI.getOwner(), player, 0, -1))) {
				return;
			}
			PacketSendUtility.sendPacket(player, new S_NPC_HTML_MESSAGE(npcAI.getOwner().getObjectId(), 10));
			for (int questId: relatedQuests) {
                if (player.getQuestStateList().hasQuest(questId)) {
					final QuestState qs = player.getQuestStateList().getQuestState(questId);
					if (qs == null || qs.getStatus() != QuestStatus.REWARD) {
						continue;
					}
					PacketSendUtility.sendPacket(player, new S_NPC_HTML_MESSAGE(npcAI.getOwner().getObjectId(), 5, questId));
					return;
				}
            }
		}
	}
	
	public static void onSimpleTalk(NpcAI2 npcAI, Creature creature) {
		if (npcAI.getOwner().getObjectTemplate().isDialogNpc()) {
            npcAI.setSubStateIfNot(AISubState.TALK);
            npcAI.getOwner().setTarget(creature);
        }
	}
	
	public static void onFinishTalk(NpcAI2 npcAI, Creature creature) {
		Npc owner = npcAI.getOwner();
		if (owner.isTargeting(creature.getObjectId())) {
			if (npcAI.getState() != AIState.FOLLOWING) {
				owner.setTarget(null);
			}
			npcAI.think();
		}
	}
	
	public static void onSimpleFinishTalk(NpcAI2 npcAI, Creature creature) {
		Npc owner = npcAI.getOwner();
		if (owner.isTargeting(creature.getObjectId()) && npcAI.setSubStateIfNot(AISubState.NONE)) {
			owner.setTarget(null);
		}
	}
}