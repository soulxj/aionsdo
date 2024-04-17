/*
 * This file is part of Eleanor project
 *
 * This is proprietary software. See the EULA file distributed with
 * this project for additional information regarding copyright ownership.
 *
 * Copyright (c) 2011-2013, Eleanor Team. All rights reserved.
 */
package com.eleanor.utils;


import com.aionemu.gameserver.geoEngine.math.FastMath;
import com.aionemu.gameserver.geoEngine.math.Vector2f;
import com.aionemu.gameserver.geoEngine.math.Vector3f;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * Collection of 2d/3d geometry math utils
 *
 * @author MetaWind
 */
public class GeomUtil {

    /**
     * Calculate point moved from source by selected angle to target distance
     *
     * @param source   Source point
     * @param angle    Source angle (!degrees, NOT radians!)
     * @param distance Target distance
     * @return Moved point
     */
    public static Vector2f getNextPoint2D(Vector2f source, float angle, float distance) {

        double x = source.x + distance * Math.cos(angle * Math.PI / 180);
        double y = source.y + distance * Math.sin(angle * Math.PI / 180);

        return new Vector2f((float) x, (float) y);
    }

    /**
     * Calculate point moved from source by selected vector to target distance
     *
     * @param sX       Source point X
     * @param sY       Source point Y
     * @param vecX     Direction unit vector X
     * @param vecY     Direction unit vector Y
     * @param distance target distance
     */
    public static Vector2f getNextPoint2D(float sX, float sY, float vecX, float vecY, float distance) {
        return new Vector2f((float) sX + (vecX * distance), sY + (vecY * distance));
    }


    /**
     * Calculate direction from source point to target as unit vector
     *
     * @param from Source point
     * @param to   Target point
     * @return Direction unit vector
     */
    public static Vector3f getDirection3D(Vector3f from, Vector3f to) {
        Vector3f direction = to.subtract(from);
        return direction.normalizeLocal();
    }

    /**
     * Calculate point moved from source by source direction to target distance
     *
     * @param source    Source point
     * @param direction Direction as unit vector
     * @param distance  Target distance
     * @return Moved point
     */
    public static Vector3f getNextPoint3D(Vector3f source, Vector3f direction, float distance) {
        //assert direction.isUnitVector();
        return source.add(direction.mult(distance));
    }

    /**
     * Get distance from source to target
     *
     * @param source Source point as vector3f
     * @param x2     Target X
     * @param y2     Target Y
     * @param z2     Target Z
     * @return distance between two points
     */
    public static float getDistance3D(Vector3f source, float x2, float y2, float z2) {
        return getDistance3D(source.x, source.y, source.z, x2, y2, z2);
    }

    /**
     * Get distance from source to target
     *
     * @param x1 Source X
     * @param y1 Source Y
     * @param z1 Source Z
     * @param x2 Target X
     * @param y2 Target Y
     * @param z2 Target Z
     * @return distance between two points
     */
    public static float getDistance3D(float x1, float y1, float z1, float x2, float y2, float z2) {

        double dx = x1 - x2;
        double dy = y1 - y2;
        double dz = z1 - z2;

        return FastMath.sqrt((float) (dx * dx + dy * dy + dz * dz));
    }
}
