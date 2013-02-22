package com.pairliu.learning.vertx;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.deploy.Verticle;

public class PumpInterceptor extends Verticle {
    public void start() {
        System.out.println("In PumpInterceptor");
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(final HttpServerRequest request) {                
                System.out.println("Request for " + request.path);
                
                //The response need to set chunked or the content-length
//                request.response.setChunked(true);
                
                HttpClient client = vertx.createHttpClient();
                client.setHost("abus-dev.aws.bluglu.net");
                client.setPort(8081);
                
                HttpClientRequest cRequest = client.request(request.method, "/files/myproject/adsk.s3/resGallery/ac4bf6ddd00b7e55014e44413ecab069/0.png", new Handler<HttpClientResponse>() {
                    public void handle(final HttpClientResponse response) {
                        request.response.headers().putAll(response.headers());
                        if ("chunked".equalsIgnoreCase((String) response.headers().get("Transfer-Encoding"))) {
                            request.response.setChunked(true);
                        }
                        
                        if (request.path.equals("/")) {
                            final PumpFileDecorator responseDeco = new PumpFileDecorator(response, request.response, PumpInterceptor.this);                            

                            responseDeco.start();
                            
                        } else {
                            Pump.createPump(response, request.response).start();
                            
                            response.endHandler(new Handler<Void>() {
                                public void handle(Void event) {
                                    request.response.end();
                                }
                            });
                        }
                        

                    }
                    
                });
                //After calling end, the request will actually fire.
                cRequest.headers().put("X-ABus-Forwarded-For-ServiceId", "07403795-c030-4d24-aff9-ce4af8cd54a7");
                cRequest.end();
            }
            
        }).listen(8082);
    }
}
