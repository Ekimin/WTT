//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.metadata;

import java.sql.SQLException;
import java.util.SortedMap;
import java.util.TreeMap;

public class DefaultDataSourceMetaData extends DefaultMetaDataObject implements DataSourceMetaData {
    private String encoding = "GBK";
    private String productName;
    private String productVersion;
    private String providerName;
    private SortedMap tables = new TreeMap();
    private SortedMap columns = new TreeMap();

    public DefaultDataSourceMetaData() {
    }

    public DefaultDataSourceMetaData(String var1, String var2) {
        super(var1);
        this.encoding = var2;
    }

    public ColumnMetaData[] getColumns() throws SQLException {
        return (ColumnMetaData[])this.columns.values().toArray();
    }

    public String getEncoding() {
        return this.encoding;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getProductVersion() {
        return this.productVersion;
    }

    public String getProviderName() {
        return this.providerName;
    }

    public TableMetaData[] getTables() throws SQLException {
        return (TableMetaData[])this.tables.values().toArray(new TableMetaData[0]);
    }

    public void setEncoding(String var1) {
        this.encoding = var1;
    }

    public TableMetaData getTable(String var1) throws SQLException {
        TableMetaData var2 = (TableMetaData)this.tables.get(var1.toUpperCase());
        if(var2 == null) {
            throw new SQLException("Object not exists: " + var1);
        } else {
            return var2;
        }
    }

    public ColumnMetaData getColumn(String var1, String var2) throws SQLException {
        ColumnMetaData var3 = (ColumnMetaData)this.columns.get(var1.toUpperCase() + "." + var2.toUpperCase());
        if(var3 == null) {
            throw new SQLException("Object not exists: " + var1 + "." + var2);
        } else {
            return var3;
        }
    }

    public void setProductName(String var1) {
        this.productName = var1;
    }

    public void setProductVersion(String var1) {
        this.productVersion = var1;
    }

    public void setProviderName(String var1) {
        this.providerName = var1;
    }

    public void addTable(TableMetaData var1) {
        this.tables.put(var1.getName().toUpperCase(), var1);
        String var2 = var1.getName().toUpperCase() + ".";
        ColumnMetaData[] var3 = var1.getColumns();
        if(var3 != null) {
            for(int var4 = 0; var4 < var3.length; ++var4) {
                this.columns.put(var2 + var3[var4].getName().toUpperCase(), var3[var4]);
            }
        }

    }
}
