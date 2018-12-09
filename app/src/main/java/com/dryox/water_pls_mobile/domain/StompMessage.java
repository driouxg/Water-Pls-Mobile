package com.dryox.water_pls_mobile.domain;

import com.dryox.water_pls_mobile.domain.value.ValueObject;

import java.util.Map;

/**
 * Created by chen0 on 10/12/2017.
 */
public class StompMessage extends ValueObject {
    private Map<String, String> headers;
    private String body;
    private StompCommand stompCommand;
    private String command;

    public StompMessage(StompCommand stompCommand, String body, Map<String, String> headers) {
        this.stompCommand = stompCommand;
        command = stompCommand.GetCommand();

        this.body = body;
        this.headers = headers;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public StompCommand getStompCommand() {
        return stompCommand;
    }

    public String getCommand() {
        return command;
    }
}

