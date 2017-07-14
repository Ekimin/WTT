//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.metadata;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.WTTService;
import com.wentuotuo.wtt.WTTServiceStub;

public abstract class MetaDataFactory implements WTTService {
    protected static MetaDataFactory factory;
    public static final String serviceId = "METADATA";

    protected MetaDataFactory() {
    }

    public abstract DataSourceMetaData getInstance(String var1);

    public static DataSourceMetaData getMetaData(String var0) {
        return getFactory().getInstance(var0);
    }

    public static MetaDataFactory getFactory() {
        if(factory == null) {
            WTTServiceStub var0 = WTT.getServiceStub("METADATA");
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

    public final void init() throws WTTException {
        this.initMetaDataFactory();
        factory = this;
    }

    public abstract void initMetaDataFactory() throws WTTException;

    public abstract DataSourceMetaData[] getDataSources();

    public String getServiceId() {
        return "METADATA";
    }
}
