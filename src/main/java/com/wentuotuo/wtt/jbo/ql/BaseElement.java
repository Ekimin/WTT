//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

public class BaseElement implements Element {
    private String expression = null;
    private int type = -1;

    protected BaseElement(int var1, String var2) {
        this.type = var1;
        this.expression = var2;
    }

    public int getType() {
        return this.type;
    }

    public String getExpression() {
        return this.expression;
    }

    public String getOfficalExpression() {
        return this.getExpression();
    }

    public String toString() {
        return this.getExpression();
    }

    public static boolean isValidJBOIdentfier(String var0) {
        return var0.matches("[a-zA-Z]\\w*");
    }
}
