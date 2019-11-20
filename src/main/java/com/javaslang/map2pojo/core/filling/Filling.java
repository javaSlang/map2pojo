package com.javaslang.map2pojo.core.filling;

import com.javaslang.map2pojo.core.filling.baking.NoBaking;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.BiFunction;

@Slf4j
public class Filling<T> {

    private final BiFunction<Field, Object, T> bakingFunction;

    public Filling(BiFunction<Field, Object, T> bakingFunction) {
        this.bakingFunction = bakingFunction;
    }

    @SneakyThrows
    public <D> void inject(D newDomainInstance, Key2Field key2Field, Map<String, Object> normalizedFieldSet) {
        noBakingWarn(key2Field);
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

    private void noBakingWarn(Key2Field key2Field) {
        if (bakingFunction instanceof NoBaking) {
            log.warn("Skipping '{}' field because there is no provided filling for '{}' type", key2Field.field.getName(), key2Field.field.getType());
        }
    }

}