package com.epam.advanced.java.grpc.server;

import com.epam.advanced.java.grpc.service.MessageExchangeServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static com.epam.advanced.java.grpc.constants.NetworkConstants.SERVICE_PORT;

@Slf4j
public class MessageExchangeServer {
    private Server server;

    @SneakyThrows
    public void startServer() {
        server = ServerBuilder.forPort(SERVICE_PORT)
                .addService(new MessageExchangeServiceImpl())
                .build()
                .start();
        log.info("Message Exchange server has started on port {}", SERVICE_PORT);
    }

    public void stopServer() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        MessageExchangeServer messageExchangeServer = new MessageExchangeServer();
        messageExchangeServer.startServer();
        messageExchangeServer.blockUntilShutdown();
    }
}
