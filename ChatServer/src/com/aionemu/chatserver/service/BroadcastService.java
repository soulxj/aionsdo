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
package com.aionemu.chatserver.service;

import com.aionemu.chatserver.model.ChatClient;
import com.aionemu.chatserver.model.message.Message;
import com.aionemu.chatserver.network.aion.serverpackets.SM_CHANNEL_MESSAGE;
import com.aionemu.chatserver.network.netty.handler.ClientChannelHandler;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import java.util.Map;

/**
 * @author ATracer
 */
public class BroadcastService {

    private static BroadcastService instance = new BroadcastService();

    public static BroadcastService getInstance() {
        return instance;
    }
    private Map<Integer, ChatClient> clients = new ConcurrentHashMap<>();

    /**
     * @param client
     */
    public void addClient(ChatClient client) {
        clients.put(client.getClientId(), client);
    }

    /**
     * @param client
     */
    public void removeClient(ChatClient client) {
        clients.remove(client.getClientId());
    }

    /**
     * @param message
     */
    public void broadcastMessage(Message message) {
        for (ChatClient client : clients.values()) {
            if (client.isInChannel(message.getChannel())) {
                sendMessage(client, message);
            }
        }
    }

    /**
     * @param chatClient
     * @param message
     */
    public void sendMessage(ChatClient chatClient, Message message) {
        ClientChannelHandler cch = chatClient.getChannelHandler();
        cch.sendPacket(new SM_CHANNEL_MESSAGE(message));
    }
}
