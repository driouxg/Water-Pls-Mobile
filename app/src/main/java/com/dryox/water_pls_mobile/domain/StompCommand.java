package com.dryox.water_pls_mobile.domain;

import com.dryox.water_pls_mobile.domain.value.ValueObject;

public class StompCommand extends ValueObject {
    private String command;

    public StompCommand(String command) {
        this.command = command;
        ValidateCommand();
    }

    private void ValidateCommand() {
        if (!StompCommandEnum.isStompCommand(command)) {
            throw new IllegalArgumentException("Not a valid stomp command: " + command);
        }
    }

    public String GetCommand() {
        return command;
    }
}
