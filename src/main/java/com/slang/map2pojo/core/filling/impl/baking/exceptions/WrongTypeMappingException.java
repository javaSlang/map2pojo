package com.slang.map2pojo.core.filling.impl.baking.exceptions;

import static java.lang.String.format;

public class WrongTypeMappingException extends IllegalArgumentException {

    private final Object rawValue;
    private final Class targetClass;

    public WrongTypeMappingException(Object rawValue, Class targetClass) {
        this.rawValue = rawValue;
        this.targetClass = targetClass;
    }

    @Override
    public String getMessage() {
        return format("Unable to convert '%s' of '%s' type to '%s'", rawValue, rawValue.getClass(), targetClass.getName());
    }
}
