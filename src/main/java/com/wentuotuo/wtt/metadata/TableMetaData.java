//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.metadata;

public interface TableMetaData extends MetaDataObject {
    String TABLE = "TABLE";
    String VIEW = "VIEW";

    ColumnMetaData[] getColumns();

    ColumnMetaData getColumn(String var1);

    ColumnMetaData getColumn(int var1);

    String getType();

    int getColumnCount();

    DataSourceMetaData getDataSource();

    ColumnMetaData[] getPrimaryKey();
}
