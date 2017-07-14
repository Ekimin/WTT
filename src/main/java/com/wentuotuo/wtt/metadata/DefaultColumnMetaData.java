//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.metadata;

import com.wentuotuo.wtt.sql.SQLConstants;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;

public class DefaultColumnMetaData extends DefaultMetaDataObject implements ColumnMetaData {
    BitSet bitSet = new BitSet(8);
    private int displaySize = 20;
    private String format = "";
    private int indexInTable = 0;
    private int precision = 0;
    private int scale = 0;
    private String schemaName = "";
    private TableMetaData table;
    private int type = 1;
    private Object defaultValue = null;
    private ColumnUIMetaData defaultUIMetaData = null;
    private HashMap uimetadata = null;
    private static DefaultColumnUIMetaData[] defaultUIMetaDataPool = null;

    public DefaultColumnMetaData(int var1) {
        super("COLUMN_" + var1);
        this.indexInTable = var1;
        this.bitSet.set(1, 3);
        this.bitSet.set(4);
    }

    public DefaultColumnMetaData(int var1, String var2) {
        super(var2);
        this.indexInTable = var1;
        this.bitSet.set(1, 3);
        this.bitSet.set(4);
    }

    public DefaultColumnMetaData(int var1, String var2, int var3) {
        super(var2);
        this.type = var3;
        this.indexInTable = var1;
        this.bitSet.set(1, 3);
        this.bitSet.set(4);
    }

    public DefaultColumnMetaData(int var1, String var2, int var3, int var4, int var5) {
        super(var2);
        this.indexInTable = var1;
        this.type = var3;
        this.precision = var4;
        this.scale = var5;
        this.bitSet.set(1, 3);
        this.bitSet.set(4);
    }

    public DefaultColumnMetaData(int var1, String var2, int var3, String var4, int var5, int var6, int var7) {
        super(var2, var4);
        this.indexInTable = var1;
        this.type = var3;
        this.precision = var5;
        this.scale = var6;
        this.displaySize = var7;
        this.bitSet.set(1, 3);
        this.bitSet.set(4);
    }

    public ColumnUIMetaData getDefaultUIMetaData() {
        return this.defaultUIMetaData == null?this.getPooledUIMetaData():this.defaultUIMetaData;
    }

    private ColumnUIMetaData getPooledUIMetaData() {
        if(defaultUIMetaDataPool == null) {
            defaultUIMetaDataPool = new DefaultColumnUIMetaData[5];
        }

        byte var1 = 0;
        byte var2 = SQLConstants.SQLTypeToBaseType(this.type);
        switch(var2) {
            case 0:
                var1 = 0;
                break;
            case 1:
                var1 = 1;
                break;
            case 2:
                var1 = 2;
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
                var1 = 3;
                break;
            case 8:
                var1 = 5;
                break;
            case 16:
                var1 = 4;
        }

        if(defaultUIMetaDataPool[var1] == null) {
            defaultUIMetaDataPool[var1] = new DefaultColumnUIMetaData();
        }

        defaultUIMetaDataPool[var1].setKey(this.isPrimaryKey());
        defaultUIMetaDataPool[var1].setReadOnly(this.isReadOnly());
        defaultUIMetaDataPool[var1].setRequried(this.isNullable());
        defaultUIMetaDataPool[var1].setSortable(this.getDisplaySize() > 100);
        if(var1 == 3) {
            StringBuffer var3 = new StringBuffer("##,###,##0");
            if(this.scale > 0) {
                char[] var4 = new char[this.scale];
                Arrays.fill(var4, '0');
                var3.append('.').append(var4);
            }

            defaultUIMetaDataPool[var1].setDisplayFormat(var3.toString());
        }

        return defaultUIMetaDataPool[var1];
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public int getDisplaySize() {
        return this.displaySize;
    }

    public String getFormat() {
        return this.format;
    }

    public int getIndex() {
        return this.indexInTable;
    }

    public int getPrecision() {
        return this.precision;
    }

    public int getScale() {
        return this.scale;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public TableMetaData getTable() {
        return this.table;
    }

    public int getType() {
        return this.type;
    }

    public String getTypeName() {
        return SQLConstants.getSQLDataTypeName(this.getType());
    }

    public boolean isAutoIncrement() {
        return this.bitSet.get(0);
    }

    public boolean isCaseSensitive() {
        return this.bitSet.get(1);
    }

    public boolean isNullable() {
        return this.bitSet.get(2);
    }

    public boolean isPrimaryKey() {
        return this.bitSet.get(3);
    }

    public boolean isSearchable() {
        return this.bitSet.get(4);
    }

    public boolean isReadOnly() {
        return this.bitSet.get(5);
    }

    public void setAutoIncrement(boolean var1) {
        if(var1) {
            this.bitSet.set(0);
        } else {
            this.bitSet.clear(0);
        }

    }

    public void setCaseSensitive(boolean var1) {
        if(var1) {
            this.bitSet.set(1);
        } else {
            this.bitSet.clear(1);
        }

    }

    public void setNullable(boolean var1) {
        if(var1) {
            this.bitSet.set(2);
        } else {
            this.bitSet.clear(2);
        }

    }

    public void setPrimaryKey(boolean var1) {
        if(var1) {
            this.bitSet.set(3);
        } else {
            this.bitSet.clear(3);
        }

    }

    public void setSearchable(boolean var1) {
        if(var1) {
            this.bitSet.set(4);
        } else {
            this.bitSet.clear(4);
        }

    }

    public void setDisplaySize(int var1) {
        this.displaySize = var1;
    }

    public void setFormat(String var1) {
        this.format = var1;
    }

    public void setIndex(int var1) {
        this.indexInTable = var1;
    }

    public void setPrecision(int var1) {
        this.precision = var1;
    }

    public void setReadOnly(boolean var1) {
        if(var1) {
            this.bitSet.set(5);
        } else {
            this.bitSet.clear(5);
        }

    }

    public void setScale(int var1) {
        this.scale = var1;
    }

    public void setSchemaName(String var1) {
        this.schemaName = var1;
    }

    public void setDefaultValue(String var1) {
        this.defaultValue = var1;
    }

    public void setDefaultValue(Date var1) {
        this.defaultValue = var1;
    }

    public void setDefaultValue(int var1) {
        this.defaultValue = new Integer(var1);
    }

    public void setDefaultValue(long var1) {
        this.defaultValue = new Long(var1);
    }

    public void setDefaultValue(double var1) {
        this.defaultValue = new Double(var1);
    }

    public void setDefaultValue(boolean var1) {
        this.defaultValue = new Boolean(var1);
    }

    public void setTable(TableMetaData var1) {
        this.table = var1;
    }

    public void setType(int var1) {
        this.type = var1;
    }

    public void setDefaultUIMetaData(ColumnUIMetaData var1) {
        this.defaultUIMetaData = var1;
    }

    public ColumnUIMetaData getUIMetaData(String var1) {
        return this.uimetadata != null && var1 != null?(ColumnUIMetaData)this.uimetadata.get(var1.toUpperCase()):null;
    }

    public void addUIMetaData(String var1, ColumnUIMetaData var2) {
        if(this.uimetadata == null) {
            this.uimetadata = new HashMap();
        }

        this.uimetadata.put(var1.toUpperCase(), var2);
        if(this.defaultUIMetaData == null) {
            this.defaultUIMetaData = var2;
        }

    }
}
