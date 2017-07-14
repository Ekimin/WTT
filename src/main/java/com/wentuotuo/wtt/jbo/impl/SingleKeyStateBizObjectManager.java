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
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.sql.Connection;
import com.wentuotuo.wtt.sql.Query;
import com.wentuotuo.wtt.sql.SQLConstants;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class SingleKeyStateBizObjectManager extends StateBizObjectManager implements KeyGenerator {
    private boolean createKey = false;
    private String keyDatePrefix = "yyyyMMdd";
    private int keyNumberLength = 4;

    public SingleKeyStateBizObjectManager() {
    }

    public void saveObject(BizObject var1) throws JBOException {
        if(this.isCreateKey() && var1.getState() == 2 && StringX.isEmpty(var1.getAttribute(var1.getKey().getAttribute(0).getName()))) {
            BizObjectKey var2 = this.generateKey(var1);
            if(var2 != null) {
                var1.getAttribute(var2.getAttribute(0).getName()).setValue(var2.getAttribute(0));
            }
        }

        super.saveObject(var1);
    }

    public final boolean isCreateKey() {
        return this.createKey;
    }

    public final void setCreateKey(boolean var1) {
        this.createKey = var1;
    }

    public final String getKeyDatePrefix() {
        return this.keyDatePrefix;
    }

    public final void setKeyDatePrefix(String var1) {
        if(var1 != null && var1 == "") {
            var1 = null;
        }

        this.keyDatePrefix = var1;
    }

    public final int getKeyNumberLength() {
        return this.keyNumberLength;
    }

    public final void setKeyNumberLength(int var1) {
        this.keyNumberLength = var1;
    }

    public BizObjectKey generateKey(BizObject var1) throws JBOException {
        if(this.isCreateKey()) {
            BizObjectKey var2 = this.getKey();
            if(var2.getAttributeNumber() > 1) {
                throw new JBOException(1325, new String[]{this.clazz.getAbsoluteName()});
            } else {
                byte var3 = var2.getAttribute(0).getType();
                if(var3 == 0) {
                    var2.setAttributeValue(0, this.getStringKey(this.getDatabase(), this.getTable(), this.getColumnOfAttribute(var2.getAttribute(0))));
                } else {
                    if(var3 != 1 && var3 != 2) {
                        throw new JBOException(1326, new String[]{this.clazz.getAbsoluteName(), "[INT|LONG|STRING]", SQLConstants.getBaseDataTypeName(var3)});
                    }

                    var2.setAttributeValue(0, this.getIntegerKey(this.getDatabase(), this.getTable(), this.getColumnOfAttribute(var2.getAttribute(0))));
                }

                return var2;
            }
        } else {
            return null;
        }
    }

    private long getIntegerKey(String var1, String var2, String var3) throws JBOException {
        long var4 = 0L;
        StringBuffer var6 = new StringBuffer("SELECT MAX(");
        var6.append(var3).append(") FROM ").append(var2);
        Connection var7 = null;
        Query var8 = null;

        try {
            var7 = WTT.getDBConnection(var1);
            var8 = var7.createQuery(var6.toString());
            ResultSet var9 = var8.getResultSet();
            var4 = (long)var9.getInt(1);
            var9.close();
        } catch (SQLException var18) {
            WTT.getLog().debug(var18);
            throw new JBOException(1327, new String[]{this.clazz.getAbsoluteName(), var6.toString()}, var18);
        } finally {
            if(var8 != null) {
                var8.close();
                var8 = null;
            }

            if(var7 != null) {
                try {
                    var7.close();
                } catch (SQLException var17) {
                    WTT.getLog().debug(var17);
                }

                var7 = null;
            }

        }

        return var4 + 1L;
    }

    private String getStringKey(String var1, String var2, String var3) throws JBOException {
        String var4 = null;
        long var5 = 0L;
        StringBuffer var7 = new StringBuffer("SELECT MAX(");
        var7.append(var3).append(") FROM ").append(var2);
        String var8 = this.getKeyDatePrefix();
        String var9 = null;
        if(var8 != null) {
            var9 = (new SimpleDateFormat(var8)).format(new Date());
            var7.append(" WHERE ").append(var3).append(" like '");
            var7.append(var9).append("%'");
        }

        Connection var10 = null;
        Query var11 = null;

        try {
            var10 = WTT.getDBConnection(var1);
            var11 = var10.createQuery(var7.toString());
            ResultSet var12 = var11.getResultSet();
            String var13 = null;
            if(var12.next()) {
                var13 = var12.getString(1);
            }

            var12.close();
            if(var13 == null) {
                var5 = 0L;
            } else {
                try {
                    var5 = Long.parseLong(var9 == null?var13:var13.substring(var9.length()));
                } catch (Exception var24) {
                    var5 = 0L;
                }
            }

            ++var5;
        } catch (SQLException var25) {
            WTT.getLog().debug(var25);
            throw new JBOException(1327, new String[]{this.clazz.getAbsoluteName(), var7.toString()}, var25);
        } finally {
            if(var11 != null) {
                var11.close();
                var11 = null;
            }

            if(var10 != null) {
                try {
                    var10.close();
                } catch (SQLException var23) {
                    WTT.getLog().debug(var23);
                }

                var10 = null;
            }

        }

        char[] var27 = new char[this.keyNumberLength];
        Arrays.fill(var27, '0');
        var4 = (var9 == null?"":var9) + (new DecimalFormat(String.valueOf(var27))).format(var5);
        return var4;
    }
}
