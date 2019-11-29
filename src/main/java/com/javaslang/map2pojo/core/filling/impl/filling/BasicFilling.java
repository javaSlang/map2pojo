package com.javaslang.map2pojo.core.filling.impl.filling;

import com.javaslang.map2pojo.core.filling.BakingFunction;
import com.javaslang.map2pojo.core.filling.Filling;
import com.javaslang.map2pojo.core.filling.impl.baking.NoBaking;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static org.apache.commons.lang3.reflect.FieldUtils.writeField;

@Slf4j
@EqualsAndHashCode
public class BasicFilling<T> implements Filling<T> {

    private final BakingFunction<T> bakingFunction;

    public BasicFilling(BakingFunction<T> bakingFunction) {
        this.bakingFunction = bakingFunction;
    }

    @Override
    public <D> void inject(D newPojoInstance, Key2Field key2Field, Map<String, Object> normalizedFieldSet) {
        if (isNoBaking(key2Field)) {
            return;
        }
        checkValueAndWriteField(
                newPojoInstance,
                key2Field,
                bakingFunction.bake(
                        key2Field.field,
                        normalizedFieldSet.get(
                                key2Field.key
                        )
                )
        );
    }

    @SneakyThrows
    private <D> void checkValueAndWriteField(D newPojoInstance, Key2Field key2Field, T bakedValue) {
        if (bakedValue == null) {
            return;
        }
        writeField(
                newPojoInstance,
                key2Field.field.getName(),
                bakedValue,
                true
        );
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
