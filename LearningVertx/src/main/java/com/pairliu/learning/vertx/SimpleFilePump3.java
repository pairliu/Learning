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

public class SimpleFilePump3 extends Verticle {
    @Override
    public void start() throws Exception {
        System.out.println("In SimpleFilePump3");
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest request) {
                
                getVertx().fileSystem().open("d:/subfolder/abcd.jpg",new AsyncResultHandler<AsyncFile>() {
                    public void handle(AsyncResult<AsyncFile> ar) {
                        if (ar.exception == null) {                            
                            final AsyncFile _file = ar.result;
                            
                            HttpClient client = vertx.createHttpClient();
//                          client.setHost("abus-dev.aws.bluglu.net");
//                          client.setPort(8081);

                            client.setHost("www.sinaimg.cn");
                            client.setPort(80);
                          
                            HttpClientRequest cRequest = client.request(request.method, "/dy/slidenews/2_img/2013_03/789_899331_838631.jpg", new Handler<HttpClientResponse>() {
                                public void handle(final HttpClientResponse cResponse) {
//                                    request.pause();
                                    cResponse.pause();
//                                  request.response.headers().putAll(cResponse.headers());
//                                  if ("chunked".equalsIgnoreCase((String) cResponse.headers().get("Transfer-Encoding"))) {
                                      //As we don't write anything back to the HttpServerResponse, 
                                      //set chunked to true here, so that it can be ended directly later.
//                                      request.response.setChunked(true);
//                                  }
                                                          
//                                  getVertx().fileSystem().open("d:/subfolder/abcd.png",new AsyncResultHandler<AsyncFile>() {
                                  
                                    final Pump pump = Pump.createPump(cResponse, _file.getWriteStream());
                                    
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
                                        
                                    });
                                    
                                    pump.start();
                                    cResponse.resume();
//                                    request.resume();
                                  
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
