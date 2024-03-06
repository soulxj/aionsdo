/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.PetCommonData;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.pet.PetDopingBag;

import java.util.List;

/**
 * @author Xitanium, Kamui, Rolandas
 */
public abstract class PlayerPetsDAO implements DAO {

	@Override
	public final String getClassName() {
		return PlayerPetsDAO.class.getName();
	}

	public abstract void insertPlayerPet(PetCommonData petCommonData);

	public abstract void removePlayerPet(Player player, int petId);

	public abstract void updatePetName(PetCommonData petCommonData);

	public abstract List<PetCommonData> getPlayerPets(Player player);

	public abstract void setTime(Player player, int petId, long time);
	
	public abstract void saveFeedStatus(Player player, int petId, int hungryLevel, int feedProgress, long reuseTime);

	public abstract boolean savePetMoodData(PetCommonData petCommonData);
	
	public abstract void saveDopingBag(Player player, int petId, PetDopingBag bag);
}
