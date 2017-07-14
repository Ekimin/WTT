//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

public class KeyWord extends BaseElement {
    private int keyWordId = -1;

    protected KeyWord(String var1) {
        super(0, var1);
        this.keyWordId = Parser.getKeyWordId(var1);
    }

    public String getOfficalExpression() {
        return Parser.getKeyWord(this.keyWordId);
    }
}
