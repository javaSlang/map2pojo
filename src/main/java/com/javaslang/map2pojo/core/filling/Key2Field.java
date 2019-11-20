package com.javaslang.map2pojo.core.filling;

import java.lang.reflect.Field;

public class Key2Field {

    final String key;
    final Field field;

    public Key2Field(String key, Field field) {
        this.key = key;
        this.field = field;
    }

}
