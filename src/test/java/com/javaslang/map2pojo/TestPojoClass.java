package com.javaslang.map2pojo;


import com.javaslang.map2pojo.annotations.Map2Pojo;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public final class TestPojoClass {

    public static final String TEST_FIELD = "testField";
    public static final String ANNOTATED_TEST_DATE_FIELD = "annotatedTestDate";
    public static final String NOT_ANNOTATED_TEST_DATE_FIELD = "notAnnotatedTestDate";

    private static final String DD_MM_YYYY = "dd-MM-yyyy";

    public static final Map<String, Field> TEST_CLASS_FIELDS = fieldToItsName();

    private Object testField;

    @Map2Pojo.FormattedDate(DD_MM_YYYY)
    private Date annotatedTestDate;

    private Date notAnnotatedTestDate;

    private static Map<String, Field> fieldToItsName() {
        return
                Arrays.stream(
                        TestPojoClass.class.getDeclaredFields()
                ).filter(field -> !Modifier.isStatic(field.getModifiers()) && Modifier.isPrivate(field.getModifiers()))
                        .collect(Collectors.toMap(Field::getName, Function.identity()));
    }
}