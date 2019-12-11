package com.javaslang.map2pojo.core.filling.impl.baking.conversions;

import com.javaslang.map2pojo.core.filling.iface.baking.BakingFunction;
import com.javaslang.map2pojo.core.filling.iface.baking.Conversion;

import java.util.HashMap;

public class Conversions<O> extends HashMap<Class, BakingFunction<O>> {

    public <I> Conversions<O> with(Class<I> rawType, Conversion<I, O> conversion) {
        super.put(rawType, (field, rawValue) -> conversion.apply(field, rawType.cast(rawValue)));
        return this;
    }

}
