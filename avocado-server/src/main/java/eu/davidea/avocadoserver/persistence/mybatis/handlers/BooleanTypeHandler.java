package eu.davidea.avocadoserver.persistence.mybatis.handlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanTypeHandler implements TypeHandler<Boolean> {

    private static final String TRUE = "T";
    private static final String FALSE = "F";

    @Override
    public Boolean getResult(ResultSet resultSet, String name) throws SQLException {
        return valueOf(resultSet.getString(name));
    }

    @Override
    public Boolean getResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return valueOf(resultSet.getString(columnIndex));
    }

    @Override
    public Boolean getResult(CallableStatement statement, int columnIndex) throws SQLException {
        return valueOf(statement.getString(columnIndex));
    }

    @Override
    public void setParameter(PreparedStatement statement, int columnIndex, Boolean value, JdbcType jdbcType) throws SQLException {
        statement.setString(columnIndex, value != null && value ? TRUE : FALSE);
    }

    private Boolean valueOf(String value) throws SQLException {
        if (value == null || FALSE.equalsIgnoreCase(value)) {
            return Boolean.FALSE;
        } else if (TRUE.equalsIgnoreCase(value)) {
            return Boolean.TRUE;
        } else {
            throw new SQLException("Unexpected value " + value + " found where " + TRUE + " or " + FALSE + " was expected");
        }
    }
}