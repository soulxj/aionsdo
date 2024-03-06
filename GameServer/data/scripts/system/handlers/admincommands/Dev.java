package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.battlePass.BattlePassSeason;
//import com.aionemu.gameserver.network.aion.serverpackets.S_BATTLEPASS_UPDATED;
import com.aionemu.gameserver.services.player.BlackCloudTradeService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Dev extends AdminCommand {


    public Dev() {
        super("dev");
    }

    @Override
    public void execute(Player player, String... params) {

        //BlackCloudTradeService.getInstance().sendBlackCloudMail(player, "Test blackCloudMessage", 188052245, 1);
        /*if (params.length < 1 || params.length > 2) {
            PacketSendUtility.sendMessage(player, "No parameters detected.\n" + "use //dev [passId] [level]");
            return;
        }

        int passid = 0;
        int level = 0;

        try {
            passid = Integer.parseInt(params[0]);
            level = Integer.parseInt(params[1]);
        }
        catch (NumberFormatException e) {
            PacketSendUtility.sendMessage(player, "Param 1 must be an integer or <all>.");
            return;
        }
        BattlePassSeason season = player.getPlayerBattlePass().getBattlePassSeason().get(passid);

        season.setLevel(level);
        PacketSendUtility.sendPacket(player, new S_BATTLEPASS_UPDATED(season));*/
    }
}
