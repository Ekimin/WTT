//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.dataconvert;

import java.util.Date;

public abstract class AbstractConvertor implements DataConvertor {
    protected String convertedStringValue;
    protected int convertedIntValue;
    protected double convertedDoubleValue;
    protected boolean convertedBooleanValue;
    protected Date convertedDateValue;
    protected long convertedLongValue;
    protected int originalValueType = 0;
    protected String originalStringValue;
    protected int originalIntValue;
    protected double originalDoubleValue;
    protected boolean originalBooleanValue;
    protected Date originalDateValue;
    protected long originalLongValue;
    private boolean converted = false;

    public AbstractConvertor() {
    }

    public void setValue(String var1) {
        if(!this.converted || var1 == null || this.originalStringValue == null || this.originalValueType != 0 || !var1.equals(this.originalStringValue)) {
            this.originalStringValue = var1;
            this.originalValueType = 0;
            this.converted = false;
        }
    }

    public void setValue(int var1) {
        if(!this.converted || this.originalIntValue != var1 || this.originalValueType != 1) {
            this.originalIntValue = var1;
            this.originalValueType = 1;
            this.converted = false;
        }
    }

    public void setValue(long var1) {
        if(!this.converted || this.originalLongValue != var1 || this.originalValueType != 2) {
            this.originalLongValue = var1;
            this.originalValueType = 2;
            this.converted = false;
        }
    }

    public void setValue(double var1) {
        if(!this.converted || this.convertedDoubleValue != var1 || this.originalValueType != 4) {
            this.originalValueType = 4;
            this.originalDoubleValue = var1;
            this.converted = false;
        }
    }

    public void setValue(boolean var1) {
        if(!this.converted || this.originalBooleanValue != var1 || this.originalValueType != 8) {
            this.originalBooleanValue = var1;
            this.originalValueType = 8;
            this.converted = false;
        }
    }

    public void setValue(Date var1) {
        if(!this.converted || var1 == null || this.originalDateValue == null || this.originalValueType != 16 || !var1.equals(this.originalDateValue)) {
            this.originalDateValue = var1;
            this.originalValueType = 16;
            this.converted = false;
        }
    }

    public long getLong() {
        if(!this.converted) {
            this.realConvert();
            this.converted = true;
        }

        return this.convertedLongValue;
    }

    public String getString() {
        if(!this.converted) {
            this.realConvert();
            this.converted = true;
        }

        return this.convertedStringValue;
    }

    public int getInt() {
        if(!this.converted) {
            this.realConvert();
            this.converted = true;
        }

        return this.convertedIntValue;
    }

    public double getDouble() {
        if(!this.converted) {
            this.realConvert();
            this.converted = true;
        }

        return this.convertedDoubleValue;
    }

    public boolean getBoolean() {
        if(!this.converted) {
            this.realConvert();
            this.converted = true;
        }

        return this.convertedBooleanValue;
    }

    public Date getDate() {
        if(!this.converted) {
            this.realConvert();
            this.converted = true;
        }

        return this.convertedDateValue;
    }

    public void close() {
    }

    protected abstract void realConvert();
}
