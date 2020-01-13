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
package com.javaslang.map2pojo.core;

import com.javaslang.map2pojo.core.filling.iface.Fillings;
import com.javaslang.map2pojo.core.filling.impl.filling.Key2Field;
import com.javaslang.map2pojo.core.filling.impl.fillings.DefaultFillings;
import com.javaslang.map2pojo.core.normalization.DefaultNormalization;
import com.javaslang.map2pojo.core.normalization.NoNormalization;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.javaslang.map2pojo.annotations.Map2Pojo.OrderedFields;

@Slf4j
public class Map2Pojo<T> implements Transforming<T> {

    private final Class<T> pojoType;
    private final Function<String, String> normalization;
    private final Fillings fillings;

    public Map2Pojo(Class<T> pojoType, Function<String, String> normalization, Fillings fillings) {
        this.pojoType = pojoType;
        this.normalization = normalization;
        this.fillings = fillings;
    }

    public Map2Pojo(Class<T> pojoType, Function<String, String> normalization) {
        this(
                pojoType,
                normalization,
                new DefaultFillings()
        );
    }

    public Map2Pojo(Class<T> pojoType) {
        this(
                pojoType,
                pojoType.isAnnotationPresent(OrderedFields.class)
                        ?
                        new NoNormalization()
                        :
                        new DefaultNormalization()
        );
    }

    @Override
    @SneakyThrows
    public T transform(Map<String, Object> originalMap) {
        boolean isOrderedFieldType = pojoType.isAnnotationPresent(OrderedFields.class);
        Map<String, Object> map2Fields = isOrderedFieldType ? originalMap : normalizedMap(originalMap);
        T newPojoInstance = pojoType.newInstance();
        List<Field> fieldList = allFields(pojoType);
        fieldList.forEach(
                field -> {
                    String key = isOrderedFieldType ? String.valueOf(fieldList.indexOf(field)) : field.getName().toLowerCase();
                    if (!map2Fields.containsKey(key)) {
                        log.info("Skipping '{}' field because normalized map does not contain expected key: '{}'", field.getName(), key);
                        return;
                    }
                    fillings.appropriate(field.getType())
                            .inject(newPojoInstance, new Key2Field(key, field), map2Fields);
                }
        );
        return newPojoInstance;
    }

    private List<Field> allFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (!Object.class.equals(clazz)) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList.stream().filter(this::onlyNonStatic).collect(Collectors.toList());
    }

    private Map<String, Object> normalizedMap(Map<String, Object> originalMap) {
        return originalMap
                .keySet()
                .stream()
                // https://stackoverflow.com/a/24634007
                .collect(HashMap::new,
                        (map, key) -> map.put(normalization.apply(key), originalMap.get(key)),
                        HashMap::putAll
                );
    }

    private boolean onlyNonStatic(Field field) {
        return !Modifier.isStatic(field.getModifiers());
    }

}