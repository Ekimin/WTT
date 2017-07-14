//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.security;

import com.wentuotuo.wtt.WTT;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESEncrypt {
    public DESEncrypt() {
    }

    public static byte[] encrypt(byte[] var0, byte[] var1) {
        return encrypt(var0, 1, var1);
    }

    public static byte[] encrypt(byte[] var0) {
        return encrypt((byte[])var0, (byte[])null);
    }

    public static byte[] decrypt(byte[] var0, byte[] var1) {
        return encrypt(var0, 2, var1);
    }

    public static byte[] decrypt(byte[] var0) {
        return decrypt((byte[])var0, (byte[])null);
    }

    public static String encrypt(String var0, String var1) {
        if(var0 == null) {
            return null;
        } else {
            byte[] var2 = string2Bytes(var0);
            byte[] var3 = encrypt(var2, var1 == null?null:string2Bytes(var1));
            StringBuffer var4 = new StringBuffer(1024);
            if(var3 != null && var3.length >= 1) {
                Random var5 = new Random((long)var0.length());

                for(int var7 = 0; var7 < var3.length; ++var7) {
                    char var6 = (char)(var5.nextInt(10) + 71);
                    var4.append(var6);
                    if(var3[var7] < 0) {
                        var6 = (char)(var5.nextInt(10) + 81);
                        var3[var7] = (byte)(-var3[var7]);
                        var4.append(var6);
                    }

                    var4.append(Integer.toString(var3[var7], 16).toUpperCase());
                }

                var4.deleteCharAt(0);
                return var4.toString();
            } else {
                return null;
            }
        }
    }

    public static String encrypt(String var0) {
        return encrypt((String)var0, (String)null);
    }

    public static String decrypt(String var0, String var1) {
        if(var0.length() < 1) {
            return null;
        } else {
            String[] var3 = var0.split("[G-Pg-p]");
            byte[] var4 = new byte[var3.length];

            for(int var5 = 0; var5 < var4.length; ++var5) {
                char var2 = var3[var5].charAt(0);
                if((var2 < 81 || var2 > 90) && (var2 < 113 || var2 > 122)) {
                    var4[var5] = Byte.parseByte(var3[var5], 16);
                } else {
                    var4[var5] = (byte)(-Byte.parseByte(var3[var5].substring(1), 16));
                }
            }

            byte[] var6 = decrypt(var4, var1 == null?null:string2Bytes(var1));
            if(var6 != null && var6.length >= 1) {
                return bytes2String(var6);
            } else {
                return null;
            }
        }
    }

    public static String decrypt(String var0) {
        return decrypt((String)var0, (String)null);
    }

    private static byte[] encrypt(byte[] var0, int var1, byte[] var2) {
        if(var2 == null || var2.length < 8) {
            byte[] var6 = new byte[]{-57, 115, 33, -116, 126, -56, -18, -103};
            if(var2 != null) {
                for(int var7 = 0; var7 < var2.length; ++var7) {
                    var6[var7] = var2[var7];
                }
            }

            var2 = var6;
        }

        byte[] var5;
        try {
            SecretKeyFactory var4 = SecretKeyFactory.getInstance("DES");
            DESKeySpec var3 = new DESKeySpec(var2);
            SecretKey var9 = var4.generateSecret(var3);
            Cipher var10 = Cipher.getInstance("DES/ECB/PKCS5Padding");
            var10.init(var1, var9);
            var5 = var10.doFinal(var0);
        } catch (Exception var8) {
            WTT.getLog().debug("DES Ecrypt error", var8);
            var5 = null;
        }

        return var5;
    }

    private static byte[] string2Bytes(String var0) {
        Object var1 = null;

        byte[] var4;
        try {
            var4 = var0.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
            WTT.getLog().debug("UTF-8 encoding not support!", var3);
            var4 = var0.getBytes();
        }

        return var4;
    }

    private static String bytes2String(byte[] var0) {
        String var1 = null;

        try {
            var1 = new String(var0, "UTF-8");
        } catch (UnsupportedEncodingException var3) {
            WTT.getLog().debug("UTF-8 encoding not support!", var3);
            var1 = new String(var0);
        }

        return var1;
    }
}
