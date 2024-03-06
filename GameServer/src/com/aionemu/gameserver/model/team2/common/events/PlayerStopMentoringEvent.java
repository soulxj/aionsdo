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
package com.aionemu.gameserver.model.team2.common.events;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.TeamMember;
import com.aionemu.gameserver.model.team2.TemporaryPlayerTeam;
import com.aionemu.gameserver.network.aion.serverpackets.S_ETC_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.S_MESSAGE_CODE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public abstract class PlayerStopMentoringEvent<T extends TemporaryPlayerTeam<? extends TeamMember<Player>>> extends AlwaysTrueTeamEvent implements Predicate<Player> {

    protected final T team;
    protected final Player player;

    public PlayerStopMentoringEvent(T team, Player player) {
        this.team = team;
        this.player = player;
    }

    @Override
    public void handleEvent() {
        player.setMentor(false);
        PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_MSG_MENTOR_END);
        team.applyOnMembers(this);
        PacketSendUtility.broadcastPacketAndReceive(player, new S_ETC_STATUS(2, player));
    }

    @Override
    public boolean apply(Player member) {
        if (!player.equals(member)) {
            PacketSendUtility.sendPacket(member, S_MESSAGE_CODE.STR_MSG_MENTOR_END_PARTYMSG(player.getName()));
        }
        sendGroupPacketOnMentorEnd(member);
        return true;
    }

    /**
     * @param member
     */
    protected abstract void sendGroupPacketOnMentorEnd(Player member);
}
