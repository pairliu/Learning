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

public class SimpleBothPump3 extends Verticle {

    @Override
    public void start() throws Exception {
        System.out.println("In SimpleBothPump3");
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest request) {
                getVertx().fileSystem().open("d:/subfolder/testBoth3.jpg", new AsyncResultHandler<AsyncFile>() {
                    public void handle(AsyncResult<AsyncFile> ar) {
                        if (ar.exception == null) {
                            final AsyncFile _file = ar.result;
                            final WriteStream _ws = _file.getWriteStream();

                            HttpClient client = vertx.createHttpClient();
                            client.setHost("www.sinaimg.cn");
                            client.setPort(80);

                            HttpClientRequest cRequest = client.request(request.method, "/dy/slidenews/2_img/2013_03/789_899331_838631.jpg", new Handler<HttpClientResponse>() {
                                public void handle(HttpClientResponse cResponse) {
                                    cResponse.pause();
                                    request.response.headers().putAll(cResponse.headers());
                                    if ("chunked".equalsIgnoreCase((String) cResponse.headers().get("Transfer-Encoding"))) {
                                        request.response.setChunked(true);
                                    }

                                    final SimpleBothDecorator3 bothDeco = new SimpleBothDecorator3(request.response, _ws);

                                    final Pump pump = Pump.createPump(cResponse, bothDeco);

                                    cResponse.endHandler(new Handler<Void>() {

                                        public void handle(Void event) {
                                            _file.close(new AsyncResultHandler<Void>() {
                                                public void handle(AsyncResult<Void> arc) {
                                                    if (arc.exception == null) {
                                                        request.response.end();
                                                        System.out.println("Pumped " + pump.getBytesPumped() + " bytes.");
                                                    } else {
                                                        arc.exception.printStackTrace();
                                                    }
                                                }
                                            });
                                        }

                                    }

                                    );
                                    pump.start();
                                    cResponse.resume();
                                }
                            });

                            cRequest.headers().put("X-ABus-Forwarded-For-ServiceId", "07403795-c030-4d24-aff9-ce4af8cd54a7");
                            cRequest.end();
                        } else {
                            ar.exception.printStackTrace();
                        }
                    }
                });
            }
        }).listen(8082);
    }
}

class SimpleBothDecorator3 implements WriteStream {

    private WriteStream _ws;
    private HttpServerResponse _response;

    public SimpleBothDecorator3(final HttpServerResponse response, final WriteStream ws) {
        _response = response;
        _ws = ws;
    }

    public void writeBuffer(Buffer data) {
        _response.writeBuffer(data);
        _ws.writeBuffer(data);
    }

    public void setWriteQueueMaxSize(int maxSize) {
        _response.setWriteQueueMaxSize(maxSize);
        _ws.setWriteQueueMaxSize(maxSize);
    }

    public boolean writeQueueFull() {
        return _ws.writeQueueFull() || _response.writeQueueFull();
    }

    public void drainHandler(Handler<Void> handler) {
        _response.drainHandler(handler);
        _ws.drainHandler(handler);
    }

    public void exceptionHandler(Handler<Exception> handler) {
        _response.exceptionHandler(handler);
        _ws.exceptionHandler(handler);
    }

}
