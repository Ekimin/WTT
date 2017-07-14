//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.security;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.StringX;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class MessageDigest {
    public MessageDigest() {
    }

    public static byte[] getDigest(String var0, byte[] var1) throws NoSuchAlgorithmException {
        Object var2 = null;
        if(var1 == null) {
            return (byte[])var2;
        } else {
            if(var0 == null) {
                var0 = "MD5";
            }

            java.security.MessageDigest var3 = java.security.MessageDigest.getInstance(var0.toUpperCase());
            var3.reset();
            var3.update(var1);
            byte[] var4 = var3.digest();
            return var4;
        }
    }

    public static byte[] getDigest(String var0, String var1) throws NoSuchAlgorithmException {
        if(var1 == null) {
            return null;
        } else {
            Object var2 = null;

            byte[] var5;
            try {
                var5 = var1.getBytes("UTF-8");
            } catch (UnsupportedEncodingException var4) {
                WTT.getLog().debug("UTF8 encoding not support!", var4);
                var5 = var1.getBytes();
            }

            return getDigest(var0, var5);
        }
    }

    public static String getDigestAsLowerHexString(String var0, String var1) throws NoSuchAlgorithmException {
        return var1 == null?null:StringX.bytesToHexString(getDigest(var0, var1.getBytes()), false);
    }

    public static String getDigestAsUpperHexString(String var0, String var1) throws NoSuchAlgorithmException {
        return var1 == null?null:StringX.bytesToHexString(getDigest(var0, var1.getBytes()), true);
    }

    public static String getDigestAsLowerHexString(String var0, byte[] var1) throws NoSuchAlgorithmException {
        return StringX.bytesToHexString(getDigest(var0, var1), false);
    }

    public static String getDigestAsUpperHexString(String var0, byte[] var1) throws NoSuchAlgorithmException {
        return StringX.bytesToHexString(getDigest(var0, var1), true);
    }
}
