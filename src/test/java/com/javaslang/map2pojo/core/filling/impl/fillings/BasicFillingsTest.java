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

import com.javaslang.map2pojo.core.filling.Filling;
import com.javaslang.map2pojo.core.filling.impl.baking.NoBaking;
import com.javaslang.map2pojo.core.filling.impl.baking.time.BakingDate;
import com.javaslang.map2pojo.core.filling.impl.filling.BasicFilling;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;

public class BasicFillingsTest {

    static final BasicFilling<Date> BASIC_FILLING_WITH_NO_BAKING = new BasicFilling<>(
            new NoBaking<>()
    );
    static final BasicFilling<Date> BASIC_FILLING_WITH_DATE_BAKING = new BasicFilling<>(
            new BakingDate()
    );

    @Test
    public void containsAppropriateFilling() {
        BasicFillings fillings = new BasicFillings(
                new HashMap<Class, Filling>() {
                    {
                        put(Date.class, BASIC_FILLING_WITH_DATE_BAKING);
                    }
                }
        );
        Filling appropriateFilling = fillings.appropriate(Date.class);
        assertEquals(BASIC_FILLING_WITH_DATE_BAKING, appropriateFilling);
    }

    @Test
    public void doesNotContainAppropriateFilling() {
        BasicFillings fillings = new BasicFillings(new HashMap<>());
        assertEquals(
                BASIC_FILLING_WITH_NO_BAKING,
                fillings.appropriate(Date.class)
        );
    }

}