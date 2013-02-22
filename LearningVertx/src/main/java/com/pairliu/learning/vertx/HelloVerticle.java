package com.pairliu.learning.vertx;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.net.NetSocket;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.deploy.Verticle;

public class HelloVerticle extends Verticle {
	
	

	@Override
	public void start() throws Exception {
		vertx.createNetServer().connectHandler( new Handler<NetSocket>() {

			public void handle(final NetSocket socket) {
				Pump.createPump(socket, socket).start();
			}			
		}).listen(1234);
		
		EventBus eb = vertx.eventBus();
		Handler<Message<String>> handler = new Handler<Message<String>>() {
			public void handle(Message<String> event) {
				System.out.println( event.body );		
				event.reply( "Reply Message" );
			}			
		};		
		eb.registerHandler( "test.address", handler );
		eb.publish("test.address", "HelloWorld Publish");
		eb.send("test.address", "HelloWorld Send");
		ConcurrentMap<String, String> map = vertx.sharedData().getMap( "mapname" );
		Set<String> set = vertx.sharedData().getSet("demo.myset");
	}

}
