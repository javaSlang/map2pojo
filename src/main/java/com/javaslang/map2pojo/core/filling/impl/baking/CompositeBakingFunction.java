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
package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.core.filling.iface.baking.BakingFunction;
import com.javaslang.map2pojo.core.filling.impl.baking.exceptions.WrongTypeMappingException;

import java.lang.reflect.Field;
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
                .get(findAppropriateClassOrThrow(field, rawValue))
                .apply(field, rawValue);
    }

    private Class findAppropriateClassOrThrow(Field field, Object rawValue) {
        for (Class clazz : parentsUpToObject(rawValue.getClass())) {
            if (conversions.containsKey(clazz)) {
                return clazz;
            }
        }
        throw new WrongTypeMappingException(rawValue, field.getType());
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