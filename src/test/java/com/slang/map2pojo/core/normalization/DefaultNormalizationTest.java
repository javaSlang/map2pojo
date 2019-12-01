package com.slang.map2pojo.core.normalization;

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