package com.aionemu.gameserver.network.aion.serverpackets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.templates.tradelist.TradeListTemplate;
import com.aionemu.gameserver.model.templates.tradelist.TradeListTemplate.TradeTab;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class S_STORE_PURCHASE_INFO extends AionServerPacket
{
	
	private Logger log = LoggerFactory.getLogger(S_STORE_PURCHASE_INFO.class);
	
	private int targetObjectId;
	private TradeListTemplate plist;
	private int sellPercentage;
	//private byte action = 1;

	public S_STORE_PURCHASE_INFO(int targetObjectId, TradeListTemplate plist, int sellPercentage) {
		this.targetObjectId = targetObjectId;
		this.plist = plist;
		this.sellPercentage = sellPercentage;
	}

	protected void writeImpl(AionConnection con) {
		if ((plist != null) && (plist.getNpcId() != 0) && (plist.getCount() != 0)) {
			writeD(targetObjectId);
			writeC(plist.getTradeNpcType().index());
			writeD(sellPercentage);// Buy Price * (sellPercentage / 100) = Display price.
			writeH(256);
			writeH(plist.getCount());
			for (TradeTab tradeTabl : plist.getTradeTablist()) {
				writeD(tradeTabl.getId());
			}
			
			
			
		} else {
			writeD(targetObjectId);
			writeD(5121); // Buy Price * (sellPercentage / 100) = Display price.
			writeD(65792);
			writeC(0);
		}
	}
}