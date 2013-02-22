package com.paradise.java7.learning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GenericsAndVarargs {
    
    public static void main(String[] args) {
        //Simple object will not cause warning in Java 6
        Integer i1 = new Integer(10);
        Integer i2 = new Integer(20);
        get(i1, i2);
        
        Map<String, String> m1 = new HashMap<String, String>();
        Map<String, String> m2 = new HashMap<String, String>();
        get(m1, m2);
    }
    
    public static <T> Collection<T> get(T... args) {
        Collection<T> t = new ArrayList<T>();
        for (T arg : args) {
            t.add(arg);
        }
        return t;
    }

}
