//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.security.DESEncrypt;
import com.wentuotuo.wtt.util.conf.Attributes;
import com.wentuotuo.wtt.util.conf.Configuration;
import com.wentuotuo.wtt.util.conf.ConfigurationLoader;
import com.wentuotuo.wtt.util.conf.Node;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp.datasources.SharedPoolDataSource;

public class PooledConnectionFactory extends DBConnectionFactory {
    private static Map dataSources = null;
    private final String version = "1.0";
    private final String provider = "Amarsoft";
    private final String describe = "Amarsoft数据库连接池";
    private String resourceFile = null;
    private String configurationLoaderClass = null;

    public PooledConnectionFactory() {
        if(dataSources == null) {
            dataSources = new Hashtable();
        }

    }

    public Connection getInstance(String var1) throws SQLException {
        if(var1 == null) {
            throw new IllegalArgumentException("Database name is null!");
        } else {
            java.sql.Connection var2 = null;
            DataSource var3 = (DataSource)dataSources.get(var1);
            if(var3 == null) {
                throw new SQLException("wentuotuo.sql.ConnectionPool: No this DataSource '" + var1 + "' defined!");
            } else {
                var2 = var3.getConnection();
                return new DefaultQueryConnection(createUID(), var1, var2);
            }
        }
    }

    private void addDataSource(String var1, Properties var2) throws WTTException {
        DriverAdapterCPDS var3 = new DriverAdapterCPDS();

        try {
            var3.setDriver(var2.getProperty("driver"));
        } catch (ClassNotFoundException var10) {
            WTT.getLog().debug(var10);
            throw new WTTException(1002, new String[]{var2.getProperty("driver")});
        }

        var3.setUrl(var2.getProperty("url"));
        var3.setUser(var2.getProperty("user"));
        var3.setPassword(var2.getProperty("password"));
        var3.setLoginTimeout(Integer.parseInt(var2.getProperty("loginTimeout", "0")));
        String var4 = var2.getProperty("logWriter", (String)null);
        PrintWriter var5 = null;
        if(var4 == null) {
            var5 = null;
        } else {
            Object var6 = null;
            if(var4.equalsIgnoreCase("system.err")) {
                var6 = System.err;
            } else if(var4.equalsIgnoreCase("system.out")) {
                var6 = System.out;
            } else {
                try {
                    var6 = new FileOutputStream(var4);
                } catch (FileNotFoundException var9) {
                    WTT.getLog().warn("Set logPrintWriter error.", var9);
                }
            }

            if(var6 != null) {
                try {
                    var5 = new PrintWriter(new OutputStreamWriter((OutputStream)var6, "GBK"), true);
                } catch (UnsupportedEncodingException var8) {
                    var5 = new PrintWriter(new OutputStreamWriter((OutputStream)var6), true);
                }
            }
        }

        if(var5 != null) {
            var3.setLogWriter(var5);
        }

        SharedPoolDataSource var11 = new SharedPoolDataSource();
        var11.setConnectionPoolDataSource(var3);
        var11.setMaxActive(Integer.parseInt(var2.getProperty("maxActive", "10")));
        var11.setMaxWait(Integer.parseInt(var2.getProperty("maxWait", "1000")));
        var11.setMaxIdle(Integer.parseInt(var2.getProperty("maxIdle", "10")));
        var11.setTestOnBorrow(Boolean.getBoolean(var2.getProperty("testOnBorrow", "false")));
        var11.setValidationQuery(var2.getProperty("validationQuery", ""));
        var11.setTestOnReturn(Boolean.getBoolean(var2.getProperty("testOnReturn", "false")));
        var11.setTestWhileIdle(Boolean.getBoolean(var2.getProperty("testWhileIdle", "false")));
        var11.setTimeBetweenEvictionRunsMillis(Integer.parseInt(var2.getProperty("timeBetweenEvictionRunsMillis", "-1")));
        var11.setMinEvictableIdleTimeMillis(Integer.parseInt(var2.getProperty("minEvictableIdleTimeMillis", "60000")));
        var11.setNumTestsPerEvictionRun(Integer.parseInt(var2.getProperty("numTestsPerEvictionRun", "10")));
        dataSources.put(var1, var11);
    }

    public void shutdown() {
        Iterator var1 = dataSources.values().iterator();
        SharedPoolDataSource var2 = null;

        while(var1.hasNext()) {
            var2 = (SharedPoolDataSource)var1.next();
            if(var2 != null) {
                try {
                    var2.close();
                } catch (Exception var4) {
                    WTT.getLog().warn(var4.getMessage());
                }
            }
        }

        dataSources.clear();
    }

    public String[] getInstanceList() {
        return (String[])dataSources.keySet().toArray(new String[0]);
    }

    public String getServiceProvider() {
        return "Amarsoft";
    }

    public String getServiceDescribe() {
        return "Amarsoft数据库连接池";
    }

    public String getServiceVersion() {
        return "1.0";
    }

    public void initConnectionFactory() throws WTTException {
        String var1 = this.getResourceFile();
        Configuration var2 = null;
        if(!StringX.isSpace(this.getConfigurationLoaderClass())) {
            String var3 = StringX.trimAll(this.getConfigurationLoaderClass());

            try {
                ConfigurationLoader var4 = (ConfigurationLoader)Class.forName(var3).newInstance();
                var2 = var4.loadConfiguration(var1);
            } catch (Exception var16) {
                WTT.getLog().warn(var16);
                var2 = new Configuration();
                var2.loadFromFile(var1);
            }
        } else {
            var2 = new Configuration();
            var2.loadFromFile(var1);
        }

        Node var18 = var2.getRootNode().getChild("resources");
        if(var18 == null) {
            throw new WTTException(14, new String[]{"resources"});
        } else {
            Iterator var19 = var18.getChildren("resource").iterator();

            while(true) {
                while(true) {
                    Node var5;
                    Attributes var6;
                    DataElement var7;
                    do {
                        if(!var19.hasNext()) {
                            return;
                        }

                        var5 = (Node)var19.next();
                        var6 = var5.getAttributes();
                        var7 = null;
                        var7 = var6.getAttribute("type");
                    } while(!var6.getAttribute("type", "jdbc").equals("jdbc"));

                    var7 = var6.getAttribute("name");
                    if(StringX.isSpace(var7)) {
                        WTT.getLog().warn(new WTTException(15, new String[]{"name"}));
                    } else {
                        String var8 = var7.getString();
                        boolean var9 = var6.getAttribute("encrypt", "false").getBoolean();
                        var7 = var6.getAttribute("jndiName");
                        String var10 = StringX.isSpace(var7)?null:var7.getString();
                        Properties var11 = new Properties();

                        String var14;
                        String var15;
                        for(Iterator var12 = var5.getChildren().iterator(); var12.hasNext(); var11.setProperty(var14, var15)) {
                            Node var13 = (Node)var12.next();
                            var14 = var13.getName();
                            var15 = StringX.trimAll(var13.getValue());
                            if(var9 && (var14.equals("driver") || var14.equals("url") || var14.equals("user") || var14.equals("password"))) {
                                var15 = DESEncrypt.decrypt(var15);
                            }
                        }

                        try {
                            if(var10 != null && WTT.getRunMode() == 1) {
                                InitialContext var20 = new InitialContext();
                                DataSource var21 = (DataSource)var20.lookup(var10);
                                dataSources.put(var8, var21);
                            } else {
                                this.addDataSource(var8, var11);
                            }
                        } catch (NamingException var17) {
                            WTT.getLog().debug(var17);
                            throw new WTTException(1003, new String[]{var10}, var17);
                        }
                    }
                }
            }
        }
    }

    public final String getResourceFile() {
        return this.resourceFile;
    }

    public final void setResourceFile(String var1) {
        this.resourceFile = var1;
    }

    public String getConfigurationLoaderClass() {
        return this.configurationLoaderClass;
    }

    public void setConfigurationLoaderClass(String var1) {
        this.configurationLoaderClass = var1;
    }
}
