package com.dryox.water_pls_mobile.client;

import com.dryox.water_pls_mobile.domain.StompCommand;
import com.dryox.water_pls_mobile.domain.StompMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen0 on 10/12/2017.
 */

public class StompMessageSerializer {
    public static String serialize(StompMessage message){
        StringBuilder buffer = new StringBuilder();
        buffer.append(message.getCommand() + "\n");
        for(Map.Entry<String, String> header : message.getHeaders().entrySet())
        {
            buffer.append(header.getKey()).append(":").append(header.getValue()).append("\n");
        }
        buffer.append("\n");
        buffer.append(message.getBody());
        buffer.append('\0');
        return buffer.toString();
    }

    public static StompMessage deserialize(String message)
    {
        String[] lines = message.split("\n");

        StompCommand command = new StompCommand(lines[0].trim());
        Map<String, String> headers = new HashMap<>();

        //StompMessage result = new StompMessage(command);

        int i=1;
        for(; i < lines.length; ++i){
            String line = lines[i].trim();
            if(line.equals("")){
                break;
            }
            String[] parts = line.split(":");
            String name = parts[0].trim();
            String value = "";
            if(parts.length==2){
                value = parts[1].trim();
            }
            headers.put(name, value);
        }

        StringBuilder sb = new StringBuilder();

        for(; i < lines.length; ++i){
            sb.append(lines[i]);
        }

        String body = sb.toString().trim();

        StompMessage result = new StompMessage(command, body, headers);
        return result;
    }
}

