//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.dataconvert;

import java.util.Date;

public interface DataConvertor {
    void setValue(String var1);

    void setValue(int var1);

    void setValue(long var1);

    void setValue(double var1);

    void setValue(boolean var1);

    void setValue(Date var1);

    String getString();

    int getInt();

    long getLong();

    double getDouble();

    boolean getBoolean();

    Date getDate();

    void close();
}
