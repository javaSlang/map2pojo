package com.javaslang.map2pojo.core.filling.impl.filling;

import org.junit.Test;

import static com.javaslang.map2pojo.TestPojoClass.TEST_CLASS_FIELDS;
import static com.javaslang.map2pojo.TestPojoClass.TEST_FIELD;
import static junit.framework.TestCase.assertEquals;

public class Key2FieldTest {

    @Test
    public void createAndDefaultAccessKey2FieldTest() {
        Key2Field key2Field = new Key2Field("test", TEST_CLASS_FIELDS.get(TEST_FIELD));
        assertEquals("test", key2Field.key);
        assertEquals(TEST_CLASS_FIELDS.get(TEST_FIELD), key2Field.field);
    }

}