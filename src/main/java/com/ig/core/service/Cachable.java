package com.ig.core.service;

public interface Cachable {

    public boolean isExpired();
    /* This method will ensure that the caching service is not responsible for
    uniquely identifying objects placed in the cache.
    */
    public Object getIdentifier();

}
