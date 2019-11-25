package com.javaslang.map2pojo.core;

import com.javaslang.map2pojo.core.filling.Fillings;
import com.javaslang.map2pojo.core.filling.impl.filling.Key2Field;
import com.javaslang.map2pojo.core.filling.impl.fillings.DefaultFillings;
import com.javaslang.map2pojo.core.normalization.DefaultNormalization;
import com.javaslang.map2pojo.core.normalization.NoNormalization;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.javaslang.map2pojo.annotations.Map2Pojo.OrderedFields;

@Slf4j
public class Map2Pojo<T> implements Transforming<T> {

    private final Class<T> pojoType;
    private final Function<String, String> normalization;
    private final Fillings fillings;

    public Map2Pojo(Class<T> pojoType, Function<String, String> normalization, Fillings fillings) {
        this.pojoType = pojoType;
        this.normalization = normalization;
        this.fillings = fillings;
    }

    public Map2Pojo(Class<T> pojoType, Function<String, String> normalization) {
        this(
                pojoType,
                normalization,
                new DefaultFillings()
        );
    }

    public Map2Pojo(Class<T> pojoType) {
        this(
                pojoType,
                pojoType.isAnnotationPresent(OrderedFields.class)
                        ?
                        new NoNormalization()
                        :
                        new DefaultNormalization()
        );
    }

    @Override
    @SneakyThrows
    public T transform(Map<String, Object> originalMap) {
        boolean isOrderedFieldType = pojoType.isAnnotationPresent(OrderedFields.class);
        Map<String, Object> map2Fields = isOrderedFieldType ? originalMap : normalizedMap(originalMap);
        Field[] declaredFields = pojoType.getDeclaredFields();
        T newPojoInstance = pojoType.newInstance();
        List<Field> fieldList = Arrays.stream(declaredFields).filter(this::onlyPrivateNonStatic).collect(Collectors.toList());
        fieldList.forEach(
                field -> {
                    String key = isOrderedFieldType ? String.valueOf(fieldList.indexOf(field)) : field.getName().toLowerCase();
                    if (!map2Fields.containsKey(key)) {
                        log.info("Skipping '{}' field because normalized map does not contain expected key: '{}'", field.getName(), key);
                        return;
                    }
                    fillings.appropriate(field.getType())
                            .inject(newPojoInstance, new Key2Field(key, field), map2Fields);
                }
        );
        return newPojoInstance;
    }

    private Map<String, Object> normalizedMap(Map<String, Object> originalMap) {
        return originalMap
                .keySet()
                .stream()
                // https://stackoverflow.com/a/24634007
                .collect(HashMap::new,
                        (map, key) -> map.put(normalization.apply(key), originalMap.get(key)),
                        HashMap::putAll
                );
    }

    private boolean onlyPrivateNonStatic(Field field) {
        return !Modifier.isStatic(field.getModifiers()) && Modifier.isPrivate(field.getModifiers());
    }

}