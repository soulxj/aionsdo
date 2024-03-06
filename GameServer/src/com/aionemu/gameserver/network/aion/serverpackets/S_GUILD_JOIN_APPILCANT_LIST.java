package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.team.legion.LegionJoinRequest;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

import java.util.Collection;

public class S_GUILD_JOIN_APPILCANT_LIST extends AionServerPacket {

    private Collection<LegionJoinRequest> ljrList;

    public S_GUILD_JOIN_APPILCANT_LIST(Collection<LegionJoinRequest> ljrList) {
        this.ljrList = ljrList;
    }

    @Override
    protected void writeImpl(AionConnection con) {
        writeH(-ljrList.size());
        for (LegionJoinRequest ljr : ljrList) {
            writeD(ljr.getPlayerId());
            writeS(ljr.getPlayerName());
            writeC(ljr.getPlayerClass());
            writeC(ljr.getGenderId());
            writeH(ljr.getLevel());
            writeS(ljr.getMsg());
            writeD((int) ljr.getDate().getTime());
        }
    }
}