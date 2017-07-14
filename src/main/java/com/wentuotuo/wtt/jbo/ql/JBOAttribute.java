//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

public class JBOAttribute extends BaseElement {
    public static final int SELECT_LIST = 0;
    public static final int UPDATE_LIST = 1;
    public static final int QUERY_EXPRESSION = 2;
    public static final int ORDER_LIST = 3;
    public static final int GROUP_LIST = 4;
    public static final int JOIN_EXPRESSION = 5;
    public static final int ATTRIBUTE_TYPE_MAJOR = 0;
    public static final int ATTRIBUTE_TYPE_EXTEND = 1;
    public static final int ATTRIBUTE_TYPE_VIRTUAL = 2;
    private String name = null;
    private String classAlias = null;
    private String label = null;
    private int attributeType = 1;
    private int usedIn = -1;
    private JBOClass jboClass = null;
    private boolean expression = false;

    public JBOAttribute(String var1) {
        super(5, var1);
        this.parse(var1);
    }

    public String getName() {
        return this.name;
    }

    public String getClassAlias() {
        return this.classAlias;
    }

    public String getOfficalExpression() {
        return this.getClassAlias() + "." + this.getName();
    }

    protected void parse(String var1) {
        String var2 = var1;
        if(var1.charAt(0) == 34) {
            var2 = var1.substring(1, var1.length() - 1);
            this.expression = true;
        }

        int var3 = var2.indexOf(46);
        if(var3 < 0) {
            this.classAlias = "O";
            this.name = var2;
        } else {
            this.classAlias = var2.substring(0, var3).toUpperCase();
            if(var3 == var2.length()) {
                this.name = "";
            }

            this.name = var2.substring(var3 + 1, var2.length());
        }

        if(this.classAlias.equals("O")) {
            this.attributeType = 0;
        } else {
            int var4;
            if(this.classAlias.equals("V")) {
                this.attributeType = 2;
                if(this.name.charAt(this.name.length() - 1) == 125) {
                    var4 = this.name.indexOf(123);
                    if(var4 > 0) {
                        this.setLabel(this.name.substring(var4 + 1, this.name.length() - 1));
                        this.name = this.name.substring(0, var4);
                    } else {
                        this.setLabel(this.name);
                    }
                }
            } else {
                this.attributeType = 1;
                if(this.name.charAt(this.name.length() - 1) == 125) {
                    var4 = this.name.indexOf(123);
                    if(var4 > 0) {
                        this.setLabel(this.name.substring(var4 + 1, this.name.length() - 1));
                        this.name = this.name.substring(0, var4);
                    } else {
                        this.setLabel((String)null);
                    }
                }
            }
        }

    }

    public int getUsedIn() {
        return this.usedIn;
    }

    public void setUsedIn(int var1) {
        this.usedIn = var1;
    }

    public JBOClass getJboClass() {
        return this.jboClass;
    }

    public void setJboClass(JBOClass var1) {
        this.jboClass = var1;
    }

    public boolean isExpression() {
        return this.expression;
    }

    public int getAttributeType() {
        return this.attributeType;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String var1) {
        this.label = var1;
    }
}
