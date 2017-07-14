//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.conf;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.util.xml.Attribute;
import com.wentuotuo.wtt.util.xml.Element;
import com.wentuotuo.wtt.util.xml.XmlTools;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node {
    private String name;
    private String value;
    private Attributes attributes = new Attributes();
    private Properties properties = new Properties();
    private List children = new ArrayList();
    private ArrayList comment;
    private boolean propertiesFirst = false;

    public Node() {
    }

    public Node(String var1) {
        this.name = var1;
    }

    public Node(String var1, String var2) {
        this.name = var1;
        this.value = var2;
    }

    public Node(String var1, String var2, String var3) {
        this.name = var1;
        this.value = var2;
        this.comment.add(var3);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String var1) {
        this.name = var1;
    }

    public boolean isPropertiesFirst() {
        return this.propertiesFirst;
    }

    public void setPropertiesFirst(boolean var1) {
        this.propertiesFirst = var1;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String var1) {
        this.value = var1;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Attributes var1) {
        this.attributes = var1;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void setProperties(Properties var1) {
        this.properties = var1;
    }

    public ArrayList getComment() {
        if(this.comment == null) {
            this.comment = new ArrayList();
        }

        return this.comment;
    }

    public void parseXMLNode(Element var1) {
        this.setName(var1.getName());

        try {
            List var2 = var1.getAttributeList();

            for(int var3 = 0; var3 < var2.size(); ++var3) {
                Attribute var4 = (Attribute)var2.get(var3);
                if(!StringX.isSpace(var4.getName())) {
                    this.attributes.addAttribute(var4.getName(), var4.getValue());
                }
            }

            this.setValue(var1.getTextTrim());
            this.getComment().clear();
            this.getComment().addAll(var1.getComment());
            List var9 = var1.getChildren();

            for(int var10 = 0; var10 < var9.size(); ++var10) {
                Element var5 = (Element)var9.get(var10);
                String var6 = var5.getName();
                if(!var6.equalsIgnoreCase("properties") && !var6.endsWith("Properties")) {
                    Node var7 = new Node();
                    var7.parseXMLNode(var5);
                    this.children.add(var7);
                } else {
                    this.properties.parseXMLNode(var5);
                    if(var10 == 0) {
                        this.setPropertiesFirst(true);
                    }
                }
            }
        } catch (Exception var8) {
            WTT.getLog().debug(var8);
        }

    }

    public void exportXMLString(StringBuffer var1, int var2) {
        int var3;
        int var5;
        for(var3 = 0; var3 < this.getComment().size(); ++var3) {
            if(this.getComment().get(var3) != null) {
                String var4 = this.getComment().get(var3).toString();
                if(!StringX.isSpace(var4)) {
                    for(var5 = 0; var5 < var2; ++var5) {
                        var1.append("    ");
                    }

                    var1.append("<!-- ");
                    var1.append(var4);
                    var1.append(" -->\n");
                }
            }
        }

        for(var3 = 0; var3 < var2; ++var3) {
            var1.append("    ");
        }

        var1.append("<").append(this.getName());
        Iterator var6 = this.attributes.getAttributes().iterator();

        while(var6.hasNext()) {
            DataElement var7 = (DataElement)var6.next();
            var1.append(" ").append(var7.getName()).append("=\"").append(XmlTools.replaceXmlSpecialChars(var7.getString())).append("\"");
        }

        if(this.children.size() == 0 && this.properties.size() == 0 && StringX.isSpace(XmlTools.replaceXmlSpecialChars(this.getValue()))) {
            var1.append("/>\n");
        } else {
            var1.append(">");
            if(this.getValue() != null) {
                var1.append(XmlTools.replaceXmlSpecialChars(this.getValue()));
            }

            if(this.children.size() == 0 && this.properties.size() == 0) {
                var1.append("</").append(this.getName()).append(">\n");
            } else {
                var1.append("\n");
                if(this.isPropertiesFirst() && this.properties != null && this.properties.size() > 0) {
                    this.properties.exportXMLString(var1, true, var2);
                }

                Iterator var8 = this.getChildren().iterator();

                while(var8.hasNext()) {
                    Node var9 = (Node)var8.next();
                    var9.exportXMLString(var1, var2 + 1);
                }

                if(!this.isPropertiesFirst() && this.properties != null && this.properties.size() > 0) {
                    this.properties.exportXMLString(var1, true, var2);
                }

                for(var5 = 0; var5 < var2; ++var5) {
                    var1.append("    ");
                }

                var1.append("</").append(this.getName()).append(">\n");
            }
        }
    }

    public List getChildren() {
        return this.children;
    }

    public List getChildren(String var1) {
        ArrayList var2 = new ArrayList();

        for(int var3 = 0; var3 < this.children.size(); ++var3) {
            Node var4 = (Node)this.children.get(var3);
            if(var4.getName().equals(var1)) {
                var2.add(var4);
            }
        }

        return var2;
    }

    public Node getChild(String var1) {
        Node var2 = null;

        for(int var3 = 0; var3 < this.children.size(); ++var3) {
            Node var4 = (Node)this.children.get(var3);
            if(var4.getName().equals(var1)) {
                var2 = var4;
                break;
            }
        }

        return var2;
    }

    public void setChildren(List var1) {
        if(var1 != null) {
            this.children = var1;
        }

    }

    public Node getChild(int var1) {
        return var1 >= 0 && var1 <= this.children.size()?(Node)this.children.get(var1):null;
    }

    public void addChild(Node var1) {
        this.children.add(var1);
    }

    public void addChild(int var1, Node var2) {
        int var3 = this.children.indexOf(var2);
        if(var3 >= 0) {
            this.children.remove(var3);
        }

        if(var1 < 0) {
            var1 = 0;
        }

        if(var1 >= this.children.size()) {
            var1 = this.children.size();
        }

        this.children.add(var1, var2);
    }

    public int indexOf(Node var1) {
        return this.children.indexOf(var1);
    }

    public void removeChild(int var1) {
        if(var1 >= 0 && var1 <= this.children.size()) {
            this.children.remove(var1);
        }
    }

    public void removeChild(Node var1) {
        this.children.remove(var1);
    }

    public void clear() {
        this.name = null;
        this.value = null;
        this.attributes.clear();
        this.properties.clear();
        this.children.clear();
        this.comment = null;
        this.propertiesFirst = false;
    }
}
