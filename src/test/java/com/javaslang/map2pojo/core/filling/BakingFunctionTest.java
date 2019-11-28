package com.javaslang.map2pojo.core.filling;

import org.junit.Test;

import static com.javaslang.map2pojo.TestPojoClass.TEST_CLASS_FIELDS;
import static com.javaslang.map2pojo.TestPojoClass.TEST_FIELD;
import static org.junit.Assert.assertNull;

public class BakingFunctionTest {

    @Test
    public void testNullValue() {
        BakingFunction<Object> bakingFunction = (field, o) -> new Object();
        assertNull(bakingFunction.bake(TEST_CLASS_FIELDS.get(TEST_FIELD), null));
    }

}
