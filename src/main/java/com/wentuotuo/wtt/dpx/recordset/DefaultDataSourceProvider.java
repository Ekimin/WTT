//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.dpx.dataconvert.DataConvertor;
import com.wentuotuo.wtt.dpx.dataconvert.DefaultConvertor;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.sql.DataSourceURI;
import com.wentuotuo.wtt.sql.TabularReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultDataSourceProvider extends DataSourceProvider {
    protected HashMap convertors = null;
    protected HashMap c2fMap = null;
    private int[] c2fIndex = null;
    private int[] colTypes = null;
    private DataConvertor[] colConvertors = null;
    private String sourceEncoding = null;
    private boolean encodingConvert = false;

    public DefaultDataSourceProvider() {
        this.convertors = new HashMap();
        this.c2fMap = new HashMap();
    }

    public DefaultDataSourceProvider(DataSourceURI var1) {
        super(var1);
        this.convertors = new HashMap();
        this.c2fMap = new HashMap();
    }

    public void open(RecordSet var1) throws RecordSetException {
        super.open(var1);

        try {
            ResultSetMetaData var2 = this.sourceData.getMetaData();
            Record var3 = var1.getRecordTemplet();
            int var4 = var2.getColumnCount();
            this.c2fIndex = new int[var4 + 1];
            this.colTypes = new int[var4 + 1];
            this.colConvertors = new DataConvertor[var4 + 1];

            for(int var5 = 1; var5 <= var4; ++var5) {
                String var6 = var2.getColumnName(var5);
                this.colTypes[var5] = var2.getColumnType(var5);
                this.colConvertors[var5] = this.getColumnConvertor(var6);
                String var7 = this.getColumnMapping(var6);
                this.c2fIndex[var5] = var3.indexOf(var7 == null?var6:var7);
            }

        } catch (SQLException var8) {
            throw new RecordSetException("获取数据源的元数据出错", var8);
        }
    }

    protected void fillRecord() throws SQLException {
        for(int var1 = 1; var1 < this.c2fIndex.length; ++var1) {
            if(this.c2fIndex[var1] >= 0) {
                this.fillField(var1, this.currentRecord.getField(this.c2fIndex[var1]));
            }
        }

    }

    private void fillField(int var1, Field var2) throws SQLException {
        Object var3 = this.colConvertors[var1];
        if(var3 == null) {
            var3 = var2;
        }

        String var4 = null;
        String var5 = null;
        switch(this.colTypes[var1]) {
            case -6:
            case 4:
            case 5:
                ((DataConvertor)var3).setValue(this.sourceData.getInt(var1));
                break;
            case -5:
                ((DataConvertor)var3).setValue(this.sourceData.getLong(var1));
                break;
            case 1:
            case 12:
                var4 = this.sourceData.getString(var1);
                if(var4 != null && this.encodingConvert) {
                    try {
                        var5 = new String(var4.getBytes(this.sourceEncoding));
                    } catch (UnsupportedEncodingException var8) {
                        WTT.getLog().debug("源数据转码错误！", var8);
                    }
                }

                ((DataConvertor)var3).setValue(var5 == null?var4:var5);
                break;
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
                ((DataConvertor)var3).setValue(this.sourceData.getDouble(var1));
                break;
            case 16:
                ((DataConvertor)var3).setValue(this.sourceData.getBoolean(var1));
                break;
            case 91:
                ((DataConvertor)var3).setValue(this.sourceData.getDate(var1));
                break;
            case 92:
                ((DataConvertor)var3).setValue(this.sourceData.getTime(var1));
                break;
            case 93:
                ((DataConvertor)var3).setValue(this.sourceData.getTimestamp(var1));
                break;
            default:
                var4 = this.sourceData.getString(var1);
                if(var4 != null && this.encodingConvert) {
                    try {
                        var5 = new String(var4.getBytes(this.sourceEncoding));
                    } catch (UnsupportedEncodingException var7) {
                        WTT.getLog().debug("源数据转码错误！", var7);
                    }
                }

                ((DataConvertor)var3).setValue(var5 == null?var4:var5);
        }

        if(this.sourceData.wasNull()) {
            var2.setNull();
        } else {
            if(var2 != var3) {
                this.setConvertedValue(var2, (DataConvertor)var3);
            }

        }
    }

    private void setConvertedValue(Field var1, DataConvertor var2) {
        switch(var1.getType()) {
            case 0:
                var1.setValue(var2.getString());
                break;
            case 1:
                var1.setValue(var2.getInt());
                break;
            case 2:
                var1.setValue(var2.getLong());
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
                var1.setValue(var2.getString());
                break;
            case 4:
                var1.setValue(var2.getDouble());
                break;
            case 8:
                var1.setValue(var2.getBoolean());
                break;
            case 16:
                var1.setValue(var2.getDate());
        }

    }

    public void setColumnConvertor(String var1, DataConvertor var2) {
        this.convertors.put(var1.toUpperCase(), var2);
    }

    public DataConvertor getColumnConvertor(String var1) {
        return (DataConvertor)this.convertors.get(var1.toUpperCase());
    }

    public void setColumnMapping(String var1, String var2) {
        this.c2fMap.put(var1.toUpperCase(), var2);
    }

    public String getColumnMapping(String var1) {
        return (String)this.c2fMap.get(var1.toUpperCase());
    }

    public void setColumnMapping(String var1) {
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

    public void setColumnConvertors(String var1) {
        if(var1 != null) {
            String[] var2 = StringX.parseArray(var1);
            Pattern var3 = Pattern.compile("\\{\\s*trim-space\\s*:.*\\}$");

            for(int var4 = 0; var4 < var2.length; ++var4) {
                String var5 = var2[var4];
                if(var5.length() >= 8) {
                    int var6 = var5.indexOf(44);
                    if(var6 > 1) {
                        String var7 = var5.substring(0, var6).trim();
                        String var8 = var5.substring(var6 + 1);
                        DefaultConvertor var9 = this.createDefaultConvertor(var8);
                        if(var9 != null) {
                            byte var10 = 3;
                            Matcher var11 = var3.matcher(var7);
                            if(var11.find()) {
                                String var12 = var11.group();
                                String var13 = StringX.trimAll(var12.substring(var12.indexOf(58) + 1, var12.length() - 1));
                                var7 = var7.substring(0, var11.start());
                                if(var13.equalsIgnoreCase("start")) {
                                    var10 = 1;
                                } else if(var13.equalsIgnoreCase("end")) {
                                    var10 = 2;
                                } else if(var13.equalsIgnoreCase("none")) {
                                    var10 = 0;
                                }
                            }

                            var9.setTrimSpace(var10);
                            this.setColumnConvertor(var7, var9);
                        }
                    }
                }
            }

        }
    }

    public void close() throws RecordSetException {
        super.close();
        Iterator var1 = this.convertors.entrySet().iterator();

        while(var1.hasNext()) {
            DataConvertor var2 = (DataConvertor)((Entry)var1.next()).getValue();
            var2.close();
        }

    }

    protected DefaultConvertor createDefaultConvertor(String var1) {
        DefaultConvertor var2 = null;
        if(var1 != null && !var1.matches("\\s*")) {
            String var3 = var1.replaceAll("^\\s*", "");
            if(var3.startsWith("datasource")) {
                TabularReader var4 = null;

                try {
                    var4 = new TabularReader(new DataSourceURI(var3));
                    Properties var5 = var4.getProperties(1, 2);
                    var2 = new DefaultConvertor(var5);
                } catch (URISyntaxException var14) {
                    WTT.getLog().debug("Initialize code table error,URI not avalibale", var14);
                } catch (SQLException var15) {
                    WTT.getLog().debug("Initialize code table error,database error.", var15);
                } finally {
                    if(var4 != null) {
                        var4.close();
                        var4 = null;
                    }

                }
            } else if(var3.startsWith("{")) {
                Properties var17 = new Properties();
                String var18 = null;
                String[] var6 = StringX.parseArray(var3);

                for(int var7 = 0; var7 < var6.length; ++var7) {
                    int var8 = var6[var7].indexOf(44);
                    if(var8 != -1) {
                        String var9 = var6[var7].substring(0, var8).replaceAll("(?:^\\s+)|(?:\\s+$)", "");
                        String var10 = var6[var7].substring(var8 + 1).replaceAll("(?:^\\s+)|(?:\\s+$)", "");
                        if(var9.equals("")) {
                            var18 = var10;
                        } else {
                            var17.setProperty(var9, var10);
                        }
                    }
                }

                if(var17.size() > 0) {
                    if(var18 == null) {
                        var2 = new DefaultConvertor(var17);
                    } else {
                        var2 = new DefaultConvertor(var17, var18);
                    }
                }
            }

            return var2;
        } else {
            return null;
        }
    }

    public String getSourceEncoding() {
        return this.sourceEncoding;
    }

    public void setSourceEncoding(String var1) {
        this.sourceEncoding = var1;
        if(var1 == null) {
            this.encodingConvert = false;
        }

        String var2 = "设置数据源的字符编码格式，格式必须认识。中国wertijseren12309&%(|{}\t\n";

        try {
            String var3 = new String(var2.getBytes(var1));
            if(var3.equals(var2)) {
                this.encodingConvert = false;
            } else {
                this.encodingConvert = true;
            }
        } catch (UnsupportedEncodingException var4) {
            this.encodingConvert = false;
            WTT.getLog().debug("数据源编码" + var1 + "无效！", var4);
        }

    }
}
