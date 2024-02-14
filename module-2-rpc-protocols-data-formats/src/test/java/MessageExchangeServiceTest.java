import com.epam.advanced.java.client.MessageExchangeClient;
import com.epam.advanced.java.server.MessageExchangeServer;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageExchangeServiceTest {
    private static MessageExchangeServer messageExchangeServer;
    private MessageExchangeClient messageExchangeClient;

    private static final String ANSWER_FOR_NOT_PING_MESSAGE = "No idea what to say";

    @BeforeAll
    static void startServer() {
        new Thread(() -> {
            messageExchangeServer = new MessageExchangeServer();
            messageExchangeServer.startServer();
            try {
                messageExchangeServer.blockUntilShutdown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @AfterAll
    static void shutdownServer() throws InterruptedException {
        messageExchangeServer.stopServer();
    }

    @BeforeEach
    void createMessageExchangeClient() {
        Channel channel = ManagedChannelBuilder
                .forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        messageExchangeClient = new MessageExchangeClient(channel);
    }

    @Test
    void testSendMessage_whenPingMessage_thenPongAnswer() {
        //given
        String message = "Ping";

        //when
        var response = messageExchangeClient.sendMessage(message);

        //then
        String expectedAnswer = "Pong";
        assertEquals(expectedAnswer, response.getMessage());
    }

    @Test
    void testSendMessage_whenNotPingMessage_thenNotPongAnswer() {
        //given
        String message = "Not ping message";

        //when
        var response = messageExchangeClient.sendMessage(message);

        //then
        assertEquals(ANSWER_FOR_NOT_PING_MESSAGE, response.getMessage());
    }

    @Test
    void testSendMessage_whenEmptyMessage_thenNotPongAnswer() {
        //given
        String message = "";

        //when
        var response = messageExchangeClient.sendMessage(message);

        //then
        assertEquals(ANSWER_FOR_NOT_PING_MESSAGE, response.getMessage());
    }
}
