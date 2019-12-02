package com.javaslang.map2pojo.core.filling.impl.fillings;

import com.javaslang.map2pojo.core.filling.impl.baking.BakingBigDecimal;
import com.javaslang.map2pojo.core.filling.impl.baking.BakingString;
import com.javaslang.map2pojo.core.filling.impl.filling.BasicFilling;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import static com.javaslang.map2pojo.core.filling.impl.fillings.BasicFillingsTest.BASIC_FILLING_WITH_DATE_BAKING;
import static com.javaslang.map2pojo.core.filling.impl.fillings.BasicFillingsTest.BASIC_FILLING_WITH_NO_BAKING;
import static junit.framework.TestCase.assertEquals;

public class DefaultFillingsTest {

    @Test
    public void defaultFillingsTest() {
        DefaultFillings defaultFillings = new DefaultFillings();
        assertEquals(
                BASIC_FILLING_WITH_DATE_BAKING,
                defaultFillings.appropriate(Date.class)
        );
        assertEquals(
                new BasicFilling<>(
                        new BakingBigDecimal()
                ),
                defaultFillings.appropriate(BigDecimal.class)
        );
        assertEquals(
                new BasicFilling<>(
                        new BakingString()
                ),
                defaultFillings.appropriate(String.class)
        );
        assertEquals(
                BASIC_FILLING_WITH_NO_BAKING,
                defaultFillings.appropriate(Object.class)
        );
    }

    @Test
    public void defaultFillingsWithLocaleTest() {
        DefaultFillings defaultFillings = new DefaultFillings();
        assertEquals(
                new BasicFilling<>(
                        new BakingBigDecimal()
                ),
                defaultFillings.appropriate(BigDecimal.class)
        );
    }

}