package com.javaslang.map2pojo.core.filling.impl.baking;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static com.javaslang.map2pojo.TestPojoClass.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class BakingDateTest {

    private static final long TIME_01_01_2000 = 946681200000L;
    public static final Date EXPECTED = new java.sql.Date(TIME_01_01_2000);
    public static final String STRING_FOR_01_01_2000 = "01-01-2000";

    @Test
    public void applyBasicTest() {
        Date testValue = new BakingDate().apply(
                TEST_CLASS_FIELDS.get(ANNOTATED_TEST_DATE_FIELD), // passing annotated field
                STRING_FOR_01_01_2000
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void shouldBeTrimmedTest() {
        Date testValue = new BakingDate().apply(
                TEST_CLASS_FIELDS.get(ANNOTATED_TEST_DATE_FIELD),
                "           01-01-2000         "
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void wrongDateFormatTest() {
        assertNull(
                new BakingDate().apply(
                        TEST_CLASS_FIELDS.get(ANNOTATED_TEST_DATE_FIELD),
                        "01dd-01mm-2000yyyy"
                )
        );
    }

    @Test
    public void timestampValueTest() {
        Date testValue = new BakingDate().apply(
                TEST_CLASS_FIELDS.get(NOT_ANNOTATED_TEST_DATE_FIELD),
                new Timestamp(TIME_01_01_2000)
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test(expected = ClassCastException.class)
    public void nonDateValueTestForNotAnnotatedField() {
        new BakingDate().apply(
                TEST_CLASS_FIELDS.get(NOT_ANNOTATED_TEST_DATE_FIELD),
                new Object()
        );
    }

    @Test(expected = ClassCastException.class)
    public void nonDateValueTestForAnnotatedField() {
        new BakingDate().apply(
                TEST_CLASS_FIELDS.get(ANNOTATED_TEST_DATE_FIELD),
                new Object()
        );
    }

    @Test
    public void nullValueTest() {
        assertNull(
                new BakingDate().apply(
                        TEST_CLASS_FIELDS.get(NOT_ANNOTATED_TEST_DATE_FIELD),
                        null
                )
        );
    }

    @Test
    public void simpleDateValueTest() {
        Date testValue = new BakingDate().apply(
                TEST_CLASS_FIELDS.get(NOT_ANNOTATED_TEST_DATE_FIELD),
                EXPECTED
        );
        assertEquals(EXPECTED, testValue);
    }

}
