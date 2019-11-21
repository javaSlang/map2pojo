package com.javaslang.map2pojo.core.normalization;

import org.junit.Test;

public class NoNormalizationTest {

    @Test(expected = UnsupportedOperationException.class)
    public void applyTest() {
        new NoNormalization().apply("test");
    }

}