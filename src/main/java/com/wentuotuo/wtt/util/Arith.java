//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util;

import java.math.BigDecimal;

public class Arith {
    private static final int DEF_DIV_SCALE = 10;
    private static final int DEF_ROUND_SCALE = 10;

    private Arith() {
    }

    public static double add(double var0, double var2) {
        BigDecimal var4 = new BigDecimal(Double.toString(var0));
        BigDecimal var5 = new BigDecimal(Double.toString(var2));
        return var4.add(var5).doubleValue();
    }

    public static double sub(double var0, double var2) {
        BigDecimal var4 = new BigDecimal(Double.toString(var0));
        BigDecimal var5 = new BigDecimal(Double.toString(var2));
        return var4.subtract(var5).doubleValue();
    }

    public static double mul(double var0, double var2) {
        BigDecimal var4 = new BigDecimal(Double.toString(var0));
        BigDecimal var5 = new BigDecimal(Double.toString(var2));
        return var4.multiply(var5).doubleValue();
    }

    public static double div(double var0, double var2) {
        return div(var0, var2, 10);
    }

    public static double div(double var0, double var2, int var4) {
        if(var4 < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal var5 = new BigDecimal(Double.toString(var0));
            BigDecimal var6 = new BigDecimal(Double.toString(var2));
            return var5.divide(var6, var4, 4).doubleValue();
        }
    }

    public static double round(double var0, int var2) {
        if(var2 < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal var3 = new BigDecimal(Double.toString(var0));
            BigDecimal var4 = new BigDecimal("1");
            return var3.divide(var4, var2, 4).doubleValue();
        }
    }

    public static double round(double var0) {
        return round(var0, 10);
    }

    public static void test() {
        System.out.println("[0.060000000000000005][" + add(0.05D, 0.01D) + "]");
        System.out.println("[0.5800000000000001][" + sub(1.0D, 0.42D) + "]");
        System.out.println("[401.49999999999994][" + mul(4.015D, 100.0D) + "]");
        System.out.println("[1.2329999999999999][" + div(123.3D, 100.0D) + "]");
        System.out.println(round(3.8046916019999996E7D, 6));
    }
}
