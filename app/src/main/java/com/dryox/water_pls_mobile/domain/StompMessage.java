package com.dryox.water_pls_mobile.domain;

import java.util.Map;

/**
 * Created by chen0 on 10/12/2017.
 */
public class StompMessage extends ValueObject {
    private Map<String, String> headers;
    private String body;
    private StompCommand command;

    public StompMessage(StompCommand command, String body, Map<String, String> headers) {
        this.command = command;
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

    public StompCommand getCommand() {
        return command;
    }
}

