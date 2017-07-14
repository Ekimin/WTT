//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql.tools;

import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.security.DESEncrypt;
import com.wentuotuo.wtt.util.CommandLineArgument;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EncryptDBConfig {
    public EncryptDBConfig() {
    }

    public static void main(String[] var0) {
        CommandLineArgument var1 = new CommandLineArgument(var0);
        String[] var2 = new String[]{"driver", "url", "user", "password"};
        String[] var3 = new String[var2.length];

        int var4;
        for(var4 = 0; var4 < var2.length; ++var4) {
            var3[var4] = var1.getArgument(var2[var4]);
        }

        for(var4 = 0; var4 < var2.length; ++var4) {
            if(StringX.isSpace(var3[var4])) {
                var3[var4] = inputArg(var2[var4]);
            }
        }

        System.out.println("Encrypt Result: ");

        for(var4 = 0; var4 < var2.length; ++var4) {
            System.out.println(var2[var4] + " : " + DESEncrypt.encrypt(var3[var4]));
        }

    }

    private static String inputArg(String var0) {
        String var1 = null;
        BufferedReader var2 = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.print("Source of " + var0 + " : ");

            try {
                var1 = var2.readLine();
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        } while(StringX.isEmpty(var1));

        return var1;
    }
}
