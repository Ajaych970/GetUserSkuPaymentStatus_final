package apis;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.java_websocket.client.WebSocketClient;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class VerifyWebSocketAPIs {
    SocketServiceData context;
    private WebClient_2 webClient_2;



    @BeforeTest
    public void createContext() throws IOException {
        FileInputStream readUrl=new FileInputStream("src/main/resources/baseUrl.properties");
        Properties p=new Properties();
        p.load(readUrl);
        try{
        context=new SocketServiceData();
        context.URI = p.getProperty("baseUrl");
        context.requestHeaders=getHeader();
        context.timeOut=10;
//        context.expectedMessage="This is a test";
//        context.actualMessage="This is a test";
        webClient_2=new WebClient_2();
        // Initialize WebSocket service
//        webClient_2 = new SocketService();

        // Establish connection to the WebSocket server
        webClient_2.connectToWebSocket(context);
        System.out.println("WebSocket connection established successfully in @BeforeTest.");
        } catch (Exception e) {
        System.err.println("Error during WebSocket connection setup: " + e.getMessage());
        throw new RuntimeException("Failed to set up WebSocket connection.", e);
        }

    }

    @Test(invocationCount = 300,threadPoolSize = 80)
    public void verifyWebSocketAPIs() {
//        WebClient_2 webClient_2=new WebClient_2();
        try {
//            Connect to the WebSocket
//            webClient_2.connectToWebSocket(context);

//            Send a message to the WebSocket
            webClient_2.sendMessageToWebSocket();

//            Listen and handle WebSocket responses
            webClient_2.listenAndHandle(context);

            System.out.println("Data sent and response received successfully.");
            System.out.println();
            System.out.println();
        } catch (Exception e) {
            System.err.println("Unexpected error during WebSocket test: " + e.getMessage());
            throw new RuntimeException("Error during WebSocket communication.", e);
        }
    }


    @AfterTest
    public void tearDown() throws IOException {
        int responseListSize=0;
        try {
            System.out.println("Closing WebSocket connection in .......");

            responseListSize = context.messageList.size();
            Thread.sleep(5000);
//            close the WebSocket connection
            webClient_2.closeConnection(responseListSize);


            System.out.println("total response in list : "+"....."+responseListSize);

            System.out.println();
            System.out.println();
            System.out.println();



            System.out.println("WebSocket connection closed successfully.");

        } catch (Exception e) {
            System.err.println("Error while closing WebSocket connection: " + e.getMessage());
        }
        // Write responses to a text file
        List<String> messageList = context.messageList; // Assuming context.messageList is of type List<String>
        try (FileWriter writer = new FileWriter("src/main/java/Request/responseOutput.txt")) {
            for (String message : messageList) {
                writer.write(message + System.lineSeparator());
            }
        }

        System.out.println("total response in list_2 : "+"....."+responseListSize);
    }

//    public void printResponse(List<String> responseData){
//        int size=responseData.size();
//        for (int i=0; i<size;i++){
//            System.out.println(context.messageList.get(i));
//            System.out.println();
//        }
//    }



    public static Properties readHeaderFile() throws FileNotFoundException {
        FileInputStream header=new FileInputStream("src/main/resources/Header.properties");
        Properties properties=new Properties();
        try {
            properties.load(header);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }



    public static Map<String,String> getHeader() throws FileNotFoundException {
        Map<String,String> header=new HashMap<>();

        for (String key : readHeaderFile().stringPropertyNames()) {
            header.put(key, readHeaderFile().getProperty(key));
        }

        return header;
    }
}
