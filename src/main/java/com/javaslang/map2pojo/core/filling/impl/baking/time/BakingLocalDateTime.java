package com.javaslang.map2pojo.core.filling.impl.baking.time;

import com.javaslang.map2pojo.core.filling.impl.baking.CompositeBakingFunction;
import com.javaslang.map2pojo.core.filling.impl.baking.conversions.BasicConversions;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BakingLocalDateTime extends CompositeBakingFunction<LocalDateTime> {

    public BakingLocalDateTime() {
        super(
                new BasicConversions<LocalDateTime>()
                        .with(String.class, new BakingLocalDate.StringToTemporalConversion<>(LocalDateTime::parse))
                        .with(Timestamp.class, (field, rawValue) -> rawValue.toLocalDateTime())
                        .with(LocalDateTime.class, (field, rawValue) -> rawValue)
        );
    }

}
