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
    public static final String NOT_ANNOTATED_TEST_DATE_FIELD = "notAnnotatedTestDate";

    public static final String DD_MM_YYYY = "dd-MM-yyyy";

    public static final Map<String, Field> TEST_CLASS_FIELDS = fieldToItsName();

    private String testField;
    @Map2Pojo.FormattedDate(DD_MM_YYYY)
    private Date annotatedTestDate;
    private Date notAnnotatedTestDate;
    private BigDecimal fieldToSkip;

    public TestPojoClass(String testField, Date annotatedTestDate, Date notAnnotatedTestDate) {
        this.testField = testField;
        this.annotatedTestDate = annotatedTestDate;
        this.notAnnotatedTestDate = notAnnotatedTestDate;
    }

    private static Map<String, Field> fieldToItsName() {
        return
                Arrays.stream(
                        TestPojoClass.class.getDeclaredFields()
                ).filter(field -> !Modifier.isStatic(field.getModifiers()) && Modifier.isPrivate(field.getModifiers()))
                        .collect(Collectors.toMap(Field::getName, Function.identity()));
    }

    @Map2Pojo.OrderedFields
    @NoArgsConstructor
    @EqualsAndHashCode
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