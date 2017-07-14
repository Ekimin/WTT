//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.WTT;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sql.DataSource;

public class DBFunction {
    public static String sDataSource = "";
    private static String sLockSql = "update OBJECT_MAXSN set MaxSerialNo = MaxSerialNo where TableName=? and ColumnName=? and DateFmt=? and NoFmt=?";
    private static String sQuerySql = "select MaxSerialNo from OBJECT_MAXSN where TableName=? and ColumnName=? and DateFmt=? and NoFmt=?";
    private static String sUpdateSql = "update OBJECT_MAXSN set MaxSerialNo = ? where TableName=? and ColumnName=? and DateFmt=? and NoFmt=?";
    private static String sInsertSql = "insert into OBJECT_MAXSN (TABLENAME,COLUMNNAME,MAXSERIALNO,DATEFMT,NOFMT) values (?,?,?,?,?)";

    public DBFunction() {
    }

    public static String getDataSource() {
        return sDataSource;
    }

    public static void setDataSource(String var0) {
        sDataSource = var0;
    }

    public static String getSerialNo(String var0, String var1) throws Exception {
        return getSerialNo(var0, var1, "yyyyMMdd", "00000000", new Date());
    }

    public static String getSerialNo(String var0, String var1, Transaction var2) throws Exception {
        return getSerialNo(var0, var1);
    }

    public static String getSerialNo(String var0, String var1, String var2) throws Exception {
        if(var2 != null && !var2.equals("")) {
            var2 = "'" + var2 + "'";
        } else {
            var2 = "";
        }

        return getSerialNo(var0, var1, var2 + "yyyyMMdd", "00000000", new Date());
    }

    public static String getSerialNo(String var0, String var1, String var2, Transaction var3) throws Exception {
        return getSerialNo(var0, var1, var2);
    }

    public static String getSerialNo(String var0, String var1, String var2, String var3, Date var4, Transaction var5) throws Exception {
        return getSerialNo(var0, var1, var2, var3, var4);
    }

    public static String getSerialNo(String var0, String var1, String var2, String var3, Date var4) throws Exception {
        Transaction var5 = null;
        String var6 = "";

        try {
            if(WTT.getRunMode() == 1) {
                try {
                    DataSource var7 = ConnectionManager.getDataSource(sDataSource);
                    var5 = ConnectionManager.getTransaction(var7);
                } catch (Exception var12) {
                    WTT.getLog().warn("无法获取数据库连接[RUN_MODE_WEBCONTAINER][" + sDataSource + "]", var12);
                    var5 = new Transaction(WTT.getDBConnection(sDataSource));
                }
            } else {
                var5 = new Transaction(WTT.getDBConnection(sDataSource));
            }

            if(var5 == null) {
                WTT.getLog().error("无法获取数据库连接[" + sDataSource + "]");
            }

            var6 = getSerialNoByCon(var0, var1, var2, var3, var4, var5);
        } catch (Exception var13) {
            throw new Exception("getSerialNo...失败！" + var13.getMessage());
        } finally {
            if(var5 != null) {
                var5.disConnect();
            }

        }

        return var6;
    }

    private static String getSerialNoByCon(String var0, String var1, String var2, String var3, Date var4, Transaction var5) throws Exception {
        SimpleDateFormat var6 = new SimpleDateFormat(var2);
        DecimalFormat var7 = new DecimalFormat(var3);
        String var8 = var6.format(var4);
        int var9 = var8.length();
        String var10 = "";
        var0 = var0.toUpperCase();
        var1 = var1.toUpperCase();
        boolean var11 = false;

        try {
            PreparedStatement var12 = var5.conn.prepareStatement(sLockSql);
            var12.setString(1, var0);
            var12.setString(2, var1);
            var12.setString(3, var2);
            var12.setString(4, var3);
            var12.executeUpdate();
            var12.close();
            PreparedStatement var13 = var5.conn.prepareStatement(sQuerySql);
            var13.setString(1, var0);
            var13.setString(2, var1);
            var13.setString(3, var2);
            var13.setString(4, var3);
            ResultSet var14 = var13.executeQuery();
            if(!var14.next()) {
                var14.close();
                var13.close();
                var10 = getSerialNoFromDB(var0, var1, "", var2, var3, var4, var5);
                PreparedStatement var19 = var5.conn.prepareStatement(sInsertSql);
                var19.setString(1, var0);
                var19.setString(2, var1);
                var19.setString(3, var10);
                var19.setString(4, var2);
                var19.setString(5, var3);
                var19.executeUpdate();
                var19.close();
            } else {
                String var15 = var14.getString(1);
                var14.close();
                var13.close();
                var11 = false;
                if(var15 != null && var15.indexOf(var8, 0) != -1) {
                    int var18 = Integer.valueOf(var15.substring(var9)).intValue();
                    var10 = var8 + var7.format((long)(var18 + 1));
                } else {
                    var10 = getSerialNoFromDB(var0, var1, "", var2, var3, var4, var5);
                }

                PreparedStatement var16 = var5.conn.prepareStatement(sUpdateSql);
                var16.setString(1, var10);
                var16.setString(2, var0);
                var16.setString(3, var1);
                var16.setString(4, var2);
                var16.setString(5, var3);
                var16.executeUpdate();
                var16.close();
            }

            var5.conn.commit();
        } catch (Exception var17) {
            var5.conn.rollback();
            WTT.getLog().error("getSerialNo...失败[" + var17.getMessage() + "]!", var17);
        }

        return var10;
    }

    public static String getSerialNoXD(String var0, String var1, String var2, String var3, Date var4, Transaction var5) throws Exception {
        return getSerialNoByCon(var0, var1, var2, var3, var4, var5);
    }

    public static String getSerialNoFromDB(String var0, String var1, String var2, String var3, String var4, Date var5, Transaction var6) throws Exception {
        WTT.getLog().warn("****不建议的取流水号的方式(getSerialNoFromDB)****[" + var0 + "][" + var1 + "]******");
        SimpleDateFormat var9 = new SimpleDateFormat(var3);
        DecimalFormat var10 = new DecimalFormat(var4);
        String var12 = var9.format(var5);
        int var13 = var12.length();
        String var14 = "select max(" + var1 + ") from " + var0 + " where " + var1 + " like '" + var12 + "%' ";
        if(var2.length() > 0) {
            var14 = var14 + " and " + var2;
        }

        ASResultSet var11 = var6.getResultSet(var14);
        int var15 = 0;
        if(var11.next()) {
            String var7 = var11.getString(1);
            if(var7 != null) {
                var15 = Integer.valueOf(var7.substring(var13)).intValue();
            }
        }

        var11.getStatement().close();
        String var8 = var12 + var10.format((long)(var15 + 1));
        WTT.getLog().info("newSerialNo[" + var0 + "][" + var1 + "]=[" + var8 + "]");
        return var8;
    }

    public static String getSerialNo_for_alarm(String var0, String var1, String var2, String var3, String var4, Date var5, Transaction var6) throws Exception {
        return getSerialNo_for_alarm(var0, var1, var3, var4, var5, var6);
    }

    public static String getSerialNo_for_alarm(String var0, String var1, String var2, String var3, Date var4, Transaction var5) throws Exception {
        return getSerialNoByCon(var0, var1, var2, var3, var4, var5);
    }

    public static String getSerialNo(String var0, String var1, String var2, String var3, String var4, Date var5, Transaction var6) throws Exception {
        return getSerialNo(var0, var1, var3, var4, var5, var6);
    }

    public static void main(String[] var0) {
        try {
            WTT.init("etc/wtt.xml");
            setDataSource("als");

            for(int var1 = 0; var1 < 10; ++var1) {
                String var2 = getSerialNo("BUSINESS_APPLY", "SERIALNO");
                WTT.getLog().info("BASE1 SerialNo=" + var2);
                var2 = getSerialNo("BUSINESS_APPLY", "SERIALNO");
                WTT.getLog().info("BASE2 SerialNo=" + var2);
                var2 = getSerialNo("BUSINESS_APPLY", "SERIALNO", "BA");
                WTT.getLog().info("Prefix1 SerialNo=" + var2);
                var2 = getSerialNo("BUSINESS_APPLY", "SERIALNO", "BA");
                WTT.getLog().info("Prefix2 SerialNo=" + var2);
                var2 = getSerialNo("BUSINESS_APPLY", "SERIALNO", "yyyyMMdd", "000000", new Date());
                WTT.getLog().info("Free1 SerialNo=" + var2);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            WTT.getLog().error("main error!", var3);
        }

    }
}
