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

import com.aionemu.commons.services.CronService;
import com.aionemu.gameserver.ShutdownHook;

import com.aionemu.gameserver.services.player.BattlePassService;
import com.aionemu.gameserver.services.player.EventWindowService;
import com.aionemu.gameserver.services.player.SielEnergyService;
import org.slf4j.*;

import java.sql.Timestamp;
import java.util.Calendar;

public class RestartService
{
    private Logger log = LoggerFactory.getLogger(RestartService.class);
	
    public void onStart() {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
		String daily1 = "0 0 9 ? * * *";
        CronService.getInstance().schedule(new Runnable() {
            public void run() {
                BattlePassService.getInstance().onRestart();
                SielEnergyService.getInstance().onRestart();
                EventWindowService.getInstance().onRestart();
            }
        }, daily1);
        CronService.getInstance().schedule(new Runnable() {
            public void run() {
				int delay1 = 300;
                ShutdownHook.getInstance().doShutdown(delay1, 20, ShutdownHook.ShutdownMode.RESTART);
            }
        }, daily1);
    }
	
    public static RestartService getInstance() {
        return SingletonHolder.instance;
    }
	
    private static class SingletonHolder {
        protected static final RestartService instance = new RestartService();
    }
}