package com.javaslang.map2pojo.core.filling.impl.baking;

import com.javaslang.map2pojo.annotations.Map2Pojo;
import com.javaslang.map2pojo.core.filling.BakingFunction;
import lombok.EqualsAndHashCode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static org.apache.commons.lang3.StringUtils.trim;

@EqualsAndHashCode(callSuper = false)
public class BakingString extends CompositeBakingFunction<String> {

    public BakingString() {
        super(
                new HashMap<Class, BakingFunction<String>>() {
                    {
                        put(String.class, (field, rawValue) -> trim((String) rawValue));
                        put(Date.class, (field, rawValue) -> {
                            Map2Pojo.FormattedDate dateFormat = field.getAnnotation(Map2Pojo.FormattedDate.class);
                            return new SimpleDateFormat(dateFormat.value()).format(rawValue);
                        });
                    }
                }
        );
    }

}
