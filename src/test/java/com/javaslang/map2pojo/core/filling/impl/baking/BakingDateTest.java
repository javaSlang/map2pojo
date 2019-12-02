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
import lombok.SneakyThrows;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.javaslang.map2pojo.TestPojoClass.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class BakingDateTest {

    public static final long TIME_01_01_2000 = 946684800000L;
    public static final Date EXPECTED = expectedDate();
    public static final String STRING_FOR_01_01_2000 = "01-01-2000";

    @Test
    public void applyBasicTest() {
        Date testValue = new BakingDate().apply(
                TEST_CLASS_FIELDS.get(ANNOTATED_TEST_DATE_FIELD), // passing annotated field
                STRING_FOR_01_01_2000
        );
        assertEquals(EXPECTED.getTime(), testValue.getTime());
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
        assertEquals(new Timestamp(TIME_01_01_2000), testValue);
    }

    @Test(expected = WrongTypeMappingException.class)
    public void nonDateValueTestForNotAnnotatedField() {
        new BakingDate().apply(
                TEST_CLASS_FIELDS.get(NOT_ANNOTATED_TEST_DATE_FIELD),
                new Object()
        );
    }

    @Test(expected = WrongTypeMappingException.class)
    public void nonDateValueTestForAnnotatedField() {
        new BakingDate().apply(
                TEST_CLASS_FIELDS.get(ANNOTATED_TEST_DATE_FIELD),
                new Object()
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

    @SneakyThrows
    private static Date expectedDate() {
        return new SimpleDateFormat(DD_MM_YYYY).parse(STRING_FOR_01_01_2000);
    }

}
