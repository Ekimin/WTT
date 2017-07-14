//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.metadata;

import java.util.Properties;

public class DefaultMetaDataObject implements MetaDataObject {
    private String name;
    private String label;
    private String describe;
    private Properties extendProperties;

    protected DefaultMetaDataObject() {
        this.name = "unnamed";
        this.label = "no label";
        this.describe = "no describe";
    }

    protected DefaultMetaDataObject(String var1) {
        this(var1, var1, var1);
    }

    protected DefaultMetaDataObject(String var1, String var2) {
        this(var1, var2, var2);
    }

    protected DefaultMetaDataObject(String var1, String var2, String var3) {
        this.name = var1;
        this.label = var2;
        this.describe = var3;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String var1) {
        this.name = var1;
    }

    public final String getDescribe() {
        return this.describe;
    }

    public final void setDescribe(String var1) {
        this.describe = var1;
    }

    public final String getLabel() {
        return this.label;
    }

    public final void setLabel(String var1) {
        this.label = var1;
    }

    public String getProperty(String var1) {
        return this.extendProperties != null && var1 != null?this.extendProperties.getProperty(var1):null;
    }

    public void setProperty(String var1, String var2) {
        if(this.extendProperties == null) {
            this.extendProperties = new Properties();
        }

        this.extendProperties.setProperty(var1, var2);
    }

    public String[] getProperties() {
        String[] var1 = new String[0];
        if(this.extendProperties != null) {
            var1 = (String[])this.extendProperties.keySet().toArray(var1);
        }

        return var1;
    }
}
