//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.metadata;

import java.sql.SQLException;

public interface DataSourceMetaData extends MetaDataObject {
    String getProductName();

    String getProductVersion();

    String getProviderName();

    String getEncoding();

    TableMetaData[] getTables() throws SQLException;

    TableMetaData getTable(String var1) throws SQLException;

    ColumnMetaData[] getColumns() throws SQLException;

    ColumnMetaData getColumn(String var1, String var2) throws SQLException;
}
