package com.epam.advanced.java.data_formats.service.consumer;


import com.epam.advanced.java.Message;
import com.epam.advanced.java.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.epam.advanced.java.data_formats.service.constants.Topics.AVRO_MESSAGE_KAFKA_TOPIC;
import static com.epam.advanced.java.data_formats.utils.AvroMessageUtils.convertAvroRecord;

@Slf4j
@Service
@RequiredArgsConstructor
public class AvroMessageConsumer {

    @KafkaListener(topics = AVRO_MESSAGE_KAFKA_TOPIC)
    public void listenFromTopic(ConsumerRecord<String, GenericRecord> consumerRecord) {
        Message message = convertAvroRecord(consumerRecord.value());

        log.info("Received Message (Avro-deserialized): key {}, message '{}' which was created at {}",
                consumerRecord.key(),
                message.getMessage(),
                DateTimeUtils.milliSecondsToLocalDateTime(message.getMessageTimpestamp()));
    }
}
