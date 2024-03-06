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
package ai;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.network.aion.serverpackets.S_INVISIBLE_LEVEL;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Rinzler
 */

@AIName("invisible_npc")
public class InvisibleNpcAI2 extends AggressiveNpcAI2
{
	private boolean canThink = true;
	
	@Override
	public boolean canThink() {
		return canThink;
	}
	
	@Override
	protected void handleSpawned() {
        super.handleSpawned();
		switch (getNpcId()) {
			//Heiron.
			case 212112:
			case 211172:
			//Telos.
			case 219351:
			case 219352:
			case 219355:
			case 219356:
			case 219370:
			case 219371:
			    hideShape();
			break;
		} switch (getNpcId()) {
			//Beluslan.
			case 213326:
			case 213327:
			case 213521:
			case 213522:
				rookieWill();
			break;
		}
		getOwner().getEffectController().setAbnormal(AbnormalState.HIDE.getId());
		getOwner().setVisualState(CreatureVisualState.HIDE1);
		PacketSendUtility.broadcastPacket(getOwner(), new S_INVISIBLE_LEVEL(getOwner()));
	}
	
    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
		getOwner().getEffectController().setAbnormal(AbnormalState.HIDE.getId());
		getOwner().unsetVisualState(CreatureVisualState.HIDE1);
		PacketSendUtility.broadcastPacket(getOwner(), new S_INVISIBLE_LEVEL(getOwner()));
    }
    
    @Override
	protected void handleTargetGiveup() {
    	super.handleTargetGiveup();
		switch (getNpcId()) {
			//Heiron.
			case 212112:
			case 211172:
			//Telos.
			case 219351:
			case 219352:
			case 219355:
			case 219356:
			case 219370:
			case 219371:
			    hideShape();
			break;
		} switch (getNpcId()) {
			//Beluslan.
			case 213326:
			case 213327:
			case 213521:
			case 213522:
				rookieWill();
			break;
		}
		getOwner().getEffectController().setAbnormal(AbnormalState.HIDE.getId());
		getOwner().setVisualState(CreatureVisualState.HIDE1);
		PacketSendUtility.broadcastPacket(getOwner(), new S_INVISIBLE_LEVEL(getOwner()));
	}
	
	private void hideShape() {
	    SkillEngine.getInstance().applyEffectDirectly(17001, getOwner(), getOwner(), 0); //Hide Shape.
	}
	private void rookieWill() {
		SkillEngine.getInstance().applyEffectDirectly(17709, getOwner(), getOwner(), 0); //Rookie's Will.
	}
	
	@Override
    protected void handleDied() {
        super.handleDied();
	}
}