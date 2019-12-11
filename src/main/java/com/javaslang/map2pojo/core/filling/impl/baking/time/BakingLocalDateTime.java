package com.javaslang.map2pojo.core.filling.impl.baking.time;

import com.javaslang.map2pojo.core.filling.impl.baking.CompositeBakingFunction;
import com.javaslang.map2pojo.core.filling.impl.baking.conversions.Conversions;

import java.time.LocalDateTime;

public class BakingLocalDateTime extends CompositeBakingFunction<LocalDateTime> {

    public BakingLocalDateTime() {
        super(
                new Conversions<LocalDateTime>()
                        .with(String.class, new BakingLocalDate.StringToTemporalConversion<>(LocalDateTime::parse))
                        .with(LocalDateTime.class, (field, rawValue) -> rawValue)
        );
    }

}
