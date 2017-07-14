//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util;

import java.lang.reflect.Array;

public class MD5 {
    static final int S11 = 7;
    static final int S12 = 12;
    static final int S13 = 17;
    static final int S14 = 22;
    static final int S21 = 5;
    static final int S22 = 9;
    static final int S23 = 14;
    static final int S24 = 20;
    static final int S31 = 4;
    static final int S32 = 11;
    static final int S33 = 16;
    static final int S34 = 23;
    static final int S41 = 6;
    static final int S42 = 10;
    static final int S43 = 15;
    static final int S44 = 21;
    static final byte[] PADDING = new byte[]{-128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private long[] state = new long[4];
    private long[] count = new long[2];
    private byte[] buffer = new byte[64];
    public String digestHexStr;
    private byte[] digest = new byte[16];

    public String getMD5ofStr(String var1) {
        this.md5Init();
        this.md5Update(var1.getBytes(), var1.length());
        this.md5Final();
        this.digestHexStr = "";

        for(int var2 = 0; var2 < 16; ++var2) {
            this.digestHexStr = this.digestHexStr + byteHEX(this.digest[var2]);
        }

        return this.digestHexStr;
    }

    public byte[] getMD5ofBytes(String var1) {
        this.md5Init();
        this.md5Update(var1.getBytes(), var1.length());
        this.md5Final();
        return this.digest;
    }

    public MD5() {
        this.md5Init();
    }

    private void md5Init() {
        this.count[0] = 0L;
        this.count[1] = 0L;
        this.state[0] = 1732584193L;
        this.state[1] = 4023233417L;
        this.state[2] = 2562383102L;
        this.state[3] = 271733878L;
    }

    private long F(long var1, long var3, long var5) {
        return var1 & var3 | ~var1 & var5;
    }

    private long G(long var1, long var3, long var5) {
        return var1 & var5 | var3 & ~var5;
    }

    private long H(long var1, long var3, long var5) {
        return var1 ^ var3 ^ var5;
    }

    private long I(long var1, long var3, long var5) {
        return var3 ^ (var1 | ~var5);
    }

    private long FF(long var1, long var3, long var5, long var7, long var9, long var11, long var13) {
        var1 += this.F(var3, var5, var7) + var9 + var13;
        var1 = (long)((int)var1 << (int)var11 | (int)var1 >>> (int)(32L - var11));
        var1 += var3;
        return var1;
    }

    private long GG(long var1, long var3, long var5, long var7, long var9, long var11, long var13) {
        var1 += this.G(var3, var5, var7) + var9 + var13;
        var1 = (long)((int)var1 << (int)var11 | (int)var1 >>> (int)(32L - var11));
        var1 += var3;
        return var1;
    }

    private long HH(long var1, long var3, long var5, long var7, long var9, long var11, long var13) {
        var1 += this.H(var3, var5, var7) + var9 + var13;
        var1 = (long)((int)var1 << (int)var11 | (int)var1 >>> (int)(32L - var11));
        var1 += var3;
        return var1;
    }

    private long II(long var1, long var3, long var5, long var7, long var9, long var11, long var13) {
        var1 += this.I(var3, var5, var7) + var9 + var13;
        var1 = (long)((int)var1 << (int)var11 | (int)var1 >>> (int)(32L - var11));
        var1 += var3;
        return var1;
    }

    private void md5Update(byte[] var1, int var2) {
        byte[] var6 = new byte[64];
        int var4 = (int)(this.count[0] >>> 3) & 63;
        if((this.count[0] += (long)(var2 << 3)) < (long)(var2 << 3)) {
            ++this.count[1];
        }

        this.count[1] += (long)(var2 >>> 29);
        int var5 = 64 - var4;
        int var3;
        if(var2 >= var5) {
            this.md5Memcpy(this.buffer, var1, var4, 0, var5);
            this.md5Transform(this.buffer);

            for(var3 = var5; var3 + 63 < var2; var3 += 64) {
                this.md5Memcpy(var6, var1, 0, var3, 64);
                this.md5Transform(var6);
            }

            var4 = 0;
        } else {
            var3 = 0;
        }

        this.md5Memcpy(this.buffer, var1, var4, var3, var2 - var3);
    }

    private void md5Final() {
        byte[] var1 = new byte[8];
        this.Encode(var1, this.count, 8);
        int var2 = (int)(this.count[0] >>> 3) & 63;
        int var3 = var2 < 56?56 - var2:120 - var2;
        this.md5Update(PADDING, var3);
        this.md5Update(var1, 8);
        this.Encode(this.digest, this.state, 16);
    }

    private void md5Memcpy(byte[] var1, byte[] var2, int var3, int var4, int var5) {
        for(int var6 = 0; var6 < var5; ++var6) {
            var1[var3 + var6] = var2[var4 + var6];
        }

    }

    private void md5Transform(byte[] var1) {
        long var2 = this.state[0];
        long var4 = this.state[1];
        long var6 = this.state[2];
        long var8 = this.state[3];
        long[] var10 = new long[16];
        this.Decode(var10, var1, 64);
        var2 = this.FF(var2, var4, var6, var8, var10[0], 7L, 3614090360L);
        var8 = this.FF(var8, var2, var4, var6, var10[1], 12L, 3905402710L);
        var6 = this.FF(var6, var8, var2, var4, var10[2], 17L, 606105819L);
        var4 = this.FF(var4, var6, var8, var2, var10[3], 22L, 3250441966L);
        var2 = this.FF(var2, var4, var6, var8, var10[4], 7L, 4118548399L);
        var8 = this.FF(var8, var2, var4, var6, var10[5], 12L, 1200080426L);
        var6 = this.FF(var6, var8, var2, var4, var10[6], 17L, 2821735955L);
        var4 = this.FF(var4, var6, var8, var2, var10[7], 22L, 4249261313L);
        var2 = this.FF(var2, var4, var6, var8, var10[8], 7L, 1770035416L);
        var8 = this.FF(var8, var2, var4, var6, var10[9], 12L, 2336552879L);
        var6 = this.FF(var6, var8, var2, var4, var10[10], 17L, 4294925233L);
        var4 = this.FF(var4, var6, var8, var2, var10[11], 22L, 2304563134L);
        var2 = this.FF(var2, var4, var6, var8, var10[12], 7L, 1804603682L);
        var8 = this.FF(var8, var2, var4, var6, var10[13], 12L, 4254626195L);
        var6 = this.FF(var6, var8, var2, var4, var10[14], 17L, 2792965006L);
        var4 = this.FF(var4, var6, var8, var2, var10[15], 22L, 1236535329L);
        var2 = this.GG(var2, var4, var6, var8, var10[1], 5L, 4129170786L);
        var8 = this.GG(var8, var2, var4, var6, var10[6], 9L, 3225465664L);
        var6 = this.GG(var6, var8, var2, var4, var10[11], 14L, 643717713L);
        var4 = this.GG(var4, var6, var8, var2, var10[0], 20L, 3921069994L);
        var2 = this.GG(var2, var4, var6, var8, var10[5], 5L, 3593408605L);
        var8 = this.GG(var8, var2, var4, var6, var10[10], 9L, 38016083L);
        var6 = this.GG(var6, var8, var2, var4, var10[15], 14L, 3634488961L);
        var4 = this.GG(var4, var6, var8, var2, var10[4], 20L, 3889429448L);
        var2 = this.GG(var2, var4, var6, var8, var10[9], 5L, 568446438L);
        var8 = this.GG(var8, var2, var4, var6, var10[14], 9L, 3275163606L);
        var6 = this.GG(var6, var8, var2, var4, var10[3], 14L, 4107603335L);
        var4 = this.GG(var4, var6, var8, var2, var10[8], 20L, 1163531501L);
        var2 = this.GG(var2, var4, var6, var8, var10[13], 5L, 2850285829L);
        var8 = this.GG(var8, var2, var4, var6, var10[2], 9L, 4243563512L);
        var6 = this.GG(var6, var8, var2, var4, var10[7], 14L, 1735328473L);
        var4 = this.GG(var4, var6, var8, var2, var10[12], 20L, 2368359562L);
        var2 = this.HH(var2, var4, var6, var8, var10[5], 4L, 4294588738L);
        var8 = this.HH(var8, var2, var4, var6, var10[8], 11L, 2272392833L);
        var6 = this.HH(var6, var8, var2, var4, var10[11], 16L, 1839030562L);
        var4 = this.HH(var4, var6, var8, var2, var10[14], 23L, 4259657740L);
        var2 = this.HH(var2, var4, var6, var8, var10[1], 4L, 2763975236L);
        var8 = this.HH(var8, var2, var4, var6, var10[4], 11L, 1272893353L);
        var6 = this.HH(var6, var8, var2, var4, var10[7], 16L, 4139469664L);
        var4 = this.HH(var4, var6, var8, var2, var10[10], 23L, 3200236656L);
        var2 = this.HH(var2, var4, var6, var8, var10[13], 4L, 681279174L);
        var8 = this.HH(var8, var2, var4, var6, var10[0], 11L, 3936430074L);
        var6 = this.HH(var6, var8, var2, var4, var10[3], 16L, 3572445317L);
        var4 = this.HH(var4, var6, var8, var2, var10[6], 23L, 76029189L);
        var2 = this.HH(var2, var4, var6, var8, var10[9], 4L, 3654602809L);
        var8 = this.HH(var8, var2, var4, var6, var10[12], 11L, 3873151461L);
        var6 = this.HH(var6, var8, var2, var4, var10[15], 16L, 530742520L);
        var4 = this.HH(var4, var6, var8, var2, var10[2], 23L, 3299628645L);
        var2 = this.II(var2, var4, var6, var8, var10[0], 6L, 4096336452L);
        var8 = this.II(var8, var2, var4, var6, var10[7], 10L, 1126891415L);
        var6 = this.II(var6, var8, var2, var4, var10[14], 15L, 2878612391L);
        var4 = this.II(var4, var6, var8, var2, var10[5], 21L, 4237533241L);
        var2 = this.II(var2, var4, var6, var8, var10[12], 6L, 1700485571L);
        var8 = this.II(var8, var2, var4, var6, var10[3], 10L, 2399980690L);
        var6 = this.II(var6, var8, var2, var4, var10[10], 15L, 4293915773L);
        var4 = this.II(var4, var6, var8, var2, var10[1], 21L, 2240044497L);
        var2 = this.II(var2, var4, var6, var8, var10[8], 6L, 1873313359L);
        var8 = this.II(var8, var2, var4, var6, var10[15], 10L, 4264355552L);
        var6 = this.II(var6, var8, var2, var4, var10[6], 15L, 2734768916L);
        var4 = this.II(var4, var6, var8, var2, var10[13], 21L, 1309151649L);
        var2 = this.II(var2, var4, var6, var8, var10[4], 6L, 4149444226L);
        var8 = this.II(var8, var2, var4, var6, var10[11], 10L, 3174756917L);
        var6 = this.II(var6, var8, var2, var4, var10[2], 15L, 718787259L);
        var4 = this.II(var4, var6, var8, var2, var10[9], 21L, 3951481745L);
        this.state[0] += var2;
        this.state[1] += var4;
        this.state[2] += var6;
        this.state[3] += var8;
    }

    private void Encode(byte[] var1, long[] var2, int var3) {
        int var4 = 0;

        for(int var5 = 0; var5 < var3; var5 += 4) {
            var1[var5] = (byte)((int)(var2[var4] & 255L));
            var1[var5 + 1] = (byte)((int)(var2[var4] >>> 8 & 255L));
            var1[var5 + 2] = (byte)((int)(var2[var4] >>> 16 & 255L));
            var1[var5 + 3] = (byte)((int)(var2[var4] >>> 24 & 255L));
            ++var4;
        }

    }

    private void Decode(long[] var1, byte[] var2, int var3) {
        int var4 = 0;

        for(int var5 = 0; var5 < var3; var5 += 4) {
            var1[var4] = b2iu(var2[var5]) | b2iu(var2[var5 + 1]) << 8 | b2iu(var2[var5 + 2]) << 16 | b2iu(var2[var5 + 3]) << 24;
            ++var4;
        }

    }

    public static long b2iu(byte var0) {
        return var0 < 0?(long)(var0 & 255):(long)var0;
    }

    public static String byteHEX(byte var0) {
        char[] var1 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] var2 = new char[]{var1[var0 >>> 4 & 15], var1[var0 & 15]};
        String var3 = new String(var2);
        return var3;
    }

    public static void main(String[] var0) {
        MD5 var1 = new MD5();
        if(Array.getLength(var0) == 0) {
            System.out.println("MD5 Test suite:");
            System.out.println("MD5(\"\"):" + var1.getMD5ofStr(""));
            System.out.println("MD5(\"a\"):" + var1.getMD5ofStr("a"));
            System.out.println("MD5(\"abc\"):" + var1.getMD5ofStr("abc"));
            System.out.println("MD5(\"message digest\"):" + var1.getMD5ofStr("message digest"));
            System.out.println("MD5(\"abcdefghijklmnopqrstuvwxyz\"):" + var1.getMD5ofStr("abcdefghijklmnopqrstuvwxyz"));
            System.out.println("MD5(\"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789\"):" + var1.getMD5ofStr("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"));
        } else {
            System.out.println("MD5(" + var0[0] + ")=" + var1.getMD5ofStr(var0[0]));
        }

    }
}
