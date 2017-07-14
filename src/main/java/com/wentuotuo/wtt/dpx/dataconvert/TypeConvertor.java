//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.dataconvert;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.StringX;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TypeConvertor extends AbstractConvertor {
    public TypeConvertor() {
    }

    protected void realConvert() {
        switch(this.originalValueType) {
            case 0:
                if(this.originalStringValue == null) {
                    this.convertedStringValue = null;
                    this.convertedDateValue = null;
                    this.convertedBooleanValue = false;
                    this.convertedIntValue = 0;
                    this.convertedLongValue = 0L;
                    this.convertedDoubleValue = 0.0D;
                } else {
                    this.convertedStringValue = this.originalStringValue;
                    this.convertedDateValue = StringX.parseDate(this.convertedStringValue);
                    this.convertedBooleanValue = StringX.parseBoolean(this.convertedStringValue);
                    Double var1 = null;

                    try {
                        var1 = Double.valueOf(this.convertedStringValue);
                    } catch (NumberFormatException var3) {
                        WTT.getLog().debug("Number format invalid:" + this.convertedStringValue);
                        var1 = new Double(0.0D);
                    }

                    this.convertedIntValue = var1.intValue();
                    this.convertedLongValue = var1.longValue();
                    this.convertedDoubleValue = var1.doubleValue();
                }
                break;
            case 1:
                this.convertedStringValue = String.valueOf(this.originalIntValue);
                this.convertedIntValue = this.originalIntValue;
                this.convertedLongValue = (long)this.originalIntValue;
                this.convertedDoubleValue = (double)this.originalIntValue;
                this.convertedDateValue = new Date((long)this.originalIntValue);
                this.convertedBooleanValue = this.originalIntValue != 0;
                break;
            case 2:
                this.convertedStringValue = String.valueOf(this.originalLongValue);
                this.convertedIntValue = (int)this.originalLongValue;
                this.convertedLongValue = this.originalLongValue;
                this.convertedDoubleValue = (double)this.originalLongValue;
                this.convertedBooleanValue = this.originalLongValue != 0L;
                this.convertedDateValue = new Date(this.originalLongValue);
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
                this.convertedStringValue = String.valueOf(this.originalDoubleValue);
                this.convertedIntValue = (int)this.originalDoubleValue;
                this.convertedLongValue = (long)this.originalDoubleValue;
                this.convertedDoubleValue = this.originalDoubleValue;
                this.convertedBooleanValue = this.originalDoubleValue != 0.0D;
                this.convertedDateValue = new Date(this.convertedLongValue);
                break;
            case 8:
                this.convertedStringValue = String.valueOf(this.originalBooleanValue);
                this.convertedIntValue = this.originalBooleanValue?1:0;
                this.convertedLongValue = (long)this.convertedIntValue;
                this.convertedDoubleValue = (double)this.convertedIntValue;
                this.convertedBooleanValue = this.originalBooleanValue;
                this.convertedDateValue = new Date((long)this.convertedIntValue);
                break;
            case 16:
                if(this.originalDateValue == null) {
                    this.convertedStringValue = null;
                    this.convertedDateValue = null;
                    this.convertedBooleanValue = false;
                    this.convertedIntValue = 0;
                    this.convertedLongValue = 0L;
                    this.convertedDoubleValue = 0.0D;
                } else {
                    this.convertedDateValue = this.originalDateValue;
                    this.convertedStringValue = (new SimpleDateFormat("yyyy/MM/dd")).format(this.originalDateValue);
                    this.convertedLongValue = this.originalDateValue.getTime();
                    this.convertedIntValue = (int)this.convertedLongValue;
                    this.convertedDoubleValue = (double)this.convertedLongValue;
                    this.convertedBooleanValue = this.convertedLongValue != 0L;
                }
        }

    }
}
