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
package quest.poeta;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.network.aion.SystemMessageId;
import com.aionemu.gameserver.network.aion.serverpackets.S_PLAY_MODE;
import com.aionemu.gameserver.network.aion.serverpackets.S_ACTION;
import com.aionemu.gameserver.network.aion.serverpackets.S_MESSAGE_CODE;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestDialog;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.services.ClassChangeService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.zone.ZoneName;

import java.util.ArrayList;
import java.util.List;

/****/
/** Author Rinzler (Encom)
/****/

public class _1006Ascension extends QuestHandler
{
	private final static int questId = 1006;

	public _1006Ascension() {
		super(questId);
	}

	@Override
	public void register() {
		if (CustomConfig.ENABLE_SIMPLE_2NDCLASS) {
			return;
		}
		int[] mobs = { 211042, 211043 };
		int[] npcs = { 790001, 730008, 205000 };
		qe.registerOnLevelUp(questId);
		for (int mob : mobs) {
			qe.registerQuestNpc(mob).addOnKillEvent(questId);
		}
		for (int npc : npcs) {
			qe.registerQuestNpc(npc).addOnTalkEvent(questId);
		}
		qe.registerQuestItem(182200007, questId);
		qe.registerOnEnterWorld(questId);
		qe.registerOnDie(questId);
		qe.registerOnMovieEndQuest(14, questId);
		qe.registerOnMovieEndQuest(151, questId);
	}

	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null) {
			return false;
		}
		QuestDialog dialog = env.getDialog();
		int var = qs.getQuestVarById(0);
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc) {
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		}

		if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 790001: { // Pernos
					switch (dialog) {
						case START_DIALOG: {
							if (var == 0) {
								return sendQuestDialog(env, 1011);
							} else if (var == 3) {
								return sendQuestDialog(env, 1693);
							} else if (var == 5) {
								return sendQuestDialog(env, 2034);
							}
						}
						case STEP_TO_1: {
							if (player.getInventory().getItemCountByItemId(182200007) == 0) {
								if (!giveQuestItem(env, 182200007, 1)) {
									return true;
								}
							}
							qs.setQuestVar(1);
							updateQuestStatus(env);
							TeleportService2.teleportTo(player, 210010000, 657f, 1071f, 99.375f, (byte) 72, TeleportAnimation.BEAM_ANIMATION);
							return true;
						}
						case STEP_TO_3: {
							WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(310010000);
							InstanceService.registerPlayerWithInstance(newInstance, player);
							TeleportService2.teleportTo(player, 310010000, newInstance.getInstanceId(), 52, 174, 229, (byte) 10);
							qs.setQuestVar(99); // 99
							updateQuestStatus(env);
							removeQuestItem(env, 182200009, 1);
							return closeDialogWindow(env);
						}
						case STEP_TO_4: {
							PlayerClass playerClass = player.getCommonData().getPlayerClass();
							if (var == 5) {
								if (playerClass.isStartingClass()) {
									if (playerClass == PlayerClass.WARRIOR) {
										return sendQuestDialog(env, 2375);
									} else if (playerClass == PlayerClass.SCOUT) {
										return sendQuestDialog(env, 2716);
									} else if (playerClass == PlayerClass.MAGE) {
										return sendQuestDialog(env, 3057);
									} else if (playerClass == PlayerClass.PRIEST) {
										return sendQuestDialog(env, 3398);
									}
								}
							}
						}
						case STEP_TO_5: {
							return setPlayerClass(env, qs, PlayerClass.GLADIATOR);
						}
						case STEP_TO_6: {
							return setPlayerClass(env, qs, PlayerClass.TEMPLAR);
						}
						case STEP_TO_7: {
							return setPlayerClass(env, qs, PlayerClass.ASSASSIN);
						}
						case STEP_TO_8: {
							return setPlayerClass(env, qs, PlayerClass.RANGER);
						}
						case STEP_TO_9: {
							return setPlayerClass(env, qs, PlayerClass.SORCERER);
						}
						case STEP_TO_10: {
							return setPlayerClass(env, qs, PlayerClass.SPIRIT_MASTER);
						}
						case STEP_TO_11: {
							return setPlayerClass(env, qs, PlayerClass.CLERIC);
						}
						case STEP_TO_12: {
							return setPlayerClass(env, qs, PlayerClass.CHANTER);
						}
						default:
							break;
					}
					break;
				}
				case 730008: { // Daminu
					switch (dialog) {
						case START_DIALOG: {
							if (var == 2) {
								if (player.getInventory().getItemCountByItemId(182200008) >= 1) {
									return sendQuestDialog(env, 1352);
								}
							}
						}
						case STEP_TO_2: {
							playQuestMovie(env, 14);
							return true;
						}
						default:
							break;
					}
					break;
				}
				case 205000: { // Belpartan
					switch (dialog) {
						case START_DIALOG: {
							if (qs.getQuestVars().getQuestVars() == 99) {
								SkillEngine.getInstance().applyEffectDirectly(1910, player, player, 0);
								player.setState(CreatureState.FLIGHT_TELEPORT);
								player.unsetState(CreatureState.ACTIVE);
								player.setFlightTeleportId(1001);
								PacketSendUtility.sendPacket(player, new S_ACTION(player, EmotionType.START_FLYTELEPORT, 1001, 0));
								qs.setQuestVar(50); // 50
								updateQuestStatus(env);
								final int instanceId = player.getInstanceId();
								ThreadPoolManager.getInstance().schedule(new Runnable() {
									@Override
									public void run() {
										qs.setQuestVar(51);
										updateQuestStatus(env);
										List<Npc> mobs = new ArrayList<Npc>();
										mobs.add((Npc) QuestService.spawnQuestNpc(310010000, instanceId, 211042, (float) 224.073, (float) 239.1, (float) 206.7,
												(byte) 0));
										mobs.add((Npc) QuestService.spawnQuestNpc(310010000, instanceId, 211042, (float) 233.5, (float) 241.04,
												(float) 206.365, (byte) 0));
										mobs.add((Npc) QuestService.spawnQuestNpc(310010000, instanceId, 211042, (float) 229.6, (float) 265.7, (float) 205.7,
												(byte) 0));
										mobs.add((Npc) QuestService.spawnQuestNpc(310010000, instanceId, 211042, (float) 222.8, (float) 262.5, (float) 205.7,
												(byte) 0));
										for (Npc mob : mobs) {
											mob.getAggroList().addDamage(player, 1000);
										}
									}
								}, 43000);
								return true;
							}
						}
						default:
							break;
					}
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 790001) { // Pernos
				switch (env.getDialog()) {
					case SELECTED_QUEST_NOREWARD:
						if (player.getWorldId() == 310010000) {
							TeleportService2.teleportTo(player, 210010000, 245.14868f, 1639.1372f, 100.35713f, (byte) 60, TeleportAnimation.BEAM_ANIMATION);
						}
						break;
					default:
						break;
				}
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}

	@Override
	public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (player.isInsideZone(ZoneName.get("CLIONA_LAKE_210010000"))) {
				int var = qs.getQuestVarById(0);
				if (var == 1) {
					return HandlerResult.fromBoolean(useQuestItem(env, item, 1, 2, false, 182200008, 1, 0)); // 2
				}
			}
		}
		return HandlerResult.SUCCESS; // ??
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			Npc npc = (Npc) env.getVisibleObject();
			int var = qs.getQuestVarById(0);
			int targetId = env.getTargetId();
			if (targetId == 211042) {
				NpcActions.delete(npc);
				if (var >= 51 && var < 54) {
					return defaultOnKillEvent(env, 211042, 51, 54); // 52 - 54
				} else if (var == 54) {
					qs.setQuestVar(4); // 4
					updateQuestStatus(env);
					Npc mob = (Npc) QuestService
							.spawnQuestNpc(310010000, player.getInstanceId(), 211043, (float) 226.7, (float) 251.5, (float) 205.5, (byte) 0);
					mob.getAggroList().addDamage(player, 1000);
					return true;
				}
			} else if (targetId == 211043 && var == 4) {
				playQuestMovie(env, 151);
				for (Npc npcInside : player.getPosition().getWorldMapInstance().getNpcs()) {
					NpcActions.delete(npcInside);
				}
				QuestService.addNewSpawn(310010000, player.getInstanceId(), 790001, (float) 220.6, (float) 247.8, (float) 206.0, (byte) 0);
				qs.setQuestVar(5); // 5
				updateQuestStatus(env);
			}
		}
		return false;
	}

	private boolean setPlayerClass(QuestEnv env, QuestState qs, PlayerClass playerClass) {
		Player player = env.getPlayer();
		if (player.getPlayerClass().isStartingClass()) {
			ClassChangeService.setClass(player, playerClass);
			player.getController().upgradePlayer();
			changeQuestStep(env, 5, 5, true); // reward
			return sendQuestDialog(env, 5);
		}
		return false;
	}

	@Override
	public boolean onDieEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() != QuestStatus.START) {
			int var = qs.getQuestVars().getQuestVars();
			if (var == 4 || (var == 5 && player.getPlayerClass().isStartingClass()) || (var >= 50 && var <= 55)) {
				qs.setQuestVar(3);
				updateQuestStatus(env);
				PacketSendUtility.sendPacket(player, new S_MESSAGE_CODE(SystemMessageId.QUEST_FAILED_$1, DataManager.QUEST_DATA.getQuestById(questId)
						.getName()));
			}
		}
		return false;
	}

	@Override
	public boolean onEnterWorldEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVars().getQuestVars();
			if (var == 4 || (var == 5 && player.getPlayerClass().isStartingClass()) || (var >= 50 && var <= 55) || var == 99) {
				if (player.getWorldId() != 310010000) {
					qs.setQuestVar(3);
					updateQuestStatus(env);
					PacketSendUtility.sendPacket(player, new S_MESSAGE_CODE(SystemMessageId.QUEST_FAILED_$1, DataManager.QUEST_DATA.getQuestById(questId)
							.getName()));
				} else {
					PacketSendUtility.sendPacket(player, new S_PLAY_MODE(1));
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env);
	}

	@Override
	public boolean onMovieEndEvent(QuestEnv env, int movieId) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);

		if (qs == null || qs.getStatus() != QuestStatus.START) {
			return false;
		}

		if (movieId == 14) {
			removeQuestItem(env, 182200008, 1);
			giveQuestItem(env, 182200009, 1);
			qs.setQuestVar(3);
			updateQuestStatus(env);
			TeleportService2.teleportTo(player, 210010000, 246f, 1639f, 100.316f, (byte) 56, TeleportAnimation.BEAM_ANIMATION);
			return true;
		}
		return false;
	}
}