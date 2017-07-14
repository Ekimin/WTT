//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class StringX {
    public StringX() {
    }

    public static Date parseDate(String var0) {
        if(var0 == null) {
            return null;
        } else {
            Date var1 = null;
            String var2 = null;
            char[] var3 = new char[]{'/', '-', '.'};
            if(var0.length() < 8) {
                return var1;
            } else {
                if(var0.length() > 10) {
                    var0 = var0.substring(0, 10);
                }

                for(int var4 = 0; var4 < var3.length; ++var4) {
                    int var5 = var0.indexOf(var3[var4]);
                    if(var5 >= 0) {
                        if(var5 == 4) {
                            var2 = "yyyy" + var3[var4] + "M" + var3[var4] + "d";
                        } else {
                            if(var5 >= 4) {
                                return null;
                            }

                            var2 = "M" + var3[var4] + "d" + var3[var4] + "y";
                        }
                        break;
                    }
                }

                if(var2 == null) {
                    if(var0.substring(0, 2).compareTo("12") > 0) {
                        var2 = "yyyyMMdd";
                    } else {
                        var2 = "MMddyyyy";
                    }
                }

                SimpleDateFormat var7 = new SimpleDateFormat(var2);

                try {
                    var1 = var7.parse(var0);
                } catch (ParseException var6) {
                    var1 = null;
                }

                return var1;
            }
        }
    }

    public static boolean parseBoolean(String var0) {
        return var0 == null?false:var0.equalsIgnoreCase("true") || var0.equalsIgnoreCase("t") || var0.equalsIgnoreCase("yes") || var0.equalsIgnoreCase("y") || var0.matches(".*[1-9]+.*");
    }

    public static String[] parseArray(String var0) {
        if(var0 == null) {
            return null;
        } else {
            ArrayList var1 = new ArrayList();
            int var2 = 0;
            int var3 = 0;
            int var4 = 0;

            for(int var5 = -1; var4 < var0.length(); ++var4) {
                if(var0.charAt(var4) == 123) {
                    ++var2;
                    if(var2 == 1) {
                        var5 = var4 + 1;
                    }
                } else if(var0.charAt(var4) == 125) {
                    ++var3;
                    if(var3 == var2) {
                        var1.add(var0.substring(var5, var4));
                        var5 = -1;
                        var2 = 0;
                        var3 = 0;
                    }
                }
            }

            return (String[])var1.toArray(new String[0]);
        }
    }

    public static Properties parseProperties(String var0) {
        Properties var1 = new Properties();
        String[] var2 = parseArray(var0);
        if(var2 != null && var2.length > 0) {
            for(int var3 = 0; var3 < var2.length; ++var3) {
                int var4 = var2[var3].indexOf(61);
                if(var4 >= 1 && var4 != var2[var3].length()) {
                    String var5 = trimAll(var2[var3].substring(0, var4));
                    if(var5 != "") {
                        String var6 = var2[var3].substring(var4 + 1);
                        var1.setProperty(var5, var6);
                    }
                }
            }
        }

        return var1;
    }

    public static String bytesToHexString(byte[] var0, boolean var1) {
        if(var0 == null) {
            return null;
        } else {
            StringBuffer var2 = new StringBuffer();

            for(int var3 = 0; var3 < var0.length; ++var3) {
                byte var4 = var0[var3];
                var2.append(Integer.toString(var4 >> 4 & 15, 16)).append(Integer.toString(var4 & 15, 16));
            }

            String var5 = var2.toString();
            return var1?var5.toUpperCase():var5.toLowerCase();
        }
    }

    public static String trimStart(String var0) {
        String var1 = null;
        boolean var2 = false;
        if(var0 != null) {
            int var3;
            for(var3 = 0; var3 < var0.length() && Character.isWhitespace(var0.charAt(var3)); ++var3) {
                ;
            }

            var1 = var3 < var0.length()?var0.substring(var3):"";
        }

        return var1;
    }

    public static String trimEnd(String var0) {
        String var1 = null;
        boolean var2 = false;
        if(var0 != null) {
            int var3;
            for(var3 = var0.length() - 1; var3 >= 0 && Character.isWhitespace(var0.charAt(var3)); --var3) {
                ;
            }

            var1 = var3 >= 0?var0.substring(0, var3 + 1):"";
        }

        return var1;
    }

    public static String trimAll(String var0) {
        String var1 = null;
        if(var0 != null) {
            var1 = trimEnd(trimStart(var0));
        }

        return var1;
    }

    public static boolean isEmpty(String var0) {
        return var0 == null || var0.equals("");
    }

    public static boolean isSpace(String var0) {
        if(var0 == null) {
            return true;
        } else {
            boolean var1 = true;

            for(int var2 = 0; var2 < var0.length(); ++var2) {
                if(!Character.isSpaceChar(var0.charAt(var2))) {
                    var1 = false;
                    break;
                }
            }

            return var1;
        }
    }

    public static boolean isEmpty(DataElement var0) {
        return var0 == null || var0.isNull() || isEmpty(var0.getString());
    }

    public static boolean isSpace(DataElement var0) {
        return isEmpty(var0) || isSpace(var0.getString());
    }
}
