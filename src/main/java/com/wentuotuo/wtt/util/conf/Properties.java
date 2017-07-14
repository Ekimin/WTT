//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.conf;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.sql.SQLConstants;
import com.wentuotuo.wtt.util.xml.Element;
import com.wentuotuo.wtt.util.xml.XmlTools;
import java.util.ArrayList;
import java.util.List;

public class Properties {
    private List properties = new ArrayList();
    private String collectionXMLTag = "extendProperties";
    private String propertyXMLTag = "property";
    private ArrayList comment;

    public Properties() {
    }

    public void addProperty(DataElement var1) {
        int var2 = this.indexOf(var1);
        if(var2 < 0) {
            this.properties.add(var1);
        } else {
            this.properties.remove(var2);
            this.properties.add(var2, var1);
        }

    }

    public void addProperty(int var1, DataElement var2) {
        int var3 = this.indexOf(var2);
        if(var3 >= 0) {
            this.properties.remove(var3);
        }

        if(var1 < 0) {
            var1 = 0;
        }

        if(var1 >= this.properties.size()) {
            var1 = this.properties.size();
        }

        this.properties.add(var1, var2);
    }

    public void addProperty(String var1, String var2) {
        if(!StringX.isSpace(var1)) {
            this.properties.add(DataElement.valueOf(StringX.trimAll(var1), var2));
        }

    }

    public void addProperty(int var1, String var2, String var3) {
        if(!StringX.isSpace(var2)) {
            this.properties.add(var1, DataElement.valueOf(StringX.trimAll(var2), var3));
        }

    }

    public int size() {
        return this.properties.size();
    }

    public int indexOf(String var1) {
        int var2;
        for(var2 = this.properties.size() - 1; var2 >= 0; --var2) {
            DataElement var3 = (DataElement)this.properties.get(var2);
            if(var3.getName().equals(var1)) {
                break;
            }
        }

        return var2;
    }

    public int indexOf(DataElement var1) {
        return this.indexOf(var1.getName());
    }

    public DataElement getProperty(int var1) {
        return var1 >= 0 && var1 < this.properties.size()?(DataElement)this.properties.get(var1):null;
    }

    public DataElement getProperty(String var1) {
        return this.getProperty(this.indexOf(var1));
    }

    public DataElement getProperty(String var1, String var2) {
        int var3 = this.indexOf(var1);
        return var3 < 0?DataElement.valueOf(var1, var2):this.getProperty(var3);
    }

    public void removeProperty(int var1) {
        if(var1 >= 0 && var1 < this.properties.size()) {
            this.properties.remove(var1);
        }

    }

    public void removeProperty(String var1) {
        this.removeProperty(this.indexOf(var1));
    }

    public void removeProperty(DataElement var1) {
        this.removeProperty(this.indexOf(var1));
    }

    public void clear() {
        this.properties.clear();
    }

    public List getProperties() {
        return this.properties;
    }

    public java.util.Properties getAsProperties(boolean var1) {
        java.util.Properties var2 = new java.util.Properties();

        for(int var3 = 0; var3 < this.properties.size(); ++var3) {
            DataElement var4 = (DataElement)this.properties.get(var3);
            String var5 = var4.getString();
            if(var5 != null && var1) {
                var5 = WTT.replaceWTTTags(var5);
            }

            var2.put(var4.getName(), var5);
        }

        return var2;
    }

    public void parseXMLNode(Element var1) {
        if(var1 != null) {
            this.clear();
            String var2 = var1.getName();
            this.setCollectionXMLTag(var2);
            this.getComment().clear();
            this.getComment().addAll(var1.getComment());
            if(Character.isUpperCase(var2.charAt(0))) {
                this.setPropertyXMLTag("Property");
            }

            List var3 = var1.getChildren(this.getPropertyXMLTag());
            Element var4 = null;
            String var5 = null;
            String var6 = null;

            for(int var7 = 0; var7 < var3.size(); ++var7) {
                var4 = (Element)var3.get(var7);
                var5 = var4.getAttributeValue("name");
                if(!StringX.isSpace(var5)) {
                    var6 = var4.getAttributeValue("value", "");
                    byte var8 = SQLConstants.getBaseDataType(var4.getAttributeValue("type", "STRING"));
                    DataElement var9 = new DataElement(var5, var8);
                    this.getComment().addAll(var4.getComment());
                    var9.setValue(var6);
                    this.addProperty(var9);
                }
            }

        }
    }

    public void exportXMLString(StringBuffer var1, boolean var2, int var3) {
        int var4;
        int var6;
        for(var4 = 0; var4 < this.getComment().size(); ++var4) {
            if(this.getComment().get(var4) != null) {
                String var5 = this.getComment().get(var4).toString();
                if(!StringX.isSpace(var5)) {
                    for(var6 = 0; var6 < var3 + 1; ++var6) {
                        var1.append("    ");
                    }

                    var1.append("<!-- ");
                    var1.append(var5);
                    var1.append(" -->\n");
                }
            }
        }

        for(var4 = 0; var4 < var3 + 1; ++var4) {
            var1.append("    ");
        }

        var1.append("<").append(this.getCollectionXMLTag()).append(">\n");

        for(var4 = 0; var4 < this.properties.size(); ++var4) {
            DataElement var7 = (DataElement)this.properties.get(var4);

            for(var6 = 0; var6 < var3 + 2; ++var6) {
                var1.append("    ");
            }

            var1.append("<").append(this.getPropertyXMLTag()).append(" name=\"").append(var7.getName()).append("\"").append(" value=\"").append(XmlTools.replaceXmlSpecialChars(var7.getString())).append("\"");
            if(var2) {
                var1.append(" type=\"").append(SQLConstants.getBaseDataTypeName(var7.getType())).append("\"");
            }

            var1.append("/>\n");
        }

        for(var4 = 0; var4 < var3 + 1; ++var4) {
            var1.append("    ");
        }

        var1.append("</").append(this.getCollectionXMLTag()).append(">\n");
    }

    public String getCollectionXMLTag() {
        return this.collectionXMLTag;
    }

    public void setCollectionXMLTag(String var1) {
        this.collectionXMLTag = var1;
    }

    public String getPropertyXMLTag() {
        return this.propertyXMLTag;
    }

    public void setPropertyXMLTag(String var1) {
        this.propertyXMLTag = var1;
    }

    public ArrayList getComment() {
        if(this.comment == null) {
            this.comment = new ArrayList();
        }

        return this.comment;
    }
}
