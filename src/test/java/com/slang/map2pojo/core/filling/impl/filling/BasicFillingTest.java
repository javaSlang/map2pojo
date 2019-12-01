package com.slang.map2pojo.core.filling.impl.filling;

import com.slang.map2pojo.TestPojoClass;
import com.slang.map2pojo.core.filling.impl.baking.BakingDate;
import com.slang.map2pojo.core.filling.impl.baking.NoBaking;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

import static com.slang.map2pojo.TestPojoClass.*;
import static com.slang.map2pojo.core.filling.impl.baking.BakingDateTest.EXPECTED;
import static com.slang.map2pojo.core.filling.impl.baking.BakingDateTest.STRING_FOR_01_01_2000;
import static com.slang.map2pojo.core.filling.impl.filling.Key2FieldTest.TEST;
import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class BasicFillingTest {

    @Test()
    public void noBakingWarnTest() {
        NoBaking<Object> spiedBakingFunction = spy(new NoBaking<>());
        BasicFilling<Object> spiedBasicFilling = spy(new BasicFilling<>(spiedBakingFunction));
        Key2Field key2Field = new Key2Field(TEST, TEST_CLASS_FIELDS.get(TEST_FIELD));
        spiedBasicFilling.inject(new Object(), key2Field, new HashMap<>());
        verify(spiedBasicFilling, times(1)).isNoBaking(key2Field);
        assertTrue(spiedBasicFilling.isNoBaking(key2Field));
        verify(spiedBakingFunction, never()).apply(any(), any());
    }

    @Test()
    public void basicBakingTest() throws IllegalAccessException, InstantiationException {
        BakingDate spiedBakingFunction = spy(new BakingDate());
        BasicFilling<Date> spiedBasicFilling = spy(new BasicFilling<>(spiedBakingFunction));
        Key2Field key2Field = new Key2Field(ANNOTATED_TEST_DATE_FIELD, TEST_CLASS_FIELDS.get(ANNOTATED_TEST_DATE_FIELD));
        TestPojoClass testPojoClassInstance = TestPojoClass.class.newInstance();
        HashMap<String, Object> normalizedFieldSet = new HashMap<String, Object>() {{
            put(ANNOTATED_TEST_DATE_FIELD, STRING_FOR_01_01_2000);
        }};
        spiedBasicFilling.inject(testPojoClassInstance, key2Field, normalizedFieldSet);
        assertFalse(spiedBasicFilling.isNoBaking(key2Field));
        assertEquals(EXPECTED, testPojoClassInstance.getAnnotatedTestDate());
        verify(spiedBakingFunction, times(1)).apply(key2Field.field, normalizedFieldSet.get(ANNOTATED_TEST_DATE_FIELD));
    }

}