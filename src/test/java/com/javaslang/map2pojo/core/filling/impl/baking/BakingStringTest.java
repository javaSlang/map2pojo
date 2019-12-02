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

import com.javaslang.map2pojo.core.filling.impl.baking.exceptions.WrongTypeMappingException;
import org.junit.Test;

import java.util.Date;

import static com.javaslang.map2pojo.TestPojoClass.*;
import static com.javaslang.map2pojo.core.filling.impl.baking.BakingDateTest.STRING_FOR_01_01_2000;
import static com.javaslang.map2pojo.core.filling.impl.baking.BakingDateTest.TIME_01_01_2000;
import static junit.framework.TestCase.assertEquals;

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

    @Test(expected = WrongTypeMappingException.class)
    public void nonStringValueTest() {
        new BakingString().apply(
                TEST_CLASS_FIELDS.get(TEST_FIELD),
                new Object()
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
