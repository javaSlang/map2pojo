package com.slang.map2pojo.core.filling.impl.baking;

import com.slang.map2pojo.core.filling.BakingFunction;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Locale;

import static com.slang.map2pojo.core.filling.impl.baking.BakingBigDecimal.StringToBigDecimalConversion.NO_LOCALE;
import static java.math.BigDecimal.valueOf;
import static java.text.NumberFormat.getNumberInstance;
import static org.apache.commons.lang3.StringUtils.trim;

@EqualsAndHashCode(callSuper = false)
public class BakingBigDecimal extends CompositeBakingFunction<BigDecimal> {

    public BakingBigDecimal(Locale locale) {
        super(
                new HashMap<Class, BakingFunction<BigDecimal>>() {
                    {
                        put(BigDecimal.class, (field, rawValue) -> (BigDecimal) rawValue);
                        put(BigInteger.class, (field, rawValue) -> new BigDecimal((BigInteger) rawValue));
                        put(Number.class, (field, rawValue) -> new BigDecimal(((Number) rawValue).doubleValue()));
                        put(String.class, (field, rawValue) -> new StringToBigDecimalConversion(locale).apply(field, rawValue));
                    }
                }
        );
    }

    public BakingBigDecimal() {
        this(NO_LOCALE);
    }

    public static class StringToBigDecimalConversion implements BakingFunction<BigDecimal> {

        public static final Locale NO_LOCALE = new Locale("NO_LOCALE");

        private final Locale locale;

        StringToBigDecimalConversion(Locale locale) {
            this.locale = locale;
        }

        @SneakyThrows
        @Override
        public BigDecimal apply(Field field, Object rawValue) {
            String trimmedRawString = trim((String) rawValue);
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

}
