package com.dryox.water_pls_mobile.service.asynctask;

import android.os.AsyncTask;

import com.dryox.water_pls_mobile.client.StompMessageListener;
import com.dryox.water_pls_mobile.client.TopicHandler;
import com.dryox.water_pls_mobile.client.WebSocketClient;
import com.dryox.water_pls_mobile.domain.StompCommand;
import com.dryox.water_pls_mobile.domain.StompMessage;
import com.dryox.water_pls_mobile.domain.value.DonatorVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class ConnectDonatorToServer extends AsyncTask<Void, Void, Void> {

    private DonatorVO donatorVO;

    public ConnectDonatorToServer(DonatorVO donatorVO) {
        this.donatorVO = donatorVO;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        // Need to make the springbootwebsocketclient a singleton, that way we dont keep creating a bunch of instances of it
        String topicName = "/secured/user/queue/specific-user-" + donatorVO.getFirstNameValue();
        String topicName2 = "/secured/user/queue/specific-user";
        String topicName3 = "/secured/user/queue/specific-user-"+donatorVO.getFirstNameValue();
        String topicName4 = "/secured/user/queue/specific-user" + donatorVO.getFirstNameValue();
        String topicName5 = "/user/queue/updates-" + donatorVO.getFirstNameValue();
        String topicName6 = "/queue/updates-" + donatorVO.getFirstNameValue();


        WebSocketClient client = new WebSocketClient();

        TopicHandler handler = new TopicHandler();
        handler.addListener(new StompMessageListener() {
            @Override
            public void onMessage(StompMessage message) {
                System.out.println(message.getHeader("destination") + ": " + message.getBody());
            }
        });

        client.connect("ws://10.0.2.2:8091/websocket-example");
        client.subscribe(topicName6);
        client.subscribe(topicName5);
        client.subscribe(topicName4);
        client.subscribe(topicName3);
        client.subscribe(topicName2);
        client.subscribe(topicName, handler);

        client.sendStompMessage(buildSendMessage(donatorVO.toJSON(), new HashMap<String, String>() {{
            put("destination", "/api/donator/register");
        }}));

        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";

        try {
            json = objectMapper.writeValueAsString("myUsername");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        client.sendStompMessage(buildSendMessage(json, new HashMap<String, String>() {{
            put("destination", "/api/donator/search");
        }}));

        return null;
    }

    private StompMessage buildSendMessage(String json, Map<String, String> headers) {
        //eaders.put("destination", "/api/donator/register");
        //String body = json; //donatorVO.toJSON();
        StompCommand command = new StompCommand("SEND");
        return new StompMessage(command, json, headers);
    }
}
