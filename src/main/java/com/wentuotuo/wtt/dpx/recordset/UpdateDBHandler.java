//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.log.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

public class UpdateDBHandler implements Handler {
    public static final int DB_RECORD_UNEXISTS = -1;
    public static final int DB_RECORD_SAME = 0;
    public static final int DB_RECORD_DIFFERENT = 1;
    private String database = null;
    private String table = null;
    private String keyColumns = null;
    private String insertColumns = null;
    private String updateColumns = null;
    private String compareColumns = null;
    private String columnMapping = null;
    private int commitNumber = 2000;
    private boolean checkDBRecord = true;
    private double numberMatchTolerance = 1.0E-6D;
    private boolean updateDifferent = true;
    private boolean insertUnexists = true;
    private boolean useTransaction = false;
    private String startSql = null;
    private String endSql = null;
    private String[] insertCols = null;
    private String[] updateCols = null;
    private String[] selectCols = null;
    private String[] compareCols = null;
    private SortedMap col2Field = null;
    private int[] selectParamFields = null;
    private int[] selectReturnFields = null;
    private int[] insertParamFields = null;
    private int[] updateParamFields = null;
    protected Connection connection = null;
    private PreparedStatement psInsert = null;
    private PreparedStatement psUpdate = null;
    private PreparedStatement psSelect = null;
    protected Statement statement = null;
    protected Record dbRecord = null;
    protected Record recordTemplet = null;
    private int insertNumber;
    private int updateNumber;
    protected Log logger = WTT.getLog();
    private String[] keyCol = null;

    public UpdateDBHandler() {
    }

    private String fieldsToString(RecordSet var1) {
        Record var2 = var1.getRecordTemplet();
        Field[] var3 = var2.getFields();
        StringBuffer var4 = new StringBuffer();

        for(int var5 = 0; var5 < var3.length; ++var5) {
            if(var5 > 0) {
                var4.append(",");
            }

            var4.append(var3[var5].getName());
        }

        return var4.toString();
    }

    public final void start(RecordSet var1) throws RecordSetException {
        this.recordTemplet = var1.getRecordTemplet();
        this.insertNumber = 0;
        this.updateNumber = 0;
        this.dbRecord = var1.createRecord();
        if(this.checkDBRecord && this.keyColumns == null) {
            throw new RecordSetException("If set checkDBRecord=true, must set key columns!");
        } else {
            if(this.insertColumns == null || this.insertColumns.equals("*")) {
                this.insertColumns = this.fieldsToString(var1);
            }

            if(this.updateColumns == null || this.updateColumns.equals("*")) {
                this.updateColumns = this.fieldsToString(var1);
            }

            if(this.compareColumns == null || this.compareColumns.equals("*")) {
                this.compareColumns = this.updateColumns;
            }

            try {
                this.connection = WTT.getDBConnection(this.database);
                if(this.useTransaction) {
                    this.connection.setAutoCommit(false);
                }
            } catch (SQLException var6) {
                if(this.connection != null) {
                    try {
                        this.connection.close();
                    } catch (SQLException var4) {
                        this.logger.debug(var4);
                    }

                    this.connection = null;
                }

                throw new RecordSetException(var6);
            }

            if(this.startSql != null) {
                try {
                    if(this.statement == null) {
                        this.statement = this.connection.createStatement();
                    }

                    String var2 = WTT.replaceWTTTags(this.startSql);
                    if(this.logger.isDebugEnabled()) {
                        this.logger.debug("Original StartSql: " + this.startSql);
                        this.logger.debug("Executed StartSql: " + var2);
                    }

                    this.statement.execute(var2);
                } catch (SQLException var5) {
                    this.finalProcess(false);
                    throw new RecordSetException("执行starSql语句出错", var5);
                }
            }

            this.dbRecord = var1.createRecord();
        }
    }

    private String getInsertSql() {
        this.insertCols = this.insertColumns.split(",");
        StringBuffer var1 = new StringBuffer("insert into ");
        var1.append(this.table);

        int var2;
        for(var2 = 0; var2 < this.insertCols.length; ++var2) {
            if(var2 == 0) {
                var1.append("(");
            } else {
                var1.append(",");
            }

            var1.append(this.insertCols[var2]);
        }

        var1.append(") values ");

        for(var2 = 0; var2 < this.insertCols.length; ++var2) {
            if(var2 == 0) {
                var1.append("(");
            } else {
                var1.append(",");
            }

            var1.append("?");
        }

        var1.append(")");
        return var1.toString();
    }

    private String getSelectSql() {
        String[] var1 = this.getKeyCols();
        String var2 = null;
        StringBuffer var3 = new StringBuffer();

        int var4;
        for(var4 = 0; var4 < var1.length; ++var4) {
            if(var4 == 0) {
                var3.append(" where ");
            } else {
                var3.append(" and ");
            }

            var3.append(var1[var4]).append("=?");
        }

        var2 = var3.toString();
        if(this.compareColumns == null) {
            this.selectCols = var1;
        } else {
            this.selectCols = this.compareColumns.split(",");
        }

        var3 = new StringBuffer();

        for(var4 = 0; var4 < var1.length; ++var4) {
            if(var4 == 0) {
                var3.append("select ");
            } else {
                var3.append(",");
            }

            var3.append(var1[var4]);
        }

        for(var4 = 0; var4 < this.selectCols.length; ++var4) {
            if(!this.isKeyColumn(this.selectCols[var4])) {
                var3.append(",").append(this.selectCols[var4]);
            }
        }

        var3.append(" from ").append(this.table).append(var2);
        return var3.toString();
    }

    private String[] getKeyCols() {
        if(this.keyCol == null) {
            this.keyCol = this.keyColumns.split(",");
        }

        return this.keyCol;
    }

    private boolean isKeyColumn(String var1) {
        boolean var2 = false;
        String[] var3 = this.getKeyCols();

        for(int var4 = 0; var4 < var3.length; ++var4) {
            if(var3[var4].equalsIgnoreCase(var1)) {
                var2 = true;
                break;
            }
        }

        return var2;
    }

    private String getUpdateSql() {
        this.updateCols = this.updateColumns.split(",");
        String var1 = null;
        StringBuffer var2 = new StringBuffer();
        String[] var3 = this.getKeyCols();

        int var4;
        for(var4 = 0; var4 < var3.length; ++var4) {
            if(var4 == 0) {
                var2.append(" where ");
            } else {
                var2.append(" and ");
            }

            var2.append(var3[var4]).append("=?");
        }

        var1 = var2.toString();
        this.updateCols = this.updateColumns.split(",");
        var2 = new StringBuffer("update ");
        var2.append(this.table);

        for(var4 = 0; var4 < this.updateCols.length; ++var4) {
            if(var4 == 0) {
                var2.append(" set ");
            } else {
                var2.append(",");
            }

            var2.append(this.updateCols[var4]).append("=? ");
        }

        var2.append(var1);
        return var2.toString();
    }

    public final void handleRecord(RecordSet var1, Record var2) throws RecordSetException {
        try {
            int var3 = this.checkRecord(var2);
            if(var3 == -1) {
                if(this.insertUnexists) {
                    this.insertRecord(var2);
                }
            } else if(var3 == 1 && this.updateDifferent) {
                this.updateRecord(var2);
            }

        } catch (SQLException var4) {
            this.logger.debug("批量更新/插入错误", var4);
            this.logger.debug("批量错误原始问题", var4.getNextException());
            this.finalProcess(false);
            throw new RecordSetException("记录处理出错", var4);
        }
    }

    public final void end(RecordSet var1) throws RecordSetException {
        if(this.psInsert != null) {
            try {
                this.psInsert.executeBatch();
            } catch (SQLException var5) {
                this.finalProcess(false);
                throw new RecordSetException("记录处理出错", var5);
            }
        }

        if(this.psUpdate != null) {
            try {
                this.psUpdate.executeBatch();
            } catch (SQLException var4) {
                this.finalProcess(false);
                throw new RecordSetException("记录处理出错", var4);
            }
        }

        if(this.endSql != null) {
            try {
                if(this.statement == null) {
                    this.statement = this.connection.createStatement();
                }

                String var2 = WTT.replaceWTTTags(this.endSql);
                if(this.logger.isDebugEnabled()) {
                    this.logger.debug("Original EndSql: " + this.endSql);
                    this.logger.debug("Executed EndSql: " + var2);
                }

                this.statement.execute(var2);
            } catch (SQLException var3) {
                this.finalProcess(false);
                throw new RecordSetException("执行后处理语句出错", var3);
            }
        }

        this.finalProcess(true);
    }

    protected boolean match(Record var1, Record var2) {
        if(this.compareColumns != null && !this.compareColumns.equalsIgnoreCase(this.keyColumns)) {
            int var4;
            if(this.compareCols == null) {
                String[] var3 = this.compareColumns.split(",");
                this.compareCols = new String[var3.length];

                for(var4 = 0; var4 < var3.length; ++var4) {
                    this.compareCols[var4] = this.columnToField(var3[var4]);
                }
            }

            boolean var7 = true;

            for(var4 = 0; var4 < this.compareCols.length; ++var4) {
                Field var5 = var2.getField(this.compareCols[var4]);
                Field var6 = var1.getField(this.compareCols[var4]);
                if(var6 != null && var5 != null && var6.compareTo(var5) != 0 && (var6.getType() != 4 || Math.abs(var6.getDouble() - var5.getDouble()) > this.getNumberMatchTolerance())) {
                    var7 = false;
                    break;
                }
            }

            return var7;
        } else {
            return false;
        }
    }

    protected int checkRecord(Record var1) throws RecordSetException {
        try {
            if(!this.readDBRecord(var1)) {
                return -1;
            }
        } catch (SQLException var3) {
            this.logger.debug(var3);
            throw new RecordSetException(var3);
        }

        return this.match(var1, this.dbRecord)?0:1;
    }

    private boolean readDBRecord(Record var1) throws SQLException {
        if(!this.checkDBRecord) {
            return false;
        } else {
            if(this.psSelect == null) {
                String var2 = this.getSelectSql();
                if(this.logger.isDebugEnabled()) {
                    this.logger.debug("SelectSql: " + var2);
                }

                this.psSelect = this.connection.prepareStatement(var2);
                String[] var3 = this.getKeyCols();
                this.selectParamFields = new int[var3.length + 1];

                for(int var4 = 0; var4 < var3.length; ++var4) {
                    this.selectParamFields[var4 + 1] = var1.indexOf(this.columnToField(var3[var4]));
                }
            }

            for(int var5 = 1; var5 < this.selectParamFields.length; ++var5) {
                Field var7 = var1.getField(this.selectParamFields[var5]);
                if(var7 != null) {
                    this.setParameter(this.psSelect, var5, var7);
                }
            }

            ResultSet var6 = this.psSelect.executeQuery();
            boolean var8 = var6.next();
            if(var8) {
                this.fillDBRecord(var6);
            }

            var6.close();
            return var8;
        }
    }

    private void fillDBRecord(ResultSet var1) throws SQLException {
        this.dbRecord.clear();
        if(this.selectReturnFields == null) {
            ResultSetMetaData var2 = var1.getMetaData();
            this.selectReturnFields = new int[var2.getColumnCount() + 1];

            for(int var3 = 1; var3 < this.selectReturnFields.length; ++var3) {
                this.selectReturnFields[var3] = this.dbRecord.indexOf(this.columnToField(var2.getColumnName(var3)));
            }
        }

        for(int var13 = 1; var13 < this.selectReturnFields.length; ++var13) {
            Field var14 = this.dbRecord.getField(this.selectReturnFields[var13]);
            if(var14 != null) {
                switch(var14.getType()) {
                    case 0:
                        String var4 = var1.getString(var13);
                        if(var1.wasNull()) {
                            var14.setNull();
                        } else {
                            var14.setValue(var4);
                        }
                        break;
                    case 1:
                        int var5 = var1.getInt(var13);
                        if(var1.wasNull()) {
                            var14.setNull();
                        } else {
                            var14.setValue(var5);
                        }
                        break;
                    case 2:
                        long var6 = var1.getLong(var13);
                        if(var1.wasNull()) {
                            var14.setNull();
                        } else {
                            var14.setValue(var6);
                        }
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
                        String var12 = var1.getString(var13);
                        if(var1.wasNull()) {
                            var14.setNull();
                        } else {
                            var14.setValue(var12);
                        }
                        break;
                    case 4:
                        double var8 = var1.getDouble(var13);
                        if(var1.wasNull()) {
                            var14.setNull();
                        } else {
                            var14.setValue(var8);
                        }
                        break;
                    case 8:
                        boolean var10 = var1.getBoolean(var13);
                        if(var1.wasNull()) {
                            var14.setNull();
                        } else {
                            var14.setValue(var10);
                        }
                        break;
                    case 16:
                        Date var11 = (Date)var1.getObject(var13);
                        if(var1.wasNull()) {
                            var14.setNull();
                        } else {
                            var14.setValue(var11);
                        }
                }
            }
        }

    }

    protected void insertRecord(Record var1) throws SQLException {
        if(this.psInsert == null) {
            String var2 = this.getInsertSql();
            if(this.logger.isDebugEnabled()) {
                this.logger.debug("InsertSql: " + var2);
            }

            this.psInsert = this.connection.prepareStatement(var2);
            this.insertParamFields = new int[this.insertCols.length + 1];

            for(int var3 = 0; var3 < this.insertCols.length; ++var3) {
                this.insertParamFields[var3 + 1] = var1.indexOf(this.columnToField(this.insertCols[var3]));
            }
        }

        for(int var4 = 1; var4 < this.insertParamFields.length; ++var4) {
            Field var5 = var1.getField(this.insertParamFields[var4]);
            this.setParameter(this.psInsert, var4, var5);
        }

        this.psInsert.addBatch();
        ++this.insertNumber;
        if(this.insertNumber >= this.commitNumber) {
            this.psInsert.executeBatch();
            this.insertNumber = 0;
        }

    }

    protected void updateRecord(Record var1) throws SQLException {
        if(this.psUpdate == null) {
            String var2 = this.getUpdateSql();
            if(this.logger.isDebugEnabled()) {
                this.logger.debug("UpdateSql: " + var2);
            }

            this.psUpdate = this.connection.prepareStatement(var2);
            String[] var3 = this.getKeyCols();
            this.updateParamFields = new int[this.updateCols.length + var3.length + 1];

            int var4;
            for(var4 = 0; var4 < this.updateCols.length; ++var4) {
                this.updateParamFields[var4 + 1] = var1.indexOf(this.columnToField(this.updateCols[var4]));
            }

            for(var4 = 0; var4 < var3.length; ++var4) {
                this.updateParamFields[var4 + this.updateCols.length + 1] = var1.indexOf(this.columnToField(var3[var4]));
            }
        }

        for(int var5 = 1; var5 < this.updateParamFields.length; ++var5) {
            Field var6 = var1.getField(this.updateParamFields[var5]);
            this.setParameter(this.psUpdate, var5, var6);
        }

        this.psUpdate.addBatch();
        ++this.updateNumber;
        if(this.updateNumber >= this.commitNumber) {
            this.psUpdate.executeBatch();
            this.updateNumber = 0;
        }

    }

    private void setParameter(PreparedStatement var1, int var2, Field var3) throws SQLException {
        if(var3 == null) {
            throw new SQLException("SQL的第" + var2 + "个参数在RecordSet中找不到对应的列!");
        } else {
            switch(var3.getType()) {
                case 1:
                    if(var3.isNull()) {
                        var1.setNull(var2, 4);
                    } else {
                        var1.setInt(var2, var3.getInt());
                    }
                    break;
                case 2:
                    if(var3.isNull()) {
                        var1.setNull(var2, -5);
                    } else {
                        var1.setLong(var2, var3.getLong());
                    }
                    break;
                case 4:
                    if(var3.isNull()) {
                        var1.setNull(var2, 8);
                    } else {
                        var1.setDouble(var2, var3.getDouble());
                    }
                    break;
                case 8:
                    if(var3.isNull()) {
                        var1.setNull(var2, -7);
                    } else {
                        var1.setBoolean(var2, var3.getBoolean());
                    }
                    break;
                case 16:
                    Date var4 = var3.getDate();
                    if(!var3.isNull() && var4 != null) {
                        Timestamp var5 = new Timestamp(var4.getTime());
                        var1.setObject(var2, var5);
                    } else {
                        var1.setNull(var2, 93);
                    }
                    break;
                default:
                    if(var3.isNull()) {
                        var1.setNull(var2, 12);
                    } else {
                        var1.setString(var2, var3.getString());
                    }
            }

        }
    }

    private void finalProcess(boolean var1) {
        if(this.useTransaction) {
            try {
                if(var1) {
                    this.connection.commit();
                } else {
                    this.connection.rollback();
                }
            } catch (SQLException var8) {
                this.logger.debug(var8);
            }
        }

        if(this.psSelect != null) {
            try {
                this.psSelect.close();
            } catch (SQLException var7) {
                this.logger.debug(var7);
            }

            this.psSelect = null;
        }

        if(this.psUpdate != null) {
            try {
                this.psUpdate.close();
            } catch (SQLException var6) {
                this.logger.debug(var6);
            }

            this.psUpdate = null;
        }

        if(this.psInsert != null) {
            try {
                this.psInsert.close();
            } catch (SQLException var5) {
                this.logger.debug(var5);
            }

            this.psInsert = null;
        }

        if(this.statement != null) {
            try {
                this.statement.close();
            } catch (SQLException var4) {
                this.logger.debug(var4);
            }

            this.statement = null;
        }

        if(this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException var3) {
                this.logger.debug(var3);
            }

            this.connection = null;
        }

    }

    public final boolean isCheckDBRecord() {
        return this.checkDBRecord;
    }

    public final void setCheckDBRecord(boolean var1) {
        this.checkDBRecord = var1;
    }

    public double getNumberMatchTolerance() {
        return this.numberMatchTolerance;
    }

    public void setNumberMatchTolerance(double var1) {
        this.numberMatchTolerance = var1;
    }

    public final int getCommitNumber() {
        return this.commitNumber;
    }

    public final void setCommitNumber(int var1) {
        this.commitNumber = var1;
    }

    public final String getDatabase() {
        return this.database;
    }

    public final void setDatabase(String var1) {
        this.database = var1;
    }

    public final String getInsertColumns() {
        return this.insertColumns;
    }

    public final void setInsertColumns(String var1) {
        this.insertColumns = this.prettySqlString(var1, true);
    }

    public final String getKeyColumns() {
        return this.keyColumns;
    }

    public final void setKeyColumns(String var1) {
        this.keyColumns = this.prettySqlString(var1, true);
    }

    public final String getUpdateColumns() {
        return this.updateColumns;
    }

    public final void setUpdateColumns(String var1) {
        this.updateColumns = this.prettySqlString(var1, true);
    }

    public final String getCompareColumns() {
        return this.compareColumns;
    }

    public final void setCompareColumns(String var1) {
        this.compareColumns = this.prettySqlString(var1, true);
    }

    public final boolean isUseTransaction() {
        return this.useTransaction;
    }

    public final void setUseTransaction(boolean var1) {
        this.useTransaction = var1;
    }

    public final String getTable() {
        return this.table;
    }

    public final void setTable(String var1) {
        this.table = this.prettySqlString(var1, true);
    }

    public final String getEndSql() {
        return this.endSql;
    }

    public final void setEndSql(String var1) {
        this.endSql = this.prettySqlString(var1, false);
    }

    public final String getStartSql() {
        return this.startSql;
    }

    public final void setStartSql(String var1) {
        this.startSql = this.prettySqlString(var1, false);
    }

    public final String getColumnMapping() {
        return this.columnMapping;
    }

    public void setColumnMapping(String var1, String var2) {
        if(this.col2Field == null) {
            this.col2Field = new TreeMap();
        }

        this.col2Field.put(var1.toUpperCase(), var2);
    }

    public String getColumnMapping(String var1) {
        return this.col2Field == null?null:(String)this.col2Field.get(var1.toUpperCase());
    }

    public final void setColumnMapping(String var1) {
        if(var1 != null) {
            String var2 = var1.trim();
            if(var2.startsWith("{")) {
                String[] var3 = StringX.parseArray(var2);

                for(int var4 = 0; var4 < var3.length; ++var4) {
                    int var5 = var3[var4].indexOf(44);
                    if(var5 != -1) {
                        String var6 = var3[var4].substring(0, var5).replaceAll("(?:^\\s+)|(?:\\s+$)", "");
                        String var7 = var3[var4].substring(var5 + 1).replaceAll("(?:^\\s+)|(?:\\s+$)", "");
                        if(!var6.equals("") && !var7.equals("")) {
                            this.setColumnMapping(var6, var7);
                        }
                    }
                }
            }

        }
    }

    public final boolean isInsertUnexists() {
        return this.insertUnexists;
    }

    public final void setInsertUnexists(boolean var1) {
        this.insertUnexists = var1;
    }

    public final boolean isUpdateDifferent() {
        return this.updateDifferent;
    }

    public final void setUpdateDifferent(boolean var1) {
        this.updateDifferent = var1;
    }

    protected String columnToField(String var1) {
        if(var1 == null) {
            return null;
        } else if(this.col2Field != null && this.col2Field.size() >= 1) {
            String var2 = (String)this.col2Field.get(var1);
            if(var2 == null) {
                var2 = var1;
            }

            return var2;
        } else {
            return var1;
        }
    }

    private String prettySqlString(String var1, boolean var2) {
        if(var1 == null) {
            return null;
        } else {
            String var3 = var1.replaceAll("[\\s]+", " ").replaceAll("[\\s]*,[\\s]*", ",").trim();
            if(var2) {
                var3 = var3.toUpperCase();
            }

            return var3 == ""?null:var3;
        }
    }
}
