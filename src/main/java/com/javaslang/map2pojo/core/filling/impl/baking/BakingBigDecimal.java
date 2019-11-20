package com.javaslang.map2pojo.core.filling.impl.baking;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.function.BiFunction;

import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class BakingBigDecimal implements BiFunction<Field, Object, BigDecimal> {

    @Override
    public BigDecimal apply(Field field, Object rawValue) {
        return rawValue instanceof BigDecimal
                ?
                (BigDecimal) rawValue
                :
                !isEmpty(valueOf(rawValue))
                        ?
                        new BigDecimal(
                                valueOf(
                                        rawValue
                                )
                        )
                        :
                        null;
    }

}
