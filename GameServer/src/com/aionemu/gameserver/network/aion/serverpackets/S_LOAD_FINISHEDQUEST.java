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

import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.questEngine.model.QuestState;
import javolution.util.FastList;

import static com.aionemu.gameserver.dataholders.DataManager.QUEST_DATA;

public class S_LOAD_FINISHEDQUEST extends AionServerPacket
{
	private FastList<QuestState> questState;
	
	public S_LOAD_FINISHEDQUEST(FastList<QuestState> questState) {
		this.questState = questState;
	}
	
	@Override
	protected void writeImpl(AionConnection con) {
		writeH(0x01);
		writeH(-questState.size() & 0xFFFF);
		for (QuestState qs: questState) {
			writeD(qs.getQuestId());
			if(GSConfig.SERVER_COUNTRY_CODE == 0 ||GSConfig.SERVER_COUNTRY_CODE == 5){
				writeD(qs.getCompleteCount());
			} else {
				writeC(qs.getCompleteCount());
			}
		}
		FastList.recycle(questState);
		questState = null;
	}
}