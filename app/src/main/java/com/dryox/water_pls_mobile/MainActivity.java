package com.dryox.water_pls_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dryox.water_pls_mobile.client.SpringBootWebSocketClient;
import com.dryox.water_pls_mobile.client.StompMessageListener;
import com.dryox.water_pls_mobile.client.TopicHandler;
import com.dryox.water_pls_mobile.domain.StompMessage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpringBootWebSocketClient client = new SpringBootWebSocketClient();

        TopicHandler handler = client.subscribe("/topics/user");
        handler.addListener(new StompMessageListener() {
            @Override
            public void onMessage(StompMessage message) {
                System.out.println(message.getHeader("destination") + ": " + message.getBody()); }});
        client.connect("ws://localhost:8091/websocket-example");
        client.sendStompMessage(client.getWebSocket(), "/app/user");
    }
}
