//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql.proc;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.sql.SQLAnalyzer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLProcessor {
    private String[] help = new String[]{"Amarsoft SQL Processor,Version 1.0,(C)Copyright Amarsoft Tech.,Co.,Ltd.,2004", "?/help--This help message,show processor version and all supported commands.", "set autocommit true/false--set connection's auto commit or not.", "set transaction isolation--none,read_uncommitted,read_committed,repeatable_read,serializable", "connect to xxx--close any exists connection,then connect to specified database", "disconnect--close current connection", "SQL Statement--All standard sql DDL and DML,like select,update,create etc."};
    private Connection connection;
    private ResultDisplay resultDisplay;
    private SQLAnalyzer sqlAnalyzer = null;
    private ResultSet rs;
    private Statement st;

    public SQLProcessor() {
        this.resultDisplay = new PrintWriterResultDisplay();
        this.sqlAnalyzer = new SQLAnalyzer();
    }

    public SQLProcessor(ResultDisplay var1) {
        this.resultDisplay = var1;
        this.sqlAnalyzer = new SQLAnalyzer();
    }

    public SQLProcessor(ResultDisplay var1, SQLAnalyzer var2) {
        this.resultDisplay = var1;
        this.sqlAnalyzer = var2;
    }

    public int execute(String var1) {
        boolean var2 = false;
        boolean var3 = false;
        this.sqlAnalyzer.setOriginalSql(var1);
        byte var20 = this.sqlAnalyzer.getSqlType();
        String var4 = this.sqlAnalyzer.getFineSql();
        String[] var5 = this.sqlAnalyzer.getSqlWords();
        if(var4 != null && var20 >= 0) {
            if(this.connection == null && var20 != 100 && var20 != 101 && var20 != 120) {
                this.resultDisplay.showMessage("Database not connect yet!");
                return -1;
            } else if(var20 == 120) {
                this.resultDisplay.showHelpMessage(this.help);
                return 0;
            } else {
                int var6 = 0;

                try {
                    switch(var20) {
                        case 0:
                            this.st = this.connection.createStatement();
                            this.rs = this.st.executeQuery(var4);
                            this.resultDisplay.showResult(var4, this.rs);
                            break;
                        case 7:
                            if(!this.connection.getAutoCommit()) {
                                this.connection.commit();
                            }

                            this.resultDisplay.showMessage("All updates commited!");
                            break;
                        case 8:
                            if(!this.connection.getAutoCommit()) {
                                this.connection.rollback();
                            }

                            this.resultDisplay.showMessage("All updates rollbacked!");
                            break;
                        case 100:
                            if(this.connection != null && !this.connection.isClosed()) {
                                this.connection.close();
                                this.resultDisplay.showMessage("Database closed!");
                            }

                            this.connection = WTT.getDBConnection(var5[2]);
                            this.resultDisplay.showMessage("Database connected--\n" + this.connection.getMetaData().getURL());
                            break;
                        case 101:
                            if(this.connection != null && !this.connection.isClosed()) {
                                this.connection.close();
                                this.connection = null;
                                this.resultDisplay.showMessage("Database closed!");
                            }
                            break;
                        case 110:
                            this.connection.setAutoCommit(Boolean.getBoolean(var5[2]));
                            this.resultDisplay.showMessage(var4 + "!");
                            break;
                        case 111:
                            byte var7 = 2;
                            if(var5[3].equalsIgnoreCase("none")) {
                                var7 = 0;
                            }

                            if(var5[3].equalsIgnoreCase("read_uncommitted")) {
                                var7 = 1;
                            }

                            if(var5[3].equalsIgnoreCase("read_committed")) {
                                var7 = 2;
                            }

                            if(var5[3].equalsIgnoreCase("repeatable_read")) {
                                var7 = 4;
                            }

                            if(var5[3].equalsIgnoreCase("serializable")) {
                                var7 = 8;
                            }

                            this.connection.setTransactionIsolation(var7);
                            this.resultDisplay.showMessage(var4 + "!");
                            break;
                        default:
                            this.st = this.connection.createStatement();
                            int var19 = this.st.executeUpdate(var4);
                            if(var20 == 6 || var20 == 4 || var20 == 5) {
                                var19 = 1;
                            }

                            this.resultDisplay.showResult(var20, var4, (long)var19);
                    }
                } catch (SQLException var17) {
                    var6 = var17.getErrorCode();
                    this.resultDisplay.showException(var17);
                    WTT.getLog().debug(var17);
                } finally {
                    try {
                        if(this.rs != null) {
                            this.rs.close();
                            this.rs = null;
                        }

                        if(this.st != null) {
                            this.st.close();
                            this.st = null;
                        }
                    } catch (Exception var16) {
                        WTT.getLog().debug(var16);
                    }

                }

                return var6;
            }
        } else {
            this.resultDisplay.showMessage("Invalid statement!");
            return -1;
        }
    }

    public void exit() {
        this.execute("disconnect");
    }

    public ResultDisplay getResultDisplay() {
        return this.resultDisplay;
    }

    public void setResultDisplay(ResultDisplay var1) {
        this.resultDisplay = var1;
    }
}
