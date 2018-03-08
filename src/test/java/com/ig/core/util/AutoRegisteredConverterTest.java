package com.ig.core.util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.support.GenericConversionService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AutoRegisteredConverterTest {

    @Before
    public void onSetup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void converterShouldBeAddedToConversionService() throws Exception {
        AutoRegisteredConverter<Object, Object> converter = mock(AutoRegisteredConverter.class);
        GenericConversionService conversionService = mock(GenericConversionService.class);
        when(converter.getConversionService()).thenReturn(conversionService);
        Mockito.doCallRealMethod().when(converter).postConstruct();
        converter.postConstruct();
        Mockito.verify(conversionService).addConverter(converter);
    }

    @Test(expected = IllegalStateException.class)
    public void whenNoGenericConversionServiceFailFast() {
        AutoRegisteredConverter<Object, Object> converter = mock(AutoRegisteredConverter.class);
        Mockito.doCallRealMethod().when(converter).postConstruct();
        converter.postConstruct();
    }
}