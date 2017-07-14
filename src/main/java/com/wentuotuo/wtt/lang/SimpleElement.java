//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.lang;

import java.io.Serializable;

public class SimpleElement implements Element, Serializable {
    private static final long serialVersionUID = 1L;
    private String name = null;
    private String label = null;
    private Object value = null;
    private String id = null;
    private static long idCount = 0L;

    public SimpleElement() {
    }

    public SimpleElement(String var1) {
        this.name = var1;
    }

    public SimpleElement(String var1, String var2) {
        this.name = var1;
        this.label = var2;
    }

    public String getId() {
        if(this.id == null) {
            this.id = String.valueOf((long)(idCount++));
        }

        return this.id;
    }

    public String getName() {
        return this.name == null?this.getId():this.name;
    }

    public void setName(String var1) {
        this.name = var1;
    }

    public String getLabel() {
        return this.label == null?this.getName():this.label;
    }

    public void setLabel(String var1) {
        this.label = var1;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object var1) {
        this.value = var1;
    }

    public boolean isNull() {
        return this.value == null;
    }

    public void setNull() {
        this.value = null;
    }
}
