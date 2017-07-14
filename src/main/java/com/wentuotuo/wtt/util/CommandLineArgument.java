//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util;

import com.wentuotuo.wtt.lang.StringX;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class CommandLineArgument {
    private Properties arguments = new Properties();

    public CommandLineArgument(String[] var1) {
        this.splitArguments(var1);
    }

    public String getArgument(String var1) {
        return this.arguments.getProperty(var1);
    }

    public int getArgument(String var1, int var2) {
        int var3 = var2;
        String var4 = this.arguments.getProperty(var1);
        if(var4 != null) {
            try {
                var3 = Integer.parseInt(var4);
            } catch (Exception var6) {
                var3 = var2;
            }
        }

        return var3;
    }

    public double getArgument(String var1, double var2) {
        double var4 = var2;
        String var6 = this.arguments.getProperty(var1);
        if(var6 != null) {
            try {
                var4 = Double.parseDouble(var6);
            } catch (Exception var8) {
                var4 = var2;
            }
        }

        return var4;
    }

    public boolean getArgument(String var1, boolean var2) {
        boolean var3 = var2;
        String var4 = this.arguments.getProperty(var1);
        if(var4 != null) {
            var3 = StringX.parseBoolean(var4);
        }

        return var3;
    }

    public Date getArgument(String var1, Date var2) {
        Date var3 = var2;
        String var4 = this.arguments.getProperty(var1);
        if(var4 != null) {
            try {
                var3 = StringX.parseDate(var4);
            } catch (Exception var6) {
                var3 = var2;
            }
        }

        return var3;
    }

    public Date getArgument(String var1, Date var2, String var3) {
        Date var4 = var2;
        String var5 = this.arguments.getProperty(var1);
        if(var5 != null) {
            SimpleDateFormat var6 = new SimpleDateFormat(var3);

            try {
                var4 = var6.parse(var5);
            } catch (Exception var8) {
                var4 = var2;
            }
        }

        return var4;
    }

    public String getArgument(String var1, String var2) {
        return this.arguments.getProperty(var1, var2);
    }

    public String[] getArguments() {
        return (String[])this.arguments.keySet().toArray(new String[0]);
    }

    public int size() {
        return this.arguments.size();
    }

    private void splitArguments(String[] var1) {
        if(var1 != null) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                int var3 = var1[var2].indexOf(61);
                String var4 = null;
                String var5 = null;
                if(var3 < 0) {
                    var4 = var1[var2];
                    var5 = "true";
                } else {
                    if(var3 <= 0 || var3 >= var1[var2].length() - 1) {
                        continue;
                    }

                    var4 = var1[var2].substring(0, var3);
                    var5 = var1[var2].substring(var3 + 1);
                }

                this.arguments.setProperty(var4, var5);
            }
        }

    }
}
