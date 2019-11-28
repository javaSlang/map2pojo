package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.annotations.Map2Pojo;
import com.javaslang.map2pojo.core.filling.BakingFunction;
import com.javaslang.map2pojo.core.filling.impl.baking.exceptions.WrongTypeMappingException;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@EqualsAndHashCode
public class BakingDate implements BakingFunction<Date> {

    @Override
    public Date apply(Field field, Object rawValue) {
        Date bakedDate;
        if (field.isAnnotationPresent(Map2Pojo.FormattedDate.class)) {
            Map2Pojo.FormattedDate dateFormat = field.getAnnotation(Map2Pojo.FormattedDate.class);
            bakedDate = formattedDate((String) rawValue, dateFormat.value());
        } else if (rawValue instanceof Date) {
            bakedDate = (Date) rawValue;
        } else {
            throw new WrongTypeMappingException(rawValue, Date.class);
        }
        return bakedDate;
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
