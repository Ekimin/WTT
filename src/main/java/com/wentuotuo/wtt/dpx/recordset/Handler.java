//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

public interface Handler {
    void start(RecordSet var1) throws RecordSetException;

    void handleRecord(RecordSet var1, Record var2) throws RecordSetException;

    void end(RecordSet var1) throws RecordSetException;
}
