package com.pairliu.learning.vertx.exception;

import org.vertx.java.core.Handler;
import org.vertx.java.core.net.NetSocket;
import org.vertx.java.deploy.Verticle;

public class ExceptionInHandler extends Verticle {

    @Override
    public void start() throws Exception {
        System.out.println("start()'s current thread is: " + Thread.currentThread().getName());
        
        vertx.createNetServer().connectHandler(new Handler<NetSocket>() {
            @Override
            public void handle(NetSocket socket) {
                System.out.println("handle()'s current thread is: " + Thread.currentThread().getName());
                throw new NullPointerException("Test");
            }
            
        }).listen(1234);
        
    }

}
