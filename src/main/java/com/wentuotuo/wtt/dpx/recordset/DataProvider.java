//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

public interface DataProvider {
    void open(RecordSet var1) throws RecordSetException;

    boolean readRecord(Record var1) throws RecordSetException;

    void close() throws RecordSetException;

    int getFetchSize();

    void setFetchSize(int var1);

    int getMaxRows();

    void setMaxRows(int var1);
}
