package com.paradise.hazelcast.learning;

import java.util.Map;
import java.util.Queue;

import com.hazelcast.core.Hazelcast;

public class GettingStarted {
    public static void main(String[] args) {
        Map<Integer, String> mapCustomers = Hazelcast.getMap("customers");
        
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");

        System.out.println("Customer with key 1: " + mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());

        Queue<String> queueCustomers = Hazelcast.getQueue("customers");
        queueCustomers.offer("Tom");
        queueCustomers.offer("Mary");
        queueCustomers.offer("Jane");
        System.out.println("First customer: " + queueCustomers.poll());
        System.out.println("Second customer: " + queueCustomers.peek());
        System.out.println("Queue size: " + queueCustomers.size());
    }
}
