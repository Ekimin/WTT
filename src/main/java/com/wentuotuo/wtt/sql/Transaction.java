//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.util.ASValuePool;
import com.wentuotuo.wtt.util.DataConvert;
import com.wentuotuo.wtt.util.SpecialTools;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    public Connection conn;
    public static int iChange = 0;
    public static int iDebugMode = 1;
    public static int iStatementMode = 0;
    public static int iResultSetType = 1004;
    public static int iResultSetConcurrency = 1007;
    public static final int FETCH_FORWARD = 1000;
    public static final int FETCH_REVERSE = 1001;
    public static final int FETCH_UNKNOWN = 1002;
    public static final int TYPE_FORWARD_ONLY = 1003;
    public static final int TYPE_SCROLL_INSENSITIVE = 1004;
    public static final int TYPE_SCROLL_SENSITIVE = 1005;
    public static final int CONCUR_READ_ONLY = 1007;
    public static final int CONCUR_UPDATABLE = 1008;
    private static double WARNING_SQLTIME = 0.5D;
    private boolean LOG_EXECUTE = false;
    private boolean LOG_SELECT = false;
    private Date dBeginTime = new Date();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
    private String sDataSourceName = "";

    public void setDataSourceName(String var1) {
        this.sDataSourceName = var1;
    }

    public String getDataSourceName() {
        return this.sDataSourceName;
    }

    public Transaction(Connection var1) throws Exception {
        this.conn = var1;
        this.conn.setAutoCommit(false);
    }

    public Transaction(int var1, Connection var2) throws Exception {
        iChange = var1;
        this.conn = var2;
        this.conn.setAutoCommit(false);
    }

    public ResultSet getResultSetOld(String var1) throws Exception {
        Statement var2 = null;
        ResultSet var3 = null;

        try {
            this.setDebugTime();
            var2 = this.conn.createStatement(iResultSetType, iResultSetConcurrency);
            var3 = var2.executeQuery(var1);
            if(this.LOG_SELECT) {
                this.log(var1, "SELECT");
            }
        } catch (Exception var8) {
            WTT.getLog().error(var8.getMessage(), var8);
            if(var3 != null) {
                var3.close();
            }

            if(var2 != null) {
                var2.close();
            }

            var3 = null;
            throw var8;
        } finally {
            this.DebugSQL(var1);
        }

        return var3;
    }

    public ASResultSet getResultSet(String var1) throws Exception {
        return this.getASResultSet(var1);
    }

    public ASResultSet getASResultSet(String var1) throws Exception {
        return iStatementMode == 1?this.getASResultSetPrepare(var1, true):this.getASResultSetStatic(var1, true);
    }

    public ASResultSet getASResultSetStatic(String var1, boolean var2) throws Exception {
        if(var2) {
            if(iChange == 1 && var1 != null) {
                var1 = new String(var1.getBytes("GBK"), "ISO8859_1");
            }

            if(iChange == 3 && var1 != null) {
                var1 = new String(var1.getBytes("ISO8859_1"), "GBK");
            }

            var1 = SpecialTools.wentuotuo2DB(var1);
        }

        Statement var3 = null;
        ResultSet var4 = null;

        ASResultSet var5;
        try {
            this.setDebugTime();
            var3 = this.conn.createStatement(iResultSetType, iResultSetConcurrency);
            var4 = var3.executeQuery(var1);
            if(this.LOG_SELECT) {
                this.log(var1, "SELECT");
            }

            var5 = new ASResultSet(iChange, var4);
        } catch (Exception var9) {
            WTT.getLog().error(var9.getMessage(), var9);
            if(var4 != null) {
                var4.close();
            }

            if(var3 != null) {
                var3.close();
            }

            throw var9;
        } finally {
            this.DebugSQL(var1);
        }

        return var5;
    }

    public ASResultSet getASResultSetPrepare(String var1, boolean var2) throws Exception {
        int var3 = var1.toLowerCase().lastIndexOf("from");
        if(var3 == -1) {
            return this.getASResultSetStatic(var1, var2);
        } else {
            if(var2) {
                if(iChange == 1 && var1 != null) {
                    var1 = new String(var1.getBytes("GBK"), "ISO8859_1");
                }

                if(iChange == 3 && var1 != null) {
                    var1 = new String(var1.getBytes("ISO8859_1"), "GBK");
                }

                var1 = SpecialTools.wentuotuo2DB(var1);
            }

            PreparedStatement var4 = null;
            ResultSet var5 = null;
            StringBuffer var6 = new StringBuffer(var1.substring(0, var3 + 4));

            ASResultSet var9;
            try {
                int var17 = 0;
                this.setDebugTime();
                String[] var18 = var1.substring(var3 + 4).split("'", -1);

                int var19;
                for(var19 = 0; var19 < var18.length; var19 += 2) {
                    if(var19 <= var18.length - 2) {
                        var6.append(var18[var19]);
                        var6.append("? ");
                        ++var17;
                    } else {
                        var6.append(var18[var19]);
                    }
                }

                WTT.getLog().trace("PrepareSql[" + var6 + "]");
                var4 = this.conn.prepareStatement(var6.toString(), iResultSetType, iResultSetConcurrency);

                for(var19 = 1; var19 <= var17; ++var19) {
                    var4.setString(var19, var18[2 * var19 - 1]);
                }

                var5 = var4.executeQuery();
                if(this.LOG_SELECT) {
                    this.log(var1, "SELECT");
                }

                var9 = new ASResultSet(iChange, var5);
                return var9;
            } catch (Exception var15) {
                Exception var7 = var15;
                Statement var8 = null;

                try {
                    WTT.getLog().warn(var7.getMessage(), var7);
                    WTT.getLog().warn("PrepareSql ERR[" + var6 + "]");
                    if(var5 != null) {
                        var5.close();
                    }

                    this.setDebugTime();
                    var8 = this.conn.createStatement(iResultSetType, iResultSetConcurrency);
                    var5 = var8.executeQuery(var1);
                    if(this.LOG_SELECT) {
                        this.log(var1, "SELECT");
                    }

                    var9 = new ASResultSet(iChange, var5);
                } catch (Exception var14) {
                    System.out.println(var14.getMessage());
                    if(var5 != null) {
                        var5.close();
                    }

                    if(var4 != null) {
                        var4.close();
                    }

                    if(var8 != null) {
                        var8.close();
                    }

                    throw var14;
                }
            } finally {
                this.DebugSQL(var1);
            }

            return var9;
        }
    }

    public int executeSQL(String var1) throws Exception {
        var1 = this.convertAmarStr2DBStr(var1);
        Statement var2 = null;

        int var4;
        try {
            this.setDebugTime();
            var2 = this.conn.createStatement(iResultSetType, iResultSetConcurrency);
            int var3 = var2.executeUpdate(var1);
            if(this.LOG_EXECUTE) {
                this.log(var1, "EXECUTE");
            }

            var2.close();
            var4 = var3;
        } catch (Exception var8) {
            WTT.getLog().error(var8.getMessage(), var8);
            if(var2 != null) {
                var2.close();
            }

            throw var8;
        } finally {
            this.DebugSQL(var1);
        }

        return var4;
    }

    public int executeSQLWithoutLog(String var1) throws Exception {
        var1 = this.convertAmarStr2DBStr(var1);
        Statement var2 = null;

        int var4;
        try {
            this.setDebugTime();
            var2 = this.conn.createStatement(iResultSetType, iResultSetConcurrency);
            int var3 = var2.executeUpdate(var1);
            var2.close();
            var4 = var3;
        } catch (Exception var8) {
            WTT.getLog().error(var8.getMessage(), var8);
            if(var2 != null) {
                var2.close();
            }

            throw var8;
        } finally {
            this.DebugSQL(var1);
        }

        return var4;
    }

    public ResultSet getResultSet2Old(String var1) throws Exception {
        Statement var2 = null;
        ResultSet var3 = null;

        try {
            this.setDebugTime();
            var2 = this.conn.createStatement(1003, 1008);
            var3 = var2.executeQuery(var1);
            if(this.LOG_SELECT) {
                this.log(var1, "SELECT");
            }
        } catch (Exception var8) {
            WTT.getLog().error(var8.getMessage(), var8);
            if(var3 != null) {
                var3.close();
            }

            if(var2 != null) {
                var2.close();
            }

            var3 = null;
            throw var8;
        } finally {
            this.DebugSQL(var1);
        }

        return var3;
    }

    public ASResultSet getResultSet2(String var1) throws Exception {
        if(iChange == 1 && var1 != null) {
            var1 = new String(var1.getBytes("GBK"), "ISO8859_1");
        }

        if(iChange == 3 && var1 != null) {
            var1 = new String(var1.getBytes("ISO8859_1"), "GBK");
        }

        var1 = SpecialTools.wentuotuo2DB(var1);
        Statement var2 = null;
        ResultSet var3 = null;

        ASResultSet var4;
        try {
            this.setDebugTime();
            var2 = this.conn.createStatement(1003, 1008);
            var3 = var2.executeQuery(var1);
            if(this.LOG_SELECT) {
                this.log(var1, "SELECT");
            }

            var4 = new ASResultSet(iChange, var3);
        } catch (Exception var8) {
            WTT.getLog().error(var8.getMessage(), var8);
            if(var3 != null) {
                var3.close();
            }

            if(var2 != null) {
                var2.close();
            }

            throw var8;
        } finally {
            this.DebugSQL(var1);
        }

        return var4;
    }

    public ASResultSet getASResultSet2(String var1) throws Exception {
        if(iChange == 1 && var1 != null) {
            var1 = new String(var1.getBytes("GBK"), "ISO8859_1");
        }

        if(iChange == 3 && var1 != null) {
            var1 = new String(var1.getBytes("ISO8859_1"), "GBK");
        }

        var1 = SpecialTools.wentuotuo2DB(var1);
        Statement var2 = null;
        ResultSet var3 = null;

        ASResultSet var4;
        try {
            this.setDebugTime();
            var2 = this.conn.createStatement(1003, 1007);
            var3 = var2.executeQuery(var1);
            if(this.LOG_SELECT) {
                this.log(var1, "SELECT");
            }

            var4 = new ASResultSet(iChange, var3);
        } catch (Exception var8) {
            WTT.getLog().error(var8.getMessage(), var8);
            if(var3 != null) {
                var3.close();
            }

            if(var2 != null) {
                var2.close();
            }

            throw var8;
        } finally {
            this.DebugSQL(var1);
        }

        return var4;
    }

    public ASResultSet getASResultSetForUpdate(String var1) throws Exception {
        var1 = this.convertAmarStr2DBStr(var1);
        Statement var2 = null;
        ResultSet var3 = null;

        ASResultSet var4;
        try {
            this.setDebugTime();
            var2 = this.conn.createStatement(1003, 1008);
            var3 = var2.executeQuery(var1);
            if(this.LOG_SELECT) {
                this.log(var1, "SELECT");
            }

            var4 = new ASResultSet(iChange, var3);
        } catch (Exception var8) {
            WTT.getLog().error(var8.getMessage(), var8);
            if(var3 != null) {
                var3.close();
            }

            if(var2 != null) {
                var2.close();
            }

            throw var8;
        } finally {
            this.DebugSQL(var1);
        }

        return var4;
    }

    public int executeSQL2(String var1) throws Exception {
        var1 = this.convertAmarStr2DBStr(var1);
        Statement var2 = null;

        int var4;
        try {
            this.setDebugTime();
            var2 = this.conn.createStatement(1003, 1008);
            int var3 = var2.executeUpdate(var1);
            if(this.LOG_EXECUTE) {
                this.log(var1, "EXECUTE");
            }

            var2.close();
            var4 = var3;
        } catch (Exception var8) {
            WTT.getLog().error(var8.getMessage(), var8);
            if(var2 != null) {
                var2.close();
            }

            throw var8;
        } finally {
            this.DebugSQL(var1);
        }

        return var4;
    }

    public String getString(String var1) throws Exception {
        String var2 = null;
        ASResultSet var3 = this.getASResultSet(var1);
        if(var3.next()) {
            var2 = var3.getString(1);
        }

        var3.getStatement().close();
        return var2;
    }

    public String[] getStringArray(String var1) throws Exception {
        return this.getStringArray(var1, 2000);
    }

    public String[] getStringArray(String var1, int var2) throws Exception {
        String[][] var3 = this.getStringMatrix(var1, var2, 1);
        String[] var4 = new String[var3.length];

        for(int var5 = 0; var5 < var3.length; ++var5) {
            var4[var5] = var3[var5][0];
        }

        return var4;
    }

    public String[][] getStringMatrix(String var1) throws Exception {
        return this.getStringMatrix(var1, 2000, 100);
    }

    public String[][] getStringMatrix(String var1, int var2, int var3) throws Exception {
        String[][] var4 = new String[var2][var3];
        int var6 = 0;
        boolean var7 = false;
        ASResultSet var8 = this.getASResultSet2(var1);
        int var11 = var8.getColumnCount();

        int var9;
        while(var8.next()) {
            ++var6;
            if(var6 >= var2) {
                break;
            }

            for(var9 = 0; var9 < var11; ++var9) {
                if(var9 >= var3) {
                    var11 = var3;
                    break;
                }

                var4[var6 - 1][var9] = var8.getString(var9 + 1);
            }
        }

        var8.getStatement().close();
        String[][] var5 = new String[var6][var11];

        for(var9 = 0; var9 < var6; ++var9) {
            for(int var10 = 0; var10 < var11; ++var10) {
                var5[var9][var10] = var4[var9][var10];
            }
        }

        return var5;
    }

    public void disConnect() throws Exception {
        this.conn.close();
    }

    public void finalize() throws Exception {
        this.disConnect();
    }

    public String convertAmarStr2DBStr(String var1) throws Exception {
        if(iChange == 1 && var1 != null) {
            var1 = new String(var1.getBytes("GBK"), "ISO8859_1");
        }

        if(iChange == 3 && var1 != null) {
            var1 = new String(var1.getBytes("ISO8859_1"), "GBK");
        }

        var1 = SpecialTools.wentuotuo2DB(var1);
        String var2 = this.conn.getMetaData().getDatabaseProductName();
        if(var2 != null && var2.equalsIgnoreCase("Informix Dynamic Server")) {
            var1 = SpecialTools.db2Informix(var1);
        }

        return var1;
    }

    public Double getDouble(String var1) throws Exception {
        Double var2 = null;
        ASResultSet var3 = this.getASResultSet(var1);
        if(var3.next()) {
            var2 = new Double(var3.getDouble(1));
        }

        var3.getStatement().close();
        return var2;
    }

    private void setDebugTime() {
        if(iDebugMode == 1) {
            this.dBeginTime = new Date();
        }

    }

    private void DebugSQL(String var1) {
        if(iDebugMode == 1) {
            Date var2 = new Date();
            double var3 = (double)(var2.getTime() - this.dBeginTime.getTime()) / 1000.0D;
            if(var3 > WARNING_SQLTIME) {
                WTT.getLog().warn("[SQL]" + sdf.format(this.dBeginTime) + " : " + var3 + " [" + var1 + "]");
            } else {
                WTT.getLog().debug("[SQL]" + sdf.format(this.dBeginTime) + " : " + var3 + " [" + var1 + "]");
            }
        }

    }

    private void log(String var1, String var2) throws Exception {
        Object var3 = null;
    }

    public void setLogExecute(boolean var1) {
        this.LOG_EXECUTE = var1;
    }

    public void setLogSelect(boolean var1) {
        this.LOG_SELECT = var1;
    }

    public ASValuePool getValuePool(String var1) throws Exception {
        ASValuePool var2 = new ASValuePool();
        ASResultSet var3 = this.getASResultSet(var1);
        if(var3.next()) {
            for(int var4 = 1; var4 < var3.iColumnCount + 1; ++var4) {
                String var5 = DataConvert.toString(var3.getColumnName(var4)).toUpperCase();
                var2.setAttribute(var5, var3.getString(var4));
            }
        }

        var3.getStatement().close();
        return var2;
    }

    public Vector getVector(String var1) throws Exception {
        Vector var2 = new Vector();
        ASResultSet var3 = this.getASResultSet(var1);

        while(var3.next()) {
            ASValuePool var4 = new ASValuePool();

            for(int var5 = 1; var5 < var3.iColumnCount + 1; ++var5) {
                var4.setAttribute(var3.getColumnName(var5), var3.getString(var5));
            }

            var2.add(var4);
        }

        var3.getStatement().close();
        return var2;
    }

    public Vector getVector(String var1, String var2, String var3, String var4, String var5, int var6, int var7) throws Exception {
        String var8 = this.conn.getMetaData().getDatabaseProductName();
        String var9 = "";
        if(!"mysql".equalsIgnoreCase(var8)) {
            throw new Exception("此函数目前不支持这种数据库：" + var8);
        } else {
            var9 = var9 + var1 + var2 + var3;
            var9 = var9 + var4 + var5;
            var9 = var9 + " limit " + var6 + "," + (var7 - var6);
            Vector var10 = new Vector();
            ASResultSet var11 = this.getASResultSet(var9);

            while(var11.next()) {
                ASValuePool var12 = new ASValuePool();

                for(int var13 = 1; var13 < var11.iColumnCount + 1; ++var13) {
                    var12.setAttribute(var11.getColumnName(var13), var11.getString(var13));
                }

                var10.add(var12);
            }

            var11.getStatement().close();
            return var10;
        }
    }

    public static double getWarningSqlTime() {
        return WARNING_SQLTIME;
    }

    public static void setWarningSqlTime(double var0) {
        WARNING_SQLTIME = var0;
    }
}
