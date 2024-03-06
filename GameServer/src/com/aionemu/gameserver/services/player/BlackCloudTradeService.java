package com.aionemu.gameserver.services.player;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.AccountBlackCloudDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.blackcloud.BlackcloudItem;
import com.aionemu.gameserver.model.gameobjects.blackcloud.BlackcloudLetter;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.StorageType;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.S_NPSHOP_GOODS_COUNT;
import com.aionemu.gameserver.network.aion.serverpackets.S_MESSAGE_CODE;
import com.aionemu.gameserver.network.aion.serverpackets.need.S_NPSHOP_GOODS_CHANGE;
import com.aionemu.gameserver.network.aion.serverpackets.need.S_RESPONSE_NPSHOP_GOODS_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.need.S_RESPONSE_NPSHOP_GOODS_RECV;
import com.aionemu.gameserver.services.WebshopService;
import com.aionemu.gameserver.services.item.ItemFactory;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.periodicaction.PeriodicAction;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Calendar;

public class BlackCloudTradeService {

    private Logger log = LoggerFactory.getLogger(BlackCloudTradeService.class);

    public void onLogin(Player player) {
        player.setBlackcloudLetters(DAOManager.getDAO(AccountBlackCloudDAO.class).loadAccountBlackcloud(player.getPlayerAccount()));
        PacketSendUtility.sendPacket(player, new S_NPSHOP_GOODS_COUNT(player.getBlackcloudLetters().size()));
        PacketSendUtility.sendPacket(player, new S_RESPONSE_NPSHOP_GOODS_LIST(player));
        log.info("Login Black Cloud size : " + player.getBlackcloudLetters().size());
        player.getController().addTask(TaskId.BLACK_CLOUD_CHECK, ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                WebshopService.getInstance().check(player);
            }
        }, 60000, 60000));
    }

    public void sendBlackCloudMail(Player player, int itemId, int itemCount) {
        String title = "Your purchase in the store.";
        String message = "Thank you for shopping in our store and supporting the server.";
        Timestamp time = new Timestamp(Calendar.getInstance().getTimeInMillis());
        BlackcloudLetter blackcloudLetter = new BlackcloudLetter(IDFactory.getInstance().nextId(), title, message, time, true);
        BlackcloudItem blackcloudItem = new BlackcloudItem(blackcloudLetter.getObjectId(), itemId, itemCount);
        blackcloudLetter.getAttachedItem().add(blackcloudItem);

        player.getBlackcloudLetters().put(blackcloudLetter.getObjectId(), blackcloudLetter);
        PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_POSTMAN_NOTIFY);
        PacketSendUtility.sendPacket(player, new S_NPSHOP_GOODS_CHANGE(1));
        PacketSendUtility.sendPacket(player, new S_NPSHOP_GOODS_COUNT(player.getBlackcloudLetters().size()));

        log.info("Black Cloud size : " + player.getBlackcloudLetters().size());

        DAOManager.getDAO(AccountBlackCloudDAO.class).addBlackcloud(player.getPlayerAccount(), blackcloudLetter);
        DAOManager.getDAO(AccountBlackCloudDAO.class).addBlackcloudItem(blackcloudItem);
    }

    public void onOpenBlackCloudBox(Player player) {
        PacketSendUtility.sendPacket(player, new S_RESPONSE_NPSHOP_GOODS_LIST(player));
    }

    public void onClaimItem(Player player, int objectId) {
        log.info("id : " + objectId);
        BlackcloudLetter letter = player.getBlackcloudLetters().get(objectId);

        if (player.getInventory().isFull()) {
            PacketSendUtility.sendPacket(player, S_MESSAGE_CODE.STR_MSG_FULL_INVENTORY);
            return;
        }
        letter.getAttachedItem().forEach(blackcloudItem -> ItemService.addItem(player, blackcloudItem.getItemId(), blackcloudItem.getItemCount()));

        player.getBlackcloudLetters().remove(letter.getObjectId());
        PacketSendUtility.sendPacket(player, new S_RESPONSE_NPSHOP_GOODS_RECV(letter.getObjectId()));
        //onOpenBlackCloudBox(player);
        DAOManager.getDAO(AccountBlackCloudDAO.class).deleteBlackcloud((int) objectId);
        DAOManager.getDAO(AccountBlackCloudDAO.class).deleteBlackcloudItem((int) objectId);
    }

    public static BlackCloudTradeService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        protected static final BlackCloudTradeService instance = new BlackCloudTradeService();
    }
}
