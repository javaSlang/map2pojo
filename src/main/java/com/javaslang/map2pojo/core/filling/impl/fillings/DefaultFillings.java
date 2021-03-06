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
import com.javaslang.map2pojo.core.filling.impl.baking.BakingBigDecimal;
import com.javaslang.map2pojo.core.filling.impl.baking.BakingString;
import com.javaslang.map2pojo.core.filling.impl.baking.time.BakingDate;
import com.javaslang.map2pojo.core.filling.impl.baking.time.BakingLocalDate;
import com.javaslang.map2pojo.core.filling.impl.baking.time.BakingLocalDateTime;
import com.javaslang.map2pojo.core.filling.impl.filling.BasicFilling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DefaultFillings extends BasicFillings {

    public DefaultFillings() {
        super(init());
    }

    @Override
    public <T> Filling appropriate(Class<T> tClass) {
        return super.appropriate(tClass);
    }

    private static Map<Class, Filling> init() {
        return new HashMap<Class, Filling>() {
            {
                put(
                        Date.class,
                        new BasicFilling<>(
                                new BakingDate()
                        )
                );
                put(
                        LocalDate.class,
                        new BasicFilling<>(
                                new BakingLocalDate()
                        )
                );
                put(
                        LocalDateTime.class,
                        new BasicFilling<>(
                                new BakingLocalDateTime()
                        )
                );
                put(
                        BigDecimal.class,
                        new BasicFilling<>(
                                new BakingBigDecimal()
                        )
                );
                put(
                        String.class,
                        new BasicFilling<>(
                                new BakingString()
                        )
                );
            }
        };
    }

}
