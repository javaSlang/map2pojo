package com.javaslang.map2pojo.wrappers.spring.jdbc;

import com.javaslang.map2pojo.core.Map2Pojo;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

public class ResultSet2Pojo<T> implements RowMapper<T> {

    private final Map2Pojo<T> map2Pojo;

    public ResultSet2Pojo(Map2Pojo<T> map2Pojo) {
        this.map2Pojo = map2Pojo;
    }

    @Override
    public T mapRow(@NonNull ResultSet resultSet, int i) {
        return map2Pojo.transform(
                resultSet2Map(
                        resultSet
                )
        );
    }

    @SneakyThrows
    private Map<String, Object> resultSet2Map(ResultSet rs) {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        Map<String, Object> row = new HashMap<>(columns);
        for (int i = 1; i <= columns; ++i) {
            row.put(md.getColumnName(i), rs.getObject(i));
        }
        return row;
    }
}
