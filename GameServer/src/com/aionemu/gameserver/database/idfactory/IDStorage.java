package com.aionemu.gameserver.database.idfactory;

import com.aionemu.gameserver.utils.IdUtils;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;

public class IDStorage {
    private static final Logger log = LoggerFactory.getLogger(IDStorage.class);
    private final BitSet idList = new BitSet();
    private final CloseableReentrantLock lock = new CloseableReentrantLock();
    private final long minId;
    private final long maxId;
    private final String storageName;
    private volatile int nextMinId = 1;
    private volatile int useMaxId;

    public IDStorage(long minId, long maxId, String storageName) {
        this.minId = minId;
        this.maxId = maxId;
        this.useMaxId = 0;
        this.storageName = storageName;
        this.lockIds(minId);
    }

    public IDStorage(long minId, long maxId, int segment, String storageName) {
        this.minId = minId;
        this.maxId = maxId;
        this.useMaxId = 0;
        this.storageName = storageName;
        this.lockIds(minId);
        if (segment <= 0) return;
        long i = minId;
        while (i <= maxId) {
            int minIndex = (int) (i - minId);//0      2048
            int maxIndex = minIndex + segment;//0-1024 2048-3072
            this.lockSegment(minIndex, maxIndex);
            i += (long) (segment * 2);
        }
    }

    public long tryNextId(){
        int id = this.idList.nextClearBit(this.nextMinId);
        if (this.maxId > 0 && (long) id + this.minId >= this.maxId) {
            throw new IDStorageError("All id's are used, please clear your database");
        }
        return this.minId + id;
    }

    public long nextMaxId() {
        return nextId(true);
    }

    public long nextId() {
        return nextId(false);
    }

    public void resetMinId(int minId){
        CloseableReentrantLock closeableLock = this.lock.open();
        Throwable throwable = null;
        try {
            resetMinIdLocked(minId);
        } catch (Throwable e) {
            throwable = e;
            throw e;
        } finally {
            if (closeableLock != null) {
                if (throwable != null) {
                    try {
                        closeableLock.close();
                    } catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                } else {
                    closeableLock.close();
                }
            }
        }
    }

    private void resetMinIdLocked(int minId){
        nextMinId = minId;
    }

    private long nextId(boolean useMax) {
        CloseableReentrantLock closeableLock = this.lock.open();
        Throwable throwable = null;
        try {
            int id;
            if (useMax) {
                id = useMaxId + 1;
            } else {
                if(this.nextMinId >= Integer.MAX_VALUE){
                    this.nextMinId = 1;
                    log.info("{} reset nextMinId.", storageName);
                }
                id = this.idList.nextClearBit(this.nextMinId);
            }
            if (this.maxId > 0 && ((long) id + this.minId) >= this.maxId) {
                resetMinId(1);
                id = this.idList.nextClearBit(this.nextMinId);
                if (((long) id + this.minId) >= this.maxId) {
                    throw new IDStorageError("All id's are used, please clear your database");
                }
            }
            if (id > useMaxId) {
                useMaxId = id;
            }
            this.idList.set(id);
            this.nextMinId = id + 1;
            return this.minId + IdUtils.int2UInt32(id);
        } catch (Throwable e) {
            throwable = e;
            throw e;
        } finally {
            if (closeableLock != null) {
                if (throwable != null) {
                    try {
                        closeableLock.close();
                    } catch (Throwable throwable2) {
                        throwable.addSuppressed(throwable2);
                    }
                } else {
                    closeableLock.close();
                }
            }
        }
    }

    public /* varargs */ void lockIds(long... ids) {
        CloseableReentrantLock closeableLock = this.lock.open();
        Throwable throwable = null;
        try {
            for(long value : ids) {
                int id = (int) (IdUtils.long2UInt32(value) - minId);
                boolean status = this.idList.get(id);
                if (status) {
                    throw new IDStorageError("Storage:" + this.storageName + " ID: " + id + " value: " + value + " is already taken, fatal error!!!");
                }
                this.idList.set(id);
            }
        } catch (Throwable e) {
            throwable = e;
            throw e;
        } finally {
            if (closeableLock != null) {
                if (throwable != null) {
                    try {
                        closeableLock.close();
                    } catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                } else {
                    closeableLock.close();
                }
            }
        }
    }

    public boolean lockId(long value) {
        CloseableReentrantLock closeableLock = this.lock.open();
        Throwable throwable = null;
        boolean ret = false;
        try {
            ret = lockIdLocked(value);
        } catch (Throwable e) {
            throwable = e;
            throw e;
        } finally {
            if (closeableLock != null) {
                if (throwable != null) {
                    try {
                        closeableLock.close();
                    } catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                } else {
                    closeableLock.close();
                }
            }
        }
        return ret;
    }

    private boolean lockIdLocked(long _value) {
        long value = IdUtils.long2UInt32(_value);
        int id = (int) (value - this.minId);
        boolean status = this.idList.get(id);
        if (status) {
            return false;//throw new IDStorageError("Storage:" + this.storageName + " ID: " + id + " value: " + value + " is already taken, fatal error!!!");
        }
        this.idList.set(id);
        return true;
    }

    public void lockMaxId(long value) {
        CloseableReentrantLock closeableLock = this.lock.open();
        Throwable throwable = null;
        try {
            lockMaxIdLocked(value);
        } catch (Throwable e) {
            throwable = e;
            throw e;
        } finally {
            if (closeableLock != null) {
                if (throwable != null) {
                    try {
                        closeableLock.close();
                    } catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                } else {
                    closeableLock.close();
                }
            }
        }
    }

    private void lockMaxIdLocked(long _value) {
        long value = IdUtils.long2UInt32(_value);
        int id = (int) (value - this.minId);
        if (useMaxId < id) {
            useMaxId = id;
        }
    }

    private void lockBasicDBListLocked(Object value) {
        BasicDBList ids = (BasicDBList) ((BasicDBObject) value).get("ids");
        Iterator<Object> iterator = ids.iterator();
        while (iterator.hasNext()) {
            Object id = iterator.next();
            if (id instanceof Long) {
                this.lockIdLocked((Long) id);
                continue;
            }
            this.lockBasicDBListLocked(id);
        }
    }

    public void lockIds(Object value) {
        CloseableReentrantLock closeableLock = this.lock.open();
        Throwable throwable = null;
        try {
            if (value instanceof DBObject) {
                this.lockBasicDBListLocked(value);
            } else {
                this.lockIdLocked((Long) value);
            }
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            if (closeableLock != null) {
                if (throwable != null) {
                    try {
                        closeableLock.close();
                    } catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                } else {
                    closeableLock.close();
                }
            }
        }
    }


    private void releaseIdLocked(long _value){
        long value = IdUtils.long2UInt32(_value);
        int id = (int) (value - this.minId);
        if (id < 0) {
            log.error("Storage: {} ID [{}] value [{}] is bad, can't release it. ", this.storageName, id, value);
            return;
        }
        boolean status = this.idList.get(id);
        if (!status) {
            log.error("Storage: {} ID [{}] value [{}] is not taken, can't release it.", this.storageName, id, value);
            return;
        }
        this.idList.clear(id);
    }

    public void releaseId(long _value){
        if(_value == 0){
            return;
        }
        CloseableReentrantLock closeableLock = this.lock.open();
        Throwable throwable = null;
        try {
            releaseIdLocked(_value);
        } catch (Throwable id) {
            throwable = id;
            throw id;
        } finally {
            if (closeableLock != null) {
                if (throwable != null) {
                    try {
                        closeableLock.close();
                    } catch (Throwable id) {
                        throwable.addSuppressed(id);
                    }
                } else {
                    closeableLock.close();
                }
            }
        }
    }

    public void releaseIds(Collection<Integer> ids) {
        CloseableReentrantLock closeableLock = this.lock.open();
        Throwable throwable = null;
        try {
            for (Integer _id : ids) {
                releaseIdLocked(_id);
            }
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            if (closeableLock != null) {
                if (throwable != null) {
                    try {
                        closeableLock.close();
                    } catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                } else {
                    closeableLock.close();
                }
            }
        }
    }

    public int getUsedCount() {
        CloseableReentrantLock closeableLock = this.lock.open();
        Throwable throwable = null;
        try {
            int n = this.idList.cardinality();
            return n;
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            if (closeableLock != null) {
                if (throwable != null) {
                    try {
                        closeableLock.close();
                    } catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                } else {
                    closeableLock.close();
                }
            }
        }
    }

    private void lockSegment(int minIndex, int maxIndex) {
        CloseableReentrantLock closeableLock = this.lock.open();
        Throwable throwable = null;
        try {
            this.idList.set(minIndex, maxIndex);
        } catch (Throwable throwable2) {
            throwable = throwable2;
            throw throwable2;
        } finally {
            if (closeableLock != null) {
                if (throwable != null) {
                    try {
                        closeableLock.close();
                    } catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                } else {
                    closeableLock.close();
                }
            }
        }
    }
}

