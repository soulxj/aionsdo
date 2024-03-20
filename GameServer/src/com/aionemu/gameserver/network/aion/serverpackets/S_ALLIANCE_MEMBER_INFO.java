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
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.stats.container.PlayerLifeStats;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.team2.common.legacy.PlayerAllianceEvent;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.ChatUtil;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.Collection;
import java.util.List;

public class S_ALLIANCE_MEMBER_INFO extends AionServerPacket
{
	private Player player;
	private PlayerAllianceEvent event;
	private final int allianceId;
	private final int objectId;
	
	public S_ALLIANCE_MEMBER_INFO(PlayerAllianceMember member, PlayerAllianceEvent event) {
		this.player = member.getObject();
		this.event = event;
		this.allianceId = member.getAllianceId();
		this.objectId = member.getObjectId();
	}
	
	@Override
	protected void writeImpl(AionConnection con) {
		PlayerCommonData pcd = player.getCommonData();
		WorldPosition wp = pcd.getPosition();
		if (event == PlayerAllianceEvent.ENTER && !player.isOnline()) {
			event = PlayerAllianceEvent.ENTER_OFFLINE;
		}
		writeD(allianceId);
		writeD(objectId);
		if (player.isOnline()) {
			PlayerLifeStats pls = player.getLifeStats();
			writeD(pls.getMaxHp());
			writeD(pls.getCurrentHp());
			writeD(pls.getMaxMp());
			writeD(pls.getCurrentMp());
			writeD(pls.getMaxFp());
			writeD(pls.getCurrentFp());
		} else {
			writeD(0);
			writeD(0);
			writeD(0);
			writeD(0);
			writeD(0);
			writeD(0);
		}
		
		writeD(0);
		writeD(wp.getMapId());
		writeD(wp.getMapId());
		writeF(wp.getX());
		writeF(wp.getY());
		writeF(wp.getZ());
		writeC(pcd.getPlayerClass().getClassId());
		writeC(pcd.getGender().getGenderId());
		writeC(pcd.getLevel());
		writeC(event.getId());
		writeH(player.isOnline() ? 1 : 0);
		writeC(player.isMentor() ? 1 : 0);
		writeC(0);
		
		switch (event) {
			case LEAVE:
            case BANNED:
            case MOVEMENT:
            case DISCONNECTED:
			case LEAVE_TIMEOUT:
            break;
			case ENTER:
            case UPDATE:
			case RECONNECT:
			case ENTER_OFFLINE:
			case DEMOTE_VICE_CAPTAIN:
            case APPOINT_VICE_CAPTAIN:
				writeS(pcd.getName());
				writeD(0x00);
                writeD(0x00);
				writeC(0x7F);
				if (player.isOnline()) {
					List<Effect> abnormalEffects = player.getEffectController().getAbnormalEffects();
					writeH(abnormalEffects.size());
					for (Effect effect : abnormalEffects) {
						writeD(effect.getEffectorId());
						writeH(effect.getSkillId());
						writeC(effect.getSkillLevel());
						writeC(effect.getTargetSlot());
						writeD(effect.getRemainingTime());
					}
				} else {
					writeH(0x00);
				}
				break;
			case JOIN:
			case MEMBER_GROUP_CHANGE:
				writeS(pcd.getName());
			break;
		}
	}
}