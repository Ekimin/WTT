//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

public class BizObjectPackage {
    private String name = "";
    private String label = null;

    public BizObjectPackage(String var1, String var2) {
        this.name = var1;
        this.label = var2;
    }

    public BizObjectPackage(String var1) {
        this.name = var1;
    }

    public String getLabel() {
        return this.label == null?this.name:this.label;
    }

    public void setLabel(String var1) {
        this.label = var1;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "[" + this.getName() + "," + this.getLabel() + "]";
    }
}
