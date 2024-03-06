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
package ai.instance.esoterrace;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.windstreams.Location2D;
import com.aionemu.gameserver.model.templates.windstreams.WindstreamTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.S_WIND_STATE_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.*;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("Esoterrace_WindStream_2")
public class Esoterrace_WindStream_2AI2 extends NpcAI2
{
	@Override
    protected void handleSpawned() {
        super.handleSpawned();
		Npc npc = getOwner();
		startWindStream(npc);
        windStreamAnnounce(getOwner(), 0);
    }
	
	private void startWindStream(final Npc npc) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                Npc npc2 = (Npc) spawn(300250000, 206033, 392.0000f, 543.0000f, 318.0000f, (byte) 18, 0, 1);
				windStreamAnnounce(npc2, 0);
				WorldMapInstance instance = getPosition().getWorldMapInstance();
				deleteNpcs(instance.getNpcs(207035));
                spawn(206033, 392.0000f, 543.0000f, 318.0000f, (byte) 18);
                PacketSendUtility.broadcastPacket(npc2, new S_WIND_STATE_INFO(1, 300250000, 159, 0));
                if (npc2 != null) {
                    npc2.getController().onDelete();
                } if (npc != null) {
                    npc.getController().onDelete();
                }
            }
        }, 5000);
    }
	
    private void windStreamAnnounce(final Npc npc, final int state) {
        WindstreamTemplate template = DataManager.WINDSTREAM_DATA.getStreamTemplate(npc.getPosition().getMapId());
        for (Location2D wind: template.getLocations().getLocation()) {
            if (wind.getId() == 159) {
                wind.setState(state);
                break;
            }
        }
        npc.getPosition().getWorld().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                PacketSendUtility.sendPacket(player, new S_WIND_STATE_INFO(1, 300250000, 159, state));
            }
        });
    }
	
	private void deleteNpcs(List<Npc> npcs) {
		for (Npc npc: npcs) {
			if (npc != null) {
				npc.getController().onDelete();
			}
		}
	}
}