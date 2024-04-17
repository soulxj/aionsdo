/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 * <p>
 * Aion-Lightning is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * Aion-Lightning is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License
 * along with Aion-Lightning.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;

/**
 * @author Alcapwnd
 */
public class CmdWish extends AbstractGMHandler {

    public CmdWish(Player admin, String params) {
        super(admin, params);
        run();
    }

    public void run() {

        PacketSendUtility.sendMessage(admin, "function not impl");
        Player t = admin;

        if (admin.getTarget() != null && admin.getTarget() instanceof Player)
            t = World.getInstance().findPlayer(Util.convertName(admin.getTarget().getName()));

        String[] p = params.split(" ");
        if (p.length != 2) {
            PacketSendUtility.sendMessage(admin, "not enough parameters");
            return;
        }


        int require = 0;


        try {
            require = Integer.parseInt(p[0]);
        } catch (Exception e) {
        }


        if (require > 0) {
            Integer qty = require;
            String name = p[1];

            if (qty > 0 && name != null) {
                ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(name);
                if (itemTemplate == null) {
                    PacketSendUtility.sendMessage(admin, "Item is incorrect: " + name);
                } else {
                    long count = ItemService.addItem(t, itemTemplate.getTemplateId(), qty);
                    if (count == 0) {
                        PacketSendUtility.sendMessage(admin, "You successfully gave " + qty + " x [item:" + itemTemplate.getTemplateId() + "] to " + t.getName() + ".");
                    } else {
                        PacketSendUtility.sendMessage(admin, "Item couldn't be added");
                    }
                }
            }
        }else {
            String itemDesc = p[0];
            Integer countitems = Integer.parseInt(p[1]);

            if (itemDesc != null && countitems > 0) {
                ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemDesc);
                if (itemTemplate != null) {
                    long count = ItemService.addItem(t, itemTemplate.getTemplateId(), countitems);
                    if (count == 0) {
                        PacketSendUtility.sendMessage(admin, "You successfully gave " + countitems + " x [item:" + itemTemplate.getTemplateId() + "] ID: " + itemTemplate.getTemplateId() + " to " + t.getName() + ".");
                    } else {
                        PacketSendUtility.sendMessage(admin, "Item couldn't be added");
                    }
                }
            }
        }

    }
}
