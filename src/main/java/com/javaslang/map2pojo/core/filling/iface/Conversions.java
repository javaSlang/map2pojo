package com.javaslang.map2pojo.core.filling.iface;

import com.javaslang.map2pojo.core.filling.iface.baking.BakingFunction;
import com.javaslang.map2pojo.core.filling.iface.baking.Conversion;

public interface Conversions<O> {
    <I> Conversions<O> with(Class<I> rawType, Conversion<I, O> conversion);

    BakingFunction<O> of(Class targetClass, Object rawValue);
}
