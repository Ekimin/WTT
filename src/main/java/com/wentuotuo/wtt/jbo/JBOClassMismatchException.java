//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

public class JBOClassMismatchException extends JBOException {
    private static final long serialVersionUID = 1L;
    private String expectClass;
    private String actualClass;

    public String getExpectClass() {
        return this.expectClass;
    }

    public String getActualClass() {
        return this.actualClass;
    }

    public JBOClassMismatchException(BizObjectClass var1, BizObjectClass var2) {
        this(var1 == null?null:var1.getAbsoluteName(), var2 == null?null:var2.getAbsoluteName());
    }

    public JBOClassMismatchException(BizObjectClass var1, String var2) {
        this(var1 == null?null:var1.getAbsoluteName(), var2);
    }

    public JBOClassMismatchException(String var1, BizObjectClass var2) {
        this(var1, var2 == null?null:var2.getAbsoluteName());
    }

    public JBOClassMismatchException(String var1, String var2) {
        super(1304, new String[]{var1, var2});
        this.expectClass = var1;
        this.actualClass = var2;
    }
}
