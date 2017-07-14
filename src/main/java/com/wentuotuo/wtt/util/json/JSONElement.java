//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.json;

import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.SimpleElement;

public class JSONElement extends SimpleElement {
    private static final long serialVersionUID = 1L;
    public static final byte STRING = 0;
    public static final byte NUMBER = 1;
    public static final byte BOOLEAN = 2;
    public static final byte NULL = 3;
    public static final byte OBJECT = 4;
    public static final byte ARRAY = 5;
    private byte type = 0;

    public JSONElement() {
        this.type = 3;
    }

    public JSONElement(byte var1) {
        if(var1 >= 0 && var1 <= 5) {
            this.type = var1;
        } else {
            this.type = 3;
        }

    }

    public JSONElement(String var1) {
        super(var1);
    }

    public JSONElement(String var1, byte var2) {
        super(var1);
        if(var2 >= 0 && var2 <= 5) {
            this.type = var2;
        } else {
            this.type = 3;
        }

    }

    public byte getType() {
        return this.type;
    }

    public boolean isNull() {
        return this.type == 3 || super.isNull();
    }

    public static JSONElement valueOf(String var0, String var1) {
        JSONElement var2 = new JSONElement(var0, (byte)0);
        var2.setValue(var1);
        return var2;
    }

    public static JSONElement valueOf(String var0, double var1) {
        JSONElement var3 = new JSONElement(var0, (byte)1);
        var3.setValue(new Double(var1));
        return var3;
    }

    public static JSONElement valueOf(String var0, int var1) {
        JSONElement var2 = new JSONElement(var0, (byte)1);
        var2.setValue(new Integer(var1));
        return var2;
    }

    public static JSONElement valueOf(String var0, boolean var1) {
        JSONElement var2 = new JSONElement(var0, (byte)2);
        var2.setValue(new Boolean(var1));
        return var2;
    }

    public static JSONElement nullElement(String var0) {
        JSONElement var1 = new JSONElement(var0, (byte)3);
        return var1;
    }

    public static JSONElement valueOf(String var0) {
        JSONElement var1 = new JSONElement((byte)0);
        var1.setValue(var0);
        return var1;
    }

    public static JSONElement valueOf(double var0) {
        JSONElement var2 = new JSONElement((byte)1);
        var2.setValue(new Double(var0));
        return var2;
    }

    public static JSONElement valueOf(int var0) {
        JSONElement var1 = new JSONElement((byte)1);
        var1.setValue(new Integer(var0));
        return var1;
    }

    public static JSONElement valueOf(boolean var0) {
        JSONElement var1 = new JSONElement((byte)2);
        var1.setValue(new Boolean(var0));
        return var1;
    }

    public static JSONElement valueOf(DataElement var0) {
        if(var0 == null) {
            return createNull();
        } else if(var0.isNull()) {
            return createNull(var0.getName());
        } else {
            byte var1 = var0.getType();
            JSONElement var2 = null;
            switch(var1) {
                case 0:
                case 16:
                    var2 = new JSONElement(var0.getName(), (byte)0);
                    var2.setValue(var0.getString());
                    break;
                case 1:
                    var2 = new JSONElement(var0.getName(), (byte)1);
                    var2.setValue(new Integer(var0.getInt()));
                    break;
                case 2:
                    var2 = new JSONElement(var0.getName(), (byte)1);
                    var2.setValue(new Long(var0.getLong()));
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
                    var2 = new JSONElement(var0.getName(), (byte)1);
                    var2.setValue(new Double(var0.getDouble()));
                    break;
                case 8:
                    var2 = new JSONElement(var0.getName(), (byte)2);
                    var2.setValue(new Boolean(var0.getBoolean()));
            }

            return var2;
        }
    }

    public static JSONElement valueOf(JSONObject var0) {
        if(var0 == null) {
            return createNull();
        } else {
            JSONElement var1 = null;
            if(var0.getType() == 5) {
                var1 = new JSONElement((byte)5);
            } else {
                var1 = new JSONElement((byte)4);
            }

            var1.setValue(var0);
            return var1;
        }
    }

    public static JSONElement valueOf(String var0, JSONObject var1) {
        if(var1 == null) {
            return createNull(var0);
        } else {
            JSONElement var2 = null;
            if(var1.getType() == 5) {
                var2 = new JSONElement(var0, (byte)5);
            } else {
                var2 = new JSONElement(var0, (byte)4);
            }

            var2.setValue(var1);
            return var2;
        }
    }

    public static JSONElement valueOf(Object var0) {
        JSONElement var1 = new JSONElement((byte)4);
        var1.setValue(var0);
        return var1;
    }

    public static JSONElement valueOf(String var0, Object var1) {
        JSONElement var2 = valueOf(var1);
        if(var2 != null) {
            var2.setName(var0);
        }

        return var2;
    }

    public static JSONElement createNull() {
        JSONElement var0 = new JSONElement((byte)3);
        return var0;
    }

    public static JSONElement createNull(String var0) {
        JSONElement var1 = new JSONElement(var0, (byte)3);
        return var1;
    }
}
