package com.pairliu.learning.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class Sample {
	public static void main(String[] args) {
		Mongo conn;
		try {
			List<ServerAddress> servers = new ArrayList<ServerAddress>();
			servers.add( new ServerAddress("localhost", 27017) );
			conn = new Mongo(servers);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		WriteConcern w = new WriteConcern(1, 2000);
		conn.setWriteConcern(w);
		DB db = conn.getDB("crawler");
		DBCollection coll = db.getCollection("sites");
		CommandResult cr = db.command( "stats");
		System.out.println( cr.toString() );
		DBObject doc = new BasicDBObject();
		String[] tags = { "database", "open-source" };
		doc.put("url", "org.mongodb");
		doc.put("tags", tags);
		DBObject attrs = new BasicDBObject();
		attrs.put("lastAccess", new Date());
		attrs.put("pingtime", 20);
		doc.put("attrs", attrs);
		
		coll.insert(doc);

		System.out.println("Initial document:n");
		System.out.println(doc.toString());
		System.out.println("Updating pingtime...n");
		coll.update(new BasicDBObject("_id", doc.get("_id")),
				new BasicDBObject("$set", new BasicDBObject("pingtime", 30)));
		DBCursor cursor = coll.find();
		System.out.println("After updaten");
		while ( cursor.hasNext() ) {
			System.out.println(cursor.next().toString());
		}
				
		System.out.println("Number of site documents: " + coll.count());
		System.out.println("Removing documents...n");
//		coll.remove(new BasicDBObject());
	}
}
