/*-
 * ========================LICENSE_START=================================
 * map2pojo
 * %%
 * Copyright (C) 2019 Serhii Lanh
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =========================LICENSE_END==================================
 */
package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.annotations.Map2Pojo;
import com.javaslang.map2pojo.core.filling.iface.baking.BakingFunction;
import com.javaslang.map2pojo.core.filling.impl.baking.conversions.Conversions;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.apache.commons.lang3.LocaleUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Locale;

import static java.math.BigDecimal.valueOf;
import static java.text.NumberFormat.getNumberInstance;
import static org.apache.commons.lang3.StringUtils.trim;

@EqualsAndHashCode(callSuper = false)
public class BakingBigDecimal extends CompositeBakingFunction<BigDecimal> {

    public BakingBigDecimal() {
        super(
                new Conversions<BigDecimal>()
                        .with(BigDecimal.class, (field, rawValue) -> rawValue)
                        .with(BigInteger.class, (field, rawValue) -> new BigDecimal(rawValue))
                        .with(Number.class, (field, rawValue) -> new BigDecimal(rawValue.doubleValue()))
                        .with(String.class, (field, rawValue) -> new StringToBigDecimalConversion().apply(field, rawValue))
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
