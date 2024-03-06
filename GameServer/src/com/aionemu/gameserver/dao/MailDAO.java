package com.aionemu.gameserver.dao;

import com.aionemu.gameserver.model.gameobjects.Letter;
import com.aionemu.gameserver.model.gameobjects.player.Mailbox;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;

import java.sql.Timestamp;

public abstract class MailDAO implements IDFactoryAwareDAO
{
	@Override
	public String getClassName() {
		return MailDAO.class.getName();
	}
	
	public abstract boolean storeLetter(Timestamp time, Letter letter);
	public abstract Mailbox loadPlayerMailbox(Player player);
	public abstract void storeMailbox(Player player);
	public abstract boolean deleteLetter(int letterId);
	public abstract void updateOfflineMailCounter(PlayerCommonData recipientCommonData);
	public abstract int mailCount(int playerId);
	public abstract int unreadedMails(int playerId);
}