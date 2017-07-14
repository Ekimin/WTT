//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.dpx.recordset.Field;
import com.wentuotuo.wtt.dpx.recordset.Record;
import com.wentuotuo.wtt.dpx.recordset.RecordSet;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.log.Log;
import com.wentuotuo.wtt.metadata.DefaultTableMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DefaultQuery implements Query {
    private int id = DBConnectionFactory.createUID();
    private int resultSetType = 1003;
    private int resultSetConcurrentcy = 1007;
    private Connection connection = null;
    private String originalSql = null;
    private String fineSql = null;
    private int sqlType = -1;
    private int fetchSize = 0;
    private int maxRows = 0;
    private Map parameters = new LinkedHashMap();
    private Map parameterReference = new TreeMap();
    private boolean closed;
    private PreparedStatement statement = null;
    private Log logger = WTT.getLog();
    private long queryBeginTime = 0L;
    private long queryEndTime = 0L;
    private static DecimalFormat leverFormat = new DecimalFormat("-000");
    private static int SQL_WARN = 100;

    protected DefaultQuery(Connection var1) {
        this.connection = var1;
    }

    protected DefaultQuery(Connection var1, int var2, int var3) {
        this.connection = var1;
        this.resultSetType = var2;
        this.resultSetConcurrentcy = var3;
    }

    public int getId() {
        return this.id;
    }

    public int getResultSetType() {
        return this.resultSetType;
    }

    public int getResultSetConcurrency() {
        return this.resultSetConcurrentcy;
    }

    public String getFineSql() {
        return this.fineSql;
    }

    public String getOriginalSql() {
        return this.originalSql;
    }

    public int getSqlType() {
        return this.sqlType;
    }

    public int getParameterNumber() {
        return this.parameters.size();
    }

    public String[] getParameters() {
        return (String[])this.parameters.keySet().toArray(new String[0]);
    }

    public void addParameter(String var1, int var2) {
        if(var1 != null && var2 >= 1) {
            String var3 = this.getPrettyName(var1);
            if(!this.parameters.containsKey(var3)) {
                this.parameters.put(var3, (Object)null);
            }

            Integer var4 = new Integer(var2);
            if(!this.parameterReference.containsKey(var4)) {
                this.parameterReference.put(var4, var3);
            }

        }
    }

    private String getPrettyName(String var1) {
        String var2 = null;
        if(var1 != null) {
            var2 = StringX.trimAll(var1).toUpperCase();
        }

        if(var2.startsWith("{$")) {
            var2 = var2.substring(2, var2.length() - 1);
        } else if(var2.startsWith(":")) {
            var2 = var2.substring(1);
        }

        return var2;
    }

    protected void setOriginalSql(String var1) {
        this.originalSql = var1;
    }

    protected void setFineSql(String var1) {
        this.fineSql = var1;
    }

    protected void setSqlType(int var1) {
        this.sqlType = var1;
    }

    public Query setParameter(DataElement var1) throws SQLException {
        String var2 = this.getPrettyName(var1.getName());
        if(var2 != null) {
            this.parameters.put(var2, var1);
        }

        return this;
    }

    public Query setParameter(String var1, int var2) throws SQLException {
        String var3 = this.getPrettyName(var1);
        if(var3 != null) {
            this.parameters.put(var3, DataElement.valueOf(var1, var2));
        }

        return this;
    }

    public Query setParameter(String var1, long var2) throws SQLException {
        String var4 = this.getPrettyName(var1);
        if(var4 != null) {
            this.parameters.put(var4, DataElement.valueOf(var1, var2));
        }

        return this;
    }

    public Query setParameter(String var1, double var2) throws SQLException {
        String var4 = this.getPrettyName(var1);
        if(var4 != null) {
            this.parameters.put(var4, DataElement.valueOf(var1, var2));
        }

        return this;
    }

    public Query setParameter(String var1, String var2) throws SQLException {
        String var3 = this.getPrettyName(var1);
        if(var3 != null) {
            this.parameters.put(var3, DataElement.valueOf(var1, var2));
        }

        return this;
    }

    public Query setParameter(String var1, Date var2) throws SQLException {
        String var3 = this.getPrettyName(var1);
        if(var3 != null) {
            this.parameters.put(var3, DataElement.valueOf(var1, var2));
        }

        return this;
    }

    public Query setParameter(String var1, boolean var2) throws SQLException {
        String var3 = this.getPrettyName(var1);
        if(var3 != null) {
            this.parameters.put(var3, DataElement.valueOf(var1, var2));
        }

        return this;
    }

    public int getFetcheSize() {
        return this.fetchSize;
    }

    public int getMaxRows() {
        return this.maxRows;
    }

    public Query setFetchSize(int var1) {
        this.fetchSize = var1;
        return this;
    }

    public Query setMaxRows(int var1) {
        this.maxRows = var1;
        return this;
    }

    public ResultSet getResultSet() throws SQLException {
        this.traceQueryStart();
        this.statement.setFetchSize(this.getFetcheSize());
        this.statement.setMaxRows(this.getMaxRows());
        ResultSet var1 = null;

        try {
            this.setParameter(this.statement);
            var1 = this.statement.executeQuery();
        } catch (SQLException var6) {
            throw var6;
        } finally {
            this.traceQueryEnd();
        }

        return var1;
    }

    public int executeUpdate() throws SQLException {
        boolean var1 = false;
        this.traceQueryStart();

        int var8;
        try {
            this.setParameter(this.statement);
            var8 = this.statement.executeUpdate();
        } catch (SQLException var6) {
            throw var6;
        } finally {
            this.traceQueryEnd();
        }

        return var8;
    }

    public List getRecordList() throws SQLException {
        ResultSet var1 = this.getResultSet();
        DefaultTableMetaData var2 = new DefaultTableMetaData(var1.getMetaData());
        RecordSet var3 = new RecordSet(var2);
        ArrayList var4 = new ArrayList();
        int[] var5 = new int[var2.getColumnCount() + 1];

        for(int var6 = 1; var6 < var5.length; ++var6) {
            var5[var6] = SQLConstants.SQLTypeToBaseType(var2.getColumn(var6).getType());
        }

        while(var1.next()) {
            Record var9 = var3.createRecord();
            Field[] var7 = var9.internalData();

            for(int var8 = 1; var8 < var7.length; ++var8) {
                switch(var5[var8]) {
                    case 0:
                        var7[var8].setValue(var1.getString(var8));
                        break;
                    case 1:
                        var7[var8].setValue(var1.getInt(var8));
                        break;
                    case 2:
                        var7[var8].setValue(var1.getLong(var8));
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
                        var7[var8].setValue(var1.getString(var8));
                        break;
                    case 4:
                        var7[var8].setValue(var1.getDouble(var8));
                        break;
                    case 8:
                        var7[var8].setValue(var1.getBoolean(var8));
                        break;
                    case 16:
                        var7[var8].setValue(var1.getTimestamp(var8));
                }

                if(var1.wasNull()) {
                    var7[var8].setNull();
                }
            }

            var4.add(var9);
        }

        return var4;
    }

    private void setParameter(PreparedStatement var1) throws SQLException {
        for(int var2 = 0; var2 < this.parameterReference.size(); ++var2) {
            String var3 = (String)this.parameterReference.get(new Integer(var2 + 1));
            DataElement var4 = (DataElement)this.parameters.get(var3);
            if(var4 == null) {
                throw new SQLException("Query Parameters not set." + var3);
            }

            switch(var4.getType()) {
                case 1:
                    if(var4.isNull()) {
                        var1.setNull(var2 + 1, 4);
                    } else {
                        var1.setInt(var2 + 1, var4.getInt());
                    }
                    break;
                case 2:
                    if(var4.isNull()) {
                        var1.setNull(var2 + 1, -5);
                    } else {
                        var1.setLong(var2 + 1, var4.getLong());
                    }
                    break;
                case 4:
                    if(var4.isNull()) {
                        var1.setNull(var2 + 1, 8);
                    } else {
                        var1.setDouble(var2 + 1, var4.getDouble());
                    }
                    break;
                case 8:
                    if(var4.isNull()) {
                        var1.setNull(var2 + 1, -7);
                    } else {
                        var1.setBoolean(var2 + 1, var4.getBoolean());
                    }
                    break;
                case 16:
                    Date var5 = var4.getDate();
                    if(!var4.isNull() && var5 != null) {
                        Timestamp var6 = new Timestamp(var5.getTime());
                        var1.setObject(var2 + 1, var6);
                        break;
                    }

                    var1.setNull(var2 + 1, 93);
                    break;
                default:
                    if(var4.isNull()) {
                        var1.setNull(var2 + 1, 12);
                    } else {
                        var1.setString(var2 + 1, var4.getString());
                    }
            }
        }

    }

    public void close() {
        if(this.statement != null) {
            try {
                this.statement.close();
            } catch (SQLException var2) {
                WTT.getLog("query").debug(var2);
            }

            this.statement = null;
            this.closed = true;
        }

    }

    public boolean isClosed() {
        return this.closed;
    }

    public Connection getConnection() {
        return this.connection;
    }

    protected void createStement(java.sql.Connection var1) throws SQLException {
        this.statement = var1.prepareStatement(this.getFineSql(), this.getResultSetType(), this.getResultSetConcurrency());
    }

    protected void traceQueryStart() {
        this.queryBeginTime = System.currentTimeMillis();
        if(WTT.getLog().isDebugEnabled()) {
            StringBuffer var1 = new StringBuffer("[SQL]");
            var1.append("{pid=").append(this.connection.getId()).append(",id=").append(this.getId()).append("}");
            var1.append("{beginTime=").append(this.queryBeginTime).append("}");
            var1.append("{").append(this.getOriginalSql()).append("}");
            var1.append("{").append(this.getFineSql()).append("}");
            if(this.parameterReference.size() > 0) {
                for(int var2 = 0; var2 < this.parameterReference.size(); ++var2) {
                    String var3 = (String)this.parameterReference.get(new Integer(var2 + 1));
                    DataElement var4 = (DataElement)this.parameters.get(var3);
                    var1.append(var2 == 0?"{":",").append(var3).append("=").append(var4 != null && !var4.isNull()?var4.getString():null);
                }

                var1.append("}");
            }

            WTT.getLog().debug(var1);
        }

    }

    protected void traceQueryEnd() {
        this.queryEndTime = System.currentTimeMillis();
        boolean var1 = true;
        int var3 = (int)(this.queryEndTime - this.queryBeginTime);
        int var2 = var3 / SQL_WARN;
        if(var2 > 0) {
            this.logger.warn(this.getLogString(var2, var3));
        } else if(this.logger.isDebugEnabled()) {
            this.logger.debug(this.getLogString(var2, var3));
        }

    }

    private String getLogString(int var1, int var2) {
        StringBuffer var3 = new StringBuffer("[SQL");
        if(var1 < 1) {
            var3.append("]");
        } else {
            var3.append(leverFormat.format((long)var1)).append("]");
        }

        var3.append("{pid=").append(this.connection.getId()).append(",id=").append(this.getId()).append("}");
        var3.append("{beginTime=").append(this.queryBeginTime).append(",endTime=").append(this.queryEndTime).append(",elapsedTime=").append((double)var2 / 1000.0D).append("}");
        var3.append("{").append(this.getOriginalSql()).append("}");
        var3.append("{").append(this.getFineSql()).append("}");
        if(this.parameterReference.size() > 0) {
            for(int var4 = 0; var4 < this.parameterReference.size(); ++var4) {
                String var5 = (String)this.parameterReference.get(new Integer(var4 + 1));
                DataElement var6 = (DataElement)this.parameters.get(var5);
                var3.append(var4 == 0?"{":",").append(var5).append("=").append(var6 != null && !var6.isNull()?var6.getString():null);
            }

            var3.append("}");
        }

        return var3.toString();
    }
}
