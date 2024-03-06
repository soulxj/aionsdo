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
package com.aionemu.chatserver.network.aion.clientpackets;

import org.jboss.netty.buffer.ChannelBuffer;

import com.aionemu.chatserver.network.aion.AbstractClientPacket;
import com.aionemu.chatserver.network.aion.serverpackets.SM_CHAT_INI;
import com.aionemu.chatserver.network.netty.handler.ClientChannelHandler;
import com.aionemu.chatserver.service.ChatService;

/**
 * @author ginho1
 */
public class CM_CHAT_INI extends AbstractClientPacket {

    public CM_CHAT_INI(ChannelBuffer channelBuffer, ClientChannelHandler clientChannelHandler, ChatService chatService) {
        super(channelBuffer, clientChannelHandler, 0x30);
    }

    @Override
    protected void readImpl() {
        readC();
        readH();
        readD();
        readD();
        readD();
    }

    @Override
    protected void runImpl() {
        clientChannelHandler.getChatClient();
        clientChannelHandler.sendPacket(new SM_CHAT_INI());
    }
}
