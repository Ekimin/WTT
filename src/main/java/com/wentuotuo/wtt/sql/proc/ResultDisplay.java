//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql.proc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultDisplay {
    void showResult(String var1, ResultSet var2);

    void showResult(int var1, String var2, long var3);

    void showConnection(Connection var1);

    void showException(SQLException var1);

    void showHelpMessage(String[] var1);

    void showMessage(String var1);
}
