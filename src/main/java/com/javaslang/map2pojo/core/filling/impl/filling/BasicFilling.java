package com.javaslang.map2pojo.core.filling.impl.filling;

import com.javaslang.map2pojo.core.filling.Filling;
import com.javaslang.map2pojo.core.filling.impl.baking.NoBaking;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
public class BasicFilling<T> implements Filling<T> {

    final BiFunction<Field, Object, T> bakingFunction;

    public BasicFilling(BiFunction<Field, Object, T> bakingFunction) {
        this.bakingFunction = bakingFunction;
    }

    @Override
    @SneakyThrows
    public <D> void inject(D newPojoInstance, Key2Field key2Field, Map<String, Object> normalizedFieldSet) {
        if (!isNoBaking(key2Field)) {
            FieldUtils.writeField(
                    newPojoInstance,
                    key2Field.field.getName(),
                    bakingFunction.apply(
                            key2Field.field,
                            normalizedFieldSet.get(
                                    key2Field.key
                            )
                    ),
                    true
            );
        }
    }

    boolean isNoBaking(Key2Field key2Field) {
        if (bakingFunction instanceof NoBaking) {
            log.warn("Skipping '{}' field because there is no provided filling for '{}' type", key2Field.field.getName(), key2Field.field.getType());
            return true;
        } else {
            return false;
        }
    }

}
