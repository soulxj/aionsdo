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
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_BUILDER_LEVEL extends AionServerPacket
{
	public S_BUILDER_LEVEL() {
	}
	
	private boolean isGM;
    private int accountType;
    private int purchaseType;
    private int time;
	
	public S_BUILDER_LEVEL(boolean isGM) {
        this.isGM = isGM;
    }
	
    public S_BUILDER_LEVEL(boolean isGM, int accountType, int purchaseType, int time) {
		this.isGM = isGM;
        this.accountType = accountType;
        this.purchaseType = purchaseType;
        this.time = time;
    }
	
	@Override
    protected void writeImpl(AionConnection con) {
        writeH(isGM ? 3:0);
        writeB(new byte[27]);
    }
}