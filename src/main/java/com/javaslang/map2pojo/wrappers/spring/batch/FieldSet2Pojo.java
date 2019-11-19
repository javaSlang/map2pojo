package com.javaslang.map2pojo.wrappers.spring.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class FieldSet2Pojo<T> implements FieldSetMapper<T> {

    public T mapFieldSet(FieldSet fieldSet) {
        return null;
    }

}
