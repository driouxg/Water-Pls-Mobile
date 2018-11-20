package com.dryox.water_pls_mobile.client;

import com.dryox.water_pls_mobile.domain.StompCommand;
import com.dryox.water_pls_mobile.domain.StompMessage;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StompMessageSerializerTest {

    String validCommand = "SEND";
    String validBody = "my body";
    String validHeader = "header";

    @Test
    public void Serialize_ValidStompMessage_SuccessfulToString() {
        StompMessage validMessage = GenerateValidStompMessage();
        String expectedMessage = GenerateSerializedMessage(validCommand, validBody, validHeader, validHeader);

        String actualMessage = StompMessageSerializer.serialize(validMessage);

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void Deserialize_ValidSerializedMessage_ReturnsValidStompMessage() {
        String serializedMessage = GenerateSerializedMessage(validCommand, validBody, validHeader, validHeader);
        StompMessage expectedMessage = GenerateValidStompMessage();

        StompMessage actualMessage = StompMessageSerializer.deserialize(serializedMessage);

        assertEquals(expectedMessage, actualMessage);
    }

    private StompMessage GenerateValidStompMessage() {
        StompCommand command = new StompCommand(validCommand);
        String body = validBody;
        Map<String, String> headers = new HashMap<>();
        headers.put(validHeader, validHeader);
        return new StompMessage(command, body, headers);
    }

    private String GenerateSerializedMessage(String command, String body, String header1, String header2) {
        return command + "\n" + header1 + ":" + header2 + "\n\n" + body + "\0";
    }
}