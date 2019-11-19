package com.javaslang.map2pojo.wrappers.spring.batch;

import com.javaslang.map2pojo.core.Map2Pojo;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class FieldSet2Pojo<T> implements FieldSetMapper<T> {

    private final Map2Pojo<T> map2Pojo;

    public FieldSet2Pojo(Map2Pojo<T> map2Pojo) {
        this.map2Pojo = map2Pojo;
    }

    @Override
    @NonNull
    public T mapFieldSet(FieldSet fieldSet) {
        return fieldSet.hasNames()
                ?
                map2Pojo.transform(
                        properties2Map(fieldSet.getProperties())
                )
                :
                map2Pojo.transform(
                        values2Map(fieldSet.getValues())
                );
    }

    private Map<String, Object> properties2Map(Properties properties) {
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

    private Map<String, Object> values2Map(String[] values) {
        Map<String, Object> mapByIndex = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            mapByIndex.put(String.valueOf(i), values[i]);
        }
        return mapByIndex;
    }

}
