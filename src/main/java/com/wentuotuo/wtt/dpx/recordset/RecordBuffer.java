//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.metadata.TableMetaData;
import com.wentuotuo.wtt.sql.DataSourceURI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Vector;

public class RecordBuffer implements Handler, DataProvider {
    private int maxRows = 2147483647;
    private TableMetaData recordSetMetaData = null;
    private DataProvider provider = null;
    private Record recordTemplete = null;
    private Vector records = new Vector();
    private Iterator rs = null;
    private int fetchPosition = 0;

    public RecordBuffer() {
        this.records = new Vector();
    }

    public void start(RecordSet var1) throws RecordSetException {
        this.records.clear();
        this.recordTemplete = var1.createRecord();
    }

    public void handleRecord(RecordSet var1, Record var2) throws RecordSetException {
        Record var3;
        try {
            var3 = (Record)var2.clone();
        } catch (CloneNotSupportedException var5) {
            WTT.getLog().debug(var5);
            throw new RecordSetException("Handler Record Error.", var5);
        }

        this.records.add(var3);
    }

    public void end(RecordSet var1) throws RecordSetException {
    }

    public Vector getRecords() {
        return this.records;
    }

    public int getRecordNumber() {
        return this.records.size();
    }

    public Record getFirstRecord() {
        Record var1 = null;
        if(this.records.size() > 0) {
            var1 = (Record)this.records.get(0);
        }

        return var1;
    }

    public Record getLastRecord() {
        Record var1 = null;
        if(this.records.size() > 0) {
            var1 = (Record)this.records.get(this.records.size() - 1);
        }

        return var1;
    }

    public Record getRecord(int var1) {
        return !this.isAvalibleRecord(var1)?null:(Record)this.records.get(var1);
    }

    public void open(RecordSet var1) throws RecordSetException {
        this.rs = this.records.iterator();
    }

    public boolean readRecord(Record var1) throws RecordSetException {
        if(this.rs.hasNext() && this.fetchPosition < this.maxRows) {
            Record var2 = (Record)this.rs.next();
            Field[] var3 = var2.getFields();
            Field[] var4 = var1.getFields();

            for(int var5 = 0; var5 < var3.length; ++var5) {
                var4[var5].setValue(var3[var5]);
            }

            ++this.fetchPosition;
            return true;
        } else {
            return false;
        }
    }

    public void close() throws RecordSetException {
        this.rs = null;
    }

    private boolean isAvalibleRecord(int var1) {
        return var1 >= 0 && var1 < this.records.size();
    }

    public final DataProvider getProvider() {
        return this.provider;
    }

    public final void setProvider(DataProvider var1) {
        this.provider = var1;
    }

    public final TableMetaData getRecordSetMetaData() {
        return this.recordSetMetaData;
    }

    public final void setRecordSetMetaData(TableMetaData var1) {
        this.recordSetMetaData = var1;
    }

    public final Record getRecordTemplete() {
        return this.recordTemplete;
    }

    public int retrieve() throws RecordSetException {
        if(this.recordSetMetaData != null && this.provider != null) {
            RecordSet var1 = new RecordSet(this.recordSetMetaData);
            var1.addHandler(this);
            var1.parse(this.provider);
            return this.records.size();
        } else {
            this.records.clear();
            this.recordSetMetaData = null;
            this.recordTemplete = null;
            throw new RecordSetException("MetaData or Provider is null!");
        }
    }

    public static RecordBuffer getRecordBuffer(TableMetaData var0, DataProvider var1) throws RecordSetException {
        RecordBuffer var2 = new RecordBuffer();
        var2.setRecordSetMetaData(var0);
        var2.setProvider(var1);
        var2.retrieve();
        return var2;
    }

    public static RecordBuffer getRecordBuffer(TableMetaData var0, DataSourceURI var1) throws RecordSetException {
        DefaultDataSourceProvider var2 = new DefaultDataSourceProvider(var1);
        return getRecordBuffer(var0, (DataProvider)var2);
    }

    public static RecordBuffer getRecordBuffer(TableMetaData var0, String var1) throws RecordSetException {
        DefaultDataSourceProvider var2 = new DefaultDataSourceProvider();
        var2.setDataSource(var1);
        return getRecordBuffer(var0, (DataProvider)var2);
    }

    public static RecordBuffer getRecordBuffer(String var0) throws RecordSetException {
        DataSourceURI var1;
        try {
            var1 = new DataSourceURI(var0);
        } catch (URISyntaxException var3) {
            WTT.getLog().debug("Invalid datasource string", var3);
            throw new RecordSetException("Invalid datasource string", var3);
        }

        return getRecordBuffer(var1);
    }

    public static RecordBuffer getRecordBuffer(DataSourceURI var0) throws RecordSetException {
        TableMetaData var1 = var0.getMetaData();
        if(var1 == null) {
            throw new RecordSetException("Get metadata from datasoruce failed!");
        } else {
            return getRecordBuffer(var1, var0);
        }
    }

    public int getFetchSize() {
        return 0;
    }

    public int getMaxRows() {
        return this.maxRows;
    }

    public void setFetchSize(int var1) {
    }

    public void setMaxRows(int var1) {
        this.maxRows = var1;
    }
}
