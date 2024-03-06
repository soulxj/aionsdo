
package playercommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.item.ItemChargeService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

public class cmd_augmentme extends PlayerCommand {
    public cmd_augmentme() {
        super("augment");
    }
    
    public void execute(Player player, String... params) {
        ItemChargeService.chargeItems(player, player.getEquipment().getEquippedItems(), 2);
        PacketSendUtility.sendMessage(player, "You've Successfully Augmented your Gear!");
    }
}
