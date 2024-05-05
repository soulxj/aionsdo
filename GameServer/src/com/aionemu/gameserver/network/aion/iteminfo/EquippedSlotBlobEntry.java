package com.aionemu.gameserver.network.aion.iteminfo;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;

import java.nio.ByteBuffer;

public class EquippedSlotBlobEntry extends ItemBlobEntry
{
	EquippedSlotBlobEntry() {
		super(ItemBlobType.EQUIPPED_SLOT);
	}
	
	@Override
	public void writeThisBlob(ByteBuffer buf) {
		Item item = ownerItem;

		int slot = item.getEquipmentSlot();
		if (item.getItemTemplate().isTwoHandWeapon() && slot == ItemSlot.MAIN_HAND.getSlotIdMask()) {
			slot = ItemSlot.MAIN_OR_SUB.getSlotIdMask();
		} else if (item.getItemTemplate().isTwoHandWeapon() && slot == ItemSlot.MAIN_OFF_HAND.getSlotIdMask()) {
			slot = ItemSlot.MAIN_OFF_OR_SUB_OFF.getSlotIdMask();
		}
		writeD(buf, item.isEquipped() ? slot : 0);
	}
	
	@Override
	public int getSize() {
		return 4;
	}
}