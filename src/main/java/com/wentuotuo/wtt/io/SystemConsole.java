//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.io;

import com.wentuotuo.wtt.WTT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.util.Arrays;

public class SystemConsole {
    public SystemConsole() {
    }

    public static String readLine() {
        String var0 = null;

        try {
            var0 = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        } catch (IOException var2) {
            WTT.getLog().debug(var2);
        }

        return var0;
    }

    public static String readLine(String var0) {
        System.out.print(var0);
        return readLine();
    }

    public static char[] readPassword() {
        MaskingThread var0 = new MaskingThread();
        Thread var1 = new Thread(var0);
        char[] var2;
        char[] var3 = var2 = new char[128];
        int var4 = var3.length;
        int var5 = 0;
        Object var7 = System.in;

        try {
            var1.start();

            label41:
            while(true) {
                int var6;
                switch(var6 = ((InputStream)var7).read()) {
                    case -1:
                    case 10:
                        break label41;
                    case 13:
                        int var8 = ((InputStream)var7).read();
                        if(var8 == 10 || var8 == -1) {
                            break label41;
                        }

                        if(!(var7 instanceof PushbackInputStream)) {
                            var7 = new PushbackInputStream((InputStream)var7);
                        }

                        ((PushbackInputStream)var7).unread(var8);
                }

                --var4;
                if(var4 < 0) {
                    var3 = new char[var5 + 128];
                    var4 = var3.length - var5 - 1;
                    System.arraycopy(var2, 0, var3, 0, var5);
                    Arrays.fill(var2, ' ');
                    var2 = var3;
                }

                var3[var5++] = (char)var6;
            }
        } catch (IOException var9) {
            WTT.getLog().debug(var9);
        }

        var0.stopMasking();
        if(var5 == 0) {
            return null;
        } else {
            char[] var10 = new char[var5];
            System.arraycopy(var3, 0, var10, 0, var5);
            Arrays.fill(var3, ' ');
            return var10;
        }
    }

    public static char[] readPassword(String var0) {
        System.out.print(var0);
        return readPassword();
    }
}
