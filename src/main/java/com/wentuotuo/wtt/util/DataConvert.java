//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class DataConvert {
    public static int iChange = 1;

    public DataConvert() {
    }

    public static String toString(String var0) {
        return var0 == null?"":var0;
    }

    public static String toString(String var0, String var1) {
        return var0 == null?var1:var0;
    }

    public static String toHTMLString(String var0) {
        return var0 != null && !var0.equals("")?var0:"&nbsp;";
    }

    public static String toHTMLMoney(String var0) {
        return var0 != null && !var0.equals("")?toMoney(var0):"&nbsp;";
    }

    public static String toString(Short var0) {
        return var0 == null?"":String.valueOf(var0);
    }

    public static String toString(Double var0) {
        return var0 == null?"":String.valueOf(var0);
    }

    public static String toString(BigDecimal var0) {
        return var0 == null?"":String.valueOf(var0);
    }

    public static String toMoney(String var0) {
        try {
            return var0 != null && var0 != "" && !var0.equalsIgnoreCase("Null")?toMoney(Double.valueOf(var0).doubleValue()):"";
        } catch (Exception var2) {
            return var0;
        }
    }

    public static String toMoney(Double var0) {
        return var0 == null?"":toMoney(var0.doubleValue());
    }

    public static String toMoney(double var0) {
        NumberFormat var2 = NumberFormat.getInstance();
        var2.setMinimumFractionDigits(2);
        var2.setMaximumFractionDigits(2);
        return var2.format(var0);
    }

    public static String toMoney(BigDecimal var0) {
        return toMoney(String.valueOf(var0));
    }

    public static String toDate_YMD(String var0) {
        String var1 = var0.substring(0, 4);
        String var2 = var0.substring(4, 6);
        String var3 = var0.substring(6, 8);
        String var4 = var1 + "年" + var2 + "月" + var3 + "日";
        return var4;
    }

    public static String toDate_YMD2(String var0, String var1) {
        String var2 = var0.substring(0, 4);
        String var3 = var1.substring(0, 4);
        String var4 = var0.substring(4, 6);
        String var5 = var1.substring(4, 6);
        String var6 = var0.substring(6, 8);
        String var7 = var1.substring(6, 8);
        String var8 = var2 + "年" + var4 + "月" + var6 + "日至" + var3 + "年" + var5 + "月" + var7 + "日";
        return var8;
    }

    public static String toDate_YM(String var0) {
        String var1 = var0.substring(0, 4);
        String var2 = var0.substring(4, 6);
        String var3 = var1 + "年" + var2 + "月";
        return var3;
    }

    public static String toDate_Y(String var0) {
        String var1 = var0.substring(0, 4);
        String var2 = var1 + "年";
        return var2;
    }

    public static String toDate_YMD0(String var0) {
        String var1 = var0.substring(0, 4);
        String var2 = var0.substring(4, 6);
        String var3 = var0.substring(6, 8);
        String var4 = "-";
        String var5 = var1 + var4 + var2 + var4 + var3;
        return var5;
    }

    public static String toDate_YM2(String var0) {
        String var1 = var0.substring(0, 4);
        String var2 = var0.substring(4, 6);
        String var3 = var1 + "年" + var2 + "月";
        return var3;
    }

    public static String toRealString_old(String var0) {
        try {
            String var1 = var0;
            if(var0 != null) {
                var1 = new String(var0.getBytes(), "ISO8859_1");
            }

            return var1;
        } catch (UnsupportedEncodingException var2) {
            return var0;
        }
    }

    public static String toRealString(String var0) {
        return toRealString(iChange, var0);
    }

    public static String toRealString(int var0, String var1) {
        if(var1 == null) {
            return null;
        } else {
            try {
                String var2 = var1;
                if(var0 == 1) {
                    return var1;
                } else {
                    if(var1 != null) {
                        if(var0 == 0) {
                            var2 = new String(var1.getBytes(), "ISO8859_1");
                        }

                        if(var0 == 2) {
                            var2 = new String(var2.getBytes("ISO8859_1"), "GBK");
                        }

                        if(var0 == 3) {
                            var2 = new String(var2.getBytes("GBK"), "ISO8859_1");
                        }

                        if(var0 == 5) {
                            var2 = decode(var2, "GBK");
                        }
                    }

                    return var2;
                }
            } catch (UnsupportedEncodingException var3) {
                return var1;
            } catch (Exception var4) {
                return var1;
            }
        }
    }

    public static String decode(String var0, String var1) throws Exception {
        if(var0 == null) {
            return var0;
        } else {
            StringBuffer var2 = new StringBuffer();

            for(int var3 = 0; var3 < var0.length(); ++var3) {
                char var4 = var0.charAt(var3);
                switch(var4) {
                    case '%':
                        try {
                            var2.append((char)Integer.parseInt(var0.substring(var3 + 1, var3 + 3), 16));
                        } catch (NumberFormatException var6) {
                            throw new IllegalArgumentException();
                        }

                        var3 += 2;
                        break;
                    case '+':
                        var2.append(' ');
                        break;
                    default:
                        var2.append(var4);
                }
            }

            String var7 = var2.toString();
            byte[] var8 = var7.getBytes("8859_1");
            return new String(var8, var1);
        }
    }

    public static double toDouble(String var0) {
        return var0 != null && !var0.equals("")?Double.parseDouble(var0):0.0D;
    }

    public static int toInt(String var0) {
        return var0 != null && !var0.equals("")?Integer.parseInt(var0):0;
    }
}
