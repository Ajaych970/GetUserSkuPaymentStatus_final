package WebSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.io.*;
import java.net.URI;
import java.util.*;

public class WebSocketService {


    ClientWebSocket clientWebSocket;
    ObjectMapper objectMapper;
    private static final Logger logger= LogManager.getLogger(WebSocketService.class);
    long messageSendTime;
    long messageReceiveTime;
//    String error="Internal server error";
    int errorCounter=0;
    List responseMessageList=new ArrayList<>();
    int counter=0;
    String lastMessage;
    long responseTime;


    public void connectWebSocket() throws FileNotFoundException, InterruptedException {
        clientWebSocket=new ClientWebSocket(URI.create(readURI()), getHeader());
        logger.info("{\"data\": [");

        boolean isConnected = clientWebSocket.connectBlocking();
        if (!isConnected){
            System.err.println("WebSocket is not connected");
            logger.info("Websocket not connected...");
        }else {
            System.out.println("WebSocket connected successfully...");
//            logger.info("Websocket connected...");
        }
    }

    public String sendMessage() throws FileNotFoundException, JsonProcessingException {
//        clientWebSocket=new ClientWebSocket(URI.create(readURI()), getHeader());
        objectMapper=new ObjectMapper();


        // Prepare the JSON request data
        Map<String, Object> requestData = readJsonFromFile("src/main/java/Data/request.json");
        String jsonRequest = objectMapper.writeValueAsString(requestData);
        messageSendTime = System.currentTimeMillis();
//        logger.info("Message send time : {}",messageSendTime);
        clientWebSocket.send(jsonRequest);
        return jsonRequest;
    }

    public String receiveMessage() throws InterruptedException {
        Thread.sleep(3000);
        messageReceiveTime = System.currentTimeMillis();
//        logger.info("Message receive time: {}", messageReceiveTime);


        lastMessage = clientWebSocket.getLastMessage();
        if (lastMessage != null){
            responseTime=messageReceiveTime-messageSendTime;
            logger.info("{\"ResponseTime\": {}, \"ResponseMessage\": {}},", responseTime, lastMessage);




//            Response message is store in responseMessageList
            boolean add = responseMessageList.add(lastMessage);

//
        }else {
//            logger.info("Response not receive...");
        }
//        long responseTime=messageReceiveTime-messageSendTime;
//        logger.info("Response time : {} ",responseTime);
        return lastMessage;
    }

    public void closeConnection(){


//        logger.info(" ]}");

        System.out.println("Websocket disconnect successfully....");
        logger.info("{\"ResponseTime\": {}, \"ResponseMessage\": {}}", responseTime, lastMessage);
        logger.info(" ]}");

        clientWebSocket.close();
        int responseMessageListSize = responseMessageList.size();
        System.out.println("Size of list : "+responseMessageListSize);



    }



    // read JSON from input file
    public static Map<String, Object> readJsonFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the JSON from the file and convert it into a Map
            return objectMapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            System.err.println("Failed to read JSON from file: " + e.getMessage());
            return null;
        }
    }







    public Map<String,String> getHeader() throws FileNotFoundException {
        Map<String,String> header=new HashMap<>();
        for (String key : readHeaderFile().stringPropertyNames()) {
            header.put(key, readHeaderFile().getProperty(key));
        }
        return header;
    }

    public static Properties readHeaderFile() throws FileNotFoundException {
        FileInputStream url=new FileInputStream("src/main/resources/Header.properties");
        Properties properties=new Properties();
        try {
            properties.load(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    public static String readURI() throws FileNotFoundException {
        FileInputStream header=new FileInputStream("src/main/resources/baseUrl.properties");
        Properties properties=new Properties();
        try {
            properties.load(header);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties.getProperty("baseUrl");
    }


    @Test
    public void generateCSVReport() throws IOException, InterruptedException {


        FileInputStream filter;

        int counterTimer_1=0;
        int counterTimer_2=0;
        int counterTimer_3=0;
        int counterTimer_4=0;
        int counterTimer_5=0;
        int counterTimer_6=0;
        int counterTimer_7=0;
        int counterTimer_8=0;
        int counterTimer_9=0;
        int counterTimer_10=0;


        int statusCounter=0;
        int statusCounter2=0;

//        String error;

        ObjectMapper mapper=new ObjectMapper();
        String inputLogFilePath="src/main/java/Data/log4.json";
        JsonNode jsonNode;

        Properties properties=new Properties();
//        Thread.sleep(30000);

        try {
            jsonNode = mapper.readTree(new File(inputLogFilePath));
            filter=new FileInputStream("src/main/java/Data/Filter.properties");
            properties.load(filter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        int timer1 = Integer.parseInt(properties.getProperty("timer_1"));
        int timer2 = Integer.parseInt(properties.getProperty("timer_2"));
        int timer3 = Integer.parseInt(properties.getProperty("timer_3"));
        int timer4 = Integer.parseInt(properties.getProperty("timer_4"));
        int timer5 = Integer.parseInt(properties.getProperty("timer_5"));
        int timer6 = Integer.parseInt(properties.getProperty("timer_6"));
        int timer7 = Integer.parseInt(properties.getProperty("timer_7"));
        int timer8 = Integer.parseInt(properties.getProperty("timer_8"));
        int timer9 = Integer.parseInt(properties.getProperty("timer_9"));
//        int timer10 = Integer.parseInt(properties.getProperty("timer_10"));

        String error = properties.getProperty("Error");

//        System.out.println(timer1+"...."+timer2+"...."+timer3);

        JsonNode dataNode = jsonNode.get("data");

        int responseMessageListSize = responseMessageList.size();
        System.out.println("Size of list : "+responseMessageListSize);

//        responseMessageListSize+2

        for (int i=0; i<responseMessageListSize+2 ; i++){

            try {
                JsonNode responseTimeNode = dataNode.get(i);
                JsonNode responseMessageNode = responseTimeNode.get("ResponseMessage");
//            JsonNode resp = responseMessage.get("resp");


                if (responseMessageNode != null) {


                    JsonNode resp = responseMessageNode.get("resp");

                    if (resp != null) {
                        int status = resp.get("Status").asInt();

                        if (status == 1) {
                            statusCounter++;
                        } else {
                            statusCounter2++;
                        }
                    } else {
                        String message = responseMessageNode.get("message").asText();
                        System.out.println(message);
                        if (error.contains(message)) {
                            errorCounter++;
                        }
                    }

                } else {
                    System.out.println("Response message not found");
                }

                int responseTime1 = responseTimeNode.get("ResponseTime").asInt();

                if (responseTime1 <= timer1) {
                    counterTimer_1++;
                } else if (responseTime1 <= timer2) {
                    counterTimer_2++;

                }else if (responseTime1 <=timer3) {
                    counterTimer_3++;

                }else if (responseTime1 <= timer4) {
                    counterTimer_4++;

                }else if (responseTime1 <= timer5) {
                    counterTimer_5++;

                }else if (responseTime1 <= timer6) {
                    counterTimer_6++;

                }else if (responseTime1 <= timer7) {
                    counterTimer_7++;

                }else if (responseTime1 <= timer8) {
                    counterTimer_8++;

                }else if (responseTime1 <= timer9) {
                    counterTimer_9++;

                }
                else {
                    counterTimer_10++;
                }

            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }


//        System.out.println("counter 1 : "+counterTimer_1+" counter 2 : "+counterTimer_2+" counter 3 : "+counterTimer_3+" status 1 : "+statusCounter+" status 0 : "+statusCounter2+"Internal error : "+errorCounter);

        String csvFilePath="src/main/java/Data/Report.csv";
        File csvFile = new File(csvFilePath);
        try {
            FileWriter fileWriter = new FileWriter(csvFile);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            String header[]={"Name" , "No of counts"};
            csvWriter.writeNext(header);

            String rowHeader[] ={"Timer 1","Timer 2", "Timer 3" , "Timer 4","Timer 5", "Timer 6", "Timer 7","Timer 8", "Timer 9", "Timer 10" ,"Status 1", "Status 0", "Error Message"};
            int rowData[] ={counterTimer_1,counterTimer_2,counterTimer_3, counterTimer_4,counterTimer_5,counterTimer_6, counterTimer_7,counterTimer_8,counterTimer_9,counterTimer_10 ,statusCounter,statusCounter2,errorCounter};

            int length = rowHeader.length;

            for (int i = 0; i < length; i++) {
                String row[] = {rowHeader[i],String.valueOf(rowData[i])};
                csvWriter.writeNext(row);
            }

            csvWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }




    }

}


/*
    public static void main(String[] args){
        String inputLogFilePath="src/main/java/Request/log4.json";
        FileInputStream filter;

        ObjectMapper mapper=new ObjectMapper();
        
        int counterTimer_1=0;
        int counterTimer_2=0;
        int counterTimer_3=0;
        int statusCounter=0;
        int statusCounter2=0;

        
        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(new File(inputLogFilePath));
            filter=new FileInputStream("src/main/java/Request/Filter.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        FileInputStream url=new FileInputStream("src/main/resources/Header.properties");
        Properties properties=new Properties();
        try {
            properties.load(filter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int timer1 = Integer.parseInt(properties.getProperty("timer_1"));
        int timer2 = Integer.parseInt(properties.getProperty("timer_2"));
        int timer3 = Integer.parseInt(properties.getProperty("timer_3"));
        System.out.println(timer1+"...."+timer2+"...."+timer3);

        JsonNode data = jsonNode.get("data");
//        JsonNode responseMessage = data.get("ResponseMessage");
//        JsonNode resp = responseMessage.get("resp");
//        int status = resp.get("Status").asInt();

        for (int i=0; i<1000; i++){
            JsonNode responseTime = data.get(i);
            JsonNode responseMessage = responseTime.get("ResponseMessage");
            JsonNode resp = responseMessage.get("resp");
            int responseTime1 = responseTime.get("ResponseTime").asInt();
            int status = resp.get("Status").asInt();
            if (status==1){
                statusCounter++;
            }else {
                statusCounter2++;
            }
//            System.out.println(responseTime1);
            if (responseTime1 >= timer1){
                counterTimer_1++;
            } else if (timer1 > responseTime1 && responseTime1 >= timer2) {
                counterTimer_2++;
                
            }else {
                counterTimer_3++;
            }
        }
//        JsonNode responseTime = data.get(4);
//        int responseTime1 = responseTime.get("ResponseTime").asInt();
//        System.out.println(responseTime1);


        System.out.println("counter 1 : "+counterTimer_1+" counter 2 : "+counterTimer_2+" counter 3 : "+counterTimer_3+" status 1 : "+statusCounter+" status 0 : "+statusCounter2);
    }


 */


