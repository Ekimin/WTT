//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util;

import java.io.Serializable;

public class ASVarNode implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sVarKey = null;
    private Object oVarValue = null;
    private int iVarType = 0;
    private int bModify = 0;
    public static final int VARNODE_NOCHANGE = 0;
    public static final int VARNODE_NEWVALUE = 1;
    public static final int VARNODE_CHANGE = 2;
    public static final int VARTYPE_OBJECT = 0;
    public static final int VARTYPE_STRING = 1;
    public static final int VARTYPE_INT = 2;

    public ASVarNode(String var1, Object var2) {
        this.initNode(var1, var2, true);
    }

    public ASVarNode(String var1, Object var2, boolean var3) {
        this.initNode(var1, var2, var3);
    }

    private void initNode(String var1, Object var2, boolean var3) {
        this.sVarKey = var1;
        this.oVarValue = var2;
        if(var3) {
            this.setBModify(1);
        }

    }

    public Object clone() {
        ASVarNode var1 = new ASVarNode(this.sVarKey, this.oVarValue);
        var1.setBModify(this.bModify);
        var1.setIVarType(this.iVarType);
        return var1;
    }

    public void setNodeValue(Object var1) {
        this.oVarValue = var1;
        if(this.isVarNodeNoChange()) {
            this.setBModify(2);
        }

    }

    public Object getNodeValue() {
        return this.oVarValue;
    }

    public boolean isVarNodeNew() {
        return this.bModify == 1;
    }

    public boolean isVarNodeNoChange() {
        return this.bModify == 0;
    }

    public boolean isVarNodeChange() {
        return this.bModify == 2;
    }

    public int hashCode() {
        return this.sVarKey.hashCode();
    }

    public String toString() {
        return this.oVarValue.toString();
    }

    public int getBModify() {
        return this.bModify;
    }

    public void setBModify(int var1) {
        this.bModify = var1;
    }

    private void setIVarType(int var1) {
        this.iVarType = var1;
    }
}
