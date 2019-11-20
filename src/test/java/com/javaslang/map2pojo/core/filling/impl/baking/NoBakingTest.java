package com.javaslang.map2pojo.core.filling.impl.baking;

import org.junit.Test;

import static com.javaslang.map2pojo.core.filling.impl.baking.TestClass.TEST_CLASS_FIELDS;
import static com.javaslang.map2pojo.core.filling.impl.baking.TestClass.TEST_FIELD;

public class NoBakingTest {

    @Test(expected = UnsupportedOperationException.class)
    public void applyTest() {
        new NoBaking<>()
                .apply(
                        TEST_CLASS_FIELDS.get(TEST_FIELD),
                        new Object()
                );
    }

}
