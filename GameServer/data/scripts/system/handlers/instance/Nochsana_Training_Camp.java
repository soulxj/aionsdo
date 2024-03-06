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
package instance;

import com.aionemu.gameserver.controllers.effect.PlayerEffectController;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.*;

/****/
/** Author Rinzler (Encom)
/****/

@InstanceID(300030000)
public class Nochsana_Training_Camp extends GeneralInstanceHandler
{
	private Race skillRace;
	
	protected final int SKILL_NPC_SHIELD_OF_COMPASSION = 1869;
	protected final int POLYMORPH_IDAB1_MINICASTLE_LIGHT_AVATAR = 20467;
	protected final int POLYMORPH_IDAB1_MINICASTLE_DARK_AVATAR = 20468;
	
	@Override
	public void onEnterInstance(final Player player) {
		super.onInstanceCreate(instance);
		instance.doOnAllPlayers(new Visitor<Player>() {
		    @Override
			public void visit(Player player) {
				final int miniCastleAvatar = skillRace == Race.ASMODIANS ? POLYMORPH_IDAB1_MINICASTLE_DARK_AVATAR : POLYMORPH_IDAB1_MINICASTLE_LIGHT_AVATAR;
				SkillEngine.getInstance().applyEffectDirectly(miniCastleAvatar, player, player, 0 * 1);
			}
		});
	}
	
	@Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
		instance.doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				player.getController().updateZone();
				player.getController().updateNearbyQuests();
			}
		});
    }
	
	@Override
    public void onDie(Npc npc) {
        Player player = npc.getAggroList().getMostPlayerDamage();
		switch (npc.getObjectTemplate().getTemplateId()) {
			case 256694: //mini_castle_door_dr.
				spawn(256686, player.getX() + 3, player.getY(), player.getZ(), (byte) 0);
				spawn(256682, player.getX(), player.getY() + 3, player.getZ(), (byte) 0);
			break;
		}
	}
	
	@Override
	public void handleUseItemFinish(final Player player, final Npc npc) {
		switch(npc.getNpcId()) {
			case 700437: //Nochsana Artifact.
				instance.doOnAllPlayers(new Visitor<Player>() {
					@Override
					public void visit(Player player) {
						SkillEngine.getInstance().applyEffectDirectly(SKILL_NPC_SHIELD_OF_COMPASSION, player, player, 60000 * 1);
					}
				});
			break;
		}
	}
	
	private void removeEffects(Player player) {
		PlayerEffectController effectController = player.getEffectController();
		effectController.removeEffect(POLYMORPH_IDAB1_MINICASTLE_LIGHT_AVATAR);
		effectController.removeEffect(POLYMORPH_IDAB1_MINICASTLE_DARK_AVATAR);
	}
	
	@Override
	public void onLeaveInstance(Player player) {
		removeEffects(player);
	}
	
	@Override
	public void onPlayerLogOut(Player player) {
		removeEffects(player);
	}
}