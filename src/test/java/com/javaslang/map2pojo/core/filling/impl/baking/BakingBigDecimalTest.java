package com.javaslang.map2pojo.core.filling.impl.baking;

import org.junit.Test;

import java.math.BigDecimal;

import static com.javaslang.map2pojo.core.filling.impl.baking.TestClass.TEST_CLASS_FIELDS;
import static com.javaslang.map2pojo.core.filling.impl.baking.TestClass.TEST_FIELD;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNull;

public class BakingBigDecimalTest {

    private static final BigDecimal EXPECTED = new BigDecimal(100);

    @Test
    public void applyBasicTest() {
        BigDecimal testValue = new BakingBigDecimal().apply(
                TEST_CLASS_FIELDS.get(TEST_FIELD),
                new BigDecimal(100.000000)
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void applyStringValueTest() {
        BigDecimal testValue = new BakingBigDecimal().apply(
                TEST_CLASS_FIELDS.get("testField"),
                "100"
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void applyStringValueToBeTrimmedTest() {
        BigDecimal testValue = new BakingBigDecimal().apply(
                TEST_CLASS_FIELDS.get("testField"),
                "   100   "
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test(expected = NumberFormatException.class)
    public void nonStringValueTest() {
        new BakingBigDecimal().apply(
                TEST_CLASS_FIELDS.get("testField"),
                new Object()
        );
    }

    @Test
    public void nullValueTest() {
        assertNull(
                new BakingBigDecimal().apply(
                        TEST_CLASS_FIELDS.get("testField"),
                        null
                )
        );
    }

}
