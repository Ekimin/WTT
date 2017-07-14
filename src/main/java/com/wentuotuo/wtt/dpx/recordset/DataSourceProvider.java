//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.sql.DataSourceURI;
import com.wentuotuo.wtt.sql.TabularReader;
import java.net.URISyntaxException;
import java.sql.SQLException;

public abstract class DataSourceProvider implements DataProvider {
    private int fetchSize = 0;
    private int maxRows = 0;
    protected Record currentRecord;
    protected TabularReader sourceData;
    private DataSourceURI dataSource;

    public DataSourceProvider() {
    }

    public DataSourceProvider(DataSourceURI var1) {
        this.dataSource = var1;
    }

    protected abstract void fillRecord() throws SQLException;

    public void open(RecordSet var1) throws RecordSetException {
        this.sourceData = new TabularReader(this.getDataSource());
        if(this.getFetchSize() > 0) {
            this.sourceData.setFetchSize(this.getFetchSize());
        }

        if(this.getMaxRows() > 0) {
            this.sourceData.setMaxRows(this.getMaxRows());
        }

    }

    public boolean readRecord(Record var1) throws RecordSetException {
        try {
            if(this.sourceData.next()) {
                this.currentRecord = var1;
                this.fillRecord();
                return true;
            } else {
                return false;
            }
        } catch (SQLException var3) {
            this.sourceData.close();
            throw new RecordSetException("源数据读取错误！", var3);
        }
    }

    public void close() throws RecordSetException {
        this.sourceData.close();
    }

    public DataSourceURI getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSourceURI var1) {
        this.dataSource = var1;
    }

    public void setDataSource(String var1) {
        try {
            this.setDataSource(new DataSourceURI(var1));
        } catch (URISyntaxException var3) {
            WTT.getLog().debug(var3);
        }

    }

    public int getFetchSize() {
        return this.fetchSize;
    }

    public void setFetchSize(int var1) {
        this.fetchSize = var1;
    }

    public int getMaxRows() {
        return this.maxRows;
    }

    public void setMaxRows(int var1) {
        this.maxRows = var1;
    }
}
