//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import java.sql.SQLException;

public interface Connection extends java.sql.Connection {
    int getId();

    String getName();

    Query createQuery(String var1) throws SQLException;

    Query createQuery(String var1, int var2, int var3) throws SQLException;
}
