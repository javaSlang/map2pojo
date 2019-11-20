package com.javaslang.map2pojo.core.filling.iface;

import com.javaslang.map2pojo.core.filling.impl.filling.Key2Field;
import lombok.SneakyThrows;

import java.util.Map;

public interface Filling<T> {

    @SneakyThrows
    <D> void inject(D newPojoInstance, Key2Field key2Field, Map<String, Object> normalizedFieldSet);

}
