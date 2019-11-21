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

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FieldSet2PojoTest {

    private static final Properties PROPERTIES = new Properties() {
        {
            put("key", "value");
        }
    };
    private static final String[] VALUES = {"test"};

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
                new HashMap<String, Object>() {
                    {
                        put("key", "value");
                    }
                },
                actualMap);
    }

    @Test
    public void testValues2Map() {
        Map<String, Object> actualMap = fieldSet2Pojo.values2Map(VALUES);
        assertEquals(
                new HashMap<String, Object>() {
                    {
                        put("0", "test");
                    }
                },
                actualMap);
    }

}
