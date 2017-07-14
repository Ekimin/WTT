//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.impl;

import com.wentuotuo.wtt.jbo.BizObject;
import com.wentuotuo.wtt.jbo.BizObjectClass;
import com.wentuotuo.wtt.jbo.BizObjectKey;
import com.wentuotuo.wtt.jbo.JBOException;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.ObjectX;
import java.io.Serializable;
import java.util.ArrayList;

public class StateBizObject extends BizObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte state = 0;
    private Object[] originalValues = null;

    public StateBizObject(BizObjectClass var1) {
        super(var1);
    }

    public StateBizObject(String var1) throws JBOException {
        super(var1);
    }

    protected void setState(byte var1) {
        if(this.state != var1) {
            if(var1 == 1) {
                this.backupAttrbutes();
                this.state = var1;
            }

            if(var1 == 2) {
                this.originalValues = null;
                this.state = var1;
            }

            if(var1 == 3) {
                if(this.state == 2) {
                    this.backupAttrbutes();
                }

                this.state = var1;
            }

        }
    }

    public byte getState() {
        if(this.state == 1 && this.checkChange()) {
            this.state = 3;
        }

        return this.state;
    }

    protected boolean checkChange() {
        boolean var1 = false;

        for(int var2 = 0; var2 < this.attributes.length; ++var2) {
            var1 = !ObjectX.equals(this.attributes[var2].getValue(), this.originalValues[var2]);
            if(var1) {
                break;
            }
        }

        return var1;
    }

    private void backupAttrbutes() {
        this.originalValues = new Object[this.attributes.length];

        for(int var1 = 0; var1 < this.attributes.length; ++var1) {
            this.originalValues[var1] = this.attributes[var1].getValue();
        }

    }

    public DataElement[] getChangedAttributes() {
        byte var1 = this.getState();
        if(var1 != 0 && var1 != 1) {
            ArrayList var2 = new ArrayList();

            for(int var3 = 0; var3 < this.attributes.length; ++var3) {
                boolean var4 = false;
                if(var1 == 2) {
                    var4 = !this.attributes[var3].isNull();
                } else {
                    var4 = !ObjectX.equals(this.attributes[var3].getValue(), this.originalValues[var3]);
                }

                if(var4) {
                    var2.add(this.attributes[var3]);
                }
            }

            return (DataElement[])var2.toArray(new DataElement[0]);
        } else {
            return null;
        }
    }

    public Object getOriginalValue(String var1) {
        int var2 = this.indexOfAttribute(var1);
        return var2 < 0?null:this.getOriginalValue(var2);
    }

    public Object getOriginalValue(int var1) {
        Object var2 = null;
        if(var1 >= 0 && var1 < this.getAttributeNumber()) {
            if(this.getState() == 2) {
                try {
                    var2 = this.getAttribute(var1).getValue();
                } catch (JBOException var4) {
                    ;
                }
            } else if(this.originalValues != null && var1 >= 0 && var1 <= this.originalValues.length) {
                var2 = this.originalValues[var1];
            } else {
                var2 = null;
            }

            return var2;
        } else {
            return null;
        }
    }

    public DataElement[] getOriginKeyAttributes() throws JBOException {
        BizObjectKey var1 = new BizObjectKey(this.clazz);
        DataElement[] var2 = var1.getAttributes();

        for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3].setValue(this.getOriginalValue(var2[var3].getName()));
        }

        return var2;
    }
}
