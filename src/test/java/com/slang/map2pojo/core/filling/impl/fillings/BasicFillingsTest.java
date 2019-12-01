package com.slang.map2pojo.core.filling.impl.fillings;

import com.slang.map2pojo.core.filling.Filling;
import com.slang.map2pojo.core.filling.impl.baking.BakingDate;
import com.slang.map2pojo.core.filling.impl.baking.NoBaking;
import com.slang.map2pojo.core.filling.impl.filling.BasicFilling;
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