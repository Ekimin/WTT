//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.dpx.dataconvert.DataConvertor;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.metadata.ColumnMetaData;
import com.wentuotuo.wtt.sql.SQLConstants;
import java.util.Date;

public class Field extends DataElement implements DataConvertor, Cloneable, Comparable {
    private static final long serialVersionUID = 1L;
    private ColumnMetaData metaData;

    public Field(ColumnMetaData var1) {
        super(var1.getName(), SQLConstants.SQLTypeToBaseType(var1.getType()));
        this.setLabel(var1.getLabel());
        this.metaData = var1;
        Object var2 = var1.getDefaultValue();
        if(var2 != null) {
            switch(this.getType()) {
                case 0:
                    this.setValue((String)var2);
                    break;
                case 1:
                    try {
                        int var11 = ((Integer)var2).intValue();
                        this.setValue(var11);
                    } catch (Exception var8) {
                        WTT.getLog().debug("Invalid default of column " + this.getName(), var8);
                    }
                    break;
                case 2:
                    try {
                        long var10 = ((Long)var2).longValue();
                        this.setValue(var10);
                    } catch (Exception var7) {
                        WTT.getLog().debug("Invalid default of column " + this.getName(), var7);
                    }
                case 3:
                case 5:
                case 6:
                case 7:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                default:
                    break;
                case 4:
                    try {
                        double var9 = ((Double)var2).doubleValue();
                        this.setValue(var9);
                    } catch (Exception var6) {
                        WTT.getLog().debug("Invalid default of column " + this.getName(), var6);
                    }
                    break;
                case 8:
                    boolean var3 = ((Boolean)var2).booleanValue();
                    this.setValue(var3);
                    break;
                case 16:
                    try {
                        this.setValue((Date)var2);
                    } catch (Exception var5) {
                        WTT.getLog().debug("Invalid default of column " + this.getName(), var5);
                    }
            }
        }

    }

    public Field(int var1, String var2, byte var3) {
        super(var2, var3);
    }

    public Field(String var1, byte var2) {
        super(var1, var2);
    }

    public Field(String var1, int var2) {
        super(var1, (byte)var2);
    }

    public Field(String var1) {
        super(var1);
    }

    public void setDate(Date var1) {
        this.setValue(var1);
    }

    public void setDouble(double var1) {
        this.setValue(var1);
    }

    public void setString(String var1) {
        this.setValue(var1);
    }

    public void setInt(int var1) {
        this.setValue(var1);
    }

    public void setLong(long var1) {
        this.setValue(var1);
    }

    public void setBoolean(boolean var1) {
        this.setValue(var1);
    }

    public void clear() {
        this.setNull();
    }

    public Object clone() {
        Field var1 = null;
        var1 = (Field)super.clone();
        var1.metaData = this.metaData;
        return var1;
    }

    public void close() {
    }

    public ColumnMetaData getMetaData() {
        return this.metaData;
    }

    public String[] getProperties() {
        return this.metaData == null?super.getProperties():this.metaData.getProperties();
    }

    public String getProperty(String var1) {
        String var2 = super.getProperty(var1);
        if(var2 == null && this.metaData != null) {
            if(var1.equals("format")) {
                var2 = this.metaData.getFormat();
            }

            if(var2 == null) {
                var2 = this.metaData.getProperty(var1);
            }
        }

        return var2;
    }

    public int getLength() {
        return this.metaData == null?super.getLength():this.metaData.getDisplaySize();
    }

    public int getScale() {
        return this.metaData == null?super.getScale():this.metaData.getScale();
    }
}
