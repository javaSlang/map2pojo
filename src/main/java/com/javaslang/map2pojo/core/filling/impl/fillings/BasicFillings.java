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
package com.javaslang.map2pojo.core.filling.impl.fillings;

import com.javaslang.map2pojo.core.filling.iface.Filling;
import com.javaslang.map2pojo.core.filling.iface.Fillings;
import com.javaslang.map2pojo.core.filling.impl.baking.NoBaking;
import com.javaslang.map2pojo.core.filling.impl.filling.BasicFilling;

import java.util.Map;

public class BasicFillings implements Fillings {

    private final Map<Class, Filling> fillings;

    public BasicFillings(Map<Class, Filling> fillings) {
        this.fillings = fillings;
    }

    @Override
    public <T> Filling appropriate(Class<T> tClass) {
        return fillings.containsKey(tClass)
                ?
                fillings.get(tClass)
                :
                new BasicFilling<>(
                        new NoBaking<T>()
                );
    }

}