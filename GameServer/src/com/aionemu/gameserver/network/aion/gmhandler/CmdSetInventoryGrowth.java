package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.StorageType;
import com.aionemu.gameserver.network.aion.serverpackets.S_EVENT;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;

/**
 * @author Soulxj
 */
public class CmdSetInventoryGrowth extends AbstractGMHandler {

    public CmdSetInventoryGrowth(Player admin, String params) {
        super(admin, params);
        run();
    }

    public void run() {
        Player t = admin;

        if (admin.getTarget() != null && admin.getTarget() instanceof Player)
            t = World.getInstance().findPlayer(Util.convertName(admin.getTarget().getName()));

        String[] p = params.split(" ");
        if (p.length != 3) {
            PacketSendUtility.sendMessage(admin, "not enough parameters");
            return;
        }

        int expand = 0;
        expand += Integer.parseInt(p[0]);
        expand += Integer.parseInt(p[1]);
        expand += Integer.parseInt(p[2]);

        if (t.getNpcExpands() + t.getQuestExpands() < expand) {
            t.setNpcExpands(expand);
            PacketSendUtility.sendPacket(t, S_EVENT.cubeSize(StorageType.CUBE, t));
        }
    }

}
