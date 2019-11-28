package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.annotations.Map2Pojo;
import com.javaslang.map2pojo.core.filling.BakingFunction;
import com.javaslang.map2pojo.core.filling.impl.baking.exceptions.WrongTypeMappingException;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import static org.apache.commons.lang3.StringUtils.trim;

@EqualsAndHashCode
public class BakingString implements BakingFunction<String> {

    @Override
    public String apply(Field field, Object rawValue) {
        String bakedString;
        if (field.isAnnotationPresent(Map2Pojo.FormattedDate.class)) {
            Map2Pojo.FormattedDate dateFormat = field.getAnnotation(Map2Pojo.FormattedDate.class);
            return new SimpleDateFormat(dateFormat.value()).format(rawValue);
        } else if (rawValue instanceof String) {
            bakedString = trim((String) rawValue);
        } else {
            throw new WrongTypeMappingException(rawValue, String.class);
        }
        return bakedString;
    }

}
