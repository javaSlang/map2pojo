package com.slang.map2pojo.wrappers.spring.jdbc;

import com.slang.map2pojo.core.Transforming;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import static com.slang.map2pojo.wrappers.spring.batch.FieldSet2PojoTest.*;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ResultSet2PojoTest {

    @Mock
    private Transforming<Object> transforming;
    @Mock
    private ResultSet resultSet;
    @Mock
    private ResultSetMetaData resultSetMetaData;

    @InjectMocks
    private ResultSet2Pojo<Object> resultSet2Pojo;

    @Test
    public void mapRowTest() throws SQLException {
        when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        when(resultSetMetaData.getColumnCount()).thenReturn(0);
        resultSet2Pojo.mapRow(resultSet, 0);
        verify(resultSet, times(1)).getMetaData();
        verify(transforming, times(1)).transform(resultSet2Pojo.resultSet2Map(resultSet));
    }

    @Test
    public void testResultSet2Map() throws SQLException {
        when(resultSet.getMetaData()).thenReturn(resultSetMetaData);
        when(resultSetMetaData.getColumnCount()).thenReturn(1);
        when(resultSetMetaData.getColumnName(1)).thenReturn(KEY);
        when(resultSet.getObject(1)).thenReturn(VALUE);
        Map<String, Object> actualMap = resultSet2Pojo.resultSet2Map(resultSet);
        assertEquals(EXPECTED_MAP, actualMap);
        verify(resultSet, times(1)).getObject(anyInt());
        verify(resultSetMetaData, times(1)).getColumnCount();
        verify(resultSetMetaData, times(1)).getColumnName(anyInt());
    }

    @Test(expected = SQLException.class)
    public void testSneakyThrownSqlException() throws SQLException {
        when(resultSet.getMetaData()).thenThrow(new SQLException());
        resultSet2Pojo.resultSet2Map(resultSet);
    }

}
