package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.annotations.Map2Pojo;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.BiFunction;

@Slf4j
@EqualsAndHashCode
public class BakingDate implements BiFunction<Field, Object, Date> {

    @Override
    public Date apply(Field field, Object rawValue) {
        if (field.isAnnotationPresent(Map2Pojo.FormattedDate.class)) {
            Map2Pojo.FormattedDate dateFormat = field.getAnnotation(Map2Pojo.FormattedDate.class);
            return formattedDate((String) rawValue, dateFormat.value());
        } else if (rawValue instanceof Timestamp) {
            return (Timestamp) rawValue;
        } else return (Date) rawValue;
    }

    private Date formattedDate(String rawValue, String format) {
        Date formattedDate = null;
        if (rawValue != null) {
            try {
                formattedDate = new SimpleDateFormat(format).parse(rawValue);
            } catch (ParseException e) {
                log.warn("Wrong date format '{}', expected '{}'", rawValue, format);
            }
        }
        return formattedDate;
    }

}
