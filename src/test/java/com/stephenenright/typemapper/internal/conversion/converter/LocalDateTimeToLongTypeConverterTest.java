package com.stephenenright.typemapper.internal.conversion.converter;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Test;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.test.fixture.utils.FixtureUtils;

public class LocalDateTimeToLongTypeConverterTest {

    @Test
    public void convert() {
        LocalDateTime time = LocalDateTime.now();
 
        TypeMappingContext<LocalDateTime, Long> context = FixtureUtils.createMappingContext(time,Long.class);
        
        Long result = LocalDateTimeToLongTypeConverter.INSTANCE.convert(context);
        assertNotNull(result);
    }
    
    
}
