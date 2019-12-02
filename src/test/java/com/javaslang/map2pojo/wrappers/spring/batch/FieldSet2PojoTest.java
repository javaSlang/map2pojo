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
package com.javaslang.map2pojo.wrappers.spring.batch;

import com.javaslang.map2pojo.core.Transforming;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.batch.item.file.transform.FieldSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.javaslang.map2pojo.core.filling.impl.filling.Key2FieldTest.TEST;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FieldSet2PojoTest {

    public static final String KEY = "key";
    public static final String VALUE = "value";
    private static final Properties PROPERTIES = new Properties() {
        {
            put(KEY, VALUE);
        }
    };
    public static final HashMap<String, Object> EXPECTED_MAP = new HashMap<String, Object>() {
        {
            put("key", "value");
        }
    };
    private static final String[] VALUES = {TEST};

    @Mock
    private Transforming<Object> transforming;
    @Mock
    private FieldSet fieldSet;

    @InjectMocks
    private FieldSet2Pojo<Object> fieldSet2Pojo;

    @Test
    public void fieldSetHasNamesTest() {
        when(fieldSet.hasNames()).thenReturn(true);
        when(fieldSet.getProperties()).thenReturn(PROPERTIES);
        fieldSet2Pojo.mapFieldSet(fieldSet);
        verify(fieldSet, times(1)).hasNames();
        verify(fieldSet, times(1)).getProperties();
        verify(transforming, times(1)).transform(fieldSet2Pojo.properties2Map(PROPERTIES));
    }

    @Test
    public void fieldSetDoNotHaveNamesTest() {
        when(fieldSet.hasNames()).thenReturn(false);
        when(fieldSet.getValues()).thenReturn(VALUES);
        fieldSet2Pojo.mapFieldSet(fieldSet);
        verify(fieldSet, times(1)).hasNames();
        verify(fieldSet, times(1)).getValues();
        verify(transforming, times(1)).transform(fieldSet2Pojo.values2Map(VALUES));
    }

    @Test
    public void testProperties2Map() {
        Map<String, Object> actualMap = fieldSet2Pojo.properties2Map(PROPERTIES);
        assertEquals(
                EXPECTED_MAP,
                actualMap);
    }

    @Test
    public void testValues2Map() {
        Map<String, Object> actualMap = fieldSet2Pojo.values2Map(VALUES);
        assertEquals(
                new HashMap<String, Object>() {
                    {
                        put("0", TEST);
                    }
                },
                actualMap);
    }

}
