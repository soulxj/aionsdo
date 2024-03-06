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
package com.aionemu.gameserver.model.templates.item.actions;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemActions")
public class ItemActions
{
	@XmlElements
	({
	    @XmlElement(name = "skilllearn", type = SkillLearnAction.class),
		@XmlElement(name = "extract", type = ExtractAction.class),
		@XmlElement(name = "skilluse", type = SkillUseAction.class),
		@XmlElement(name = "enchant", type = EnchantItemAction.class),
		@XmlElement(name = "queststart", type = QuestStartAction.class),
		@XmlElement(name = "dye", type = DyeAction.class),
		@XmlElement(name = "craftlearn", type = CraftLearnAction.class),
		@XmlElement(name = "toypetspawn", type = ToyPetSpawnAction.class),
		@XmlElement(name = "decompose", type = DecomposeAction.class),
		@XmlElement(name = "titleadd", type = TitleAddAction.class),
		@XmlElement(name = "learnemotion", type = EmotionLearnAction.class),
		@XmlElement(name = "read", type = ReadAction.class),
		@XmlElement(name = "fireworkact", type = FireworksUseAction.class),
		@XmlElement(name = "instancetimeclear", type = InstanceTimeClear.class),
		@XmlElement(name = "expandinventory", type = ExpandInventoryAction.class),
		@XmlElement(name = "animation", type = AnimationAddAction.class),
		@XmlElement(name = "cosmetic", type = CosmeticItemAction.class),
		@XmlElement(name = "charge", type = ChargeAction.class),
        @XmlElement(name = "adoptpet", type = AdoptPetAction.class),
		@XmlElement(name = "assemble", type = AssemblyItemAction.class),
	})
	protected List<AbstractItemAction> itemActions;
	
	public List<AbstractItemAction> getItemActions() {
		if (itemActions == null) {
			itemActions = new ArrayList<AbstractItemAction>();
		}
		return this.itemActions;
	}
	
	public List<ToyPetSpawnAction> getToyPetSpawnActions() {
		List<ToyPetSpawnAction> result = new ArrayList<ToyPetSpawnAction>();
		if (itemActions == null) {
			return result;
		} for (AbstractItemAction action : itemActions) {
			if (action instanceof ToyPetSpawnAction) {
				result.add((ToyPetSpawnAction) action);
			}
		}
		return result;
	}
	
	public EnchantItemAction getEnchantAction() {
		if (itemActions == null) {
			return null;
		} for (AbstractItemAction action : itemActions) {
			if ((action instanceof EnchantItemAction)) {
				return (EnchantItemAction) action;
			}
		}
		return null;
	}
	
	public CraftLearnAction getCraftLearnAction() {
		if (itemActions == null) {
			return null;
		} for (AbstractItemAction action : itemActions) {
			if ((action instanceof CraftLearnAction)) {
				return (CraftLearnAction) action;
			}
		}
		return null;
	}
	
    public AdoptPetAction getAdoptPetAction() {
        if (itemActions == null) {
            return null;
        } for (AbstractItemAction action : itemActions) {
            if (action instanceof AdoptPetAction) {
                return (AdoptPetAction) action;
            }
        }
        return null;
    }
}