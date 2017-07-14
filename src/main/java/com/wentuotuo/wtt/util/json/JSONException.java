//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.json;

public class JSONException extends Exception {
    private static final long serialVersionUID = 1L;

    public JSONException(String var1) {
        super(var1);
    }

    public JSONException(Exception var1, String var2) {
        super(var2, var1);
    }

    public JSONException(int var1, String var2, char var3) {
        this("Json syntax error at " + var1 + ", expect [" + var2 + "], but get [" + var3 + "]");
    }
}
