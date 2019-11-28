package com.javaslang.map2pojo.core.filling;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

public interface BakingFunction<T> extends BiFunction<Field, Object, T> {

    default T bake(Field field, Object rawValue) {
        return rawValue == null ? null : apply(field, rawValue);
    }

}
