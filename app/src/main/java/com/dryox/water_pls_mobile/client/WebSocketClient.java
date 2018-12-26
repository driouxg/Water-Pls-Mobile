package com.dryox.water_pls_mobile.client;

import com.dryox.water_pls_mobile.domain.StompCommand;
import com.dryox.water_pls_mobile.domain.StompMessage;
import com.dryox.water_pls_mobile.domain.Topics;
import com.noveogroup.android.log.Logger;
import com.noveogroup.android.log.LoggerManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by chen0 on 9/12/2017.
 *
 * @author drioux.guidry
 * <p>
 * important resources about STOMP
 * https://stomp.github.io/stomp-specification-1.2.html
 */

public final class WebSocketClient extends WebSocketListener {

    private static final Logger LOGGER = LoggerManager.getLogger("root");

    private Topics topics;
    private List<StompMessage> messages;
    private WebSocket webSocket;

    public WebSocketClient() {
        topics = new Topics();
        messages = new ArrayList<>();
    }

    public void subscribe(String topicName, TopicHandler topicHandler) {
        topics.subscribe(topicName, topicHandler);
        sendStompMessage(buildSubscribeMessage(topicName));
    }

    public void subscribe(String topicName) {
        topics.subscribe(topicName);
        sendStompMessage(buildSubscribeMessage(topicName));
    }

    public void unSubscribe(String topic) {
        topics.unSubscribe(topic);
        sendStompMessage(buildUnsubscribeMessage());
    }

    public void connect(String address) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newWebSocket(request, this);

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        LOGGER.i("Opened connection to server with server response: " + response);
        this.webSocket = webSocket;
        sendStompMessage(buildConnectMessage());
        sendStompMessages();
    }

    private void sendStompMessages() {
        if (isConnected()) {
            LOGGER.i("Sending " + messages.size() + " messages that were stored in queue.");
            for (StompMessage message : messages) {
                sendStompMessage(message);
            }
        }
    }

    public void sendStompMessage(StompMessage message) {
        if (isConnected()) {
            webSocket.send(StompMessageSerializer.serialize(message));
        } else {
            LOGGER.i("Websocket not connected yet, added " + message.getCommand() + " to message queue.");
            messages.add(message);
        }
    }

    public StompMessage buildUnsubscribeMessage() {
        StompCommand stompCommand = new StompCommand("UNSUBSCRIBE");
        return new StompMessage(stompCommand, "", new HashMap<String, String>(){{put("id", "myID");}});
    }

    private StompMessage buildSubscribeMessage(String topic) {
        StompCommand command = new StompCommand("SUBSCRIBE");
        Map<String, String> headers = new HashMap<>();
        headers.put("id", "my-unique-client-id");
        headers.put("destination", topic);
        return new StompMessage(command, "", headers);
    }

    private StompMessage buildConnectMessage() {
        StompCommand command = new StompCommand("CONNECT");
        Map<String, String> headers = new HashMap<>();
        headers.put("accept-version", "1.1");
        headers.put("heart-beat", "10000,10000");
        return new StompMessage(command, "", headers);
    }

    public void disconnect() {
        if (isConnected()) {
            webSocket.close(1000, "close websocket");
            webSocket = null;
        }
    }

    public boolean isConnected() {
        return webSocket != null;
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        StompMessage message = StompMessageSerializer.deserialize(text);
        LOGGER.i("onMessage called: " + text);
        String topicName = message.getHeader("destination");

        if (topics.hasTopic(topicName)) {
            topics.getTopicHandler(topicName).onMessage(message);
        } else {
            LOGGER.i("TopicName: " + topicName + " not found.");
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        LOGGER.i("MESSAGE: " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        LOGGER.i("CLOSE: " + code + " " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }
}
