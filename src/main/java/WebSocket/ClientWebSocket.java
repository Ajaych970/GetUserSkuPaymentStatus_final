package WebSocket;

import org.java_websocket.WebSocketListener;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

public class ClientWebSocket extends WebSocketClient {

    String lastMessage;
    long reponseTime;

    public ClientWebSocket(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);

    }



    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {

//        System.out.println("Response on Message : "+message);
        lastMessage=message;
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
    @Override
    public void close() {
        super.close();
    }

//    @Override
//    public void send(String text) {
//        super.send(text);
//    }

    @Override
    public boolean isOpen() {
        return super.isOpen();
    }

    @Override
    public boolean isClosed() {
        return super.isClosed();
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
