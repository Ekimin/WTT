//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

public class Parameter extends BaseElement {
    protected Parameter(String var1) {
        super(6, var1);
    }

    public String getName() {
        return this.getOfficalExpression().substring(1);
    }
}
