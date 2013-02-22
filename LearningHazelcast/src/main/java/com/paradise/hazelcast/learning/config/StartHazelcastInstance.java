package com.paradise.hazelcast.learning.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class StartHazelcastInstance {
    
    public static void main(String[] args) throws Exception {
        Config cfg = new XmlConfigBuilder("D:\\Work\\ABus\\LearningHazelcast\\src\\main\\resources\\hazelcast.xml").build();
//        final Config cfg = new Config();
        final HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        
    }

}
