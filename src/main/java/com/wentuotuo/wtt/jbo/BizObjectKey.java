//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import com.wentuotuo.wtt.lang.DataElement;
import java.io.Serializable;

public class BizObjectKey implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private BizObjectClass clazz = null;
    private DataElement[] keyAttributes = null;

    public BizObjectKey(BizObjectClass var1) throws JBOException {
        this.clazz = var1;
        this.initKey();
    }

    public BizObjectKey(String var1) throws JBOException {
        this.clazz = JBOFactory.getFactory().getClass(var1);
        this.initKey();
    }

    private void assertAttributeAccesstIndex(int var1) throws JBOException {
        if(this.keyAttributes == null) {
            throw new JBOException(1310, new String[]{this.clazz.getAbsoluteName()});
        } else if(var1 < 0 || var1 >= this.keyAttributes.length) {
            throw new JBOException(1308, new Integer[]{new Integer(var1), new Integer(0), new Integer(this.keyAttributes.length - 1)});
        }
    }

    public DataElement getAttribute(String var1) throws JBOException {
        if(this.keyAttributes == null) {
            new JBOException(1309, new String[]{this.clazz.getAbsoluteName(), var1});
        }

        DataElement var2 = null;

        for(int var3 = 0; var3 < this.keyAttributes.length; ++var3) {
            if(this.keyAttributes[var3].getName().equalsIgnoreCase(var1)) {
                var2 = this.keyAttributes[var3];
                break;
            }
        }

        if(var2 == null) {
            new JBOException(1309, new String[]{this.clazz.getAbsoluteName(), var1});
        }

        return var2;
    }

    public DataElement getAttribute(int var1) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        DataElement var2 = this.keyAttributes[var1];
        return var2;
    }

    public int getAttributeNumber() {
        return this.keyAttributes == null?0:this.keyAttributes.length;
    }

    public DataElement[] getAttributes() {
        return this.keyAttributes;
    }

    public Object clone() {
        BizObjectKey var1 = null;

        try {
            var1 = (BizObjectKey)super.clone();
            if(this.keyAttributes != null) {
                for(int var2 = 0; var2 < this.keyAttributes.length; ++var2) {
                    var1.keyAttributes[var2] = (DataElement)this.keyAttributes[var2].clone();
                }
            }
        } catch (CloneNotSupportedException var3) {
            ;
        }

        return var1;
    }

    public BizObjectClass getBizObjectClass() {
        return this.clazz;
    }

    protected void initKey() throws JBOException {
        String[] var1 = this.clazz.getKeyAttributes();
        if(var1 == null) {
            throw new JBOException(1310, new String[]{this.clazz.getAbsoluteName()});
        } else {
            this.keyAttributes = new DataElement[var1.length];

            for(int var2 = 0; var2 < this.keyAttributes.length; ++var2) {
                this.keyAttributes[var2] = (DataElement)this.clazz.getAttribute(var1[var2]).clone();
            }

        }
    }

    public BizObjectKey setAttributeValue(String var1, int var2) throws JBOException {
        this.getAttribute(var1).setValue(var2);
        return this;
    }

    public BizObjectKey setAttributeValue(String var1, long var2) throws JBOException {
        this.getAttribute(var1).setValue(var2);
        return this;
    }

    public BizObjectKey setAttributeValue(String var1, double var2) throws JBOException {
        this.getAttribute(var1).setValue(var2);
        return this;
    }

    public BizObjectKey setAttributeValue(String var1, boolean var2) throws JBOException {
        this.getAttribute(var1).setValue(var2);
        return this;
    }

    public BizObjectKey setAttributeValue(String var1, Object var2) throws JBOException {
        this.getAttribute(var1).setValue(var2);
        return this;
    }

    public BizObjectKey setAttributeValue(int var1, int var2) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        this.keyAttributes[var1].setValue(var2);
        return this;
    }

    public BizObjectKey setAttributeValue(int var1, long var2) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        this.keyAttributes[var1].setValue(var2);
        return this;
    }

    public BizObjectKey setAttributeValue(int var1, double var2) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        this.keyAttributes[var1].setValue(var2);
        return this;
    }

    public BizObjectKey setAttributeValue(int var1, boolean var2) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        this.keyAttributes[var1].setValue(var2);
        return this;
    }

    public BizObjectKey setAttributeValue(int var1, Object var2) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        this.keyAttributes[var1].setValue(var2);
        return this;
    }

    public BizObjectKey setAttributesValue(Object[] var1) throws JBOException {
        int var2 = var1 == null?-1:var1.length;
        this.assertAttributeAccesstIndex(var2 - 1);

        for(int var3 = 0; var3 < var2; ++var3) {
            this.keyAttributes[var3].setValue(var1[var3]);
        }

        return this;
    }

    public boolean instanceOf(BizObjectClass var1) {
        BizObjectClass var2;
        for(var2 = this.clazz; var2 != null && !var2.equals(var1); var2 = var2.getParent()) {
            ;
        }

        return var2 != null;
    }

    public boolean instanceOf(String var1) {
        try {
            BizObjectClass var2 = JBOFactory.getFactory().getClass(var1);
            return this.instanceOf(var2);
        } catch (JBOException var3) {
            return false;
        }
    }
}
