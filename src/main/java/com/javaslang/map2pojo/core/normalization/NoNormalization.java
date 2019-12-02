package com.javaslang.map2pojo.core.normalization;

import java.util.function.Function;

public class NoNormalization implements Function<String, String> {

    @Override
    public String apply(String normalizable) {
        throw new UnsupportedOperationException("There is no field names normalization for classes annotated with '@OrderedFields'");
    }

}