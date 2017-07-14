//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util;

import java.io.Serializable;

public class Property implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final byte DISPLAYFORMAT_KEY_ONLY = 0;
    public static final byte DISPLAYFORMAT_VALUE_ONLY = 1;
    public static final byte DISPLAYFORMAT_KEY_VALUE = 2;
    public static final byte DISPLAYFORMAT_KEY_VALUE_DOT = 3;
    public static final byte DISPLAYFORMAT_KEY_VALUE_DASH = 4;
    public static final byte DISPLAYFORMAT_KEY_VALUE_LSLASH = 5;
    public static final byte DISPLAYFORMAT_KEY_VALUE_RSLASH = 6;
    public static final byte DISPLAYFORMAT_KEY_VALUE_LPWTTNTHESIS = 7;
    public static final byte DISPLAYFORMAT_KEY_VALUE_RPWTTNTHESIS = 8;
    public static final byte DISPLAYFORMAT_KEY_VALUE_LBRACKET = 9;
    public static final byte DISPLAYFORMAT_KEY_VALUE_RBRACKET = 10;
    public static final byte DISPLAYFORMAT_VALUE_KEY = 102;
    public static final byte DISPLAYFORMAT_VALUE_KEY_DOT = 103;
    public static final byte DISPLAYFORMAT_VALUE_KEY_DASH = 104;
    public static final byte DISPLAYFORMAT_VALUE_KEY_LSLASH = 105;
    public static final byte DISPLAYFORMAT_VALUE_KEY_RSLASH = 106;
    public static final byte DISPLAYFORMAT_VALUE_KEY_LPWTTNTHESIS = 107;
    public static final byte DISPLAYFORMAT_VALUE_KEY_RPWTTNTHESIS = 108;
    public static final byte DISPLAYFORMAT_VALUE_KEY_LBRACKET = 109;
    public static final byte DISPLAYFORMAT_VALUE_KEY_RBRACKET = 110;
    private byte displayFormat = 0;
    public Object key;
    public Object value;

    public Property() {
        this.key = null;
        this.value = null;
    }

    public Property(Object var1, Object var2) {
        this.key = var1;
        this.value = var2;
    }

    public Property(Object var1, Object var2, byte var3) {
        this.key = var1;
        this.value = var2;
        this.displayFormat = var3;
    }

    public Object getKey() {
        return this.key;
    }

    public Property setKey(Object var1) {
        this.key = var1;
        return this;
    }

    public Object getValue() {
        return this.value;
    }

    public Property setValue(Object var1) {
        this.value = var1;
        return this;
    }

    public Property setNameAndValue(Object var1, Object var2) {
        this.key = var1;
        this.value = var2;
        return this;
    }

    public Property setDisplayFormat(byte var1) {
        this.displayFormat = var1;
        return this;
    }

    public String toString() {
        switch(this.displayFormat) {
            case 0:
                return this.key.toString();
            case 1:
                return this.value.toString();
            case 2:
                return this.key.toString() + this.value.toString();
            case 3:
                return this.key.toString() + "." + this.value.toString();
            case 4:
                return this.key.toString() + "-" + this.value.toString();
            case 5:
                return this.key.toString() + "/" + this.value.toString();
            case 6:
                return this.key.toString() + "\\" + this.value.toString();
            case 7:
                return this.key.toString() + "(" + this.value.toString() + ")";
            case 8:
                return "(" + this.key.toString() + ")" + this.value.toString();
            case 9:
                return this.key.toString() + "[" + this.value.toString() + "]";
            case 10:
                return "[" + this.key.toString() + "]" + this.value.toString();
            case 102:
                return this.value.toString() + this.key.toString();
            case 103:
                return this.value.toString() + "." + this.key.toString();
            case 104:
                return this.value.toString() + "-" + this.key.toString();
            case 105:
                return this.value.toString() + "/" + this.key.toString();
            case 106:
                return this.value.toString() + "\\" + this.key.toString();
            case 107:
                return this.value.toString() + "(" + this.key.toString() + ")";
            case 108:
                return "(" + this.value.toString() + ")" + this.key.toString();
            case 109:
                return this.value.toString() + "[" + this.key.toString() + "]";
            case 110:
                return "[" + this.value.toString() + "]" + this.key.toString();
            default:
                return this.key.toString();
        }
    }
}
