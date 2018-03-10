package com.ig.core.service;

import com.ig.core.model.CacheObject;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheServiceTest {

    @Test
    public void testMyObectCache() throws InterruptedException {
        CacheService cacheService = new CacheService(1000, 4);
        CacheObject cacheObject = new CacheObject("value");
        cacheService.put("1", cacheObject);
        CacheObject object =  cacheService.get("1");

        assertThat(object.getValue(), is("value"));
        Thread.sleep(1000);
        cacheService.cleanup();

        assertThat(object.getValue(), is("value"));
    }


}