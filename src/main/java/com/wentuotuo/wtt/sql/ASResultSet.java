//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.util.SpecialTools;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ASResultSet {
    public ResultSet rs;
    public int iColumnCount;
    public int iRowCount;
    public ResultSetMetaData rsmd;
    public int iChange;

    public ASResultSet(ResultSet var1) throws Exception {
        this.rs = var1;
        this.rsmd = var1.getMetaData();
        this.iColumnCount = this.rsmd.getColumnCount();
        this.iChange = 0;
    }

    public ASResultSet(int var1, ResultSet var2) throws Exception {
        this.rs = var2;
        this.rsmd = var2.getMetaData();
        this.iColumnCount = this.rsmd.getColumnCount();
        this.iChange = var1;
    }

    public void close() throws Exception {
        this.rs.getStatement().close();
    }

    public Statement getStatement() throws Exception {
        return this.rs.getStatement();
    }

    public int getColumnCount() throws Exception {
        return this.iColumnCount;
    }

    public int getRowCount() throws Exception {
        int var1 = this.rs.getRow();
        this.rs.last();
        this.iRowCount = this.rs.getRow();
        if(var1 <= 0) {
            this.rs.beforeFirst();
        } else {
            this.rs.absolute(var1);
        }

        return this.iRowCount;
    }

    public String getColumnName(int var1) throws Exception {
        return this.rsmd.getColumnName(var1);
    }

    public int getColumnIndex(String var1) throws Exception {
        int var2;
        for(var2 = 1; var2 <= this.iColumnCount && !var1.equals(this.rsmd.getColumnName(var2)); ++var2) {
            ;
        }

        if(var2 > this.iColumnCount) {
            var2 = 0;
        }

        return var2;
    }

    public int getColumnType(int var1) throws Exception {
        return this.rsmd.getColumnType(var1);
    }

    public String getColumnTypeName(int var1) throws Exception {
        return this.rsmd.getColumnTypeName(var1);
    }

    public void beforeFirst() throws Exception {
        this.rs.beforeFirst();
    }

    public boolean first() throws Exception {
        return this.rs.first();
    }

    public boolean last() throws Exception {
        return this.rs.last();
    }

    public boolean next() throws Exception {
        return this.rs.next();
    }

    public int getRow() throws Exception {
        return this.rs.getRow();
    }

    public boolean wasNull() throws Exception {
        return this.rs.wasNull();
    }

    public String getStringValue(String var1) throws Exception {
        return this.getStringValue(this.getColumnIndex(var1));
    }

    public String getStringValue(int var1) throws Exception {
        String var2 = "";
        int var3 = this.rsmd.getColumnType(var1);
        if(var3 != -7 && var3 != -6 && var3 != 5 && var3 != 4 && var3 != -5) {
            if(var3 != 6 && var3 != 7 && var3 != 8 && var3 != 3 && var3 != 2) {
                if(var3 != 1 && var3 != 12 && var3 != -1) {
                    if(var3 == 91) {
                        var2 = this.rs.getDate(var1).toString();
                    } else if(var3 == 92) {
                        var2 = this.rs.getTime(var1).toString();
                    } else if(var3 == 93) {
                        var2 = this.rs.getTimestamp(var1).toString();
                    }
                } else {
                    var2 = this.rs.getString(var1);
                    var2 = StringX.trimEnd(var2);
                    if(this.iChange == 1 && var2 != null) {
                        try {
                            var2 = new String(var2.getBytes("ISO8859_1"), "GBK");
                        } catch (UnsupportedEncodingException var8) {
                            throw new SQLException("UnsupportedEncodingException");
                        }
                    } else if(this.iChange == 3 && var2 != null) {
                        try {
                            var2 = new String(var2.getBytes("GBK"), "ISO8859_1");
                        } catch (UnsupportedEncodingException var7) {
                            throw new SQLException("UnsupportedEncodingException");
                        }
                    } else if(this.iChange == 11 && var2 != null) {
                        try {
                            var2 = new String(var2.getBytes(), "UTF-8");
                        } catch (UnsupportedEncodingException var6) {
                            throw new SQLException("UnsupportedEncodingException");
                        }
                    }
                }
            } else {
                double var4 = this.rs.getDouble(var1);
                if(this.rs.wasNull()) {
                    var2 = "";
                } else {
                    var2 = String.valueOf(var4);
                }
            }
        } else {
            var2 = String.valueOf(this.rs.getLong(var1));
        }

        if(this.rs.wasNull()) {
            var2 = "";
        }

        return var2;
    }

    public String getString(int var1) throws Exception {
        String var2 = this.rs.getString(var1);
        var2 = StringX.trimEnd(var2);
        String var3 = this.getStatement().getConnection().getMetaData().getDatabaseProductName();
        if(var3 != null && var3.equalsIgnoreCase("Informix Dynamic Server")) {
            var2 = SpecialTools.informix2DB(var2);
        }

        if(var2 != null && var2.equals(" ")) {
            return "";
        } else {
            if(this.iChange == 1 && var2 != null) {
                try {
                    var2 = new String(var2.getBytes("ISO8859_1"), "GBK");
                } catch (UnsupportedEncodingException var7) {
                    throw new SQLException("UnsupportedEncodingException");
                }
            }

            if(this.iChange == 3 && var2 != null) {
                try {
                    var2 = new String(var2.getBytes("GBK"), "ISO8859_1");
                } catch (UnsupportedEncodingException var6) {
                    throw new SQLException("UnsupportedEncodingException");
                }
            }

            if(this.iChange == 11 && var2 != null) {
                try {
                    var2 = new String(var2.getBytes(), "UTF-8");
                } catch (UnsupportedEncodingException var5) {
                    throw new SQLException("UnsupportedEncodingException");
                }
            }

            return var2;
        }
    }

    public String getString(String var1) throws Exception {
        String var2 = this.rs.getString(var1);
        var2 = StringX.trimEnd(var2);
        String var3 = this.getStatement().getConnection().getMetaData().getDatabaseProductName();
        if(var3 != null && var3.equalsIgnoreCase("Informix Dynamic Server")) {
            var2 = SpecialTools.informix2DB(var2);
        }

        if(var2 != null && var2.equals(" ")) {
            return "";
        } else {
            if(this.iChange == 1 && var2 != null) {
                try {
                    var2 = new String(var2.getBytes("ISO8859_1"), "GBK");
                } catch (UnsupportedEncodingException var7) {
                    throw new SQLException("UnsupportedEncodingException");
                }
            } else if(this.iChange == 3 && var2 != null) {
                try {
                    var2 = new String(var2.getBytes("GBK"), "ISO8859_1");
                } catch (UnsupportedEncodingException var6) {
                    throw new SQLException("UnsupportedEncodingException");
                }
            } else if(this.iChange == 11 && var2 != null) {
                try {
                    var2 = new String(var2.getBytes(), "UTF-8");
                } catch (UnsupportedEncodingException var5) {
                    throw new SQLException("UnsupportedEncodingException");
                }
            }

            return var2;
        }
    }

    public double getDouble(int var1) throws Exception {
        return this.rs.getDouble(var1);
    }

    public double getDouble(String var1) throws Exception {
        return this.rs.getDouble(var1);
    }

    public int getInt(int var1) throws Exception {
        return this.rs.getInt(var1);
    }

    public int getInt(String var1) throws Exception {
        return this.rs.getInt(var1);
    }

    public void toSystemString() throws Exception {
        label24:
        while(true) {
            if(this.rs.next()) {
                int var1 = 1;

                while(true) {
                    if(var1 > this.getColumnCount()) {
                        continue label24;
                    }

                    if(var1 > 1) {
                        System.out.print(",");
                    }

                    String var2 = this.getString(var1);
                    if(this.rs.wasNull()) {
                        System.out.print("null");
                    } else {
                        System.out.print(var2);
                    }

                    ++var1;
                }
            }

            return;
        }
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return this.rsmd;
    }

    public long getLong(int var1) throws SQLException {
        return this.rs.getLong(var1);
    }

    public long getLong(String var1) throws SQLException {
        return this.rs.getLong(var1);
    }

    public float getFloat(int var1) throws SQLException {
        return this.rs.getFloat(var1);
    }

    public float getFloat(String var1) throws SQLException {
        return this.rs.getFloat(var1);
    }

    public void updateDouble(int var1, double var2) throws SQLException {
        this.rs.updateDouble(var1, var2);
    }

    public void updateDouble(String var1, double var2) throws SQLException {
        this.rs.updateDouble(var1, var2);
    }

    public void updateFloat(int var1, float var2) throws SQLException {
        this.rs.updateFloat(var1, var2);
    }

    public void updateFloat(String var1, float var2) throws SQLException {
        this.rs.updateFloat(var1, var2);
    }

    public void updateString(int var1, String var2) throws SQLException, Exception {
        if(this.iChange == 1 && var2 != null) {
            try {
                var2 = new String(var2.getBytes("GBK"), "ISO8859_1");
            } catch (UnsupportedEncodingException var5) {
                throw new SQLException("UnsupportedEncodingException");
            }
        }

        if(this.iChange == 3 && var2 != null) {
            try {
                var2 = new String(var2.getBytes("ISO8859_1"), "GBK");
            } catch (UnsupportedEncodingException var4) {
                throw new SQLException("UnsupportedEncodingException");
            }
        }

        var2 = SpecialTools.wentuotuo2DB(var2);
        this.rs.updateString(var1, var2);
    }

    public void updateString(String var1, String var2) throws SQLException, Exception {
        if(this.iChange == 1 && var2 != null) {
            try {
                var2 = new String(var2.getBytes("GBK"), "ISO8859_1");
            } catch (UnsupportedEncodingException var5) {
                throw new SQLException("UnsupportedEncodingException");
            }
        }

        if(this.iChange == 3 && var2 != null) {
            try {
                var2 = new String(var2.getBytes("ISO8859_1"), "GBK");
            } catch (UnsupportedEncodingException var4) {
                throw new SQLException("UnsupportedEncodingException");
            }
        }

        var1 = SpecialTools.wentuotuo2DB(var1);
        this.rs.updateString(var1, var2);
    }

    public void updateRow() throws SQLException {
        this.rs.updateRow();
    }

    public BigDecimal getBigDecimal(int var1) throws SQLException {
        return this.rs.getBigDecimal(var1);
    }

    public BigDecimal getBigDecimal(String var1) throws SQLException {
        return this.rs.getBigDecimal(var1);
    }

    public InputStream getBinaryStream(int var1) throws SQLException {
        return this.rs.getBinaryStream(var1);
    }

    public InputStream getBinaryStream(String var1) throws SQLException {
        return this.rs.getBinaryStream(var1);
    }
}
