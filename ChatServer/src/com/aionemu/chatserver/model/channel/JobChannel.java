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
package com.aionemu.chatserver.model.channel;

import com.aionemu.chatserver.model.ChannelType;
import com.aionemu.chatserver.model.Gender;
import com.aionemu.chatserver.model.PlayerClass;
import com.aionemu.chatserver.model.Race;

/**
 * @author ATracer
 */
public class JobChannel extends RaceChannel {

    private PlayerClass playerClass;
    private Gender gender;

    /**
     * @param playerClass
     * @param race
     */
    public JobChannel(Gender gender, PlayerClass playerClass, Race race, String identifier) {
        super(ChannelType.JOB, race, identifier);
        this.playerClass = playerClass;
        this.gender = gender;
    }

    /**
     * @return the playerClass
     */
    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public Gender getGender() {
        return gender;
    }
}
