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
package com.aionemu.gameserver.services;

import com.aionemu.commons.network.util.ThreadPoolManager;

import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.questEngine.model.*;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

import java.util.Iterator;

import java.util.concurrent.Future;

public class QuestUpdateService
{
	private Future<?> checkQuestUpdateTask;
	
    public void onStart() {
		//checkQuestUpdate();
    }
	
	private void checkQuestUpdate() {
		checkQuestUpdateTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				Iterator<Player> iter = World.getInstance().getPlayersIterator();
				while (iter.hasNext()) {
					final Player player = iter.next();
					if (player.getRace() == Race.ELYOS) {
					} else if (player.getRace() == Race.ASMODIANS) {
					}
				}
			}
		}, 5 * 1000, 5 * 1000);
	}
	
	public void onQuestUpdateLogin(final Player player) {
		if (player.getRace() == Race.ELYOS) {
		} else if (player.getRace() == Race.ASMODIANS) {
		}
    }
	
    public static QuestUpdateService getInstance() {
        return SingletonHolder.instance;
    }
	
	@SuppressWarnings("synthetic-access")
    private static class SingletonHolder {
        protected static final QuestUpdateService instance = new QuestUpdateService();
    }
}