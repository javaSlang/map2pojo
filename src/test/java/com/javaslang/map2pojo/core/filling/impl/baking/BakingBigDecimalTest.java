package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.TestPojoClass;
import com.javaslang.map2pojo.core.filling.impl.baking.exceptions.WrongTypeMappingException;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

import static com.javaslang.map2pojo.TestPojoClass.TEST_CLASS_FIELDS;
import static com.javaslang.map2pojo.TestPojoClass.TEST_FIELD;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

public class BakingBigDecimalTest {

    private static final BigDecimal EXPECTED = new BigDecimal(100);
    private static final String STRING_100 = "100";
    public static final String NAN = "NaN";

    @Test
    public void applyBasicTest() {
        BigDecimal testValue = new BakingBigDecimal().apply(
                TEST_CLASS_FIELDS.get(TestPojoClass.TEST_FIELD),
                new BigDecimal(100.000000)
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void applyIntegerValueTest() {
        BigDecimal testValue = new BakingBigDecimal().apply(
                TEST_CLASS_FIELDS.get(TestPojoClass.TEST_FIELD),
                new BigInteger(STRING_100)
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void applyStringValueTest() {
        BigDecimal testValue = new BakingBigDecimal().apply(
                TEST_CLASS_FIELDS.get(TEST_FIELD),
                STRING_100
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test(expected = NumberFormatException.class)
    public void applyNotNumberStringValueTest() {
        new BakingBigDecimal().apply(
                TEST_CLASS_FIELDS.get(TEST_FIELD),
                NAN
        );
    }

    @Test
    public void applyStringValueToBeTrimmedTest() {
        BigDecimal testValue = new BakingBigDecimal().apply(
                TEST_CLASS_FIELDS.get(TEST_FIELD),
                "   100   "
        );
        assertEquals(EXPECTED, testValue);
    }

    @Test(expected = WrongTypeMappingException.class)
    public void nonStringValueTest() {
        new BakingBigDecimal().apply(
                TEST_CLASS_FIELDS.get(TEST_FIELD),
                new Object()
        );
    }

    @Test
    public void localeCaseTest() {
        BigDecimal testValue = new BakingBigDecimal(Locale.GERMAN).apply(
                TEST_CLASS_FIELDS.get(TestPojoClass.TEST_FIELD),
                "100,000000"
        );
        assertThat(EXPECTED, comparesEqualTo(testValue));
    }

}