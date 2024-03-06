/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.model.team2.league.events;

import com.aionemu.gameserver.model.team2.TeamEvent;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.league.League;
import com.aionemu.gameserver.model.team2.league.LeagueMember;
import com.aionemu.gameserver.model.team2.league.LeagueService;
import com.aionemu.gameserver.network.aion.serverpackets.S_ALLIANCE_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.S_TACTICS_SIGN;
import com.google.common.base.Predicate;

public class LeagueEnteredEvent implements Predicate<LeagueMember>, TeamEvent
{
    private final League league;
    private final PlayerAlliance invitedAlliance;
	
    public LeagueEnteredEvent(League league, PlayerAlliance alliance) {
        this.league = league;
        this.invitedAlliance = alliance;
    }
	
    @Override
    public boolean checkCondition() {
        return !league.hasMember(invitedAlliance.getObjectId());
    }
	
    @Override
    public void handleEvent() {
        LeagueService.addAllianceToLeague(league, invitedAlliance);
        league.apply(this);
    }
	
    @Override
    public boolean apply(LeagueMember member) {
        PlayerAlliance alliance = member.getObject();
        alliance.sendPacket(new S_ALLIANCE_INFO(alliance, S_ALLIANCE_INFO.LEAGUE_ENTERED, league.getLeaderObject().getLeader().getName()));
        alliance.sendPacket(new S_TACTICS_SIGN(0, 0));
        return true;
    }
}