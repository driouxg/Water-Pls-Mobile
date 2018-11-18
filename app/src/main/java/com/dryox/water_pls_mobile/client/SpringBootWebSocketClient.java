package com.dryox.water_pls_mobile.client;

import com.dryox.water_pls_mobile.domain.StompCommand;
import com.dryox.water_pls_mobile.domain.StompMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.WebSocket;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * Created by chen0 on 9/12/2017.
 *
 * @author drioux.guidry
 *
 * important resources about STOMP
 * https://stomp.github.io/stomp-specification-1.2.html
 *
 */

public final class SpringBootWebSocketClient extends WebSocketListener {

    private Map<String, TopicHandler> topics = new HashMap<>();
    private CloseHandler closeHandler;
    private String id = "sub-001";
    private WebSocket webSocket;

    public SpringBootWebSocketClient() {

    }

    public SpringBootWebSocketClient(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public TopicHandler subscribe(String topic) {
        TopicHandler handler = new TopicHandler(topic);
        topics.put(topic, handler);
        if(webSocket != null){
            sendSubscribeMessage(webSocket, topic);
        }
        return handler;
    }

    public void unSubscribe(String topic) {
        topics.remove(topic);
    }

    public TopicHandler getTopicHandler(String topic) {
        if(topics.containsKey(topic)){
            return topics.get(topic);
        }
        return null;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void connect(String address) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newWebSocket(request, this);

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown();
    }

    @Override public void onOpen(WebSocket webSocket, Response response) {

        /*
        webSocket.send("Hello...");
        webSocket.send("...World!");
        webSocket.send(ByteString.decodeHex("deadbeef"));
        */

        System.out.println("Opened connection to server!");

        this.webSocket = webSocket;

        sendConnectMessage(webSocket);
        sendStompMessage(webSocket, "/app/user");

        for(String topic : topics.keySet()){
            sendSubscribeMessage(webSocket, topic);
        }


        closeHandler = new CloseHandler(webSocket);
    }

    private void sendStompMessage(WebSocket websocket, String endpoint) {
        StompCommand command = new StompCommand("SEND");
        Map<String, String> headers = new HashMap<>();
        headers.put("destination", endpoint);
        String body = "Hi there server, it's me the client!";
        StompMessage message = new StompMessage(command, body, headers);
        websocket.send(StompMessageSerializer.serialize(message));
        //message.put("content-type", "text/html");
    }

    private void sendConnectMessage(WebSocket webSocket) {
        StompCommand command = new StompCommand("CONNECT");
        Map<String, String> headers = new HashMap<>();
        headers.put("accept-version", "1.1");
        headers.put("heart-beat", "10000,10000");
        StompMessage message = new StompMessage(command, "", headers);
        webSocket.send(StompMessageSerializer.serialize(message));
    }

    private void sendSubscribeMessage(WebSocket webSocket, String topic) {
        StompCommand command = new StompCommand("SUBSCRIBE");
        Map<String, String> headers = new HashMap<>();
        headers.put("id", id);
        headers.put("destination", topic);
        StompMessage message = new StompMessage(command, "", headers);
        webSocket.send(StompMessageSerializer.serialize(message));
    }

    public void disconnect() {
        if(webSocket != null){
            closeHandler.close();
            webSocket = null;
            closeHandler = null;
        }
    }

    public boolean isConnected() {
        return closeHandler != null;
    }

    @Override public void onMessage(WebSocket webSocket, String text) {
        //System.out.println("MESSAGE: " + text);
        StompMessage message = StompMessageSerializer.deserialize(text);
        String topic = message.getHeader("destination");
        if(topics.containsKey(topic)){
            topics.get(topic).onMessage(message);
        }
    }

    @Override public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println("MESSAGE: " + bytes.hex());
    }

    @Override public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        System.out.println("CLOSE: " + code + " " + reason);
    }

    @Override public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }

    //public static void main(String... args) {
//
//
    //    SpringBootWebSocketClient client = new SpringBootWebSocketClient();
    //    TopicHandler handler = client.subscribe("/topics/user");
    //    handler.addListener(new StompMessageListener() {
    //        @Override
    //        public void onMessage(StompMessage message) {
    //            System.out.println(message.getHeader("destination") + ": " + message.getBody());
    //        }
    //    });
    //    client.connect("ws://localhost:8091/websocket-example");
//
    //    //client.sendStompMessage(client.getWebSocket(), "/app/user");
    //}
}
