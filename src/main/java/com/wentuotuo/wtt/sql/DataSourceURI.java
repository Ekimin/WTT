//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.log.Log;
import com.wentuotuo.wtt.metadata.DataSourceMetaData;
import com.wentuotuo.wtt.metadata.DefaultTableMetaData;
import com.wentuotuo.wtt.metadata.TableMetaData;
import java.net.URISyntaxException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DataSourceURI {
    public static final String DATA_SOURCE = "datasource";
    public static final String DATA_SOURCE_TYPE_DB = "db";
    public static final String DATA_SOURCE_TYPE_NDB = "ndb";
    private String schema;
    private String dataSourceType;
    private String database;
    private String querySql;
    private String dataSeparator;
    private String dataFile;
    private String uriString;
    private TableMetaData metaData;

    public DataSourceURI(String var1, String var2) throws URISyntaxException {
        this("db", var1, var2, (String)null, (String)null);
    }

    public DataSourceURI(String var1, String var2, String var3) throws URISyntaxException {
        this("ndb", var1, var2, var3, (String)null);
    }

    public DataSourceURI(String var1, String var2, String var3, String var4) throws URISyntaxException {
        this("ndb", var1, var2, var3, var4);
    }

    public DataSourceURI(String var1, String var2, String var3, String var4, String var5) throws URISyntaxException {
        this.schema = "datasource";
        this.dataSourceType = null;
        this.database = null;
        this.querySql = null;
        this.dataSeparator = ",";
        this.dataFile = null;
        this.uriString = null;
        this.metaData = null;
        this.dataSourceType = var1;
        if(this.dataSourceType != null && (this.dataSourceType.equals("db") || this.dataSourceType.equals("ndb"))) {
            this.database = var2;
            if(var2 == null) {
                throw new URISyntaxException(var2, "database error!");
            } else {
                this.querySql = var3;
                if(this.querySql == null) {
                    throw new URISyntaxException(var3, "sql error!");
                } else {
                    String var6 = var3.substring(0, 6).toLowerCase();
                    if(!var6.equals("select")) {
                        throw new URISyntaxException(var3, "sql error!");
                    } else {
                        this.dataFile = var4;
                        if(this.dataSourceType.equals("ndb") && this.dataFile == null) {
                            throw new URISyntaxException(this.dataFile, "data file error!");
                        } else {
                            this.dataSeparator = var5;
                        }
                    }
                }
            }
        } else {
            throw new URISyntaxException(this.dataSourceType, "dataSourceType error!");
        }
    }

    public DataSourceURI(String var1) throws URISyntaxException {
        this.schema = "datasource";
        this.dataSourceType = null;
        this.database = null;
        this.querySql = null;
        this.dataSeparator = ",";
        this.dataFile = null;
        this.uriString = null;
        this.metaData = null;
        if(var1 == null) {
            throw new URISyntaxException(var1, "uri is null!");
        } else {
            var1 = var1.replaceAll("[\\s]+", " ");
            this.parseURI(var1);
            this.uriString = var1;
        }
    }

    private void parseURI(String var1) throws URISyntaxException {
        String[] var2 = var1.split(":", -1);
        if(var2.length < 4) {
            throw new URISyntaxException(var1, "too few elements!");
        } else if(!var2[0].equals("datasource")) {
            throw new URISyntaxException(var1, "scheme must be 'datasource'!");
        } else if(!var2[1].equals("db") && !var2[1].equals("ndb")) {
            throw new URISyntaxException(var1, "type must be 'db' or 'ndb' !");
        } else {
            this.dataSourceType = var2[1];
            this.database = var2[2];
            String var3 = var2[3].substring(0, 6).toLowerCase();
            if(!var3.equals("select")) {
                throw new URISyntaxException(var1, "not a query sql!");
            } else {
                this.querySql = var2[3];
                if(this.dataSourceType.equalsIgnoreCase("ndb")) {
                    if(var2.length < 5) {
                        throw new URISyntaxException(var1, "ndb must have a data file element!");
                    }

                    this.dataFile = var2[4].replace('|', ':');
                    if(var2.length > 5) {
                        this.dataSeparator = var2[5];
                    }
                }

            }
        }
    }

    public String getDatabase() {
        return this.database;
    }

    public String getDataSourceType() {
        return this.dataSourceType;
    }

    public String getDataFile() {
        return this.dataFile;
    }

    public String getDataUrl() {
        return this.getDataFile();
    }

    public String getQuerySql() {
        return this.querySql;
    }

    public String getSchema() {
        return this.schema;
    }

    public String getDataSeparator() {
        return this.dataSeparator;
    }

    public String getURIString() {
        if(this.uriString == null) {
            this.resetURIString();
        }

        return this.uriString;
    }

    private void resetURIString() {
        StringBuffer var1 = new StringBuffer("datasource");
        var1.append(this.dataSourceType).append(":").append(this.database).append(":").append(this.querySql);
        if(this.dataSourceType.equalsIgnoreCase("ndb")) {
            var1.append(":").append(this.dataFile == null?"":this.dataFile.replace(':', '|'));
            var1.append(":").append(this.dataSeparator == null?"":this.dataSeparator);
        }

        this.uriString = var1.toString();
    }

    public TableMetaData getMetaData() {
        if(this.metaData == null) {
            try {
                this.metaData = this.createMetaData();
            } catch (SQLException var2) {
                WTT.getLog().debug(var2);
            }
        }

        return this.metaData;
    }

    private TableMetaData createMetaData() throws SQLException {
        TabularReader var1 = null;
        Log var2 = WTT.getLog();
        Object var3 = null;

        try {
            String var4;
            String var5;
            if(this.getDataSourceType().equalsIgnoreCase("db")) {
                var4 = this.getQuerySql();
                var5 = null;
                var4 = var4.replaceAll("\\s+[oO][dD][eE][rR]\\s+[bB][yY]\\s+.*$", "");
                Pattern var6 = Pattern.compile("\\s+[gG][rR][oO][uU][pP]\\s+[bB][yY]\\s+");
                Matcher var7 = var6.matcher(var4);
                if(var7.find()) {
                    var5 = var4.substring(var7.start());
                    var4 = var4.substring(0, var7.start());
                }

                var4 = var4.replaceAll("\\s+[wW][hH][eE][rR][eE]\\s+.*$", "");
                var4 = var4 + " where 1=2 " + (var5 == null?"":var5);
                if(var2.isDebugEnabled()) {
                    var2.debug("MetadataSql: " + var4);
                }

                DataSourceURI var8 = new DataSourceURI(this.getDatabase(), var4);
                var1 = new TabularReader(var8);
                ResultSetMetaData var9 = var1.getMetaData();
                var3 = new DefaultTableMetaData(var9);
            } else {
                var4 = this.getDatabase();
                var5 = this.getQuerySql();
                String[] var16 = var5.split("\\s");
                String var17 = var16[var16.length - 1];
                DataSourceMetaData var18 = WTT.getMetaData(var4);
                if(var18 == null) {
                    throw new SQLException("DataBase " + var4 + "not exists!");
                }

                var3 = var18.getTable(var17);
                if(var3 == null) {
                    throw new SQLException("Table " + var17 + "not exists!");
                }
            }
        } catch (URISyntaxException var14) {
            throw new SQLException("prh.recordSet syntax error." + var14.getMessage());
        } finally {
            if(var1 != null) {
                var1.close();
                var1 = null;
            }

        }

        return (TableMetaData)var3;
    }
}
