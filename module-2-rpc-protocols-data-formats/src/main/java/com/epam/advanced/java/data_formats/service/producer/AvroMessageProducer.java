package com.epam.advanced.java.data_formats.service.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.epam.advanced.java.data_formats.service.constants.Topics.AVRO_MESSAGE_KAFKA_TOPIC;


@RequiredArgsConstructor
@Service
@Slf4j
public class AvroMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        log.info("Kafka Producer sends message: '{}'", message);
        kafkaTemplate.send(AVRO_MESSAGE_KAFKA_TOPIC, message);
        log.info("Kafka message is sent into Topic {}", AVRO_MESSAGE_KAFKA_TOPIC);
    }

    public void sendMessageWithCallback(String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(AVRO_MESSAGE_KAFKA_TOPIC, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message=[{}] due to : {}", message, ex.getMessage());
            }
        });
    }
}
