package com.epam.advanced.java.server;

import com.epam.advanced.java.service.MessageExchangeServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MessageExchangeServer {
    private Server server;

    @SneakyThrows
    public void startServer() {
        int listeningPort = 8080;

        server = ServerBuilder.forPort(listeningPort)
                .addService(new MessageExchangeServiceImpl())
                .build()
                .start();
        log.info("Message Exchange server has started on port {}", listeningPort);
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
