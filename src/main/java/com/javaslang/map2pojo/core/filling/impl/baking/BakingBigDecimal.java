package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.annotations.Map2Pojo;
import com.javaslang.map2pojo.core.filling.BakingFunction;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.lang3.LocaleUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Locale;

import static java.math.BigDecimal.valueOf;
import static java.text.NumberFormat.getNumberInstance;
import static org.apache.commons.lang3.StringUtils.trim;

@EqualsAndHashCode(callSuper = false)
public class BakingBigDecimal extends CompositeBakingFunction<BigDecimal> {

    public BakingBigDecimal() {
        super(
                new HashMap<Class, BakingFunction<BigDecimal>>() {
                    {
                        put(BigDecimal.class, (field, rawValue) -> (BigDecimal) rawValue);
                        put(BigInteger.class, (field, rawValue) -> new BigDecimal((BigInteger) rawValue));
                        put(Number.class, (field, rawValue) -> new BigDecimal(((Number) rawValue).doubleValue()));
                        put(String.class, (field, rawValue) -> new StringToBigDecimalConversion().apply(field, rawValue));
                    }
                }
        );
    }

    public static class StringToBigDecimalConversion implements BakingFunction<BigDecimal> {

        private static final Locale NO_LOCALE = new Locale("NO_LOCALE");

        @SneakyThrows
        @Override
        public BigDecimal apply(Field field, Object rawValue) {
            String trimmedRawString = trim((String) rawValue);
            Locale locale = presentLocale(field);
            return NO_LOCALE.equals(locale)
                    ?
                    new BigDecimal(trimmedRawString)
                    :
                    valueOf(
                            getNumberInstance(locale)
                                    .parse(trimmedRawString)
                                    .doubleValue()
                    );
        }

        private Locale presentLocale(Field field) {
            Locale presentLocale;
            if (field.isAnnotationPresent(Map2Pojo.Locale.class)) {
                Map2Pojo.Locale localeAnnotation = field.getAnnotation(Map2Pojo.Locale.class);
                presentLocale = LocaleUtils.toLocale(localeAnnotation.value());
            } else if (field.getDeclaringClass().isAnnotationPresent(Map2Pojo.Locale.class)) {
                Map2Pojo.Locale localeAnnotation = field.getDeclaringClass().getAnnotation(Map2Pojo.Locale.class);
                presentLocale = LocaleUtils.toLocale(localeAnnotation.value());
            } else {
                presentLocale = NO_LOCALE;
            }
            return presentLocale;
        }

    }

}
