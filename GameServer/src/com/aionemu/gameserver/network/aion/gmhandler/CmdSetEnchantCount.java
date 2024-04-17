package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author Soulxj
 */
public class CmdSetEnchantCount extends AbstractGMHandler {

    public CmdSetEnchantCount(Player admin, String params) {
        super(admin, params);
        run();
    }

    public void run() {
        //todo
//        Player t = admin;
//
//        if (admin.getTarget() != null && admin.getTarget() instanceof Player)
//            t = World.getInstance().findPlayer(Util.convertName(admin.getTarget().getName()));
//
//        String[] p = params.split(" ");
//        if (p.length != 3) {
//            PacketSendUtility.sendMessage(admin, "not enough parameters");
//            return;
//        }
//
//        int expand = 0;
//        expand += Integer.parseInt(p[0]);
//        expand += Integer.parseInt(p[1]);
//        expand += Integer.parseInt(p[2]);
//
//        if (t.getNpcExpands() + t.getQuestExpands() < expand) {
//            t.setNpcExpands(expand);
//            PacketSendUtility.sendPacket(t, SM_CUBE_UPDATE.cubeSize(StorageType.CUBE, t));
//        }
    }

}
