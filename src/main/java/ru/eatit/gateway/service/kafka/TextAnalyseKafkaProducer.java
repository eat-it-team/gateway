package ru.eatit.gateway.service.kafka;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import org.springframework.stereotype.Service;

@Service
public class TextAnalyseKafkaProducer {
    private Map<String, Queue<Object>> kafka = new ConcurrentHashMap<>();

    public void sendMessage(String topic, Object body) {
        if (kafka.containsKey(topic)) {
            Queue<Object> queue = new LinkedBlockingDeque<>();
            queue.add(body);
            kafka.put(topic, queue);
        } else {
            kafka.get(topic).add(body);
        }
    }
}
