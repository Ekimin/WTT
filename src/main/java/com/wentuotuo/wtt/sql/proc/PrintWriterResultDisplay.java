//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql.proc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class PrintWriterResultDisplay implements ResultDisplay {
    private PrintWriter writer = null;

    public PrintWriterResultDisplay() {
        this.writer = new PrintWriter(System.out);
    }

    public PrintWriterResultDisplay(PrintWriter var1) {
        this.writer = var1;
    }

    public void showResult(String var1, ResultSet var2) {
        try {
            ResultSetMetaData var3 = var2.getMetaData();
            int var4 = var3.getColumnCount();

            int var5;
            for(var5 = 1; var5 <= var4; ++var5) {
                this.writer.print(var3.getColumnLabel(var5) + "\t");
            }

            this.writer.println();
            this.writer.flush();

            while(var2.next()) {
                for(var5 = 1; var5 <= var4; ++var5) {
                    this.writer.print(var2.getString(var5) + "\t");
                }

                this.writer.println();
            }

            this.writer.flush();
        } catch (SQLException var6) {
            this.showException(var6);
        }

    }

    public void showResult(int var1, String var2, long var3) {
        StringBuffer var5 = new StringBuffer();
        var5.append(var3);
        switch(var1) {
            case 0:
                var5.append(" Rows Selected!");
                break;
            case 1:
                var5.append(" Rows Updated!");
                break;
            case 2:
                var5.append(" Rows Inserted!");
                break;
            case 3:
                var5.append(" Rows Deleted!");
                break;
            case 4:
                var5.append(" Objects Created!");
                break;
            case 5:
                var5.append(" Objects Droped!");
                break;
            case 6:
                var5.append(" Objects Altered!");
                break;
            case 100:
                var5 = new StringBuffer("Database Connected!");
                break;
            case 101:
                var5 = new StringBuffer("Database Closed!");
                break;
            default:
                var5 = new StringBuffer("Invalid Statement!");
        }

        this.writer.println(var5);
        this.writer.flush();
    }

    public void showConnection(Connection var1) {
        this.writer.println(var1);
        this.writer.flush();
    }

    public void showException(SQLException var1) {
        this.writer.println(var1.getMessage());
        this.writer.flush();
    }

    public void showHelpMessage(String[] var1) {
        for(int var2 = 0; var2 < var1.length; ++var2) {
            this.writer.println(var1[var2]);
        }

        this.writer.flush();
    }

    public void showMessage(String var1) {
        this.writer.println(var1);
        this.writer.flush();
    }
}
