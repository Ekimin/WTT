//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.WTT;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

public abstract class ConnectionWrapper implements Connection {
    private int id = 0;
    protected java.sql.Connection connection = null;
    protected String name = null;
    long createTime = 0L;

    protected ConnectionWrapper(int var1, String var2, java.sql.Connection var3) {
        this.id = var1;
        this.connection = var3;
        this.name = var2 == null?var3.toString():var2;
        if(WTT.getLog().isDebugEnabled()) {
            this.createTime = (new Date()).getTime();
            StringBuffer var4 = new StringBuffer("[SQL-CONN]");
            var4.append("{pid=0,id=").append(this.getId()).append(",name=").append(this.getName()).append("}");
            var4.append("{createTime=").append(this.createTime).append("}");
            WTT.getLog().debug(var4);
        }

    }

    public int getId() {
        return this.id;
    }

    protected void setName(String var1) {
        this.name = var1;
    }

    public String getName() {
        return this.name;
    }

    public void clearWarnings() throws SQLException {
        this.connection.clearWarnings();
    }

    public void close() throws SQLException {
        this.connection.close();
        if(WTT.getLog().isDebugEnabled()) {
            long var1 = System.currentTimeMillis();
            StringBuffer var3 = new StringBuffer("[SQL-CONN]");
            var3.append("{pid=0,id=").append(this.getId()).append(",name=").append(this.getName()).append("}");
            var3.append("{createTime=").append(this.createTime).append(",closeTime=").append(var1).append(",elapsedTime=").append((double)(var1 - this.createTime) / 1000.0D).append("}");
            WTT.getLog().debug(var3);
        }

    }

    public void commit() throws SQLException {
        this.connection.commit();
    }

    public Statement createStatement() throws SQLException {
        return this.connection.createStatement();
    }

    public Statement createStatement(int var1, int var2) throws SQLException {
        return this.connection.createStatement(var1, var2);
    }

    public Statement createStatement(int var1, int var2, int var3) throws SQLException {
        return this.connection.createStatement(var1, var2, var3);
    }

    public boolean getAutoCommit() throws SQLException {
        return this.connection.getAutoCommit();
    }

    public String getCatalog() throws SQLException {
        return this.connection.getCatalog();
    }

    public int getHoldability() throws SQLException {
        return this.connection.getHoldability();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return this.connection.getMetaData();
    }

    public int getTransactionIsolation() throws SQLException {
        return this.connection.getTransactionIsolation();
    }

    public Map getTypeMap() throws SQLException {
        return this.connection.getTypeMap();
    }

    public SQLWarning getWarnings() throws SQLException {
        return this.connection.getWarnings();
    }

    public boolean isClosed() throws SQLException {
        return this.connection.isClosed();
    }

    public boolean isReadOnly() throws SQLException {
        return this.connection.isReadOnly();
    }

    public String nativeSQL(String var1) throws SQLException {
        return this.connection.nativeSQL(var1);
    }

    public CallableStatement prepareCall(String var1) throws SQLException {
        return this.connection.prepareCall(var1);
    }

    public CallableStatement prepareCall(String var1, int var2, int var3) throws SQLException {
        return this.connection.prepareCall(var1, var2, var3);
    }

    public CallableStatement prepareCall(String var1, int var2, int var3, int var4) throws SQLException {
        return this.connection.prepareCall(var1, var2, var3, var4);
    }

    public PreparedStatement prepareStatement(String var1) throws SQLException {
        return this.connection.prepareStatement(var1);
    }

    public PreparedStatement prepareStatement(String var1, int var2) throws SQLException {
        return this.connection.prepareStatement(var1, var2);
    }

    public PreparedStatement prepareStatement(String var1, int[] var2) throws SQLException {
        return this.connection.prepareStatement(var1, var2);
    }

    public PreparedStatement prepareStatement(String var1, String[] var2) throws SQLException {
        return this.connection.prepareStatement(var1, var2);
    }

    public PreparedStatement prepareStatement(String var1, int var2, int var3) throws SQLException {
        return this.connection.prepareStatement(var1, var2, var3);
    }

    public PreparedStatement prepareStatement(String var1, int var2, int var3, int var4) throws SQLException {
        return this.connection.prepareStatement(var1, var2, var3, var4);
    }

    public void releaseSavepoint(Savepoint var1) throws SQLException {
        this.connection.releaseSavepoint(var1);
    }

    public void rollback() throws SQLException {
        this.connection.rollback();
    }

    public void rollback(Savepoint var1) throws SQLException {
        this.connection.rollback(var1);
    }

    public void setAutoCommit(boolean var1) throws SQLException {
        this.connection.setAutoCommit(var1);
    }

    public void setCatalog(String var1) throws SQLException {
        this.connection.setCatalog(var1);
    }

    public void setHoldability(int var1) throws SQLException {
        this.connection.setHoldability(var1);
    }

    public void setReadOnly(boolean var1) throws SQLException {
        this.connection.setReadOnly(var1);
    }

    public Savepoint setSavepoint() throws SQLException {
        return this.connection.setSavepoint();
    }

    public Savepoint setSavepoint(String var1) throws SQLException {
        return this.connection.setSavepoint(var1);
    }

    public void setTransactionIsolation(int var1) throws SQLException {
        this.connection.setTransactionIsolation(var1);
    }

    public void setTypeMap(Map var1) throws SQLException {
        this.connection.setTypeMap(var1);
    }
}
