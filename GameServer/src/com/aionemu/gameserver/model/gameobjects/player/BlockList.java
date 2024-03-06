package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.commons.utils.internal.chmv8.PlatformDependent;

import java.util.Iterator;
import java.util.Map;

public class BlockList implements Iterable<BlockedPlayer>
{
	public static final int MAX_BLOCKS = 10;
	
	private final Map<Integer, BlockedPlayer> blockedList;
	
	public BlockList() {
		this.blockedList = PlatformDependent.newConcurrentHashMap();
	}
	
	public BlockList(Map<Integer, BlockedPlayer> initialList) {
		this.blockedList = PlatformDependent.newConcurrentHashMap(initialList);
	}
	
	public void add(BlockedPlayer plr) {
		blockedList.put(plr.getObjId(), plr);
	}
	
	public void remove(int objIdOfPlayer) {
		blockedList.remove(objIdOfPlayer);
	}
	
	public BlockedPlayer getBlockedPlayer(String name) {
		Iterator<BlockedPlayer> iterator = blockedList.values().iterator();
		while (iterator.hasNext()) {
			BlockedPlayer entry = iterator.next();
			if (entry.getName().equalsIgnoreCase(name))
				return entry;
		}
		return null;
	}
	
	public BlockedPlayer getBlockedPlayer(int playerObjId) {
		return blockedList.get(playerObjId);
	}
	
	public boolean contains(int playerObjectId) {
		return blockedList.containsKey(playerObjectId);
	}
	
	public int getSize() {
		return blockedList.size();
	}
	
	public boolean isFull() {
		return getSize() >= MAX_BLOCKS;
	}
	
	@Override
	public Iterator<BlockedPlayer> iterator() {
		return blockedList.values().iterator();
	}
}