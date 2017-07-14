//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.impl;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.jbo.AbstractBizObjectQuery;
import com.wentuotuo.wtt.jbo.BizObject;
import com.wentuotuo.wtt.jbo.BizObjectClass;
import com.wentuotuo.wtt.jbo.BizObjectManager;
import com.wentuotuo.wtt.jbo.JBOException;
import com.wentuotuo.wtt.jbo.JBOFactory;
import com.wentuotuo.wtt.jbo.ql.Element;
import com.wentuotuo.wtt.jbo.ql.JBOAttribute;
import com.wentuotuo.wtt.jbo.ql.JBOClass;
import com.wentuotuo.wtt.jbo.ql.Parser;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.sql.Connection;
import com.wentuotuo.wtt.sql.Query;
import com.wentuotuo.wtt.sql.SQLConstants;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class BasicSQLQuery extends AbstractBizObjectQuery {
    private int queryType = -1;
    private String querySql = null;
    private Parser parser = null;
    private String sumarrySql = null;
    private char identifierQuoteCharacter = 0;
    private Element[] extendedSequence = null;
    private String[][] dbMapper = (String[][])null;
    private byte[] queryResultColType = null;
    private String[] queryResultColName = null;
    private int totalCount = -1;
    private Map sumResult = null;

    protected BasicSQLQuery(BizObjectManager var1, String var2) throws JBOException {
        super(var1, var2);
        this.parser = this.parseQuery();
        this.queryType = this.parser.getQueryType();
        this.extendedSequence = this.createExtendedSequence(this.parser.getElementSequence());
    }

    private Element[] createExtendedSequence(Element[] var1) throws JBOException {
        Element[] var2 = null;
        HashMap var3 = new HashMap(4);
        ArrayList var4 = new ArrayList();

        for(int var5 = 0; var5 < var1.length; ++var5) {
            if(var1[var5].getType() == 4) {
                BizObjectManager var6 = null;
                JBOClass var7 = (JBOClass)var1[var5];
                var6 = var7.getAlias().equals("O")?this.getBizObjectManager():JBOFactory.getFactory().getManager(var7.getOfficalExpression());
                if(!(var6 instanceof BizObjectTableMapper)) {
                    throw new JBOException(1331, new String[]{var6.getId()});
                }

                var3.put(var7.getAlias(), var6);
            }

            if(var1[var5].getType() == 5 && ((JBOAttribute)var1[var5]).isExpression()) {
                var4.add(new Integer(var5));
            }
        }

        int var8;
        if(var4.size() < 1) {
            var2 = var1;
        } else {
            ArrayList var11 = new ArrayList();
            List var13 = Arrays.asList(var1);
            int var15 = 0;

            for(var8 = 0; var8 < var4.size(); ++var8) {
                int var9 = ((Integer)var4.get(var8)).intValue();
                var11.addAll(var13.subList(var15, var9));
                JBOAttribute var10 = (JBOAttribute)var1[var9];
                var11.addAll(this.evalueAttributeExpression((BizObjectManager)var3.get(var10.getClassAlias()), var10));
                var15 = var9 + 1;
                if(var8 == var4.size() - 1) {
                    var11.addAll(var13.subList(var15, var13.size()));
                }
            }

            var2 = (Element[])var11.toArray(new Element[var11.size()]);
        }

        this.dbMapper = new String[var2.length][2];
        String var12 = "O";
        BizObjectTableMapper var14 = (BizObjectTableMapper)var3.get(var12);
        this.identifierQuoteCharacter = var14.getIdentifierQuoteCharacter();
        boolean var16 = var3.size() > 1;

        for(var8 = 0; var8 < var2.length; ++var8) {
            if(var2[var8].getType() == 5) {
                JBOAttribute var17 = (JBOAttribute)var2[var8];
                if(var16 && !var17.getClassAlias().equals(var12)) {
                    var12 = var17.getClassAlias();
                    var14 = (BizObjectTableMapper)var3.get(var12);
                }

                this.dbMapper[var8][0] = var17.getAttributeType() == 2?"V":var14.getTable();
                this.dbMapper[var8][1] = var17.getAttributeType() == 2?var17.getName():var14.getColumnOfAttribute(var17.getName());
            }

            if(var2[var8].getType() == 4) {
                JBOClass var18 = (JBOClass)var2[var8];
                if(var16 && !var18.getAlias().equals(var12)) {
                    var12 = var18.getAlias();
                    var14 = (BizObjectTableMapper)var3.get(var12);
                }

                this.dbMapper[var8][0] = var14.getTable();
            }
        }

        return var2;
    }

    private List evalueAttributeExpression(BizObjectManager var1, JBOAttribute var2) {
        ArrayList var3 = new ArrayList();
        String var4 = var2.getName();
        DataElement[] var5 = var1.getManagedClass().getAttributes();
        if(var4.startsWith("*")) {
            for(int var6 = 0; var6 < var5.length; ++var6) {
                JBOAttribute var7 = Parser.createJBOAttribute(var2.getClassAlias() + "." + var5[var6].getName());
                var7.setJboClass(var2.getJboClass());
                var7.setUsedIn(var2.getUsedIn());
                var3.add(var7);
                var3.add(Parser.createOperator(","));
            }
        } else {
            Pattern var9 = Pattern.compile(var4, 2);

            for(int var10 = 0; var10 < var5.length; ++var10) {
                if(var9.matcher(var5[var10].getName()).matches()) {
                    JBOAttribute var8 = Parser.createJBOAttribute(var2.getClassAlias() + "." + var5[var10].getName());
                    var8.setJboClass(var2.getJboClass());
                    var8.setUsedIn(var2.getUsedIn());
                    var3.add(var8);
                    var3.add(Parser.createOperator(","));
                }
            }
        }

        if(var3.size() > 0) {
            var3.remove(var3.size() - 1);
        }

        return var3;
    }

    public List getResultList(boolean var1) throws JBOException {
        ArrayList var2 = new ArrayList();
        int var3 = this.getMaxResults();
        if(var3 < 0) {
            var3 = 0;
        }

        int var4 = this.getFirstResult();
        int var5 = 0;

        try {
            Connection var6 = this.getConnection();
            Query var7 = null;

            try {
                var7 = this.createQuery(var6);
                this.sumarrySql = this.createSumarrySql(var7);
                var7.setFetchSize(var3);
                if(var3 > 0) {
                    var7.setMaxRows(var4 + var3);
                } else {
                    var7.setMaxRows(0);
                }

                ResultSet var8 = var7.getResultSet();
                this.setDynamicClass(var8.getMetaData());

                int var9;
                for(var9 = 0; var9 < var4 && var8.next(); ++var9) {
                    ;
                }

                var9 = this.getLargeResultLimit();
                int var10 = this.getLargeResultWarn();

                while(var8.next()) {
                    var2.add(this.wrapResultSet(var8));
                    ++var5;
                    if(var5 % var10 == 0) {
                        WTT.getLog().warn(WTTException.getWarnMessage(8, new String[]{String.valueOf(var5), this.getBizObjectClass().getAbsoluteName() + ": " + this.getStatement()}));
                    }

                    if(var5 > var9 || var3 > 0 && var5 >= var3) {
                        break;
                    }
                }

                var7.close();
            } catch (SQLException var16) {
                WTT.getLog().debug(var16.getMessage(), var16);
                throw new JBOException(1328, new String[]{this.querySql}, var16);
            } finally {
                if(var7 != null) {
                    var7.close();
                    var7 = null;
                }

                this.closeConnection(var6);
            }
        } catch (SQLException var18) {
            WTT.getLog().debug(var18);
            throw new JBOException(1329, var18);
        }

        if(var5 > this.getLargeResultLimit()) {
            var2.clear();
            Runtime.getRuntime().gc();
            throw new JBOException(1333, new String[]{String.valueOf(var5), this.getBizObjectClass().getAbsoluteName() + ": " + this.getStatement()});
        } else {
            return var2;
        }
    }

    public BizObject getSingleResult(boolean var1) throws JBOException {
        BizObject var2 = null;

        try {
            Connection var3 = this.getConnection();
            Query var4 = null;

            try {
                var4 = this.createQuery(var3);
                var4.setFetchSize(1);
                var4.setMaxRows(1);
                ResultSet var5 = var4.getResultSet();
                this.setDynamicClass(var5.getMetaData());
                if(var5.next()) {
                    var2 = this.wrapResultSet(var5);
                }

                var4.close();
            } catch (SQLException var11) {
                WTT.getLog().error(var11);
                throw new JBOException(1328, new String[]{this.querySql}, var11);
            } finally {
                if(var4 != null) {
                    var4.close();
                    var4 = null;
                }

                this.closeConnection(var3);
            }

            return var2;
        } catch (SQLException var13) {
            WTT.getLog().error(var13);
            throw new JBOException(1329, var13);
        }
    }

    public int executeUpdate() throws JBOException {
        boolean var1 = false;

        try {
            Connection var2 = this.getConnection();
            Query var3 = null;

            int var13;
            try {
                var3 = this.createQuery(var2);
                var13 = var3.executeUpdate();
            } catch (SQLException var10) {
                WTT.getLog().debug(var10);
                throw new JBOException(1328, new String[]{this.querySql}, var10);
            } finally {
                if(var3 != null) {
                    var3.close();
                    var3 = null;
                }

                this.closeConnection(var2);
            }

            return var13;
        } catch (SQLException var12) {
            WTT.getLog().debug(var12);
            throw new JBOException(1329, var12);
        }
    }

    protected final Query createQuery(Connection var1) throws SQLException, JBOException {
        String var2 = this.getQuerySql();
        if(var2 == null) {
            throw new SQLException("sql is null!");
        } else {
            Query var3 = var1.createQuery(var2);
            if(this.parameters != null) {
                for(int var4 = 0; var4 < this.parameters.size(); ++var4) {
                    var3.setParameter((DataElement)this.parameters.get(var4));
                }
            }

            return var3;
        }
    }

    private Connection getConnection() throws SQLException, JBOException {
        if(this.transaction == null) {
            Connection var1 = WTT.getDBConnection(this.getDatabase());
            var1.setAutoCommit(true);
            return var1;
        } else {
            return this.transaction.getConnection(this);
        }
    }

    private void closeConnection(Connection var1) {
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

    private void setDynamicClass(ResultSetMetaData var1) throws SQLException {
        if(this.queryResultColType == null) {
            this.queryResultColType = new byte[var1.getColumnCount()];
            this.queryResultColName = new String[var1.getColumnCount()];
            ArrayList var2 = new ArrayList();

            for(int var3 = 0; var3 < this.queryResultColType.length; ++var3) {
                this.queryResultColType[var3] = SQLConstants.SQLTypeToBaseType(var1.getColumnType(var3 + 1));
                this.queryResultColName[var3] = var1.getColumnLabel(var3 + 1);
                if(this.queryResultColName[var3] == null || this.queryResultColName[var3].equals("")) {
                    this.queryResultColName[var3] = var1.getColumnName(var3 + 1);
                }

                if(this.bizObjectClass.getAttribute(this.queryResultColName[var3]) == null) {
                    DataElement var4 = new DataElement(this.queryResultColName[var3], this.queryResultColType[var3]);
                    var4.setLabel(this.getAttributeLabel(this.queryResultColName[var3]));
                    var2.add(var4);
                }
            }

            if(var2.size() > 0) {
                BizObjectClass var5 = this.bizObjectClass.extend((DataElement[])var2.toArray(new DataElement[0]));
                this.bizObjectClass = var5;
            }
        }

    }

    private String getAttributeLabel(String var1) {
        String var2 = null;
        Element[] var3 = this.parser.getElementSequence();

        for(int var4 = 0; var4 < var3.length; ++var4) {
            if(var3[var4].getType() == 5) {
                JBOAttribute var5 = (JBOAttribute)var3[var4];
                if(var5.getName().equalsIgnoreCase(var1)) {
                    var2 = var5.getLabel();
                }
            }
        }

        return var2 == null?var1:var2;
    }

    protected BizObject wrapResultSet(ResultSet var1) throws SQLException {
        BizObject var2 = new BizObject(this.bizObjectClass);

        for(int var3 = 0; var3 < this.queryResultColName.length; ++var3) {
            DataElement var4 = null;

            try {
                var4 = var2.getAttribute(this.queryResultColName[var3]);
            } catch (JBOException var6) {
                continue;
            }

            switch(this.queryResultColType[var3]) {
                case 0:
                    var4.setValue(var1.getString(var3 + 1));
                    break;
                case 1:
                    var4.setValue(var1.getInt(var3 + 1));
                    break;
                case 2:
                    var4.setValue(var1.getLong(var3 + 1));
                    break;
                case 3:
                case 5:
                case 6:
                case 7:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                default:
                    var4.setValue(var1.getString(var3 + 1));
                    break;
                case 4:
                    var4.setValue(var1.getDouble(var3 + 1));
                    break;
                case 8:
                    var4.setValue(var1.getBoolean(var3 + 1));
                    break;
                case 16:
                    var4.setValue(var1.getTimestamp(var3 + 1));
            }

            if(var1.wasNull()) {
                var4.setNull();
            }
        }

        return var2;
    }

    public int getQueryType() {
        return this.queryType;
    }

    protected String getQuerySql() throws JBOException {
        if(this.querySql == null) {
            switch(this.queryType) {
                case 1:
                    this.querySql = this.createQuerySql(this.extendedSequence);
                    break;
                case 2:
                    this.querySql = this.createUnionSql(this.extendedSequence);
                    break;
                case 3:
                case 4:
                    this.querySql = this.createUpdateSql(this.extendedSequence);
            }

            if(this.isTraceQuery() && WTT.getLog().isDebugEnabled()) {
                StringBuffer var1 = new StringBuffer("[JBO]");
                var1.append("{").append(this.getStatement()).append("}").append("{").append(this.querySql).append("}");
                WTT.getLog().debug(var1.toString());
            }
        }

        return this.querySql;
    }

    private String getIdentifier(String var1) {
        return this.identifierQuoteCharacter == 0?var1:this.identifierQuoteCharacter + var1 + this.identifierQuoteCharacter;
    }

    private String createQuerySql(Element[] var1) throws JBOException {
        StringBuffer var2 = new StringBuffer();
        String var3 = this.getTableFilter(true);

        for(int var4 = 0; var4 < var1.length; ++var4) {
            int var5 = var1[var4].getType();
            switch(var5) {
                case 0:
                    if(var2.length() > 0 && var2.charAt(var2.length() - 1) != 32) {
                        var2.append(" ");
                    }

                    String var6 = var1[var4].getOfficalExpression();
                    if(var3 != null) {
                        if(var6.equals("WHERE")) {
                            var2.append(var6).append(" (").append(var3).append(") AND ");
                            var3 = null;
                            break;
                        }

                        if(var6.equals("GROUP") || var6.equals("ORDER")) {
                            var2.append("WHERE ").append(var3).append(" ").append(var6);
                            var3 = null;
                            break;
                        }
                    }

                    var2.append(var6).append(" ");
                    break;
                case 1:
                case 2:
                case 3:
                default:
                    var2.append(var1[var4].getOfficalExpression());
                    break;
                case 4:
                    var2.append(this.getIdentifier(this.dbMapper[var4][0])).append(" ").append(((JBOClass)var1[var4]).getAlias());
                    break;
                case 5:
                    JBOAttribute var7 = (JBOAttribute)var1[var4];
                    String var8 = this.dbMapper[var4][1];
                    if(var7.getAttributeType() == 2) {
                        var2.append(var8);
                    } else {
                        if(var8.startsWith("[") && var8.endsWith("]")) {
                            var2.append(var8.substring(1, var8.length() - 1));
                        } else {
                            var2.append(var7.getClassAlias()).append(".").append(this.getIdentifier(var8));
                        }

                        if(var7.getUsedIn() == 0 && !var8.equalsIgnoreCase(var7.getName())) {
                            var2.append(" AS ").append(var7.getName());
                        }
                    }
                    break;
                case 6:
                    var2.append(var1[var4].getExpression());
            }
        }

        if(var3 != null) {
            var2.append(" WHERE ").append(var3);
        }

        return var2.toString();
    }

    private String createUpdateSql(Element[] var1) throws JBOException {
        StringBuffer var2 = new StringBuffer();
        String var3 = this.getTableFilter(false);

        for(int var4 = 0; var4 < var1.length; ++var4) {
            int var5 = var1[var4].getType();
            switch(var5) {
                case 0:
                    if(var2.length() > 0 && var2.charAt(var2.length() - 1) != 32) {
                        var2.append(" ");
                    }

                    String var6 = var1[var4].getOfficalExpression();
                    var2.append(var6).append(" ");
                    if(var3 != null && var6.equals("WHERE")) {
                        var2.append(" (").append(var3).append(") AND ");
                        var3 = null;
                    }
                    break;
                case 1:
                case 2:
                case 3:
                default:
                    var2.append(var1[var4].getOfficalExpression());
                    break;
                case 4:
                    JBOClass var7 = (JBOClass)var1[var4];
                    var2.append(this.getIdentifier(this.dbMapper[var4][0]));
                    if(!var7.isMajorClass()) {
                        var2.append(" ").append(var7.getAlias());
                    }
                    break;
                case 5:
                    JBOAttribute var8 = (JBOAttribute)var1[var4];
                    JBOClass var9 = var8.getJboClass();
                    if(var9 != null && var9.isMajorClass()) {
                        var2.append(this.getIdentifier(this.dbMapper[var4][0]));
                    } else {
                        var2.append(var8.getClassAlias());
                    }

                    var2.append('.').append(this.getIdentifier(this.dbMapper[var4][1]));
                    break;
                case 6:
                    var2.append(var1[var4].getExpression());
            }
        }

        if(var3 != null) {
            var2.append(" WHERE ").append(var3);
        }

        return var2.toString();
    }

    private String createUnionSql(Element[] var1) throws JBOException {
        StringBuffer var2 = new StringBuffer();
        ArrayList var3 = new ArrayList();
        int var4 = 0;

        for(int var5 = 0; var5 < var1.length; ++var5) {
            if(var1[var5].getType() == 0 && var1[var5].getOfficalExpression().equals("UNION")) {
                var3.add(new int[]{var4, var5});
                var4 = var5 + 1;
            }
        }

        ArrayList var10 = new ArrayList();
        int var6 = ((int[])var3.get(0))[1];

        for(int var7 = 0; var7 < var6 && (var1[var7].getType() != 0 || !var1[var7].getOfficalExpression().equals("FROM")); ++var7) {
            if(var1[var7].getType() == 5 && ((JBOAttribute)var1[var7]).getAttributeType() != 2) {
                var10.add(((JBOAttribute)var1[var7]).getName());
            }
        }

        String[] var11 = (String[])var10.toArray(new String[0]);

        for(int var8 = 0; var8 < var3.size(); ++var8) {
            if(var2.length() > 0) {
                var2.append(" UNION ");
            }

            int[] var9 = (int[])var3.get(var8);
            var2.append(this.createSubQuery(var1, var9[0], var9[1], var11));
        }

        return var2.toString();
    }

    private String createSubQuery(Element[] var1, int var2, int var3, String[] var4) throws JBOException {
        StringBuffer var5 = new StringBuffer();
        int var6 = 0;
        String var7 = var2 == 0?null:this.getTableFilter(true);

        for(int var8 = 0; var8 < var1.length; ++var8) {
            int var9 = var1[var8].getType();
            switch(var9) {
                case 0:
                    if(var5.length() > 0 && var5.charAt(var5.length() - 1) != 32) {
                        var5.append(" ");
                    }

                    String var10 = var1[var8].getOfficalExpression();
                    if(var7 != null) {
                        if(var10.equals("WHERE")) {
                            var5.append(var10).append(" (").append(var7).append(") AND ");
                            var7 = null;
                            break;
                        }

                        if(var10.equals("GROUP") || var10.equals("ORDER")) {
                            var5.append("WHERE ").append(var7).append(" ").append(var10);
                            var7 = null;
                            break;
                        }
                    }

                    var5.append(var10).append(" ");
                    break;
                case 1:
                case 2:
                case 3:
                default:
                    var5.append(var1[var8].getOfficalExpression());
                    break;
                case 4:
                    var5.append(this.getIdentifier(this.dbMapper[var8][0])).append(" ").append(((JBOClass)var1[var8]).getAlias());
                    break;
                case 5:
                    JBOAttribute var11 = (JBOAttribute)var1[var8];
                    String var12 = this.dbMapper[var8][1];
                    var5.append(var11.getClassAlias()).append(".").append(this.getIdentifier(var12));
                    if(var11.getUsedIn() == 0) {
                        String var13 = null;
                        if(var6 >= var4.length) {
                            var13 = var11.getName();
                        } else {
                            var13 = var4[var6];
                        }

                        var5.append(" AS ").append(var13);
                        ++var6;
                    }
                    break;
                case 6:
                    var5.append(var1[var8].getExpression());
            }
        }

        if(var7 != null) {
            var5.append(" WHERE ").append(var7);
        }

        return var5.toString();
    }

    private String getTableFilter(boolean var1) throws JBOException {
        String var2 = null;

        try {
            BizObjectTableMapper var3 = (BizObjectTableMapper)this.getBizObjectManager();
            String var4 = var3.getFilterColumn();
            if(var4 != null) {
                var2 = (var1?"O.":"") + var3.getFilterColumn() + "='" + var3.getFilterValue() + "'";
            }

            return var2;
        } catch (ClassCastException var5) {
            throw new JBOException(1331, new String[]{this.getBizObjectManager().getId()});
        }
    }

    public int getTotalCount() throws JBOException {
        if(this.getQueryType() != 4 && this.getQueryType() != 3) {
            if(this.totalCount < 0) {
                this.sumResult = new HashMap();

                try {
                    Connection var1 = this.getConnection();
                    Query var2 = null;

                    try {
                        if(this.sumarrySql == null) {
                            this.sumarrySql = this.createSumarrySql(this.createQuery(var1));
                        }

                        if(this.sumarrySql == null) {
                            throw new SQLException("sql is null!");
                        }

                        var2 = var1.createQuery(this.sumarrySql);
                        if(this.parameters != null) {
                            for(int var3 = 0; var3 < this.parameters.size(); ++var3) {
                                var2.setParameter((DataElement)this.parameters.get(var3));
                            }
                        }

                        ResultSet var14 = var2.getResultSet();
                        var14.next();
                        this.totalCount = var14.getInt(1);
                        ResultSetMetaData var4 = var14.getMetaData();

                        for(int var5 = 2; var5 <= var4.getColumnCount(); ++var5) {
                            this.sumResult.put(var4.getColumnLabel(var5).toUpperCase(), new Double(var14.getDouble(var5)));
                        }
                    } catch (SQLException var11) {
                        WTT.getLog().debug(var11.getMessage(), var11);
                        throw new JBOException(1330, new String[]{this.sumarrySql}, var11);
                    } finally {
                        if(var2 != null) {
                            var2.close();
                            var2 = null;
                        }

                        this.closeConnection(var1);
                    }
                } catch (SQLException var13) {
                    WTT.getLog().debug(var13.getMessage(), var13);
                    throw new JBOException(1329, var13);
                }
            }

            return this.totalCount;
        } else {
            return 0;
        }
    }

    public double getSumOf(String var1) throws JBOException {
        if(this.getQueryType() != 4 && this.getQueryType() != 3) {
            if(StringX.isSpace(var1)) {
                return 0.0D;
            } else if(this.getTotalCount() <= 0) {
                return 0.0D;
            } else {
                Double var2 = (Double)this.sumResult.get(this.sumName(var1));
                return var2 == null?0.0D:var2.doubleValue();
            }
        } else {
            return 0.0D;
        }
    }

    private String sumName(String var1) {
        return "S_" + var1.toUpperCase();
    }

    private String createSumarrySql(Query var1) throws JBOException {
        String var2 = null;
        StringBuffer var3 = new StringBuffer("SELECT COUNT(*) AS TOTAL_COUNT");
        Element[] var4 = this.parser.getElementSequence();

        for(int var5 = 0; var5 < var4.length; ++var5) {
            if(var4[var5].getType() == 5) {
                JBOAttribute var6 = (JBOAttribute)var4[var5];
                if(var6.getAttributeType() == 0 && var6.getUsedIn() == 0 && this.isNumberAttribute(var6)) {
                    var3.append(",").append("SUM(").append(this.getIdentifier(var6.getName())).append(") AS ").append(this.sumName(var6.getName()));
                }
            }
        }

        var3.append(" FROM (").append(var1.getOriginalSql()).append(") JBO_SUMMARY__TABLE");
        var2 = var3.toString();
        return var2;
    }

    private boolean isNumberAttribute(JBOAttribute var1) {
        DataElement var2 = this.bizObjectClass.getAttribute(var1.getName());
        if(var2 == null) {
            return false;
        } else {
            byte var3 = var2.getType();
            return var3 == 4 || var3 == 1 || var3 == 2;
        }
    }

    public List getResultList() throws JBOException {
        return this.getResultList(false);
    }

    public BizObject getSingleResult() throws JBOException {
        return this.getSingleResult(false);
    }
}
