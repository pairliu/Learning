package com.paradise.ehcache.learning;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheTest {

    public static void main(String[] args) {
        CacheManager cm = CacheManager.newInstance();
//        Cache c = new Cache("abcd", Integer.MAX_VALUE, false, false, 3600, 3600); //Will cause OOM
        
        cm.addCache("testCache");
        for (String s : cm.getCacheNames()) {
            System.out.println(s);
        }

        //Get another time
        CacheManager cm2 = CacheManager.newInstance();
        for (String s : cm2.getCacheNames()) {
            System.out.println(s);
        }
        
        System.out.println("Is identical? " + (cm == cm2));
        
        Cache cache = cm.getCache("testCache");
        Element elem = new Element("akey", "value");
        cache.put(elem);
        
        cm.shutdown();
    }

}
