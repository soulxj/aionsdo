package com.aionemu.loginserver.model;

public enum SielEnergyType {

    NONE(1),
    TRIAL(2),
    MEMBERSHIP(3);

    private int id;

    private SielEnergyType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SielEnergyType getSielTypeById(int id) {
        for (SielEnergyType lt : values()) {
            if (lt.id == id)
                return lt;
        }
        throw new IllegalArgumentException("Unsupported SielEnergyType : " + id);
    }
}
