package com.slang.map2pojo.wrappers.spring.jdbc;

import com.slang.map2pojo.core.Transforming;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

public class ResultSet2Pojo<T> implements RowMapper<T> {

    private final Transforming<T> transforming;

    public ResultSet2Pojo(Transforming<T> transforming) {
        this.transforming = transforming;
    }

    @Override
    public T mapRow(@NonNull ResultSet resultSet, int i) {
        return transforming.transform(
                resultSet2Map(
                        resultSet
                )
        );
    }

    @SneakyThrows
    Map<String, Object> resultSet2Map(ResultSet rs) {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        Map<String, Object> row = new HashMap<>(columns);
        for (int i = 1; i <= columns; ++i) {
            row.put(md.getColumnName(i), rs.getObject(i));
        }
        return row;
    }
}
