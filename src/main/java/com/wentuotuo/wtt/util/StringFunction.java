//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.DateX;
import com.wentuotuo.wtt.lang.StringX;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFunction {
    private static int[] weight = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    private static char[] vcode = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
    private static String hzDigital = "([１一壹])|([２二贰])|([３三叁])|([４四肆])|([５五伍])|([６六陆])|([７七柒])|([８八捌])|([９九玖])|([０〇零])";
    private static Pattern dpattern;

    public StringFunction() {
    }

    public static String removeSpaces(String var0) {
        StringTokenizer var1 = new StringTokenizer(var0, " ", false);

        String var2;
        for(var2 = ""; var1.hasMoreElements(); var2 = var2 + var1.nextElement()) {
            ;
        }

        return var2;
    }

    public static String ltrim(String var0) {
        return StringX.trimStart(var0);
    }

    public static String rtrim(String var0) {
        return StringX.trimEnd(var0);
    }

    public static String itrim(String var0) {
        return var0.replaceAll("\\b\\s{2,}\\b", " ");
    }

    public static String trim(String var0) {
        return itrim(ltrim(rtrim(var0)));
    }

    public static String lrtrim(String var0) {
        return ltrim(rtrim(var0));
    }

    public static String getFileName(String var0) {
        var0 = DataConvert.toString(var0);
        boolean var1 = false;
        int var2 = var0.lastIndexOf(47);
        if(var2 != -1) {
            return var0.substring(var2 + 1, var0.length());
        } else {
            var2 = var0.lastIndexOf(92);
            return var2 != -1?var0.substring(var2 + 1, var0.length()):var0;
        }
    }

    public static String replace(String var0, String var1, String var2) {
        if(var0 != null && var0.length() < 256) {
            for(int var5 = var0.indexOf(var1, 0); var5 != -1; var5 = var0.indexOf(var1, var5 + var2.length())) {
                var0 = var0.substring(0, var5) + var2 + var0.substring(var5 + var1.length());
            }

            return var0;
        } else if(var0 == null) {
            return null;
        } else {
            Pattern var3 = null;
            Matcher var4 = null;
            var3 = Pattern.compile(convert2Reg(var1));
            var4 = var3.matcher(var0);
            return var4.replaceAll(var2);
        }
    }

    public static String convert2Reg(String var0) {
        Hashtable var1 = new Hashtable();
        var1.put(new Character('\n'), new Character('n'));
        var1.put(new Character('\r'), new Character('r'));
        var1.put(new Character('\\'), new Character('\\'));
        var1.put(new Character('{'), new Character('{'));
        var1.put(new Character('['), new Character('['));
        var1.put(new Character('$'), new Character('$'));
        var1.put(new Character('^'), new Character('^'));
        var1.put(new Character('|'), new Character('|'));
        var1.put(new Character('('), new Character('('));
        var1.put(new Character(')'), new Character(')'));
        var1.put(new Character('*'), new Character('*'));
        var1.put(new Character('+'), new Character('+'));
        StringBuffer var2 = new StringBuffer();
        char[] var3 = var0.toCharArray();

        for(int var4 = 0; var4 < var3.length; ++var4) {
            Character var5 = new Character(var3[var4]);
            if(var1.containsKey(var5)) {
                var2.append('\\');
                var2.append(((Character)var1.get(var5)).charValue());
            } else {
                var2.append(var3[var4]);
            }
        }

        return var2.toString();
    }

    public static String applyArgs(String var0, String var1, String var2) {
        StringTokenizer var6 = new StringTokenizer(var1.trim(), ", ");

        String var3;
        String var4;
        for(StringTokenizer var7 = new StringTokenizer(var2.trim(), ","); var6.hasMoreTokens() && var7.hasMoreTokens(); var0 = replace(var0, "#" + var4, var3)) {
            String var5 = var6.nextToken().trim();
            var4 = var6.nextToken().trim();
            var3 = var7.nextToken().trim();
            if(!var5.equals("String")) {
                if(var5.equals("Number")) {
                    if(var3.substring(0, 1).equals("'") || var3.substring(0, 1).equals(String.valueOf('"'))) {
                        var3 = var3.substring(1, var3.length() - 1);
                    }
                } else if(var5.equals("Date")) {
                    ;
                }
            }
        }

        return var0;
    }

    public static String getNow() {
        Calendar var0 = Calendar.getInstance();
        return String.valueOf(Time.valueOf(var0.get(11) + ":" + var0.get(12) + ":" + var0.get(13)));
    }

    public static int indexOf(String var0, String var1, String var2, String var3, int var4) {
        int var5;
        for(var5 = var0.indexOf(var1, var4); var5 >= 0 && getOccurTimes(var0, var2, var3, var4, var5) != 0; var5 = var0.indexOf(var1, var5 + var1.length())) {
            ;
        }

        return var5;
    }

    public static int indexOf(String var0, String var1, int var2, int var3) {
        return var0.substring(var2, var3).indexOf(var1);
    }

    public static int getOccurTimes(String var0, String var1, String var2, int var3, int var4) {
        var0 = var0.substring(var3, var4);
        return var1.equals(var2)?getOccurTimes(var0, var1) % 2:getOccurTimes(var0, var1) - getOccurTimes(var0, var2);
    }

    public static int getOccurTimes(String var0, String var1) {
        int var2 = 0;
        int var3 = 0;

        for(int var4 = var1.length(); (var2 = var0.indexOf(var1, var2) + var4) > var4 - 1; ++var3) {
            ;
        }

        return var3;
    }

    public static int getOccurTimesIgnoreCase(String var0, String var1) {
        int var2 = 0;
        int var3 = 0;

        for(int var4 = var1.length(); (var2 = var0.toLowerCase().indexOf(var1.toLowerCase(), var2) + var4) > var4 - 1; ++var3) {
            ;
        }

        return var3;
    }

    public static String getSeparate(String var0, String var1, int var2) {
        int var4 = 0;
        int var5 = 0;
        int var6 = var1.length();

        int var3;
        for(var0 = var0 + var1; (var3 = var0.indexOf(var1, var4)) >= 0; var4 = var3 + var6) {
            ++var5;
            if(var5 == var2) {
                return var0.substring(var4, var3);
            }
        }

        return "";
    }

    public static int getSeparateSum(String var0, String var1) {
        if(var0 == null) {
            return 0;
        } else {
            int var2 = 0;
            int var3 = 0;

            for(int var4 = var1.length(); (var2 = var0.indexOf(var1, var2) + var4) > var4 - 1; ++var3) {
                ;
            }

            return var3 + 1;
        }
    }

    public static String[] toStringArray(String var0, String var1) {
        String[] var2 = null;
        int var3 = getSeparateSum(var0, var1);
        if(var3 > 0) {
            var2 = new String[var3];

            for(int var4 = 1; var4 <= var3; ++var4) {
                var2[var4 - 1] = getSeparate(var0, var1, var4);
            }
        }

        return var2;
    }

    public static String toArrayString(String[] var0, String var1) {
        String var2 = "";
        int var3 = var0.length;

        for(int var4 = 1; var4 <= var3; ++var4) {
            var2 = var2 + var1 + var0[var4 - 1];
        }

        if(var2.length() > 0) {
            var2 = var2.substring(var1.length());
        }

        return var2;
    }

    public static String[] doubleArray(String[] var0) {
        String[] var1 = null;
        int var2 = var0.length;
        var1 = new String[2 * var2];
        if(var2 > 0) {
            for(int var3 = 0; var3 < var2; ++var3) {
                var1[2 * var3] = var0[var3];
                var1[2 * var3 + 1] = var0[var3];
            }
        }

        return var1;
    }

    public static String getToday() {
        return DateX.format(new Date());
    }

    public static String getToday(String var0) {
        Date var1 = new Date();
        String var2 = (new java.sql.Date(var1.getTime())).toString();
        var2 = replace(var2, "-", var0);
        return var2;
    }

    public static String date2String(java.sql.Date var0, String var1) {
        String var2 = var0.toString();
        var2 = replace(var2, "-", var1);
        return var2;
    }

    public static String date2String(Date var0, String var1) {
        String var2 = (new java.sql.Date(var0.getTime())).toString();
        var2 = replace(var2, "-", var1);
        return var2;
    }

    public static String[] encodingStrings(String[] var0, String var1) {
        int var2 = var0.length;
        String[] var3 = new String[var2];

        try {
            for(int var4 = 0; var4 < var2; ++var4) {
                var3[var4] = new String(var0[var4].getBytes(var1));
            }
        } catch (Exception var5) {
            WTT.getLog().error("encodingStrings error!", var5);
        }

        return var3;
    }

    public static String getProfileString(String var0, String var1, String var2) {
        if(var0 == null) {
            return "";
        } else {
            int var5 = var0.indexOf(var1 + "=");
            if(var5 < 0) {
                return "";
            } else {
                int var4 = (var0 + var2).indexOf(var2, var5);
                if(var4 < 0) {
                    return "";
                } else {
                    int var3 = var0.indexOf("=", var5);
                    return var3 < 0?"":var0.substring(var3 + 1, var4);
                }
            }
        }
    }

    public static String getProfileString(String var0, String var1) {
        return getProfileString(var0, var1, ";");
    }

    public static String[] toStringArray(String var0, String var1, String var2, int var3) {
        if(var1 == null || var1.equals("")) {
            var1 = " ";
        }

        if(var2 == null || var2.equals("")) {
            var2 = " ";
        }

        int var5 = getSeparateSum(var0, var1);
        String[] var6 = new String[var5];

        for(int var4 = 1; var4 <= var5; ++var4) {
            String var7 = getSeparate(var0, var1, var4);
            var6[var4 - 1] = getSeparate(var7, var2, var3);
        }

        return var6;
    }

    public static boolean isLike(String var0, String var1) {
        int var2 = var0.length();
        int var3 = var1.length();
        if(replace(var1, "%", "").length() > var2) {
            return false;
        } else {
            int var5 = 0;
            int var4 = 0;
            boolean var8 = true;
            char var6 = 32;
            char var7 = 32;

            while((var4 < var2 || var5 < var3) && var8) {
                if(var4 < var2) {
                    var6 = var0.charAt(var4);
                }

                if(var5 < var3) {
                    var7 = var1.charAt(var5);
                }

                if(var6 == var7) {
                    ++var4;
                    ++var5;
                } else if(var7 == 95) {
                    if(var5 < var3 && var4 < var2) {
                        return isLike(var0.substring(var4 + 1), var1.substring(var5 + 1));
                    }

                    var8 = false;
                } else if(var7 != 37) {
                    var8 = false;
                } else {
                    int var10 = 0;
                    ++var5;

                    while((var7 == 95 || var7 == 37) && var5 < var3) {
                        if(var7 == 95) {
                            ++var10;
                        }

                        var7 = var1.charAt(var5);
                        ++var5;
                    }

                    --var5;
                    if(var7 != 37) {
                        boolean var9 = false;

                        for(var4 = var0.indexOf(var7, var4 + var10); var4 >= 0 && !var9; var4 = var0.indexOf(var7, var4 + 1)) {
                            var9 = isLike(var0.substring(var4), var1.substring(var5));
                        }

                        return var9;
                    }

                    ++var4;
                    var5 = var3;
                }
            }

            return var8;
        }
    }

    public static boolean setAttribute(String[][] var0, String var1, String var2) {
        boolean var3 = false;

        for(int var4 = 0; var4 < var0.length; ++var4) {
            if(var0[var4][0].equalsIgnoreCase(var1)) {
                var0[var4][1] = var2;
                var3 = true;
                return var3;
            }
        }

        return var3;
    }

    public static String getAttribute(String[][] var0, String var1) {
        return getAttribute(var0, var1, 0, 1);
    }

    public static String getAttribute(String[][] var0, String var1, int var2, int var3) {
        String var4 = null;

        for(int var5 = 0; var5 < var0.length; ++var5) {
            if(var0[var5][var2].equalsIgnoreCase(var1)) {
                var4 = var0[var5][var3];
                return var4;
            }
        }

        return var4;
    }

    public static long getQuot(String var0, String var1) {
        long var2 = 0L;
        SimpleDateFormat var4 = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date var5 = var4.parse(var0);
            Date var6 = var4.parse(var1);
            var2 = var5.getTime() - var6.getTime();
            var2 = var2 / 1000L / 60L / 60L / 24L;
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return var2;
    }

    public static String getRelativeAccountMonth(String var0, String var1, int var2) {
        String var3 = var0.substring(0, 4);
        String var4 = var0.substring(5, 7);
        int var5 = Integer.valueOf(var3).intValue();
        int var6 = Integer.valueOf(var4).intValue();
        if(var1.equalsIgnoreCase("year")) {
            var5 += var2;
        } else if(var1.equalsIgnoreCase("month")) {
            var5 += (var6 + var2) / 12;
            var6 = (var6 + var2) % 12;
            if(var6 <= 0) {
                --var5;
                var6 += 12;
            }
        }

        var3 = String.valueOf(var5);
        var4 = String.valueOf(var6);
        if(var4.length() == 1) {
            var4 = "0" + var4;
        }

        return var3 + "/" + var4;
    }

    public static String getRelativeMonth(String var0, int var1) {
        DateX var2 = new DateX(var0);
        return DateX.format(var2.getAdjustRelativeDate(1, var1, false));
    }

    public static String getRelativeDate(String var0, int var1) {
        DateX var2 = new DateX(var0);
        return DateX.format(var2.getAdjustRelativeDate(2, var1, false));
    }

    public static String numberToChinese(double var0) {
        DecimalFormat var2 = new DecimalFormat("############0.00");
        String var3 = var2.format(var0);
        int var4 = var3.indexOf(".");
        if(var3.substring(var4).compareTo(".00") == 0) {
            var3 = var3.substring(0, var4);
        }

        String var5 = "";
        String[] var6 = new String[4];
        String[] var7 = new String[4];
        String[] var8 = new String[2];
        String var9 = "";
        String var10 = "";
        String var11 = "";
        boolean var12 = false;
        int var13 = 0;
        boolean var14 = false;
        var6[0] = "";
        var6[1] = "拾";
        var6[2] = "佰";
        var6[3] = "仟";
        var7[0] = "";
        var7[1] = "万";
        var7[2] = "亿";
        var7[3] = "万";
        var8[0] = "分";
        var8[1] = "角";
        if(var3.compareTo("0") != 0 && var3.compareTo("0.0") != 0 && var3.compareTo("0.00") != 0) {
            if(var3.indexOf(".") > 0) {
                var5 = var3.substring(0, var3.indexOf("."));
            } else {
                var5 = var3;
            }

            int var17 = var5.length() % 4 != 0?var5.length() / 4 + 1:var5.length() / 4;

            int var15;
            for(var15 = var17; var15 >= 1; --var15) {
                int var18;
                if(var15 == var17 && var5.length() % 4 != 0) {
                    var18 = var5.length() % 4;
                } else {
                    var18 = 4;
                }

                var9 = var5.substring(var13, var13 + var18);

                for(int var16 = 0; var16 < var9.length(); ++var16) {
                    if(Integer.parseInt(var9.substring(var16, var16 + 1)) != 0) {
                        switch(Integer.parseInt(var9.substring(var16, var16 + 1))) {
                            case 1:
                                var11 = var11 + "壹" + var6[var9.length() - var16 - 1];
                                break;
                            case 2:
                                var11 = var11 + "贰" + var6[var9.length() - var16 - 1];
                                break;
                            case 3:
                                var11 = var11 + "叁" + var6[var9.length() - var16 - 1];
                                break;
                            case 4:
                                var11 = var11 + "肆" + var6[var9.length() - var16 - 1];
                                break;
                            case 5:
                                var11 = var11 + "伍" + var6[var9.length() - var16 - 1];
                                break;
                            case 6:
                                var11 = var11 + "陆" + var6[var9.length() - var16 - 1];
                                break;
                            case 7:
                                var11 = var11 + "柒" + var6[var9.length() - var16 - 1];
                                break;
                            case 8:
                                var11 = var11 + "捌" + var6[var9.length() - var16 - 1];
                                break;
                            case 9:
                                var11 = var11 + "玖" + var6[var9.length() - var16 - 1];
                        }
                    } else if(var16 + 1 < var9.length() && var9.charAt(var16 + 1) != 48) {
                        var11 = var11 + "零";
                    }
                }

                var13 += var18;
                if(var15 < var17) {
                    if(Integer.parseInt(var9.substring(var9.length() - 1, var9.length())) != 0 || Integer.parseInt(var9.substring(var9.length() - 2, var9.length() - 1)) != 0 || Integer.parseInt(var9.substring(var9.length() - 3, var9.length() - 2)) != 0 || Integer.parseInt(var9.substring(var9.length() - 4, var9.length() - 3)) != 0) {
                        var11 = var11 + var7[var15 - 1];
                    }
                } else {
                    var11 = var11 + var7[var15 - 1];
                }
            }

            if(var11.length() > 0) {
                var11 = var11 + "元";
            }

            if(var3.indexOf(".") > 0) {
                var10 = var3.substring(var3.indexOf(".") + 1);

                for(var15 = 0; var15 < 2; ++var15) {
                    if(Integer.parseInt(var10.substring(var15, var15 + 1)) != 0) {
                        switch(Integer.parseInt(var10.substring(var15, var15 + 1))) {
                            case 1:
                                var11 = var11 + "壹" + var8[1 - var15];
                                break;
                            case 2:
                                var11 = var11 + "贰" + var8[1 - var15];
                                break;
                            case 3:
                                var11 = var11 + "叁" + var8[1 - var15];
                                break;
                            case 4:
                                var11 = var11 + "肆" + var8[1 - var15];
                                break;
                            case 5:
                                var11 = var11 + "伍" + var8[1 - var15];
                                break;
                            case 6:
                                var11 = var11 + "陆" + var8[1 - var15];
                                break;
                            case 7:
                                var11 = var11 + "柒" + var8[1 - var15];
                                break;
                            case 8:
                                var11 = var11 + "捌" + var8[1 - var15];
                                break;
                            case 9:
                                var11 = var11 + "玖" + var8[1 - var15];
                        }
                    } else if(var11.length() > 0) {
                        var11 = var11 + "零";
                    }
                }
            } else {
                var11 = var11 + "整";
            }

            if(var11.substring(var11.length() - 1).compareTo("零") == 0) {
                var11 = var11.substring(0, var11.length() - 1);
            }

            return var11;
        } else {
            var11 = "零元整";
            return var11;
        }
    }

    public static String macroReplace(String[][] var0, String var1, String var2, String var3) throws Exception {
        return macroReplace(var0, var1, var2, var3, 0, 1);
    }

    public static String macroReplace(String[][] var0, String var1, String var2, String var3, int var4, int var5) throws Exception {
        int var6 = 0;
        boolean var7 = false;
        String var8 = "";
        String var9 = var1;

        try {
            if(var0 != null && var0.length != 0 && var1 != null) {
                while((var6 = var9.indexOf(var2, var6)) >= 0) {
                    int var12 = var9.indexOf(var3, var6);
                    var8 = var9.substring(var6, var12 + var3.length());
                    var9 = var9.substring(0, var6) + getAttribute(var0, var8, var4, var5) + var9.substring(var12 + var3.length());
                }

                return var9;
            } else {
                return null;
            }
        } catch (Exception var11) {
            throw new Exception("宏替换错误:" + var11.toString());
        }
    }

    public static String macroReplace(ASValuePool var0, String var1, String var2, String var3) throws Exception {
        if(var1 == null) {
            return null;
        } else if(var0 != null && var0.size() != 0) {
            int var4 = 0;
            boolean var5 = false;
            String var6 = "";
            String var7 = "";
            Object var8 = null;
            String var9 = var1;

            try {
                while((var4 = var9.indexOf(var2, var4)) >= 0) {
                    int var12 = var9.indexOf(var3, var4);
                    if(var12 < 0) {
                        break;
                    }

                    var6 = var9.substring(var4 + var2.length(), var12);
                    var8 = var0.getAttribute(var6);
                    if(var8 == null) {
                        var4 = var12;
                    } else {
                        var7 = (String)var8;
                        var9 = var9.substring(0, var4) + var7 + var9.substring(var12 + var3.length());
                    }
                }

                return var9;
            } catch (Exception var11) {
                throw new Exception("宏替换错误:" + var11.toString());
            }
        } else {
            return var1;
        }
    }

    public static String macroRepeat(String[] var0, String var1) throws Exception {
        String var2 = var1;

        for(int var3 = 0; var3 < var0.length; ++var3) {
            var2 = var2 + replace(var2, "[$CIRCULATOR/$]", var0[var3]);
        }

        return var2;
    }

    public static String getXProfileString(String var0, String var1, String var2) {
        return var0.substring(var0.indexOf(var1) + var1.length(), var0.indexOf(var2));
    }

    public static String replaceConstants(String var0, String var1, String var2) throws Exception {
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

    public static ASValuePool convertStringArray2ValuePool(String[] var0, String[] var1) throws Exception {
        ASValuePool var2 = new ASValuePool();

        for(int var3 = 0; var3 < var0.length; ++var3) {
            if(var1.length >= var3) {
                if(var1[var3] != null) {
                    var1[var3] = var1[var3].trim();
                }

                var2.setAttribute(var0[var3], var1[var3]);
            } else {
                var2.setAttribute(var0[var3], (Object)null);
            }
        }

        return var2;
    }

    public static ASValuePool convertStringArray2ValuePool(String[][] var0) throws Exception {
        ASValuePool var1 = new ASValuePool();

        for(int var2 = 0; var2 < var0.length; ++var2) {
            if(var0[var2][1] != null) {
                var0[var2][1] = var0[var2][1].trim();
            }

            var1.setAttribute(var0[var2][0], var0[var2][1]);
        }

        return var1;
    }

    public static String getTodayNow() {
        return getToday("/") + " " + getNow();
    }

    public static String getMathRandom() {
        String var0 = Double.toString(Math.random());
        return var0.length() == 18?var0:(var0.length() > 18?var0.substring(0, 18):(var0.length() < 18?(var0 + "00000000").substring(0, 18):var0));
    }

    public static String[][] genStringArray(String var0) {
        int var1 = getSeparateSum(var0, "{") - 1;
        int var2 = getSeparateSum(var0, "}") - 1;
        int var3 = getSeparateSum(var0, "\"") - 1;
        int var4 = var3 / 2;
        int var5 = var1 - 1;
        boolean var6 = false;
        int var7 = 0;
        int var8 = 0;
        int var14 = var4 / var5;
        String var9 = "";
        if(var1 >= 2 && var1 == var2 && var3 % 2 == 0) {
            String var10 = var0.trim();
            var10 = var10.substring(var10.indexOf("{") + 1, var10.lastIndexOf("}")).trim();
            String[][] var11 = new String[var5][var14];

            for(int var12 = 0; var12 < var5; ++var12) {
                var1 = var10.indexOf("{", var7) + 1;
                var2 = var10.indexOf("}", var7);
                var7 = var2 + 1;
                if(var1 > var2) {
                    return (String[][])null;
                }

                var9 = var10.substring(var1, var2).trim();
                System.out.println(var9);

                for(int var13 = 0; var13 < var14; ++var13) {
                    var1 = var9.indexOf("\"", var8) + 1;
                    var2 = var9.indexOf("\"", var1);
                    var11[var12][var13] = var9.substring(var1, var2);
                    System.out.println("strArray2[" + var12 + "][" + var13 + "]=" + var11[var12][var13]);
                    var8 = var2 + 1;
                }

                var8 = 0;
                if(var7 >= var10.length()) {
                    break;
                }
            }

            return var11;
        } else {
            return (String[][])null;
        }
    }

    public static String filterControlChar(String var0) {
        return var0.replaceAll("\\p{Cntrl}", "");
    }

    public static String fixDigital(String var0) {
        Matcher var1 = dpattern.matcher(var0);

        StringBuffer var2;
        int var3;
        for(var2 = new StringBuffer(); var1.find(); var1.appendReplacement(var2, String.valueOf(var3 % 10))) {
            var3 = 0;

            for(int var4 = 1; var4 <= 10; ++var4) {
                if(var1.group(var4) != null) {
                    var3 = var4;
                    break;
                }
            }
        }

        var1.appendTail(var2);
        return var2.toString();
    }

    public static String fixPID(String var0) {
        String var1 = filterControlChar(var0);
        String var2 = fixDigital(var1);
        if(var2.length() != 15) {
            return var2.toUpperCase();
        } else {
            StringBuffer var3 = new StringBuffer(var2);
            var3.insert(6, "19");
            int var4 = 0;

            for(int var5 = 0; var5 < 17; ++var5) {
                var4 += Character.digit(var3.charAt(var5), 10) * weight[var5];
            }

            var3.append(vcode[var4 % 11]);
            return var3.toString();
        }
    }

    public static void main(String[] var0) {
        System.out.println(fixPID("150202760929122"));
    }

    static {
        dpattern = Pattern.compile(hzDigital);
    }
}
