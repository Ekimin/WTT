//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.impl;

import java.util.Properties;

public class ManagerInfo {
    private String managerId = null;
    private String managerDescribe = null;
    private String managerClass = null;
    private Properties managerProperties = new Properties();

    public ManagerInfo() {
    }

    public String getManagerId() {
        return this.managerId;
    }

    public void setManagerId(String var1) {
        this.managerId = var1;
    }

    public String getManagerDescribe() {
        return this.managerDescribe;
    }

    public void setManagerDescribe(String var1) {
        this.managerDescribe = var1;
    }

    public String getManagerClass() {
        return this.managerClass;
    }

    public void setManagerClass(String var1) {
        this.managerClass = var1;
    }

    public Properties getManagerProperties() {
        return this.managerProperties;
    }

    public void setProperty(String var1, String var2) {
        this.managerProperties.setProperty(var1, var2);
    }

    public String getProperty(String var1) {
        return (String)this.managerProperties.get(var1);
    }
}
