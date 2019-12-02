package com.javaslang.map2pojo.core.normalization;

import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.*;

public final class DefaultNormalization implements Function<String, String> {

    private static final String UNDERSCORE = "_";

    @Override
    public String apply(String normalizable) {
        return
                lowerCase(
                        replace(
                                replace(
                                        normalizable,
                                        SPACE,
                                        EMPTY
                                ),
                                UNDERSCORE,
                                EMPTY
                        )
                );
    }
}