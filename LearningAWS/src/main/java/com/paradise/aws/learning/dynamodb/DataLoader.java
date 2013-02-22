package com.paradise.aws.learning.dynamodb;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodb.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodb.model.AttributeValue;
import com.amazonaws.services.dynamodb.model.PutItemRequest;

public class DataLoader {
    static AmazonDynamoDBClient client;
    static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    static String productCatalogTableName = "ProductCatalog";
    static String forumTableName = "Forum";
    static String threadTableName = "Thread";
    static String replyTableName = "Reply";
    
    public static void main(String[] args) throws Exception {
        createClient();

        try {
            uploadSampleProducts(productCatalogTableName);
            uploadSampleForums(forumTableName);
            uploadSampleThreads(threadTableName);
            uploadSampleReplies(replyTableName);

        } catch (AmazonServiceException ase) {
            System.err.println("Data load script failed.");
        }
    }
    
    private static void createClient() throws Exception {
        AWSCredentials credentials = new PropertiesCredentials(
                DataLoader.class.getResourceAsStream("AwsCredentials.properties"));

        client = new AmazonDynamoDBClient(credentials);
        client.setEndpoint("https://dynamodb.us-east-1.amazonaws.com");    
    }

    private static void uploadSampleProducts(String tableName) {
        
        try {
            // Add books.
            Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
            item.put("Id", new AttributeValue().withN("101"));
            item.put("Title", new AttributeValue().withS("Book 101 Title"));
            item.put("ISBN", new AttributeValue().withS("111-1111111111"));
            item.put("Authors", new AttributeValue().withSS(Arrays.asList("Author1")));
            item.put("Price", new AttributeValue().withN("2"));
            item.put("Dimensions", new AttributeValue().withS("8.5 x 11.0 x 0.5"));
            item.put("PageCount", new AttributeValue().withN("500"));
            item.put("InPublication", new AttributeValue().withN("1"));
            item.put("ProductCategory", new AttributeValue().withS("Book"));
            
            PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            client.putItem(itemRequest);
            item.clear();
            
            item.put("Id", new AttributeValue().withN("102"));
            item.put("Title", new AttributeValue().withS("Book 102 Title"));
            item.put("ISBN", new AttributeValue().withS("222-2222222222"));
            item.put("Authors", new AttributeValue().withSS(Arrays.asList("Author1", "Author2")));
            item.put("Price", new AttributeValue().withN("20"));
            item.put("Dimensions", new AttributeValue().withS("8.5 x 11.0 x 0.8"));
            item.put("PageCount", new AttributeValue().withN("600"));
            item.put("InPublication", new AttributeValue().withN("1"));
            item.put("ProductCategory", new AttributeValue().withS("Book"));
            
            itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            client.putItem(itemRequest);
            item.clear();
            
            item.put("Id", new AttributeValue().withN("103"));
            item.put("Title", new AttributeValue().withS("Book 103 Title"));
            item.put("ISBN", new AttributeValue().withS("333-3333333333"));
            item.put("Authors", new AttributeValue().withSS(Arrays.asList("Author1", "Author2")));
            // Intentional. Later we run scan to find price error. Find items > 1000 in price.            
            item.put("Price", new AttributeValue().withN("2000")); 
            item.put("Dimensions", new AttributeValue().withS("8.5 x 11.0 x 1.5"));
            item.put("PageCount", new AttributeValue().withN("600"));
            item.put("InPublication", new AttributeValue().withN("0"));
            item.put("ProductCategory", new AttributeValue().withS("Book"));

            itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            client.putItem(itemRequest);
            item.clear();

            // Add bikes.
            item.put("Id", new AttributeValue().withN("201"));
            item.put("Title", new AttributeValue().withS("18-Bike-201")); // Size, followed by some title.
            item.put("Description", new AttributeValue().withS("201 Description"));
            item.put("BicycleType", new AttributeValue().withS("Road"));
            item.put("Brand", new AttributeValue().withS("Mountain A")); // Trek, Specialized.
            item.put("Price", new AttributeValue().withN("100"));
            item.put("Gender", new AttributeValue().withS("M")); // Men's
            item.put("Color", new AttributeValue().withSS(Arrays.asList("Red", "Black")));
            item.put("ProductCategory", new AttributeValue().withS("Bicycle"));

            itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            client.putItem(itemRequest);
            item.clear();

            item.put("Id", new AttributeValue().withN("202"));
            item.put("Title", new AttributeValue().withS("21-Bike-202")); 
            item.put("Description", new AttributeValue().withS("202 Description"));
            item.put("BicycleType", new AttributeValue().withS("Road"));
            item.put("Brand", new AttributeValue().withS("Brand-Company A"));
            item.put("Price", new AttributeValue().withN("200"));
            item.put("Gender", new AttributeValue().withS("M"));
            item.put("Color", new AttributeValue().withSS(Arrays.asList("Green", "Black")));
            item.put("ProductCategory", new AttributeValue().withS("Bicycle"));
            
            itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            client.putItem(itemRequest);
            item.clear();

            item.put("Id", new AttributeValue().withN("203"));
            item.put("Title", new AttributeValue().withS("19-Bike-203")); 
            item.put("Description", new AttributeValue().withS("203 Description"));
            item.put("BicycleType", new AttributeValue().withS("Road"));
            item.put("Brand", new AttributeValue().withS("Brand-Company B"));
            item.put("Price", new AttributeValue().withN("300"));
            item.put("Gender", new AttributeValue().withS("W")); // Women's
            item.put("Color", new AttributeValue().withSS(Arrays.asList("Red", "Green", "Black")));
            item.put("ProductCategory", new AttributeValue().withS("Bicycle"));

            itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            client.putItem(itemRequest);
            item.clear();

            item.put("Id", new AttributeValue().withN("204"));
            item.put("Title", new AttributeValue().withS("18-Bike-204")); 
            item.put("Description", new AttributeValue().withS("204 Description"));
            item.put("BicycleType", new AttributeValue().withS("Mountain"));
            item.put("Brand", new AttributeValue().withS("Brand-Company B"));
            item.put("Price", new AttributeValue().withN("400"));
            item.put("Gender", new AttributeValue().withS("W"));
            item.put("Color", new AttributeValue().withSS(Arrays.asList("Red")));
            item.put("ProductCategory", new AttributeValue().withS("Bicycle"));

            itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            client.putItem(itemRequest);
            item.clear();

            item.put("Id", new AttributeValue().withN("205"));
            item.put("Title", new AttributeValue().withS("20-Bike-205")); 
            item.put("Description", new AttributeValue().withS("205 Description"));
            item.put("BicycleType", new AttributeValue().withS("Hybrid"));
            item.put("Brand", new AttributeValue().withS("Brand-Company C"));
            item.put("Price", new AttributeValue().withN("500"));
            item.put("Gender", new AttributeValue().withS("B")); // Boy's
            item.put("Color", new AttributeValue().withSS(Arrays.asList("Red", "Black")));
            item.put("ProductCategory", new AttributeValue().withS("Bicycle"));
            
            itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            client.putItem(itemRequest);

                
        }   catch (AmazonServiceException ase) {
            System.err.println("Failed to create item in " + tableName);
        } 

    }

    private static void uploadSampleForums(String tableName) {
        try {
            // Add forums.
            Map<String, AttributeValue> forum = new HashMap<String, AttributeValue>();
            forum.put("Name", new AttributeValue().withS("Amazon DynamoDB"));
            forum.put("Category", new AttributeValue().withS("Amazon Web Services"));
            forum.put("Threads", new AttributeValue().withN("2"));
            forum.put("Messages", new AttributeValue().withN("4"));
            forum.put("Views", new AttributeValue().withN("1000"));

            PutItemRequest forumRequest = new PutItemRequest().withTableName(tableName).withItem(forum);
            client.putItem(forumRequest);
            forum.clear();
            
            forum.put("Name", new AttributeValue().withS("Amazon S3"));
            forum.put("Category", new AttributeValue().withS("Amazon Web Services"));
            forum.put("Threads", new AttributeValue().withN("0"));
            
            forumRequest = new PutItemRequest().withTableName(tableName).withItem(forum);
            client.putItem(forumRequest);
                
        }   catch (AmazonServiceException ase) {
            System.err.println("Failed to create item in " + tableName);
        }         
    }

    private static void uploadSampleThreads(String tableName) {
        try {
            long time1 = (new Date()).getTime() - (7L*24L*60L*60L*1000L); // 7 days ago
            long time2 = (new Date()).getTime() - (14L*24L*60L*60L*1000L); // 14 days ago
            long time3 = (new Date()).getTime() - (21L*24L*60L*60L*1000L); // 21 days ago

            Date date1 = new Date();
            date1.setTime(time1);

            Date date2 = new Date();
            date2.setTime(time2);

            Date date3 = new Date();
            date3.setTime(time3);

            // Add threads.
            Map<String, AttributeValue> forum = new HashMap<String, AttributeValue>();
            forum.put("ForumName", new AttributeValue().withS("Amazon DynamoDB"));
            forum.put("Subject", new AttributeValue().withS("DynamoDB Thread 1"));
            forum.put("Message", new AttributeValue().withS("DynamoDB thread 1 message"));
            forum.put("LastPostedBy", new AttributeValue().withS("User A"));
            forum.put("LastPostedDateTime", new AttributeValue().withS(dateFormatter.format(date2)));
            forum.put("Views", new AttributeValue().withN("0"));
            forum.put("Replies", new AttributeValue().withN("0"));
            forum.put("Answered", new AttributeValue().withN("0"));
            forum.put("Tags", new AttributeValue().withSS(Arrays.asList("index", "primarykey", "table")));
            
            PutItemRequest forumRequest = new PutItemRequest().withTableName(tableName).withItem(forum);
            client.putItem(forumRequest);

            forum.clear();
            
            forum.put("ForumName", new AttributeValue().withS("Amazon DynamoDB"));
            forum.put("Subject", new AttributeValue().withS("DynamoDB Thread 2"));
            forum.put("Message", new AttributeValue().withS("DynamoDB thread 2 message"));
            forum.put("LastPostedBy", new AttributeValue().withS("User A"));
            forum.put("LastPostedDateTime", new AttributeValue().withS(dateFormatter.format(date3)));
            forum.put("Views", new AttributeValue().withN("0"));
            forum.put("Replies", new AttributeValue().withN("0"));
            forum.put("Answered", new AttributeValue().withN("0"));
            forum.put("Tags", new AttributeValue().withSS(Arrays.asList("index", "primarykey", "rangekey")));

            forumRequest = new PutItemRequest().withTableName(tableName).withItem(forum);
            client.putItem(forumRequest);
            
            forum.clear();
            
            forum.put("ForumName", new AttributeValue().withS("Amazon S3"));
            forum.put("Subject", new AttributeValue().withS("S3 Thread 1"));
            forum.put("Message", new AttributeValue().withS("S3 Thread 3 message"));
            forum.put("LastPostedBy", new AttributeValue().withS("User A"));
            forum.put("LastPostedDateTime", new AttributeValue().withS(dateFormatter.format(date1)));
            forum.put("Views", new AttributeValue().withN("0"));
            forum.put("Replies", new AttributeValue().withN("0"));
            forum.put("Answered", new AttributeValue().withN("0"));
            forum.put("Tags", new AttributeValue().withSS(Arrays.asList("largeobjects", "multipart upload")));

            forumRequest = new PutItemRequest().withTableName(tableName).withItem(forum);
            
            client.putItem(forumRequest);

                
        }   catch (AmazonServiceException ase) {
            System.err.println("Failed to create item in " + tableName);
        }         
        
    }

    private static void uploadSampleReplies(String tableName) {
        try {
            long time0 = (new Date()).getTime() - (1L*24L*60L*60L*1000L); // 1 day ago
            long time1 = (new Date()).getTime() - (7L*24L*60L*60L*1000L); // 7 days ago
            long time2 = (new Date()).getTime() - (14L*24L*60L*60L*1000L); // 14 days ago
            long time3 = (new Date()).getTime() - (21L*24L*60L*60L*1000L); // 21 days ago

            Date date0 = new Date();
            date0.setTime(time0);

            Date date1 = new Date();
            date1.setTime(time1);

            Date date2 = new Date();
            date2.setTime(time2);

            Date date3 = new Date();
            date3.setTime(time3);

            // Add threads.
            Map<String, AttributeValue> reply = new HashMap<String, AttributeValue>();
            reply.put("Id", new AttributeValue().withS("Amazon DynamoDB#DynamoDB Thread 1"));
            reply.put("ReplyDateTime", new AttributeValue().withS(dateFormatter.format(date3)));
            reply.put("Message", new AttributeValue().withS("DynamoDB Thread 1 Reply 1 text"));
            reply.put("PostedBy", new AttributeValue().withS("User A"));

            PutItemRequest replyRequest = new PutItemRequest().withTableName(tableName).withItem(reply);
            client.putItem(replyRequest);
            
            reply.clear();
            
            reply = new HashMap<String, AttributeValue>();
            reply.put("Id", new AttributeValue().withS("Amazon DynamoDB#DynamoDB Thread 1"));
            reply.put("ReplyDateTime", new AttributeValue().withS(dateFormatter.format(date2)));
            reply.put("Message", new AttributeValue().withS("DynamoDB Thread 1 Reply 2 text"));
            reply.put("PostedBy", new AttributeValue().withS("User B"));

            replyRequest = new PutItemRequest().withTableName(tableName).withItem(reply);
            client.putItem(replyRequest);

            reply.clear();

            reply = new HashMap<String, AttributeValue>();
            reply.put("Id", new AttributeValue().withS("Amazon DynamoDB#DynamoDB Thread 2"));
            reply.put("ReplyDateTime", new AttributeValue().withS(dateFormatter.format(date1)));
            reply.put("Message", new AttributeValue().withS("DynamoDB Thread 2 Reply 1 text"));
            reply.put("PostedBy", new AttributeValue().withS("User A"));

            replyRequest = new PutItemRequest().withTableName(tableName).withItem(reply);
            client.putItem(replyRequest);
            
            reply.clear();

            reply = new HashMap<String, AttributeValue>();
            reply.put("Id", new AttributeValue().withS("Amazon DynamoDB#DynamoDB Thread 2"));
            reply.put("ReplyDateTime", new AttributeValue().withS(dateFormatter.format(date0)));
            reply.put("Message", new AttributeValue().withS("DynamoDB Thread 2 Reply 2 text"));
            reply.put("PostedBy", new AttributeValue().withS("User A"));

            replyRequest = new PutItemRequest().withTableName(tableName).withItem(reply);

            client.putItem(replyRequest);
 
        }   catch (AmazonServiceException ase) {
            System.err.println("Failed to create item in " + tableName);
        }        
    }
}
