package com.aionemu.gameserver.database.idfactory;

public class IDStorageError extends Error {
    public IDStorageError() {
    }

    public IDStorageError(String message) {
        super(message);
    }

    public IDStorageError(String message, Throwable cause) {
        super(message, cause);
    }

    public IDStorageError(Throwable cause) {
        super(cause);
    }
}
