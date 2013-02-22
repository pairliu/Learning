package com.pairliu.learning.vertx;

import org.jboss.netty.handler.codec.http.HttpConstants;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.SimpleHandler;
import org.vertx.java.core.file.AsyncFile;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.core.streams.ReadStream;
import org.vertx.java.deploy.Verticle;

public class PumpFileBack extends Verticle {
    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest request) {
                request.pause();
                getVertx().fileSystem().open("C:\\Users\\liujam\\cache\\HR\\HRWO/Xp9AXgtNHZHxEN8zb6Ll6o=", new AsyncResultHandler<AsyncFile>() {
                    public void handle(AsyncResult<AsyncFile> ar) {
                        if (ar.exception != null) {
                            ar.exception.printStackTrace();
                        } else {
                            final AsyncFile file = ar.result;
                            request.response.headers().put("content-length", "46856");
                            request.response.headers().put("content-type", "image/png;charset=UTF-8");
                            final ReadStream rs = file.getReadStream();
//                            rs.pause();
                            Pump.createPump(rs, request.response).start();
//                            request.resume();
//                            rs.resume();
                            rs.endHandler(new SimpleHandler() {
                                public void handle() {
                                    // File sent, end HTTP response
                                    request.response.statusCode = 200;
                                    request.response.statusMessage = "OK";
                                    request.response.end();
                                    file.close();
                                }
                            });
                        }
                    }
                    
                });
            }
        }).listen(8082);
        
    }

}
