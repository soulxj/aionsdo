package com.aionemu.gameserver.database.idfactory;

import java.util.concurrent.locks.ReentrantLock;

public class CloseableReentrantLock extends ReentrantLock implements AutoCloseable {
    public CloseableReentrantLock() {
    }

    public CloseableReentrantLock open() {
        this.lock();
        return this;
    }

    public void close() {
        this.unlock();
    }
}
