package apis;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SocketServiceData {
    String URI;
    String actualMessage;
    String expectedMessage;
    Map<String,String> requestHeaders=new HashMap<>();
    List<String> messageList=new ArrayList<>();
    int statusCode;
    int timeOut=10;
    int timeTaken;


}
