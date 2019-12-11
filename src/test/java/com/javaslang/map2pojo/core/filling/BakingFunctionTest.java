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
package com.javaslang.map2pojo.core.filling;

import com.javaslang.map2pojo.core.filling.iface.baking.BakingFunction;
import org.junit.Test;

import static com.javaslang.map2pojo.TestPojoClass.TEST_CLASS_FIELDS;
import static com.javaslang.map2pojo.TestPojoClass.TEST_FIELD;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertNull;

public class BakingFunctionTest {

    @Test
    public void testNullValue() {
        BakingFunction<Object> bakingFunction = (field, o) -> new Object();
        assertNull(bakingFunction.bake(TEST_CLASS_FIELDS.get(TEST_FIELD), null));
    }

    @Test
    public void testEmptyString() {
        BakingFunction<Object> bakingFunction = (field, o) -> new Object();
        assertNull(bakingFunction.bake(TEST_CLASS_FIELDS.get(TEST_FIELD), EMPTY));
    }

}
