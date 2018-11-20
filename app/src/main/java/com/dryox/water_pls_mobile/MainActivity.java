package com.dryox.water_pls_mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dryox.water_pls_mobile.client.SpringBootWebSocketClient;
import com.dryox.water_pls_mobile.client.StompMessageListener;
import com.dryox.water_pls_mobile.client.TopicHandler;
import com.dryox.water_pls_mobile.domain.StompMessage;
import com.dryox.water_pls_mobile.service.ConnectClientToServerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectClientToServerTask task = new ConnectClientToServerTask();
        task.execute();
    }
}
