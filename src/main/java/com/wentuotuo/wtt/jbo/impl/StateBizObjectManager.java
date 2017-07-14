//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.impl;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.jbo.AbstractBizObjectManager;
import com.wentuotuo.wtt.jbo.BizObject;
import com.wentuotuo.wtt.jbo.BizObjectClass;
import com.wentuotuo.wtt.jbo.BizObjectKey;
import com.wentuotuo.wtt.jbo.BizObjectManager;
import com.wentuotuo.wtt.jbo.BizObjectQuery;
import com.wentuotuo.wtt.jbo.JBOClassMismatchException;
import com.wentuotuo.wtt.jbo.JBOException;
import com.wentuotuo.wtt.jbo.audit.BizObjectData;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.ObjectX;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.security.audit.AuditData;
import com.wentuotuo.wtt.security.audit.AuditRecord;
import com.wentuotuo.wtt.security.audit.Auditor;
import com.wentuotuo.wtt.sql.Connection;
import com.wentuotuo.wtt.sql.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateBizObjectManager extends AbstractBizObjectManager implements BizObjectTableMapper {
    private String table = null;
    private int tableNameCase = 0;
    private int columnNameCase = 0;
    private char identifierQuoteCharacter = 0;
    private Map attributeMap = null;
    private String filterColumn = null;
    private String filterValue = null;
    private String updateIncludes = null;
    private String updateExcludes = null;
    private List updateInclude = null;
    private List updateExclude = null;
    private List basicAttribute = null;
    private boolean uaInited = false;

    public StateBizObjectManager() {
    }

    public BizObjectQuery createQuery(String var1) throws JBOException {
        BizObjectQuery var2 = this.createBasicQuery(this, var1);
        if(var2 instanceof BasicSQLQuery) {
            ((BasicSQLQuery)var2).setDatabase(this.getDatabase());
        }

        if(this.getAppContext() != null) {
            var2.setAppContext(this.getAppContext());
        }

        ObjectX.setProperties(var2, this.getQueryProperties(), false);
        StateBizObjectQuery var3 = new StateBizObjectQuery(var2);
        ObjectX.setProperties(var3, this.getQueryProperties(), false);
        if(this.getTransaction() != null) {
            this.getTransaction().join(var2);
        }

        return var3;
    }

    protected BizObjectQuery createBasicQuery(BizObjectManager var1, String var2) throws JBOException {
        BasicSQLQuery var3 = new BasicSQLQuery(this, var2);
        return var3;
    }

    public BizObject getObject(BizObjectKey var1) throws JBOException {
        StateBizObjectQuery var2 = (StateBizObjectQuery)this.createQuery(this.getKeyQuery(var1));
        DataElement[] var3 = var1.getAttributes();

        for(int var4 = 0; var4 < var3.length; ++var4) {
            var2.setParameter(var3[var4]);
        }

        return var2.getSingleResult(true);
    }

    public BizObject newObject() throws JBOException {
        StateBizObject var1 = new StateBizObject(this.clazz);
        var1.setState((byte) 2);
        return var1;
    }

    public BizObject newObject(DataElement[] var1) throws JBOException {
        BizObjectClass var2 = this.clazz.extend(var1);
        StateBizObject var3 = new StateBizObject(var2);
        var3.setState((byte)2);
        return var3;
    }

    public void deleteObject(BizObjectKey var1) throws JBOException {
        if(!var1.getBizObjectClass().equals(this.clazz) && !var1.getBizObjectClass().getParent().equals(this.clazz)) {
            throw new JBOClassMismatchException(this.clazz, var1.getBizObjectClass());
        } else {
            String var2 = this.getDeleteSql(var1);
            if(var2 != null) {
                this.executeUpdate(var2, var1.getAttributes());
            }

            if(this.isAudit()) {
                Auditor.getAuditor("com.wentuotuo.wtt.jbo").audit(this.createAuditRecord("DELETE", var1.getAttributes(), (DataElement[])null, (Object[])null));
            }

        }
    }

    public void deleteObject(BizObject var1) throws JBOException {
        super.deleteObject(var1);
        if(var1 instanceof StateBizObject) {
            ((StateBizObject)var1).setState((byte)0);
        }

    }

    public void saveObject(BizObject var1) throws JBOException {
        if(var1 == null) {
            WTT.getLog().debug("Try to save null object,ignored! " + this.clazz);
        }

        if(!var1.instanceOf(this.clazz)) {
            throw new JBOClassMismatchException(this.clazz, var1.getBizObjectClass());
        } else if(!(var1 instanceof StateBizObject)) {
            throw new JBOException(1320, new String[]{var1.getClass().getName()});
        } else {
            StateBizObject var2 = (StateBizObject)var1;
            byte var3 = var2.getState();
            if(var3 == 0) {
                throw new JBOException(1321, new String[]{var2.toString()});
            } else {
                DataElement[] var4 = this.getUpdateAttributes(var2.getChangedAttributes());
                if(var4 != null && var4.length >= 1) {
                    String var5 = null;
                    DataElement[] var6 = null;
                    int var8;
                    if(var3 == 2) {
                        var5 = this.getInsertSql(var4);
                        var6 = var4;
                    } else if(var3 == 3) {
                        DataElement[] var7 = ((StateBizObject)var1).getOriginKeyAttributes();
                        var5 = this.getUpdateSql(var4, var7);
                        var6 = new DataElement[var4.length + var7.length];
                        System.arraycopy(var4, 0, var6, 0, var4.length);

                        for(var8 = 0; var8 < var7.length; ++var8) {
                            var6[var4.length + var8] = new DataElement("_KEY_" + var7[var8].getName(), var7[var8].getType());
                            var6[var4.length + var8].setValue(var7[var8]);
                        }
                    }

                    if(var5 != null) {
                        this.executeUpdate(var5, var6);
                    }

                    if(this.isAudit()) {
                        if(var3 == 2) {
                            Auditor.getAuditor("com.wentuotuo.wtt.jbo").audit(this.createAuditRecord("CREATE", var2.getKey().getAttributes(), var4, (Object[])null));
                        } else if(var3 == 3) {
                            Object[] var9 = new Object[var4.length];

                            for(var8 = 0; var8 < var9.length; ++var8) {
                                var9[var8] = var2.getOriginalValue(var4[var8].getName());
                            }

                            Auditor.getAuditor("com.wentuotuo.wtt.jbo").audit(this.createAuditRecord("UPDATE", var2.getOriginKeyAttributes(), var4, var9));
                        }
                    }

                    var2.setState((byte)1);
                }
            }
        }
    }

    private int executeUpdate(String var1, DataElement[] var2) throws JBOException {
        boolean var3 = false;

        try {
            Connection var4 = this.getConnection();
            Query var5 = null;

            int var15;
            try {
                var5 = var4.createQuery(var1);
                this.setParameter(var5, var2);
                var15 = var5.executeUpdate();
            } catch (SQLException var12) {
                WTT.getLog().debug(var12);
                throw new JBOException(1322, new String[]{var1}, var12);
            } finally {
                if(var5 != null) {
                    var5.close();
                    var5 = null;
                }

                this.closeConnection(var4);
            }

            return var15;
        } catch (SQLException var14) {
            WTT.getLog().debug(var14);
            throw new JBOException(1323, var14);
        }
    }

    private String getInsertSql(DataElement[] var1) {
        StringBuffer var2 = new StringBuffer();
        StringBuffer var3 = new StringBuffer();

        for(int var4 = 0; var4 < var1.length; ++var4) {
            if(var4 > 0) {
                var2.append(", ");
                var3.append(",");
            }

            var2.append(this.getIdentifier(this.getColumnOfAttribute(var1[var4])));
            var3.append(":").append(var1[var4].getName());
        }

        String var5;
        if(this.getFilterColumn() != null) {
            var2.append(",").append(this.getIdentifier(this.getFilterColumn()));
            var5 = this.getFilterValue();
            if(var5 == null) {
                var5 = this.clazz.getAbsoluteName();
            }

            var3.append(",'").append(var5).append("'");
        }

        var5 = "INSERT INTO " + this.getIdentifier(this.getTable()) + "(" + var2.toString() + ") VALUES(" + var3.toString() + ")";
        if(this.isTraceQuery()) {
            WTT.getLog().debug("[JBO]{" + var5 + "}");
        }

        return var5;
    }

    private String getUpdateSql(DataElement[] var1, DataElement[] var2) {
        StringBuffer var3 = new StringBuffer();
        StringBuffer var4 = new StringBuffer();

        int var5;
        for(var5 = 0; var5 < var1.length; ++var5) {
            if(this.isUpdatable(var1[var5])) {
                var3.append(var5 == 0?" SET ":", ");
                var3.append(this.getIdentifier(this.getColumnOfAttribute(var1[var5])));
                var3.append("=:").append(var1[var5].getName());
            }
        }

        for(var5 = 0; var5 < var2.length; ++var5) {
            if(var5 > 0) {
                var4.append(" AND ");
            }

            var4.append(this.getIdentifier(this.getColumnOfAttribute(var2[var5])));
            var4.append("=:_KEY_").append(var2[var5].getName());
        }

        String var6 = "UPDATE " + this.getIdentifier(this.getTable()) + var3.toString() + " WHERE " + var4.toString();
        if(this.isTraceQuery()) {
            WTT.getLog().debug("[JBO]{" + var6 + "}");
        }

        return var6;
    }

    private String getDeleteSql(BizObjectKey var1) {
        DataElement[] var2 = var1.getAttributes();
        StringBuffer var3 = new StringBuffer();

        for(int var4 = 0; var4 < var2.length; ++var4) {
            if(var4 > 0) {
                var3.append(" AND ");
            }

            var3.append(this.getIdentifier(this.getColumnOfAttribute(var2[var4])));
            var3.append("=:").append(var2[var4].getName());
        }

        String var5 = "DELETE FROM " + this.getIdentifier(this.getTable()) + " WHERE " + var3.toString();
        if(this.isTraceQuery()) {
            WTT.getLog().debug("[JBO]" + var5);
        }

        return var5;
    }

    private String getKeyQuery(BizObjectKey var1) {
        DataElement[] var2 = var1.getAttributes();
        StringBuffer var3 = new StringBuffer("SELECT \"O.*\" FROM O WHERE ");

        for(int var4 = 0; var4 < var2.length; ++var4) {
            if(var4 > 0) {
                var3.append(" AND ");
            }

            var3.append(var2[var4].getName());
            var3.append("=:").append(var2[var4].getName());
        }

        return var3.toString();
    }

    protected void setParameter(Query var1, DataElement[] var2) throws JBOException {
        for(int var3 = 0; var3 < var2.length; ++var3) {
            try {
                var1.setParameter(var2[var3]);
            } catch (SQLException var5) {
                WTT.getLog().debug(var5);
                throw new JBOException(1324, new String[]{var2[var3].getName()}, var5);
            }
        }

    }

    private DataElement[] getUpdateAttributes(DataElement[] var1) {
        DataElement[] var2 = null;
        if(var1 != null && var1.length >= 1) {
            ArrayList var3 = new ArrayList();

            for(int var4 = 0; var4 < var1.length; ++var4) {
                if(this.isUpdatable(var1[var4])) {
                    var3.add(var1[var4]);
                }
            }

            if(var3.size() > 0) {
                var2 = (DataElement[])var3.toArray(new DataElement[0]);
            }

            return var2;
        } else {
            return var2;
        }
    }

    public String getColumnOfAttribute(DataElement var1) {
        return var1 == null?null:this.getColumnOfAttribute(var1.getName());
    }

    public boolean isUpdatable(String var1) {
        if(var1 == null) {
            return false;
        } else if(this.getFilterColumn() != null && this.getColumnOfAttribute(var1).equalsIgnoreCase(this.getFilterColumn())) {
            return false;
        } else {
            String var2 = var1.toUpperCase();
            if(!this.uaInited) {
                String[] var3;
                if(this.updateExcludes != null) {
                    if(this.updateExcludes.equals("*")) {
                        this.updateExclude = new ArrayList(0);
                    } else {
                        var3 = this.updateExcludes.split(",");
                        if(var3.length > 0) {
                            this.updateExclude = Arrays.asList(var3);
                        }
                    }
                }

                if(this.updateIncludes != null) {
                    if(this.updateIncludes.equals("*")) {
                        this.updateInclude = new ArrayList(0);
                    } else {
                        var3 = this.updateIncludes.split(",");
                        if(var3.length > 0) {
                            this.updateInclude = Arrays.asList(var3);
                        }
                    }
                }

                if(this.basicAttribute == null) {
                    DataElement[] var5 = this.clazz.getAttributes();
                    this.basicAttribute = new ArrayList(var5.length);

                    for(int var4 = 0; var4 < var5.length; ++var4) {
                        this.basicAttribute.add(var5[var4].getName().toUpperCase());
                    }
                }

                this.uaInited = true;
            }

            return !this.basicAttribute.contains(var2)?false:(this.updateExclude != null && (this.updateExclude.size() == 0 || this.updateExclude.contains(var2))?false:this.updateInclude == null || this.updateInclude.size() == 0 || this.updateInclude.contains(var2));
        }
    }

    public boolean isUpdatable(DataElement var1) {
        return var1 == null?false:this.isUpdatable(var1.getName());
    }

    public String getTable() {
        if(this.table == null) {
            this.setTable(this.clazz.getName());
        }

        return this.table;
    }

    public void setTable(String var1) {
        this.table = this.caseConvert(var1, this.getTableNameCase());
    }

    public void setAttributeMap(String var1) {
        if(var1 != null) {
            String var2 = var1.trim();
            if(var2.startsWith("{")) {
                String[] var3 = StringX.parseArray(var2);

                for(int var4 = 0; var4 < var3.length; ++var4) {
                    int var5 = var3[var4].indexOf(44);
                    if(var5 != -1) {
                        String var6 = StringX.trimAll(var3[var4].substring(0, var5)).toUpperCase();
                        String var7 = StringX.trimAll(var3[var4].substring(var5 + 1));
                        if(!var6.equals("") && !var7.equals("")) {
                            this.setAttributeMap(var6, var7);
                        }
                    }
                }
            }

        }
    }

    public void setAttributeMap(String var1, String var2) {
        if(!StringX.isSpace(var1) && !StringX.isSpace(var2)) {
            if(this.attributeMap == null) {
                this.attributeMap = new HashMap();
            }

            this.attributeMap.put(var1.toUpperCase(), var2);
        }
    }

    public String getColumnOfAttribute(String var1) {
        if(this.attributeMap == null) {
            return var1;
        } else {
            String var2 = (String)this.attributeMap.get(var1.toUpperCase());
            if(var2 == null) {
                var2 = var1;
            }

            return this.caseConvert(var2, this.getColumnNameCase());
        }
    }

    public String getFilterColumn() {
        return this.filterColumn;
    }

    public void setFilterColumn(String var1) {
        this.filterColumn = StringX.isSpace(var1)?null:var1;
    }

    public String getFilterValue() {
        return this.filterValue;
    }

    public void setFilterValue(String var1) {
        this.filterValue = StringX.isSpace(var1)?null:var1;
    }

    public void setFilter(String var1) {
        if(StringX.isSpace(var1)) {
            this.setFilterValue((String)null);
            this.setFilterColumn((String)null);
        } else {
            String[] var2 = var1.split(",");
            if(var2.length < 2) {
                this.setFilterColumn(var1);
                this.setFilterValue((String)null);
            } else {
                this.setFilterColumn(var2[0]);
                this.setFilterValue(var2[1]);
            }
        }

    }

    public String getUpdateInclues() {
        return this.updateIncludes;
    }

    public void setUpdateIncludes(String var1) {
        String var2 = var1;
        if(var1 != null) {
            var2 = var1.replaceAll("([^,]\\s+)|(\\s+[$,])", "");
        }

        if(var2.length() == 0) {
            var2 = null;
        }

        this.updateIncludes = var2;
    }

    public String getUpdateExcludes() {
        return this.updateExcludes;
    }

    public void setUpdateExcludes(String var1) {
        String var2 = var1;
        if(var1 != null) {
            var2 = var1.replaceAll("([^,]\\s+)|(\\s+[$,])", "");
        }

        if(var2.length() == 0) {
            var2 = null;
        }

        this.updateExcludes = var2;
    }

    public int getTableNameCase() {
        return this.tableNameCase;
    }

    public void setTableNameCase(int var1) {
        this.tableNameCase = (var1 & 3) % 3;
    }

    public int getColumnNameCase() {
        return this.columnNameCase;
    }

    public void setColumnNameCase(int var1) {
        this.columnNameCase = (var1 & 3) % 3;
    }

    public char getIdentifierQuoteCharacter() {
        return this.identifierQuoteCharacter;
    }

    public void setIdentifierQuoteCharacter(char var1) {
        this.identifierQuoteCharacter = var1;
    }

    protected AuditRecord createAuditRecord(String var1, DataElement[] var2, DataElement[] var3, Object[] var4) {
        AuditRecord var5 = new AuditRecord(this.getAppContext());
        var5.setType("JBO-MANAGE");
        var5.setAction(var1);
        var5.setTarget(this.getManagedClass().getAbsoluteName());
        var5.setData(this.createAuditData(var2, var3, var4));
        return var5;
    }

    protected AuditData createAuditData(DataElement[] var1, DataElement[] var2, Object[] var3) {
        BizObjectData var4 = new BizObjectData();
        var4.setKeyAttributes(var1);
        var4.setChangedAttributes(var2);
        var4.setOriginalValues(var3);
        return var4;
    }

    private String caseConvert(String var1, int var2) {
        return var2 != 0 && !StringX.isEmpty(var1)?(var2 == 1?var1.toUpperCase():var1.toLowerCase()):var1;
    }

    private String getIdentifier(String var1) {
        return this.identifierQuoteCharacter == 0?var1:this.identifierQuoteCharacter + var1 + this.identifierQuoteCharacter;
    }
}
