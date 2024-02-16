package com.epam.advanced.java.data_formats.service.consumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.epam.advanced.java.data_formats.service.constants.Topics.AVRO_MESSAGE_KAFKA_TOPIC;

@Slf4j
@Service
public class AvroMessageConsumer {

    @KafkaListener(topics = AVRO_MESSAGE_KAFKA_TOPIC, autoStartup = "${listen.auto.start:true}", concurrency = "${listen.concurrency:3}")
    public void listenFromTopic(String message) {
        log.info("Received Message: '{}'", message);
    }
}
