//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.StringX;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.regex.Pattern;

public class BufferedReaderResultSet implements ResultSet {
    private BufferedReaderResultSetMetaData metaData;
    private BufferedReader reader;
    private boolean closed = false;
    private DecimalFormat decimalFormat;
    private SimpleDateFormat dateFormat;
    private int currentRow = 0;
    private String[] row;
    private String[] rowBuffer;
    private boolean wasNull = false;
    private boolean eof = false;
    private int rowSize;
    private int columnCount;
    private String separator;
    private boolean fixLengthMode = false;
    private String encoding = null;
    private int[] indexMap;
    private int[] columnSize;
    private String[] columnFormat;
    private int[] columnPrecision;
    private int[] columnScale;
    private String[] columnNames;
    private int[] columnIndexes;
    private boolean findContextInited = false;
    private char[] specialChar = new char[]{'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', '}'};
    private Pattern pattern1 = Pattern.compile("([0-9]*)");
    private Pattern pattern2 = Pattern.compile("([0-9]*([}]||[J]||[K]||[L]||[M]||[N]||[O]||[P]||[Q]||[R]){1})$");

    public BufferedReaderResultSet(BufferedReader var1, BufferedReaderResultSetMetaData var2) throws SQLException {
        if(var1 == null) {
            throw new SQLException("Data reader is null.");
        } else if(var2 == null) {
            throw new SQLException("Data MetaData is null.");
        } else {
            this.reader = var1;
            this.metaData = var2;
            this.encoding = var2.getEncoding();
            this.currentRow = 0;
            this.eof = false;
            this.rowBuffer = null;
            this.separator = var2.getSeparator();
            if(StringX.isEmpty(this.separator)) {
                this.fixLengthMode = true;
            }

            this.columnCount = var2.getColumnCount();
            this.columnFormat = new String[this.columnCount];
            this.columnPrecision = new int[this.columnCount];
            this.columnScale = new int[this.columnCount];
            this.columnSize = new int[this.columnCount];
            this.indexMap = new int[this.columnCount];

            for(int var3 = 0; var3 < this.columnCount; ++var3) {
                this.columnSize[var3] = var2.getColumnDisplaySize(var3 + 1);
                this.rowSize += this.columnSize[var3];
                this.indexMap[var3] = var2.getColumnIndexInTable(var3 + 1) - 1;
                this.columnFormat[var3] = var2.getColumnFormat(var3 + 1);
                this.columnPrecision[var3] = var2.getPrecision(var3 + 1);
                this.columnScale[var3] = var2.getScale(var3 + 1);
            }

            this.dateFormat = new SimpleDateFormat();
            this.decimalFormat = new DecimalFormat();
            this.row = new String[this.columnCount];
        }
    }

    public boolean absolute(int var1) throws SQLException {
        this.notSupport("absolute");
        return false;
    }

    public void afterLast() throws SQLException {
        this.notSupport("afterLast");
    }

    public void beforeFirst() throws SQLException {
        this.notSupport("beforeFirst");
    }

    public void cancelRowUpdates() throws SQLException {
        this.notSupport("cancelRowUpdates");
    }

    public void clearWarnings() throws SQLException {
        this.notSupport("clearWarnings");
    }

    public void close() throws SQLException {
        try {
            this.reader.close();
            this.closed = true;
        } catch (IOException var2) {
            throw this.toSQLException("Close reader error.", var2);
        }
    }

    public void deleteRow() throws SQLException {
        this.notSupport("deleteRow");
    }

    public int findColumn(String var1) throws SQLException {
        if(!this.findContextInited) {
            this.initFindColumnContext();
        }

        int var2 = Arrays.binarySearch(this.columnNames, var1.toUpperCase());
        return var2 >= 0 && var2 <= this.columnCount?this.columnIndexes[var2] + 1:var2;
    }

    public boolean first() throws SQLException {
        this.notSupport("first");
        return false;
    }

    public Array getArray(int var1) throws SQLException {
        this.notSupport("getArray");
        return null;
    }

    public Array getArray(String var1) throws SQLException {
        this.notSupport("getArray");
        return null;
    }

    public InputStream getAsciiStream(int var1) throws SQLException {
        this.notSupport("getXXXStream");
        return null;
    }

    public InputStream getAsciiStream(String var1) throws SQLException {
        this.notSupport("XXXStream");
        return null;
    }

    public BigDecimal getBigDecimal(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        this.wasNull = false;
        BigDecimal var2 = null;
        if(this.row[var1].length() == 0) {
            this.wasNull = true;
        } else {
            try {
                var2 = new BigDecimal(this.row[var1]);
            } catch (Exception var4) {
                throw this.toSQLException("Read column error.", var4);
            }
        }

        return var2;
    }

    public BigDecimal getBigDecimal(int var1, int var2) throws SQLException {
        return new BigDecimal(new BigInteger(this.row[var1]), var2);
    }

    public BigDecimal getBigDecimal(String var1) throws SQLException {
        return this.getBigDecimal(this.findColumn(var1));
    }

    public BigDecimal getBigDecimal(String var1, int var2) throws SQLException {
        return this.getBigDecimal(this.findColumn(var1), var2);
    }

    public InputStream getBinaryStream(int var1) throws SQLException {
        this.notSupport("getXXXStream");
        return null;
    }

    public InputStream getBinaryStream(String var1) throws SQLException {
        this.notSupport("XXXStream");
        return null;
    }

    public Blob getBlob(int var1) throws SQLException {
        this.notSupport("getBlob");
        return null;
    }

    public Blob getBlob(String var1) throws SQLException {
        this.notSupport("getBlob");
        return null;
    }

    public boolean getBoolean(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        String var2 = this.row[var1];
        this.wasNull = false;
        if(var2.length() == 0) {
            this.wasNull = true;
            return false;
        } else {
            return var2.equalsIgnoreCase("true") || var2.equalsIgnoreCase("yes") || var2.equals("1");
        }
    }

    public boolean getBoolean(String var1) throws SQLException {
        return this.getBoolean(this.findColumn(var1));
    }

    public byte getByte(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        Number var2 = this.parseNumber(this.row[var1], this.columnFormat[var1], this.columnPrecision[var1], this.columnScale[var1]);
        this.wasNull = var2 == null;
        return this.wasNull?0:var2.byteValue();
    }

    public byte getByte(String var1) throws SQLException {
        return this.getByte(this.findColumn(var1));
    }

    public byte[] getBytes(int var1) throws SQLException {
        return this.row[var1].getBytes();
    }

    public byte[] getBytes(String var1) throws SQLException {
        return this.getBytes(this.findColumn(var1));
    }

    public Reader getCharacterStream(int var1) throws SQLException {
        this.notSupport("getXXXStream");
        return null;
    }

    public Reader getCharacterStream(String var1) throws SQLException {
        this.notSupport("XXXStream");
        return null;
    }

    public Clob getClob(int var1) throws SQLException {
        this.notSupport("getClob");
        return null;
    }

    public Clob getClob(String var1) throws SQLException {
        this.notSupport("getClob");
        return null;
    }

    public int getConcurrency() throws SQLException {
        return 1007;
    }

    public String getCursorName() throws SQLException {
        return "BufferredReaderResultSet";
    }

    public Date getDate(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        this.wasNull = false;
        if(this.row[var1].length() == 0) {
            this.wasNull = true;
            return null;
        } else {
            this.dateFormat.applyPattern(this.columnFormat[var1]);
            java.util.Date var2 = null;

            try {
                var2 = this.dateFormat.parse(this.row[var1]);
            } catch (ParseException var4) {
                throw this.toSQLException("Date format error.", var4);
            }

            return new Date(var2.getTime());
        }
    }

    public Date getDate(int var1, Calendar var2) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.getDate(var1);
    }

    public Date getDate(String var1) throws SQLException {
        return this.getDate(this.findColumn(var1));
    }

    public Date getDate(String var1, Calendar var2) throws SQLException {
        return this.getDate(this.findColumn(var1), var2);
    }

    public double getDouble(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        Number var2 = this.parseNumber(this.row[var1], this.columnFormat[var1], this.columnPrecision[var1], this.columnScale[var1]);
        this.wasNull = var2 == null;
        return this.wasNull?0.0D:var2.doubleValue();
    }

    public double getDouble(String var1) throws SQLException {
        return this.getDouble(this.findColumn(var1));
    }

    public int getFetchDirection() throws SQLException {
        return 1000;
    }

    public int getFetchSize() throws SQLException {
        return 1;
    }

    public float getFloat(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        Number var2 = this.parseNumber(this.row[var1], this.columnFormat[var1], this.columnPrecision[var1], this.columnScale[var1]);
        this.wasNull = var2 == null;
        return this.wasNull?0.0F:var2.floatValue();
    }

    public float getFloat(String var1) throws SQLException {
        return this.getFloat(this.findColumn(var1));
    }

    public int getInt(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        Number var2 = this.parseNumber(this.row[var1], this.columnFormat[var1], this.columnPrecision[var1], this.columnScale[var1]);
        this.wasNull = var2 == null;
        return this.wasNull?0:var2.intValue();
    }

    public int getInt(String var1) throws SQLException {
        return this.getInt(this.findColumn(var1));
    }

    public long getLong(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        Number var2 = this.parseNumber(this.row[var1], this.columnFormat[var1], this.columnPrecision[var1], this.columnScale[var1]);
        this.wasNull = var2 == null;
        return this.wasNull?0L:var2.longValue();
    }

    public long getLong(String var1) throws SQLException {
        return this.getLong(this.findColumn(var1));
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return this.metaData;
    }

    public Object getObject(int var1) throws SQLException {
        this.notSupport("getObject");
        return null;
    }

    public Object getObject(int var1, Map var2) throws SQLException {
        this.notSupport("getObject");
        return null;
    }

    public Object getObject(String var1) throws SQLException {
        this.notSupport("getObject");
        return null;
    }

    public Object getObject(String var1, Map var2) throws SQLException {
        this.notSupport("getObject");
        return null;
    }

    public Ref getRef(int var1) throws SQLException {
        this.notSupport("getRef");
        return null;
    }

    public Ref getRef(String var1) throws SQLException {
        this.notSupport("getRef");
        return null;
    }

    public int getRow() throws SQLException {
        return this.currentRow;
    }

    public short getShort(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        Number var2 = this.parseNumber(this.row[var1], this.columnFormat[var1], this.columnPrecision[var1], this.columnScale[var1]);
        this.wasNull = var2 == null;
        return this.wasNull?0:var2.shortValue();
    }

    public short getShort(String var1) throws SQLException {
        return this.getShort(this.findColumn(var1));
    }

    public Statement getStatement() throws SQLException {
        this.notSupport("getStatement");
        return null;
    }

    public String getString(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        this.wasNull = this.row[var1].length() == 0;
        return this.wasNull?null:this.row[var1];
    }

    public String getString(String var1) throws SQLException {
        return this.getString(this.findColumn(var1));
    }

    public Time getTime(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return new Time(this.getDate(var1).getTime());
    }

    public Time getTime(int var1, Calendar var2) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.getTime(var1);
    }

    public Time getTime(String var1) throws SQLException {
        return this.getTime(this.findColumn(var1));
    }

    public Time getTime(String var1, Calendar var2) throws SQLException {
        return this.getTime(this.findColumn(var1), var2);
    }

    public Timestamp getTimestamp(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return new Timestamp(this.getDate(var1).getTime());
    }

    public Timestamp getTimestamp(int var1, Calendar var2) throws SQLException {
        --var1;
        this.checkColumn(var1);
        return this.getTimestamp(var1);
    }

    public Timestamp getTimestamp(String var1) throws SQLException {
        return this.getTimestamp(this.findColumn(var1));
    }

    public Timestamp getTimestamp(String var1, Calendar var2) throws SQLException {
        return this.getTimestamp(this.findColumn(var1), var2);
    }

    public int getType() throws SQLException {
        return 1003;
    }

    public InputStream getUnicodeStream(int var1) throws SQLException {
        this.notSupport("getXXXStream");
        return null;
    }

    public InputStream getUnicodeStream(String var1) throws SQLException {
        this.notSupport("XXXStream");
        return null;
    }

    public URL getURL(int var1) throws SQLException {
        --var1;
        this.checkColumn(var1);
        this.wasNull = false;
        URL var2 = null;
        if(this.row[var1].length() == 0) {
            this.wasNull = true;
        } else {
            try {
                var2 = new URL(this.row[var1]);
            } catch (MalformedURLException var4) {
                throw this.toSQLException("URL format error.", var4);
            }
        }

        return var2;
    }

    public URL getURL(String var1) throws SQLException {
        return this.getURL(this.findColumn(var1));
    }

    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    public void insertRow() throws SQLException {
        this.notSupport("insertRow");
    }

    public boolean isAfterLast() throws SQLException {
        return this.eof;
    }

    public boolean isBeforeFirst() throws SQLException {
        return this.currentRow == 0;
    }

    public boolean isFirst() throws SQLException {
        return this.currentRow == 1;
    }

    public boolean isLast() throws SQLException {
        this.notSupport("isLast");
        return false;
    }

    public boolean last() throws SQLException {
        this.notSupport("last");
        return false;
    }

    public void moveToCurrentRow() throws SQLException {
        this.notSupport("moveToCurrentRow");
    }

    public void moveToInsertRow() throws SQLException {
        this.notSupport("moveToInsertRow");
    }

    public boolean next() throws SQLException {
        ++this.currentRow;

        String var1;
        try {
            var1 = this.reader.readLine();
        } catch (IOException var8) {
            throw this.toSQLException("Read line error. ", var8);
        }

        if(var1 == null) {
            this.eof = true;
            return false;
        } else if(var1.trim().equals("")) {
            return this.next();
        } else {
            int var2;
            if(!this.fixLengthMode) {
                this.rowBuffer = var1.split(this.separator, -1);

                try {
                    for(var2 = 0; var2 < this.columnCount; ++var2) {
                        this.row[var2] = this.rowBuffer[this.indexMap[var2]].trim();
                    }
                } catch (Exception var10) {
                    throw this.toSQLException("Split row error.", var10);
                }
            } else {
                var2 = 0;

                try {
                    Object var3 = null;

                    byte[] var11;
                    try {
                        var11 = StringX.isSpace(this.encoding)?var1.getBytes():var1.getBytes(this.encoding);
                    } catch (UnsupportedEncodingException var7) {
                        WTT.getLog().debug(var7);
                        var11 = var1.getBytes();
                    }

                    if(var11.length < this.rowSize) {
                        throw new SQLException(" row size error , expect " + this.rowSize + " but get " + var11.length + "!");
                    }

                    for(int var4 = 0; var4 < this.columnCount; ++var4) {
                        try {
                            this.row[var4] = StringX.isSpace(this.encoding)?new String(var11, var2, this.columnSize[var4]):new String(var11, var2, this.columnSize[var4], this.encoding);
                        } catch (UnsupportedEncodingException var6) {
                            WTT.getLog().debug(var6);
                            this.row[var4] = new String(var11, var2, this.columnSize[var4]);
                        }

                        var2 += this.columnSize[var4];
                    }
                } catch (Exception var9) {
                    throw this.toSQLException("Split row error", var9);
                }
            }

            return true;
        }
    }

    public boolean previous() throws SQLException {
        this.notSupport("previous");
        return false;
    }

    public void refreshRow() throws SQLException {
        this.notSupport("refreshRow");
    }

    public boolean relative(int var1) throws SQLException {
        this.notSupport("relative");
        return false;
    }

    public boolean rowDeleted() throws SQLException {
        this.notSupport("rowDeleted");
        return false;
    }

    public boolean rowInserted() throws SQLException {
        this.notSupport("rowInserted");
        return false;
    }

    public boolean rowUpdated() throws SQLException {
        this.notSupport("rowUpdated");
        return false;
    }

    public void setFetchDirection(int var1) throws SQLException {
        if(var1 != 1000) {
            this.notSupport("setFetchDirection");
        }

    }

    public void setFetchSize(int var1) throws SQLException {
        this.notSupport("setFetchSize");
    }

    public void updateArray(int var1, Array var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateArray(String var1, Array var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public RowId getRowId(int columnIndex) throws SQLException {
        return null;
    }

    public RowId getRowId(String columnLabel) throws SQLException {
        return null;
    }

    public void updateRowId(int columnIndex, RowId x) throws SQLException {

    }

    public void updateRowId(String columnLabel, RowId x) throws SQLException {

    }

    public void updateAsciiStream(int var1, InputStream var2, int var3) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateAsciiStream(String var1, InputStream var2, int var3) throws SQLException {
        this.notSupport("XXXStream");
    }

    public void updateBigDecimal(int var1, BigDecimal var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateBigDecimal(String var1, BigDecimal var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateBinaryStream(int var1, InputStream var2, int var3) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateBinaryStream(String var1, InputStream var2, int var3) throws SQLException {
        this.notSupport("XXXStream");
    }

    public void updateBlob(int var1, Blob var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateBlob(String var1, Blob var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateBoolean(int var1, boolean var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateBoolean(String var1, boolean var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateByte(int var1, byte var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateByte(String var1, byte var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateBytes(int var1, byte[] var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateBytes(String var1, byte[] var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateCharacterStream(int var1, Reader var2, int var3) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateCharacterStream(String var1, Reader var2, int var3) throws SQLException {
        this.notSupport("XXXStream");
    }

    public void updateClob(int var1, Clob var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateClob(String var1, Clob var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateDate(int var1, Date var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateDate(String var1, Date var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateDouble(int var1, double var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateDouble(String var1, double var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateFloat(int var1, float var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateFloat(String var1, float var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateInt(int var1, int var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateInt(String var1, int var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateLong(int var1, long var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateLong(String var1, long var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateNull(int var1) throws SQLException {
        this.notSupport("updateNull");
    }

    public void updateNull(String var1) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateObject(int var1, Object var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateObject(int var1, Object var2, int var3) throws SQLException {
        this.notSupport("updateObject");
    }

    public void updateObject(String var1, Object var2) throws SQLException {
        this.notSupport("updateObject");
    }

    public void updateObject(String var1, Object var2, int var3) throws SQLException {
        this.notSupport("updateObject");
    }

    public void updateRef(int var1, Ref var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateRef(String var1, Ref var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateRow() throws SQLException {
        this.notSupport("updateRow");
    }

    public void updateShort(int var1, short var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateShort(String var1, short var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateString(int var1, String var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateString(String var1, String var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateTime(int var1, Time var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateTime(String var1, Time var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateTimestamp(int var1, Timestamp var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public void updateTimestamp(String var1, Timestamp var2) throws SQLException {
        this.notSupport("updateXXX");
    }

    public boolean wasNull() throws SQLException {
        return this.wasNull;
    }

    private void checkColumn(int var1) throws SQLException {
        if(var1 < 0 || var1 >= this.columnCount) {
            throw new SQLException("BufferedResultSet: Column " + (var1 + 1) + " not found!");
        }
    }

    private void notSupport(String var1) throws SQLException {
        throw new SQLException("BufferedResultSet: Method '" + var1 + "' is not suppordded in this version!");
    }

    private SQLException toSQLException(String var1, Exception var2) {
        return new SQLException(var1 + " Row Number: " + this.currentRow + var2.toString());
    }

    private void initFindColumnContext() throws SQLException {
        this.columnNames = new String[this.columnCount];
        this.columnIndexes = new int[this.columnCount];

        for(int var1 = 0; var1 < this.columnCount; ++var1) {
            this.columnNames[var1] = this.metaData.getColumnName(var1 + 1).toUpperCase();
        }

        String[] var3 = (String[])this.columnNames.clone();
        Arrays.sort(this.columnNames);

        for(int var2 = 0; var2 < this.columnCount; this.columnIndexes[Arrays.binarySearch(this.columnNames, var3[var2])] = var2++) {
            ;
        }

        this.findContextInited = true;
    }

    private Number parseNumber(String var1, String var2, int var3, int var4) throws SQLException {
        if(var1 != null && var1.length() != 0) {
            Object var5 = null;

            try {
                if(var2 != null && var2.equalsIgnoreCase("AS400") && var1.getBytes().length < var3) {
                    return Double.valueOf(var1);
                } else {
                    if(var2 != null && var2.length() != 0) {
                        if(var2.equalsIgnoreCase("AS400")) {
                            return this.parseNumber(this.as400ToPc(var1), (String)null, var3 + 1, var4);
                        }

                        this.decimalFormat.applyPattern(var2);
                        var5 = this.decimalFormat.parse(var1);
                    } else if(var3 > 0 && var3 > var4) {
                        StringBuffer var6 = new StringBuffer(var1.length() > var3?var1.substring(0, var3):var1);
                        if(var4 > 0) {
                            if(var6.charAt(0) == 45) {
                                while(var6.length() < var4) {
                                    var6.insert(1, '0');
                                }

                                var6.insert(var6.length() - var4, '.');
                            } else {
                                while(var6.length() < var4) {
                                    var6.insert(0, '0');
                                }

                                var6.insert(var6.length() - var4, '.');
                            }
                        }

                        var5 = Double.valueOf(var6.toString());
                    } else {
                        var5 = Double.valueOf(var1);
                    }

                    return (Number)var5;
                }
            } catch (Exception var7) {
                throw this.toSQLException("Parse number error.value=" + var1, var7);
            }
        } else {
            return null;
        }
    }

    private String as400ToPc(String var1) throws SQLException {
        String var2 = "";
        if(this.pattern1.matcher(var1).matches()) {
            var2 = var1;
        } else {
            if(!this.pattern2.matcher(var1).matches()) {
                throw new SQLException("AS400数字格式有误！");
            }

            var2 = "-" + var1.substring(0, var1.length() - 1);
            char var3 = var1.charAt(var1.length() - 1);
            int var4 = (Arrays.binarySearch(this.specialChar, var3) + 1) % 10;
            var2 = var2 + var4;
        }

        return var2;
    }

    public int getHoldability() throws SQLException {
        this.notSupport("getHoldability");
        return 0;
    }

    public boolean isClosed() throws SQLException {
        return this.closed;
    }

    public void updateNString(int var1, String var2) throws SQLException {
        this.notSupport("XXXXXXXX");
    }

    public void updateNString(String var1, String var2) throws SQLException {
        this.notSupport("XXXXXXXX");
    }

    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {

    }

    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {

    }

    public NClob getNClob(int columnIndex) throws SQLException {
        return null;
    }

    public NClob getNClob(String columnLabel) throws SQLException {
        return null;
    }

    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return null;
    }

    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return null;
    }

    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {

    }

    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {

    }

    public String getNString(int var1) throws SQLException {
        this.notSupport("getNString");
        return null;
    }

    public String getNString(String var1) throws SQLException {
        this.notSupport("getNString");
        return null;
    }

    public Reader getNCharacterStream(int var1) throws SQLException {
        this.notSupport("getNCharacterStream");
        return null;
    }

    public Reader getNCharacterStream(String var1) throws SQLException {
        this.notSupport("getNCharacterStream");
        return null;
    }

    public void updateNCharacterStream(int var1, Reader var2, long var3) throws SQLException {
        this.notSupport("updateNCharacterStream");
    }

    public void updateNCharacterStream(String var1, Reader var2, long var3) throws SQLException {
        this.notSupport("updateNCharacterStream");
    }

    public void updateAsciiStream(int var1, InputStream var2, long var3) throws SQLException {
        this.notSupport("updateAsciiStream");
    }

    public void updateBinaryStream(int var1, InputStream var2, long var3) throws SQLException {
        this.notSupport("updateBinaryStream");
    }

    public void updateCharacterStream(int var1, Reader var2, long var3) throws SQLException {
        this.notSupport("updateCharacterStream");
    }

    public void updateAsciiStream(String var1, InputStream var2, long var3) throws SQLException {
        this.notSupport("updateAsciiStream");
    }

    public void updateBinaryStream(String var1, InputStream var2, long var3) throws SQLException {
        this.notSupport("updateBinaryStream");
    }

    public void updateCharacterStream(String var1, Reader var2, long var3) throws SQLException {
        this.notSupport("updateCharacterStream");
    }

    public void updateBlob(int var1, InputStream var2, long var3) throws SQLException {
        this.notSupport("updateBlob");
    }

    public void updateBlob(String var1, InputStream var2, long var3) throws SQLException {
        this.notSupport("updateBlob");
    }

    public void updateClob(int var1, Reader var2, long var3) throws SQLException {
        this.notSupport("updateClob");
    }

    public void updateClob(String var1, Reader var2, long var3) throws SQLException {
        this.notSupport("updateClob");
    }

    public void updateNClob(int var1, Reader var2, long var3) throws SQLException {
        this.notSupport("updateNClob");
    }

    public void updateNClob(String var1, Reader var2, long var3) throws SQLException {
        this.notSupport("updateNClob");
    }

    public void updateNCharacterStream(int var1, Reader var2) throws SQLException {
        this.notSupport("updateNCharacterStream");
    }

    public void updateNCharacterStream(String var1, Reader var2) throws SQLException {
        this.notSupport("updateNCharacterStream");
    }

    public void updateAsciiStream(int var1, InputStream var2) throws SQLException {
    }

    public void updateBinaryStream(int var1, InputStream var2) throws SQLException {
        this.notSupport("updateBinaryStream");
    }

    public void updateCharacterStream(int var1, Reader var2) throws SQLException {
        this.notSupport("updateCharacterStream");
    }

    public void updateAsciiStream(String var1, InputStream var2) throws SQLException {
        this.notSupport("updateAsciiStream");
    }

    public void updateBinaryStream(String var1, InputStream var2) throws SQLException {
        this.notSupport("updateBinaryStream");
    }

    public void updateCharacterStream(String var1, Reader var2) throws SQLException {
        this.notSupport("updateCharacterStream");
    }

    public void updateBlob(int var1, InputStream var2) throws SQLException {
        this.notSupport("updateBlob");
    }

    public void updateBlob(String var1, InputStream var2) throws SQLException {
        this.notSupport("updateBlob");
    }

    public void updateClob(int var1, Reader var2) throws SQLException {
        this.notSupport("updateClob");
    }

    public void updateClob(String var1, Reader var2) throws SQLException {
        this.notSupport("updateClob");
    }

    public void updateNClob(int var1, Reader var2) throws SQLException {
        this.notSupport("updateNClob");
    }

    public void updateNClob(String var1, Reader var2) throws SQLException {
        this.notSupport("updateNClob");
    }

    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return null;
    }

    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return null;
    }

    public Object unwrap(Class var1) throws SQLException {
        this.notSupport("unwrap");
        return null;
    }

    public boolean isWrapperFor(Class var1) throws SQLException {
        this.notSupport("isWrapperFor");
        return false;
    }
}
