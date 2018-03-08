package com.ig.core.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDateTime> {

    public LocalDateTime unmarshal(String localDateTime) throws Exception {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(localDateTime)), ZoneId.systemDefault());
    }

    public String marshal(LocalDateTime localDateTime) throws Exception {
        return localDateTime.toString();
    }
}