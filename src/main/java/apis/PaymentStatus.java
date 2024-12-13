package apis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PaymentStatus {

   public static void check(){


       FileInputStream urlHeader;

       FileInputStream requestData;

       {
           try {
               urlHeader = new FileInputStream("src/main/resources/baseUrl.properties");
               requestData = new FileInputStream("src/main/resources/request.properties");
           } catch (FileNotFoundException e) {
               throw new RuntimeException(e);
           }
       }

       Properties p=new Properties();
       try {
           p.load(urlHeader);
           p.load(requestData);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       String url=p.getProperty("baseUrl");
       System.out.println("Base Url : "+url);
       String requestValue=p.getProperty("gocid");

       System.out.println("Gocid : "+requestValue);

   }


//    public static void main(String[] args) {
//        check();
//    }


}
