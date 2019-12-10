package com.javaslang.map2pojo.core.filling.impl.baking.time;

import com.javaslang.map2pojo.core.filling.BakingFunction;
import com.javaslang.map2pojo.core.filling.impl.baking.CompositeBakingFunction;

import java.time.LocalDateTime;
import java.util.HashMap;

public class BakingLocalDateTime extends CompositeBakingFunction<LocalDateTime> {

    public BakingLocalDateTime() {
        super(
                new HashMap<Class, BakingFunction<LocalDateTime>>() {
                    {
                        put(String.class, new BakingLocalDate.StringToTemporalConversion<>(LocalDateTime::parse));
                        put(LocalDateTime.class, (field, rawValue) -> (LocalDateTime) rawValue);
                    }
                }
        );
    }

}
