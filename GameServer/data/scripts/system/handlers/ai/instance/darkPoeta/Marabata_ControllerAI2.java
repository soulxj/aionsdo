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
package ai.instance.darkPoeta;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;

/****/
/** Author Rinzler (Encom)
/****/

@AIName("Marabata_Controller")
public class Marabata_ControllerAI2 extends NpcAI2
{
	@Override
	public void think() {
	}
	
	@Override
    protected void handleSpawned() {
        super.handleSpawned();
		switch (getNpcId()) {
			///Marabata Of Strength.
			case 700439:
			case 700440:
			case 700441:
				AI2Actions.targetCreature(Marabata_ControllerAI2.this, getPosition().getWorldMapInstance().getNpc(214849));
				AI2Actions.useSkill(Marabata_ControllerAI2.this, 18110);
				AI2Actions.useSkill(Marabata_ControllerAI2.this, 18556);
		    break;
			///Marabata Of Aether.
			case 700442:
			case 700443:
			case 700444:
				AI2Actions.targetCreature(Marabata_ControllerAI2.this, getPosition().getWorldMapInstance().getNpc(214850));
				AI2Actions.useSkill(Marabata_ControllerAI2.this, 18110);
				AI2Actions.useSkill(Marabata_ControllerAI2.this, 18556);
		    break;
			///Marabata Of Poisoning.
			case 700445:
			case 700446:
			case 700447:
				AI2Actions.targetCreature(Marabata_ControllerAI2.this, getPosition().getWorldMapInstance().getNpc(214851));
				AI2Actions.useSkill(Marabata_ControllerAI2.this, 18110);
				AI2Actions.useSkill(Marabata_ControllerAI2.this, 18556);
		    break;
		}
    }
	
	@Override
	public boolean isMoveSupported() {
		return false;
	}
}