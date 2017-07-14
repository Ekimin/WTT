//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util;

public class SpecialTools {
    static String[][] sAmarReplace = new String[][]{{"\\", "~[isl]", "\\", "\\\\", "&#92;", "\\"}, {"'", "~[sqt]", "''", "\\'", "&#39;", "''"}, {"\"", "~[dqt]", "\"", "\"", "&#34;", "\""}, {"<", "~[alt]", "<", "<", "&#60;", "<"}, {">", "~[agt]", ">", ">", "&#62;", ">"}, {"\r\n", "~[arn]", "\r\n", "\\r\\n", "\r\n", "\\r\\n"}, {"\r", "~[aor]", "\r\n", "\\r\\n", "\r\n", "\\r"}, {"\n", "~[aon]", "\r\n", "\\r\\n", "\r\n", "\\n"}, {"#", "~[pds]", "#", "#", "#", "#"}, {"(", "~[lpr]", "(", "(", "(", "("}, {")", "~[rpr]", ")", ")", ")", ")"}, {"+", "~[pls]", "+", "+", "+", "+"}};

    public SpecialTools() {
    }

    public static String real2Amarsoft(String var0) throws Exception {
        String var1 = "";
        if(var0 != null) {
            var1 = var0;

            for(int var2 = 0; var2 < sAmarReplace.length; ++var2) {
                var1 = StringFunction.replace(var1, sAmarReplace[var2][0], sAmarReplace[var2][1]);
            }
        } else {
            var1 = null;
        }

        return var1;
    }

    public static String wentuotuo2Real(String var0) throws Exception {
        String var1 = "";
        if(var0 != null) {
            var1 = StringFunction.macroReplace(sAmarReplace, var0, "~[", "]", 1, 0);
        } else {
            var1 = null;
        }

        return var1;
    }

    public static String wentuotuo2DB(String var0) throws Exception {
        String var1 = "";
        if(var0 != null) {
            var1 = StringFunction.macroReplace(sAmarReplace, var0, "~[", "]", 1, 2);
        } else {
            var1 = null;
        }

        return var1;
    }

    public static String wentuotuo2Informix(String var0) throws Exception {
        String var1 = "";
        if(var0 != null) {
            var1 = StringFunction.macroReplace(sAmarReplace, var0, "~[", "]", 1, 5);
        } else {
            var1 = null;
        }

        return var1;
    }

    public static String db2Informix(String var0) throws Exception {
        String var1 = "";
        if(var0 != null) {
            var1 = var0;

            for(int var2 = 0; var2 < sAmarReplace.length; ++var2) {
                if(!sAmarReplace[var2][2].equals(sAmarReplace[var2][5])) {
                    var1 = StringFunction.replace(var1, sAmarReplace[var2][2], sAmarReplace[var2][5]);
                }
            }
        } else {
            var1 = null;
        }

        return var1;
    }

    public static String informix2DB(String var0) throws Exception {
        String var1 = "";
        if(var0 != null) {
            var1 = var0;

            for(int var2 = 0; var2 < sAmarReplace.length; ++var2) {
                if(!sAmarReplace[var2][5].equals(sAmarReplace[var2][2])) {
                    var1 = StringFunction.replace(var1, sAmarReplace[var2][5], sAmarReplace[var2][2]);
                }
            }
        } else {
            var1 = null;
        }

        return var1;
    }

    public static String replaceAll(String var0, String var1, String var2) throws Exception {
        if(var0 != null && !var0.equals("")) {
            String var3 = var0;

            for(int var4 = var0.indexOf(var1); var4 >= 0; var4 = var3.indexOf(var1, var4 + var2.length())) {
                var3 = var3.substring(0, var4) + var2 + var3.substring(var4 + var1.length(), var3.length());
            }

            return var3;
        } else {
            return "";
        }
    }

    public static String htmlEncoder(String var0) throws Exception {
        if(var0 != null && !var0.equals("")) {
            String var1 = replaceAll(var0, "<", "&lt;");
            var1 = replaceAll(var1, ">", "&rt;");
            var1 = replaceAll(var1, "\"", "&quot;");
            var1 = replaceAll(var1, "'", "&#039;");
            var1 = replaceAll(var1, " ", "&nbsp;");
            var1 = replaceAll(var1, "\r\n", "<br>");
            var1 = replaceAll(var1, "\r", "<br>");
            var1 = replaceAll(var1, "\n", "<br>");
            return var1;
        } else {
            return "";
        }
    }

    public static String xmlEncoder(String var0) throws Exception {
        if(var0 != null && !var0.equals("")) {
            String var1 = replaceAll(var0, "&", "&amp;");
            var1 = replaceAll(var1, "<", "&lt;");
            var1 = replaceAll(var1, ">", "&gt;");
            var1 = replaceAll(var1, "\"", "&quot;");
            var1 = replaceAll(var1, "'", "&acute;");
            return var1;
        } else {
            return "";
        }
    }

    public static String add0BeforeString(String var0, int var1) {
        String var2;
        for(var2 = var0; var2.length() < var1; var2 = "0" + var2) {
            ;
        }

        return var2;
    }

    public static void main(String[] var0) throws Exception {
        String var1 = "abc'\"\\";
        String var2 = "示例'<textarea>aaa</textarea>";
        System.out.println(real2Amarsoft(var1));
        System.out.println(real2Amarsoft(var2));
    }
}
