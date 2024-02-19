package com.epam.advanced.java.data_formats.config;

import com.epam.advanced.java.data_formats.service.constants.Topics;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;


public class KafkaTopicConfig {

    @Value("${spring.kafka.bootstrap.servers}")
    private String bootstrapServer;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(Topics.AVRO_MESSAGE_KAFKA_TOPIC, 1, (short) 1);
    }
}
