package com.epam.advanced.java.data_formats.controller;

import com.epam.advanced.java.data_formats.service.producer.AvroMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/epam/backend/advanced/kafka")
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerController {
    private final AvroMessageProducer avroMessageProducer;

    @PostMapping("/send")
    public void sendKafkaMessage (@RequestBody String message){
        log.info("Controller get POST request to send message to Kafka: '{}'", message);
        avroMessageProducer.sendMessage(message);
        log.info("Message sent by controller");
    }
}
