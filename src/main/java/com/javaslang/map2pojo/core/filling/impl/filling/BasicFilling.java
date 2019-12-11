/*-
 * ========================LICENSE_START=================================
 * map2pojo
 * %%
 * Copyright (C) 2019 Serhii Lanh
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =========================LICENSE_END==================================
 */
package com.javaslang.map2pojo.core.filling.impl.filling;

import com.javaslang.map2pojo.core.filling.iface.Filling;
import com.javaslang.map2pojo.core.filling.iface.baking.BakingFunction;
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
