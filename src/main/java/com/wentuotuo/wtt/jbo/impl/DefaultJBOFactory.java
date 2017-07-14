//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.impl;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.jbo.BizObjectClass;
import com.wentuotuo.wtt.jbo.BizObjectManager;
import com.wentuotuo.wtt.jbo.BizObjectPackage;
import com.wentuotuo.wtt.jbo.JBOClassNotFoundException;
import com.wentuotuo.wtt.jbo.JBOException;
import com.wentuotuo.wtt.jbo.JBOFactory;
import com.wentuotuo.wtt.jbo.JBOTransaction;
import com.wentuotuo.wtt.jbo.LocalTransaction;
import com.wentuotuo.wtt.lang.ObjectX;
import com.wentuotuo.wtt.resource.WTTResourceManager;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public abstract class DefaultJBOFactory extends JBOFactory {
    private static final String version = "1.0";
    private static final String provider = "Amarsoft";
    private static final String describe = "缺省的JBO对象管理工厂服务";
    private String defaultPackage = null;
    private Map classPool = new TreeMap();
    private Map managerPool = new TreeMap();
    private Map classManagerInfo = new HashMap();
    private Map packages = new TreeMap();

    public DefaultJBOFactory() {
    }

    public BizObjectClass getClass(String var1) throws JBOClassNotFoundException {
        if(var1 == null) {
            return null;
        } else {
            String var2 = var1;
            if(var1.indexOf(".") < 0) {
                var2 = this.getDefaultPackage() + "." + var1;
            }

            BizObjectClass var3 = (BizObjectClass)this.classPool.get(var2);
            if(var3 == null) {
                throw new JBOClassNotFoundException(var2);
            } else {
                return var3;
            }
        }
    }

    public String[] getPackages() {
        String[] var1 = (String[])this.packages.keySet().toArray(new String[0]);
        return var1;
    }

    public String[] getClasses() {
        return (String[])this.classPool.keySet().toArray(new String[0]);
    }

    public BizObjectPackage getPackage(String var1) {
        return (BizObjectPackage)this.packages.get(var1);
    }

    public String[] getClasses(String var1) {
        ArrayList var2 = new ArrayList();
        Iterator var3 = this.classPool.keySet().iterator();

        while(var3.hasNext()) {
            String var4 = (String)var3.next();
            String var5 = var4.substring(0, var4.lastIndexOf(46));
            if(var5.equals(var1)) {
                var2.add(var4);
            }
        }

        return (String[])var2.toArray(new String[0]);
    }

    public String getDefaultPackage() {
        return this.defaultPackage;
    }

    public void setDefaultPackage(String var1) {
        this.defaultPackage = var1;
    }

    public BizObjectManager getManager(String var1) throws JBOException {
        BizObjectClass var2 = this.getClass(var1);
        BizObjectManager var3 = null;
        Properties var4 = (Properties)this.classManagerInfo.get(var2.getAbsoluteName());
        ManagerInfo var5 = null;
        String var6;
        if(var4 != null) {
            var6 = var4.getProperty("id");
            if(var6 != null) {
                var5 = (ManagerInfo)this.managerPool.get(var6);
            }
        }

        int var7;
        for(var6 = var2.getAbsoluteName(); var5 == null && var6 != null; var6 = var7 < 0?null:var6.substring(0, var7)) {
            var5 = (ManagerInfo)this.managerPool.get(var6);
            if(var5 != null) {
                break;
            }

            var7 = var6.lastIndexOf(46);
        }

        if(var5 == null) {
            var5 = (ManagerInfo)this.managerPool.get("default");
        }

        if(var5 == null) {
            throw new JBOException(1332, new String[]{var1});
        } else {
            var3 = this.createManager(var5, var4);
            var3.setManagedClass(var2);
            return var3;
        }
    }

    public JBOTransaction createTransaction() throws JBOException {
        return new LocalTransaction();
    }

    private BizObjectManager createManager(ManagerInfo var1, Properties var2) throws JBOException {
        BizObjectManager var3 = null;

        try {
            var3 = (BizObjectManager)ObjectX.createObject(var1.getManagerClass());
        } catch (WTTException var9) {
            WTT.getLog().debug(var9);
            throw new JBOException(1302, var9);
        }

        ObjectX.setProperty(var3, "id", var1.getManagerId(), false);
        ObjectX.setProperty(var3, "describe", var1.getManagerDescribe(), false);
        Properties var4 = var1.getManagerProperties();
        Properties var5 = var3.getQueryProperties();
        Enumeration var6 = var4.keys();

        String var7;
        String var8;
        while(var6.hasMoreElements()) {
            var7 = (String)var6.nextElement();
            var8 = var4.getProperty(var7);
            if(var7.startsWith("query.")) {
                var5.put(var7.substring(6), var8);
            } else if(!ObjectX.setPropertyX(var3, var7, var8, false)) {
                WTT.getLog().debug(WTTResourceManager.getResourceString("WTTW0007") + "[" + var7 + "," + var8 + "]");
            }
        }

        if(var2 != null) {
            var6 = var2.keys();

            while(var6.hasMoreElements()) {
                var7 = (String)var6.nextElement();
                var8 = var2.getProperty(var7);
                if(var7.startsWith("query.")) {
                    var5.put(var7.substring(6), var8);
                } else if(!ObjectX.setPropertyX(var3, var7, var8, false)) {
                    WTT.getLog().debug(WTTResourceManager.getMessage("WTTW0007", new String[]{var3.getClass().getName(), var7, var8}));
                }
            }
        }

        return var3;
    }

    public String getServiceDescribe() {
        return "缺省的JBO对象管理工厂服务";
    }

    public String getServiceProvider() {
        return "Amarsoft";
    }

    public String getServiceVersion() {
        return "1.0";
    }

    public void shutdown() {
    }

    public void initFactory() throws WTTException {
        this.loadFactoryPool(this.packages, this.managerPool, this.classPool);
    }

    protected Properties getClassManager(String var1) {
        return (Properties)this.classManagerInfo.get(var1);
    }

    protected void setClassManager(String var1, Properties var2) {
        if(var2 == null) {
            this.classManagerInfo.remove(var1);
        } else {
            this.classManagerInfo.put(var1, var2);
        }

    }

    protected abstract void loadFactoryPool(Map var1, Map var2, Map var3) throws WTTException;
}
