package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.core.filling.BakingFunction;
import com.javaslang.map2pojo.core.filling.impl.baking.exceptions.WrongTypeMappingException;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

import static java.math.BigDecimal.valueOf;
import static java.text.NumberFormat.getNumberInstance;
import static org.apache.commons.lang3.StringUtils.trim;

@EqualsAndHashCode
public class BakingBigDecimal implements BakingFunction<BigDecimal> {

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
        BigDecimal bakedBigDecimal;
        if (rawValue instanceof BigDecimal) {
            bakedBigDecimal = (BigDecimal) rawValue;
        } else if (rawValue instanceof String) {
            bakedBigDecimal = bigDecimalFromString((String) rawValue);
        } else if (rawValue instanceof BigInteger) {
            bakedBigDecimal = new BigDecimal((BigInteger) rawValue);
        } else if (rawValue instanceof Number) {
            bakedBigDecimal = new BigDecimal(((Number) rawValue).doubleValue());
        } else {
            throw new WrongTypeMappingException(rawValue, BigDecimal.class);
        }
        return bakedBigDecimal;
    }

    @SneakyThrows
    private BigDecimal bigDecimalFromString(String rawValue) {
        String trimmedRawString = trim(rawValue);
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

}
