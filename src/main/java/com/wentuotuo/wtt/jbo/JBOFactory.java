//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.WTTService;
import com.wentuotuo.wtt.WTTServiceStub;

public abstract class JBOFactory implements WTTService {
    protected static JBOFactory factory;
    public static final String serviceId = "JBO";

    protected JBOFactory() {
    }

    public abstract String getDefaultPackage();

    public abstract String[] getPackages();

    public abstract BizObjectPackage getPackage(String var1);

    public abstract String[] getClasses(String var1);

    public abstract String[] getClasses();

    public abstract BizObjectClass getClass(String var1) throws JBOException;

    public abstract BizObjectManager getManager(String var1) throws JBOException;

    public BizObjectManager getManager(String var1, JBOTransaction var2) throws JBOException {
        if(var2 == null) {
            throw new JBOException(1301, new String[]{"Transaction invalid!"});
        } else {
            BizObjectManager var3 = this.getManager(var1);
            var2.join(var3);
            return var3;
        }
    }

    public abstract JBOTransaction createTransaction() throws JBOException;

    public static JBOFactory getFactory() {
        if(factory == null) {
            WTTServiceStub var0 = WTT.getServiceStub("JBO");
            if(var0 != null) {
                try {
                    var0.loadService();
                    var0.initService();
                } catch (WTTException var2) {
                    WTT.getLog().debug(var2);
                }
            }
        }

        return factory;
    }

    public static BizObjectManager getBizObjectManager(String var0) throws JBOException {
        return getFactory().getManager(var0);
    }

    public static BizObjectManager getBizObjectManager(String var0, JBOTransaction var1) throws JBOException {
        return getFactory().getManager(var0, var1);
    }

    public static BizObjectQuery createBizObjectQuery(String var0, String var1) throws JBOException {
        return getBizObjectManager(var0).createQuery(var1);
    }

    public static BizObject getBizObject(String var0, Object var1) throws JBOException {
        return getBizObjectManager(var0).getObject(var1);
    }

    public static BizObjectClass getBizObjectClass(String var0) throws JBOException {
        return getFactory().getClass(var0);
    }

    public static JBOTransaction createJBOTransaction() throws JBOException {
        return getFactory().createTransaction();
    }

    public abstract void initFactory() throws WTTException;

    public void init() throws WTTException {
        this.initFactory();
        factory = this;
    }

    public String getServiceId() {
        return "JBO";
    }
}
