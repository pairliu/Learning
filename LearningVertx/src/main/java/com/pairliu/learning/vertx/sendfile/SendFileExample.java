package com.pairliu.learning.vertx.sendfile;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.deploy.Verticle;

public class SendFileExample extends Verticle {
    private static final String webroot = "D:\\Work\\Workspace\\LearningVertx\\target\\classes\\com\\pairliu\\learning\\vertx\\sendfile\\";

    public void start() {
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                if (req.path.equals("/")) {
                    req.response.sendFile(webroot + "index.html");
                } else {
                    // Clearly in a real server you would check the path for
                    // better security!!
                    req.response.sendFile(webroot + req.path);
                }
            }
        }).listen(8082);
    }
}
