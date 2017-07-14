//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.impl;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.jbo.BizObject;
import com.wentuotuo.wtt.jbo.BizObjectKey;
import com.wentuotuo.wtt.jbo.JBOException;
import com.wentuotuo.wtt.jbo.KeyGenerator;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.sql.Connection;
import com.wentuotuo.wtt.sql.Query;
import com.wentuotuo.wtt.sql.SQLConstants;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ALSBizObjectManager extends StateBizObjectManager implements KeyGenerator {
    private boolean createKey = false;
    private String keyDatePrefix = "yyyyMMdd";
    private int keyNumberLength = 8;
    private Date baseDate = null;
    private static String sLockSql = "update object_maxsn set MaxSerialNo = MaxSerialNo where TableName=:sTable and ColumnName=:sColumn and DateFmt=:sDateFmt and NoFmt=:sNoFmt";
    private static String sQuerySql = "select MaxSerialNo from object_maxsn where TableName=:sTable and ColumnName=:sColumn and DateFmt=:sDateFmt and NoFmt=:sNoFmt";
    private static String sUpdateSql = "update object_maxsn set MaxSerialNo = :sNewSerialNo where TableName=:sTable and ColumnName=:sColumn and DateFmt=:sDateFmt and NoFmt=:sNoFmt";
    private static String sInsertSql = "insert into object_maxsn (TABLENAME,COLUMNNAME,MAXSERIALNO,DATEFMT,NOFMT) values (:sTable,:sColumn ,:sNewSerialNo,:sDateFmt,:sNoFmt)";

    public ALSBizObjectManager() {
    }

    public void saveObject(BizObject var1) throws JBOException {
        if(var1.getState() == 2 && this.isCreateKey()) {
            BizObjectKey var2 = this.getKey();
            if(var2.getAttributeNumber() < 1) {
                throw new JBOException(1325, new String[]{this.clazz.getAbsoluteName()});
            }

            if(StringX.isEmpty(var1.getAttribute(var2.getAttribute(0).getName()))) {
                BizObjectKey var3 = this.generateKey(var1);
                if(var3 != null) {
                    var1.getAttribute(var3.getAttribute(0).getName()).setValue(var3.getAttribute(0));
                }
            }
        }

        super.saveObject(var1);
    }

    protected void setParameter(Query var1, DataElement[] var2) throws JBOException {
        for(int var3 = 0; var3 < var2.length; ++var3) {
            if(var2[var3].getType() == 16 && var2[var3].getLength() == 10) {
                DataElement var4 = new DataElement(var2[var3].getName());
                var4.setLength(10);
                var4.setValue(var2[var3].getString());
                var2[var3] = var4;
            }
        }

        super.setParameter(var1, var2);
    }

    public boolean isCreateKey() {
        return this.createKey;
    }

    public void setCreateKey(boolean var1) {
        this.createKey = var1;
    }

    public String getKeyDatePrefix() {
        return this.keyDatePrefix;
    }

    public void setKeyDatePrefix(String var1) {
        this.keyDatePrefix = var1;
    }

    public int getKeyNumberLength() {
        return this.keyNumberLength;
    }

    public void setKeyNumberLength(int var1) {
        this.keyNumberLength = var1;
    }

    public Date getBaseDate() {
        return this.baseDate;
    }

    public void setBaseDate(Date var1) {
        this.baseDate = var1;
    }

    public BizObjectKey generateKey(BizObject var1) throws JBOException {
        BizObjectKey var2 = null;
        if(this.isCreateKey()) {
            var2 = this.getKey();
            byte var3 = var2.getAttribute(0).getType();
            if(var3 != 0) {
                throw new JBOException(1326, new String[]{this.clazz.getAbsoluteName(), "[STRING]", SQLConstants.getBaseDataTypeName(var3)});
            }

            StringBuffer var4 = new StringBuffer();

            while(var4.length() < this.getKeyNumberLength()) {
                var4.append('0');
            }

            String var5 = var4.toString();
            Date var6 = null;
            if(this.baseDate == null) {
                var6 = new Date();
            } else {
                var6 = this.baseDate;
            }

            var2.setAttributeValue(0, getSerialNoByCon(this.getDatabase(), this.getTable(), this.getColumnOfAttribute(var2.getAttribute(0)), this.getKeyDatePrefix(), var5, var6, this.getTableNameCase()));
        }

        return var2;
    }

    private static String getSerialNoByCon(String var0, String var1, String var2, String var3, String var4, Date var5, int var6) throws JBOException {
        Connection var7 = null;
        SimpleDateFormat var8 = new SimpleDateFormat(var3);
        DecimalFormat var9 = new DecimalFormat(var4);
        String var10 = var8.format(var5);
        boolean var11 = var6 == 1;
        int var12 = var10.length();
        String var13 = "";
        String var14 = var1;
        switch(var6) {
            case 1:
                var14 = var1.toUpperCase();
                break;
            case 2:
                var14 = var1.toLowerCase();
        }

        String var15 = var1.toUpperCase();
        String var16 = var2.toUpperCase();
        boolean var17 = false;

        try {
            var7 = WTT.getDBConnection(var0);
            var7.setAutoCommit(false);
        } catch (SQLException var34) {
            throw new JBOException(1327, var34);
        }

        try {
            Query var18 = var7.createQuery(var11?sLockSql.replaceAll("object_maxsn", "OBJECT_MAXSN"):sLockSql);
            var18.setParameter("sTable", var15);
            var18.setParameter("sColumn", var16);
            var18.setParameter("sDateFmt", var3);
            var18.setParameter("sNoFmt", var4);
            var18.executeUpdate();
            var18.close();
            Query var19 = var7.createQuery(var11?sQuerySql.replaceAll("object_maxsn", "OBJECT_MAXSN"):sQuerySql);
            var19.setParameter("sTable", var15);
            var19.setParameter("sColumn", var16);
            var19.setParameter("sDateFmt", var3);
            var19.setParameter("sNoFmt", var4);
            ResultSet var20 = var19.getResultSet();
            if(!var20.next()) {
                var20.close();
                var19.close();
                var13 = getInitSerialNo(var14, var2, var3, var4, var5, var7);
                Query var37 = var7.createQuery(var11?sInsertSql.replaceAll("object_maxsn", "OBJECT_MAXSN"):sInsertSql);
                var37.setParameter("sTable", var15);
                var37.setParameter("sColumn", var16);
                var37.setParameter("sNewSerialNo", var13);
                var37.setParameter("sDateFmt", var3);
                var37.setParameter("sNoFmt", var4);
                var37.executeUpdate();
                var37.close();
            } else {
                String var21 = var20.getString(1);
                var20.close();
                var19.close();
                var17 = false;
                if(var21 != null && var21.indexOf(var10, 0) != -1) {
                    int var38 = Integer.valueOf(var21.substring(var12)).intValue();
                    var13 = var10 + var9.format((long)(var38 + 1));
                } else {
                    var13 = getInitSerialNo(var14, var2, var3, var4, var5, var7);
                }

                Query var22 = var7.createQuery(var11?sUpdateSql.replaceAll("object_maxsn", "OBJECT_MAXSN"):sUpdateSql);
                var22.setParameter("sNewSerialNo", var13);
                var22.setParameter("sTable", var15);
                var22.setParameter("sColumn", var16);
                var22.setParameter("sDateFmt", var3);
                var22.setParameter("sNoFmt", var4);
                var22.executeUpdate();
                var22.close();
            }

            var7.commit();
        } catch (Exception var35) {
            try {
                var7.rollback();
            } catch (SQLException var33) {
                WTT.getLog().error(var33);
            }

            WTT.getLog().debug("getSerialNo...失败[" + var35.getMessage() + "]!", var35);
            throw new JBOException(1327, var35);
        } finally {
            if(var7 != null) {
                try {
                    var7.close();
                } catch (SQLException var32) {
                    WTT.getLog().error(var32);
                }

                var7 = null;
            }

        }

        return var13;
    }

    private static String getInitSerialNo(String var0, String var1, String var2, String var3, Date var4, Connection var5) throws Exception {
        SimpleDateFormat var8 = new SimpleDateFormat(var2);
        DecimalFormat var9 = new DecimalFormat(var3);
        ResultSet var10 = null;
        String var11 = var8.format(var4);
        int var12 = var11.length();
        String var13 = "select max(" + var1 + ") from " + var0 + " where " + var1 + " like '" + var11 + "%' ";
        Query var14 = var5.createQuery(var13);
        var10 = var14.getResultSet();
        int var15 = 0;
        if(var10.next()) {
            String var6 = var10.getString(1);
            if(var6 != null) {
                var15 = Integer.valueOf(var6.substring(var12)).intValue();
            }
        }

        var14.close();
        var10.close();
        String var7 = var11 + var9.format((long)(var15 + 1));
        WTT.getLog().info("newSerialNo[" + var0 + "][" + var1 + "]=[" + var7 + "]");
        return var7;
    }
}
