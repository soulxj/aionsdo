package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.team.legion.Legion;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.legion.LegionService;
import javolution.util.FastList;

public class S_GUILD_LIST extends AionServerPacket {

    private FastList<Legion> legions;

    public S_GUILD_LIST(FastList<Legion> legions) {
        this.legions = legions;
    }

    @Override
    protected void writeImpl(AionConnection con) {
        writeH(legions.size());
        for (Legion legion : legions) {
            writeD(legion.getLegionId());
            writeS(legion.getLegionName());
            writeS(LegionService.getInstance().getBrigadeGeneralName(legion));
            writeC(legion.getLegionLevel());
            writeS(legion.getLegionDescription());
            writeC(legion.getLegionJoinType());
            writeH(legion.getMinLevel());
            writeH(legion.getActivity());
            writeD(legion.getGrowthLevel());//growth level
            writeC(1);//unk
        }
    }
}