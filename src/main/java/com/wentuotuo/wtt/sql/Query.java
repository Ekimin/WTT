//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.lang.DataElement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface Query {
    int getId();

    Connection getConnection();

    int getResultSetType();

    int getResultSetConcurrency();

    String getOriginalSql();

    String getFineSql();

    int getSqlType();

    String[] getParameters();

    int getParameterNumber();

    Query setMaxRows(int var1);

    int getMaxRows();

    Query setFetchSize(int var1);

    int getFetcheSize();

    Query setParameter(DataElement var1) throws SQLException;

    Query setParameter(String var1, int var2) throws SQLException;

    Query setParameter(String var1, long var2) throws SQLException;

    Query setParameter(String var1, double var2) throws SQLException;

    Query setParameter(String var1, String var2) throws SQLException;

    Query setParameter(String var1, Date var2) throws SQLException;

    Query setParameter(String var1, boolean var2) throws SQLException;

    ResultSet getResultSet() throws SQLException;

    List getRecordList() throws SQLException;

    int executeUpdate() throws SQLException;

    boolean isClosed();

    void close();
}
