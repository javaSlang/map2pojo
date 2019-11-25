package com.javaslang.map2pojo.core.filling.impl.baking;

import org.junit.Test;

import java.util.Date;

import static com.javaslang.map2pojo.TestPojoClass.*;
import static com.javaslang.map2pojo.core.filling.impl.baking.BakingDateTest.STRING_FOR_01_01_2000;
import static com.javaslang.map2pojo.core.filling.impl.baking.BakingDateTest.TIME_01_01_2000;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class BakingStringTest {

    private static final String EXPECTED = "testValue";

    @Test
    public void applyBasicTest() {
        String testValue = new BakingString().apply(
                TEST_CLASS_FIELDS.get(TEST_FIELD),
                "testValue"
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void shouldBeTrimmedTest() {
        String testValue = new BakingString().apply(
                TEST_CLASS_FIELDS.get(TEST_FIELD),
                "           testValue         "
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test(expected = ClassCastException.class)
    public void nonStringValueTest() {
        new BakingString().apply(
                TEST_CLASS_FIELDS.get(TEST_FIELD),
                new Object()
        );
    }

    @Test
    public void nullValueTest() {
        assertNull(
                new BakingString().apply(
                        TEST_CLASS_FIELDS.get(TEST_FIELD),
                        null
                )
        );
    }

    @Test
    public void dateAsStringTest() {
        String testValue = new BakingString().apply(
                TEST_CLASS_FIELDS.get(DATE_AS_STRING),
                new Date(TIME_01_01_2000)
        );
        assertEquals(STRING_FOR_01_01_2000, testValue);
    }

}
