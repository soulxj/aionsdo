package playercommands;

import com.aionemu.gameserver.configs.administration.CommandsConfig;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.item.ItemPacketService;
import com.aionemu.gameserver.utils.PacketSendUtility;

import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

public class Enchant extends PlayerCommand {
    public Enchant() {
        super("enchant");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length != 0) {
            int i = 0;
            if ("level".startsWith(params[i])) {
                if (admin.getAccessLevel() < CommandsConfig.EQUIP) {
                    PacketSendUtility.sendMessage(admin, "You don't have enough rights to execute this command");
                    return;
                }
                int enchant = 0;
                try {
                    enchant = params[i + 1] == null ? enchant : Integer.parseInt(params[i + 1]);
                } catch (Exception ex) {
                    showHelpEnchant(admin);
                    return;
                }
                enchant(admin, enchant); // Only applying to the executing player (self)
                return;
            }
        }
        showHelp(admin);
    }

    private void enchant(Player player, int enchant) {
        for (Item targetItem : player.getEquipment().getEquippedItemsWithoutStigma()) {
            if (isUpgradable(targetItem)) {
                if (targetItem.getEnchantLevel() == enchant) {
                    continue;
                }
                enchant = Math.min(enchant, 15); // Ensure enchant level does not go beyond +15
                enchant = Math.max(enchant, 0); // Ensure enchant level is not negative

                targetItem.setEnchantLevel(enchant);
                if (targetItem.isEquipped()) {
                    player.getGameStats().updateStatsVisually();
                }
                ItemPacketService.updateItemAfterInfoChange(player, targetItem);
                targetItem.setPersistentState(PersistentState.UPDATE_REQUIRED);
            }
        }
        PacketSendUtility.sendMessage(player, "All equipped items were enchanted to level " + enchant);
    }

    private boolean isUpgradable(Item item) {
        if (item.getItemTemplate().isNoEnchant()) {
            return false;
        }
        if (item.getItemTemplate().isWeapon()) {
            return true;
        }
        if (item.getItemTemplate().isArmor()) {
            int at = item.getItemTemplate().getItemSlot();
            if (at == 1 || at == 2 || at == 8 || at == 16 || at == 32 || at == 2048 || at == 4096
                    || at == 32768 || at == 131072 || at == 262144) {
                return true;
            }
        }
        return false;
    }

    private void showHelp(Player admin) {
        PacketSendUtility.sendMessage(admin, ".enchant level 15");
    }

    private void showHelpEnchant(Player admin) {
        PacketSendUtility.sendMessage(admin, "Syntax: .enchant level <0-15>");
    }

    @Override
    public void onFail(Player player, String message) {
        showHelp(player);
    }
}
