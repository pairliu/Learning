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
import org.vertx.java.core.streams.Pump;
import org.vertx.java.core.streams.WriteStream;
import org.vertx.java.deploy.Verticle;

public class SimpleFilePump extends Verticle {
    @Override
    public void start() throws Exception {
        System.out.println("In SimpleFilePump");
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest request) {
                HttpClient client = vertx.createHttpClient();
                client.setHost("abus-dev.aws.bluglu.net");
                client.setPort(8081);
                
                HttpClientRequest cRequest = client.request(request.method, "/files/myproject/adsk.s3/resGallery/ac4bf6ddd00b7e55014e44413ecab069/0.png", new Handler<HttpClientResponse>() {
                    public void handle(HttpClientResponse cResponse) {
                        cResponse.pause();
//                        request.response.headers().putAll(cResponse.headers());
//                        if ("chunked".equalsIgnoreCase((String) cResponse.headers().get("Transfer-Encoding"))) {
                            //As we don't write anything back to the HttpServerResponse, 
                            //set chunked to true here, so that it can be ended directly later.
//                            request.response.setChunked(true);
//                        }
                        
                        final SimpleFileDecorator fileDeco = new SimpleFileDecorator(SimpleFilePump.this, cResponse);
                        cResponse.endHandler(new Handler<Void>() {
                            public void handle(Void event) {
                                request.response.end();
                                //need to close file
                                fileDeco.end();
                                System.out.println("Size when cReponse ends is: " + fileDeco.getSize());
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

class SimpleFileDecorator implements WriteStream {
    AtomicInteger size = new AtomicInteger();
    private AsyncFile _file;
    private WriteStream _ws;
    public SimpleFileDecorator(final Verticle verticle, final HttpClientResponse cResponse) {
        final Pump pump = Pump.createPump(cResponse, SimpleFileDecorator.this);
        
        verticle.getVertx().fileSystem().open("d:/subfolder/test.png",new AsyncResultHandler<AsyncFile>() {
            public void handle(AsyncResult<AsyncFile> ar) {
                if (ar.exception == null) {
                    _file = ar.result;
                    _ws = _file.getWriteStream();
                    pump.start();
                    cResponse.resume();
                } else {
                    ar.exception.printStackTrace();
                }
            }
        });
    }

    public void writeBuffer(Buffer data) {
        size.addAndGet(data.length());
        _ws.writeBuffer(data);
    }

    public void setWriteQueueMaxSize(int maxSize) {
        _ws.setWriteQueueMaxSize(maxSize);
    }

    public boolean writeQueueFull() {
        return _ws.writeQueueFull();
    }

    public void drainHandler(Handler<Void> handler) {
        _ws.drainHandler(handler);
    }

    public void exceptionHandler(Handler<Exception> handler) {
        _ws.exceptionHandler(handler);
    }
    
    public void end() {
        _file.close();
    }
    
    public int getSize() {
        return size.get();
    }
}
