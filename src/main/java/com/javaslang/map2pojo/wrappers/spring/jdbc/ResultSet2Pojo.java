package com.javaslang.map2pojo.wrappers.spring.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class ResultSet2Pojo<T> implements RowMapper<T> {

    public T mapRow(ResultSet resultSet, int i) {
        return null;
    }

}
