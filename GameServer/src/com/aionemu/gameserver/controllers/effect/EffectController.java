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
package com.aionemu.gameserver.controllers.effect;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.*;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
import com.aionemu.gameserver.skillengine.effect.EffectType;
import com.aionemu.gameserver.skillengine.model.*;
import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster.BroadcastMode;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import javolution.util.FastMap;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class EffectController
{
	private Creature owner;
	protected Map<String, Effect> passiveEffectMap = new FastMap<String, Effect>().shared();
	protected Map<String, Effect> noshowEffects = new FastMap<String, Effect>().shared();
	protected Map<String, Effect> abnormalEffectMap = new FastMap<String, Effect>().shared();
	private final Lock lock = new ReentrantLock();
	protected int abnormals;
	private boolean isUnderShield = false;
	private boolean isUnderholyShield = false;
	
	public EffectController(Creature owner) {
		this.owner = owner;
	}
	
	public Creature getOwner() {
		return owner;
	}
	
	public boolean isUnderShield() {
		return isUnderShield;
	}
	
	public boolean isUnderholyShield() {
		return isUnderholyShield;
	}
	
	public void setUnderShield(boolean isUnderShield) {
		this.isUnderShield = isUnderShield;
	}
	
	public void setUnderholyShield(boolean isUnderholyShield) {
		this.isUnderholyShield = isUnderholyShield;
	}
	
	/**
	 * @param effect
	 */
	public void addEffect(Effect nextEffect) {
		Map<String, Effect> mapToUpdate = getMapForEffect(nextEffect);

		lock.lock();
		try {
			
			if (nextEffect.isPassive()) {
				boolean useEffectId = true;
				Effect existingEffect = mapToUpdate.get(nextEffect.getStack());
				if (existingEffect != null && existingEffect.isPassive()) {
					// check stack level
					if (existingEffect.getSkillStackLvl() > nextEffect.getSkillStackLvl()){
						return;
					}
					
					// check skill level (when stack level same)
					if (existingEffect.getSkillStackLvl() == nextEffect.getSkillStackLvl()
						&& existingEffect.getSkillLevel() > nextEffect.getSkillLevel()){
						return;
					}
					existingEffect.endEffect();
					useEffectId = false;
				}
				
				if (useEffectId) {
					/**
					 * idea here is that effects with same effectId shouldnt stack
					 * effect with higher basiclvl takes priority
					 */
					for (Effect effect : mapToUpdate.values()) {
						if (effect.getTargetSlot() == nextEffect.getTargetSlot()) {
							for (EffectTemplate et : effect.getEffectTemplates()) {
								if (et.getEffectid() == 0)
									continue;
								for (EffectTemplate et2 : nextEffect.getEffectTemplates()) {
									if (et2.getEffectid() == 0)
										continue;
									if (et.getEffectid() == et2.getEffectid()) {
										if (et.getBasicLvl() > et2.getBasicLvl())
											return;
										else
											effect.endEffect();
									}
								}
							}
						}
					}
				}
			}
			
			Effect conflictedEffect = findConflictedEffect(mapToUpdate, nextEffect);
			if (conflictedEffect != null) {
				conflictedEffect.endEffect();
			}
			
			if (!nextEffect.isPassive()) {
				if (searchConflict(nextEffect)) {
					return;
				}
				checkEffectCooldownId(nextEffect);
			}
			
			//Max 3 Chants Effect
			if (nextEffect.isToggle() && nextEffect.getTargetSlotEnum() == SkillTargetSlot.NOSHOW) {
				int mts = nextEffect.getSkillSubType() == SkillSubType.CHANT ? 3 : 1;
				Collection<Effect> filteredMap = (nextEffect.getSkillSubType() == SkillSubType.CHANT ? this.getAuraEffects() : this.getNoShowToggleEffects());
				 // for ranger or rider 2 toggle
                if (nextEffect.getEffector() instanceof Player) {
                      Player player = (Player) nextEffect.getEffector();
                      if (player.getPlayerClass() == PlayerClass.RANGER || player.getPlayerClass() == PlayerClass.TEMPLAR) {
                          mts = 2;
                      }
                }
                if (filteredMap.size() >= mts) {
                	Iterator<Effect> iter = filteredMap.iterator();
      				iter.next().endEffect();

			  }
			}
			
			if (nextEffect.isToggle() && nextEffect.getTargetSlotEnum() != SkillTargetSlot.NOSHOW) {
				Collection<Effect> filteredMap = this.getAbnormalEffectsToShow().stream().filter(p -> p.isToggle()).collect(Collectors.toList());
				if (nextEffect.getEffector() instanceof Player) {
					Player player = (Player) nextEffect.getEffector();
					if (player.getPlayerClass() == PlayerClass.GLADIATOR) {
						if (filteredMap.size() >= 1) {
		                	Iterator<Effect> iter = filteredMap.iterator();
		      				iter.next().endEffect();
						}
					}	 
				}
			}
            
			//Max 4 Chants Effect.
			if (nextEffect.isChant()) {
				Collection<Effect> chants = this.getChantEffects();
				if (chants.size() >= 4) {
					Iterator<Effect> chantIter = chants.iterator();
					chantIter.next().endEffect();
				}
			}
            
			mapToUpdate.put(nextEffect.getStack(), nextEffect);
		}
		finally {
			lock.unlock();
		}
		nextEffect.startEffect(false);
		if (!nextEffect.isPassive()) {
			broadCastEffects();
		}
	}

	/**
	 * @param mapToUpdate
	 * @param effect
	 * @return
	 */
	private final Effect findConflictedEffect(Map<String, Effect> mapToUpdate, Effect newEffect) {
		int conflictId = newEffect.getSkillTemplate().getConflictId();
		if(conflictId == 0){
			return null;
		}
		for(Effect effect : mapToUpdate.values()){
			if(effect.getSkillTemplate().getConflictId() == conflictId){
				return effect;
			}
		}
		return null;
	}

	/**
	 * @param effect
	 * @return
	 */
	private Map<String, Effect> getMapForEffect(Effect effect) {
		if (effect.isPassive())
			return passiveEffectMap;

		if (effect.isToggle() && effect.getTargetSlotEnum() == SkillTargetSlot.NOSHOW)
			return noshowEffects;

		return abnormalEffectMap;
	}

	/**
	 * @param stack
	 * @return abnormalEffectMap
	 */
	public Effect getAnormalEffect(String stack) {
		return abnormalEffectMap.get(stack);
	}

	/**
	 * @param skillId
	 * @return
	 */
	public boolean hasAbnormalEffect(int skillId) {
		Iterator<Effect> localIterator = this.abnormalEffectMap.values().iterator();
		while (localIterator.hasNext()) {
			Effect localEffect = localIterator.next();
			if (localEffect.getSkillId() == skillId)
				return true;
		}
		return false;
	}

	public void broadCastEffects() {
		owner.addPacketBroadcastMask(BroadcastMode.BROAD_CAST_EFFECTS);
	}

	/**
	 * Broadcasts current effects to all visible objects
	 */
	public void broadCastEffectsImp() {
		List<Effect> effects = getAbnormalEffects();
		PacketSendUtility.broadcastPacket(getOwner(), new S_ABNORMAL_STATUS_OTHER(getOwner(), abnormals, effects));
	}

	/**
	 * Used when player see new player
	 * 
	 * @param player
	 */
	public void sendEffectIconsTo(Player player) {
		List<Effect> effects = getAbnormalEffects();
		PacketSendUtility.sendPacket(player, new S_ABNORMAL_STATUS_OTHER(getOwner(), abnormals, effects));
	}

	/**
	 * @param effect
	 */
	public void clearEffect(Effect effect) {
		Map<String, Effect> mapForEffect = getMapForEffect(effect);
		mapForEffect.remove(effect.getStack());
		broadCastEffects();
	}

	/**
	 * Removes the effect by skillid.
	 * 
	 * @param skillid
	 */
	public void removeEffect(int skillid) {
		for (Effect effect : abnormalEffectMap.values()) {
			if (effect.getSkillId() == skillid) {
				effect.endEffect();
			}
		}

		for (Effect effect : passiveEffectMap.values()) {
			if (effect.getSkillId() == skillid) {
				effect.endEffect();
			}
		}

		for (Effect effect : noshowEffects.values()) {
			if (effect.getSkillId() == skillid) {
				effect.endEffect();
			}
		}
	}
	
	public void removeHideEffects() {
		for (Effect effect : abnormalEffectMap.values()) {
			if (effect.isHideEffect() && owner.getVisualState() < 10) {
				effect.endEffect();
				abnormalEffectMap.remove(effect.getStack());
			}
		}
	}

	/**
	 * Removes Paralyze effects from owner.
	 * 
	 */
	public void removeParalyzeEffects() {
		for (Effect effect : abnormalEffectMap.values()) {
			if (effect.isParalyzeEffect()) {
				effect.endEffect();
				abnormalEffectMap.remove(effect.getStack());
			}
		}
	}

	/**
	 * @param effectId
	 */
	public void removeEffectByEffectId(int effectId) {
		for (Effect effect : abnormalEffectMap.values()) {
			if (effect.containsEffectId(effectId)) {
				effect.endEffect();
			}
		}
	}

	public void removeByDispelEffect(DispelType dispelType, String value, int count, int dispelLevel, int power) {
        int effectId = 0;
        EffectType effectType = null;
        SkillTargetSlot skillTargetSlot = null;
        switch (dispelType) {
            case EFFECTID: 
            case EFFECTIDRANGE: {
                effectId = Integer.parseInt(value);
                break;
            } case EFFECTTYPE: {
                effectType = EffectType.valueOf(value);
                break;
            } case SLOTTYPE: {
                skillTargetSlot = SkillTargetSlot.valueOf(value);
            }
        } for (Effect effect : this.abnormalEffectMap.values()) {
            if (count == 0)
				break;
            switch (dispelType) {
                case EFFECTID: 
                case EFFECTIDRANGE: {
                    if (effect.containsEffectId(effectId)) break;
                    continue;
                } case EFFECTTYPE: {
                    if (effect.getSkillTemplate().getEffects().isEffectTypePresent(effectType) && (!effectType.equals((Object)EffectType.STUN) || effect.getSkillId() != 11904)) break;
                    continue;
                } case SLOTTYPE: {
                    if (effect.getTargetSlot() == skillTargetSlot.ordinal()) break;
                    continue;
                }
            } if (effect.getReqDispelLevel() <= dispelLevel) continue;
            if (this.removePower(effect, power)) {
                effect.endEffect();
                this.abnormalEffectMap.remove(effect.getStack());
            }
            count--;
        } for (Effect effect : this.noshowEffects.values()) {
            if (count == 0)
				break;
            switch (dispelType) {
                case EFFECTID: 
                case EFFECTIDRANGE: {
                    if (effect.containsEffectId(effectId)) break;
                    continue;
                } case EFFECTTYPE: {
                    if (effect.getSkillTemplate().getEffects().isEffectTypePresent(effectType) && (!effectType.equals((Object)EffectType.STUN) || effect.getSkillId() != 11904)) break;
                    continue;
                } case SLOTTYPE: {
                    if (effect.getTargetSlot() == skillTargetSlot.ordinal()) break;
                    continue;
                }
            } if (effect.getReqDispelLevel() <= dispelLevel) continue;
            if (this.removePower(effect, power)) {
                effect.endEffect();
                this.noshowEffects.remove(effect.getStack());
            }
            count--;
        }
    }

	public int calculateNumberOfEffects(int dispelLevel) {
        int number = 0;
        for (Effect effect : this.abnormalEffectMap.values()) {
            DispelCategoryType dispelCat = effect.getDispelCategory();
            SkillTargetSlot tragetSlot = effect.getSkillTemplate().getTargetSlot();
            if (effect.getDuration() >= 86400000) {
            	continue;
            }
            
            if (effect.isSanctuaryEffect()) {
				continue;
			}
            
            if (tragetSlot != SkillTargetSlot.BUFF && (tragetSlot != SkillTargetSlot.DEBUFF && dispelCat != DispelCategoryType.ALL)
					|| effect.getTargetSlotLevel() >= 2) {
				continue;
			}
            
            switch (dispelCat) {
                case ALL: 
                case BUFF: {
                    if (effect.getReqDispelLevel() <= dispelLevel)
                    number++;
					break;
                }
			default:
				break;
            }
        }
        return number;
    }

	public void removeEffectByDispelCat(DispelCategoryType dispelCat, SkillTargetSlot targetSlot, int count, int dispelLevel, int power, boolean itemTriggered) {
        for (Effect effect : this.abnormalEffectMap.values()) {
            if (count == 0)
				break;
			if ((effect.getSkillTemplate().isUndispellableByPotions()) && itemTriggered)
				continue;
            if (effect.getDuration() >= 86400000 || effect.isSanctuaryEffect() || effect.getTargetSlot() != targetSlot.ordinal() || effect.getTargetSlotLevel() >= 2) continue;
            boolean remove = false;
            switch (dispelCat) {
                case ALL: {
                    if (effect.getDispelCategory() == DispelCategoryType.ALL ||
					    effect.getDispelCategory() == DispelCategoryType.DEBUFF_MENTAL ||
						effect.getDispelCategory() == DispelCategoryType.DEBUFF_PHYSICAL &&
						effect.getReqDispelLevel() <= dispelLevel)
                    remove = true;
                    break;
                } case DEBUFF_MENTAL: {
                    if (effect.getDispelCategory() == DispelCategoryType.ALL ||
					    effect.getDispelCategory() == DispelCategoryType.DEBUFF_MENTAL &&
						effect.getReqDispelLevel() <= dispelLevel)
                    remove = true;
                    break;
                } case DEBUFF_PHYSICAL: {
                    if (effect.getDispelCategory() == DispelCategoryType.ALL ||
					    effect.getDispelCategory() == DispelCategoryType.DEBUFF_PHYSICAL &&
						effect.getReqDispelLevel() <= dispelLevel)
                    remove = true;
                    break;
                } case BUFF: {
                    if (effect.getDispelCategory() == DispelCategoryType.BUFF &&
					    effect.getReqDispelLevel() <= dispelLevel)
                    remove = true;
                    break;
                } case STUN: {
                    if (effect.getDispelCategory() == DispelCategoryType.STUN)
                    remove = true;
                    break;
                } case NPC_BUFF: {
                    if (effect.getDispelCategory() == DispelCategoryType.NPC_BUFF)
                    remove = true;
                    break;
                } case NPC_DEBUFF_PHYSICAL: {
                    if (effect.getDispelCategory() == DispelCategoryType.NPC_DEBUFF_PHYSICAL)
                    remove = true;
                }
            } if (remove) {
                if (this.removePower(effect, power)) {
                    effect.endEffect();
                    this.abnormalEffectMap.remove(effect.getStack());
                } else if (this.owner instanceof Player) {
                    PacketSendUtility.sendPacket((Player)this.owner, S_MESSAGE_CODE.STR_MSG_NOT_ENOUGH_DISPELCOUNT);
                    count++;
                    power += 10;
                }
                count--;
                continue;
            }
            if (!(this.owner instanceof Player)) continue;
            PacketSendUtility.sendPacket((Player)this.owner, S_MESSAGE_CODE.STR_MSG_NOT_ENOUGH_DISPELLEVEL);
        }
    }

	private int removeEffectByTargetSlot(int count, SkillTargetSlot targetSlot, int dispelLevel, int power) {
        for (Effect effect : this.abnormalEffectMap.values()) {
            SkillTargetSlot ts = effect.getSkillTemplate().getTargetSlot();
            DispelCategoryType dispelCat = effect.getDispelCategory();
            if (ts != targetSlot) continue;
            if (count == 0)
				break;
            if (effect.getDuration() >= 86400000 || effect.isSanctuaryEffect() || effect.getTargetSlotLevel() >= 2)
				continue;
            boolean remove = false;
            switch (dispelCat) {
                case ALL: 
                case BUFF: {
                    if (effect.getReqDispelLevel() <= dispelLevel)
                    remove = true;
				    break;
                }
            } if (remove) {
                if (this.removePower(effect, power)) {
                    effect.endEffect();
                    this.abnormalEffectMap.remove(effect.getStack());
                    count--;
                    continue;
                }
                if (!(effect.getEffector() instanceof Player)) continue;
                PacketSendUtility.sendPacket((Player)effect.getEffector(), S_MESSAGE_CODE.STR_MSG_NOT_ENOUGH_DISPELCOUNT);
                continue;
            }
            if (!(effect.getEffector() instanceof Player)) continue;
            PacketSendUtility.sendPacket((Player)effect.getEffector(), S_MESSAGE_CODE.STR_MSG_NOT_ENOUGH_DISPELLEVEL);
        }
        return count;
    }

    public void dispelBuffCounterAtkEffect(int count, int dispelLevel, int power) {
        if (this.removeEffectByTargetSlot(count, SkillTargetSlot.BUFF, dispelLevel, power) > 0) {
            this.removeEffectByTargetSlot(count, SkillTargetSlot.DEBUFF, dispelLevel, power);
        }
    }

	public void removeEffectByEffectType(EffectType effectType) {
		for (Effect effect : abnormalEffectMap.values()) {
			for (EffectTemplate et : effect.getSuccessEffect()) {
				if (effectType == et.getEffectType())
					effect.endEffect();
			}
		}
	}
	
	private boolean removePower(Effect effect, int power) {
		int effectPower = effect.removePower(power);
		
		if (effectPower <= 0)
			return true;
		else
			return false;
	}

	/**
	 * Removes the effect by skillid.
	 * 
	 * @param skillid
	 */
	public void removePassiveEffect(int skillid) {
		for (Effect effect : passiveEffectMap.values()) {
			if (effect.getSkillId() == skillid) {
				effect.endEffect();
			}
		}
	}

	/**
	 * @param skillid
	 */
	public void removeNoshowEffect(int skillid) {
		for (Effect effect : noshowEffects.values()) {
			if (effect.getSkillId() == skillid) {
				effect.endEffect();
			}
		}
	}

	/**
	 * @see TargetSlot
	 * @param targetSlot
	 */
	public void removeAbnormalEffectsByTargetSlot(SkillTargetSlot targetSlot) {
		for (Effect effect : abnormalEffectMap.values()) {
			if (effect.getTargetSlot() == targetSlot.ordinal()) {
				effect.endEffect();
			}
		}
	}
	
	public Collection<Effect> getAuraEffects() {
		return Collections2.filter(this.noshowEffects.values(), new Predicate<Effect>() {

			@Override
			public boolean apply(Effect effect) {
				return effect.getSkillSubType() == SkillSubType.CHANT;
			}
		});
	}
	/**
	 * returns toggle noshow effects that are not auras
	 * @return
	 */
	public Collection<Effect> getNoShowToggleEffects() {
		return Collections2.filter(this.noshowEffects.values(), new Predicate<Effect>() {

			@Override
			public boolean apply(Effect effect) {
				return effect.getSkillSubType() != SkillSubType.CHANT;
			}
		});
	}

	/**
	 * Removes all effects from controllers and ends them appropriately Passive effect will not be removed
	 */
	public void removeAllEffects() {
		this.removeAllEffects(false);
	}

	public void removeAllEffects(boolean logout) {
		if (!logout) {
			Iterator<Map.Entry<String, Effect>> it = abnormalEffectMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Effect> entry = it.next();
				if (!entry.getValue().getSkillTemplate().isNoRemoveAtDie() &&
				    !entry.getValue().isXpBoost() &&
					!entry.getValue().isApBoost() &&
					!entry.getValue().isDrBoost() &&
					!entry.getValue().isBdrBoost() &&
					!entry.getValue().isEnchantBoost() &&
					!entry.getValue().isIdunDropBoost() &&
					!entry.getValue().isAuthorizeBoost() &&
					!entry.getValue().isSprintFpReduce() &&
					!entry.getValue().isReturnCoolReduce() &&
					!entry.getValue().isEnchantOptionBoost() &&
					!entry.getValue().isDeathPenaltyReduce() &&
					!entry.getValue().isOdellaRecoverIncrease()) {
					entry.getValue().endEffect();
					it.remove();
				}
			}

			for (Effect effect : noshowEffects.values()) {
				effect.endEffect();
			}
			noshowEffects.clear();
		} else {
			//remove all effects on logout
			for (Effect effect : abnormalEffectMap.values()) {
				effect.endEffect();
			}
			abnormalEffectMap.clear();
			for (Effect effect : noshowEffects.values()) {
				effect.endEffect();
			}
			noshowEffects.clear();
			for (Effect effect : passiveEffectMap.values()) {
				effect.endEffect();
			}
			passiveEffectMap.clear();
		}
	}

	/**
	 * Return true if skillId is present among creature's abnormals
	 */
	public boolean isAbnormalPresentBySkillId(int skillId) {
		for (Effect effect : abnormalEffectMap.values()) {
			if (effect.getSkillId() == skillId)
				return true;
		}
		return false;
	}

	public boolean isNoshowPresentBySkillId(int skillId) {
		for (Effect effect : noshowEffects.values()) {
			if (effect.getSkillId() == skillId)
				return true;
		}
		return false;
	}

	public boolean isPassivePresentBySkillId(int skillId) {
		for (Effect effect : passiveEffectMap.values()) {
			if (effect.getSkillId() == skillId)
				return true;
		}
		return false;
	}

	/**
	 * return true if creature is under Fear effect
	 */
	public boolean isUnderFear() {
		return isAbnormalSet(AbnormalState.FEAR);
	}

	public void updatePlayerEffectIcons() {
	}

	public void updatePlayerEffectIconsImpl() {
	}

	/**
	 * @return copy of anbornals list
	 */
	public List<Effect> getAbnormalEffects() {
		List<Effect> effects = new ArrayList<Effect>();
		Iterator<Effect> iterator = iterator();
		while (iterator.hasNext()) {
			Effect effect = iterator.next();
			if (effect != null)
				effects.add(effect);
		}
		return effects;
	}

	/**
	 * @return list of effects to display as top icons
	 */
	public Collection<Effect> getAbnormalEffectsToShow() {
		return Collections2.filter(abnormalEffectMap.values(), new Predicate<Effect>() {
			@Override
			public boolean apply(Effect effect) {
				return effect.getSkillTemplate().getTargetSlot() != SkillTargetSlot.NOSHOW;
			}
		});
	}
	public Collection<Effect> getChantEffects() {
		return Collections2.filter(abnormalEffectMap.values(), new Predicate<Effect>() {
			@Override
			public boolean apply(Effect effect) {
				return effect.isChant();
			}
		});
	}
	public Collection<Effect> getRangerEffects() {
        return Collections2.filter(abnormalEffectMap.values(), new Predicate<Effect>() {
            @Override
            public boolean apply(Effect effect) {
                return effect.isRangerBuff();
            }
        });
    }
	public Collection<Effect> getGladiatorEffects() {
        return Collections2.filter(abnormalEffectMap.values(), new Predicate<Effect>() {
            @Override
            public boolean apply(Effect effect) {
                return effect.isGladiatorBuff();
            }
        });
    }
	public Collection<Effect> getTemplarEffects() {
        return Collections2.filter(abnormalEffectMap.values(), new Predicate<Effect>() {
            @Override
            public boolean apply(Effect effect) {
                return effect.isTemplarBuff();
            }
        });
    }
	public Collection<Effect> getBuffEffects() {
        return Collections2.filter(abnormalEffectMap.values(), new Predicate<Effect>() {
            @Override
            public boolean apply(Effect effect) {
                return effect.isBuff();
            }
        });
    }

	/**
	 * ABNORMAL EFFECTS
	 */

	public void setAbnormal(int mask) {
        this.owner.getObserveController().notifyAbnormalSettedObservers(AbnormalState.getStateById(mask));
        this.abnormals |= mask;
        if (this.owner instanceof Player && this.owner.isInState(CreatureState.RESTING)) {
            this.owner.unsetState(CreatureState.RESTING);
            PacketSendUtility.broadcastPacket((Player)this.owner, (AionServerPacket)new S_ACTION(this.owner, EmotionType.STAND), true);
        }
    }

	public void unsetAbnormal(int mask) {
		int count = 0;
		for (Effect effect : abnormalEffectMap.values()) {
			if ((effect.getAbnormals() & mask) == mask)
				count++;
		}
		if (count <= 1)
			abnormals &= ~mask;
	}

	/**
	 * Used for checking unique abnormal states
	 * 
	 * @param id
	 * @return
	 */
	public boolean isAbnormalSet(AbnormalState id) {
		return (abnormals & id.getId()) == id.getId();
	}

	/**
	 * Used for compound abnormal state checks
	 * 
	 * @param id
	 * @return
	 */
	public boolean isAbnormalState(AbnormalState id) {
		int state = abnormals & id.getId();
		return state > 0 && state <= id.getId();
	}

	public int getAbnormals() {
		return abnormals;
	}

	/**
	 * @return
	 */
	public Iterator<Effect> iterator() {
		return abnormalEffectMap.values().iterator();
	}

	public TransformType getTransformType() {
		for (Effect eff : getAbnormalEffects()) {
			if (eff.isDeityAvatar()) {
				return TransformType.AVATAR;
			} else {
				return eff.getTransformType();
			}
		}
		return TransformType.NONE;		
	}
	
	public boolean isEmpty() {
		return abnormalEffectMap.isEmpty();
	}
	
	public void checkEffectCooldownId(Effect effect) {
		Collection<Effect> effects = this.getAbnormalEffectsToShow();
		int delayId = effect.getSkillTemplate().getDelayId();
		int rDelay = 0;
		int size = 0;
		if (delayId == 1) {
			return;
		}
		rDelay = delayId;
		if (delayId == rDelay && effects.size() >= size && !isArcherBuff(delayId)) {
			int i = 0;
			Effect toRemove = null;
			Iterator<Effect> iter2 = effects.iterator();
			while(iter2.hasNext()){
				Effect nextEffect = iter2.next();
				if (nextEffect.getSkillTemplate().getDelayId() == rDelay && nextEffect.getTargetSlot() == effect.getTargetSlot()) {
					i++;
					if (toRemove == null) {
						toRemove = nextEffect;
					}
				}
			} if (i >= size && toRemove != null) {
				toRemove.endEffect();
			}
		}
		
		if (isArcherBuff(effect.getSkillTemplate().getDelayId())) {
			Iterator<Effect> iter2 = effects.iterator();
			int count = 0;
			Effect toRemove = null;
			while (iter2.hasNext()) {
				Effect nextEffect = iter2.next();
				if (isArcherBuff(nextEffect.getSkillTemplate().getDelayId())) {
					count++;
					if (toRemove == null) {
						toRemove = nextEffect;
						continue;
					}
				}
				if (count >= 2) {
					toRemove.endEffect();
					break;
				}
			}
		}
		
		if (isGladiatorBuff(effect.getSkillTemplate().getSkillId())) {
			Iterator<Effect> iter2 = effects.iterator();
			int count = 0;
			Effect toRemove = null;
			while (iter2.hasNext()) {
				Effect nextEffect = iter2.next();
				if (isGladiatorBuff(nextEffect.getSkillTemplate().getSkillId())) {
					count++;
					if (toRemove == null) {
						toRemove = nextEffect;
						continue;
					}
				}
				if (count >= 1) {
					toRemove.endEffect();
					break;
				}
			}
		}
		
		if (isTemplarBuff(effect.getSkillTemplate().getSkillId())) {
			Iterator<Effect> iter2 = effects.iterator();
			int count = 0;
			Effect toRemove = null;
			while (iter2.hasNext()) {
				Effect nextEffect = iter2.next();
				if (isTemplarBuff(nextEffect.getSkillTemplate().getSkillId())) {
					count++;
					if (toRemove == null) {
						toRemove = nextEffect;
						continue;
					}
				}
				if (count >= 1) {
					toRemove.endEffect();
					break;
				}
			}
		}
	}
	
	public boolean isGladiatorBuff(int skillId) {
        switch (skillId) {
            case 251:
            case 252:
            case 253:
			case 258: 
			case 259:
			case 260:
            case 385: 
                return true;
            default:
                return false;
        }
    }
	
	public boolean isTemplarBuff(int skillId) {
        switch (skillId) {
            case 1976: 
			case 524: 
            case 173: 
                return true;
            default:
                return false;
        }
    }
	
	private boolean isArcherBuff(int cooldownId) {
		switch(cooldownId) {
			case 2005:
				return true;
			default:
				return false;
		}
	}
	
	private boolean checkExtraEffect(Effect effect) {
	Effect existingEffect = getMapForEffect(effect).get(effect.getStack());
		if (existingEffect != null) {
			if (existingEffect.getDispelCategory() == DispelCategoryType.EXTRA && effect.getDispelCategory() == DispelCategoryType.EXTRA) {
				existingEffect.endEffect();
				return true;
			}
		}
		return false;
	}
	
	public boolean searchConflict(Effect nextEffect) {
		if (priorityStigmaEffect(nextEffect) || checkExtraEffect(nextEffect)) {
			return false;
		} for (Effect effect : abnormalEffectMap.values()) {
			if (effect.getSkillSubType().equals(nextEffect.getSkillSubType()) || effect.getTargetSlotEnum().equals(nextEffect.getTargetSlotEnum())) {
				for (EffectTemplate et : effect.getEffectTemplates())	{
					if (et.getEffectid() == 0)
						continue;
					for (EffectTemplate et2 : nextEffect.getEffectTemplates())	{
						if (et2.getEffectid() == 0)
							continue;
						if (et.getEffectid() == et2.getEffectid()) {
							if (et.getBasicLvl() > et2.getBasicLvl()) {
								if (nextEffect.getTargetSlotEnum() != SkillTargetSlot.DEBUFF) {
									if (nextEffect.getEffectTemplates().size() == nextEffect.getSuccessEffect().size()) {
										nextEffect.setEffectResult(EffectResult.CONFLICT);
									} if (nextEffect.isToggle()) {
										nextEffect.getSkill().setSkillConflict(true);
									}
								}
								return true;
							} else {
								effect.endEffect();
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean priorityStigmaEffect(Effect nextEffect) {
		for (Effect effect : abnormalEffectMap.values()) {
			if (effect.getSkillTemplate().getStigmaType().getId() < nextEffect.getSkillTemplate().getStigmaType().getId() &&
			    effect.getTargetSlot() == nextEffect.getTargetSlot() &&
				effect.getTargetSlotLevel() == nextEffect.getTargetSlotLevel()) {
				for (EffectTemplate et : effect.getEffectTemplates()) {
					if (et.getEffectid() == 0) {
						continue;
					} for (EffectTemplate et2: nextEffect.getEffectTemplates())	{
						if (et2.getEffectid() == 0) {
							continue;
						} if (et.getEffectid() == et2.getEffectid()) {
							effect.endEffect();
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean hasPhysicalStateEffect() {
		Iterator<Effect> effectIterator = this.abnormalEffectMap.values().iterator();
		while (effectIterator.hasNext()) {
			Effect localEffect = effectIterator.next();
			if (localEffect.isPhysicalState()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasMagicalStateEffect() {
		Iterator<Effect> effectIterator = this.abnormalEffectMap.values().iterator();
		while (effectIterator.hasNext()) {
			Effect localEffect = effectIterator.next();
			if (localEffect.isMagicalState()) {
				return true;
			}
		}
		return false;
	}
	
	public Collection<Effect> getAbnormalEffectsToTargetSlot(final int slot) {
		return Collections2.filter(abnormalEffectMap.values(), new Predicate<Effect>() {

			@Override
			public boolean apply(Effect effect) {
				return effect.getTargetSlotEnum().getId() == slot;
			}
		});
	}

}