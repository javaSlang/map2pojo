package com.javaslang.map2pojo.core.normalization;

import org.junit.Test;

import static com.javaslang.map2pojo.core.filling.impl.filling.Key2FieldTest.TEST;

public class NoNormalizationTest {

    @Test(expected = UnsupportedOperationException.class)
    public void applyTest() {
        new NoNormalization().apply(TEST);
    }

}