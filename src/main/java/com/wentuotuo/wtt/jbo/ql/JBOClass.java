//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

public class JBOClass extends BaseElement {
    private String name = null;
    private String alias = null;
    private boolean majorClass = false;

    protected JBOClass(String var1) {
        super(4, var1);
        this.parse(this.getExpression());
    }

    protected void parse(String var1) {
        String[] var2 = var1.split("\\s+");
        if(var2.length == 1) {
            if(var2[0].equalsIgnoreCase("O")) {
                this.alias = "O";
            }
        } else {
            this.name = var2[0];
            this.alias = var2[var2.length - 1].toUpperCase();
        }

        this.majorClass = this.alias.equals("O");
    }

    public String getOfficalExpression() {
        return this.getName();
    }

    public String getName() {
        return this.name;
    }

    public String getAlias() {
        return this.alias;
    }

    public boolean isMajorClass() {
        return this.majorClass;
    }

    protected void setName(String var1) {
        this.name = var1;
    }
}
