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
package com.aionemu.gameserver.services.player;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.network.aion.serverpackets.S_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.S_EXP;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReposeEnergyTask implements Runnable
{
    private static final Logger log = LoggerFactory.getLogger(ReposeEnergyTask.class);
    private final int playerId;
	
    ReposeEnergyTask(int playerId) {
        this.playerId = playerId;
    }
	
    public void run() {
        Player player = World.getInstance().findPlayer(playerId);
		PlayerCommonData pcd = player.getCommonData();
        if (player != null && player.isInState(CreatureState.RESTING) && !player.isInInstance() && pcd.getCurrentReposteEnergy() < pcd.getMaxReposteEnergy()) {
			pcd.addReposteEnergy(3388542); //5%
			PacketSendUtility.sendPacket(player, new S_STATUS(player));
			PacketSendUtility.sendPacket(player, new S_EXP(pcd.getExpShown(), pcd.getExpRecoverable(), pcd.getExpNeed(), pcd.getCurrentReposteEnergy(), pcd.getMaxReposteEnergy()));
        }
    }
}