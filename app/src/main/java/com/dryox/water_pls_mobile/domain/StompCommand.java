package com.dryox.water_pls_mobile.domain;

public class StompCommand extends ValueObject {
    private String command;

    public StompCommand(String command) {
        this.command = command;
        validateCommand();
    }

    private void validateCommand() {
        if (!StompCommandEnum.isStompCommand(command)) {
            throw new IllegalArgumentException("Not a valid stomp command.");
        }
    }

    public String getCommand() {
        return command;
    }
}
