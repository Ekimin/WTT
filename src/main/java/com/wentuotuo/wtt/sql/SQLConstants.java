//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

public final class SQLConstants {
    public static final byte SQL_TYPE_SELECT = 0;
    public static final byte SQL_TYPE_UPDATE = 1;
    public static final byte SQL_TYPE_INSERT = 2;
    public static final byte SQL_TYPE_DELETE = 3;
    public static final byte SQL_TYPE_CREATE = 4;
    public static final byte SQL_TYPE_DROP = 5;
    public static final byte SQL_TYPE_ALTER = 6;
    public static final byte SQL_TYPE_COMMIT = 7;
    public static final byte SQL_TYPE_ROLLBACK = 8;
    public static final byte SQL_TYPE_CONNECT = 100;
    public static final byte SQL_TYPE_DISCONNECT = 101;
    public static final byte SQL_TYPE_SET_AUTOCOMMIT = 110;
    public static final byte SQL_TYPE_SET_TRANSACTION_ISOLATION = 111;
    public static final byte SQL_TYPE_HELP = 120;
    public static final byte SQL_TYPE_INVALID = -1;
    public static final byte BASE_DATA_TYPE_STRING = 0;
    public static final byte BASE_DATA_TYPE_INT = 1;
    public static final byte BASE_DATA_TYPE_LONG = 2;
    public static final byte BASE_DATA_TYPE_DOUBLE = 4;
    public static final byte BASE_DATA_TYPE_BOOLEAN = 8;
    public static final byte BASE_DATA_TYPE_DATE = 16;
    public static final int BASE_DATA_TYPE_OBJECT = 32;

    public SQLConstants() {
    }

    public static final int getSQLDataType(String var0) {
        String var1 = var0.toUpperCase();
        return var1.equals("BIGINT")?-5:(var1.equals("BINARY")?-2:(var1.equals("BIT")?-7:(var1.equals("BLOB")?2004:(var1.equals("BOOLEAN")?16:(var1.equals("CHAR")?1:(var1.equals("CLOB")?2005:(var1.equals("DATALINK")?70:(var1.equals("DATE")?91:(var1.equals("DATETIME")?91:(var1.equals("DECIMAL")?3:(var1.equals("DISTINCT")?2001:(var1.equals("DOUBLE")?8:(var1.equals("FLOAT")?6:(var1.equals("INT")?4:(var1.equals("INTEGER")?4:(var1.equals("JAVA_OBJECT")?2000:(var1.equals("LONGVARBINARY")?-4:(var1.equals("LONGVARCHAR")?-1:(var1.equals("NULL")?0:(var1.equals("NUMBER")?2:(var1.equals("NUMERIC")?2:(var1.equals("OTHER")?1111:(var1.equals("REAL")?7:(var1.equals("REF")?2006:(var1.equals("SMALLINT")?5:(var1.equals("STRUCT")?2002:(var1.equals("TIME")?92:(var1.equals("TIMESTAMP")?93:(var1.equals("TINYINT")?-6:(var1.equals("VARBINARY")?-3:(var1.equals("VARCHAR")?12:(var1.equals("VARCHAR2")?12:(var1.equals("VCHAR")?12:1)))))))))))))))))))))))))))))))));
    }

    public static final String getSQLDataTypeName(int var0) {
        return var0 == -5?"BIGINT":(var0 == -2?"BINARY":(var0 == -7?"BIT":(var0 == 2004?"BLOB":(var0 == 16?"BOOLEAN":(var0 == 1?"CHAR":(var0 == 2005?"CLOB":(var0 == 70?"DATALINK":(var0 == 91?"DATE":(var0 == 3?"DECIMAL":(var0 == 2001?"DISTINCT":(var0 == 8?"DOUBLE":(var0 == 6?"FLOAT":(var0 == 4?"INTEGER":(var0 == 2000?"JAVA_OBJECT":(var0 == -4?"LONGVARBINARY":(var0 == -1?"LONGVARCHAR":(var0 == 0?"NULL":(var0 == 2?"NUMERIC":(var0 == 1111?"OTHER":(var0 == 7?"REAL":(var0 == 2006?"REF":(var0 == 5?"SMALLINT":(var0 == 2002?"STRUCT":(var0 == 92?"TIME":(var0 == 93?"TIMESTAMP":(var0 == -6?"TINYINT":(var0 == -3?"VARBINARY":(var0 == 12?"VARCHAR":"CHAR"))))))))))))))))))))))))))));
    }

    public static final byte getBaseDataType(String var0) {
        String var1 = var0.toUpperCase();
        return (byte)(var1.equals("CHAR")?0:(var1.equals("VARCHAR")?0:(var1.equals("STRING")?0:(var1.equals("INT")?1:(var1.equals("INTEGER")?1:(var1.equals("LONG")?2:(var1.equals("DOUBLE")?4:(var1.equals("BOOLEAN")?8:(var1.equals("BOOL")?8:(var1.equals("DATE")?16:(var1.equals("DATETIME")?16:0)))))))))));
    }

    public static final String getBaseDataTypeName(byte var0) {
        String var1 = "STRING";
        switch(var0) {
            case 1:
                var1 = "INT";
                break;
            case 2:
                var1 = "LONG";
                break;
            case 4:
                var1 = "DOUBLE";
                break;
            case 8:
                var1 = "BOOL";
                break;
            case 16:
                var1 = "DATE";
        }

        return var1;
    }

    public static final byte SQLTypeToBaseType(int var0) {
        boolean var1 = false;
        byte var2;
        switch(var0) {
            case -6:
            case 4:
            case 5:
                var2 = 1;
                break;
            case -5:
                var2 = 2;
                break;
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
                var2 = 4;
                break;
            case 16:
                var2 = 8;
                break;
            case 91:
            case 92:
            case 93:
                var2 = 16;
                break;
            default:
                var2 = 0;
        }

        return var2;
    }
}
