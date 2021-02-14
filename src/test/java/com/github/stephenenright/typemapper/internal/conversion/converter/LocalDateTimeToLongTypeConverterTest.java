package com.github.stephenenright.typemapper.internal.conversion.converter;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import com.github.stephenenright.typemapper.DefaultMapperConfiguration;
import com.github.stephenenright.typemapper.TypeMappingContext;
import org.junit.Test;

import com.github.stephenenright.typemapper.test.fixture.utils.FixtureUtils;

public class LocalDateTimeToLongTypeConverterTest {

    @Test
    public void convert() {
        LocalDateTime time = LocalDateTime.now();

        TypeMappingContext<LocalDateTime, Long> context = FixtureUtils.createMappingContext(time, Long.class,
                DefaultMapperConfiguration.create());

        Long result = LocalDateTimeToLongTypeConverter.INSTANCE.convert(context);
        assertNotNull(result);
    }

}
