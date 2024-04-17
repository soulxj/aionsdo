/*
 * This file is part of Eleanor project
 *
 * This is proprietary software. See the EULA file distributed with
 * this project for additional information regarding copyright ownership.
 *
 * Copyright (c) 2011-2013, Eleanor Team. All rights reserved.
 */
package com.eleanor.processors.movement.motor;

import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.controllers.movement.MovementMask;
import com.aionemu.gameserver.geoEngine.math.Vector3f;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.stats.calc.Stat2;
import com.eleanor.processors.movement.MovementProcessor;

/**
 * Base abstraction of all movement motors
 *
 * @author MetaWind
 */
public abstract class AMovementMotor {

    /* Motor owner */
    final Npc _owner;

    /* Parent movement processor */
    final MovementProcessor _processor;

    /* Current movement target position */
    Vector3f _targetPosition;

    /* Current movement mask */
    byte _targetMask;

    /**
     * Base constructor
     *
     * @param owner     Creature owner
     * @param processor Parent movement processor
     */
    AMovementMotor(Npc owner, MovementProcessor processor) {
        _owner = owner;
        _processor = processor;
    }

    /* Start this motor */
    public abstract void start();

    /* Stop this motor */
    public abstract void stop();

    /**
     * @return Current movement target coordinates
     */
    public Vector3f getCurrentTarget() {
        return _targetPosition;
    }

    /**
     * @return Current movement mask
     */
    public byte getMovementMask() {
        return _targetMask;
    }

    /**
     * Recalculate current movement heading and movement mask
     *
     * @return
     */
    void recalculateMovementParams() {

        byte oldHeading = _owner.getHeading();
        /* Current movement heading */
        byte _targetHeading = (byte) (Math.toDegrees(Math.atan2(
                _targetPosition.getY() - _owner.getY(),
                _targetPosition.getX() - _owner.getX())) / 3);

        _targetMask = MovementMask.IMMEDIATE;
        if (oldHeading != _targetHeading)
            _targetMask |= MovementMask.NPC_STARTMOVE;

        final Stat2 stat = _owner.getGameStats().getMovementSpeed();
        if (_owner.isInState(CreatureState.WEAPON_EQUIPPED)) {
            _targetMask |= stat.getBonus() < 0 ? MovementMask.NPC_RUN_FAST : MovementMask.NPC_RUN_SLOW;
        } else if (_owner.isInState(CreatureState.WALKING) || _owner.isInState(CreatureState.ACTIVE)) {
            _targetMask |= stat.getBonus() < 0 ? MovementMask.NPC_WALK_FAST : MovementMask.NPC_WALK_SLOW;
        }
        if (_owner.isFlying())
            _targetMask |= MovementMask.GLIDE;
        if (_owner.getAi2().getState() == AIState.RETURNING) {
            _targetMask |= MovementMask.NPC_RUN_FAST;
        }
    }
}
