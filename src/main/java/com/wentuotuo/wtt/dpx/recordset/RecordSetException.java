//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

public class RecordSetException extends Exception {
    private static final long serialVersionUID = 1L;
    protected Throwable cause;

    public RecordSetException() {
        this.cause = null;
    }

    public RecordSetException(String var1) {
        super(var1);
        this.cause = null;
    }

    public RecordSetException(Throwable var1) {
        this(var1 == null?null:var1.toString(), var1);
    }

    public RecordSetException(String var1, Throwable var2) {
        super(var1 + " (Caused by " + var2 + ")");
        this.cause = null;
        this.cause = var2;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
