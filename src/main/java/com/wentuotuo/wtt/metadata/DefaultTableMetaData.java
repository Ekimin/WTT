//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.metadata;

import com.wentuotuo.wtt.sql.SQLConstants;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DefaultTableMetaData extends DefaultMetaDataObject implements TableMetaData {
    private String type;
    private DefaultColumnMetaData[] columns;
    private int columnCount = 0;
    private DataSourceMetaData dataSource;
    private String[] columnNames;
    private int[] columnIndexes;
    private boolean findContextInited = false;
    private DefaultColumnMetaData[] primaryKey = null;

    public DefaultTableMetaData(String var1, String var2, DefaultColumnMetaData[] var3) {
        super(var1);
        this.type = var2;
        this.columnCount = var3.length;
        this.columns = new DefaultColumnMetaData[this.columnCount];

        for(int var4 = 0; var4 < this.columnCount; ++var4) {
            var3[var4].setTable(this);
            this.columns[var3[var4].getIndex() - 1] = var3[var4];
        }

    }

    public DefaultTableMetaData(ResultSetMetaData var1) throws SQLException {
        super(var1.getTableName(1));
        this.type = "TABLE";
        this.setLabel(var1.getSchemaName(1));
        this.columnCount = var1.getColumnCount();
        this.columns = new DefaultColumnMetaData[this.columnCount];

        for(int var2 = 0; var2 < this.columnCount; ++var2) {
            int var3 = var2 + 1;
            this.columns[var2] = new DefaultColumnMetaData(var3, var1.getColumnName(var3), var1.getColumnType(var3), var1.getColumnLabel(var3), var1.getPrecision(var3), var1.getScale(var3), var1.getColumnDisplaySize(var3));
            this.columns[var2].setTable(this);
            this.columns[var2].setAutoIncrement(var1.isAutoIncrement(var3));
            this.columns[var2].setCaseSensitive(var1.isCaseSensitive(var3));
            this.columns[var2].setNullable(var1.isNullable(var3) == 1);
            this.columns[var2].setSearchable(var1.isSearchable(var3));
            this.columns[var2].setReadOnly(var1.isReadOnly(var3));
            this.columns[var2].setSchemaName(var1.getSchemaName(var3));
            if(SQLConstants.SQLTypeToBaseType(this.columns[var2].getType()) == 16) {
                this.columns[var2].setFormat("yyyy/MM/dd");
            }
        }

    }

    public ColumnMetaData[] getColumns() {
        return this.columns;
    }

    public ColumnMetaData getColumn(int var1) {
        return this.columns[var1 - 1];
    }

    public ColumnMetaData getColumn(String var1) {
        return this.getColumn(this.findColumn(var1));
    }

    public String getType() {
        return this.type;
    }

    public int getColumnCount() {
        return this.columnCount;
    }

    public DataSourceMetaData getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSourceMetaData var1) {
        this.dataSource = var1;
    }

    public void setType(String var1) {
        this.type = var1;
    }

    public int findColumn(String var1) {
        if(!this.findContextInited) {
            this.initFindColumnContext();
        }

        int var2 = Arrays.binarySearch(this.columnNames, var1.toUpperCase());
        return var2 >= 0 && var2 <= this.columnCount?this.columnIndexes[var2] + 1:var2;
    }

    private void initFindColumnContext() {
        this.columnNames = new String[this.columnCount];
        this.columnIndexes = new int[this.columnCount];

        int var1;
        for(var1 = 0; var1 < this.columnCount; ++var1) {
            this.columnNames[var1] = this.columns[var1].getName().toUpperCase();
        }

        Arrays.sort(this.columnNames);

        for(var1 = 0; var1 < this.columnCount; this.columnIndexes[Arrays.binarySearch(this.columnNames, this.columns[var1].getName().toUpperCase())] = var1++) {
            ;
        }

        this.findContextInited = true;
    }

    public ColumnMetaData[] getPrimaryKey() {
        if(this.primaryKey == null) {
            ArrayList var1 = new ArrayList();

            for(int var2 = 0; var2 < this.columns.length; ++var2) {
                if(this.columns[var2].isPrimaryKey()) {
                    var1.add(this.columns[var2]);
                }
            }

            this.primaryKey = (DefaultColumnMetaData[])var1.toArray(new DefaultColumnMetaData[0]);
        }

        return this.primaryKey;
    }
}
