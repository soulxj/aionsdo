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
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.S_BLOCK_RESULT;
import com.aionemu.gameserver.network.aion.serverpackets.S_MESSAGE_CODE;
import com.aionemu.gameserver.services.SocialService;
import com.aionemu.gameserver.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ben
 */
public class CM_BLOCK_ADD extends AionClientPacket {

	private static Logger log = LoggerFactory.getLogger(CM_BLOCK_ADD.class);

	private String targetName;
	private String reason;

	public CM_BLOCK_ADD(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void readImpl() {
		targetName = readS();
		reason = readS();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void runImpl() {

		Player activePlayer = getConnection().getActivePlayer();

		Player targetPlayer = World.getInstance().findPlayer(targetName);

		// Trying to block self
		if (activePlayer.getName().equalsIgnoreCase(targetName)) {
			sendPacket(new S_BLOCK_RESULT(S_BLOCK_RESULT.CANT_BLOCK_SELF, targetName));
		}

		// List full
		else if (activePlayer.getBlockList().isFull()) {
			sendPacket(new S_BLOCK_RESULT(S_BLOCK_RESULT.LIST_FULL, targetName));
		}

		// Player offline
		else if (targetPlayer == null) {
			sendPacket(new S_BLOCK_RESULT(S_BLOCK_RESULT.TARGET_NOT_FOUND, targetName));
		}

		// Player is your friend
		else if (activePlayer.getFriendList().getFriend(targetPlayer.getObjectId()) != null) {
			sendPacket(S_MESSAGE_CODE.STR_BLOCKLIST_NO_BUDDY);
		}

		// Player already blocked
		else if (activePlayer.getBlockList().contains(targetPlayer.getObjectId())) {
			sendPacket(S_MESSAGE_CODE.STR_BLOCKLIST_ALREADY_BLOCKED);
		}

		// Try and block player
		else if (!SocialService.addBlockedUser(activePlayer, targetPlayer, reason)) {
			log.error("Failed to add " + targetPlayer.getName() + " to the block list for " + activePlayer.getName()
				+ " - check database setup.");
		}

	}

}
