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
package ai.instance.theobomosLab;

import ai.GeneralNpcAI2;

import com.aionemu.commons.network.util.ThreadPoolManager;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AI2Actions;

import java.util.*;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("Triroan_Element")
public class Triroan_ElementAI2 extends GeneralNpcAI2
{
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		startTriroanElement();
	}
	
	private void startTriroanElement() {
        if (!isAlreadyDead()) {
			if (getNpcId() == 280975) { //Fire Of Triroan.
				think();
				ThreadPoolManager.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						AI2Actions.useSkill(Triroan_ElementAI2.this, 18486); //Curse Of Flame.
					}
				}, 25000);
				ThreadPoolManager.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						if (!isAlreadyDead()) {
							despawn();
						}
					}
				}, 30000);
			} if (getNpcId() == 280976) { //Water Of Triroan.
				think();
				ThreadPoolManager.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						AI2Actions.useSkill(Triroan_ElementAI2.this, 18483); //Scream Of Water.
					}
				}, 25000);
				ThreadPoolManager.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						if (!isAlreadyDead()) {
							despawn();
						}
					}
				}, 30000);
			} if (getNpcId() == 280977) { //Earth Of Triroan.
				think();
				ThreadPoolManager.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						AI2Actions.targetCreature(Triroan_ElementAI2.this, getPosition().getWorldMapInstance().getNpc(237250)); //Sealed Unstable Triroan.
						AI2Actions.useSkill(Triroan_ElementAI2.this, 18485); //Wall Of Earth.
					}
				}, 30000);
				ThreadPoolManager.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						if (!isAlreadyDead()) {
							despawn();
						}
					}
				}, 35000);
			} if (getNpcId() == 280978) { //Wind Of Triroan.
				think();
				ThreadPoolManager.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						AI2Actions.useSkill(Triroan_ElementAI2.this, 18484); //Wind Blade Of Rage.
					}
				}, 30000);
				ThreadPoolManager.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						if (!isAlreadyDead()) {
							despawn();
						}
					}
				}, 35000);
            }
        }
    }
	
	private void despawn() {
		AI2Actions.deleteOwner(this);
	}
}