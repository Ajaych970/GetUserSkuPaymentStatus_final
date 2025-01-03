package apis;

import WebSocket.ClientWebSocket;
import WebSocket.WebSocketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class TestCLasses {
    WebSocketService webSocketService = new WebSocketService();
    ClientWebSocket clientWebSocket;
    ObjectMapper objectMapper;
    public static int numberOfInvocationCount=1000;

    public TestCLasses() {
    }

    @BeforeTest
    public void connectWithWebSocket() throws FileNotFoundException, InterruptedException, JsonProcessingException {
        objectMapper=new ObjectMapper();

        webSocketService.connectWebSocket();


    }

    @Test(invocationCount = 5000,threadPoolSize =1000)
    public void sendMessage() throws FileNotFoundException, JsonProcessingException, InterruptedException {
//        webSocketService=new WebSocketService();

        String sendMessage = webSocketService.sendMessage();
//        System.out.println("Sent JSON request: " + sendMessage);


        String receiveMessage = webSocketService.receiveMessage();
//        System.out.println("Response : "+receiveMessage);

    }

    @AfterTest
    public void closeConnection() throws InterruptedException, IOException {
//        webSocketService=new WebSocketService();
        webSocketService.closeConnection();
        Thread.sleep(5000);
        webSocketService.generateCSVReport();
//        clientWebSocket.close();
//        generateReport();
    }


//    @Test
//    public void generateReport() throws FileNotFoundException, InterruptedException {
//        webSocketService.generateCSVReport();
//    }


}
