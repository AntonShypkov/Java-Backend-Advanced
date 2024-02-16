package com.epam.advanced.java.grpc.client;

import com.epam.advanced.java.exchange.MessageRequest;
import com.epam.advanced.java.exchange.MessageResponse;
import com.epam.advanced.java.exchange.MessagesExchangeServiceGrpc;
import com.epam.advanced.java.grpc.utils.TimestampUtils;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MessageExchangeClient {
    private final MessagesExchangeServiceGrpc.MessagesExchangeServiceBlockingStub messagesExchangeServiceBlockingStub;

    public MessageExchangeClient(Channel channel) {
        messagesExchangeServiceBlockingStub = MessagesExchangeServiceGrpc.newBlockingStub(channel);
    }

    public MessageResponse sendMessage(String message) {
        var request = MessageRequest.newBuilder()
                .setMessage(message)
                .setMessageTime(TimestampUtils.nowTime())
                .build();

        return messagesExchangeServiceBlockingStub.getInboundMessage(request);
    }

    public static void main(String[] args) {
        var messages = List.of("Ping", "Non Ping", "Hi Joe");
        log.info("Client has prepared message to be sent to exchange server.");

        Channel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        var client = new MessageExchangeClient(channel);
        log.info("Client has been fully ready for messages exchange.");

        messages.forEach(message -> {
            log.info("Client is sending message: {}", message);
            var response = client.sendMessage(message);
            var recievedAt = TimestampUtils.parseTimestamp(response.getMessageTime());

            log.info("Client has obtained answer: '{}', received at {} {}",
                    response.getMessage(), recievedAt.toLocalDate(), recievedAt.toLocalTime());
        });
    }
}