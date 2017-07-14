//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.util.ElementList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BizObjectClass implements Comparable, Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private BizObjectClass parent = null;
    private String packageName;
    private String name;
    private String label;
    private String absoluteName;
    private List attributes = new ArrayList();
    private Map uihints = null;
    private String[] briefAttributes = null;
    private String[] keyAttributes = null;
    private int extendNumber = 0;
    private ElementList extendProperties = null;
    private static AttributeUIHints stringHints = null;
    private static AttributeUIHints doubleHints = null;
    private static AttributeUIHints dateHints = null;
    private static AttributeUIHints intHints = null;
    private static AttributeUIHints longHints = null;
    private static AttributeUIHints booleanHints = null;

    public BizObjectClass(String var1, String var2) {
        this.packageName = var1.replaceAll("\\s+", "");
        this.name = var2.replaceAll("\\s+", "");
        this.absoluteName = this.packageName + "." + this.name;
    }

    public BizObjectClass(String var1) {
        String var2 = var1.replaceAll("\\s+", "");
        int var3 = var2.lastIndexOf(46);
        if(var3 > 0) {
            this.packageName = var2.substring(0, var3);
            this.name = var2.substring(var3 + 1);
        } else {
            this.packageName = null;
            this.name = var2;
        }

    }

    public final String getPackageName() {
        return this.packageName == null?this.getDefaultPackageName():this.packageName;
    }

    public final String getName() {
        return this.name;
    }

    public final String getAbsoluteName() {
        if(this.absoluteName == null) {
            this.absoluteName = this.getPackageName() + "." + this.getName();
        }

        return this.absoluteName;
    }

    public BizObjectClass getParent() {
        return this.parent;
    }

    public BizObjectClass getRoot() {
        return this.parent == null?this:this.parent.getRoot();
    }

    protected String getDefaultPackageName() {
        return JBOFactory.getFactory().getDefaultPackage();
    }

    public final String getLabel() {
        return this.label == null?this.getName():this.label;
    }

    public final void setLabel(String var1) {
        this.label = var1;
    }

    public int getAttributeNumber() {
        return this.attributes.size();
    }

    public DataElement[] getAttributes() {
        return (DataElement[])this.attributes.toArray(new DataElement[0]);
    }

    public DataElement getAttribute(String var1) {
        DataElement var2 = null;

        for(int var3 = 0; var3 < this.attributes.size(); ++var3) {
            DataElement var4 = (DataElement)this.attributes.get(var3);
            if(var4.getName().equalsIgnoreCase(var1)) {
                var2 = var4;
                break;
            }
        }

        return var2;
    }

    public String[] getKeyAttributes() {
        return this.keyAttributes;
    }

    public void setKeyAttributes(String[] var1) {
        this.keyAttributes = var1;
    }

    public void setKeyAttributes(String var1) {
        if(var1 == null) {
            this.keyAttributes = null;
        } else {
            String var2 = var1.replaceAll("([^,]\\s+)|(\\s+[$,])", "");
            this.keyAttributes = var2.split(",");
        }

    }

    public String[] getBriefAttributes() {
        return this.briefAttributes;
    }

    public void setBriefAttributes(String[] var1) {
        this.briefAttributes = var1;
    }

    public void setBriefAttributes(String var1) {
        if(var1 == null) {
            this.briefAttributes = null;
        } else {
            String var2 = var1.replaceAll("([^,]\\s+)|(\\s+[$,])", "");
            this.briefAttributes = var2.split(",");
        }

    }

    public void addAttribute(DataElement var1) {
        if(var1 != null && this.indexOfAttribute(var1.getName()) < 0) {
            this.attributes.add(var1);
        }
    }

    public ElementList getProperties() {
        if(this.extendProperties == null) {
            this.extendProperties = new ElementList(1);
        }

        return this.extendProperties;
    }

    public BizObjectClass extend(String var1, DataElement[] var2) {
        if(var1 == null) {
            return this.extend(var2);
        } else {
            BizObjectClass var3 = null;
            var3 = (BizObjectClass)this.clone();
            String var4 = var1.replaceAll("\\s+", "");
            int var5 = var4.lastIndexOf(46);
            if(var5 > 0) {
                var3.packageName = var4.substring(0, var5);
                var3.name = var4.substring(var5 + 1);
            } else {
                var3.name = var4;
            }

            var3.parent = this;
            var3.extendNumber = 0;

            for(int var6 = 0; var6 < var2.length; ++var6) {
                var3.addAttribute(var2[var6]);
            }

            return var3;
        }
    }

    public BizObjectClass extend(DataElement[] var1) {
        BizObjectClass var2 = null;
        var2 = (BizObjectClass)this.clone();
        ++this.extendNumber;
        var2.name = this.name + "X" + this.extendNumber;
        var2.parent = this;
        var2.extendNumber = 0;

        for(int var3 = 0; var3 < var1.length; ++var3) {
            var2.addAttribute(var1[var3]);
        }

        return var2;
    }

    public void setAttributeUIHints(String var1, AttributeUIHints var2) {
        if(this.uihints == null) {
            this.uihints = new HashMap();
        }

        this.uihints.put(var1, var2);
    }

    public AttributeUIHints getAttributeUIHint(String var1) {
        AttributeUIHints var2 = null;
        byte var3 = 0;
        if(this.uihints != null && var1 != null) {
            var2 = (AttributeUIHints)this.uihints.get(var1.toUpperCase());
        }

        if(var2 == null) {
            int var4 = this.indexOfAttribute(var1);
            if(var4 >= 0) {
                var3 = ((DataElement)this.attributes.get(var4)).getType();
            }

            var2 = this.getDefaultAttributeUIHint(var3);
        }

        return var2;
    }

    public int indexOfAttribute(String var1) {
        int var2 = -1;

        for(int var3 = 0; var3 < this.attributes.size(); ++var3) {
            if(((DataElement)this.attributes.get(var3)).getName().equalsIgnoreCase(var1)) {
                var2 = var3;
                break;
            }
        }

        return var2;
    }

    public String toString() {
        return this.getAbsoluteName();
    }

    public boolean equals(Object var1) {
        return var1 == null?false:var1 instanceof BizObjectClass && var1.toString().equals(this.toString());
    }

    public int compareTo(Object var1) {
        return this.toString().compareTo(var1.toString());
    }

    public Object clone() {
        BizObjectClass var1 = null;

        try {
            var1 = (BizObjectClass)super.clone();
            var1.absoluteName = null;
            var1.attributes = new ArrayList();
            var1.attributes.addAll(this.attributes);
        } catch (CloneNotSupportedException var3) {
            ;
        }

        return var1;
    }

    private AttributeUIHints getDefaultAttributeUIHint(int var1) {
        AttributeUIHints var2 = null;
        switch(var1) {
            case 0:
                if(stringHints == null) {
                    stringHints = createHints(0);
                }

                var2 = stringHints;
                break;
            case 1:
                if(intHints == null) {
                    intHints = createHints(1);
                }

                var2 = intHints;
                break;
            case 2:
                if(longHints == null) {
                    longHints = createHints(2);
                }

                var2 = longHints;
                break;
            case 4:
                if(doubleHints == null) {
                    doubleHints = createHints(4);
                }

                var2 = doubleHints;
                break;
            case 8:
                if(booleanHints == null) {
                    booleanHints = createHints(8);
                }

                var2 = booleanHints;
            case 3:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
                if(stringHints == null) {
                    stringHints = createHints(0);
                }

                var2 = stringHints;
                break;
            case 16:
                if(dateHints == null) {
                    dateHints = createHints(16);
                }

                var2 = dateHints;
        }

        return var2;
    }

    public static AttributeUIHints createHints(int var0) {
        AttributeUIHints var1 = new AttributeUIHints();
        switch(var0) {
            case 0:
            case 3:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
                break;
            case 1:
                var1.setDisplayFormat("###,##0");
                break;
            case 2:
                var1.setDisplayFormat("###,##0");
                break;
            case 4:
                var1.setDisplayFormat("###,##0.00");
                break;
            case 8:
                var1.setBoundValues(new Boolean(true), new Boolean(false));
                var1.setRequired(true);
                break;
            case 16:
                var1.setDisplayFormat("yyyy/MM/dd");
                var1.setInputMask("yyyy/MM/dd");
        }

        return var1;
    }
}
