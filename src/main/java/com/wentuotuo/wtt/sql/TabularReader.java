//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.util.Property;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

public class TabularReader {
    private int fetchSize = 0;
    private int maxRows = 0;
    private DataSourceURI dataSource = null;
    private Connection dbConnection = null;
    private NDBConnection ndbConnection = null;
    private String querySql = null;
    private ArrayList resultSets = new ArrayList();
    private int bufferSize = 0;
    private ResultSet internalResultSet = null;

    public TabularReader(DataSourceURI var1) {
        this.dataSource = var1;
        this.querySql = var1.getQuerySql();
    }

    public TabularReader(String var1) throws URISyntaxException {
        DataSourceURI var2 = new DataSourceURI(var1);
        this.dataSource = var2;
        this.querySql = var2.getQuerySql();
    }

    public ResultSet getResultSet() throws SQLException {
        Object var1 = null;
        if(this.dataSource != null) {
            if(this.dataSource.getDataSourceType().equals("db")) {
                if(this.dbConnection == null || this.dbConnection.isClosed()) {
                    this.dbConnection = DBConnectionFactory.getConnection(this.dataSource.getDatabase());
                }

                Statement var2 = this.dbConnection.createStatement();
                var2.setFetchSize(this.getFetchSize());
                var2.setMaxRows(this.getMaxRows());
                var1 = var2.executeQuery(this.querySql);
            } else {
                if(this.ndbConnection == null) {
                    this.ndbConnection = NDBConnection.getConnecion(this.dataSource.getDatabase(), this.dataSource.getDataSeparator());
                }

                if(this.bufferSize != 0) {
                    this.ndbConnection.setBufferSize(this.bufferSize);
                }

                var1 = this.ndbConnection.getResultSet(this.querySql, this.dataSource.getDataFile());
            }
        }

        if(var1 != null) {
            this.resultSets.add(var1);
        }

        return (ResultSet)var1;
    }

    public void close() {
        this.ndbConnection = null;
        Iterator var1 = this.resultSets.iterator();

        while(var1.hasNext()) {
            ResultSet var2 = (ResultSet)var1.next();
            if(var2 != null) {
                try {
                    Statement var3 = null;
                    if(this.dataSource.getDataSourceType().equals("db")) {
                        var3 = var2.getStatement();
                    }

                    var2.close();
                    if(var3 != null) {
                        var3.close();
                    }
                } catch (SQLException var5) {
                    WTT.getLog().trace(var5);
                }
            }

            try {
                var1.remove();
            } catch (RuntimeException var6) {
                WTT.getLog().trace(var6);
            }
        }

        if(this.dbConnection != null) {
            try {
                if(!this.dbConnection.isClosed()) {
                    this.dbConnection.close();
                }
            } catch (SQLException var4) {
                WTT.getLog().debug(var4);
            }

            this.dbConnection = null;
        }

        this.internalResultSet = null;
    }

    public Properties getProperties(int var1, int var2) throws SQLException {
        Properties var3 = new Properties();
        ResultSet var4 = this.getResultSet();

        while(var4.next()) {
            String var5 = var4.getString(var1);
            String var6 = var4.getString(var2);
            if(var5 != null && var6 != null) {
                var3.setProperty(var5, var6);
            }
        }

        return var3;
    }

    public List getPropertyList(int var1, int var2, byte var3) throws SQLException {
        Vector var4 = new Vector();
        ResultSet var5 = this.getResultSet();

        while(var5.next()) {
            Object var6 = var5.getObject(var1);
            Object var7 = var5.getObject(var2);
            var4.add(new Property(var6, var7, var3));
        }

        return var4;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setBufferSize(int var1) {
        this.bufferSize = var1;
    }

    public boolean next() throws SQLException {
        if(this.internalResultSet == null) {
            this.internalResultSet = this.getResultSet();
        }

        return this.internalResultSet.next();
    }

    public String getString(int var1) throws SQLException {
        return this.internalResultSet.getString(var1);
    }

    public String getString(String var1) throws SQLException {
        return this.internalResultSet.getString(var1);
    }

    public int getInt(int var1) throws SQLException {
        return this.internalResultSet.getInt(var1);
    }

    public int getInt(String var1) throws SQLException {
        return this.internalResultSet.getInt(var1);
    }

    public short getShort(int var1) throws SQLException {
        return this.internalResultSet.getShort(var1);
    }

    public short getShort(String var1) throws SQLException {
        return this.internalResultSet.getShort(var1);
    }

    public long getLong(int var1) throws SQLException {
        return this.internalResultSet.getLong(var1);
    }

    public long getLong(String var1) throws SQLException {
        return this.internalResultSet.getLong(var1);
    }

    public byte getByte(String var1) throws SQLException {
        return this.internalResultSet.getByte(var1);
    }

    public byte getByte(int var1) throws SQLException {
        return this.internalResultSet.getByte(var1);
    }

    public double getDouble(int var1) throws SQLException {
        return this.internalResultSet.getDouble(var1);
    }

    public double getDouble(String var1) throws SQLException {
        return this.internalResultSet.getDouble(var1);
    }

    public float getFloat(int var1) throws SQLException {
        return this.internalResultSet.getFloat(var1);
    }

    public float getFloat(String var1) throws SQLException {
        return this.internalResultSet.getFloat(var1);
    }

    public Date getDate(int var1) throws SQLException {
        return this.internalResultSet.getDate(var1);
    }

    public Date getDate(String var1) throws SQLException {
        return this.internalResultSet.getDate(var1);
    }

    public Time getTime(int var1) throws SQLException {
        return this.internalResultSet.getTime(var1);
    }

    public Time getTime(String var1) throws SQLException {
        return this.internalResultSet.getTime(var1);
    }

    public Timestamp getTimestamp(int var1) throws SQLException {
        return this.internalResultSet.getTimestamp(var1);
    }

    public Timestamp getTimestamp(String var1) throws SQLException {
        return this.internalResultSet.getTimestamp(var1);
    }

    public BigDecimal getBigDecimal(int var1) throws SQLException {
        return this.internalResultSet.getBigDecimal(var1);
    }

    public BigDecimal getBigDecimal(String var1) throws SQLException {
        return this.internalResultSet.getBigDecimal(var1);
    }

    public boolean getBoolean(int var1) throws SQLException {
        return this.internalResultSet.getBoolean(var1);
    }

    public boolean getBoolean(String var1) throws SQLException {
        return this.internalResultSet.getBoolean(var1);
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        if(this.internalResultSet == null) {
            this.internalResultSet = this.getResultSet();
        }

        return this.internalResultSet.getMetaData();
    }

    public boolean wasNull() throws SQLException {
        return this.internalResultSet.wasNull();
    }

    public int getFetchSize() {
        return this.fetchSize;
    }

    public void setFetchSize(int var1) {
        this.fetchSize = var1;
    }

    public int getMaxRows() {
        return this.maxRows;
    }

    public void setMaxRows(int var1) {
        this.maxRows = var1;
    }
}
