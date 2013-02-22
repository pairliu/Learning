package com.pairliu.learning.vertx;

import java.util.concurrent.atomic.AtomicInteger;

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.core.streams.WriteStream;
import org.vertx.java.deploy.Verticle;

public class SimpleResponsePump extends Verticle {
    @Override
    public void start() throws Exception {
        System.out.println("In SimpleResponsePump");
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest request) {
                HttpClient client = vertx.createHttpClient();
//                client.setHost("abus-dev.aws.bluglu.net");
//                client.setPort(8081);
                client.setHost("www.sinaimg.cn");
                client.setPort(80);
                
//                HttpClientRequest cRequest = client.request(request.method, "/files/myproject/adsk.s3/resGallery/ac4bf6ddd00b7e55014e44413ecab069/0.png", new Handler<HttpClientResponse>() {
                HttpClientRequest cRequest = client.request(request.method, "/dy/slidenews/2_img/2013_03/789_899331_838631.jpg", new Handler<HttpClientResponse>() {
                    public void handle(HttpClientResponse cResponse) {
                        cResponse.pause();
                        request.response.headers().putAll(cResponse.headers());
                        if ("chunked".equalsIgnoreCase((String) cResponse.headers().get("Transfer-Encoding"))) {
                            request.response.setChunked(true);
                        }
                        
                        final SimpleResponseDecorator responseDeco = new SimpleResponseDecorator(request.response);
                        Pump.createPump(cResponse, responseDeco).start();
                        cResponse.endHandler(new Handler<Void>() {
                            public void handle(Void event) {
                                request.response.end();
                                System.out.println("Size when cReponse ends is: " + responseDeco.getSize());
                            }
                            
                        });
                        cResponse.resume();
                    }
                });
                cRequest.headers().put("X-ABus-Forwarded-For-ServiceId", "07403795-c030-4d24-aff9-ce4af8cd54a7");
                cRequest.end();
            }
        }).listen(8082);
    }
}

class SimpleResponseDecorator implements WriteStream {
    AtomicInteger size = new AtomicInteger();
    AtomicInteger count = new AtomicInteger();
    private final HttpServerResponse _response;
    
    public SimpleResponseDecorator(final HttpServerResponse response) {
        _response = response;
    }

    public void writeBuffer(Buffer data) {
        size.addAndGet(data.length());
        _response.writeBuffer(data);
    }

    public void setWriteQueueMaxSize(int maxSize) {
        _response.setWriteQueueMaxSize(maxSize);
    }

    public boolean writeQueueFull() {
        return _response.writeQueueFull();
    }

    public void drainHandler(Handler<Void> handler) {
        _response.drainHandler(handler);
        
    }

    public void exceptionHandler(Handler<Exception> handler) {
        _response.exceptionHandler(handler);
    }
    
    public int getSize() {
        return size.get();
    }
}
