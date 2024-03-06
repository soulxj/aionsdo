package com.aionemu.gameserver.services.player;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.dao.ItemStoneListDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ItemUpdateTask
  implements Runnable
{
  private static final Logger log = LoggerFactory.getLogger(ItemUpdateTask.class);
  private final int playerId;

  ItemUpdateTask(int playerId)
  {
    this.playerId = playerId;
  }

  public void run()
  {
    Player player = World.getInstance().findPlayer(playerId);
    if (player != null)
      try {
        DAOManager.getDAO(InventoryDAO.class).store(player);
        DAOManager.getDAO(ItemStoneListDAO.class).save(player);
      }
      catch (Exception ex) {
        log.error("Exception during periodic saving of player items " + player.getName(), ex);
      }
  }
}