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
package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.stats.AbsoluteStatsTemplate;
import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "absoluteStats" })
@XmlRootElement(name = "absolute_stats")
public class AbsoluteStatsData {

	@XmlElement(name = "stats_set", required = true)
	protected List<AbsoluteStatsTemplate> absoluteStats;

	@XmlTransient
	private TIntObjectHashMap<ModifiersTemplate> absoluteStatsData = new TIntObjectHashMap<ModifiersTemplate>();

	void afterUnmarshal(Unmarshaller u, Object parent) {
		for (AbsoluteStatsTemplate stats : absoluteStats) {
			absoluteStatsData.put(stats.getId(), stats.getModifiers());
		}
		absoluteStats.clear();
		absoluteStats = null;
	}

	public ModifiersTemplate getTemplate(int statSetId) {
		return absoluteStatsData.get(statSetId);
	}

	public int size() {
		return absoluteStatsData.size();
	}

}
