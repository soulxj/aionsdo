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
package com.aionemu.chatserver.common.netty;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

/**
 * @author ATracer
 */
public abstract class AbstractPacketHandler {

    private static final Logger log = LoggerFactory.getLogger(AbstractPacketHandler.class);

    /**
     * Unknown packet
     *
     * @param id
     * @param state
     */
    protected void unknownPacket(int id, String state) {
        log.warn(String.format("Unknown packet received from Game server: 0x%02X state=%s", id, state));
    }
}
