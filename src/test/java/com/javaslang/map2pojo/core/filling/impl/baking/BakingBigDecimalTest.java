/*-
 * ========================LICENSE_START=================================
 * map2pojo
 * %%
 * Copyright (C) 2019 Serhii Lanh
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =========================LICENSE_END==================================
 */
package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.TestPojoClass;
import com.javaslang.map2pojo.core.filling.impl.baking.exceptions.WrongTypeMappingException;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

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
        assertThat(EXPECTED, comparesEqualTo(testValue));
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
        assertThat(EXPECTED, comparesEqualTo(testValue));
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
        BigDecimal testValue = new BakingBigDecimal().apply(
                TEST_CLASS_FIELDS.get(TestPojoClass.FIELD_TO_SKIP),
                "100,000000"
        );
        assertThat(EXPECTED, comparesEqualTo(testValue));
    }

}