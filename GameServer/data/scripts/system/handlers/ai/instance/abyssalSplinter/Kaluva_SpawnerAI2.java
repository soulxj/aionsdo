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
package ai.instance.abyssalSplinter;

import com.aionemu.commons.utils.Rnd;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.concurrent.Future;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("Kaluva_Spawner")
public class Kaluva_SpawnerAI2 extends NpcAI2
{
	private Future<?> kaluvaSpawnerTask;
	
	@Override
	public void think() {
	}
	
	private void kaluvaSpawner() {
		kaluvaSpawnerTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				switch (Rnd.get(1, 2)) {
					case 1:
					    getOwner().getController().onDelete();
						spawn(281911, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) getOwner().getHeading());
					break;
					case 2:
					    getOwner().getController().onDelete();
					    spawn(281912, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) getOwner().getHeading());
					break;
				}
			}
		}, 30000, 120000);
	}
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		kaluvaSpawner();
	}
	
	@Override
	protected void handleDespawned() {
		super.handleDespawned();
		kaluvaSpawnerTask.cancel(true);
		getOwner().getEffectController().removeAllEffects();
	}
	
	@Override
	protected void handleDied() {
		super.handleDied();
		kaluvaSpawnerTask.cancel(true);
		getOwner().getController().onDelete();
		getOwner().getEffectController().removeAllEffects();
	}
	
	@Override
	public boolean isMoveSupported() {
		return false;
	}
}