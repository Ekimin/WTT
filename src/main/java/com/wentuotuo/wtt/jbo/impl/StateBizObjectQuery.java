//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.impl;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.jbo.BizObject;
import com.wentuotuo.wtt.jbo.BizObjectClass;
import com.wentuotuo.wtt.jbo.BizObjectManager;
import com.wentuotuo.wtt.jbo.BizObjectQuery;
import com.wentuotuo.wtt.jbo.JBOException;
import com.wentuotuo.wtt.jbo.JBOTransaction;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.security.AppContext;
import java.util.Date;
import java.util.List;

public class StateBizObjectQuery implements BizObjectQuery {
    protected BizObjectQuery query = null;
    private boolean defaultForUPdate = WTT.getProperty("com.wentuotuo.wtt.jbo.impl.StateBizObjectQuery.defaultForUpdate", false);

    protected StateBizObjectQuery(BizObjectQuery var1) {
        this.query = var1;
    }

    public List getResultList() throws JBOException {
        return this.getResultList(this.defaultForUPdate);
    }

    public List getResultList(boolean var1) throws JBOException {
        List var2 = this.query.getResultList(false);
        if(var1) {
            for(int var3 = 0; var3 < var2.size(); ++var3) {
                StateBizObject var4 = this.wrap((BizObject)var2.get(var3));
                var2.set(var3, var4);
            }
        }

        return var2;
    }

    public BizObject getSingleResult() throws JBOException {
        return this.getSingleResult(this.defaultForUPdate);
    }

    public BizObject getSingleResult(boolean var1) throws JBOException {
        BizObject var2 = this.query.getSingleResult(false);
        return (BizObject)(var2 != null && var1?this.wrap(var2):var2);
    }

    private StateBizObject wrap(BizObject var1) throws JBOException {
        StateBizObject var2 = null;
        if(var1 != null) {
            var2 = new StateBizObject(this.getBizObjectClass());
            var2.setValue(var1);
            var2.setState((byte)1);
        }

        return var2;
    }

    public BizObjectClass getBizObjectClass() {
        return this.query.getBizObjectClass();
    }

    public int getFirstResult() {
        return this.query.getFirstResult();
    }

    public int getMaxResults() {
        return this.query.getMaxResults();
    }

    public DataElement[] getParameters() {
        return this.query.getParameters();
    }

    public BizObjectQuery setFirstResult(int var1) {
        this.query.setFirstResult(var1);
        return this;
    }

    public BizObjectQuery setMaxResults(int var1) {
        this.query.setMaxResults(var1);
        return this;
    }

    public BizObjectQuery setParameter(DataElement var1) {
        this.query.setParameter(var1);
        return this;
    }

    public BizObjectQuery setParameter(String var1, boolean var2) {
        this.query.setParameter(var1, var2);
        return this;
    }

    public BizObjectQuery setParameter(String var1, Date var2) {
        this.query.setParameter(var1, var2);
        return this;
    }

    public BizObjectQuery setParameter(String var1, double var2) {
        this.query.setParameter(var1, var2);
        return this;
    }

    public BizObjectQuery setParameter(String var1, int var2) {
        this.query.setParameter(var1, var2);
        return this;
    }

    public BizObjectQuery setParameter(String var1, long var2) {
        this.query.setParameter(var1, var2);
        return this;
    }

    public BizObjectQuery setParameter(String var1, String var2) {
        this.query.setParameter(var1, var2);
        return this;
    }

    public int executeUpdate() throws JBOException {
        return this.query.executeUpdate();
    }

    public int getTotalCount() throws JBOException {
        return this.query.getTotalCount();
    }

    public String getDatabase() {
        return this.query.getDatabase();
    }

    public JBOTransaction getTransaction() {
        return this.query.getTransaction();
    }

    public void registerTransaction(JBOTransaction var1) throws JBOException {
        var1.join(this.query);
    }

    public void transactionComplete() {
        this.query.transactionComplete();
    }

    public int getQueryType() {
        return this.query.getQueryType();
    }

    public BizObjectManager getBizObjectManager() {
        return this.query.getBizObjectManager();
    }

    public double getSumOf(String var1) throws JBOException {
        return this.query.getSumOf(var1);
    }

    public void setAppContext(AppContext var1) {
        this.query.setAppContext(var1);
    }
}
