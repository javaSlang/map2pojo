package com.slang.map2pojo.core;

import com.slang.map2pojo.TestPojoClass;
import com.slang.map2pojo.core.filling.impl.baking.BakingDateTest;
import com.slang.map2pojo.core.filling.impl.filling.Key2FieldTest;
import com.slang.map2pojo.core.filling.impl.fillings.DefaultFillings;
import com.slang.map2pojo.core.normalization.DefaultNormalization;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.slang.map2pojo.core.filling.impl.baking.BakingBigDecimal.StringToBigDecimalConversion.NO_LOCALE;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

public class Map2PojoTest {

    @Test
    public void generalCaseTest() {
        Map2Pojo<TestPojoClass> testMap2Pojo = new Map2Pojo<>(TestPojoClass.class, new DefaultNormalization(), new DefaultFillings());
        TestPojoClass transformedPojoClass = testMap2Pojo.transform(new HashMap<String, Object>() {{
            put("TEST_FIELD", Key2FieldTest.TEST);
            put("Annotated Test Date", BakingDateTest.STRING_FOR_01_01_2000);
            put("NOT ANNOTATED TEST DATE", new Date(BakingDateTest.TIME_01_01_2000));
        }});
        TestPojoClass testPojoClass = new TestPojoClass(Key2FieldTest.TEST, BakingDateTest.EXPECTED, new Date(BakingDateTest.TIME_01_01_2000));
        assertEquals(testPojoClass, transformedPojoClass);
    }

    @Test
    public void defaultsWithLocaleTest() {
        Map2Pojo<TestPojoClass> testMap2Pojo = new Map2Pojo<>(TestPojoClass.class, NO_LOCALE);
        TestPojoClass transformedPojoClass = testMap2Pojo.transform(new HashMap<String, Object>() {{
            put("TEST_FIELD", Key2FieldTest.TEST);
            put("Annotated Test Date", BakingDateTest.STRING_FOR_01_01_2000);
            put("NOT ANNOTATED TEST DATE", new Date(BakingDateTest.TIME_01_01_2000));
        }});
        TestPojoClass testPojoClass = new TestPojoClass(Key2FieldTest.TEST, BakingDateTest.EXPECTED, new Date(BakingDateTest.TIME_01_01_2000));
        assertEquals(testPojoClass, transformedPojoClass);
    }

    @Test
    public void orderedFieldsCaseTest() {
        Map2Pojo<TestPojoClass.OrderedTestPojo> testMap2Pojo = new Map2Pojo<>(TestPojoClass.OrderedTestPojo.class);
        TestPojoClass.OrderedTestPojo transformedPojoClass = testMap2Pojo.transform(new HashMap<String, Object>() {{
            put("0", Key2FieldTest.TEST);
            put("1", BakingDateTest.STRING_FOR_01_01_2000);
            put("2", 100);
        }});
        TestPojoClass.OrderedTestPojo testPojoClass = new TestPojoClass.OrderedTestPojo(Key2FieldTest.TEST, BakingDateTest.EXPECTED, new BigDecimal(100));
        assertEquals(testPojoClass, transformedPojoClass);
    }

    @Test(expected = InstantiationException.class)
    public void noDefaultCtorCaseTest() {
        new Map2Pojo<>(TestPojoClass.NoDefaultCtorClass.class).transform(new HashMap<>());
    }

    @Test
    // https://github.com/javaSlang/map2pojo/issues/33
    public void originalMapContainsNullValueCaseTest() {
        Map2Pojo<TestPojoClass> testMap2Pojo = new Map2Pojo<>(TestPojoClass.class, new DefaultNormalization(), new DefaultFillings());
        TestPojoClass transformedPojoClass = testMap2Pojo.transform(new HashMap<String, Object>() {{
            put("TEST_FIELD", Key2FieldTest.TEST);
            put("Annotated Test Date", BakingDateTest.STRING_FOR_01_01_2000);
            put("NOT ANNOTATED TEST DATE", null);
        }});
        TestPojoClass testPojoClass = new TestPojoClass(Key2FieldTest.TEST, BakingDateTest.EXPECTED, null);
        assertEquals(testPojoClass, transformedPojoClass);
    }

    @Test
    public void localeCaseTest() {
        Map2Pojo<TestPojoClass.OrderedTestPojo> testMap2Pojo = new Map2Pojo<>(TestPojoClass.OrderedTestPojo.class, Locale.GERMAN);
        TestPojoClass.OrderedTestPojo transformedPojoClass = testMap2Pojo.transform(new HashMap<String, Object>() {{
            put("0", Key2FieldTest.TEST);
            put("1", BakingDateTest.STRING_FOR_01_01_2000);
            put("2", "100,000000");
        }});
        TestPojoClass.OrderedTestPojo testPojoClass = new TestPojoClass.OrderedTestPojo(Key2FieldTest.TEST, new Date(BakingDateTest.TIME_01_01_2000), new BigDecimal(100));
        assertThat(testPojoClass.getThirdField(), comparesEqualTo(transformedPojoClass.getThirdField()));
    }

}
