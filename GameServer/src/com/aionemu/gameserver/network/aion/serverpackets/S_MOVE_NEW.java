/**
 * This file is part of aion-emu <aion-emu.com>.
 * <p>
 * aion-emu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * aion-emu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.controllers.movement.MoveController;
import com.aionemu.gameserver.controllers.movement.MovementMask;
import com.aionemu.gameserver.controllers.movement.PlayableMoveController;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_MOVE_NEW extends AionServerPacket {
    private Creature creature;


    private int _objectId;
    /**
     * Movement begin point coordinates
     */
    private float _sX, _sY, _sZ;
    /**
     * Movement target point coordinates
     */
    private float _tX, _tY, _tZ;
    /**
     * Movement direction
     */
    private byte _heading;
    /**
     * Movement type flag
     */
    private byte _moveTypeFlag;


    public S_MOVE_NEW(Creature creature) {
        this.creature = creature;
    }

    /**
     * Movement notice message constructor
     *
     * @param objectId Movement event owner
     * @param sX       Movement begin point X coordinate
     * @param sY       Movement begin point Y coordinate
     * @param sZ       Movement begin point Z coordinate
     * @param tX       Movement end point X coordinate
     * @param tY       Movement end point Y coordinate
     * @param tZ       Movement end point Z coordinate
     * @param heading  Movement direction
     * @param flag     Movement type flag
     */
    public S_MOVE_NEW(
            int objectId,
            float sX, float sY, float sZ,
            float tX, float tY, float tZ,
            byte heading,
            byte flag) {

        creature = null;

        _objectId = objectId;

        _sX = sX;
        _sY = sY;
        _sZ = sZ;

        _tX = tX;
        _tY = tY;
        _tZ = tZ;

        _heading = heading;
        _moveTypeFlag = flag;
    }

    @Override
    protected void writeImpl(AionConnection client) {

        if (creature == null) {
            writeD(_objectId);
            writeF(_sX);
            writeF(_sY);
            writeF(_sZ);
            writeC(_heading);

            writeC(_moveTypeFlag);
            if ((_moveTypeFlag & MovementMask.STARTMOVE) == MovementMask.STARTMOVE ||
                    (_moveTypeFlag & MovementMask.NPC_STARTMOVE) == MovementMask.NPC_STARTMOVE) {
                writeF(_tX);
                writeF(_tY);
                writeF(_tZ);
            }
            return;
        }

        MoveController moveData = creature.getMoveController();
        writeD(creature.getObjectId());
        writeF(creature.getX());
        writeF(creature.getY());
        writeF(creature.getZ());
        writeC(creature.getHeading());
        writeC(moveData.getMovementMask());
        if (moveData instanceof PlayableMoveController) {
            PlayableMoveController<?> playermoveData = (PlayableMoveController<?>) moveData;
            if ((moveData.getMovementMask() & MovementMask.STARTMOVE) == MovementMask.STARTMOVE) {
                if ((moveData.getMovementMask() & MovementMask.MOUSE) == 0) {
                    writeF(playermoveData.vectorX);
                    writeF(playermoveData.vectorY);
                    writeF(playermoveData.vectorZ);
                } else {
                    writeF(moveData.getTargetX2());
                    writeF(moveData.getTargetY2());
                    writeF(moveData.getTargetZ2());
                }
            }
            if ((moveData.getMovementMask() & MovementMask.GLIDE) == MovementMask.GLIDE) {
                writeC(playermoveData.glideFlag);
            }
            if ((moveData.getMovementMask() & MovementMask.VEHICLE) == MovementMask.VEHICLE) {
                writeD(playermoveData.unk1);
                writeD(playermoveData.unk2);
                writeF(playermoveData.vectorX);
                writeF(playermoveData.vectorY);
                writeF(playermoveData.vectorZ);
            }
            if ((moveData.getMovementMask() & MovementMask.STARTMOVE_NEW) == MovementMask.STARTMOVE_NEW) {
                if ((moveData.getMovementMask() & MovementMask.MOUSE) == 0) {
                    writeF(playermoveData.vectorX);
                    writeF(playermoveData.vectorY);
                    writeF(playermoveData.vectorZ);
                    writeC(0);
                } else {
                    writeF(moveData.getTargetX2());
                    writeF(moveData.getTargetY2());
                    writeF(moveData.getTargetZ2());
                    writeC(0);
                }
            }
        } else {
            if ((moveData.getMovementMask() & MovementMask.STARTMOVE) == MovementMask.STARTMOVE) {
                writeF(moveData.getTargetX2());
                writeF(moveData.getTargetY2());
                writeF(moveData.getTargetZ2());
            }
        }
    }
}