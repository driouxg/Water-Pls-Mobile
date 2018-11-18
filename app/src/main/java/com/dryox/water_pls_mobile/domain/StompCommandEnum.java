package com.dryox.water_pls_mobile.domain;

public enum StompCommandEnum {
    SEND("SEND"),
    CONNECT("CONNECT"),
    SUBSCRIBE("SUBSCRIBE"),
    UNSUBSCRIBE("UNSUBSCRIBE"),
    COMMIT("COMMIT"),
    BEGIN("BEGIN"),
    ABORT("ABORT"),
    ACK("ACK"),
    NACK("NACK"),
    DISCONNECT("DISCONNECT");

    private String command;

    StompCommandEnum(String command) {
        this.command = command;
    }

    public static StompCommandEnum fromString(String resourceKey) {
        for (StompCommandEnum resource : StompCommandEnum.values()) {
            if (resource.command.equals(resourceKey)) {
                return resource;
            }
        }
        return null;
    }

    public static boolean isStompCommand(String command) {
        for (StompCommandEnum resource : StompCommandEnum.values()) {
            if (resource.command.equals(command)) {
                return true;
            }
        }
        return false;
    }
}
