package com.ig.core.util;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

public abstract class AutoRegisteredConverter<S, T> implements Converter<S, T> {

    @Resource
    private ConversionService joConversionService;

    public AutoRegisteredConverter() {
    }

    public ConversionService getConversionService() {
        return this.joConversionService;
    }

    @PostConstruct
    public void postConstruct() {
        if (this.getConversionService() instanceof GenericConversionService) {
            ((GenericConversionService) this.getConversionService()).addConverter(this);
        } else {
            throw new IllegalStateException("conversionService is not a GenericConversionService!");
        }
    }
}
