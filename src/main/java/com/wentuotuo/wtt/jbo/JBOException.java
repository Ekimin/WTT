//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.lang.StringX;

public class JBOException extends WTTException {
    private static final long serialVersionUID = 1L;
    public static final int S1301_JBO_ERROR = 1301;
    public static final int S1302_JBO_CREATE_MANAGER_ERROR = 1302;
    public static final int S1303_JBO_CLASS_NOT_FOUND = 1303;
    public static final int S1304_JBO_CLASS_MISMATCH = 1304;
    public static final int S1305_JBO_NOT_INSTANCE_OF = 1305;
    public static final int S1306_JBO_ATTRIBUTE_INDEX_OUT_BOUNDS = 1306;
    public static final int S1307_JBO_ATTRIBUTE_NOT_FOUND = 1307;
    public static final int S1308_JBO_KEY_ATTRIBUTE_INDEX_OUT_BOUNDS = 1308;
    public static final int S1309_JBO_KEY_ATTRIBUTE_NOT_FOUND = 1309;
    public static final int S1310_JBO_KEY_NOT_DEFINE = 1310;
    public static final int S1311_JBO_TRANSACTION_CREATE_ERROR = 1311;
    public static final int S1312_JBO_TRANSACTION_COMMIT_ERROR = 1312;
    public static final int S1313_JBO_TRANSACTION_ROLLBACK_ERROR = 1313;
    public static final int S1314_JBO_TRANSACTION_OBJECT_NOT_JOIN = 1314;
    public static final int S1315_JBO_TRANSACTION_CONNECTION_ERROR = 1315;
    public static final int S1316_JBO_LOCAL_TRANSACTION_DATABASE_NOT_SET = 1316;
    public static final int S1317_JBO_LOCAL_TRANSACTION_DATABASE_NOT_SAME = 1317;
    public static final int S1318_JBO_XML_CONFIG_FILE_NOT_EXISTS = 1318;
    public static final int S1319_JBO_XML_CONFIG_FILE_PARSE_ERROR = 1319;
    public static final int S1320_JBO_MANAGER_NOT_STATE_OBJECT = 1320;
    public static final int S1321_JBO_MANAGER_STATUS_UNKNOWN = 1321;
    public static final int S1322_JBO_MANAGER_EXECUTE_UPDATE = 1322;
    public static final int S1323_JBO_MANAGER_OPEN_CONNECTOIN = 1323;
    public static final int S1324_JBO_MANAGER_SET_PARAMERER = 1324;
    public static final int S1325_JBO_MANAGER_NOT_SINGLE_KEY = 1325;
    public static final int S1326_JBO_MANAGER_KEY_TYPE_INVALID = 1326;
    public static final int S1327_JBO_MANAGER_CREATE_KEY_ERROR = 1327;
    public static final int S1328_JBO_QUERY_EXECUTE_SQL = 1328;
    public static final int S1329_JBO_QUERY_OPEN_CONNECTION = 1329;
    public static final int S1330_JBO_QUERY_SUMMARY_ERROR = 1330;
    public static final int S1331_JBO_QUERY_NOT_TABLEMAPPER = 1331;
    public static final int S1332_JBO_MANAGER_UNDEFINED = 1332;
    public static final int S1333_JBO_QUERY_RESULT_EXCEED = 1333;
    public static final int S1334_JBO_TRANSACTION_DUPLICATE_JOIN = 1334;
    public static final int S1401_JBO_QL_SYNTAX_ERROR = 1401;
    public static final int S1402_JBO_QL_SYNTAX_LEFT_PWTTNTHESIS_MISSING = 1402;
    public static final int S1403_JBO_QL_SYNTAX_RIGHT_PWTTNTHESIS_MISSING = 1403;
    public static final int S1404_JBO_QL_SYNTAX_STRING_CONSTANT_NOT_CLOSE = 1404;
    public static final int S1405_JBO_QL_SYNTAX_ATTRIBUTE_EXPRESSION_NOT_CLOSE = 1405;
    public static final int S1406_JBO_QL_SYNTAX_INVALID_OPERATOR_AT_END = 1406;
    public static final int S1407_JBO_QL_SYNTAX_INVALID_KEYWORD_AT_END = 1407;
    public static final int S1408_JBO_QL_SYNTAX_INVALID_KEYWORD = 1408;
    public static final int S1409_JBO_QL_SYNTAX_INVALID_FUNCTION_INDENTIFIER = 1409;
    public static final int S1410_JBO_QL_SYNTAX_INVALID_PARAMETER_INDENTIFIER = 1410;
    public static final int S1411_JBO_QL_SYNTAX_INVALID_ALIAS_INDENTIFIER = 1411;
    public static final int S1412_JBO_QL_SYNTAX_INVALID_CLASS_INDENTIFIER = 1412;
    public static final int S1413_JBO_QL_SYNTAX_INVALID_ATTRIBUTE_INDENTIFIER = 1413;
    public static final int S1414_JBO_QL_SYNTAX_INVALID_VIRTUAL_ATTRIBUTE_INDENTIFIER = 1414;
    public static final int S1415_JBO_QL_SYNTAX_INVALID_START_WORD = 1415;
    public static final int S1416_JBO_QL_SYNTAX_INVALID_NUMBER_FORMAT = 1416;
    public static final int S1417_JBO_QL_SYNTAX_INVALID_FUNCTION_SYNTAX = 1417;
    public static final int S1418_JBO_QL_SYNTAX_ALIAS_MISSING = 1418;
    public static final int S1419_JBO_QL_SYNTAX_EMPTY_QUERY = 1419;
    public static final int S1431_JBO_QL_PARSER_CLASS_UNDEFINE = 1431;
    public static final int S1432_JBO_QL_PARSER_CLASS_NOT_FOUND = 1432;
    public static final int S1433_JBO_QL_PARSER_INITIALIZE_OBJECT_ERROR = 1433;
    public static final int S1434_JBO_QL_PARSER_ACCESS_OBJECT_ERROR = 1434;

    public JBOException() {
        super(1301, new String[]{""});
    }

    public JBOException(String var1) {
        this(9999, (Object[])(new String[]{StringX.isSpace(var1)?"":var1}));
    }

    public JBOException(int var1) {
        super(var1);
    }

    public JBOException(int var1, Object[] var2) {
        super(var1, var2);
    }

    public JBOException(int var1, Throwable var2) {
        super(var1, var2);
    }

    public JBOException(int var1, Object[] var2, Throwable var3) {
        super(var1, var2, var3);
    }

    public static JBOException QLError(int var0, String var1, String var2) {
        return new JBOException(var0, new String[]{var1, var2});
    }
}
