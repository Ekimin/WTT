//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt;

import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.resource.WTTResourceManager;
import java.text.MessageFormat;

public class WTTException extends A3Exception {
    private static final long serialVersionUID = 1L;
    public static final int WTTI0001_INITIALIZE_BEGIN = 1;
    public static final int WTTI0002_INITIALIZE_COMPLETE = 2;
    public static final int WTTI0003_LOAD_SYSTEM_PROPERTIES_BEGIN = 3;
    public static final int WTTI0004_LOAD_SYSTEM_PROPERTIES_COMPLETE = 4;
    public static final int WTTI0005_REGISTER_SERVICE_BEGIN = 5;
    public static final int WTTI0006_REGISTER_SERVICE_COMPLETE = 6;
    public static final int WTTI0007_REGISTER_SERVICE_X = 7;
    public static final int WTTI0008_INITIALIZE_STARTUP_SERVICES = 8;
    public static final int WTTI0009_SERVICE_X_INITIALIZED = 9;
    public static final int WTTI0010_INITIALIZE_STARTUP_SERVICES_COMPLETE = 10;
    public static final int WTTW0001_WTT_INITIALIZE_FAILED = 1;
    public static final int WTTW0002_DUPLICATE_INIT_WTT = 2;
    public static final int WTTW0003_NO_SYSTEM_PROPERTIES_DEFINED = 3;
    public static final int WTTW0004_IGNORE_NULL_ID_SERVICE = 4;
    public static final int WTTW0005_IGNORE_DISABLED_SERVICE_X = 5;
    public static final int WTTW0006_IGNORE_NULL_CLASS_SERVICE_X = 6;
    public static final int WTTW0007_SET_PROPERTY_ERROR_X = 7;
    public static final int WTTW0008_JBO_QUERY_RESULT_TOO_LARGE_N_Q = 8;
    public static final int WTTW0009_METHOD_NOT_IMPLEMENT_M = 9;
    public static final int S0001_WTT_ERROR = 1;
    public static final int S0002_WTT_INVALID_RUN_MODE = 2;
    public static final int S0003_WTT_LOAD_PROPERTIES_ERROR = 3;
    public static final int S0004_WTT_LOAD_SERVICE_ERROR = 4;
    public static final int S0005_WTT_INIT_SERVICE_FAILED = 5;
    public static final int S0006_CFG_SOURCE_NOT_EXISTS = 6;
    public static final int S0007_CFG_LOAD_ERROR = 7;
    public static final int S0008_CFG_PARSE_ERROR = 8;
    public static final int S0009_CFG_TARGET_NOT_EXISTS = 9;
    public static final int S0010_CFG_SAVE_ERROR = 10;
    public static final int S0011_WTT_ARGUMENT_IS_NULL = 11;
    public static final int S0012_WTT_ARGUMENT_IS_EMPTY = 12;
    public static final int S0013_WTT_ARGUMENT_IS_SPACE = 13;
    public static final int S0014_CFG_NODE_MISSING = 14;
    public static final int S0015_CFG_ATTRIBUTE_MISSING = 15;
    public static final int S0016_CFG_PROPERTY_MISSING = 16;
    public static final int S0021_OBJ_CLASS_UNDEFINE = 21;
    public static final int S0022_OBJ_CLASS_NOT_FOUND = 22;
    public static final int S0023_OB_OBJECT_INITIALIZE_ERROR = 23;
    public static final int S0024_OBJ_OBJECT_ACCESS_ERROR = 24;
    public static final int S1001_SQL_ERROR = 1001;
    public static final int S1002_SQL_DRIVER_CLASS_NOT_FOUND = 1002;
    public static final int S1003_SQL_JNDI_ERROR = 1003;
    public static final int S9999_CUSTOM_ERROR = 9999;

    public WTTException() {
        this(1);
    }

    public WTTException(String var1) {
        this(9999, (Object[])(new String[]{StringX.isSpace(var1)?"":var1}));
    }

    public WTTException(String var1, Throwable var2) {
        this(9999, new String[]{StringX.isSpace(var1)?"":var1}, var2);
    }

    public WTTException(int var1) {
        super(getErrorCode(var1));
    }

    public WTTException(int var1, Object[] var2) {
        super(getErrorCode(var1), var2);
    }

    public WTTException(int var1, Throwable var2) {
        super(getErrorCode(var1), var2);
    }

    public WTTException(int var1, Object[] var2, Throwable var3) {
        super(getErrorCode(var1), var2, var3);
    }

    public String getErrorMessage() {
        return WTTResourceManager.getResourceString(this.getErrorCode());
    }

    protected static String getErrorCode(int var0) {
        String var1 = null;
        if(var0 < 10) {
            var1 = "000" + String.valueOf(var0);
        } else if(var0 < 100) {
            var1 = "00" + String.valueOf(var0);
        } else if(var0 < 1000) {
            var1 = "0" + String.valueOf(var0);
        } else {
            var1 = String.valueOf(var0);
        }

        return "WTTS" + var1;
    }

    protected static String getWarnCode(int var0) {
        String var1 = null;
        if(var0 < 10) {
            var1 = "000" + String.valueOf(var0);
        } else if(var0 < 100) {
            var1 = "00" + String.valueOf(var0);
        } else if(var0 < 1000) {
            var1 = "0" + String.valueOf(var0);
        } else {
            var1 = String.valueOf(var0);
        }

        return "WTTW" + var1;
    }

    protected static String getInfoCode(int var0) {
        String var1 = null;
        if(var0 < 10) {
            var1 = "000" + String.valueOf(var0);
        } else if(var0 < 100) {
            var1 = "00" + String.valueOf(var0);
        } else if(var0 < 1000) {
            var1 = "0" + String.valueOf(var0);
        } else {
            var1 = String.valueOf(var0);
        }

        return "WTTI" + var1;
    }

    public static String getInfoMessage(int var0, Object[] var1) {
        String var2 = getWarnCode(var0);
        String var3 = WTTResourceManager.getResourceString(var2);
        if(var1 != null) {
            var3 = MessageFormat.format(var3, var1);
        }

        return var2 + ": " + var3;
    }

    public static String getWarnMessage(int var0, Object[] var1) {
        String var2 = getWarnCode(var0);
        String var3 = WTTResourceManager.getResourceString(var2);
        if(var1 != null) {
            var3 = MessageFormat.format(var3, var1);
        }

        return var2 + ": " + var3;
    }

    public static String getErrorMessage(int var0, Object[] var1) {
        String var2 = getWarnCode(var0);
        String var3 = WTTResourceManager.getResourceString(var2);
        if(var1 != null) {
            var3 = MessageFormat.format(var3, var1);
        }

        return var2 + ": " + var3;
    }
}
