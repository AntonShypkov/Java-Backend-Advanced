package com.epam.advanced.java.data_formats.service.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import static com.epam.advanced.java.data_formats.service.constants.Topics.AVRO_MESSAGE_KAFKA_TOPIC;
import static com.epam.advanced.java.data_formats.utils.AvroMessageUtils.buildAvroRecord;


@RequiredArgsConstructor
@Service
@Slf4j
public class AvroMessageProducer {

    private final KafkaProducer<Object, Object> kafkaProducer;

    public void sendMessage(String message) {
        log.info("Kafka Producer sends message: '{}'", message);
        kafkaProducer.send(createProducerRecord(buildAvroRecord(message)));
        log.info("Kafka message (Avro-serialized) is sent into Topic {}", AVRO_MESSAGE_KAFKA_TOPIC);
    }

    private ProducerRecord<Object, Object> createProducerRecord(GenericRecord avroRecord) {
        return new ProducerRecord<>(AVRO_MESSAGE_KAFKA_TOPIC, avroRecord);
    }
}
