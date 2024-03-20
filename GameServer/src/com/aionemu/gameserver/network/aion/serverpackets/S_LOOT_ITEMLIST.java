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

import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import javolution.util.FastList;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class S_LOOT_ITEMLIST extends AionServerPacket {
    private int targetObjectId;
    private FastList<DropItem> dropItems;

    public S_LOOT_ITEMLIST(int targetObjectId, Set<DropItem> setItems, Player player) {
        this.targetObjectId = targetObjectId;
        this.dropItems = new FastList<DropItem>();
        if (setItems == null) {
            LoggerFactory.getLogger(S_LOOT_ITEMLIST.class).warn("null Set<DropItem>, skip");
            return;
        }
        for (DropItem item : setItems) {
            if (item.getPlayerObjId() == 0 || player.getObjectId() == item.getPlayerObjId())
                dropItems.add(item);
        }
    }

    @Override
    protected void writeImpl(AionConnection con) {
        writeD(targetObjectId);
        writeH(dropItems.size());
        for (DropItem dropItem : dropItems) {
            writeD(dropItem.getIndex());
            writeD(dropItem.getItemId());
            writeD((int) dropItem.getCount());
            writeC(dropItem.getOptionalSocket());
            writeC(dropItem.getDropTemplate().isTradeable() ? 0 : 1);
        }
        FastList.recycle(dropItems);
    }
}