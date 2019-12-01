package com.slang.map2pojo.core;

import java.util.Map;

public interface Transforming<T> {

    T transform(Map<String, Object> originalMap);

}