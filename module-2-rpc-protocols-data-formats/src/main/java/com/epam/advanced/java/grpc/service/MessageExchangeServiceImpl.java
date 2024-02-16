package com.epam.advanced.java.grpc.service;

import com.epam.advanced.java.exchange.MessageRequest;
import com.epam.advanced.java.exchange.MessageResponse;
import com.epam.advanced.java.exchange.MessagesExchangeServiceGrpc;
import com.epam.advanced.java.grpc.utils.TimestampUtils;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class MessageExchangeServiceImpl extends MessagesExchangeServiceGrpc.MessagesExchangeServiceImplBase {

    private static final String PING_MESSAGE_CONTENT = "Ping";
    private static final String PING_MESSAGE_ANSWER = "Pong";
    private static final String NON_PING_MESSAGE_ANSWER = "No idea what to say";

    @Override
    public void getInboundMessage(MessageRequest request, StreamObserver<MessageResponse> response) {
        String inboundMessage = request.getMessage();
        LocalDateTime receivedAt = TimestampUtils.parseTimestamp(request.getMessageTime());

        log.info("Inbound message '{}' received at {} {}", inboundMessage, receivedAt.toLocalDate(), receivedAt.toLocalTime());
        var messageResponseBuilder = MessageResponse.newBuilder();

        if (inboundMessage.equals(PING_MESSAGE_CONTENT)) {
            log.info("Ping message detected.");
            messageResponseBuilder.setMessage(PING_MESSAGE_ANSWER);
            log.info("Pong message as answer prepared.");
        } else {
            messageResponseBuilder.setMessage(NON_PING_MESSAGE_ANSWER);
            log.warn("non-Ping message has come.");
        }

        messageResponseBuilder.setMessageTime(TimestampUtils.nowTime());
        var messageResponse = messageResponseBuilder.build();
        response.onNext(messageResponseBuilder.build());
        response.onCompleted();

        log.info("The following answer sent: {}.", messageResponse.getMessage());
    }

}
