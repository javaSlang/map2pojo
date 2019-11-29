package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.core.filling.BakingFunction;
import com.javaslang.map2pojo.core.filling.impl.baking.exceptions.WrongTypeMappingException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompositeBakingFunction<T> implements BakingFunction<T> {

    private final Map<Class, BakingFunction<T>> conversions;

    public CompositeBakingFunction(Map<Class, BakingFunction<T>> conversions) {
        this.conversions = conversions;
    }

    @Override
    public T apply(Field field, Object rawValue) {
        return conversions
                .get(findAppropriateClassOrThrow(rawValue))
                .apply(field, rawValue);
    }

    private Class findAppropriateClassOrThrow(Object rawValue) {
        for (Class clazz : parentsUpToObject(rawValue.getClass())) {
            if (conversions.containsKey(clazz)) {
                return clazz;
            }
        }
        throw new WrongTypeMappingException(rawValue, BigDecimal.class);
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