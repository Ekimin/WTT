//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

public class Operator extends BaseElement {
    private static char[] operatorChars = new char[]{'(', ')', '*', '+', ',', '-', '/', '<', '=', '>'};

    protected Operator(String var1) {
        super(2, var1);
    }

    public static boolean isOperatorChar(char var0) {
        if(var0 >= 40 && var0 <= 62) {
            for(int var1 = 0; var1 < operatorChars.length; ++var1) {
                if(var0 == operatorChars[var1]) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public static boolean isOperator(String var0) {
        boolean var1 = false;
        if(var0.length() == 1) {
            var1 = isOperatorChar(var0.charAt(0));
        } else if(var0.length() == 2) {
            char var2 = var0.charAt(0);
            char var3 = var0.charAt(1);
            var1 = var2 == 62 && var3 == 61 || var2 == 60 && (var3 == 61 || var3 == 62);
        }

        return var1;
    }
}
