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
        System.out.println("Serializing message");
        StringBuilder buffer = new StringBuilder();
        System.out.println("Serializing message1");
        buffer.append(message.getCommand() + "\n");
        System.out.println("Serializing message2");
        for(Map.Entry<String, String> header : message.getHeaders().entrySet())
        {
            buffer.append(header.getKey()).append(":").append(header.getValue()).append("\n");
        }
        System.out.println("Serializing message3");
        buffer.append("\n");
        buffer.append(message.getBody());
        buffer.append('\0');
        System.out.println("serialized message");
        System.out.println("THE Serialized MESSAGE: " + buffer.toString());
        return buffer.toString();
    }

    public static StompMessage deserialize(String message)
    {
        System.out.println("deserializing message");
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

        //result.setBody(body);

        StompMessage result = new StompMessage(command, body, headers);
        System.out.println("deserialized message");
        return result;
    }
}

