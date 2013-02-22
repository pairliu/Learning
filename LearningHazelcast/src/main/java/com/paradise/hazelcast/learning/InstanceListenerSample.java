package com.paradise.hazelcast.learning;

import java.util.Collection;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Instance;
import com.hazelcast.core.InstanceEvent;
import com.hazelcast.core.InstanceListener;

public class InstanceListenerSample implements InstanceListener {

    public void instanceCreated(InstanceEvent event) {
        Instance in = event.getInstance();
        System.out.println("Instance created with id:   " + in.getId());
        System.out.println("Instance created with type: " + in.getInstanceType());
        
    }

    public void instanceDestroyed(InstanceEvent event) {
        Instance in = event.getInstance();
        System.out.println("Instance destroyed with id:   " + in.getId());
        System.out.println("Instance destroyed with type: " + in.getInstanceType());
    }
    
    public static void main(String[] args) {
        InstanceListenerSample sample = new InstanceListenerSample();
        Config cfg = new Config();
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(cfg);
        hz.addInstanceListener(sample);
        
        Collection<Instance> instances = hz.getInstances();
        for (Instance instance : instances) {
            System.out.println(instance.getInstanceType() + "," + instance.getId());
        }
    }

}
