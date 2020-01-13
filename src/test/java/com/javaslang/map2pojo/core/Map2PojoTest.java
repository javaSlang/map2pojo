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

import static com.javaslang.map2pojo.core.filling.impl.baking.time.BakingDateTest.*;
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
    public void defaultsWithLocaleTest() {
        Map2Pojo<TestPojoClass> testMap2Pojo = new Map2Pojo<>(TestPojoClass.class);
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
        Map2Pojo<OrderedTestPojo> testMap2Pojo = new Map2Pojo<>(OrderedTestPojo.class);
        OrderedTestPojo transformedPojoClass = testMap2Pojo.transform(new HashMap<String, Object>() {{
            put("0", TEST);
            put("1", STRING_FOR_01_01_2000);
            put("2", "100,000000");
        }});
        OrderedTestPojo testPojoClass = new OrderedTestPojo(TEST, new Date(TIME_01_01_2000), new BigDecimal(100));
        assertThat(testPojoClass.getThirdField(), comparesEqualTo(transformedPojoClass.getThirdField()));
    }

    @Test
    public void protectedFieldCaseTest() {
        Map2Pojo<TestPojoClass> testMap2Pojo = new Map2Pojo<>(TestPojoClass.class, new DefaultNormalization(), new DefaultFillings());
        TestPojoClass transformedPojoClass = testMap2Pojo.transform(new HashMap<String, Object>() {{
            put("TEST_FIELD", TEST);
            put("Annotated Test Date", STRING_FOR_01_01_2000);
            put("NOT ANNOTATED TEST DATE", new Date(TIME_01_01_2000));
            put("PROTECTED___field", TEST);
        }});
        TestPojoClass testPojoClass = new TestPojoClass(TEST, EXPECTED, new Date(TIME_01_01_2000), TEST);
        assertEquals(testPojoClass, transformedPojoClass);
    }

}
