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
package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class AutoGroupConfig
{
	@Property(key = "gameserver.autogroup.enable", defaultValue = "true")
	public static boolean AUTO_GROUP_ENABLED;
	
	@Property(key = "gameserver.dredgion.timer", defaultValue = "60")
	public static long DREDGION_TIMER;
	@Property(key = "gameserver.dredgion.enable", defaultValue = "true")
	public static boolean DREDGION_ENABLED;
	@Property(key = "gameserver.dredgion.schedule.midday", defaultValue = "0 0 12 ? * MON-SUN *")
	public static String DREDGION_SCHEDULE_MIDDAY;
	@Property(key = "gameserver.dredgion.schedule.evening", defaultValue = "0 0 18 ? * MON-SUN *")
	public static String DREDGION_SCHEDULE_EVENING;
	@Property(key = "gameserver.dredgion.schedule.midnight", defaultValue = "0 0 0 ? * MON-SUN *")
	public static String DREDGION_SCHEDULE_MIDNIGHT;
	
	@Property(key = "gameserver.tiak.base.timer", defaultValue = "120")
	public static long TIAK_BASE_TIMER;
	@Property(key = "gameserver.tiak.base.enable", defaultValue = "true")
	public static boolean TIAK_BASE_ENABLED;
	@Property(key = "gameserver.tiak.base.schedule.evening", defaultValue = "0 0 20 ? * MON-SUN *")
	public static String TIAK_BASE_SCHEDULE_EVENING;
}