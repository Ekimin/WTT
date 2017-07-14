//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

public class Constant extends BaseElement {
    public static final int CONSTANT_TYPE_STRING = 0;
    public static final int CONSTANT_TYPE_NUMBER = 1;
    int constantType = 0;

    protected Constant(String var1) {
        super(1, var1);
        this.constantType = var1.startsWith("'")?0:1;
    }

    public int getConstantType() {
        return this.constantType;
    }
}
