package com.aionemu.gameserver.database.idfactory;

public enum GSIDStorageType {
    PLAYER(10000000000000000L, 9223372036854775806L),
    ;

    private long minId;
    private long maxId;
    private int segment;

    GSIDStorageType(final long minId, final long maxId) {
        this.minId = minId;
        this.maxId = maxId;
    }

    GSIDStorageType(final long minId, final long maxId, final int segment) {
        this.minId = minId;
        this.maxId = maxId;
        this.segment = segment;
    }

    public long getMinId() {
        return this.minId;
    }

    public long getMaxId() {
        return this.maxId;
    }

    public int getSegment() {
        return this.segment;
    }
}
