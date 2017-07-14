//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.xml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlTools {
    private static String xmlSpecialChar = "[\"'&<>]";

    public XmlTools() {
    }

    public static String replaceXmlSpecialChars(String var0) {
        if(var0 == null) {
            return null;
        } else {
            StringBuffer var1 = new StringBuffer();

            Matcher var2;
            String var4;
            for(var2 = Pattern.compile(xmlSpecialChar).matcher(var0); var2.find(); var2.appendReplacement(var1, "&" + var4 + ";")) {
                char var3 = var2.group().charAt(0);
                var4 = null;
                switch(var3) {
                    case '"':
                        var4 = "quot";
                        break;
                    case '&':
                        var4 = "amp";
                        break;
                    case '\'':
                        var4 = "apos";
                        break;
                    case '<':
                        var4 = "lt";
                        break;
                    case '>':
                        var4 = "gt";
                        break;
                    default:
                        var4 = "";
                }
            }

            var2.appendTail(var1);
            return var1.toString();
        }
    }
}
