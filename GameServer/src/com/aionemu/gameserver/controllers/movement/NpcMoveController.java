/*
 * This file is part of aion_gates 
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aiongates is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aiongates.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.controllers.movement;

import com.eleanor.Global;
import com.eleanor.processors.movement.motor.FollowMotor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import com.aionemu.gameserver.ai2.AI2Logger;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.AISubState;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.handler.TargetEventHandler;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.configs.main.GeoDataConfig;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.geometry.Point3D;
import com.aionemu.gameserver.model.stats.calc.Stat2;
import com.aionemu.gameserver.model.templates.walker.RouteStep;
import com.aionemu.gameserver.model.templates.zone.Point2D;
import com.aionemu.gameserver.network.aion.serverpackets.S_MOVE_NEW;
import com.aionemu.gameserver.spawnengine.WalkerGroup;
import com.aionemu.gameserver.taskmanager.tasks.MoveTaskManager;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.collections.LastUsedCache;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.geo.GeoService;

/**
 * @author ATracer
 */
public class NpcMoveController extends CreatureMoveController<Npc> {

    private static final Logger log = LoggerFactory.getLogger(NpcMoveController.class);
    public static final float MOVE_CHECK_OFFSET = 0.1f;
    private static final float MOVE_OFFSET = 0.05f;
    private Destination destination = Destination.TARGET_OBJECT;
    private float pointX;
    private float pointY;
    private float pointZ;
    private LastUsedCache<Byte, Point3D> lastSteps = null;
    private byte stepSequenceNr = 0;
    private float offset = 0.1f;
    // walk related
    List<RouteStep> currentRoute;
    int currentPoint;
    int walkPause;
    private float cachedTargetZ;
    private FollowMotor _followMotor;
    public NpcMoveController(Npc owner) {
        super(owner);
    }

    private static enum Destination {

        TARGET_OBJECT,
        POINT;
    }

    /**
     * Move to current target
     */
    public void moveToTargetObject() {
        if (started.compareAndSet(false, true)) {
            if (owner.getAi2().isLogging()) {
                AI2Logger.moveinfo(owner, "MC: moveToTarget started");
            }
            destination = Destination.TARGET_OBJECT;
            updateLastMove();
            MoveTaskManager.getInstance().addCreature(owner);
        }
    }

    public void moveToPoint(float x, float y, float z) {
        if (started.compareAndSet(false, true)) {
            if (owner.getAi2().isLogging()) {
                AI2Logger.moveinfo(owner, "MC: moveToPoint started");
            }
            destination = Destination.POINT;
            pointX = x;
            pointY = y;
            pointZ = z;
            updateLastMove();
            MoveTaskManager.getInstance().addCreature(owner);
        }
    }

    public void moveToNextPoint() {
        if (started.compareAndSet(false, true)) {
            if (owner.getAi2().isLogging()) {
                AI2Logger.moveinfo(owner, "MC: moveToNextPoint started");
            }
            destination = Destination.POINT;
            updateLastMove();
            MoveTaskManager.getInstance().addCreature(owner);
        }
    }

    /**
     * @return if destination reached
     */
    @Override
    public void moveToDestination() {
        if (owner.getAi2().isLogging()) {
            AI2Logger.moveinfo(owner, "moveToDestination destination: " + destination);
        }
        if (NpcActions.isAlreadyDead(owner)) {
            abortMove();
            return;
        }
        if (!owner.canPerformMove() || (owner.getAi2().getSubState() == AISubState.CAST)) {
            if (owner.getAi2().isLogging()) {
                AI2Logger.moveinfo(owner, "moveToDestination can't perform move");
            }
            if (started.compareAndSet(true, false)) {
                setAndSendStopMove(owner);
            }
            updateLastMove();
            return;
        } else if (started.compareAndSet(false, true)) {
            movementMask = MovementMask.NPC_STARTMOVE;
            PacketSendUtility.broadcastPacket(owner, new S_MOVE_NEW(owner));
        }

        if (!started.get()) {
            if (owner.getAi2().isLogging()) {
                AI2Logger.moveinfo(owner, "moveToDestination not started");
            }
        }

        switch (destination) {
            case TARGET_OBJECT:
                Npc npc = owner;
                VisibleObject target = owner.getTarget();// todo no target
                if (target == null) {
                    cancelFollow();
                    return;
                }
                if (!(target instanceof Creature)) {
                    cancelFollow();
                    return;
                }
//                if (MathUtil.getDistance(target, pointX, pointY, pointZ) > MOVE_CHECK_OFFSET) {
//                    Creature creature = (Creature) target;
//                    offset = npc.getController().getAttackDistanceToTarget();
//                    pointX = target.getX();
//                    pointY = target.getY();
//                    pointZ = getTargetZ(npc, creature);
//                }
//                moveToLocation(pointX, pointY, pointZ, offset);
                if (owner.getAi2().getState() == AIState.FOLLOWING) {
                    cancelFollow();
                    offset = 0.1f;
                    moveToLocation(target.getX(), target.getY(), target.getZ(), offset);
                } else {
                    applyFollow(target);
                }
                break;
            case POINT:
                cancelFollow();
                offset = 0.1f;
                moveToLocation(pointX, pointY, pointZ, offset);
                break;
        }
        updateLastMove();
    }

    /**
     * @param npc
     * @param creature
     * @return
     */
    private float getTargetZ(Npc npc, Creature creature) {
        float targetZ = creature.getZ();
        if (GeoDataConfig.GEO_NPC_MOVE && creature.isInFlyingState() && !npc.isInFlyingState()) {
            if (npc.getGameStats().checkGeoNeedUpdate()) {
                this.cachedTargetZ = GeoService.getInstance().getZ(creature);
            }
            targetZ = this.cachedTargetZ;
        }
        return targetZ;
    }

    /**
     * @param targetX
     * @param targetY
     * @param targetZ
     * @param offset
     * @return
     */
    protected void moveToLocation(float targetX, float targetY, float targetZ, float offset) {
        boolean directionChanged;
        float ownerX = this.owner.getX();
        float ownerY = this.owner.getY();
        float ownerZ = this.owner.getZ();
        directionChanged = targetX != this.targetDestX || targetY != this.targetDestY || targetZ != this.targetDestZ;
        if (directionChanged) {
            this.heading = (byte)(Math.toDegrees(Math.atan2(targetY - ownerY, targetX - ownerX)) / 3.0);
        }
        if (this.owner.getAi2().isLogging()) {
            AI2Logger.moveinfo(this.owner, "OLD targetDestX: " + this.targetDestX + " targetDestY: " + this.targetDestY + " targetDestZ " + this.targetDestZ);
        }
        if (targetX == 0.0f && targetY == 0.0f) {
            targetX = this.owner.getSpawn().getX();
            targetY = this.owner.getSpawn().getY();
            targetZ = this.owner.getSpawn().getZ();
        }
        this.targetDestX = targetX;
        this.targetDestY = targetY;
        this.targetDestZ = targetZ;
        if (this.owner.getAi2().isLogging()) {
            AI2Logger.moveinfo(this.owner, "ownerX=" + ownerX + " ownerY=" + ownerY + " ownerZ=" + ownerZ);
            AI2Logger.moveinfo(this.owner, "targetDestX: " + this.targetDestX + " targetDestY: " + this.targetDestY + " targetDestZ " + this.targetDestZ);
        }
        float currentSpeed = this.owner.getGameStats().getMovementSpeedFloat();
        float futureDistPassed = currentSpeed * (float)(System.currentTimeMillis() - this.lastMoveUpdate) / 1000.0f;
        float dist = (float)MathUtil.getDistance(ownerX, ownerY, ownerZ, targetX, targetY, targetZ);
        if (this.owner.getAi2().isLogging()) {
            AI2Logger.moveinfo(this.owner, "futureDist: " + futureDistPassed + " dist: " + dist);
        } if (dist == 0.0f) {
            if (this.owner.getAi2().getState() == AIState.RETURNING) {
                if (this.owner.getAi2().isLogging()) {
                    AI2Logger.moveinfo(this.owner, "State RETURNING: abort move");
                }
                TargetEventHandler.onTargetReached((NpcAI2) this.owner.getAi2());
            }
            return;
        } if (futureDistPassed > dist) {
            futureDistPassed = dist;
        }
        float distFraction = futureDistPassed / dist;
        float newX = (this.targetDestX - ownerX) * distFraction + ownerX;
        float newY = (this.targetDestY - ownerY) * distFraction + ownerY;
        float newZ = (this.targetDestZ - ownerZ) * distFraction + ownerZ;
        if (ownerX == newX && ownerY == newY && this.owner.getSpawn().getRandomWalk() > 0) {
            return;
        } if (GeoDataConfig.GEO_NPC_MOVE && GeoDataConfig.GEO_ENABLE && this.owner.getAi2().getSubState() != AISubState.WALK_PATH && this.owner.getAi2().getState() != AIState.RETURNING && this.owner.getGameStats().getLastGeoZUpdate() < System.currentTimeMillis()) {
            if (this.owner.getSpawn().getX() != this.targetDestX || this.owner.getSpawn().getY() != this.targetDestY || this.owner.getSpawn().getZ() != this.targetDestZ) {
                float geoZ = GeoService.getInstance().getZ(this.owner.getWorldId(), newX, newY, newZ, - 0.1f, this.owner.getInstanceId());
                if (Math.abs(newZ - geoZ) > - 0.1f) {
                    directionChanged = true;
                }
                newZ = geoZ + this.owner.getObjectTemplate().getBoundRadius().getUpper() - this.owner.getObjectTemplate().getHeight();
            }
            this.owner.getGameStats().setLastGeoZUpdate(System.currentTimeMillis() + 500);
        } if (this.owner.getAi2().isLogging()) {
            AI2Logger.moveinfo(this.owner, "newX=" + newX + " newY=" + newY + " newZ=" + newZ + " mask=" + this.movementMask);
        }
        World.getInstance().updatePosition(this.owner, newX, newY, newZ, this.heading, false);
        byte newMask = this.getMoveMask(directionChanged);
        if (this.movementMask != newMask) {
            if (this.owner.getAi2().isLogging()) {
                AI2Logger.moveinfo(this.owner, "oldMask=" + this.movementMask + " newMask=" + newMask);
            }
            this.movementMask = newMask;
            PacketSendUtility.broadcastPacket(this.owner, new S_MOVE_NEW(this.owner));
        }
    }

    private byte getMoveMask(boolean directionChanged) {
        if (directionChanged) {
            return MovementMask.NPC_STARTMOVE;
        } else if (owner.getAi2().getState() == AIState.RETURNING) {
            return MovementMask.NPC_RUN_FAST;
        } else if (owner.getAi2().getState() == AIState.FOLLOWING) {
            return MovementMask.NPC_WALK_SLOW;
        }
        byte mask = MovementMask.IMMEDIATE;
        final Stat2 stat = owner.getGameStats().getMovementSpeed();
        if (owner.isInState(CreatureState.WEAPON_EQUIPPED)) {
            mask = stat.getBonus() < 0 ? MovementMask.NPC_RUN_FAST : MovementMask.NPC_RUN_SLOW;
        } else if (owner.isInState(CreatureState.WALKING) || owner.isInState(CreatureState.ACTIVE)) {
            mask = stat.getBonus() < 0 ? MovementMask.NPC_WALK_FAST : MovementMask.NPC_WALK_SLOW;
        } if (owner.isFlying()) {
            mask |= MovementMask.GLIDE;
        }
        return mask;
    }

    @Override
    public void abortMove() {
        if (!this.started.get()) {
            return;
        }
        this.resetMove();
        this.setAndSendStopMove(this.owner);
    }

    /**
     * Initialize values to default ones
     */
    public void resetMove() {
        if (this.owner.getAi2().isLogging()) {
            AI2Logger.moveinfo(this.owner, "MC perform stop");
        }
        cancelFollow();
        this.started.set(false);
        this.targetDestX = 0.0f;
        this.targetDestY = 0.0f;
        this.targetDestZ = 0.0f;
        this.pointX = 0.0f;
        this.pointY = 0.0f;
        this.pointZ = 0.0f;
    }

    /**
     * Walker
     *
     * @param currentRoute
     */
    public void setCurrentRoute(List<RouteStep> currentRoute) {
        if (currentRoute == null) {
            AI2Logger.info(owner.getAi2(), String.format("MC: setCurrentRoute is setting route to null (NPC id: {})!!!", owner.getNpcId()));
        } else {
            this.currentRoute = currentRoute;
        }
        this.currentPoint = 0;
    }

    public void setRouteStep(RouteStep step, RouteStep prevStep) {
        Point2D dest = null;
        if (this.owner.getWalkerGroup() != null) {
            dest = WalkerGroup.getLinePoint(new Point2D(prevStep.getX(), prevStep.getY()), new Point2D(step.getX(), step.getY()), ((Npc)this.owner).getWalkerGroupShift());
            this.pointZ = prevStep.getZ();
            if (this.owner.getWalkerGroup().getPool() > 10 && GeoDataConfig.GEO_ENABLE && GeoDataConfig.GEO_NPC_MOVE) {
                float geoZ = Math.round(GeoService.getInstance().getZ(this.owner.getWorldId(), dest.getX(), dest.getY(), prevStep.getZ() - 0.1f, 0.1f, ((Npc)this.owner).getInstanceId()) * 10.0f) / 10;
                this.pointZ = geoZ - 0.1f;
            }
            this.owner.getWalkerGroup().setStep(this.owner, step.getRouteStep());
        } else {
            this.pointZ = step.getZ();
        }
        this.currentPoint = step.getRouteStep() - 1;
        this.pointX = dest == null ? step.getX() : dest.getX();
        this.pointY = dest == null ? step.getY() : dest.getY();
        this.destination = Destination.POINT;
        this.walkPause = step.getRestTime();
    }

    public int getCurrentPoint() {
        return currentPoint;
    }

    public boolean isReachedPoint() {
        return MathUtil.getDistance(this.owner.getX(), this.owner.getY(), this.owner.getZ(), this.pointX, this.pointY, this.pointZ) < (double)0.05f;
    }

    public void chooseNextStep() {
        int oldPoint = currentPoint;
        if (currentRoute == null) {
            WalkManager.stopWalking((NpcAI2) owner.getAi2());
            return;
        } if (currentPoint < (currentRoute.size() - 1)) {
            currentPoint++;
        } else {
            currentPoint = 0;
        }
        setRouteStep(currentRoute.get(currentPoint), currentRoute.get(oldPoint));
    }

    public int getWalkPause() {
        return walkPause;
    }

    public boolean isChangingDirection() {
        return currentPoint == 0;
    }

    @Override
    public final float getTargetX2() {
        return started.get() ? targetDestX : owner.getX();
    }

    @Override
    public final float getTargetY2() {
        return started.get() ? targetDestY : owner.getY();
    }

    @Override
    public final float getTargetZ2() {
        return started.get() ? targetDestZ : owner.getZ();
    }

    /**
     * @return
     */
    public boolean isFollowingTarget() {
        return destination == Destination.TARGET_OBJECT;
    }

    public void storeStep() {
        if (this.owner.getAi2().getState() == AIState.RETURNING) {
            return;
        } if (this.lastSteps == null) {
            this.lastSteps = new LastUsedCache(10);
        }
        Point3D currentStep = new Point3D(this.owner.getX(), this.owner.getY(), this.owner.getZ());
        if (this.owner.getAi2().isLogging()) {
            AI2Logger.moveinfo(this.owner, "store back step: X=" + this.owner.getX() + " Y=" + this.owner.getY() + " Z=" + this.owner.getZ());
        } if (this.stepSequenceNr == 0 || MathUtil.getDistance(this.lastSteps.get(this.stepSequenceNr), currentStep) >= 10.0) {
            this.stepSequenceNr = (byte)(this.stepSequenceNr + 1);
            this.lastSteps.put(this.stepSequenceNr, currentStep);
        }
    }

    public Point3D recallPreviousStep() {
        if (lastSteps == null) {
            lastSteps = new LastUsedCache<Byte, Point3D>(10);
        }
        Point3D result = stepSequenceNr == 0 ? null : lastSteps.get(stepSequenceNr--);
        if (result == null) {
            if (owner.getAi2().isLogging()) {
                AI2Logger.moveinfo(owner, "recall back step: spawn point");
            }
            targetDestX = owner.getSpawn().getX();
            targetDestY = owner.getSpawn().getY();
            targetDestZ = owner.getSpawn().getZ();
            result = new Point3D(targetDestX, targetDestY, targetDestZ);
        } else {
            if (owner.getAi2().isLogging()) {
                AI2Logger.moveinfo(owner, "recall back step: X=" + result.getX() + " Y=" + result.getY() + " Z=" + result.getZ());
            }
            targetDestX = result.getX();
            targetDestY = result.getY();
            targetDestZ = result.getZ();
        }
        return result;
    }

    public void clearBackSteps() {
        this.stepSequenceNr = 0;
        this.lastSteps = null;
        this.movementMask = MovementMask.IMMEDIATE;
    }

    private void applyFollow(VisibleObject target) {

        if (_followMotor != null && _followMotor._target == target)
            return;

        if (_followMotor != null) {
            _followMotor.stop();
        }

        _followMotor = new FollowMotor(Global.MovementProcessor, owner, target);
        _followMotor.start();
    }

    private void cancelFollow() {

        if (_followMotor != null) {
            _followMotor.stop();
            _followMotor = null;
        }
    }

}