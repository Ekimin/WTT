//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.io;

import com.wentuotuo.wtt.WTT;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class FileTool {
    public static int bufferSize = 1024;
    public static boolean useNIO = false;

    public FileTool() {
    }

    public static void copy(String var0, String var1) throws IOException {
        copy((File)(new File(var0)), (File)(new File(var1)), 0);
    }

    public static void copy(String var0, String var1, int var2) throws IOException {
        copy(new File(var0), new File(var1), var2);
    }

    public static void copy(File var0, File var1) throws IOException {
        copy((File)var0, (File)var1, 0);
    }

    public static void copy(File var0, File var1, int var2) throws IOException {
        if(var2 > 0) {
            bufferSize = var2;
        }

        var1.createNewFile();
        if(useNIO) {
            copyNIO(var0, var1);
        } else {
            copyIO(var0, var1);
        }

    }

    private static void copyIO(File var0, File var1) throws IOException {
        FileInputStream var2 = new FileInputStream(var0);
        FileOutputStream var3 = new FileOutputStream(var1);
        byte[] var4 = new byte[bufferSize];

        int var5;
        while((var5 = var2.read(var4)) > 0) {
            var3.write(var4, 0, var5);
        }

        var2.close();
        var3.close();
    }

    private static void copyNIO(File var0, File var1) throws IOException {
        FileInputStream var2 = new FileInputStream(var0);
        FileChannel var3 = var2.getChannel();
        FileOutputStream var4 = new FileOutputStream(var1);
        FileChannel var5 = var4.getChannel();
        MappedByteBuffer var6 = var3.map(MapMode.READ_ONLY, 0L, var0.length());
        var5.write(var6);
        var2.close();
        var4.close();
        var6 = null;
    }

    public static File findFile(String var0) {
        File var1 = new File(var0);
        if(!var1.exists()) {
            ClassLoader var2 = WTT.class.getClassLoader();
            URL var3 = var2.getResource(var0);
            if(var3 != null) {
                var1 = new File(var3.getPath());
            }
        }

        if(!var1.exists()) {
            var1 = null;
        }

        return var1;
    }
}
