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
import java.util.Arrays;
import java.util.Date;

public class LastKeyStateBizObjectManager extends StateBizObjectManager implements KeyGenerator {
    private boolean createKey = false;
    private String keyDatePrefix = null;
    private int keyNumberLength = 4;

    public LastKeyStateBizObjectManager() {
    }

    public void saveObject(BizObject var1) throws JBOException {
        DataElement[] var2 = this.getKey().getAttributes();
        if(this.isCreateKey() && var1.getState() == 2 && StringX.isEmpty(var1.getAttribute(var2[var2.length - 1].getName()))) {
            BizObjectKey var3 = this.generateKey(var1);
            if(var3 != null) {
                var1.getAttribute(var3.getAttribute(var2.length - 1).getName()).setValue(var3.getAttribute(var2.length - 1));
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
            BizObjectKey var2 = var1.getKey();
            DataElement[] var3 = var2.getAttributes();
            if(var3.length < 2) {
                throw new JBOException(1325, new String[]{this.clazz.getAbsoluteName()});
            } else {
                for(int var4 = 0; var4 < var3.length - 1; ++var4) {
                    if(var3[var4].isNull()) {
                        throw new JBOException(1325, new String[]{this.clazz.getAbsoluteName()});
                    }
                }

                byte var5 = var2.getAttribute(0).getType();
                if(var5 == 0) {
                    var2.setAttributeValue(var3.length - 1, this.getStringKey(this.getDatabase(), this.getTable(), this.getColumnOfAttribute(var2.getAttribute(var3.length - 1)), var3));
                } else {
                    if(var5 != 1 && var5 != 2) {
                        throw new JBOException(1326, new String[]{this.clazz.getAbsoluteName(), "[INT|LONG|STRING]", SQLConstants.getBaseDataTypeName(var5)});
                    }

                    var2.setAttributeValue(var3.length - 1, this.getIntegerKey(this.getTable(), this.getColumnOfAttribute(var2.getAttribute(var3.length - 1)), var3));
                }

                return var2;
            }
        } else {
            return null;
        }
    }

    private long getIntegerKey(String var1, String var2, DataElement[] var3) throws JBOException {
        long var4 = 0L;
        StringBuffer var6 = new StringBuffer("SELECT MAX(");
        var6.append(var2).append(") FROM ").append(var1);

        for(int var7 = 0; var7 < var3.length - 1; ++var7) {
            var6.append(var7 == 0?" WHERE ":" AND ");
            var6.append(this.getColumnOfAttribute(var3[var7])).append("=:").append(var3[var7].getName());
        }

        Connection var16 = null;
        Query var8 = null;

        try {
            var16 = this.getConnection();
            var8 = var16.createQuery(var6.toString());

            for(int var9 = 0; var9 < var3.length - 1; ++var9) {
                var8.setParameter(var3[var9]);
            }

            ResultSet var17 = var8.getResultSet();
            var4 = (long)var17.getInt(1);
            var17.close();
            return var4 + 1L;
        } catch (SQLException var14) {
            WTT.getLog().debug(var14);
            throw new JBOException(1327, new String[]{this.clazz.getAbsoluteName(), var6.toString()}, var14);
        } finally {
            if(var8 != null) {
                var8.close();
                var8 = null;
            }

        }
    }

    private String getStringKey(String var1, String var2, String var3, DataElement[] var4) throws JBOException {
        String var5 = null;
        long var6 = 0L;
        StringBuffer var8 = new StringBuffer("SELECT MAX(");
        var8.append(var3).append(") FROM ").append(var2);
        String var9 = this.getKeyDatePrefix();
        String var10 = null;
        int var11;
        if(var9 != null) {
            var10 = (new SimpleDateFormat(var9)).format(new Date());
            var8.append(" WHERE ").append(var3).append(" like '");
            var8.append(var10).append("%'");

            for(var11 = 0; var11 < var4.length - 1; ++var11) {
                var8.append(" AND ");
                var8.append(this.getColumnOfAttribute(var4[var11])).append("=:").append(var4[var11].getName());
            }
        } else {
            for(var11 = 0; var11 < var4.length - 1; ++var11) {
                var8.append(var11 == 0?" WHERE ":" AND ");
                var8.append(this.getColumnOfAttribute(var4[var11])).append("=:").append(var4[var11].getName());
            }
        }

        Connection var28 = null;
        Query var12 = null;

        try {
            var28 = WTT.getDBConnection(var1);
            var12 = var28.createQuery(var8.toString());

            for(int var13 = 0; var13 < var4.length - 1; ++var13) {
                var12.setParameter(var4[var13]);
            }

            ResultSet var29 = var12.getResultSet();
            String var14 = null;
            if(var29.next()) {
                var14 = var29.getString(1);
            }

            var29.close();
            if(var14 == null) {
                var6 = 0L;
            } else {
                try {
                    var6 = Long.parseLong(var10 == null?var14:var14.substring(var10.length()));
                } catch (Exception var25) {
                    var6 = 0L;
                }
            }

            ++var6;
        } catch (SQLException var26) {
            WTT.getLog().debug(var26);
            throw new JBOException(1327, new String[]{this.clazz.getAbsoluteName(), var8.toString()}, var26);
        } finally {
            if(var12 != null) {
                var12.close();
                var12 = null;
            }

            if(var28 != null) {
                try {
                    var28.close();
                } catch (SQLException var24) {
                    WTT.getLog().debug(var24);
                }

                var28 = null;
            }

        }

        char[] var30 = new char[this.keyNumberLength];
        Arrays.fill(var30, '0');
        var5 = (var10 == null?"":var10) + (new DecimalFormat(String.valueOf(var30))).format(var6);
        return var5;
    }
}
