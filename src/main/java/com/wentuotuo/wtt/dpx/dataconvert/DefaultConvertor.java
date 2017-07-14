//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.dataconvert;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.StringX;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class DefaultConvertor extends AbstractConvertor {
    public static final byte TRIM_SPACE_NONE = 0;
    public static final byte TRIM_SPACE_START = 1;
    public static final byte TRIM_SPACE_END = 2;
    public static final byte TRIM_SPACE_ALL = 3;
    private static SimpleDateFormat amarDate = new SimpleDateFormat("yyyy/MM/dd");
    private Properties prop = null;
    private String defaultValue = null;
    private byte trimSpace = 3;

    public DefaultConvertor(Properties var1) {
        this.prop = var1;
    }

    public DefaultConvertor(Properties var1, String var2) {
        this.prop = var1;
        this.defaultValue = var2;
    }

    public DefaultConvertor(Properties var1, String var2, byte var3) {
        this.prop = var1;
        this.defaultValue = var2;
        if(var3 <= 3 || var3 >= 0) {
            this.setTrimSpace(var3);
        }

    }

    protected void realConvert() {
        String var1 = null;
        switch(this.originalValueType) {
            case 0:
                var1 = this.originalStringValue;
                break;
            case 1:
                var1 = String.valueOf(this.originalIntValue);
                break;
            case 2:
                var1 = String.valueOf(this.originalLongValue);
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
                var1 = String.valueOf(this.originalDoubleValue);
                break;
            case 8:
                var1 = String.valueOf(this.originalBooleanValue);
                break;
            case 16:
                var1 = amarDate.format(this.originalDateValue);
        }

        if(var1 == null) {
            this.convertedStringValue = null;
            this.convertedDateValue = null;
            this.convertedIntValue = 0;
            this.convertedLongValue = 0L;
            this.convertedDoubleValue = 0.0D;
            this.convertedBooleanValue = false;
        } else {
            if(this.trimSpace == 3) {
                var1 = StringX.trimAll(var1);
            } else if(this.trimSpace == 2) {
                var1 = StringX.trimEnd(var1);
            } else if(this.trimSpace == 1) {
                var1 = StringX.trimStart(var1);
            }

            this.convertedStringValue = this.prop.getProperty(var1, this.defaultValue);
            if(this.convertedStringValue == null) {
                this.convertedStringValue = var1;
            }

            try {
                this.convertedIntValue = Integer.parseInt(this.convertedStringValue);
                this.convertedLongValue = Long.parseLong(this.convertedStringValue);
                this.convertedDoubleValue = Double.parseDouble(this.convertedStringValue);
            } catch (NumberFormatException var3) {
                WTT.getLog().debug("Number format invalid:" + this.convertedDoubleValue);
            }

            this.convertedBooleanValue = StringX.parseBoolean(this.convertedStringValue);
            this.convertedDateValue = StringX.parseDate(this.convertedStringValue);
        }
    }

    public void close() {
        super.close();
        this.prop.clear();
    }

    public void setTrimSpace(byte var1) {
        if(var1 >= 0 && var1 <= 3) {
            this.trimSpace = var1;
        }

    }

    public byte getTrimSpace() {
        return this.trimSpace;
    }
}
