//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.security.audit;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.StringX;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AuditManager {
    private Map auditors = null;
    private Properties properties = null;
    private static AuditManager manager = null;

    protected AuditManager() {
        this.auditors = new HashMap();
        this.properties = new Properties();
    }

    public void addAuditor(Auditor var1) {
        this.auditors.put(var1.getName(), var1);
    }

    public Auditor getAuditor(String var1) {
        return (Auditor)this.auditors.get(var1);
    }

    public String getProperty(String var1) {
        return this.properties.getProperty(var1);
    }

    public Properties getProperties() {
        return this.properties;
    }

    public Properties getHandlerProperties(String var1) {
        Enumeration var2 = this.properties.propertyNames();
        Properties var3 = new Properties();

        while(var2.hasMoreElements()) {
            String var4 = (String)var2.nextElement();
            if(var4.startsWith(var1 + ".")) {
                String var5 = var4.substring(var1.length() + 1);
                if(var5.indexOf(".") < 0 && !var5.equals("filter")) {
                    var3.setProperty(var5, this.properties.getProperty(var4));
                }
            }
        }

        return var3;
    }

    public Properties getFilterProperties(String var1) {
        String var2 = ".filter";
        if(!StringX.isEmpty(var1)) {
            var2 = var1 + var2;
        }

        return this.getHandlerProperties(var2);
    }

    void readConfiguration() throws IOException {
        String var1 = WTT.getProperty("com.wentuotuo.wtt.security.audit.Auditor.configFile");
        if(var1 == null) {
            var1 = WTT.getProperty("com.wentuotuo.wtt.security.audit.config.file");
        }

        FileInputStream var2 = null;
        if(var1 == null) {
            var2 = new FileInputStream(new File(new File(WTT.getProperty("APP_HOME", "./")), "etc/audit.properties"));
        } else {
            var2 = new FileInputStream(var1);
        }

        this.readConfiguration(var2);
        var2.close();
    }

    void readConfiguration(InputStream var1) throws IOException {
        this.properties.clear();
        this.properties.load(var1);
    }

    public void reset() {
    }

    public static AuditManager getManager() {
        synchronized(AuditManager.class) {
            if(manager == null) {
                manager = new AuditManager();

                try {
                    manager.readConfiguration();
                } catch (IOException var3) {
                    WTT.getLog().debug("Read audit configuration  error.", var3);
                }
            }
        }

        return manager;
    }
}
