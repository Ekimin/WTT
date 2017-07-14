//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt;

public class ASException extends WTTException {
    private int ErrorLevel = 1;
    private static final long serialVersionUID = 1L;

    public ASException(String var1) {
        super(var1);
    }

    public ASException(String var1, int var2) {
        super(var1);
        this.ErrorLevel = var2;
    }

    public String getMessage() {
        return "<br>" + super.getMessage();
    }

    public int getErrorLevel() {
        return this.ErrorLevel;
    }
}
