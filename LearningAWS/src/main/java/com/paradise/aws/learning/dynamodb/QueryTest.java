package com.paradise.aws.learning.dynamodb;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodb.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodb.model.AttributeValue;
import com.amazonaws.services.dynamodb.model.ComparisonOperator;
import com.amazonaws.services.dynamodb.model.Condition;
import com.amazonaws.services.dynamodb.model.GetItemRequest;
import com.amazonaws.services.dynamodb.model.GetItemResult;
import com.amazonaws.services.dynamodb.model.Key;
import com.amazonaws.services.dynamodb.model.QueryRequest;
import com.amazonaws.services.dynamodb.model.QueryResult;

public class QueryTest {
    static AmazonDynamoDBClient client;

    public static void main(String[] args) throws Exception {
        try {

            String forumName = "Amazon DynamoDB";
            String threadSubject = "DynamoDB Thread 1";

            createClient();
            // Get an item.
            getBook("101", "ProductCatalog");

            // Query replies posted in the past 15 days for a forum thread.
            findRepliesInLast15DaysWithConfig("Reply", forumName, threadSubject);
        } catch (AmazonServiceException ase) {
            System.err.println(ase.getMessage());
        }
    }

    private static void createClient() throws IOException {

        AWSCredentials credentials = new PropertiesCredentials(
                QueryTest.class
                        .getResourceAsStream("AwsCredentials.properties"));

        client = new AmazonDynamoDBClient(credentials);
    }

    private static void getBook(String id, String tableName) {

        GetItemRequest getItemRequest = new GetItemRequest()
            .withTableName(tableName)
            .withKey(new Key().withHashKeyElement(new AttributeValue().withN(id)))
            .withAttributesToGet(Arrays.asList("Id", "ISBN", "Title", "Authors"));

        GetItemResult result = client.getItem(getItemRequest);

        // Check the response.
        System.out.println("Printing item after retrieving it....");
        printItem(result.getItem());
    }

    private static void findRepliesInLast15DaysWithConfig(
            String tableName,
            String forumName, String threadSubject) {

        String replyId = forumName + "#" + threadSubject;
        long twoWeeksAgoMilli = (new Date()).getTime()
                - (15L * 24L * 60L * 60L * 1000L);
        Date twoWeeksAgo = new Date();
        twoWeeksAgo.setTime(twoWeeksAgoMilli);
        SimpleDateFormat df = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String twoWeeksAgoStr = df.format(twoWeeksAgo);

        Key lastKeyEvaluated = null;
        do {

            //Date is a String, which is not good...
            Condition rangeKeyCondition = new Condition()
                    .withComparisonOperator(ComparisonOperator.GT.toString())
                    .withAttributeValueList(new AttributeValue().withS(twoWeeksAgoStr));

            //Only range key can be query by the comparison operator?
            QueryRequest queryRequest = new QueryRequest()
                    .withTableName(tableName)
                    .withHashKeyValue(new AttributeValue().withS(replyId))
                    .withRangeKeyCondition(rangeKeyCondition)
                    .withAttributesToGet(Arrays.asList("Message", "ReplyDateTime",
                                    "PostedBy")).withLimit(1)
                    .withExclusiveStartKey(lastKeyEvaluated);

            QueryResult result = client.query(queryRequest);
            for (Map<String, AttributeValue> item : result.getItems()) {
                printItem(item);
            }
            lastKeyEvaluated = result.getLastEvaluatedKey();
        } while (lastKeyEvaluated != null);
    }

    private static void printItem(Map<String, AttributeValue> attributeList) {
        for (Map.Entry<String, AttributeValue> item : attributeList.entrySet()) {
            String attributeName = item.getKey();
            AttributeValue value = item.getValue();
            System.out.println(attributeName
                    + " "
                    + (value.getS() == null ? "" : "S=[" + value.getS() + "]")
                    + (value.getN() == null ? "" : "N=[" + value.getN() + "]")
                    + (value.getB() == null ? "" : "B=[" + value.getB() + "]")
                    + (value.getSS() == null ? "" : "SS=[" + value.getSS()
                            + "]")
                    + (value.getNS() == null ? "" : "NS=[" + value.getNS()
                            + "]")
                    + (value.getBS() == null ? "" : "BS=[" + value.getBS()
                            + "] \n"));
        }
    }

}
