package com.slang.map2pojo.core.filling.impl.filling;

import org.junit.Test;

import static com.slang.map2pojo.TestPojoClass.TEST_CLASS_FIELDS;
import static com.slang.map2pojo.TestPojoClass.TEST_FIELD;
import static junit.framework.TestCase.assertEquals;

public class Key2FieldTest {

    public static final String TEST = "test";

    @Test
    public void createAndDefaultAccessKey2FieldTest() {
        Key2Field key2Field = new Key2Field(TEST, TEST_CLASS_FIELDS.get(TEST_FIELD));
        assertEquals(TEST, key2Field.key);
        assertEquals(TEST_CLASS_FIELDS.get(TEST_FIELD), key2Field.field);
    }

}