//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.xml;

import com.wentuotuo.wtt.lang.StringX;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Attribute {
    private String name = null;
    private String value = null;

    public Attribute(String var1) {
        this.name = var1;
    }

    public Attribute(String var1, String var2) {
        this.name = var1;
        this.value = var2;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String var1) {
        this.value = var1;
    }

    public final int getValue(int var1) {
        int var2 = var1;
        if(this.value != null) {
            try {
                var2 = Integer.parseInt(this.value);
            } catch (Exception var4) {
                var2 = var1;
            }
        }

        return var2;
    }

    public final double getValue(double var1) {
        double var3 = var1;
        if(this.value != null) {
            try {
                var3 = Double.parseDouble(this.value);
            } catch (Exception var6) {
                var3 = var1;
            }
        }

        return var3;
    }

    public final boolean getValue(boolean var1) {
        boolean var2 = var1;
        if(this.value != null) {
            var2 = StringX.parseBoolean(this.value);
        }

        return var2;
    }

    public final Date getValue(Date var1) {
        Date var2 = var1;
        if(this.value != null) {
            var2 = StringX.parseDate(this.value);
        }

        return var2;
    }

    public final Date getValue(Date var1, String var2) {
        Date var3 = var1;
        if(this.value != null) {
            SimpleDateFormat var4 = new SimpleDateFormat(var2);

            try {
                var3 = var4.parse(this.value);
            } catch (Exception var6) {
                var3 = var1;
            }
        }

        return var3;
    }

    public final String getValue(String var1) {
        return this.value == null?var1:this.value;
    }
}
