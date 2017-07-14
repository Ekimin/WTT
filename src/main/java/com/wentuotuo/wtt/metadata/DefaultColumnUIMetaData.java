//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.metadata;

import com.wentuotuo.wtt.sql.SQLConstants;
import java.util.Properties;

public class DefaultColumnUIMetaData extends DefaultMetaDataObject implements ColumnUIMetaData {
    private int bitSet;
    private int hAlignment;
    private int vAlignment;
    private int valueCharacter;
    private String inputMask;
    private String displayFormat;
    private String css;
    private String valueCodetable;
    private String valueRange;
    private String valueList;
    private Properties extendProperties;
    private static final int BIT_KEY = 1;
    private static final int BIT_READONLY = 2;
    private static final int BIT_VISIBLE = 4;
    private static final int BIT_REQUIRED = 8;
    private static final int BIT_SORTABLE = 16;
    private static final int BIT_ALL = 31;
    private static final int BIT_NONE = 0;

    public DefaultColumnUIMetaData() {
        this.bitSet = 0;
        this.hAlignment = 1;
        this.vAlignment = 4;
        this.valueCharacter = 0;
        this.inputMask = null;
        this.displayFormat = null;
        this.css = null;
        this.bitSet = 28;
    }

    public DefaultColumnUIMetaData(ColumnMetaData var1) {
        this();
        byte var2 = SQLConstants.SQLTypeToBaseType(var1.getType());
        switch(var2) {
            case 1:
            case 2:
                this.hAlignment = 2;
                this.inputMask = "000000";
                this.displayFormat = "##,###,##0";
                break;
            case 4:
                this.hAlignment = 2;
                this.inputMask = "000000";
                this.displayFormat = "##,###,##0.00";
                break;
            case 8:
                this.hAlignment = 0;
                this.valueCharacter = 3;
                break;
            case 16:
                this.hAlignment = 0;
                this.valueCharacter = 1;
                this.inputMask = "0000/00/00";
                this.displayFormat = "yyyy/MM/dd";
        }

    }

    public void setKey(boolean var1) {
        this.setBit(1, var1);
    }

    public void setReadOnly(boolean var1) {
        this.setBit(2, var1);
    }

    public void setVisible(boolean var1) {
        this.setBit(4, var1);
    }

    public void setRequried(boolean var1) {
        this.setBit(8, var1);
    }

    public void setSortable(boolean var1) {
        this.setBit(16, var1);
    }

    public boolean isKey() {
        return this.getBit(1);
    }

    public boolean isReadOnly() {
        return this.getBit(2);
    }

    public boolean isVisible() {
        return this.getBit(4);
    }

    public boolean isRequired() {
        return this.getBit(8);
    }

    public boolean isSortable() {
        return this.getBit(16);
    }

    private void setBit(int var1, boolean var2) {
        if(var2) {
            this.bitSet |= var1;
        } else {
            this.bitSet &= var1 ^ 31;
        }

    }

    private boolean getBit(int var1) {
        return (var1 & this.bitSet) > 0;
    }

    public int getHAlignment() {
        return this.hAlignment;
    }

    public int getVAlignment() {
        return this.vAlignment;
    }

    public int getValueCharacter() {
        return this.valueCharacter;
    }

    public String getInputMask() {
        return this.inputMask;
    }

    public boolean matchMask(String var1) {
        return false;
    }

    public String getDisplayFormat() {
        return this.displayFormat;
    }

    public String getCSS() {
        return this.css;
    }

    public String getProperty(String var1) {
        return this.extendProperties != null && var1 != null?this.extendProperties.getProperty(var1):null;
    }

    public void setProperty(String var1, String var2) {
        if(this.extendProperties == null) {
            this.extendProperties = new Properties();
        }

        this.extendProperties.setProperty(var1, var2);
    }

    public final void setCss(String var1) {
        this.css = var1;
    }

    public final void setDisplayFormat(String var1) {
        this.displayFormat = var1;
    }

    public final void setHAlignment(int var1) {
        this.hAlignment = var1;
    }

    public final void setInputMask(String var1) {
        this.inputMask = var1;
    }

    public final void setVAlignment(int var1) {
        this.vAlignment = var1;
    }

    public final void setValueCharacter(int var1) {
        this.valueCharacter = var1;
    }

    public final String getValueCodetable() {
        return this.valueCodetable;
    }

    public final void setValueCodetable(String var1) {
        this.valueCodetable = var1;
    }

    public final String getValueList() {
        return this.valueList;
    }

    public final void setValueList(String var1) {
        this.valueList = var1;
    }

    public final String getValueRange() {
        return this.valueRange;
    }

    public final void setValueRange(String var1) {
        this.valueRange = var1;
    }
}
