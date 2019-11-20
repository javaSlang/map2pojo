package com.javaslang.map2pojo.core.filling.impl.baking;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

@Slf4j
public class NoBaking<T> implements BiFunction<Field, Object, T> {

    @Override
    public T apply(Field field, Object o) {
        throw new UnsupportedOperationException(
                String.format(
                        "There is no filling for %s type - field %s should be skipped",
                        field.getType(),
                        field.getName()
                )
        );
    }

}