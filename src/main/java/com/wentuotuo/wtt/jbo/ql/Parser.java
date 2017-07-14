//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.jbo.BizObjectClass;
import com.wentuotuo.wtt.jbo.JBOException;
import com.wentuotuo.wtt.lang.ObjectX;
import com.wentuotuo.wtt.lang.StringX;

public abstract class Parser {
    public static final String DEFAULT_PARSER = "com.wentuotuo.wtt.jbo.ql.DefaultParser";
    private static ReservedWordDictionary keyWords = null;
    private static ReservedWordDictionary functions = null;

    public Parser() {
    }

    public abstract void parse(BizObjectClass var1, String var2) throws JBOException;

    public abstract BizObjectClass getBizObjectClass();

    public abstract int getQueryType();

    public abstract String getStatement();

    public abstract JBOClass getMajorClass();

    public abstract Element[] getElementSequence();

    public static KeyWord createKeyWord(String var0) {
        KeyWord var1 = new KeyWord(var0);
        return var1;
    }

    public static JBOClass createJBOClass(String var0) throws JBOException {
        JBOClass var1 = new JBOClass(var0);
        return var1;
    }

    public static JBOAttribute createJBOAttribute(String var0) {
        JBOAttribute var1 = new JBOAttribute(var0);
        return var1;
    }

    public static Parameter createParameter(String var0) {
        Parameter var1 = new Parameter(var0);
        return var1;
    }

    public static Function createJBOFunction(String var0) {
        Function var1 = new Function(var0);
        return var1;
    }

    public static Constant createConstant(String var0) {
        Constant var1 = new Constant(var0);
        return var1;
    }

    public static Operator createOperator(String var0) {
        Operator var1 = new Operator(var0);
        return var1;
    }

    public static final Parser createParser() {
        Object var0 = null;
        String var1 = WTT.getProperty("com.wentuotuo.wtt.jbo.ql.Parser.default");
        if(!StringX.isSpace(var1) && !StringX.trimAll(var1).equals("com.wentuotuo.wtt.jbo.ql.DefaultParser")) {
            try {
                var0 = createParser(var1);
            } catch (JBOException var3) {
                WTT.getLog().debug("Create default parser failed, some error on class '" + var1 + "', use buildin parser", var3);
                var0 = new DefaultParser();
            }
        } else {
            var0 = new DefaultParser();
        }

        return (Parser)var0;
    }

    public static final Parser createParser(String var0) throws JBOException {
        Parser var1 = null;

        try {
            var1 = (Parser)ObjectX.createObject(var0);
            return var1;
        } catch (WTTException var4) {
            WTT.getLog().debug(var4);
            int var3 = Integer.parseInt(var4.getErrorCode().substring(4)) + 1410;
            throw new JBOException(var3, var4.getArguments(), var4);
        }
    }

    public static Parser parseQuery(BizObjectClass var0, String var1) throws JBOException {
        Parser var2 = createParser();
        var2.parse(var0, var1);
        return var2;
    }

    public static boolean isKeyWord(String var0) {
        return keyWords.isResrvedWord(var0);
    }

    public static int getKeyWordId(String var0) {
        return keyWords.getWordId(var0);
    }

    public static String getKeyWord(int var0) {
        return keyWords.getWord(var0);
    }

    public static void registerKeyWord(String var0) {
        keyWords.registerWord(var0);
    }

    public static boolean isFunction(String var0) {
        return functions.isResrvedWord(var0);
    }

    public static int getFunctionId(String var0) {
        return functions.getWordId(var0);
    }

    public static String getFunction(int var0) {
        return functions.getWord(var0);
    }

    public static void registerFunction(String var0) {
        functions.registerWord(var0);
    }

    static {
        if(keyWords == null) {
            keyWords = new ReservedWordDictionary();
            keyWords.registerWords(new String[]{"WHERE", "AS", "FROM", "SELECT", "AND", "OR", "UPDATE", "SET", "DELETE", "ORDER", "BY", "ASC", "DESC", "LIKE", "BETWEEN", "IN", "IS", "EXISTS", "NULL", "NOT", "GROUP", "ON", "JOIN", "LEFT", "RIGHT", "UNION", "GROUP", "HAVING", "CASE", "WHEN", "THEN", "ELSE", "END", "DISTINCT"});
        }

        if(functions == null) {
            functions = new ReservedWordDictionary();
            functions.registerWords(new String[]{"COUNT", "SUM", "MAX", "MIN", "AVG", "LENGTH"});
        }

    }
}
