package com.slang.map2pojo.core.filling;

import com.slang.map2pojo.core.filling.impl.filling.Key2Field;

import java.util.Map;

public interface Filling<T> {

    <D> void inject(D newPojoInstance, Key2Field key2Field, Map<String, Object> normalizedFieldSet);

}
