package com.javaslang.map2pojo.core.filling.impl.fillings;

import com.javaslang.map2pojo.core.filling.Filling;
import com.javaslang.map2pojo.core.filling.impl.baking.BakingBigDecimal;
import com.javaslang.map2pojo.core.filling.impl.baking.BakingDate;
import com.javaslang.map2pojo.core.filling.impl.baking.BakingString;
import com.javaslang.map2pojo.core.filling.impl.filling.BasicFilling;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.javaslang.map2pojo.core.filling.impl.baking.BakingBigDecimal.StringToBigDecimalConversion.NO_LOCALE;

public class DefaultFillings extends BasicFillings {

    public DefaultFillings() {
        super(init(NO_LOCALE));
    }

    public DefaultFillings(Locale locale) {
        super(init(locale));
    }

    @Override
    public <T> Filling appropriate(Class<T> tClass) {
        return super.appropriate(tClass);
    }

    private static Map<Class, Filling> init(Locale locale) {
        return new HashMap<Class, Filling>() {
            {
                put(
                        Date.class,
                        new BasicFilling<>(
                                new BakingDate()
                        )
                );
                put(
                        BigDecimal.class,
                        new BasicFilling<>(
                                NO_LOCALE.equals(locale)
                                        ?
                                        new BakingBigDecimal()
                                        :
                                        new BakingBigDecimal(locale)

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
