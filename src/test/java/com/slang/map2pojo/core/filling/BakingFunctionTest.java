package com.slang.map2pojo.core.filling;

import com.slang.map2pojo.TestPojoClass;
import org.junit.Test;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertNull;

public class BakingFunctionTest {

    @Test
    public void testNullValue() {
        BakingFunction<Object> bakingFunction = (field, o) -> new Object();
        assertNull(bakingFunction.bake(TestPojoClass.TEST_CLASS_FIELDS.get(TestPojoClass.TEST_FIELD), null));
    }

    @Test
    public void testEmptyString() {
        BakingFunction<Object> bakingFunction = (field, o) -> new Object();
        assertNull(bakingFunction.bake(TestPojoClass.TEST_CLASS_FIELDS.get(TestPojoClass.TEST_FIELD), EMPTY));
    }

}
