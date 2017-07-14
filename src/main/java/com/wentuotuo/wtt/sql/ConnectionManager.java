//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.WTT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
    public ConnectionManager() {
    }

    public static DataSource getDataSource(String var0) throws NamingException {
        DataSource var1 = null;

        try {
            InitialContext var2 = new InitialContext();
            var1 = (DataSource)var2.lookup(var0);
            var2.close();
            return var1;
        } catch (NamingException var3) {
            WTT.getLog().fatal("get dataSource error!", var3);
            var3.printStackTrace();
            throw var3;
        }
    }

    public static DataSource getDataSource(String var0, String var1, String var2) throws Exception {
        InitialContext var3 = null;
        Hashtable var4 = new Hashtable();
        if(var0 != null && var0.trim().length() > 0) {
            var4.put("java.naming.factory.initial", var0);
        }

        if(var1 != null && var1.trim().length() > 0) {
            var4.put("java.naming.provider.url", var1);
        }

        if(var4.size() > 0) {
            var3 = new InitialContext(var4);
        } else {
            var3 = new InitialContext();
        }

        DataSource var5 = (DataSource)var3.lookup(var2);
        var3.close();
        return var5;
    }

    public static Connection getConnection(String var0) throws NamingException, SQLException {
        DataSource var1 = getDataSource(var0);
        return var1.getConnection();
    }

    public static Connection getConnection(String var0, String var1, String var2, String var3) throws Exception {
        Class.forName(var1);
        return DriverManager.getConnection(var0, var2, var3);
    }

    public static Connection getConnection(String var0, String var1, String var2) throws Exception {
        InitialContext var3 = null;
        Hashtable var4 = new Hashtable();
        var4.put("java.naming.factory.initial", var0);
        if(var1 != null) {
            var4.put("java.naming.provider.url", var1);
        }

        var3 = new InitialContext(var4);
        DataSource var5 = (DataSource)var3.lookup(var2);
        var3.close();
        return var5.getConnection();
    }

    public static Connection getConnection(DataSource var0) throws Exception {
        return var0.getConnection();
    }

    public static Transaction getTransaction(String var0, String var1, String var2, String var3) throws Exception {
        return new Transaction(getConnection(var0, var1, var2, var3));
    }

    public static Transaction getTransaction(int var0, String var1, String var2, String var3, String var4) throws Exception {
        return new Transaction(var0, getConnection(var1, var2, var3, var4));
    }

    public static Transaction getTransaction(String var0) throws Exception {
        return new Transaction(getConnection(var0));
    }

    public static Transaction getTransaction(String var0, String var1, String var2) throws Exception {
        return new Transaction(getConnection(var0, var1, var2));
    }

    public static Transaction getTransaction(int var0, String var1, String var2, String var3) throws Exception {
        return new Transaction(var0, getConnection(var1, var2, var3));
    }

    public static Transaction getTransaction(DataSource var0) throws Exception {
        return new Transaction(getConnection(var0));
    }

    public static Transaction getTransaction(int var0, DataSource var1) throws Exception {
        return new Transaction(var0, getConnection(var1));
    }

    public static Connection getConnection(DataSource var0, String var1, String var2) throws Exception {
        return var0.getConnection(var1, var2);
    }

    public static Transaction getTransaction(DataSource var0, String var1, String var2) throws Exception {
        return new Transaction(getConnection(var0, var1, var2));
    }

    public static Transaction getTransaction(int var0, DataSource var1, String var2, String var3) throws Exception {
        return new Transaction(var0, getConnection(var1, var2, var3));
    }
}
