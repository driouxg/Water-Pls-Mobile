package com.dryox.water_pls_mobile.service.asynctask;

import android.os.AsyncTask;

import com.dryox.water_pls_mobile.client.WebSocketClient;
import com.dryox.water_pls_mobile.client.StompMessageListener;
import com.dryox.water_pls_mobile.client.TopicHandler;
import com.dryox.water_pls_mobile.domain.StompCommand;
import com.dryox.water_pls_mobile.domain.StompMessage;

import java.util.HashMap;
import java.util.Map;

public class ConnectRequesterToServer extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            WebSocketClient client = new WebSocketClient();

            String topicName = "/topic/user";
            TopicHandler topicHandler = new TopicHandler(topicName);

            topicHandler.addListener(new StompMessageListener() {
                @Override
                public void onMessage(StompMessage message) {
                    System.out.println(message.getHeader("destination") + ": " + message.getBody()); }});

            client.subscribe(topicName, topicHandler);
            client.connect("ws://10.0.2.2:8091/websocket-example");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
