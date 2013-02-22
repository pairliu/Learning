package com.pairliu.learning.vertx;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.file.AsyncFile;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.deploy.Verticle;

public class SimpleRelayPump  extends Verticle {
    
    @Override
    public void start() throws Exception {
        System.out.println("In SimpleRelayPump");
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest request) {
                HttpClient client = vertx.createHttpClient();

//                client.setHost("abus-dev.aws.bluglu.net");
//                client.setPort(8081);
                client.setHost("www.sinaimg.cn");
                client.setPort(80);
                
//                HttpClientRequest cRequest = client.request(request.method, "/files/myproject/adsk.s3/resGallery/ac4bf6ddd00b7e55014e44413ecab069/0.png", new Handler<HttpClientResponse>() {
                  HttpClientRequest cRequest = client.request(request.method, "/dy/slidenews/2_img/2013_03/789_899331_838631.jpg", new Handler<HttpClientResponse>() {
                    public void handle(final HttpClientResponse cResponse) {
                        cResponse.pause();
                        request.response.headers().putAll(cResponse.headers());
                        if ("chunked".equalsIgnoreCase((String) cResponse.headers().get("Transfer-Encoding"))) {
                            request.response.setChunked(true);
                        }
                        
//                        getVertx().fileSystem().open("d:/subfolder/testBoth2.png", new AsyncResultHandler<AsyncFile>() {
                        getVertx().fileSystem().open("d:/subfolder/testRelay.jpg", new AsyncResultHandler<AsyncFile>() {
                            public void handle(final AsyncResult<AsyncFile> ar) {
                                if (ar.exception == null) {
                                    final Pump pump = Pump.createPump(cResponse, ar.result.getWriteStream());
                                    cResponse.endHandler(new Handler<Void>() {
                                        public void handle(Void event) {
                                            ar.result.close(new AsyncResultHandler<Void>() {
                                                public void handle(AsyncResult<Void> event) {
                                                    System.out.println("Written " + pump.getBytesPumped() + " bytes.");
                                                    //Open again to pump back to response
//                                                    getVertx().fileSystem().open("d:/subfolder/testBoth2.png", new AsyncResultHandler<AsyncFile>() {
                                                    getVertx().fileSystem().open("d:/subfolder/testRelay.jpg", new AsyncResultHandler<AsyncFile>() {
                                                        public void handle(final AsyncResult<AsyncFile> ar2) {
                                                            if (ar2.exception == null) {
                                                                final Pump pump2 = Pump.createPump(ar2.result.getReadStream(), request.response);
                                                                ar2.result.getReadStream().endHandler(new Handler<Void>() {
                                                                    public void handle(Void event) {
                                                                        ar2.result.close(new AsyncResultHandler<Void>() {
                                                                            public void handle(AsyncResult<Void> event) {
                                                                                System.out.println("Read " + pump2.getBytesPumped() + " bytes.");
                                                                                //End the response finally
                                                                                request.response.end();
                                                                            }
                                                                            
                                                                        });
                                                                    }
                                                                });
                                                                pump2.start();
                                                            } else {
                                                                System.out.println("Exception when reading...");
                                                                ar2.exception.printStackTrace();
                                                            }
                                                        }
                                                        
                                                    });
                                                }
                                                
                                            });
                                        }
                                    });
                                    pump.start();
                                    
                                    cResponse.resume();
                                } else {
                                    System.out.println("Exception when writing...");
                                    ar.exception.printStackTrace();
                                }
                            }
                        });
                        
                        
                    }
                });
//                cRequest.headers().put("X-ABus-Forwarded-For-ServiceId", "07403795-c030-4d24-aff9-ce4af8cd54a7");
                cRequest.end();
            }
        }).listen(8082);
    }

}
