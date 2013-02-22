package com.pairliu.learning.vertx;

import java.util.concurrent.atomic.AtomicInteger;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.file.AsyncFile;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.core.streams.WriteStream;
import org.vertx.java.deploy.Verticle;

public class SimpleBothPump  extends Verticle {
    
    @Override
    public void start() throws Exception {
        System.out.println("In SimpleBothPump");
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest request) {
                HttpClient client = vertx.createHttpClient();
                client.setHost("abus-dev.aws.bluglu.net");
                client.setPort(8081);
                
                HttpClientRequest cRequest = client.request(request.method, "/files/myproject/adsk.s3/resGallery/ac4bf6ddd00b7e55014e44413ecab069/0.png", new Handler<HttpClientResponse>() {
                    public void handle(HttpClientResponse cResponse) {
                        cResponse.pause();
                        request.response.headers().putAll(cResponse.headers());
                        if ("chunked".equalsIgnoreCase((String) cResponse.headers().get("Transfer-Encoding"))) {
                            request.response.setChunked(true);
                        }
                        
                        final SimpleBothDecorator bothDeco = new SimpleBothDecorator(SimpleBothPump.this, cResponse, request.response);
                        cResponse.endHandler(new Handler<Void>() {
                            public void handle(Void event) {
                                
                                request.response.end();
                                //need to close file
                                bothDeco.end();
                                System.out.println("Size when cReponse ends is: " + bothDeco.getSize());
                            }
                            
                        });
                    }
                });
                cRequest.headers().put("X-ABus-Forwarded-For-ServiceId", "07403795-c030-4d24-aff9-ce4af8cd54a7");
                cRequest.end();
            }
        }).listen(8082);
    }
}

class SimpleBothDecorator implements WriteStream {

    AtomicInteger size = new AtomicInteger();
    AtomicInteger count = new AtomicInteger();
    private AsyncFile _file;
    private WriteStream _ws;
    private HttpServerResponse _response;
    
    public SimpleBothDecorator(final Verticle verticle, final HttpClientResponse cResponse, final HttpServerResponse response) {
        verticle.getVertx().fileSystem().open("d:/subfolder/testBoth2.png", null, true, true, true, true, new AsyncResultHandler<AsyncFile>() {
            public void handle(AsyncResult<AsyncFile> ar) {
                if (ar.exception == null) {
                    _file = ar.result;
                    _ws = _file.getWriteStream();
                    Pump.createPump(cResponse, SimpleBothDecorator.this).start();
                    cResponse.resume();
                } else {
                    ar.exception.printStackTrace();
                }
            }
        });
        _response = response;
    }

    public void writeBuffer(Buffer data) {
        _response.writeBuffer(data);
        size.addAndGet(data.length());
//        data.appendString("**"+ count.addAndGet(1) + "**");
        _ws.writeBuffer(data);
    }

    public void setWriteQueueMaxSize(int maxSize) {
        _response.setWriteQueueMaxSize(maxSize);
        _ws.setWriteQueueMaxSize(maxSize);
    }

    public boolean writeQueueFull() {
//        return _ws.writeQueueFull();
        return false;
    }

    public void drainHandler(Handler<Void> handler) {
        _response.drainHandler(handler);
        _ws.drainHandler(handler);
    }

    public void exceptionHandler(Handler<Exception> handler) {
        _response.exceptionHandler(handler);
        _ws.exceptionHandler(handler);
    }
    
    public void end() {
        _file.close();
    }
    
    public int getSize() {
        return size.get();
    }
    
}
