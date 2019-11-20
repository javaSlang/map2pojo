package com.javaslang.map2pojo.core.filling.impl.fillings;

import com.javaslang.map2pojo.core.filling.iface.Filling;
import com.javaslang.map2pojo.core.filling.impl.baking.BakingBigDecimal;
import com.javaslang.map2pojo.core.filling.impl.baking.BakingDate;
import com.javaslang.map2pojo.core.filling.impl.baking.BakingString;
import com.javaslang.map2pojo.core.filling.impl.filling.BasicFilling;

import java.math.BigDecimal;
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
