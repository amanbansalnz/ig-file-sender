package com.ig.core.model;

public class CacheObject {

    private long lastAccessed = 500;
    private final String value;

    public CacheObject(String value) {
        this.value = value;
    }

    public long getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public String getValue() {
        return value;
    }
}
