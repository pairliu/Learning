package com.pairliu.learning.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		DBObject simple = new BasicDBObject("username", "Jones");
		simple.put("zip", "10011");

		DBObject doc = new BasicDBObject();
		String[] tags = { "database", "open-source" };
		doc.put("url", "org.mongodb");
		doc.put("tags", tags);
		DBObject attrs = new BasicDBObject();
		attrs.put("lastAccess", new Date());
		attrs.put("pingtime", 20);
		doc.put("attrs", attrs);
		System.out.println(doc.toString());
		
		try {
			List<ServerAddress> servers = new ArrayList<ServerAddress>();
			servers.add( new ServerAddress("localhost", 27017 ));
			
			Mongo conn = new Mongo( servers );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		
		
	}
}
