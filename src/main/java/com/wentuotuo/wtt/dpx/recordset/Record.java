//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

import com.wentuotuo.wtt.metadata.ColumnMetaData;

public class Record implements Cloneable {
    private Field[] fields;

    protected Record(ColumnMetaData[] var1) {
        this.fields = new Field[var1.length + 1];
        this.fields[0] = new Field("RECORD_INFO", 0);

        for(int var2 = 0; var2 < var1.length; ++var2) {
            this.fields[var2 + 1] = new Field(var1[var2]);
        }

    }

    public Field[] getFields() {
        Field[] var1 = new Field[this.fields.length - 1];
        System.arraycopy(this.fields, 1, var1, 0, var1.length);
        return var1;
    }

    public Field getField(String var1) {
        Field var2 = null;

        for(int var3 = 0; var3 < this.fields.length; ++var3) {
            if(this.fields[var3].getName().equalsIgnoreCase(var1)) {
                var2 = this.fields[var3];
                break;
            }
        }

        return var2;
    }

    public Field getField(int var1) {
        Field var2 = null;
        if(var1 > 0 && var1 < this.fields.length) {
            var2 = this.fields[var1];
        }

        return var2;
    }

    public int indexOf(String var1) {
        int var2 = -1;
        if(var1 == null) {
            return var2;
        } else {
            for(int var3 = 1; var3 < this.fields.length; ++var3) {
                if(this.fields[var3].getName().equalsIgnoreCase(var1)) {
                    var2 = var3;
                    break;
                }
            }

            return var2;
        }
    }

    public int getFieldNumber() {
        return this.fields.length - 1;
    }

    public Field fieldAt(int var1) {
        return this.getField(var1 + 1);
    }

    public Object clone() throws CloneNotSupportedException {
        Record var1 = (Record)super.clone();
        var1.fields = new Field[this.fields.length];

        for(int var2 = 0; var2 < this.fields.length; ++var2) {
            ColumnMetaData var3 = this.fields[var2].getMetaData();
            var1.fields[var2] = var3 == null?new Field(this.fields[var2].getName(), this.fields[var2].getType()):new Field(this.fields[var2].getMetaData());
            var1.fields[var2].setValue(this.fields[var2]);
        }

        return var1;
    }

    public void clear() {
        for(int var1 = 0; var1 < this.fields.length; ++var1) {
            this.fields[var1].setNull();
        }

    }

    public Field[] internalData() {
        return this.fields;
    }

    public Field getRecordInfo() {
        return this.fields[0];
    }
}
