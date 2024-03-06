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
package com.aionemu.gameserver.model.team2.group.events;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.TeamEvent;
import com.aionemu.gameserver.model.team2.common.legacy.GroupEvent;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.model.team2.group.PlayerGroupService;
import com.aionemu.gameserver.network.aion.serverpackets.S_PARTY_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.S_PARTY_MEMBER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.S_INSTANCE_DUNGEON_COOLTIMES;
import com.aionemu.gameserver.network.aion.serverpackets.S_MESSAGE_CODE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public class PlayerEnteredEvent implements Predicate<Player>, TeamEvent {

    private final PlayerGroup group;
    private final Player enteredPlayer;

    public PlayerEnteredEvent(PlayerGroup group, Player enteredPlayer) {
        this.group = group;
        this.enteredPlayer = enteredPlayer;
    }

    /**
     * Entered player should not be in group yet
     */
    @Override
    public boolean checkCondition() {
        return !group.hasMember(enteredPlayer.getObjectId());
    }

    @Override
    public void handleEvent() {
        PlayerGroupService.addPlayerToGroup(group, enteredPlayer);
        PacketSendUtility.sendPacket(enteredPlayer, new S_PARTY_INFO(group));
        PacketSendUtility.sendPacket(enteredPlayer, new S_PARTY_MEMBER_INFO(group, enteredPlayer, GroupEvent.JOIN));
        PacketSendUtility.sendPacket(enteredPlayer, S_MESSAGE_CODE.STR_PARTY_ENTERED_PARTY);
        group.applyOnMembers(this);
    }

    @Override
    public boolean apply(Player player) {
        if (!player.getObjectId().equals(enteredPlayer.getObjectId())) {
            // TODO probably here JOIN event
            PacketSendUtility.sendPacket(player, new S_PARTY_MEMBER_INFO(group, enteredPlayer, GroupEvent.ENTER));
            PacketSendUtility.sendPacket(player, new S_INSTANCE_DUNGEON_COOLTIMES(enteredPlayer, false, group));
            PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_PARTY_HE_ENTERED_PARTY(enteredPlayer.getName()));

            PacketSendUtility.sendPacket(enteredPlayer, new S_PARTY_MEMBER_INFO(group, player, GroupEvent.ENTER));
        }
        return true;
    }
}
