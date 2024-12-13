package apis;

import Http.BaseApi;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiLogic {

    private static final int CLIENT_COUNT = 1000;

    public static Properties readUrlFile() throws FileNotFoundException {
        FileInputStream urlAndHeader=new FileInputStream("src/main/resources/baseUrl.properties");
        Properties properties=new Properties();
        try {
            properties.load(urlAndHeader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

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

    /*
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

//        CountDownLatch latch=new CountDownLatch(CLIENT_COUNT);
//        ExecutorService execute= Executors.newFixedThreadPool(CLIENT_COUNT);
//
//        for(int i=0;i<CLIENT_COUNT; i++){
//            execute.submit(() ->{
//
//
//            });
//
//        }
//        latch.wait();
//        execute.shutdown();
//        System.out.println("all task completed");
//
//        System.out.println(readUrlFile().getProperty("baseUrl"));
//        System.out.println(getHeader());

        BaseApi baseUrl = new BaseApi(URI.create(readUrlFile().getProperty("baseUrl")), getHeader());

        boolean connected = baseUrl.connectBlocking();

        if(connected == true){
            System.out.println("Websocket connected successfully");
        }else {
            System.out.println("Websocket not connected");
        }





    }

     */
    int count=0;
    @Test(invocationCount = 10,threadPoolSize = 1)
    public void check() throws FileNotFoundException, InterruptedException {
        BaseApi baseUrl = new BaseApi(URI.create(readUrlFile().getProperty("baseUrl")), getHeader());

        long taskStartCurrentTime=System.currentTimeMillis();

        boolean connected = false;
        try {
            connected = baseUrl.connectBlocking();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(connected == true){
            System.out.println("Websocket connected successfully");
            count=count+1;
            System.out.println("Count "+count);
        }else {
//            System.out.println("Websocket not connected");
            System.err.println("WebSocket not connected");
        }
        long taskEndCurrentTime=System.currentTimeMillis();
//        Thread.sleep(1000);
        baseUrl.close();

        System.out.println("Request time : "+(taskEndCurrentTime - taskStartCurrentTime)+" milliSeconds");

    }


}
