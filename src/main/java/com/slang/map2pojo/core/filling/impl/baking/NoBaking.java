package com.slang.map2pojo.core.filling.impl.baking;

import com.slang.map2pojo.core.filling.BakingFunction;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
@EqualsAndHashCode
public class NoBaking<T> implements BakingFunction<T> {

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