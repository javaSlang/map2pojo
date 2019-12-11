package com.javaslang.map2pojo.core.filling.impl.baking.conversions;

import com.javaslang.map2pojo.core.filling.iface.Conversions;
import com.javaslang.map2pojo.core.filling.iface.baking.BakingFunction;
import com.javaslang.map2pojo.core.filling.iface.baking.Conversion;
import com.javaslang.map2pojo.core.filling.impl.baking.exceptions.WrongTypeMappingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BasicConversions<O> extends HashMap<Class, BakingFunction<O>> implements Conversions<O> {

    @Override
    public <I> Conversions<O> with(Class<I> rawType, Conversion<I, O> conversion) {
        super.put(rawType, (field, rawValue) -> conversion.apply(field, rawType.cast(rawValue)));
        return this;
    }

    @Override
    public BakingFunction<O> of(Class targetClass, Object rawValue) {
        for (Class clazz : parentsUpToObject(rawValue.getClass())) {
            if (this.containsKey(clazz)) {
                return this.get(clazz);
            }
        }
        throw new WrongTypeMappingException(rawValue, targetClass);
    }

    private List<Class> parentsUpToObject(Class clazz) {
        List<Class> classAndParents = new ArrayList<>();
        while (!Object.class.equals(clazz)) {
            classAndParents.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return classAndParents;
    }

}
