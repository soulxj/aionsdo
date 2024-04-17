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
import com.aionemu.gameserver.ai2.AISubState;
import com.aionemu.gameserver.controllers.movement.MovementMask;
import com.aionemu.gameserver.geoEngine.math.Vector3f;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.network.aion.serverpackets.S_MOVE_NEW;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.geo.GeoService;
import com.eleanor.processors.movement.MovementProcessor;
import com.eleanor.processors.movement.PathfindHelper;
import com.eleanor.utils.GeomUtil;

import java.util.concurrent.ScheduledFuture;

/**
 * Motor, that move owner object to other target object
 * This motor use pathfinding for movements validation
 */
public class FollowMotor extends AMovementMotor {

    /* Revalidate target position time in milliseconds */
    private static final int TARGET_REVALIDATE_TIME = 200;

    /* Revalidate pathfind position time in milliseconds */
    private static long pathfindRevalidationTime;

    /* Object to follow to */
    public VisibleObject _target;

    /* Current movement task */
    private ScheduledFuture<?> _task;

    /* Last movement milliseconds */
    private long _lastMoveMs;

    /* Last movement owner point */
    private Vector3f _lastMovePoint;

    /* Current movement heading */
    private byte new_targetHeading;

    /**
     * Default constructor
     *
     * @param parentProcessor Parent movement processor
     * @param owner           Motor owner
     * @param target
     */
    public FollowMotor(
            MovementProcessor parentProcessor,
            Npc owner,
            VisibleObject target) {
        super(owner, parentProcessor);

        _target = target;
    }

    @Override
    public void start() {
        assert _task == null;
        update();
    }

    @Override
    public void stop() {
        if (_task != null)
            _task.cancel(false);

        pathfindRevalidationTime = 0;
        _lastMoveMs = 0;
        _lastMovePoint = null;
        _target = null;
    }

    /**
     * Select new movement point and begin move
     *
     * @return
     */
    public boolean update() {

        VisibleObject target = _target;

        if (target == null || _task != null && _task.isCancelled() || _owner.getLifeStats().isAlreadyDead() || _owner.getAi2().getState() == AIState.DIED)
            return false;

        _lastMovePoint = new Vector3f(_owner.getX(), _owner.getY(), _owner.getZ());
        boolean canPass = GeoService.getInstance().canPass(_owner, target);
        if (canMove() && !canPass && pathfindRevalidationTime < System.currentTimeMillis()) {
            _targetPosition = PathfindHelper.selectFollowStep(
                    _owner,
                    target);
        } else if (canMove() && canPass) {
            float newZ = GeoService.getInstance().getZ(_owner.getWorldId(), target.getX(), target.getY(), target.getZ(), 0, _owner.getInstanceId());
            Vector3f getTargetPos = new Vector3f(target.getX(), target.getY(), newZ);

            float range = _owner.getGameStats().getAttackRange().getCurrent() / 1000f;
            //fix for rangers
            if (range > 10)
                range = 2;

            if (_lastMovePoint == null)
                _lastMovePoint = new Vector3f(_owner.getX(), _owner.getY(), _owner.getZ());

            double distance = GeomUtil.getDistance3D(_lastMovePoint,
                    getTargetPos.x, getTargetPos.y, getTargetPos.z) -
                    Math.min(range,
                            _owner.getCollision());

            Vector3f dir = GeomUtil.getDirection3D(_lastMovePoint, getTargetPos);
            _targetPosition = GeomUtil.getNextPoint3D(_lastMovePoint, dir, (float) distance);

        } else if (pathfindRevalidationTime < System.currentTimeMillis())
            _targetPosition = null;

        long movementTime;
        if (_targetPosition != null) {
            //Calculate distance and time for move to point
            double distance = GeomUtil.getDistance3D(
                    _owner.getX(), _owner.getY(), _owner.getZ(),
                    _targetPosition.x, _targetPosition.y, _targetPosition.z);

            final float speed = _owner.getGameStats().getMovementSpeedFloat();
            movementTime = (long) ((distance / speed) * 1000);
            pathfindRevalidationTime = System.currentTimeMillis() + movementTime;

            recalculateMovementParams();
            new_targetHeading =  (byte) (Math.toDegrees(Math.atan2(_targetPosition.y - _owner.getY(), _targetPosition.x - _owner.getX())) / 3);

            PacketSendUtility.broadcastPacket(_owner, new S_MOVE_NEW(
                    _owner.getObjectId(),
                    _owner.getX(), _owner.getY(), _owner.getZ(),
                    _targetPosition.x, _targetPosition.y, _targetPosition.z,
                    new_targetHeading,
                    _targetMask
            ));

            _lastMoveMs = System.currentTimeMillis();
        }

        _task = _processor.schedule(new Runnable() {
            @Override
            public void run() {
                if (_targetPosition != null) {

                    Vector3f lastMove = _lastMovePoint;
                    Vector3f targetMove = new Vector3f(_targetPosition.x, _targetPosition.y, _targetPosition.z);

                    float speed = _owner.getGameStats().getMovementSpeedFloat();
                    long time = System.currentTimeMillis() - _lastMoveMs;
                    float distPassed = speed * (time / 1000f);
                    if (lastMove == null)
                        lastMove = new Vector3f(_owner.getX(), _owner.getY(), _owner.getZ());

                    float maxDist = lastMove.distance(targetMove);
                    if (distPassed <= 0)
                        return;

                    if (distPassed > maxDist)
                        distPassed = maxDist;

                    Vector3f dir = GeomUtil.getDirection3D(lastMove, targetMove);
                    Vector3f position = GeomUtil.getNextPoint3D(lastMove, dir, distPassed);

                    if (_owner.getWorldId() != 300230000) {
                        float newZ = GeoService.getInstance().getZ(_owner.getWorldId(), position.x, position.y, position.z, 0, _owner.getInstanceId());
                        //ground fix
                        if (lastMove.getZ() < newZ & Math.abs(lastMove.getZ() - newZ) > 1)
                            position.z = newZ + _owner.getObjectTemplate().getBoundRadius().getUpper() - _owner.getObjectTemplate().getHeight();
                        else
                            position.z = newZ;
                    }

                    World.getInstance().updatePosition(_owner,
                            position.x,
                            position.y,
                            position.z, new_targetHeading, false);
                } else {
                    PacketSendUtility.broadcastPacket(_owner, new S_MOVE_NEW(
                            _owner.getObjectId(),
                            _owner.getX(), _owner.getY(), _owner.getZ(),
                            _owner.getX(), _owner.getY(), _owner.getZ(),
                            _owner.getHeading(),
                            MovementMask.IMMEDIATE
                    ));
                    pathfindRevalidationTime = 0;
                }

                _processor.schedule(new Runnable() {
                    @Override
                    public void run() {
                        update();
                    }
                }, 0);
            }
        }, TARGET_REVALIDATE_TIME);

        return true;
    }

    private boolean canMove() {
        return !_owner.getEffectController().isUnderFear() && _owner.canPerformMove() && !(_owner.getAi2().getSubState() == AISubState.CAST);
    }
}

