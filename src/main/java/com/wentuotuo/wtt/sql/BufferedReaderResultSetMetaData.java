//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.metadata.ColumnMetaData;
import com.wentuotuo.wtt.metadata.DefaultColumnMetaData;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class BufferedReaderResultSetMetaData implements ResultSetMetaData {
    private ColumnMetaData[] columns;
    private int columnCount = 0;
    private String separator = null;
    private String encoding = null;

    public BufferedReaderResultSetMetaData(ColumnMetaData[] var1, String var2, String var3) throws SQLException {
        this.columnCount = var1.length;
        this.columns = var1;
        this.separator = var2;
        this.encoding = var3;
    }

    public BufferedReaderResultSetMetaData(ColumnMetaData[] var1, String var2) throws SQLException {
        this.columnCount = var1.length;
        this.columns = var1;
        this.separator = var2;
    }

    public BufferedReaderResultSetMetaData(ResultSetMetaData var1, String var2) throws SQLException {
        this.columnCount = var1.getColumnCount();
        this.columns = new DefaultColumnMetaData[this.columnCount + 1];

        for(int var3 = 0; var3 < this.columnCount; ++var3) {
            this.columns[var3] = new DefaultColumnMetaData(var3 + 1, var1.getColumnName(var3 + 1), var1.getColumnType(var3 + 1), var1.getColumnLabel(var3 + 1), var1.getPrecision(var3 + 1), var1.getScale(var3 + 1), var1.getColumnDisplaySize(var3 + 1));
        }

        this.separator = var2;
    }

    public int getColumnCount() throws SQLException {
        return this.columnCount;
    }

    public int getColumnDisplaySize(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].getDisplaySize();
    }

    public int getColumnType(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].getType();
    }

    public int getPrecision(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].getPrecision();
    }

    public int getScale(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].getScale();
    }

    public int isNullable(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].isNullable()?0:1;
    }

    public boolean isAutoIncrement(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].isAutoIncrement();
    }

    public boolean isCaseSensitive(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].isCaseSensitive();
    }

    public boolean isCurrency(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return false;
    }

    public boolean isDefinitelyWritable(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return false;
    }

    public boolean isReadOnly(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return false;
    }

    public boolean isSearchable(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].isSearchable();
    }

    public boolean isSigned(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return false;
    }

    public boolean isWritable(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return false;
    }

    public String getCatalogName(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return null;
    }

    public String getColumnClassName(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return null;
    }

    public String getColumnLabel(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].getLabel();
    }

    public String getColumnName(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].getName();
    }

    public String getColumnTypeName(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].getTypeName();
    }

    public String getSchemaName(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].getSchemaName();
    }

    public String getTableName(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].getTable().getName();
    }

    public int getColumnIndexInTable(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].getIndex();
    }

    public String getSeparator() {
        return this.separator;
    }

    public void setSeparator(String var1) {
        this.separator = var1;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String var1) {
        this.encoding = var1;
    }

    public String getColumnFormat(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.columns[var1].getFormat();
    }

    private void checkColumn(int var1) throws SQLException {
        if(var1 < 0 || var1 >= this.columnCount) {
            throw new SQLException("BufferedResultSet: Column " + (var1 + 1) + " not found!");
        }
    }

    public Object unwrap(Class var1) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class var1) throws SQLException {
        return false;
    }
}
