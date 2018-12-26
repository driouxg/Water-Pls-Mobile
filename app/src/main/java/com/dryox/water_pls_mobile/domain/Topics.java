package com.dryox.water_pls_mobile.domain;

import com.dryox.water_pls_mobile.client.StompMessageListener;
import com.dryox.water_pls_mobile.client.TopicHandler;

import java.util.HashMap;
import java.util.Map;

public class Topics {
    private Map<String, TopicHandler> topics;

    public Topics() {
        this.topics = new HashMap<>();
    }

    public TopicHandler getTopicHandler(String topic) {
        return topics.get(topic);
    }

    public void unSubscribe(String topic) {
        topics.remove(topic);
    }

    public void subscribe(String topicName, TopicHandler topicHandler) {
        topics.put(topicName, topicHandler);
    }

    public void subscribe(String topicName) {
        TopicHandler topicHandler = createDefaultTopicHandler();
        topics.put(topicName, topicHandler);
    }

    private TopicHandler createDefaultTopicHandler() {
        TopicHandler topicHandler = new TopicHandler();
        topicHandler.addListener(new StompMessageListener() {
            @Override
            public void onMessage(StompMessage message) {
                System.out.println(message.getHeader("destination") + ": " + message.getBody());
            }
        });
        return topicHandler;
    }

    public boolean hasTopic(String topicName) {
        return topics.containsKey(topicName);
    }
}
