//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.dataconvert;

import com.wentuotuo.wtt.WTT;
import java.io.UnsupportedEncodingException;

public class EncodingConvertor extends AbstractConvertor {
    private String fromEncoding = null;
    private String toEncoding = null;

    public EncodingConvertor() {
        this.fromEncoding = System.getProperty("file.encoding");
        this.toEncoding = this.fromEncoding;
    }

    public EncodingConvertor(String var1) {
        this.toEncoding = var1;
    }

    public EncodingConvertor(String var1, String var2) {
        this.toEncoding = var2;
        this.fromEncoding = var1;
    }

    public String getFromEncoding() {
        return this.fromEncoding;
    }

    public String getToEncoding() {
        return this.toEncoding;
    }

    public void setFromEncoding(String var1) {
        this.fromEncoding = var1;
    }

    public void setToEncoding(String var1) {
        this.toEncoding = var1;
    }

    protected void realConvert() {
        this.convertedBooleanValue = this.originalBooleanValue;
        this.convertedDateValue = this.originalDateValue;
        this.convertedDoubleValue = this.originalDoubleValue;
        this.convertedIntValue = this.originalIntValue;
        this.convertedLongValue = this.originalLongValue;
        this.convertedStringValue = this.originalStringValue;
        if(this.originalValueType == 0 && this.fromEncoding != null && this.toEncoding != null && !this.fromEncoding.equalsIgnoreCase(this.toEncoding) && this.convertedStringValue != null) {
            try {
                this.convertedStringValue = new String(this.convertedStringValue.getBytes(this.fromEncoding), this.toEncoding);
            } catch (UnsupportedEncodingException var2) {
                WTT.getLog().debug(var2);
            }
        }

    }
}
