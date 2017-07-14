//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Document {
    private org.w3c.dom.Document xmlDoc;
    private String encoding;
    private String documentName;
    private Element domRootNode;
    private com.wentuotuo.wtt.util.xml.Element rootElement;
    private DocumentBuilderFactory factory;
    private final int MaxBackupFileCount;

    public Document(String var1) throws Exception {
        this(var1, (InputStream)(new FileInputStream(var1)));
    }

    public Document(File var1) throws Exception {
        this(var1.getAbsolutePath(), (InputStream)(new FileInputStream(var1)));
    }

    public Document(InputStream var1) throws Exception {
        this((String)null, (InputStream)var1);
    }

    public Document(String var1, InputStream var2) throws Exception {
        this.factory = DocumentBuilderFactory.newInstance();
        this.MaxBackupFileCount = 5;
        this.documentName = var1;

        try {
            DocumentBuilder var3 = this.factory.newDocumentBuilder();
            this.xmlDoc = var3.parse(var2);

            try {
                this.encoding = (String)this.xmlDoc.getClass().getMethod("getXmlEncoding", new Class[0]).invoke(this.xmlDoc, new Object[0]);
            } catch (Exception var5) {
                ;
            }

            this.domRootNode = this.xmlDoc.getDocumentElement();
            this.rootElement = new com.wentuotuo.wtt.util.xml.Element(this.domRootNode);
        } catch (SAXParseException var6) {
            throw var6;
        } catch (SAXException var7) {
            throw var7;
        } catch (ParserConfigurationException var8) {
            throw var8;
        } catch (IOException var9) {
            throw var9;
        }
    }

    public Document(org.w3c.dom.Document var1) {
        this((String)null, (org.w3c.dom.Document)var1);
    }

    public Document(String var1, org.w3c.dom.Document var2) {
        this.factory = DocumentBuilderFactory.newInstance();
        this.MaxBackupFileCount = 5;
        this.xmlDoc = var2;
        this.domRootNode = this.xmlDoc.getDocumentElement();
        this.rootElement = new com.wentuotuo.wtt.util.xml.Element(this.domRootNode);
    }

    public org.w3c.dom.Document getDomDocument() {
        return this.xmlDoc;
    }

    public String getEncoding() {
        if(this.encoding == null) {
            this.encoding = "GB18030";
        }

        return this.encoding;
    }

    public void setEncoding(String var1) {
        this.encoding = var1;
    }

    public String getDocumentName() {
        return this.documentName;
    }

    public Node getRootNode() {
        return this.domRootNode;
    }

    public com.wentuotuo.wtt.util.xml.Element getRootElement() {
        return this.rootElement;
    }

    public Node getNode(String var1, String var2, String var3, Node var4) throws Exception {
        NodeList var5 = var4.getChildNodes();

        for(int var6 = 0; var6 < var5.getLength(); ++var6) {
            Node var7 = var5.item(var6);
            if(var7.getNodeName().trim().equalsIgnoreCase(var1) && var7.getNodeType() == 1 && var7.getAttributes().getLength() > 0 && var7.getAttributes().getNamedItem(var2).getNodeValue().trim().equalsIgnoreCase(var3)) {
                return var7;
            }

            if(var7.getChildNodes().getLength() > 0) {
                Node var8 = this.getNode(var1, var2, var3, var7);
                if(var8 != null) {
                    return var8;
                }
            }
        }

        return null;
    }

    public String getNodeValue(Node var1) throws Exception {
        String var2 = "";
        NodeList var3 = var1.getChildNodes();

        for(int var4 = 0; var4 < var3.getLength(); ++var4) {
            Node var5 = var3.item(var4);
            if(var5.getNodeType() == 3 && var5.getNodeValue().trim().length() > 0) {
                return var5.getNodeValue();
            }
        }

        return var2;
    }

    public void setNodeValue(Node var1, String var2) throws Exception {
        NodeList var3 = var1.getChildNodes();
        if(var3.getLength() == 0) {
            Node var4 = this.getTextNode(this.getRootNode()).cloneNode(false);
            var4.setNodeValue(var2);
            var1.appendChild(var4);
        }

        for(int var6 = 0; var6 < var3.getLength(); ++var6) {
            Node var5 = var3.item(var6);
            if(var5.getNodeType() == 3) {
                var5.setNodeValue(var2);
                return;
            }
        }

    }

    public Node getTextNode(Node var1) throws Exception {
        NodeList var2 = var1.getChildNodes();

        for(int var3 = 0; var3 < var2.getLength(); ++var3) {
            Node var4 = var2.item(var3);
            if(var4.getNodeType() == 3) {
                return var4;
            }

            if(var4.getChildNodes().getLength() > 0) {
                Node var5 = this.getTextNode(var4);
                if(var5 != null) {
                    return var5;
                }
            }
        }

        return null;
    }

    public String printDOMTree(Node var1) {
        String var2 = "";
        short var3 = var1.getNodeType();
        switch(var3) {
            case 1:
                var2 = var2 + "<";
                var2 = var2 + var1.getNodeName();
                NamedNodeMap var8 = var1.getAttributes();

                for(int var5 = 0; var5 < var8.getLength(); ++var5) {
                    Node var6 = var8.item(var5);
                    var2 = var2 + " " + var6.getNodeName() + "=\"" + var6.getNodeValue() + "\"";
                }

                var2 = var2 + ">";
                NodeList var9 = var1.getChildNodes();
                if(var9 != null) {
                    int var10 = var9.getLength();

                    for(int var7 = 0; var7 < var10; ++var7) {
                        var2 = var2 + this.printDOMTree(var9.item(var7));
                    }
                }
            case 2:
            case 6:
            case 8:
            default:
                break;
            case 3:
                var2 = var2 + var1.getNodeValue();
                break;
            case 4:
                var2 = var2 + "<![CDATA[";
                var2 = var2 + var1.getNodeValue();
                var2 = var2 + "]]>";
                break;
            case 5:
                var2 = var2 + "&";
                var2 = var2 + var1.getNodeName();
                var2 = var2 + ";";
                break;
            case 7:
                var2 = var2 + "<?";
                var2 = var2 + var1.getNodeName();
                String var4 = var1.getNodeValue();
                var2 = var2 + " ";
                var2 = var2 + var4;
                var2 = var2 + "?>";
                break;
            case 9:
                var2 = var2 + "<?xml version='1.0' encoding='ISO8859-1'?>";
                var2 = var2 + this.printDOMTree(((org.w3c.dom.Document)var1).getDocumentElement());
        }

        if(var3 == 1) {
            var2 = var2 + "";
            var2 = var2 + "</";
            var2 = var2 + var1.getNodeName();
            var2 = var2 + '>';
        }

        return var2;
    }

    public void backup() throws Exception {
        int var1 = -1;
        long var2 = 0L;

        for(int var4 = 0; var4 < 5; ++var4) {
            File var5 = new File(this.getDocumentName() + ".00" + var4);
            var5.lastModified();
            if(var5.lastModified() > var2) {
                var2 = var5.lastModified();
                var1 = var4;
            }
        }

        ++var1;
        if(var1 > 4) {
            var1 = 0;
        }

        this.writeToFile(this.getDocumentName() + ".00" + var1, this.getXmlFileToString().trim());
    }

    public String getXmlFileToString() throws Exception {
        FileInputStream var1 = new FileInputStream(this.getDocumentName());
        InputStreamReader var2 = new InputStreamReader(var1);
        char[] var3 = new char[var1.available()];
        var2.read(var3);
        var2.close();
        return new String(var3);
    }

    public void writeToFile(String var1, String var2) throws Exception {
        FileWriter var3 = new FileWriter(var1);

        for(int var4 = 0; var4 < var2.length(); ++var4) {
            var3.write(var2.charAt(var4));
        }

        var3.close();
    }
}
