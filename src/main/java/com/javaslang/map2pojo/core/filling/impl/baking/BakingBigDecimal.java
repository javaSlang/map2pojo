package com.javaslang.map2pojo.core.filling.impl.baking;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.function.BiFunction;

import static java.lang.String.valueOf;
import static java.text.NumberFormat.getNumberInstance;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.trim;

@EqualsAndHashCode
public class BakingBigDecimal implements BiFunction<Field, Object, BigDecimal> {

    public static final Locale NO_LOCALE = new Locale("NO_LOCALE");

    private final Locale locale;

    public BakingBigDecimal() {
        this.locale = NO_LOCALE;
    }

    public BakingBigDecimal(Locale locale) {
        this.locale = locale;
    }

    @Override
    public BigDecimal apply(Field field, Object rawValue) {
        return rawValue instanceof BigDecimal
                ?
                (BigDecimal) rawValue
                :
                toBigDecimal(rawValue);
    }

    @SneakyThrows
    private BigDecimal toBigDecimal(Object rawValue) {
        String trimmedRawValue = trim(valueOf(rawValue));
        return rawValue != null && !isEmpty(valueOf(rawValue))
                ?
                NO_LOCALE.equals(locale)
                        ?
                        new BigDecimal(trimmedRawValue)
                        :
                        BigDecimal.valueOf(
                                getNumberInstance(locale).parse(trimmedRawValue).doubleValue()
                        )
                :
                null;
    }

}
