/*
 * This file is part of aion-lightning <aion-lightning.com>.
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
package com.aionemu.gameserver.spawnengine;

import com.aionemu.commons.utils.internal.chmv8.PlatformDependent;

import java.util.Map;

/**
 * @author Rolandas
 */
public class WorldWalkerFormations {

	private Map<Integer, InstanceWalkerFormations> formations;

	public WorldWalkerFormations() {
		formations = PlatformDependent.newConcurrentHashMap();
	}

	/**
	 * @param instanceId
	 * @return
	 */
	protected InstanceWalkerFormations getInstanceFormations(int instanceId) {
		InstanceWalkerFormations instanceFormation = formations.get(instanceId);
		if (instanceFormation == null) {
			instanceFormation = new InstanceWalkerFormations();
			formations.put(instanceId, instanceFormation);
		}
		return instanceFormation;
	}

}
