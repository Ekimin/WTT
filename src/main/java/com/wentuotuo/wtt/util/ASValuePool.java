//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util;

import java.io.Serializable;
import java.util.HashMap;

public class ASValuePool implements Serializable {
    private static final long serialVersionUID = 1L;
    private HashMap htDataSet = null;

    public ASValuePool() {
        this.initPool(100);
    }

    public ASValuePool(int var1) {
        this.initPool(var1);
    }

    private void initPool(int var1) {
        if(this.htDataSet == null) {
            this.htDataSet = new HashMap(var1);
        } else {
            this.htDataSet.clear();
        }

    }

    public void setAttribute(String var1, Object var2) throws Exception {
        this.setAttribute(var1, var2, true);
    }

    public void setAttribute(String var1, Object var2, boolean var3) throws Exception {
        ASVarNode var4 = this.getVarNode(var1);
        if(var4 == null) {
            var4 = new ASVarNode(var1, var2, var3);
            this.htDataSet.put(var1, var4);
        } else {
            var4.setNodeValue(var2);
        }

    }

    public Object getAttribute(String var1) throws Exception {
        ASVarNode var2 = this.getVarNode(var1);
        return var2 == null?null:var2.getNodeValue();
    }

    public boolean isVarNodeNew(String var1) throws Exception {
        ASVarNode var2 = this.getVarNode(var1);
        return var2 == null?true:var2.isVarNodeNew();
    }

    public boolean isVarNodeNoChange(String var1) throws Exception {
        ASVarNode var2 = this.getVarNode(var1);
        return var2 == null?true:var2.isVarNodeNoChange();
    }

    public boolean isVarNodeChange(String var1) throws Exception {
        ASVarNode var2 = this.getVarNode(var1);
        return var2 == null?false:var2.isVarNodeChange();
    }

    public void setVarNodeNew(String var1) throws Exception {
        ASVarNode var2 = this.getVarNode(var1);
        if(var2 != null) {
            var2.setBModify(1);
        }

    }

    public void setVarNodeNoChange(String var1) throws Exception {
        ASVarNode var2 = this.getVarNode(var1);
        if(var2 != null) {
            var2.setBModify(0);
        }

    }

    public void setVarNodeChange(String var1) throws Exception {
        ASVarNode var2 = this.getVarNode(var1);
        if(var2 != null) {
            var2.setBModify(2);
        }

    }

    private ASVarNode getVarNode(String var1) throws Exception {
        Object var2 = this.htDataSet.get(var1);
        return var2 == null?null:(ASVarNode)var2;
    }

    public boolean containsKey(String var1) {
        return this.htDataSet.containsKey(var1);
    }

    public boolean containsValue(Object var1) {
        return this.htDataSet.containsValue(var1);
    }

    public int size() {
        return this.htDataSet.size();
    }

    public boolean isEmpty() {
        return this.htDataSet.isEmpty();
    }

    public ASValuePool clonePool() throws Exception {
        boolean var1 = false;
        ASValuePool var2 = new ASValuePool();
        Object[] var3 = this.getKeys();

        for(int var4 = 0; var4 < var3.length; ++var4) {
            var2.htDataSet.put(var3[var4], this.getVarNode((String)var3[var4]).clone());
        }

        return var2;
    }

    public Object delAttribute(String var1) throws Exception {
        Object var2 = this.getAttribute(var1);
        if(var2 != null) {
            this.htDataSet.remove(var1);
        }

        return var2;
    }

    public void uniteFromValuePool(ASValuePool var1) throws Exception {
        boolean var2 = false;
        Object[] var3 = var1.getKeys();

        for(int var4 = 0; var4 < var3.length; ++var4) {
            this.htDataSet.put(var3[var4], var1.getVarNode((String)var3[var4]).clone());
        }

    }

    public void resetPool() throws Exception {
        this.htDataSet.clear();
    }

    public Object[] getKeys() {
        return (Object[])this.htDataSet.keySet().toArray();
    }

    public String getString(String var1) throws Exception {
        try {
            String var2 = null;
            var2 = (String)this.getAttribute(var1);
            if(var2 == null) {
                var2 = (String)this.getAttribute(var1.toUpperCase());
            }

            return DataConvert.toString(var2);
        } catch (Exception var3) {
            throw new Exception("无法将变量" + var1 + "转换为字符串。");
        }
    }

    public String getNumber(String var1) throws Exception {
        try {
            String var2 = null;
            var2 = (String)this.getAttribute(var1);
            if(var2 == null) {
                var2 = (String)this.getAttribute(var1.toUpperCase());
            }

            if(var2 == null || var2.equals("")) {
                var2 = "null";
            }

            return var2;
        } catch (Exception var3) {
            throw new Exception("无法将变量" + var1 + "转换为字符串。");
        }
    }

    public int getInt(String var1) throws Exception {
        try {
            String var2 = null;
            boolean var3 = false;
            var2 = (String)this.getAttribute(var1);
            if(var2 == null) {
                var2 = (String)this.getAttribute(var1.toUpperCase());
            }

            int var5;
            if(var2 != null && !var2.equals("")) {
                var5 = Integer.parseInt(var2);
            } else {
                var5 = 0;
            }

            return var5;
        } catch (Exception var4) {
            throw new Exception("无法将变量" + var1 + "转换为int。");
        }
    }
}
