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
package com.aionemu.chatserver.network.gameserver.serverpackets;

import com.aionemu.chatserver.configs.Config;
import com.aionemu.chatserver.network.gameserver.GsAuthResponse;
import com.aionemu.chatserver.network.gameserver.GsConnection;
import com.aionemu.chatserver.network.gameserver.GsServerPacket;

/**
 * @author ATracer
 */
public class SM_GS_AUTH_RESPONSE extends GsServerPacket {

    private GsAuthResponse response;

    public SM_GS_AUTH_RESPONSE(GsAuthResponse resp) {
        this.response = resp;
    }

    @Override
    protected void writeImpl(GsConnection con) {
        writeC(0);
        writeC(response.getResponseId());
        writeB(Config.CHAT_ADDRESS.getAddress().getAddress());
        writeH(Config.CHAT_ADDRESS.getPort());
    }
}
