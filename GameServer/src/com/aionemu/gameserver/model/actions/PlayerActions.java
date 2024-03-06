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
package com.aionemu.gameserver.model.actions;

import com.aionemu.gameserver.model.gameobjects.player.InRoll;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.windstreams.WindstreamPath;

/****/
/** Author Rinzler (Encom)
/****/

public class PlayerActions extends CreatureActions
{
	public static boolean isInPlayerMode(Player player, PlayerMode mode) {
		switch (mode) {
			case IN_ROLL:
				return player.inRoll != null;
			case WINDSTREAM:
				return player.windstreamPath != null;
		}
		return false;
	}
	
	public static void setPlayerMode(Player player, PlayerMode mode, Object obj) {
		switch (mode) {
			case IN_ROLL:
				player.inRoll = (InRoll) obj;
			break;
			case WINDSTREAM:
				player.windstreamPath = (WindstreamPath) obj;
			break;	
		}
	}
	
	public static boolean unsetPlayerMode(Player player, PlayerMode mode) {
		switch (mode) {
			case IN_ROLL:
				if (player.inRoll == null) {
					return false;
				}
				player.inRoll = null;
				return true;
			case WINDSTREAM:
				if (player.windstreamPath == null) {
					return false;
				}
				player.windstreamPath = null;
				return true;
		}
		return false;
	}
}