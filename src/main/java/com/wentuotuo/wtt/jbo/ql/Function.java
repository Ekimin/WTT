//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

public class Function extends BaseElement {
    private int functionId = -1;

    protected Function(String var1) {
        super(3, var1);
        this.functionId = Parser.getFunctionId(var1);
    }

    public String getOfficalExpression() {
        return Parser.getFunction(this.functionId);
    }
}
