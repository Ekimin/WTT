//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.WTTService;
import com.wentuotuo.wtt.WTTServiceStub;
import java.sql.SQLException;

public abstract class DBConnectionFactory implements WTTService {
    private static final String serviceID = "DBCONNECTION";
    private static DBConnectionFactory factory = null;
    private static int maxUid = 1;

    protected DBConnectionFactory() {
    }

    protected static synchronized int createUID() {
        if(maxUid == 2147483647) {
            maxUid = 1;
        }

        return maxUid++;
    }

    public abstract Connection getInstance(String var1) throws SQLException;

    public abstract String[] getInstanceList();

    public abstract void shutdown();

    public static DBConnectionFactory getFactory() throws SQLException {
        if(factory == null) {
            WTTServiceStub var0 = WTT.getServiceStub("DBCONNECTION");
            if(var0 != null) {
                try {
                    var0.loadService();
                    var0.initService();
                    WTT.getLog().debug("WTTService " + var0.getId() + " initialiezed!");
                } catch (WTTException var2) {
                    WTT.getLog().debug("WTTService " + var0.getId() + " initialieze failed!", var2);
                }
            }
        }

        return factory;
    }

    public abstract void initConnectionFactory() throws WTTException;

    public static Connection getConnection(String var0) throws SQLException {
        return getFactory().getInstance(var0);
    }

    public String getServiceId() {
        return "DBCONNECTION";
    }

    public final void init() throws WTTException {
        this.initConnectionFactory();
        factory = this;
    }
}
