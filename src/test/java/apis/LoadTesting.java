package apis;

import eu.luminis.jmeter.wssampler.SamplingAbortedException;
import eu.luminis.jmeter.wssampler.WebsocketSampler;
import eu.luminis.websocket.Frame;
import eu.luminis.websocket.UnexpectedFrameException;
import eu.luminis.websocket.WebSocketClient;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.log.Logger;

import java.io.IOException;

public class LoadTesting {

    WebsocketSampler websocketSampler;

    public void verifyLoadTesting(){
        websocketSampler= new WebsocketSampler() {
            @Override
            protected String validateArguments() {
                return "";
            }

            @Override
            protected WebSocketClient prepareWebSocketClient(SampleResult sampleResult) {
                return null;
            }

            @Override
            protected Frame doSample(WebSocketClient webSocketClient, SampleResult sampleResult) throws IOException, UnexpectedFrameException, SamplingAbortedException {
                return null;
            }

            @Override
            protected Logger getLogger() {
                return null;
            }
        };
    }
}
