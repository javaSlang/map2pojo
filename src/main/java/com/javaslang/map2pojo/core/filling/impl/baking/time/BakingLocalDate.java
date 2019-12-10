package com.javaslang.map2pojo.core.filling.impl.baking.time;

import com.javaslang.map2pojo.annotations.Map2Pojo;
import com.javaslang.map2pojo.core.filling.BakingFunction;
import com.javaslang.map2pojo.core.filling.impl.baking.CompositeBakingFunction;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.function.BiFunction;

public class BakingLocalDate extends CompositeBakingFunction<LocalDate> {

    public BakingLocalDate() {
        super(
                new HashMap<Class, BakingFunction<LocalDate>>() {
                    {
                        put(String.class, new StringToTemporalConversion<>(LocalDate::parse));
                        put(LocalDate.class, (field, rawValue) -> (LocalDate) rawValue);
                    }
                }
        );
    }

    public static class StringToTemporalConversion<T extends Temporal> implements BakingFunction<T> {

        private final BiFunction<CharSequence, DateTimeFormatter, T> formattingFunction;

        public StringToTemporalConversion(BiFunction<CharSequence, DateTimeFormatter, T> formattingFunction) {
            this.formattingFunction = formattingFunction;
        }

        @Override
        public T apply(Field field, Object rawValue) {
            Map2Pojo.FormattedDate dateFormat = field.getAnnotation(Map2Pojo.FormattedDate.class);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat.value());
            return formattingFunction.apply((String) rawValue, formatter);
        }

    }

}
