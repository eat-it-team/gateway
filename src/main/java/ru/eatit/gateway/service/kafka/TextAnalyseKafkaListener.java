package ru.eatit.gateway.service.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.eatit.gateway.service.ResponseCacheService;


@Service
public class TextAnalyseKafkaListener {

    Logger LOGGER = LoggerFactory.getLogger(TextAnalyseKafkaListener.class);

    private final ResponseCacheService cacheService;

    @Autowired
    public TextAnalyseKafkaListener(ResponseCacheService cacheService) {
        this.cacheService = cacheService;
    }

    @KafkaListener(topics = "${kafka.text_analyse.response.topicName}", id = "${kafka.text_analyse.groupId}")
    public void listener(ConsumerRecord<String, String> record) throws ParseException {

        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.topic());
        System.out.println(record.offset());
        cacheService.put(record.key(), record.value());
    }
}
