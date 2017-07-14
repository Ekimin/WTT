//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.security.AppContext;
import com.wentuotuo.wtt.security.DefaultAppContext;
import com.wentuotuo.wtt.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class LocalTransaction implements JBOTransaction {
    private static int maxId = 0;
    private int id = 0;
    private ArrayList shareobjects = new ArrayList();
    private String database = null;
    private Connection connection = null;
    private AppContext appContext = null;

    public LocalTransaction() {
        if(maxId == 2147483647) {
            maxId = 0;
        }

        this.id = maxId++;
        if(WTT.getLog().isDebugEnabled()) {
            WTT.getLog().debug("[JBO-TRANS]{" + this.getId() + "}{CreateTime=" + (new Date()).getTime() + "}");
        }

    }

    public LocalTransaction(String var1) {
        this.database = var1;
        if(maxId == 2147483647) {
            maxId = 0;
        }

        this.id = maxId++;
        if(WTT.getLog().isDebugEnabled()) {
            WTT.getLog().debug("[JBO-TRANS]{" + this.getId() + "}{CreateTime=" + (new Date()).getTime() + "}");
        }

    }

    public int getId() {
        return this.id;
    }

    private void reset() {
        for(int var1 = 0; var1 < this.shareobjects.size(); ++var1) {
            ShareTransaction var2 = (ShareTransaction)this.shareobjects.get(var1);
            var2.transactionComplete();
        }

        this.shareobjects.clear();
        if(this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException var3) {
                WTT.getLog().warn(var3);
            }

            this.connection = null;
        }

        this.setDatabase((String)null);
    }

    public void commit() throws JBOException {
        if(WTT.getLog().isDebugEnabled()) {
            WTT.getLog().debug("[JBO-TRANS]{" + this.getId() + "}{CommitTime=" + (new Date()).getTime() + "}");
        }

        if(this.connection == null) {
            WTT.getLog().trace("No connection before commit, do nothing here!");
            this.reset();
        } else {
            try {
                this.connection.commit();
            } catch (SQLException var5) {
                WTT.getLog().warn(var5);
                throw new JBOException(1312, var5);
            } finally {
                this.reset();
            }

        }
    }

    public void rollback() throws JBOException {
        if(WTT.getLog().isDebugEnabled()) {
            WTT.getLog().debug("[JBO-TRANS]{" + this.getId() + "}{RollbackTime=" + (new Date()).getTime() + "}");
        }

        if(this.connection == null) {
            WTT.getLog().trace("No connection before rollback, do nothing here!");
            this.reset();
        } else {
            try {
                this.connection.rollback();
            } catch (SQLException var5) {
                WTT.getLog().warn(var5);
                throw new JBOException(1313, var5);
            } finally {
                this.reset();
            }

        }
    }

    public void join(ShareTransaction var1) throws JBOException {
        if(var1.getTransaction() != null) {
            throw new JBOException(1334);
        } else {
            String var2 = this.getDatabase();
            String var3 = var1.getDatabase();
            if(var3 == null) {
                throw new JBOException(1316);
            } else {
                if(var2 == null) {
                    this.setDatabase(var3);
                } else if(!var2.equals(var3)) {
                    throw new JBOException(1317, new String[]{var2, var3});
                }

                if(!this.shareobjects.contains(var1)) {
                    this.shareobjects.add(var1);
                    var1.registerTransaction(this);
                    var1.setAppContext(this.getappContext());
                    WTT.getLog().trace("New ShareTransaction object joined, total " + this.shareobjects.size());
                }

            }
        }
    }

    public String getDatabase() {
        return this.database;
    }

    protected void setDatabase(String var1) {
        this.database = var1;
    }

    public Connection getConnection(ShareTransaction var1) throws JBOException {
        if(!this.shareobjects.contains(var1)) {
            throw new JBOException(1314, new String[]{var1.toString()});
        } else {
            try {
                if(this.connection == null || this.connection.isClosed()) {
                    this.connection = WTT.getDBConnection(this.getDatabase());
                    this.connection.setAutoCommit(false);
                }
            } catch (SQLException var3) {
                WTT.getLog().warn(var3);
                throw new JBOException(1315, new String[]{this.getDatabase()}, var3);
            }

            return this.connection;
        }
    }

    public AppContext getappContext() {
        if(this.appContext == null) {
            this.appContext = new DefaultAppContext();
        }

        return this.appContext;
    }

    public void setAppContext(AppContext var1) {
        if(var1 != null) {
            this.appContext = var1;

            for(int var2 = 0; var2 < this.shareobjects.size(); ++var2) {
                ShareTransaction var3 = (ShareTransaction)this.shareobjects.get(var2);
                var3.setAppContext(var1);
            }
        }

    }
}
