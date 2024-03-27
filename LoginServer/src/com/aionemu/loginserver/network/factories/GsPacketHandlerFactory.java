/*
 *  Aion Classic Emu based on Aion Encom Source Files
 *
 *  ENCOM Team based on Aion-Lighting Open Source
 *  All Copyrights : "Data/Copyrights/AEmu-Copyrights.text
 *
 *  iMPERIVM.FUN - AION DEVELOPMENT FORUM
 *  Forum: <http://https://imperivm.fun/>
 *
 */
package com.aionemu.loginserver.network.factories;

import java.nio.ByteBuffer;

import com.aionemu.loginserver.network.gameserver.clientpackets.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.loginserver.network.gameserver.GsClientPacket;
import com.aionemu.loginserver.network.gameserver.GsConnection;
import com.aionemu.loginserver.network.gameserver.GsConnection.State;

/**
 * @author -Nemesiss-
 */
public class GsPacketHandlerFactory {

	/**
	 * logger for this class
	 */
	private static final Logger log = LoggerFactory.getLogger(GsPacketHandlerFactory.class);

	/**
	 * Reads one packet from given ByteBuffer
	 * 
	 * @param data
	 * @param client
	 * @return GsClientPacket object from binary data
	 */
	public static GsClientPacket handle(ByteBuffer data, GsConnection client) {
		GsClientPacket msg = null;
		State state = client.getState();
		int id = data.get() & 0xff;

		switch (state) {
			case CONNECTED: {
				switch (id) {
					case 0:
						msg = new CM_GS_AUTH();
						break;
					case 13:
						msg = new CM_MAC();
						break;
					default:
						unknownPacket(state, id);
				}
				break;
			}
			case AUTHED: {
				switch (id) {
					case 1:
						msg = new CM_ACCOUNT_AUTH();
						break;
					case 2:
						msg = new CM_ACCOUNT_RECONNECT_KEY();
						break;
					case 3:
						msg = new CM_ACCOUNT_DISCONNECTED();
						break;
					case 4:
						msg = new CM_ACCOUNT_LIST();
						break;
					case 5:
						msg = new CM_LS_CONTROL();
						break;
					case 6:
						msg = new CM_BAN();
						break;
					case 8:
						msg = new CM_GS_CHARACTER();
						break;
					case 9:
						msg = new CM_ACCOUNT_TOLL_INFO();
						break;
					case 10:
						msg = new CM_MACBAN_CONTROL();
						break;
					case 11:
						msg = new CM_PREMIUM_CONTROL();
						break;
					case 12:
						msg = new CM_GS_PONG();
						break;
					case 13:
						//TODO find out if sometimes received in AUTHED state
						msg = new CM_MAC();
						break;
					case 0x0f:
						msg = new CM_ACCOUNT_CHARGE();
						break;
					default:
						unknownPacket(state, id);
				}
				break;
			}
		}
		
		if(msg != null) {
			msg.setConnection(client);
			msg.setBuffer(data);
		}
		
		return msg;
	}

	/**
	 * Logs unknown packet.
	 * 
	 * @param state
	 * @param id
	 */
	private static void unknownPacket(State state, int id) {
		log.warn(String.format("Unknown packet recived from Game Server: 0x%02X state=%s", id, state.toString()));
	}
}
