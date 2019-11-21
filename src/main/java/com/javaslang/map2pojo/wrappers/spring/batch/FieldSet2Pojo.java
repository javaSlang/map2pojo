package com.javaslang.map2pojo.wrappers.spring.batch;

import com.javaslang.map2pojo.core.Transforming;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class FieldSet2Pojo<T> implements FieldSetMapper<T> {

    private final Transforming<T> transforming;

    public FieldSet2Pojo(Transforming<T> transforming) {
        this.transforming = transforming;
    }

    @Override
    @NonNull
    public T mapFieldSet(FieldSet fieldSet) {
        return fieldSet.hasNames()
                ?
                transforming.transform(
                        properties2Map(fieldSet.getProperties())
                )
                :
                transforming.transform(
                        values2Map(fieldSet.getValues())
                );
    }

    Map<String, Object> properties2Map(Properties properties) {
        return properties
                .keySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                String::valueOf,
                                key -> properties.getProperty((String) key)
                        )
                );
    }

    Map<String, Object> values2Map(String[] values) {
        Map<String, Object> mapByIndex = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            mapByIndex.put(String.valueOf(i), values[i]);
        }
        return mapByIndex;
    }

}
