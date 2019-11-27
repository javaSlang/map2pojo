package com.javaslang.map2pojo.core;

import com.javaslang.map2pojo.TestPojoClass;
import com.javaslang.map2pojo.TestPojoClass.NoDefaultCtorClass;
import com.javaslang.map2pojo.TestPojoClass.OrderedTestPojo;
import com.javaslang.map2pojo.core.filling.impl.fillings.DefaultFillings;
import com.javaslang.map2pojo.core.normalization.DefaultNormalization;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.javaslang.map2pojo.core.filling.impl.baking.BakingDateTest.*;
import static com.javaslang.map2pojo.core.filling.impl.filling.Key2FieldTest.TEST;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

public class Map2PojoTest {

    @Test
    public void generalCaseTest() {
        Map2Pojo<TestPojoClass> testMap2Pojo = new Map2Pojo<>(TestPojoClass.class, new DefaultNormalization(), new DefaultFillings());
        TestPojoClass transformedPojoClass = testMap2Pojo.transform(new HashMap<String, Object>() {{
            put("TEST_FIELD", TEST);
            put("Annotated Test Date", STRING_FOR_01_01_2000);
            put("NOT ANNOTATED TEST DATE", new Date(TIME_01_01_2000));
        }});
        TestPojoClass testPojoClass = new TestPojoClass(TEST, EXPECTED, new Date(TIME_01_01_2000));
        assertEquals(testPojoClass, transformedPojoClass);
    }

    @Test
    public void orderedFieldsCaseTest() {
        Map2Pojo<OrderedTestPojo> testMap2Pojo = new Map2Pojo<>(OrderedTestPojo.class);
        OrderedTestPojo transformedPojoClass = testMap2Pojo.transform(new HashMap<String, Object>() {{
            put("0", TEST);
            put("1", STRING_FOR_01_01_2000);
            put("2", 100);
        }});
        OrderedTestPojo testPojoClass = new OrderedTestPojo(TEST, EXPECTED, new BigDecimal(100));
        assertEquals(testPojoClass, transformedPojoClass);
    }

    @Test(expected = InstantiationException.class)
    public void noDefaultCtorCaseTest() {
        new Map2Pojo<>(NoDefaultCtorClass.class).transform(new HashMap<>());
    }

    @Test
    // https://github.com/javaSlang/map2pojo/issues/33
    public void originalMapContainsNullValueCaseTest() {
        Map2Pojo<TestPojoClass> testMap2Pojo = new Map2Pojo<>(TestPojoClass.class, new DefaultNormalization(), new DefaultFillings());
        TestPojoClass transformedPojoClass = testMap2Pojo.transform(new HashMap<String, Object>() {{
            put("TEST_FIELD", TEST);
            put("Annotated Test Date", STRING_FOR_01_01_2000);
            put("NOT ANNOTATED TEST DATE", null);
        }});
        TestPojoClass testPojoClass = new TestPojoClass(TEST, EXPECTED, null);
        assertEquals(testPojoClass, transformedPojoClass);
    }

    @Test
    public void localeCaseTest() {
        Map2Pojo<OrderedTestPojo> testMap2Pojo = new Map2Pojo<>(OrderedTestPojo.class, Locale.GERMAN);
        OrderedTestPojo transformedPojoClass = testMap2Pojo.transform(new HashMap<String, Object>() {{
            put("0", TEST);
            put("1", STRING_FOR_01_01_2000);
            put("2", "100,000000");
        }});
        OrderedTestPojo testPojoClass = new OrderedTestPojo(TEST, new Date(TIME_01_01_2000), new BigDecimal(100));
        assertThat(testPojoClass.getThirdField(), comparesEqualTo(transformedPojoClass.getThirdField()));
    }

}
