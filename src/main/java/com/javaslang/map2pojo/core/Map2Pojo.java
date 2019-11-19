package com.javaslang.map2pojo.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.javaslang.map2pojo.annotations.Map2Pojo.FormattedDate;
import static com.javaslang.map2pojo.annotations.Map2Pojo.OrderedFields;

@Slf4j
public class Map2Pojo<T> {

    private final Class<T> domainType;
    private final Function<String, String> normalizationFunction;
    private final Map<Class, Filling> fillings;

    public Map2Pojo(Class<T> domainType, Function<String, String> normalizationFunction, Map<Class, Filling> fillings) {
        this.domainType = domainType;
        this.normalizationFunction = normalizationFunction;
        this.fillings = fillings;
    }

    public Map2Pojo(Class<T> domainType, Function<String, String> normalizationFunction) {
        this(
                domainType,
                normalizationFunction,
                new HashMap<Class, Filling>() {
                    {
                        put(
                                Date.class, new Filling<>(
                                        (field, rawValue) -> {
                                            if (field.isAnnotationPresent(FormattedDate.class)) {
                                                FormattedDate dateFormat = field.getAnnotation(FormattedDate.class);
                                                return formattedDate((String) rawValue, dateFormat.value());
                                            } else if (rawValue instanceof Timestamp) {
                                                return new java.sql.Date(((Timestamp) rawValue).getTime());
                                            } else return rawValue;
                                        }
                                )
                        );
                        put(
                                BigDecimal.class, new Filling<>(
                                        (field, rawValue) -> {
                                            if (rawValue instanceof BigDecimal) {
                                                return rawValue;
                                            } else {
                                                return !StringUtils.isEmpty(String.valueOf(rawValue)) ? new BigDecimal(String.valueOf(rawValue)) : null;
                                            }
                                        }
                                )
                        );
                        put(
                                String.class, new Filling<>(
                                        (field, rawValue) -> StringUtils.trim((String) rawValue)
                                )
                        );
                    }
                }
        );
    }

    public Map2Pojo(Class<T> domainType) {
        this(domainType, Function.identity());
    }

    @SneakyThrows
    public T transform(Map<String, Object> originalMap) {
        boolean isOrderedFieldType = domainType.isAnnotationPresent(OrderedFields.class);
        Map<String, Object> map2Fields = isOrderedFieldType ? originalMap : normalizedMap(originalMap);
        Field[] declaredFields = domainType.getDeclaredFields();
        T newDomainInstance = domainType.newInstance();
        List<Field> fieldList = Arrays.stream(declaredFields).filter(this::onlyPrivateNonStatic).collect(Collectors.toList());
        fieldList.forEach(
                field -> {
                    String key = isOrderedFieldType ? String.valueOf(fieldList.indexOf(field)) : field.getName().toLowerCase();
                    if (!map2Fields.containsKey(key)) {
                        log.info("Skipping '{}' field because normalized map does not contain expected key: '{}'", field.getName(), key);
                        return;
                    }
                    Filling filling = fillings.get(field.getType());
                    if (filling != null) {
                        filling.inject(newDomainInstance, new Key2Field(key, field), map2Fields);
                    } else {
                        log.warn("Skipping '{}' field because there is no provided filling for '{}' type", field.getName(), field.getType());
                    }
                }
        );
        return newDomainInstance;
    }

    private Map<String, Object> normalizedMap(Map<String, Object> originalMap) {
        return originalMap
                .keySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                normalizationFunction,
                                originalMap::get
                        )
                );
    }

    private boolean onlyPrivateNonStatic(Field field) {
        return !Modifier.isStatic(field.getModifiers()) && Modifier.isPrivate(field.getModifiers());
    }

    private static Date formattedDate(String rawValue, String format) {
        Date formattedDate = null;
        if (rawValue != null) {
            try {
                formattedDate = new SimpleDateFormat(format).parse(rawValue);
            } catch (ParseException e) {
                log.error("Wrong date format '{}', expected '{}'", rawValue, format);
            }
        }
        return formattedDate;
    }

}