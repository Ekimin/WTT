//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import com.wentuotuo.wtt.jbo.ql.Parser;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.security.AppContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractBizObjectQuery implements BizObjectQuery {
    private int firstResult = 0;
    private int maxResult = 0;
    private String statement = null;
    protected BizObjectClass bizObjectClass;
    protected BizObjectManager bizObjectManager;
    protected List parameters = null;
    protected String database = null;
    protected JBOTransaction transaction = null;
    private AppContext appContext = null;
    private boolean traceQuery = false;
    private int largeResultWarn = 1000;
    private int largeResultLimit = 10000;

    protected AbstractBizObjectQuery(BizObjectManager var1, String var2) throws JBOException {
        this.bizObjectManager = var1;
        this.bizObjectClass = var1.getManagedClass();
        this.statement = StringX.isSpace(var2)?null:StringX.trimAll(var2);
        this.parameters = new ArrayList();
        this.traceQuery = StringX.parseBoolean(var1.getQueryProperties().getProperty("traceQuery", "false"));
    }

    public int getFirstResult() {
        return this.firstResult;
    }

    public BizObjectClass getBizObjectClass() {
        return this.bizObjectClass;
    }

    public int getMaxResults() {
        return this.maxResult;
    }

    public DataElement[] getParameters() {
        return (DataElement[])this.parameters.toArray(new DataElement[this.parameters.size()]);
    }

    public BizObjectQuery setFirstResult(int var1) {
        this.firstResult = var1;
        return this;
    }

    public BizObjectQuery setMaxResults(int var1) {
        this.maxResult = var1;
        return this;
    }

    public BizObjectQuery setParameter(DataElement var1) {
        this.parameters.add(var1);
        return this;
    }

    public BizObjectQuery setParameter(String var1, int var2) {
        this.parameters.add(DataElement.valueOf(var1, var2));
        return this;
    }

    public BizObjectQuery setParameter(String var1, long var2) {
        this.parameters.add(DataElement.valueOf(var1, var2));
        return this;
    }

    public BizObjectQuery setParameter(String var1, double var2) {
        this.parameters.add(DataElement.valueOf(var1, var2));
        return this;
    }

    public BizObjectQuery setParameter(String var1, String var2) {
        this.parameters.add(DataElement.valueOf(var1, var2));
        return this;
    }

    public BizObjectQuery setParameter(String var1, Date var2) {
        this.parameters.add(DataElement.valueOf(var1, var2));
        return this;
    }

    public BizObjectQuery setParameter(String var1, boolean var2) {
        this.parameters.add(DataElement.valueOf(var1, var2));
        return this;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String var1) {
        this.database = var1;
    }

    public JBOTransaction getTransaction() {
        return this.transaction;
    }

    public void registerTransaction(JBOTransaction var1) {
        this.transaction = var1;
    }

    public void transactionComplete() {
        this.transaction = null;
    }

    protected Parser parseQuery() throws JBOException {
        Parser var1 = Parser.createParser();
        var1.parse(this.getBizObjectClass(), this.getStatement());
        return var1;
    }

    public String getStatement() {
        return this.statement;
    }

    public BizObjectManager getBizObjectManager() {
        return this.bizObjectManager;
    }

    public void setAppContext(AppContext var1) {
        this.appContext = var1;
    }

    public AppContext getAppContext() {
        return this.appContext;
    }

    public int getLargeResultWarn() {
        return this.largeResultWarn;
    }

    public void setLargeResultWarn(int var1) {
        this.largeResultWarn = var1;
    }

    public int getLargeResultLimit() {
        return this.largeResultLimit;
    }

    public void setLargeResultLimit(int var1) {
        this.largeResultLimit = var1;
    }

    public boolean isTraceQuery() {
        return this.traceQuery;
    }

    public void setTraceQuery(boolean var1) {
        this.traceQuery = var1;
    }
}
