package com.epam.advanced.java.grpc.client;

import com.epam.advanced.java.exchange.MessageRequest;
import com.epam.advanced.java.exchange.MessageResponse;
import com.epam.advanced.java.exchange.MessagesExchangeServiceGrpc;
import com.epam.advanced.java.utils.DateTimeUtils;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.epam.advanced.java.grpc.constants.NetworkConstants.SERVICE_HOST;
import static com.epam.advanced.java.grpc.constants.NetworkConstants.SERVICE_PORT;

@Slf4j
public class MessageExchangeClient {
    private final MessagesExchangeServiceGrpc.MessagesExchangeServiceBlockingStub messagesExchangeServiceBlockingStub;

    public MessageExchangeClient(Channel channel) {
        messagesExchangeServiceBlockingStub = MessagesExchangeServiceGrpc.newBlockingStub(channel);
    }

    public MessageResponse sendMessage(String message) {
        var request = MessageRequest.newBuilder()
                .setMessage(message)
                .setMessageTime(DateTimeUtils.nowTime())
                .build();

        return messagesExchangeServiceBlockingStub.getInboundMessage(request);
    }

    public static void main(String[] args) {
        var messages = List.of("Ping", "Non Ping", "Hi Joe");
        log.info("Client has prepared message to be sent to exchange server.");

        Channel channel = ManagedChannelBuilder
                .forAddress(SERVICE_HOST, SERVICE_PORT)
                .usePlaintext()
                .build();
        var client = new MessageExchangeClient(channel);
        log.info("Client has been fully ready for messages exchange.");

        messages.forEach(message -> {
            log.info("Client is sending message: {}", message);
            var response = client.sendMessage(message);
            var recievedAt = DateTimeUtils.timestampToLocalDateTime(response.getMessageTime());

            log.info("Client has obtained answer: '{}', received at {} {}",
                    response.getMessage(), recievedAt.toLocalDate(), recievedAt.toLocalTime());
        });
    }
}