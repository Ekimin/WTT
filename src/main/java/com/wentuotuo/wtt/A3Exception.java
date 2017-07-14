//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt;

import java.text.MessageFormat;

public abstract class A3Exception extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private Object[] arguments;

    public A3Exception(String var1) {
        this.errorCode = var1;
    }

    public A3Exception(String var1, Throwable var2) {
        super(var2);
        this.errorCode = var1;
    }

    public A3Exception(String var1, Object[] var2) {
        this.errorCode = var1;
        this.arguments = var2;
    }

    public A3Exception(String var1, Object[] var2, Throwable var3) {
        super(var3);
        this.errorCode = var1;
        this.arguments = var2;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public final Object[] getArguments() {
        return this.arguments;
    }

    public final void setArguments(Object[] var1) {
        this.arguments = var1;
    }

    public abstract String getErrorMessage();

    public String getMessage() {
        String var1 = this.getErrorMessage();
        if(this.arguments != null) {
            var1 = MessageFormat.format(var1, this.arguments);
        }

        return this.getErrorCode() + ": " + var1;
    }
}
