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
package com.javaslang.map2pojo.core.filling.impl.filling;

import com.javaslang.map2pojo.TestPojoClass;
import com.javaslang.map2pojo.core.filling.impl.baking.BakingDate;
import com.javaslang.map2pojo.core.filling.impl.baking.NoBaking;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

import static com.javaslang.map2pojo.TestPojoClass.*;
import static com.javaslang.map2pojo.core.filling.impl.baking.BakingDateTest.EXPECTED;
import static com.javaslang.map2pojo.core.filling.impl.baking.BakingDateTest.STRING_FOR_01_01_2000;
import static com.javaslang.map2pojo.core.filling.impl.filling.Key2FieldTest.TEST;
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