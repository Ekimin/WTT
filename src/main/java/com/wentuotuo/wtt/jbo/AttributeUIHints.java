//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import java.io.Serializable;
import java.util.Properties;

public class AttributeUIHints implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int VALUE_CHARACTER_UNLIMIT = 0;
    public static final int VALUE_CHARACTER_CODETABLE = 1;
    public static final int VALUE_CHARACTER_RANGE = 2;
    public static final int VALUE_CHARACTER_BOOLEAN = 4;
    private static final int MASK_VALUECHARACTER = 7;
    private static final int MASK_DISPLAYONLY = 8;
    private static final int MASK_REQUIRED = 16;
    private static final int MASK_SORTINGSUIETABLE = 32;
    private static final int MASK_FILTERSUIETABLE = 64;
    private static final int MASK_ALL = -1;
    private static final int MASK_NONE = 0;
    private int hintbits = 96;
    private String css = null;
    private String displayFormat = null;
    private String inputMask = null;
    private String codeTable = null;
    private Object[] boundValues = null;
    private String validator = null;
    private Properties customerHints = null;

    public AttributeUIHints() {
    }

    public int getValueCharacter() {
        return this.hintbits & 7;
    }

    public int getValueCharater() {
        return this.hintbits & 7;
    }

    public void setValueCharacter(int var1) {
        if(var1 >= 0 && var1 <= 7) {
            this.hintbits = this.hintbits & -8 | var1;
        }
    }

    public String getCodeTable() {
        return this.codeTable;
    }

    public void setCodeTable(String var1) {
        this.codeTable = var1;
    }

    public Object[] getBoundValues() {
        return this.boundValues;
    }

    public void setBoundValues(Object var1, Object var2) {
        this.boundValues = new Object[]{var1, var2};
    }

    public String getInputMask() {
        return this.inputMask;
    }

    public void setInputMask(String var1) {
        this.inputMask = var1;
    }

    public String getDisplayFormat() {
        return this.displayFormat;
    }

    public void setDisplayFormat(String var1) {
        this.displayFormat = var1;
    }

    public String getCss() {
        return this.css;
    }

    public void setCss(String var1) {
        this.css = var1;
    }

    public boolean isDisplayOnly() {
        return this.getBit(8);
    }

    public void setDisplayOnly(boolean var1) {
        this.setBit(8, var1);
    }

    public boolean isRequired() {
        return this.getBit(16);
    }

    public void setRequired(boolean var1) {
        this.setBit(16, var1);
    }

    public boolean isSortingSuitable() {
        return this.getBit(32);
    }

    public void setSortingSuitable(boolean var1) {
        this.setBit(32, var1);
    }

    public boolean isFilterSuitable() {
        return this.getBit(64);
    }

    public void setFilterSuitable(boolean var1) {
        this.setBit(64, var1);
    }

    public String getValidator() {
        return this.validator;
    }

    public void setValidator(String var1) {
        this.validator = var1;
    }

    public String getCustomerHint(String var1) {
        return this.customerHints != null && var1 != null?this.customerHints.getProperty(var1):null;
    }

    public void setCustomerHint(String var1, String var2) {
        if(this.customerHints == null) {
            this.customerHints = new Properties();
        }

        this.customerHints.setProperty(var1, var2);
    }

    private void setBit(int var1, boolean var2) {
        if(var2) {
            this.hintbits |= var1;
        } else {
            this.hintbits &= ~var1;
        }

    }

    private boolean getBit(int var1) {
        return (var1 & this.hintbits) > 0;
    }
}
