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
package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import javax.xml.bind.annotation.XmlAttribute;

public class DelayedSkillEffect extends EffectTemplate
{
	@XmlAttribute(name = "skill_id")
	protected int skillId;
	
	@Override
	public void applyEffect(Effect effect) {
		effect.addToEffectedController();
	}
	
	@Override
    public void startEffect(final Effect effect) {
		ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
				if (!effect.getEffected().getEffectController().hasAbnormalEffect(skillId)) {
					final SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillId);
					Effect e = new Effect(effect.getEffector(), effect.getEffected(), template, template.getLvl(), 0);
					e.initialize();
					e.applyEffect();
				}
			}
		}, effect.getEffectsDuration());
    }
	
	@Override
    public void endEffect(Effect effect) {
    }
}