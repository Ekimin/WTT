//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.audit;

import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.security.audit.AuditData;

public class BizObjectData implements AuditData {
    private DataElement[] keyAttributes = null;
    private DataElement[] changedAttributes = null;
    private Object[] originalValues = null;

    public BizObjectData() {
    }

    public DataElement[] getKeyAttributes() {
        return this.keyAttributes;
    }

    public void setKeyAttributes(DataElement[] var1) {
        this.keyAttributes = var1;
    }

    public DataElement[] getChangedAttributes() {
        return this.changedAttributes;
    }

    public void setChangedAttributes(DataElement[] var1) {
        this.changedAttributes = var1;
    }

    public Object[] getOriginalValues() {
        return this.originalValues;
    }

    public void setOriginalValues(Object[] var1) {
        this.originalValues = var1;
    }

    public String exportText() {
        StringBuffer var1 = new StringBuffer();
        var1.append("{");
        int var2;
        if(this.keyAttributes != null) {
            for(var2 = 0; var2 < this.keyAttributes.length; ++var2) {
                var1.append("{").append(this.keyAttributes[var2].getName()).append("=").append(this.keyAttributes[var2].getValue()).append("}");
            }
        }

        var1.append("}");
        var1.append("{");
        if(this.changedAttributes != null) {
            for(var2 = 0; var2 < this.changedAttributes.length; ++var2) {
                var1.append("{");
                var1.append(this.changedAttributes[var2].getName()).append("={").append(this.changedAttributes[var2].isNull()?"":this.changedAttributes[var2].getValue()).append("}");
                var1.append("{").append(this.originalValues != null && var2 < this.originalValues.length?this.originalValues[var2]:"").append("}");
                var1.append("}");
            }
        }

        var1.append("}");
        return var1.toString();
    }

    public void importText(String var1) {
    }
}
