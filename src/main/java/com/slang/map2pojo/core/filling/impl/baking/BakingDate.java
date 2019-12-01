package com.slang.map2pojo.core.filling.impl.baking;

import com.slang.map2pojo.annotations.Map2Pojo;
import com.slang.map2pojo.core.filling.BakingFunction;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@EqualsAndHashCode(callSuper = false)
public class BakingDate extends CompositeBakingFunction<Date> {

    public BakingDate() {
        super(
                new HashMap<Class, BakingFunction<Date>>() {
                    {
                        put(String.class, (field, rawValue) -> new StringToDateConversion().apply(field, rawValue));
                        put(Date.class, (field, rawValue) -> (Date) rawValue);
                    }
                }
        );
    }

    public static class StringToDateConversion implements BakingFunction<Date> {

        @Override
        public Date apply(Field field, Object rawValue) {
            Map2Pojo.FormattedDate dateFormat = field.getAnnotation(Map2Pojo.FormattedDate.class);
            return formattedDate((String) rawValue, dateFormat.value());
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

}
