//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.security.audit;

public abstract class Handler {
    private Filter filter = null;

    public Handler() {
    }

    public Filter getFilter() {
        return this.filter;
    }

    public void setFilter(Filter var1) {
        this.filter = var1;
    }

    public abstract void handle(AuditRecord var1);

    public abstract void close();
}
