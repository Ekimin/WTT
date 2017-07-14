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
import java.util.Properties;

public abstract class AbstractBizObjectManager implements BizObjectManager {
    private String id;
    private String describe;
    private String database = null;
    private JBOTransaction transaction = null;
    protected BizObjectClass clazz = null;
    private boolean traceQuery = false;
    private AppContext appContext = null;
    private boolean audit = false;
    private Properties queryProperties = null;

    public AbstractBizObjectManager() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String var1) {
        this.id = var1;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String var1) {
        this.describe = var1;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String var1) {
        this.database = var1;
    }

    public boolean isTraceQuery() {
        return this.traceQuery;
    }

    public void setTraceQuery(boolean var1) {
        this.traceQuery = var1;
    }

    public boolean isAudit() {
        return this.audit;
    }

    public void setAudit(boolean var1) {
        this.audit = var1;
    }

    public BizObjectKey getKey() throws JBOException {
        return new BizObjectKey(this.clazz);
    }

    public BizObjectKey getBizObjectKey() throws JBOException {
        return this.getKey();
    }

    public BizObjectClass getManagedClass() {
        return this.clazz;
    }

    public void setManagedClass(BizObjectClass var1) {
        if(this.clazz == null) {
            this.clazz = var1;
        }

    }

    public BizObject getBizObject(BizObjectKey var1) throws JBOException {
        return this.getObject(var1);
    }

    public BizObject getBizObject(Object var1) throws JBOException {
        return this.getObject(var1);
    }

    public BizObject getObject(Object var1) throws JBOException {
        if(var1 == null) {
            throw new JBOException(" KeyValue is null!");
        } else {
            BizObjectKey var2 = this.getKey();
            if(var2.getAttributeNumber() != 1) {
                throw new JBOException(1325, new String[]{this.clazz.getAbsoluteName()});
            } else {
                var2.setAttributeValue(0, var1);
                return this.getObject(var2);
            }
        }
    }

    public void deleteObject(BizObject var1) throws JBOException {
        BizObjectKey var2 = var1.getKey();
        if(var2 == null) {
            throw new JBOException(1310, new String[]{var1.getBizObjectClass().getAbsoluteName()});
        } else {
            this.deleteObject(var2);
        }
    }

    protected Connection getConnection() throws SQLException, JBOException {
        if(this.transaction == null) {
            Connection var1 = WTT.getDBConnection(this.getDatabase());
            var1.setAutoCommit(true);
            return var1;
        } else {
            return this.transaction.getConnection(this);
        }
    }

    protected void closeConnection(Connection var1) {
        if(var1 != null) {
            if(this.transaction == null) {
                try {
                    if(!var1.isClosed()) {
                        var1.close();
                    }
                } catch (SQLException var3) {
                    WTT.getLog().debug(var3);
                }
            }

        }
    }

    public void transactionComplete() {
        this.transaction = null;
        this.setAppContext((AppContext)null);
    }

    public JBOTransaction getTransaction() {
        return this.transaction;
    }

    public void registerTransaction(JBOTransaction var1) {
        this.transaction = var1;
    }

    public void setAppContext(AppContext var1) {
        this.appContext = var1;
    }

    public AppContext getAppContext() {
        if(this.appContext == null) {
            this.appContext = new DefaultAppContext();
        }

        return this.appContext;
    }

    public void setQueryProperties(Properties var1) {
        this.queryProperties = var1;
    }

    public Properties getQueryProperties() {
        if(this.queryProperties == null) {
            this.queryProperties = new Properties();
        }

        return this.queryProperties;
    }
}
