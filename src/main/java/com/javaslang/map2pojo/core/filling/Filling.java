package com.javaslang.map2pojo.core.filling;

import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.BiFunction;

public class Filling<T> {

    private final BiFunction<Field, Object, T> bakingFunction;

    public Filling(BiFunction<Field, Object, T> bakingFunction) {
        this.bakingFunction = bakingFunction;
    }

    @SneakyThrows
    public <D> void inject(D newDomainInstance, Key2Field key2Field, Map<String, Object> normalizedFieldSet) {
        FieldUtils.writeField(
                newDomainInstance,
                key2Field.field.getName(),
                bakingFunction.apply(
                        key2Field.field,
                        normalizedFieldSet.get(
                                key2Field.key
                        )
                ), true
        );
    }

}
