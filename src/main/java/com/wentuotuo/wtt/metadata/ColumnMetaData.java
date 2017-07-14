//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.metadata;

public interface ColumnMetaData extends MetaDataObject {
    int getType();

    String getTypeName();

    int getPrecision();

    int getScale();

    int getDisplaySize();

    String getFormat();

    String getSchemaName();

    TableMetaData getTable();

    int getIndex();

    Object getDefaultValue();

    ColumnUIMetaData getDefaultUIMetaData();

    ColumnUIMetaData getUIMetaData(String var1);

    boolean isAutoIncrement();

    boolean isCaseSensitive();

    boolean isNullable();

    boolean isSearchable();

    boolean isPrimaryKey();

    boolean isReadOnly();
}
