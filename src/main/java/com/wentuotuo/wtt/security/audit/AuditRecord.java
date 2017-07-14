//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.security.audit;

import com.wentuotuo.wtt.security.AppContext;

public class AuditRecord {
    private AppContext appContext = null;
    private String type = null;
    private String target = null;
    private String action = null;
    private AuditData data = null;

    public AuditRecord() {
    }

    public AuditRecord(AppContext var1) {
        this.appContext = var1;
    }

    public AppContext getAppContext() {
        return this.appContext;
    }

    public void setAppContext(AppContext var1) {
        this.appContext = var1;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String var1) {
        this.type = var1;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String var1) {
        this.target = var1;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String var1) {
        this.action = var1;
    }

    public AuditData getData() {
        return this.data;
    }

    public void setData(AuditData var1) {
        this.data = var1;
    }
}
