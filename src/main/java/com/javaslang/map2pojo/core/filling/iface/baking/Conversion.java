package com.javaslang.map2pojo.core.filling.iface.baking;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

public interface Conversion<I, O> extends BiFunction<Field, I, O> {
    @Override
    O apply(Field field, I rawValue);
}
