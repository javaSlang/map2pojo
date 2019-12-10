package com.javaslang.map2pojo.core.filling.impl.baking.time;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.javaslang.map2pojo.TestPojoClass.*;
import static org.junit.Assert.assertEquals;

public class StringToTemporalConversionTest {

    @Test
    public void localDateConversionTest() {
        assertEquals(
                LocalDate.of(1992, 8, 19),
                new BakingLocalDate.StringToTemporalConversion<>(LocalDate::parse).apply(TEST_CLASS_FIELDS.get(TEST_LOCAL_DATE), "19-08-1992")
        );
    }

    @Test
    public void localDateTimeConversionTest() {
        assertEquals(
                LocalDateTime.of(1992, 8, 19, 3, 40, 0),
                new BakingLocalDate.StringToTemporalConversion<>(LocalDateTime::parse).apply(TEST_CLASS_FIELDS.get(TEST_LOCAL_DATE_TIME), "1992-08-19-03.40.00")
        );
    }

}
