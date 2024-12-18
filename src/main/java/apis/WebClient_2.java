package apis;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebClient_2 {

    SocketServiceData socketServiceData;
//    CountDownLatch messageLatch;
    private Client webSocket;
    ObjectMapper objectMapper;


    public synchronized void connectToWebSocket(SocketServiceData socketContext) throws URISyntaxException, InterruptedException {
        webSocket = new Client(socketContext);
        System.out.println("WebSocket instance created: " + webSocket);

        // Add headers if they exist
        if (!socketContext.requestHeaders.isEmpty()) {
            socketContext.requestHeaders.forEach(webSocket::addHeader);
        }

        // Establish a blocking connection
        boolean isConnected = webSocket.connectBlocking();
        if (!isConnected) {
            throw new IllegalStateException("WebSocket connection could not be established.");
        }
        System.out.println("WebSocket connection established: " + isConnected);
        System.out.println("WebSocket is closed: " + webSocket.isClosed());
    }


    int requestCount=0;
    public void sendMessageToWebSocket() throws JsonProcessingException {
        objectMapper=new ObjectMapper();
//        Check if WebSocket is connected
        if (webSocket == null || !webSocket.isOpen()) {
            throw new IllegalStateException("WebSocket is not connected. Please connect first.");
        }

//        Prepare the JSON request data
        Map<String, Object> requestData = Client.readJsonFromFile("src/main/java/Request/request.json");
        String jsonRequest = objectMapper.writeValueAsString(requestData);

//        Send the JSON request
        webSocket.send(jsonRequest);
        System.out.println("Sent JSON request: " + jsonRequest);
        requestCount++;
    }


    int responseCount=0;
    public void listenAndHandle(SocketServiceData socketContext) throws InterruptedException {

//        Thread.sleep(15000);
//            System.out.println("WebSocket listener terminated.");
//            System.out.println();
//            System.out.println();
//            System.out.println();



        CountDownLatch messageLatch = new CountDownLatch(1);  // To wait for message
        long maxWaitTime = 60; // Timeout in seconds


//        long startTime = System.currentTimeMillis();

//        waiting to get response from server
        Thread.sleep(5000);


        messageLatch.countDown();

//        response or timeout after the specified maxWaitTime
        boolean received = messageLatch.await(maxWaitTime, TimeUnit.SECONDS);

        if (received) {

            System.out.println("Response received within "+maxWaitTime+" seconds.");
        } else {
//          error message after 8 second reached
            System.err.println("Error: Timeout reached after "+maxWaitTime+" seconds. No response received.");
            throw new RuntimeException("Error: Timeout reached after "+maxWaitTime+" seconds.");
        }
        responseCount++;
        System.out.println("WebSocket listener terminated.");
    }


    // Close WebSocket connection
    public void closeConnection(int responseListSize) {
        int size=0;
        if (webSocket != null && !webSocket.isClosed()) {
            webSocket.close(1000, "Test completed");
            System.out.println("Total Request Count : "+requestCount);
            System.out.println("Total Response Count : "+responseCount);
//            printResponse(responseListSize);
//            System.out.println("total response in list : "+webSocket.mess);
//            System.out.println();
//            System.out.println();
//            size = socketServiceData.messageList.size();
//            for (int i=0; i<size;i++){
//                System.out.println(socketServiceData.messageList.get(i));
//                System.out.println();
//            }
        }


    }

    public void printResponse(SocketServiceData socketServiceData){
        int size = socketServiceData.messageList.size();

        for (int i = 0; i <size; i++) {
            System.out.println(socketServiceData.messageList.get(i));
            System.out.println();
        }
    }

/*

//    This is rety code
    public void connectToWebSocketWithRetry(SocketServiceData socketContext, int retryCount) throws Exception {
        int attempts = 0;
        while (attempts < retryCount) {
            try {
                connectToWebSocket(socketContext);
                return; // Exit the loop if connection is successful
            } catch (IllegalStateException e) {
                attempts++;
                System.out.println("Retrying WebSocket connection... Attempt: " + attempts);
                Thread.sleep(1000); // Wait before retrying
            }
        }
        throw new IllegalStateException("Failed to connect to WebSocket after " + retryCount + " attempts.");
    }

 */





}
