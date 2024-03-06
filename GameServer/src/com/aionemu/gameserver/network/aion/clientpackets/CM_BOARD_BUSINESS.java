package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.serverpackets.S_BOARD_BUSINESS_MODEL;
import com.aionemu.gameserver.services.player.ShugoSweepService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CM_BOARD_BUSINESS extends AionClientPacket {

    private static final Logger log = LoggerFactory.getLogger(CM_BOARD_BUSINESS.class);
    private int action;
    private boolean unk;

    public CM_BOARD_BUSINESS(int opcode, AionConnection.State state, AionConnection.State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        this.action = readC();
        switch (this.action){
            case 2:
            case 3:
                this.unk = readC() == 1;
                break;
        }
    }

    @Override
    protected void runImpl() {
        log.info("CM_BOARD_BUSINESS : action => " + this.action);
        Player player = getConnection().getActivePlayer();
        //action 1 = open, 2 launch, 3 accepte move;
        switch (this.action){
            case 1:
                ShugoSweepService.getInstance().onOpenBoard(player);
                break;
            case 2 :
                ShugoSweepService.getInstance().onLaunchDice(player);
                break;
            case 3 :
                ShugoSweepService.getInstance().onAcceptMove(player);
                break;
        }
    }
}