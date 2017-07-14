//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.lang.StringX;

import java.sql.*;
import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.Executor;

public class DefaultQueryConnection extends ConnectionWrapper {
    public DefaultQueryConnection(int var1, String var2, Connection var3) {
        super(var1, var2, var3);
    }

    public Query createQuery(String var1) throws SQLException {
        if(var1 == null) {
            throw new SQLException("Null sql to building");
        } else {
            DefaultQuery var2 = new DefaultQuery(this);
            var2.setOriginalSql(var1);
            this.buildParameters(var2);
            var2.setSqlType(this.getSqlType(var2.getOriginalSql()));
            var2.createStement(this.connection);
            return var2;
        }
    }

    public Query createQuery(String var1, int var2, int var3) throws SQLException {
        if(var1 == null) {
            throw new SQLException("Null sql to building");
        } else {
            DefaultQuery var4 = new DefaultQuery(this, var2, var3);
            var4.setOriginalSql(var1);
            this.buildParameters(var4);
            var4.setSqlType(this.getSqlType(var4.getOriginalSql()));
            var4.createStement(this.connection);
            return var4;
        }
    }

    private void buildParameters(DefaultQuery var1) throws SQLException {
        String var2 = StringX.trimAll(var1.getOriginalSql()).replaceAll("\\s*,\\s*", ",").replaceAll("\\s+", " ");
        StringBuffer var3 = new StringBuffer();
        char[] var4 = var2.toCharArray();
        int var5 = 0;
        int var6 = 0;

        while(true) {
            while(var5 < var4.length) {
                switch(var4[var5]) {
                    case '\'':
                        do {
                            var3.append(var4[var5]);
                            ++var5;
                        } while(var5 < var4.length && var4[var5] != 39);

                        if(var5 == var4.length) {
                            throw new SQLException("SQL中有未关闭的字符串常量：" + var2);
                        }

                        var3.append(var4[var5]);
                        ++var5;
                        break;
                    case ':':
                        ++var5;
                        if(var5 == var4.length || !Character.isJavaIdentifierStart(var4[var5])) {
                            throw new SQLException("SQL未正常结束：" + var2);
                        }

                        ++var6;
                        int var7 = var5;

                        do {
                            ++var5;
                        } while(var5 < var4.length && Character.isJavaIdentifierPart(var4[var5]));

                        String var8 = new String(var4, var7, var5 - var7);
                        var3.append('?');
                        var1.addParameter(var8, var6);
                        break;
                    default:
                        var3.append(var4[var5]);
                        ++var5;
                }
            }

            var1.setFineSql(var3.toString());
            return;
        }
    }

    private int getSqlType(String var1) {
        byte var2 = -1;
        String[] var3 = var1.split(" ");
        if(var3.length < 1) {
            var2 = -1;
            return var2;
        } else if(var3.length == 1) {
            if(var3[0].equalsIgnoreCase("disconnect")) {
                var2 = 101;
            } else if(!var3[0].equalsIgnoreCase("?") && !var3[0].equalsIgnoreCase("help")) {
                if(var3[0].equalsIgnoreCase("commit")) {
                    var2 = 7;
                } else if(var3[0].equalsIgnoreCase("rollback")) {
                    var2 = 8;
                } else {
                    var2 = -1;
                }
            } else {
                var2 = 120;
            }

            return var2;
        } else {
            byte var5;
            if(var3[0].equalsIgnoreCase("select")) {
                var5 = 0;
                return var5;
            } else if(var3[0].equalsIgnoreCase("update")) {
                var5 = 1;
                return var5;
            } else if(var3[0].equalsIgnoreCase("insert")) {
                if(!var3[1].equalsIgnoreCase("into")) {
                    var2 = -1;
                } else {
                    var2 = 2;
                }

                return var2;
            } else if(var3[0].equalsIgnoreCase("delete")) {
                if(!var3[1].equalsIgnoreCase("from")) {
                    var2 = -1;
                } else {
                    var2 = 3;
                }

                return var2;
            } else {
                if(var3[0].equalsIgnoreCase("create")) {
                    var2 = 4;
                }

                if(var3[0].equalsIgnoreCase("drop")) {
                    var2 = 5;
                }

                if(var3[0].equalsIgnoreCase("alter")) {
                    var2 = 6;
                }

                if(var2 != -1) {
                    String var4 = var3[1].toLowerCase();
                    if(!var4.equals("table") && !var4.equals("index") && !var4.equals("procedure") && !var4.equals("function") && !var4.equals("view") && !var4.equals("database")) {
                        var2 = -1;
                    }

                    return var2;
                } else if(var3[0].equalsIgnoreCase("connect")) {
                    if(var3[1].equalsIgnoreCase("to") && var3.length == 3) {
                        var2 = 100;
                    } else {
                        var2 = -1;
                    }

                    return var2;
                } else {
                    if(var3[0].equalsIgnoreCase("set")) {
                        if(var3[1].equalsIgnoreCase("autocommit")) {
                            if(var3.length != 3) {
                                var2 = -1;
                            } else if(!var3[2].equalsIgnoreCase("true") && !var3[2].equalsIgnoreCase("false")) {
                                var2 = -1;
                            } else {
                                var2 = 110;
                            }

                            return var2;
                        }

                        if(var3[1].equalsIgnoreCase("transaction")) {
                            if(var3.length != 4) {
                                var2 = -1;
                            } else if(!var3[2].equalsIgnoreCase("isolation") || !var3[3].equalsIgnoreCase("none") && !var3[3].equalsIgnoreCase("read_uncommitted") && !var3[3].equalsIgnoreCase("read_committed") && !var3[3].equalsIgnoreCase("repeatable_read") && !var3[3].equalsIgnoreCase("serializable")) {
                                var2 = -1;
                            } else {
                                var2 = 111;
                            }

                            return var2;
                        }
                    } else {
                        var2 = -1;
                    }

                    return var2;
                }
            }
        }
    }

    public Clob createClob() throws SQLException {
        return null;
    }

    public Blob createBlob() throws SQLException {
        return null;
    }

    public NClob createNClob() throws SQLException {
        return null;
    }

    public SQLXML createSQLXML() throws SQLException {
        return null;
    }

    public boolean isValid(int timeout) throws SQLException {
        return false;
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {

    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {

    }

    public String getClientInfo(String name) throws SQLException {
        return null;
    }

    public Properties getClientInfo() throws SQLException {
        return null;
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return null;
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return null;
    }

    public void setSchema(String schema) throws SQLException {

    }

    public String getSchema() throws SQLException {
        return null;
    }

    public void abort(Executor executor) throws SQLException {

    }

    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
