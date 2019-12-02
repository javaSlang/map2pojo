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
package com.javaslang.map2pojo.core.normalization;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class DefaultNormalizationTest {

    private static final String EXPECTED = "currencyname";

    @Test
    public void applyTestWithSpace() {
        String testValue = new DefaultNormalization().apply("Currency Name");
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void applyTestWithSpaces() {
        String testValue = new DefaultNormalization().apply(" Currency   Name   ");
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void applyTestWithUnderscore() {
        String testValue = new DefaultNormalization().apply("Currency_Name");
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void applyTestWithUnderscores() {
        String testValue = new DefaultNormalization().apply("___Currency__Name_");
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void applyTestWithCamelCase() {
        String testValue = new DefaultNormalization().apply("currencyName");
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void applyTestWithCamelCaseCapitalized() {
        String testValue = new DefaultNormalization().apply("CurrencyName");
        assertEquals(EXPECTED, testValue);
    }

    @Test
    public void applyTestNull() {
        assertNull(
                new DefaultNormalization()
                        .apply(null)
        );
    }

}