//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.DataElement;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public class BizObject implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    public static final byte STATE_UNKNOWN = 0;
    public static final byte STATE_SYNC = 1;
    public static final byte STATE_NEW = 2;
    public static final byte STATE_CHANGED = 3;
    protected BizObjectClass clazz = null;
    protected DataElement[] attributes;
    protected String[] briefAttributes;

    public BizObject(BizObjectClass var1) {
        this.clazz = var1;
        this.initObject();
    }

    public BizObject(String var1) throws JBOException {
        this.clazz = JBOFactory.getFactory().getClass(var1);
        if(this.clazz == null) {
            throw new JBOException(1303, new String[]{var1});
        } else {
            this.initObject();
        }
    }

    protected void initObject() {
        DataElement[] var1 = this.clazz.getAttributes();
        this.attributes = new DataElement[var1.length];

        for(int var2 = 0; var2 < this.attributes.length; ++var2) {
            this.attributes[var2] = (DataElement)var1[var2].clone();
        }

        this.briefAttributes = this.clazz.getBriefAttributes();
    }

    public BizObjectClass getBizObjectClass() {
        return this.clazz;
    }

    private void assertAttributeAccesstIndex(int var1) throws JBOException {
        if(this.attributes == null) {
            throw new JBOException(1306, new Integer[]{new Integer(var1), new Integer(0), new Integer(0)});
        } else if(var1 < 0 || var1 >= this.attributes.length) {
            throw new JBOException(1306, new Integer[]{new Integer(var1), new Integer(0), new Integer(this.attributes.length - 1)});
        }
    }

    public DataElement getAttribute(int var1) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        return this.attributes[var1];
    }

    public DataElement getAttribute(String var1) throws JBOException {
        int var2 = this.indexOfAttribute(var1);
        if(var2 < 0) {
            throw new JBOException(1307, new String[]{this.clazz.getAbsoluteName(), var1});
        } else {
            return this.getAttribute(var2);
        }
    }

    public int getAttributeNumber() {
        return null == this.attributes?0:this.attributes.length;
    }

    public DataElement[] getAttributes() {
        return this.attributes;
    }

    public DataElement[] getBriefAttributes() throws JBOException {
        DataElement[] var1 = new DataElement[this.briefAttributes.length];

        for(int var2 = 0; var2 < var1.length; ++var2) {
            var1[var2] = this.getAttribute(this.briefAttributes[var2]);
        }

        return var1;
    }

    public BizObjectKey getKey() throws JBOException {
        BizObjectKey var1 = new BizObjectKey(this.clazz);
        DataElement[] var2 = var1.getAttributes();

        for(int var3 = 0; var3 < var2.length; ++var3) {
            if(!this.attributes[this.indexOfAttribute(var2[var3].getName())].isNull()) {
                var2[var3].setValue(this.attributes[this.indexOfAttribute(var2[var3].getName())]);
            }
        }

        return var1;
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
            WTT.getLog().debug(var3);
            return false;
        }
    }

    public int indexOfAttribute(String var1) {
        int var2 = -1;
        if(var1 != null && this.attributes != null) {
            for(int var3 = 0; var3 < this.attributes.length; ++var3) {
                if(this.attributes[var3].getName().equalsIgnoreCase(var1)) {
                    var2 = var3;
                    break;
                }
            }

            return var2;
        } else {
            return var2;
        }
    }

    public void setValue(BizObject var1) throws JBOException {
        if(var1 != null) {
            if(!var1.instanceOf(this.clazz)) {
                throw new JBOClassMismatchException(this.clazz, var1.clazz);
            } else {
                for(int var2 = 0; var2 < this.attributes.length; ++var2) {
                    this.attributes[var2].setValue(var1.getAttribute(this.attributes[var2].getName()));
                }

            }
        }
    }

    public void setAttributesValue(BizObject var1) {
        if(var1 != null) {
            for(int var2 = 0; var2 < this.attributes.length; ++var2) {
                try {
                    DataElement var3 = var1.getAttribute(this.attributes[var2].getName());
                    this.attributes[var2].setValue(var3);
                } catch (JBOException var4) {
                    ;
                }
            }

        }
    }

    public void setAttributesValue(Map var1) {
        if(var1 != null) {
            Iterator var2 = var1.keySet().iterator();

            while(var2.hasNext()) {
                Object var3 = var2.next();

                try {
                    DataElement var4 = this.getAttribute(var3.toString());
                    var4.setValue(var1.get(var3));
                } catch (JBOException var5) {
                    ;
                }
            }

        }
    }

    public BizObject setAttributeValue(String var1, int var2) throws JBOException {
        this.getAttribute(var1).setValue(var2);
        return this;
    }

    public BizObject setAttributeValue(String var1, long var2) throws JBOException {
        this.getAttribute(var1).setValue(var2);
        return this;
    }

    public BizObject setAttributeValue(String var1, double var2) throws JBOException {
        this.getAttribute(var1).setValue(var2);
        return this;
    }

    public BizObject setAttributeValue(String var1, boolean var2) throws JBOException {
        this.getAttribute(var1).setValue(var2);
        return this;
    }

    public BizObject setAttributeValue(String var1, Object var2) throws JBOException {
        this.getAttribute(var1).setValue(var2);
        return this;
    }

    public BizObject setAttributeValue(int var1, int var2) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        this.attributes[var1].setValue(var2);
        return this;
    }

    public BizObject setAttributeValue(int var1, long var2) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        this.attributes[var1].setValue(var2);
        return this;
    }

    public BizObject setAttributeValue(int var1, double var2) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        this.attributes[var1].setValue(var2);
        return this;
    }

    public BizObject setAttributeValue(int var1, boolean var2) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        this.attributes[var1].setValue(var2);
        return this;
    }

    public BizObject setAttributeValue(int var1, Object var2) throws JBOException {
        this.assertAttributeAccesstIndex(var1);
        this.attributes[var1].setValue(var2);
        return this;
    }

    public byte getState() {
        return 0;
    }

    public Object clone() {
        BizObject var1 = null;

        try {
            var1 = (BizObject)super.clone();
            if(this.attributes != null) {
                var1.attributes = new DataElement[this.attributes.length];

                for(int var2 = 0; var2 < this.attributes.length; ++var2) {
                    var1.attributes[var2] = (DataElement)this.attributes[var2].clone();
                }
            }
        } catch (CloneNotSupportedException var3) {
            ;
        }

        return var1;
    }
}
