/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.model.templates;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.stats.calc.StatOwner;
import com.aionemu.gameserver.model.stats.calc.functions.StatFunction;
import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xavier
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "title_templates")
public class TitleTemplate implements StatOwner{

	@XmlAttribute(name = "id", required = true)
	@XmlID
	private String id;

	@XmlElement(name = "modifiers", required = false)
	protected ModifiersTemplate modifiers;

	@XmlAttribute(name = "race", required = true)
	private Race race;

	private int titleId;
	
	@XmlAttribute(name = "nameId")
	private int nameId;
	
	@XmlAttribute(name = "desc")
	private String description;

	public int getTitleId() {
		return titleId;
	}

	public Race getRace() {
		return race;
	}
	
	public int getNameId() {
		return nameId;
	}
	
	public String getDesc() {
		return description;
	}

	public List<StatFunction> getModifiers() {
		if (modifiers != null) {
			return modifiers.getModifiers();
		}
		return null;
	}

	void afterUnmarshal(Unmarshaller u, Object parent) {
		this.titleId = Integer.parseInt(id);
	}
}
