/*
 * This file is part of Encom. **ENCOM FUCK OTHER SVN**
 *
 *  Encom is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Encom is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with Encom.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.gameserver.model.items.storage;

/****/
/** Author Rinzler (Encom)
/****/

public enum StorageType
{
	//Cube & Warehouse.
	CUBE(0, 27, 9, 135), //2.7
	REGULAR_WAREHOUSE(1, 96, 8),
	ACCOUNT_WAREHOUSE(2, 16, 8),
	LEGION_WAREHOUSE(3, 56, 8),
	
	//Pet's Bag.
	PET_BAG_6(32, 6, 6),
    PET_BAG_12(33, 12, 6),
    PET_BAG_18(34, 18, 6),
    PET_BAG_24(35, 24, 6),
	
	//Cash Pet's Bag.
	CASH_PET_BAG_12(36, 12, 6),
	CASH_PET_BAG_18(37, 18, 6),
	CASH_PET_BAG_30(38, 30, 6),
	CASH_PET_BAG_16(39, 16, 6),
	CASH_PET_BAG_13A(40, 13, 6),
	CASH_PET_BAG_22(41, 22, 6),
	CASH_PET_BAG_13B(42, 13, 6),
	CASH_PET_BAG_20(43, 20, 6),
    CASH_PET_BAG_21(44, 21, 6),
	
	//Other.
	BROKER(126),
	MAILBOX(127);
	
	//Pet's.
	public static final int PET_BAG_MIN = 32;
	public static final int PET_BAG_MAX = 44;
	
	private int id;
	private int limit;
	private int length;
	private int specialLimit;
	
	private StorageType(int id, int limit, int length, int specialLimit) {
		this(id, limit, length);
		this.specialLimit = specialLimit;
	}
	
	private StorageType(int id, int limit, int length) {
		this(id);
		this.limit = limit;
		this.length = length;
	}
	
	private StorageType(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getSpecialLimit() {
		return specialLimit;
	}
	
	public static StorageType getStorageTypeById(int id) {
		for (StorageType st: values()) {
			if (st.id == id)
				return st;
		}
		return null;
	}
	
	public static int getStorageId(int limit, int length) {
		for (StorageType st: values()) {
			if (st.limit == limit && st.length == length)
				return st.id;
		}
		return -1;
	}
}