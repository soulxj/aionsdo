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
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.common.legacy.LootGroupRules;
import com.aionemu.gameserver.model.team2.league.LeagueMember;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;

/**
 * @author Sarynth, xTz
 */
public class S_ALLIANCE_INFO extends AionServerPacket {

	private LootGroupRules lootRules;
	private PlayerAlliance alliance;
	private int leaderid;
	private int groupid;
	private final int messageId;
	private final String message;
	
	public static final int VICECAPTAIN_PROMOTE = 1300984;
    public static final int VICECAPTAIN_DEMOTE = 1300985;
    public static final int LEAGUE_ENTERED = 1400560;
	public static final int LEAGUE_JOINED_ALLIANCE = 1400561;
    public static final int LEAGUE_LEFT_ME = 1400571;
	public static final int LEAGUE_LEFT_HIM = 1400572;
    public static final int LEAGUE_EXPEL = 1400574;
    public static final int LEAGUE_EXPELLED = 1400576;
	public static final int LEAGUE_DISPERSED = 1400579;
	
	public S_ALLIANCE_INFO(PlayerAlliance alliance) {
		this(alliance, 0, StringUtils.EMPTY);
	}
	
	public S_ALLIANCE_INFO(PlayerAlliance alliance, int messageId, String message) {
		this.alliance = alliance;
		groupid = alliance.getObjectId();
		leaderid = alliance.getLeader().getObjectId();
		lootRules = alliance.getLootGroupRules();
		this.messageId = messageId;
		this.message = message;
	}
	
	@Override
	protected void writeImpl(AionConnection con) {
		Player player = con.getActivePlayer();
		writeH(alliance.groupSize());
		writeD(groupid);
		writeD(leaderid);
		Collection<Integer> ids = alliance.getViceCaptainIds();
		for (Integer id : ids) {
			writeD(id);
		} for (int i = 0; i < 4 - ids.size(); i++) {
			writeD(0);
		}
		writeD(lootRules.getLootRule().getId());
		writeD(lootRules.getMisc());
		writeD(lootRules.getCommonItemAbove());
		writeD(lootRules.getSuperiorItemAbove());
		writeD(lootRules.getHeroicItemAbove());
		writeD(lootRules.getFabledItemAbove());
		writeD(lootRules.getEthernalItemAbove());
		writeD(lootRules.getAutodistribution().getId());
		writeD(0x02);
		writeC(0x00);
		writeD(0x3F);
		writeD(alliance.isInLeague() ? alliance.getLeague().getTeamId() : 0);
		for (int a = 0; a < 4; a++) {
			writeD(a);
			writeD(1000 + a);
		}
		writeD(messageId);
		writeS(messageId != 0 ? message : StringUtils.EMPTY);
		if (alliance != null && alliance.isInLeague()) {
			lootRules = alliance.getLeague().getLootGroupRules();
			writeH(alliance.getLeague().size());
			writeD(lootRules.getLootRule().getId());
			writeD(lootRules.getAutodistribution().getId());
			writeD(lootRules.getCommonItemAbove());
			writeD(lootRules.getSuperiorItemAbove());
			writeD(lootRules.getHeroicItemAbove());
			writeD(lootRules.getFabledItemAbove());
			writeD(lootRules.getEthernalItemAbove());
			writeD(0);
			writeD(0);
			for (LeagueMember leagueMember : alliance.getLeague().getSortedMembers()) {
				writeD(leagueMember.getLeaguePosition());
				writeD(leagueMember.getObjectId());
				writeD(leagueMember.getObject().size());
				writeS(leagueMember.getObject().getLeaderObject().getName());
			}
		}
	}
}