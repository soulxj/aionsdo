/*
 * This file is part of aion-lightning <aion-lightning.org>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.utils;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.world.WorldPosition;

/**
 * @author antness
 */
public class ChatUtil {

	public static String position(String label, WorldPosition pos) {
		return position(label, pos.getMapId(), pos.getX(), pos.getY(), pos.getZ());
	}

	public static String position(String label, long worldId, float x, float y, float z) {
		// TODO: need rework for abyss map
		return String.format("[pos:%s;%d %f %f %f -1]", label, worldId, x, y, z);
	}

	public static String item(long itemId) {
		return String.format("[item: %d]", itemId);
	}

	public static String recipe(long recipeId) {
		return String.format("[recipe: %d]", recipeId);
	}

	public static String quest(int questId) {
		return String.format("[quest: %d]", questId);
	}

	public static String getRealAdminName(String name) {
		int index = name.lastIndexOf(" ");
		if (index == -1)
			return name;
		return name.substring(index + 1);
	}
	
	public static String removePattern(String PlayerName, String Pattern) {

        int index = Pattern.indexOf("%s");
        if (index == -1) return PlayerName;

        String RealName = "";
        RealName = PlayerName.replace(Pattern.substring(0, index), "");
        RealName = RealName.replace(Pattern.substring(index + 2), "");

        return RealName;
    }
	
	public static String getRealAdminName2 (String RealAdminName) {
		RealAdminName = removePattern(RealAdminName, AdminConfig.ADMIN_TAG_1);
		RealAdminName = removePattern(RealAdminName, AdminConfig.ADMIN_TAG_2);
		RealAdminName = removePattern(RealAdminName, AdminConfig.ADMIN_TAG_3);
		RealAdminName = removePattern(RealAdminName, AdminConfig.ADMIN_TAG_4);
		RealAdminName = removePattern(RealAdminName, AdminConfig.ADMIN_TAG_5);
		return RealAdminName;
	}
}
