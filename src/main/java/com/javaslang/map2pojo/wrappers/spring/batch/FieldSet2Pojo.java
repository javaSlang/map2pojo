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
package com.javaslang.map2pojo.wrappers.spring.batch;

import com.javaslang.map2pojo.core.Transforming;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class FieldSet2Pojo<T> implements FieldSetMapper<T> {

    private final Transforming<T> transforming;

    public FieldSet2Pojo(Transforming<T> transforming) {
        this.transforming = transforming;
    }

    @Override
    @NonNull
    public T mapFieldSet(FieldSet fieldSet) {
        return fieldSet.hasNames()
                ?
                transforming.transform(
                        properties2Map(fieldSet.getProperties())
                )
                :
                transforming.transform(
                        values2Map(fieldSet.getValues())
                );
    }

    Map<String, Object> properties2Map(Properties properties) {
        return properties
                .keySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                String::valueOf,
                                key -> properties.getProperty((String) key)
                        )
                );
    }

    Map<String, Object> values2Map(String[] values) {
        Map<String, Object> mapByIndex = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            mapByIndex.put(String.valueOf(i), values[i]);
        }
        return mapByIndex;
    }

}
