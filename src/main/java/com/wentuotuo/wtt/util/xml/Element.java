//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.xml;

import com.wentuotuo.wtt.lang.StringX;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Element {
    private org.w3c.dom.Element domElement;

    public Element(org.w3c.dom.Element var1) {
        this.domElement = var1;
    }

    public org.w3c.dom.Element getDomElement() {
        return this.domElement;
    }

    public String getName() {
        return this.getDomElement().getTagName();
    }

    public ArrayList getComment() {
        Node var1 = this.getDomElement().getPreviousSibling();

        ArrayList var2;
        for(var2 = new ArrayList(); var1 != null && var1.getNodeType() != 1; var1 = var1.getPreviousSibling()) {
            if(var1.getNodeType() == 8) {
                String[] var3 = var1.getNodeValue().split("\n");
                ArrayList var4 = new ArrayList();

                for(int var5 = 0; var5 < var3.length; ++var5) {
                    var3[var5] = var3[var5].trim();
                    if(!StringX.isSpace(var3[var5])) {
                        var4.add(var3[var5]);
                    }
                }

                var2.addAll(0, var4);
            }
        }

        return var2;
    }

    public String getTextTrim() {
        Node var1 = this.getTextNode(this.getDomElement());
        return var1 != null?var1.getNodeValue().trim():null;
    }

    public String getChildTextTrim(String var1) {
        Element var2 = this.getChild(var1);
        return var2 == null?null:var2.getTextTrim();
    }

    private Node getTextNode(Node var1) {
        NodeList var2 = var1.getChildNodes();

        for(int var3 = 0; var3 < var2.getLength(); ++var3) {
            Node var4 = var2.item(var3);
            if(var4.getNodeType() == 3) {
                return var4;
            }
        }

        return null;
    }

    public Element getChild(String var1) {
        Element var2 = null;
        NodeList var3 = this.getDomElement().getChildNodes();

        for(int var4 = 0; var4 < var3.getLength(); ++var4) {
            Node var5 = var3.item(var4);
            if(var5.getNodeType() == 1 && var5.getNodeName().equals(var1)) {
                var2 = new Element((org.w3c.dom.Element)var5);
                break;
            }
        }

        return var2;
    }

    public List getNodeList(String var1) {
        return this.getChildren(var1);
    }

    public List getChildren(String var1) {
        ArrayList var2 = new ArrayList();
        NodeList var3 = this.getDomElement().getChildNodes();

        for(int var4 = 0; var4 < var3.getLength(); ++var4) {
            Node var5 = var3.item(var4);
            if(var5.getNodeType() == 1 && var5.getNodeName().equals(var1)) {
                var2.add(new Element((org.w3c.dom.Element)var5));
            }
        }

        return var2;
    }

    public List getChildren() {
        ArrayList var1 = new ArrayList();
        NodeList var2 = this.getDomElement().getChildNodes();

        for(int var3 = 0; var3 < var2.getLength(); ++var3) {
            Node var4 = var2.item(var3);
            if(var4.getNodeType() == 1) {
                var1.add(new Element((org.w3c.dom.Element)var4));
            }
        }

        return var1;
    }

    public String getAttributeValue(String var1) {
        return this.getAttributeValue(var1, (String)null);
    }

    public String getAttributeValue(String var1, String var2) {
        String var3 = this.getDomElement().getAttribute(var1);
        return var3.equals("")?var2:var3;
    }

    public Attribute getAttribute(String var1) {
        Attr var2 = this.getDomElement().getAttributeNode(var1);
        return var2 == null?null:new Attribute(var1, var2.getValue());
    }

    public List getAttributeList() throws Exception {
        ArrayList var1 = new ArrayList();
        NamedNodeMap var2 = this.getDomElement().getAttributes();

        for(int var3 = 0; var3 < var2.getLength(); ++var3) {
            var1.add(new Attribute(var2.item(var3).getNodeName(), var2.item(var3).getNodeValue()));
        }

        return var1;
    }
}
