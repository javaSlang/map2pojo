package com.javaslang.map2pojo.core.filling.impl.baking;

import lombok.EqualsAndHashCode;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

import static org.apache.commons.lang3.StringUtils.trim;

@EqualsAndHashCode
public class BakingString implements BiFunction<Field, Object, String> {

    @Override
    public String apply(Field field, Object rawValue) {
        return trim((String) rawValue);
    }

}
