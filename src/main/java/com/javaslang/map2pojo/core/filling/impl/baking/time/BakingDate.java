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
package com.javaslang.map2pojo.core.filling.impl.baking.time;

import com.javaslang.map2pojo.annotations.Map2Pojo;
import com.javaslang.map2pojo.core.filling.BakingFunction;
import com.javaslang.map2pojo.core.filling.impl.baking.CompositeBakingFunction;
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
            try {
                formattedDate = new SimpleDateFormat(format).parse(rawValue);
            } catch (ParseException e) {
                log.warn("Wrong date format '{}', expected '{}'", rawValue, format);
            }
            return formattedDate;
        }

    }

}
