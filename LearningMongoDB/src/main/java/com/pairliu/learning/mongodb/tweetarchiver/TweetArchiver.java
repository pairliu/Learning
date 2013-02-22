package com.pairliu.learning.mongodb.tweetarchiver;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

public class TweetArchiver {
	
	private Mongo conn;
	private DB db;
	private DBCollection coll;
	
	public TweetArchiver() {
		try {
			List<ServerAddress> servers = new ArrayList<ServerAddress>();
			servers.add( new ServerAddress("localhost", 27017) );
			conn = new Mongo( servers );
			db = conn.getDB( Constants.DB_NAME );
			coll = db.getCollection( Constants.COLL_NAME );
			
			//Create two indexes
			coll.createIndex( new BasicDBObject("id", 1),
					new BasicDBObject("unique", true) );
			coll.createIndex( new BasicDBObject("tags", 1) );
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		} catch (MongoException e) {			
			e.printStackTrace();
			
		}
	}
	
	
	
}
