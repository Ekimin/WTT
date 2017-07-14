//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt;

import com.wentuotuo.wtt.io.FileTool;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.log.Log;
import com.wentuotuo.wtt.log.LogFactory;
import com.wentuotuo.wtt.metadata.DataSourceMetaData;
import com.wentuotuo.wtt.metadata.MetaDataFactory;
import com.wentuotuo.wtt.resource.WTTResourceManager;
import com.wentuotuo.wtt.sql.Connection;
import com.wentuotuo.wtt.sql.DBConnectionFactory;
import com.wentuotuo.wtt.util.conf.Attributes;
import com.wentuotuo.wtt.util.conf.Configuration;
import com.wentuotuo.wtt.util.conf.Node;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WTT {
    public static final int RUN_MODE_STANDALONE = 0;
    public static final int RUN_MODE_WEBCONTAINER = 1;
    public static final int RUN_MODE_EJBCONTAINER = 3;
    private static int runMode = 0;
    private static Pattern varPattern = Pattern.compile("\\{\\$(?:(WTT)|(?:SYSTEM))\\.([^\\{\\}][^\\}]*)\\}");
    private static LinkedHashMap services = new LinkedHashMap();
    private static Properties systemProperties = new Properties();
    private static String version = "1.0.0";
    private static String configureFile = "etc/wtt.xml";
    private static boolean initialized = false;
    private static Log log = null;

    public WTT() {
    }

    public static Connection getDBConnection(String var0) throws SQLException {
        return DBConnectionFactory.getConnection(var0);
    }

    public static DataSourceMetaData getMetaData(String var0) throws SQLException {
        return MetaDataFactory.getMetaData(var0);
    }

    public static Log getLog() {
        if(!isInitOk()) {
            return LogFactory.getLog("com.wentuotuo.wtt.WTT");
        } else {
            if(log == null) {
                log = LogFactory.getLog("com.wentuotuo.wtt.WTT");
            }

            return log;
        }
    }

    public static Log getLog(String var0) {
        return LogFactory.getLog(var0);
    }

    public static String getProperty(String var0) {
        return systemProperties.getProperty(var0);
    }

    public static void setProperty(String var0, String var1) {
        if(var1 != null) {
            systemProperties.setProperty(var0, replaceWTTTags(var1));
        } else {
            systemProperties.setProperty(var0, var1);
        }

    }

    public static void init() {
        String[] var0 = new String[]{"wtt.xml", "etc/wtt.xml"};
        boolean var1 = false;

        int var3;
        for(var3 = 0; var3 < var0.length; ++var3) {
            File var2 = new File(var0[var3]);
            if(var2.exists()) {
                break;
            }
        }

        if(var3 < var0.length) {
            init(var0[var3]);
        }

    }

    public static synchronized void init(String var0) {
        if(initialized) {
            System.out.println(WTTResourceManager.getResourceString("WTTW0002"));
        } else {
            File var1 = FileTool.findFile(var0);
            if(var1 == null) {
                System.out.println((new WTTException(6, new String[]{"NULL FILE"})).getMessage());
                System.out.println(WTTResourceManager.getResourceString("WTTW0001"));
                System.out.println();
            } else {
                init(var1);
            }
        }
    }

    public static synchronized void init(File var0) {
        if(initialized) {
            System.out.println(WTTResourceManager.getResourceString("WTTW0002"));
        } else if(var0 == null) {
            System.out.println((new WTTException(6, new String[]{"NULL FILE"})).getMessage());
            System.out.println(WTTResourceManager.getResourceString("WTTW0001"));
            System.out.println();
        } else {
            try {
                init((InputStream)(new FileInputStream(var0)));
            } catch (FileNotFoundException var2) {
                System.out.println((new WTTException(6, new String[]{var0.getAbsolutePath()})).getMessage());
                System.out.println(WTTResourceManager.getResourceString("WTTW0001"));
                System.out.println();
                var2.printStackTrace(System.err);
            }

        }
    }

    public static synchronized void init(InputStream var0) {
        if(initialized) {
            System.out.println(WTTResourceManager.getResourceString("WTTW0002"));
        } else if(var0 == null) {
            System.out.println((new WTTException(6, new String[]{"NULL INPUT STREAM"})).getMessage());
            System.out.println(WTTResourceManager.getResourceString("WTTW0001"));
            System.out.println();
        } else {
            try {
                Configuration var1 = new Configuration();
                var1.loadFromStream(var0);
                init(var1);
            } catch (Exception var2) {
                System.out.println(var2.getMessage());
                System.out.println(WTTResourceManager.getResourceString("WTTW0001"));
                System.out.println();
                var2.printStackTrace(System.err);
            }
        }
    }

    private static synchronized void init(Configuration var0) {
        System.out.println(WTTResourceManager.getResourceString("WTTI0001"));
        System.out.println();
        Node var1 = var0.getRootNode();
        loadSystemProperties(var1.getProperties());
        registerServices(var1.getChild("WTTServices"));
        initStartOnService();
        System.out.println();
        System.out.println(WTTResourceManager.getResourceString("WTTI0002"));
        System.out.println();
        initialized = true;
    }

    public static synchronized boolean isInitOk() {
        return initialized;
    }

    private static void loadSystemProperties(com.wentuotuo.wtt.util.conf.Properties var0) {
        System.out.println(WTTResourceManager.getResourceString("WTTI0003"));

        DataElement var2;
        String var3;
        for(Iterator var1 = var0.getProperties().iterator(); var1.hasNext(); systemProperties.put(var2.getName(), var3)) {
            var2 = (DataElement)var1.next();
            var3 = var2.getString();
            if(var3 != null) {
                var3 = replaceWTTTags(var3);
            }
        }

        System.getProperties().putAll(systemProperties);
        System.out.println(WTTResourceManager.getResourceString("WTTI0004"));
    }

    private static void registerServices(Node var0) {
        System.out.println(WTTResourceManager.getResourceString("WTTI0005"));
        Iterator var1 = var0.getChildren("Service").iterator();

        while(true) {
            while(true) {
                while(var1.hasNext()) {
                    Node var2 = (Node)var1.next();
                    Attributes var3 = var2.getAttributes();
                    DataElement var4 = var3.getAttribute("id");
                    if(var4 != null && !StringX.isSpace(var4.getString())) {
                        DataElement var5 = var3.getAttribute("enabled");
                        if(var5 != null && !var5.getBoolean()) {
                            System.out.println("--" + MessageFormat.format(WTTResourceManager.getResourceString("WTTW0005"), new String[]{var4.getString()}));
                        } else {
                            WTTServiceStub var6 = new WTTServiceStub(var4.getString());
                            DataElement var7 = var3.getAttribute("initOnStart");
                            var6.initOnStart = var7 == null?false:var7.getBoolean();
                            Node var8 = var2.getChild("ServiceClass");
                            var6.serviceClass = var8 == null?null:StringX.trimAll(var8.getValue());
                            if(var6.serviceClass == null) {
                                System.out.println("--" + MessageFormat.format(WTTResourceManager.getResourceString("WTTW0006"), new String[]{var6.id}));
                            } else {
                                var6.properties.putAll(var2.getProperties().getAsProperties(true));
                                services.put(var6.id, var6);
                                System.out.println("--" + MessageFormat.format(WTTResourceManager.getResourceString("WTTI0007"), new String[]{var6.id}));
                            }
                        }
                    } else {
                        System.out.println("--" + WTTResourceManager.getResourceString("WTTW0004"));
                    }
                }

                System.out.println(WTTResourceManager.getResourceString("WTTI0006"));
                return;
            }
        }
    }

    private static void initStartOnService() {
        System.out.println(WTTResourceManager.getResourceString("WTTI0008"));
        WTTServiceStub var0 = null;
        Iterator var1 = services.values().iterator();

        while(var1.hasNext()) {
            var0 = (WTTServiceStub)var1.next();
            if(var0.isInitOnStart()) {
                try {
                    var0.loadService();
                    var0.initService();
                    System.out.println("--" + MessageFormat.format(WTTResourceManager.getResourceString("WTTI0009"), new String[]{var0.getId()}));
                } catch (WTTException var3) {
                    getLog().warn(var3);
                    System.out.println("--" + MessageFormat.format(WTTResourceManager.getResourceString("WTTW0006"), new String[]{var0.getId()}));
                }
            }
        }

        System.out.println(WTTResourceManager.getResourceString("WTTI0010"));
    }

    public static String getConfigureFile() {
        return configureFile;
    }

    public static WTTServiceStub getServiceStub(String var0) {
        WTTServiceStub var1 = (WTTServiceStub)services.get(var0);
        return var1;
    }

    public static int getProperty(String var0, int var1) {
        int var2 = var1;
        String var3 = systemProperties.getProperty(var0);
        if(var3 != null) {
            try {
                var2 = Integer.parseInt(var3);
            } catch (Exception var5) {
                var2 = var1;
            }
        }

        return var2;
    }

    public static double getProperty(String var0, double var1) {
        double var3 = var1;
        String var5 = systemProperties.getProperty(var0);
        if(var5 != null) {
            try {
                var3 = Double.parseDouble(var5);
            } catch (Exception var7) {
                var3 = var1;
            }
        }

        return var3;
    }

    public static boolean getProperty(String var0, boolean var1) {
        boolean var2 = var1;
        String var3 = systemProperties.getProperty(var0);
        if(var3 != null) {
            var2 = var3.equalsIgnoreCase("true") || var3.equalsIgnoreCase("t") || var3.equalsIgnoreCase("yes") || var3.equalsIgnoreCase("y") || var3.equals("1");
        }

        return var2;
    }

    public static Date getProperty(String var0, Date var1) {
        Date var2 = var1;
        String var3 = systemProperties.getProperty(var0);
        if(var3 != null) {
            if(var3.indexOf(47) > 0) {
                var3 = var3.replaceAll("/", "");
            } else if(var3.indexOf(45) > 0) {
                var3 = var3.replaceAll("-", "");
            }

            String var4 = "yyyyMMdd";
            if(var3.length() == 6) {
                var4 = "yyMMdd";
            }

            try {
                var2 = (new SimpleDateFormat(var4)).parse(var3);
            } catch (ParseException var6) {
                var2 = var1;
            }
        }

        return var2;
    }

    public static Date getProperty(String var0, Date var1, String var2) {
        Date var3 = var1;
        String var4 = systemProperties.getProperty(var0);
        if(var4 != null) {
            SimpleDateFormat var5 = new SimpleDateFormat(var2);

            try {
                var3 = var5.parse(var4);
            } catch (Exception var7) {
                var3 = var1;
            }
        }

        return var3;
    }

    public static String getProperty(String var0, String var1) {
        return systemProperties.getProperty(var0, var1);
    }

    public static String replaceEnvVar(String var0) {
        return replaceVar(var0);
    }

    private static String replaceVar(String var0) {
        Matcher var1 = null;
        StringBuffer var2 = new StringBuffer();

        String var3;
        for(var1 = varPattern.matcher(var0); var1.find(); var1.appendReplacement(var2, var3)) {
            var3 = null;
            if(var1.group(1) == null) {
                var3 = System.getProperty(var1.group(2));
            } else {
                var3 = getProperty(var1.group(2));
            }

            if(var3 == null) {
                var3 = "";
            }
        }

        var1.appendTail(var2);
        return var2.toString();
    }

    public static String replaceComment(String var0) {
        String var1 = "\\{#[^\\}]*\\}";
        return var0.replaceAll(var1, "");
    }

    public static String replaceWTTTags(String var0) {
        return replaceEnvVar(replaceComment(var0));
    }

    public static int getRunMode() {
        return runMode;
    }

    public static void setRunMode(int var0) {
        if(var0 != 0 && var0 != 1 && var0 != 3) {
            System.out.println((new WTTException(2, new String[]{String.valueOf(var0)})).getMessage());
        } else {
            runMode = var0;
        }
    }

    public static void main(String[] var0) {
    }

    static {
        System.out.println("Version " + version);
        System.out.println();
        String var0 = System.getProperty("java.version", "1.2");
        String var1 = "1.4.2_12";
        if(var0.compareToIgnoreCase(var1) < 0) {
            System.out.println("WARNING: Java version " + var0 + " too low to  run WTT, expect " + var1 + " or above!!!  ");
        }

    }
}
