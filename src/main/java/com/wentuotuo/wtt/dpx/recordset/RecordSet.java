//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.log.Log;
import com.wentuotuo.wtt.metadata.TableMetaData;
import com.wentuotuo.wtt.sql.SQLConstants;
import java.util.ArrayList;

public class RecordSet {
    private String name = null;
    private TableMetaData metaData;
    private ArrayList handlers = new ArrayList();
    private Record currentRecord = null;
    private Record recordTemplet = null;
    private int recordNumber = 0;
    private Log logger = WTT.getLog("com.wentuotuo.wtt.dpx.recordset");
    private int[] flsType = null;

    public RecordSet() {
    }

    public RecordSet(TableMetaData var1) {
        this.setMetaData(var1);
    }

    public void setMetaData(TableMetaData var1) {
        this.metaData = var1;
        this.name = this.metaData.getName();
        if(this.name == null || this.name.equals("")) {
            this.name = "NO_NAME";
        }

        this.recordTemplet = new Record(var1.getColumns());
    }

    public Record createRecord() {
        Record var1 = null;

        try {
            var1 = (Record)this.recordTemplet.clone();
        } catch (CloneNotSupportedException var3) {
            WTT.getLog().error(var3);
        }

        return var1;
    }

    public void addHandler(Handler var1) {
        this.handlers.add(var1);
    }

    public void removeHandler(Handler var1) {
        this.handlers.remove(var1);
    }

    public void removeAllHandler() {
        this.handlers.clear();
    }

    public Handler[] getHandlers() {
        return (Handler[])this.handlers.toArray(new Handler[0]);
    }

    public void parse(DataProvider var1) throws RecordSetException {
        try {
            var1.open(this);
            this.trace(0);

            int var2;
            for(var2 = 0; var2 < this.handlers.size(); ++var2) {
                ((Handler)this.handlers.get(var2)).start(this);
            }

            this.currentRecord = this.createRecord();

            while(var1.readRecord(this.currentRecord)) {
                ++this.recordNumber;
                this.trace(1);

                for(var2 = 0; var2 < this.handlers.size(); ++var2) {
                    ((Handler)this.handlers.get(var2)).handleRecord(this, this.currentRecord);
                }

                this.currentRecord.clear();
            }

        } catch (RecordSetException var8) {
            throw new RecordSetException("[rpcess record error. Record Number:  " + this.recordNumber, var8);
        } finally {
            var1.close();
            this.trace(2);

            for(int var5 = 0; var5 < this.handlers.size(); ++var5) {
                ((Handler)this.handlers.get(var5)).end(this);
            }

        }
    }

    public String getName() {
        return this.name;
    }

    public TableMetaData getMetaData() {
        return this.metaData;
    }

    public Record getRecordTemplet() {
        return this.recordTemplet;
    }

    public int getRecordNumber() {
        return this.recordNumber;
    }

    public Record getCurrentRecord() {
        return this.currentRecord;
    }

    private void trace(int var1) {
        if(this.logger.isTraceEnabled()) {
            StringBuffer var2;
            Field[] var3;
            int var4;
            if(var1 == 0) {
                var2 = new StringBuffer("RecordSet Start!");
                var2.append("\nRecord Structure: ");
                var3 = this.recordTemplet.internalData();
                this.flsType = new int[var3.length];

                for(var4 = 0; var4 < var3.length; ++var4) {
                    var2.append(var3[var4].getName()).append(" ").append(SQLConstants.getBaseDataTypeName(var3[var4].getType())).append("|");
                    this.flsType[var4] = var3[var4].getType();
                }

                this.logger.trace(var2);
            } else if(var1 == 2) {
                this.logger.trace("RecordSet End!");
            } else {
                var2 = new StringBuffer();
                var2.append("Record[").append(this.recordNumber).append("]->");
                var3 = this.currentRecord.internalData();

                for(var4 = 0; var4 < var3.length; ++var4) {
                    if(var3[var4].isNull()) {
                        var2.append("NULL");
                    } else {
                        switch(this.flsType[var4]) {
                            case 0:
                                var2.append(var3[var4].getString());
                                break;
                            case 1:
                                var2.append(var3[var4].getInt());
                                break;
                            case 2:
                                var2.append(var3[var4].getLong());
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
                                var2.append(var3[var4].getString());
                                break;
                            case 4:
                                var2.append(var3[var4].getDouble());
                                break;
                            case 8:
                                var2.append(var3[var4].getBoolean());
                                break;
                            case 16:
                                var2.append(var3[var4].getDate());
                        }
                    }

                    if(var4 != var3.length - 1) {
                        var2.append("|");
                    }
                }

                this.logger.trace(var2);
            }

        }
    }
}
