package com.slang.map2pojo.core.filling.impl.fillings;

import com.slang.map2pojo.core.filling.impl.baking.BakingBigDecimal;
import com.slang.map2pojo.core.filling.impl.baking.BakingString;
import com.slang.map2pojo.core.filling.impl.filling.BasicFilling;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;

public class DefaultFillingsTest {

    @Test
    public void defaultFillingsTest() {
        DefaultFillings defaultFillings = new DefaultFillings();
        assertEquals(
                BasicFillingsTest.BASIC_FILLING_WITH_DATE_BAKING,
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
                BasicFillingsTest.BASIC_FILLING_WITH_NO_BAKING,
                defaultFillings.appropriate(Object.class)
        );
    }

    @Test
    public void defaultFillingsWithLocaleTest() {
        DefaultFillings defaultFillings = new DefaultFillings(Locale.GERMAN);
        assertEquals(
                new BasicFilling<>(
                        new BakingBigDecimal(Locale.GERMAN)
                ),
                defaultFillings.appropriate(BigDecimal.class)
        );
    }

}