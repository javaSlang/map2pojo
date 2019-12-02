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
package com.javaslang.map2pojo;


import com.javaslang.map2pojo.annotations.Map2Pojo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public final class TestPojoClass {

    public static final String TEST_FIELD = "testField";
    public static final String ANNOTATED_TEST_DATE_FIELD = "annotatedTestDate";
    public static final String DATE_AS_STRING = "dateAsString";
    public static final String NOT_ANNOTATED_TEST_DATE_FIELD = "notAnnotatedTestDate";
    public static final String FIELD_TO_SKIP = "fieldToSkip";

    public static final String DD_MM_YYYY = "dd-MM-yyyy";

    public static final Map<String, Field> TEST_CLASS_FIELDS = fieldToItsName();

    private String testField;
    @Map2Pojo.FormattedDate(DD_MM_YYYY)
    private Date annotatedTestDate;
    @Map2Pojo.FormattedDate(DD_MM_YYYY)
    private String dateAsString;
    private Date notAnnotatedTestDate;
    @Map2Pojo.Locale("de")
    private BigDecimal fieldToSkip;

    public TestPojoClass(String testField, Date annotatedTestDate, Date notAnnotatedTestDate) {
        this.testField = testField;
        this.annotatedTestDate = annotatedTestDate;
        this.notAnnotatedTestDate = notAnnotatedTestDate;
    }

    private static Map<String, Field> fieldToItsName() {
        return
                Arrays.stream(TestPojoClass.class.getDeclaredFields())
                        .filter(field -> !Modifier.isStatic(field.getModifiers()) && Modifier.isPrivate(field.getModifiers()))
                        .collect(Collectors.toMap(Field::getName, Function.identity()));
    }

    @Map2Pojo.OrderedFields
    @Map2Pojo.Locale("de")
    @Data
    @NoArgsConstructor
    public static class OrderedTestPojo {

        private String firstField;
        @Map2Pojo.FormattedDate(DD_MM_YYYY)
        private Date secondField;
        private BigDecimal thirdField;

        public OrderedTestPojo(String firstField, Date secondField, BigDecimal thirdField) {
            this.firstField = firstField;
            this.secondField = secondField;
            this.thirdField = thirdField;
        }
    }

    public static class NoDefaultCtorClass {

        private final String value;

        public NoDefaultCtorClass(String value) {
            this.value = value;
        }

    }
}