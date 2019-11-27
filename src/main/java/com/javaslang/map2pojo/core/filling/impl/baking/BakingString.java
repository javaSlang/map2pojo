package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.annotations.Map2Pojo;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BiFunction;

import static org.apache.commons.lang3.StringUtils.trim;

@EqualsAndHashCode
public class BakingString implements BiFunction<Field, Object, String> {

    @Override
    public String apply(Field field, Object rawValue) {
        if (field.isAnnotationPresent(Map2Pojo.FormattedDate.class)) {
            Map2Pojo.FormattedDate dateFormat = field.getAnnotation(Map2Pojo.FormattedDate.class);
            return formattedDate((Date) rawValue, dateFormat.value());
        } else {
            return trim((String) rawValue);
        }
    }

    private String formattedDate(Date rawValue, String format) {
        return rawValue != null ? new SimpleDateFormat(format).format(rawValue) : null;
    }

}
