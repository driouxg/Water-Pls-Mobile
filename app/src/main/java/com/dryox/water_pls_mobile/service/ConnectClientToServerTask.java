package com.dryox.water_pls_mobile.service;

import android.os.AsyncTask;

import com.dryox.water_pls_mobile.client.SpringBootWebSocketClient;
import com.dryox.water_pls_mobile.client.StompMessageListener;
import com.dryox.water_pls_mobile.client.TopicHandler;
import com.dryox.water_pls_mobile.domain.StompMessage;

public class ConnectClientToServerTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            SpringBootWebSocketClient client = new SpringBootWebSocketClient();

            TopicHandler handler = client.subscribe("/topic/user");
            handler.addListener(new StompMessageListener() {
                @Override
                public void onMessage(StompMessage message) {
                    System.out.println(message.getHeader("destination") + ": " + message.getBody()); }});
            client.connect("ws://10.0.2.2:8091/websocket-example");

            if (client.getWebSocket() == null) {
                System.out.println("IT'S NULL YO");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
