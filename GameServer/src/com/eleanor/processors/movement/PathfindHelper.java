/*
 * This file is part of Eleanor project
 *
 * This is proprietary software. See the EULA file distributed with
 * this project for additional information regarding copyright ownership.
 *
 * Copyright (c) 2011-2013, Eleanor Team. All rights reserved.
 */
package com.eleanor.processors.movement;

import com.aionemu.gameserver.geoEngine.math.Vector2f;
import com.aionemu.gameserver.geoEngine.math.Vector3f;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.world.geo.GeoService;
import com.eleanor.utils.GeomUtil;

/**
 * Calculator of pathfinding mob's steps
 *
 * @author MetaWind, Alexsis
 */
public class PathfindHelper {

    /* Owner visible angle in degrees */
    private static final int VISIBLE_ANGLE = 180; //180

    /* Pathfinding single step angle to search accepible movement point */
    private static final int PATHFIND_ANGLE_STEP = 15; //15

    /**
     * Select next movement step to follow target point
     *
     * @param source Source creature
     * @param target Target creature
     * @return Selected point or NULL if target can't be reached
     */
    public static Vector3f selectStep(Creature source, Vector3f target) {
        int mapId = source.getPosition().getMapId();
        int instanceId = source.getPosition().getInstanceId();

        float zOffset = source.getObjectTemplate().getBoundRadius().getUpper() / 2;

        if (zOffset > 2.2)
            zOffset = 2.2f;

        float newZOffset = Math.max(0.6f, source.getObjectTemplate().getBoundRadius().getUpper() * 0.7f);

        if (source.getTarget() instanceof Player)
            newZOffset = 1.5f;

        Vector3f sourcePoint = new Vector3f(source.getX(), source.getY(), source.getZ());
        Vector3f targetPoint = target.clone();

        //real source and target movement points for ray trace check
        final double futureDistance = GeomUtil.getDistance3D(sourcePoint.x, sourcePoint.y, sourcePoint.z, targetPoint.x, targetPoint.y, targetPoint.z);
        final int offset = VISIBLE_ANGLE / 2;

        Vector3f closetsPoint = null;
        double minimalDistance = Short.MAX_VALUE;

        for (int i = 0; i < 16; i++) {
            Vector3f rotated = Rotate(source,
                    sourcePoint.x, sourcePoint.y,
                    targetPoint.x, targetPoint.y,
                    futureDistance, (float) (i * PATHFIND_ANGLE_STEP - offset), targetPoint.z);

            if (targetPoint.z - rotated.z > source.getObjectTemplate().getBoundRadius().getUpper() || rotated.z == 0)
                continue;

            double newRotatedDistance = MathUtil.getDistance(
                    sourcePoint.x, sourcePoint.y, sourcePoint.z,
                    rotated.x, rotated.y, rotated.z);

            boolean canPassTemp = GeoService.getInstance().canPass(
                    mapId,
                    sourcePoint.x, sourcePoint.y, sourcePoint.z + zOffset,
                    rotated.x, rotated.y, rotated.z + newZOffset,
                    (float) (newRotatedDistance),
                    instanceId);

            if (!canPassTemp)
                continue;

            double canPassDistance = MathUtil.getDistance(
                    targetPoint.x, targetPoint.y, targetPoint.z,
                    rotated.x, rotated.y, rotated.z);

            if (minimalDistance > canPassDistance) {
                minimalDistance = canPassDistance;
                closetsPoint = rotated;
            } else {
                break;
            }
        }

        return closetsPoint;
    }

    /**
     * Select next movement point to follow target
     *
     * @param source Source creature
     * @param target Target creature
     * @return Selected point or NULL if target can't be reached
     */
    public static Vector3f selectFollowStep(Creature source, VisibleObject target) {

        int mapId = source.getPosition().getMapId();
        int instanceId = source.getPosition().getInstanceId();

        if (target.getPosition().getMapId() != mapId || target.getPosition().getInstanceId() != instanceId)
            return null;

        Vector3f point = new Vector3f(target.getX(), target.getY(), target.getZ());

        assert point.x != 0 && point.y != 0;

        return selectStep(source, point);
    }


    private static Vector3f Rotate(Creature owner, float cx, float cy, float x1, float y1, double radius, float degrees, float defaultZ) {
        double beginDeg = Math.toDegrees(Math.atan2(y1 - cy, x1 - cx));
        degrees += beginDeg;

        double x = cx + radius * Math.cos(degrees * Math.PI / 180);
        double y = cy + radius * Math.sin(degrees * Math.PI / 180);

        double z = GeoService.getInstance().getZ(owner.getWorldId(), (float) x, (float) y, defaultZ, 0, owner.getInstanceId());

        return new Vector3f((float) x, (float) y, (float) z);
    }

    /**
     * Select next random point for creature movement
     *
     * @param source   Source creature
     * @param minRange Minimal walk range
     * @param maxRange Maximal walk range
     * @return
     */
    public static Vector3f getRandomPoint(Creature source, float minRange, float maxRange) {

        Vector3f origin = new Vector3f(source.getX(), source.getY(), source.getZ());

        assert minRange > 0 && maxRange > minRange;

        final int SearchAngle = 360;
        final int AngleStep = 60;

        int randDist = (int) (Math.random() * maxRange + minRange);
        int randAngle = (int) (Math.random() * SearchAngle);

        for (int i = 0; i < SearchAngle; i += AngleStep) {

            Vector2f rotated2D =
                    GeomUtil.getNextPoint2D(new Vector2f(origin.x, origin.y), (float) randAngle + i, randDist);

            Vector3f rotatedPoint = new Vector3f(rotated2D.x, rotated2D.y, GeoService.getInstance().getZ(source.getWorldId(), rotated2D.x, rotated2D.y));

            if (Math.abs(origin.z - rotatedPoint.z) > 2)
                continue;

            if (!GeoService.getInstance().canPass(source.getWorldId(), origin.x, origin.y, origin.z,
                    rotatedPoint.x, rotatedPoint.y, rotatedPoint.z, (randDist + 0.1f), 0))
                continue;

            Vector3f rotated = new Vector3f(
                    rotatedPoint.x,
                    rotatedPoint.y,
                    rotatedPoint.z);

            if (rotated.distance(origin) < minRange)
                continue;

            return rotated;
        }

        return null;
    }

}
