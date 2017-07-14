//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt;

import com.wentuotuo.wtt.lang.ObjectX;
import com.wentuotuo.wtt.resource.WTTResourceManager;
import java.util.Enumeration;
import java.util.Properties;

public class WTTServiceStub {
    protected String id;
    protected String serviceClass;
    protected Properties properties;
    protected boolean initOnStart = false;
    protected WTTService serviceInstance = null;

    public WTTServiceStub() {
        this.properties = new Properties();
    }

    public WTTServiceStub(String var1) {
        this.id = var1;
        this.properties = new Properties();
    }

    public void setProperty(String var1, String var2) {
        this.properties.setProperty(var1, var2);
    }

    public String getProperty(String var1) {
        return this.properties.getProperty(var1);
    }

    public String getServiceClass() {
        return this.serviceClass;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String var1) {
        this.id = var1;
    }

    public void setServiceClass(String var1) {
        this.serviceClass = var1;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public boolean isInitOnStart() {
        return this.initOnStart;
    }

    public void loadService() throws WTTException {
        WTTService var1 = null;

        try {
            Class var2 = Class.forName(this.getServiceClass());
            var1 = (WTTService)var2.newInstance();
            Enumeration var3 = this.properties.keys();

            while(var3.hasMoreElements()) {
                String var4 = (String)var3.nextElement();
                String var5 = this.properties.getProperty(var4);
                if(!ObjectX.setPropertyX(var1, var4, var5, true)) {
                    System.err.println(WTTResourceManager.getResourceString("AWEW0007") + "[" + var4 + "," + var5 + "]");
                }
            }

            this.serviceInstance = var1;
        } catch (Exception var6) {
            if(this.id.equals("LOG")) {
                System.err.println(WTTResourceManager.getResourceString("AWES0006"));
                var6.printStackTrace(System.err);
            } else {
                WTT.getLog().debug("Load service failed", var6);
            }

            throw new WTTException(4, new String[]{this.id}, var6);
        }
    }

    public void initService() throws WTTException {
        if(this.serviceInstance != null) {
            this.serviceInstance.init();
        }

    }

    public final WTTService getServiceInstance() {
        return this.serviceInstance;
    }
}
