//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.metadata.ColumnMetaData;
import com.wentuotuo.wtt.metadata.DataSourceMetaData;
import com.wentuotuo.wtt.metadata.MetaDataFactory;
import com.wentuotuo.wtt.metadata.TableMetaData;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;

public class NDBConnection {
    private DataSourceMetaData dataSourceMetaData;
    private int bufferSize;
    private String encoding;
    private String separator;

    public static NDBConnection getConnecion(String var0) throws SQLException {
        return new NDBConnection(MetaDataFactory.getMetaData(var0));
    }

    public static NDBConnection getConnecion(String var0, String var1) throws SQLException {
        return new NDBConnection(MetaDataFactory.getMetaData(var0), var1, 0);
    }

    public NDBConnection(DataSourceMetaData var1) throws SQLException {
        this(var1, ",", 0);
    }

    public NDBConnection(DataSourceMetaData var1, String var2, int var3) throws SQLException {
        this.bufferSize = 0;
        this.encoding = "GBK";
        this.separator = ",";
        if(var1 == null) {
            throw new SQLException("Connect with null metadata.");
        } else {
            this.dataSourceMetaData = var1;
            this.bufferSize = var3;
            this.separator = var2;
            String var4 = this.dataSourceMetaData.getEncoding();
            if(var4 != null && !var4.equalsIgnoreCase("")) {
                this.encoding = var4;
            }

        }
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public DataSourceMetaData getDataSourceMetaData() {
        return this.dataSourceMetaData;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public String getSeparator() {
        return this.separator;
    }

    public void setBufferSize(int var1) {
        this.bufferSize = var1;
    }

    public void setDataSourceMetaData(DataSourceMetaData var1) {
        this.dataSourceMetaData = var1;
    }

    public void setEncoding(String var1) {
        this.encoding = var1;
    }

    public void setSeparator(String var1) {
        this.separator = var1;
    }

    public BufferedReaderResultSet getResultSet(String var1, String var2, Object var3) throws SQLException {
        ColumnMetaData[] var4 = this.getSelectedColumns(var1, var2);
        BufferedReader var5 = this.getReader(var3);
        BufferedReaderResultSetMetaData var6 = new BufferedReaderResultSetMetaData(var4, this.separator, this.getEncoding());
        return new BufferedReaderResultSet(var5, var6);
    }

    public BufferedReaderResultSet getResultSet(String var1, int[] var2, Object var3) throws SQLException {
        ColumnMetaData[] var4 = this.getSelectedColumns(var1, var2);
        BufferedReader var5 = this.getReader(var3);
        BufferedReaderResultSetMetaData var6 = new BufferedReaderResultSetMetaData(var4, this.separator, this.getEncoding());
        return new BufferedReaderResultSet(var5, var6);
    }

    public BufferedReaderResultSet getResultSet(String var1, String[] var2, Object var3) throws SQLException {
        ColumnMetaData[] var4 = this.getSelectedColumns(var1, var2);
        BufferedReader var5 = this.getReader(var3);
        BufferedReaderResultSetMetaData var6 = new BufferedReaderResultSetMetaData(var4, this.separator, this.getEncoding());
        return new BufferedReaderResultSet(var5, var6);
    }

    public BufferedReaderResultSet getResultSet(String var1, Object var2) throws SQLException {
        SQLAnalyzer var5 = new SQLAnalyzer(var1);
        var5.doAnalyse();
        if(var5.getSqlType() != 0) {
            throw new SQLException("Not a query statement: " + var1);
        } else {
            String[] var6 = var5.getSqlWords();
            String var3 = var6[3];
            String var4 = var6[1];
            ColumnMetaData[] var7 = this.getSelectedColumns(var3, var4);
            BufferedReader var8 = this.getReader(var2);
            BufferedReaderResultSetMetaData var9 = new BufferedReaderResultSetMetaData(var7, this.separator, this.getEncoding());
            return new BufferedReaderResultSet(var8, var9);
        }
    }

    private BufferedReader getReader(Object var1) throws SQLException {
        BufferedReader var2 = null;
        if(var1 == null) {
            throw new SQLException("Data is null");
        } else {
            if(var1 instanceof BufferedReader) {
                var2 = (BufferedReader)var1;
            } else if(var1 instanceof InputStream) {
                try {
                    if(this.bufferSize == 0) {
                        var2 = new BufferedReader(new InputStreamReader((InputStream)var1, this.encoding));
                    } else {
                        var2 = new BufferedReader(new InputStreamReader((InputStream)var1, this.encoding), this.bufferSize);
                    }
                } catch (UnsupportedEncodingException var10) {
                    throw new SQLException("Encoding not supported: " + this.encoding);
                }
            } else {
                FileInputStream var3;
                if(var1 instanceof String) {
                    var3 = null;

                    try {
                        var3 = new FileInputStream((String)var1);
                        if(this.bufferSize == 0) {
                            var2 = new BufferedReader(new InputStreamReader(var3, this.encoding));
                        } else {
                            var2 = new BufferedReader(new InputStreamReader(var3, this.encoding), this.bufferSize);
                        }
                    } catch (FileNotFoundException var8) {
                        new SQLException("File not found: " + var1);
                    } catch (UnsupportedEncodingException var9) {
                        throw new SQLException("Encoding not supported: " + this.encoding);
                    }
                } else {
                    if(!(var1 instanceof URL)) {
                        throw new SQLException("Data invalide: " + var1);
                    }

                    var3 = null;

                    try {
                        InputStream var11 = ((URL)var1).openStream();
                        if(this.bufferSize == 0) {
                            var2 = new BufferedReader(new InputStreamReader(var11, this.encoding));
                        } else {
                            var2 = new BufferedReader(new InputStreamReader(var11, this.encoding), this.bufferSize);
                        }
                    } catch (FileNotFoundException var5) {
                        new SQLException("File not found: " + var1);
                    } catch (UnsupportedEncodingException var6) {
                        throw new SQLException("Encoding not supported: " + this.encoding);
                    } catch (IOException var7) {
                        throw new SQLException("URL Inputstream error: " + var1);
                    }
                }
            }

            return var2;
        }
    }

    private ColumnMetaData[] getSelectedColumns(String var1, int[] var2) throws SQLException {
        ColumnMetaData[] var3 = null;
        TableMetaData var4 = this.dataSourceMetaData.getTable(var1);
        if(var4 == null) {
            throw new SQLException("Table not found!");
        } else {
            ColumnMetaData[] var5 = var4.getColumns();
            if(var2 == null) {
                var3 = var5;
            } else {
                var3 = new ColumnMetaData[var2.length];

                for(int var6 = 0; var6 < var3.length; ++var6) {
                    var3[var6] = var5[var2[var6] - 1];
                }
            }

            return var3;
        }
    }

    private ColumnMetaData[] getSelectedColumns(String var1, String[] var2) throws SQLException {
        ColumnMetaData[] var3 = null;
        TableMetaData var4 = this.dataSourceMetaData.getTable(var1);
        if(var4 == null) {
            throw new SQLException("Table not found!");
        } else {
            if(var2 == null) {
                var3 = var4.getColumns();
            } else {
                var3 = new ColumnMetaData[var2.length];

                for(int var5 = 0; var5 < var3.length; ++var5) {
                    var3[var5] = var4.getColumn(var2[var5]);
                }
            }

            return var3;
        }
    }

    private ColumnMetaData[] getSelectedColumns(String var1, String var2) throws SQLException {
        if(var2 != null && !var2.equals("*") && !var2.equals("")) {
            String[] var3 = var2.split(",");
            Object var4 = null;
            int[] var8 = new int[var3.length];

            for(int var5 = 0; var5 < var3.length; ++var5) {
                try {
                    var8[var5] = Integer.parseInt(var3[var5]);
                } catch (Exception var7) {
                    return this.getSelectedColumns(var1, var3);
                }
            }

            return this.getSelectedColumns(var1, var8);
        } else {
            return this.getSelectedColumns(var1, (int[])null);
        }
    }
}
