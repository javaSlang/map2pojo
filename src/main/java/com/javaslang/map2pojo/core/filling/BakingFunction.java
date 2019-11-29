package com.javaslang.map2pojo.core.filling;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

import static org.springframework.util.StringUtils.isEmpty;

public interface BakingFunction<T> extends BiFunction<Field, Object, T> {

    default T bake(Field field, Object rawValue) {
        return isEmpty(rawValue) ? null : apply(field, rawValue);
    }

}
