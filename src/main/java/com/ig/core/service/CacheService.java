package com.ig.core.service;

import com.ig.core.model.CacheObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CacheService {
    private long timeToLive;
    private Map<String, CacheObject> cacheMap = new HashMap<>();



    public CacheService(final long timeInterval, int max) {
        this.timeToLive = System.currentTimeMillis() + 60;

        cacheMap = new HashMap<>(max);

        if (timeInterval > 0) {

            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(timeInterval * 1000);
                        } catch (InterruptedException ex) {
                        }

                    }
                }
            });

            t.setDaemon(true);
            t.start();
        }
    }

    public void put(String key, CacheObject value){
        synchronized (cacheMap){
            cacheMap.put(key,value);
        }
    }

    public CacheObject get(String key){
        synchronized (cacheMap) {
            return cacheMap.get(key);
        }
    }

    public CacheService(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void remove(String key){
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    public int size(){
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    public void cleanup() {

        long now = System.currentTimeMillis();
        List<String> deleteKey;

        synchronized (cacheMap) {
//            Iterator<Map.Entry<String, String>> itr = cacheMap.entrySet().iterator();

            deleteKey = new ArrayList<>((cacheMap.size() / 2) + 1);
            CacheObject c;

            for (Map.Entry<String, CacheObject> m : cacheMap.entrySet()) {

                c =  cacheMap.get(m.getValue());
                if (c != null && (now > (timeToLive + c.getLastAccessed()))) {
                    deleteKey.add(m.getKey());
                }
            }
        }

        for (String key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }

            Thread.yield();
        }
    }
}

