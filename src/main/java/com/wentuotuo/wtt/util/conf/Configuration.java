//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.conf;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.util.xml.Document;
import com.wentuotuo.wtt.util.xml.Element;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Configuration {
    private Node rootNode = null;
    private String encoding;

    public Configuration() {
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String var1) {
        this.encoding = var1;
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    public void setRootNode(Node var1) {
        this.rootNode = var1;
    }

    public void loadFromFile(String var1) throws WTTException {
        FileInputStream var2 = null;

        try {
            this.loadFromStream(var2 = new FileInputStream(var1));
        } catch (FileNotFoundException var13) {
            WTT.getLog().error(var13);
            throw new WTTException(6, new String[]{var1});
        } catch (WTTException var14) {
            WTT.getLog().error(var14);
            throw new WTTException(8, new String[]{var1});
        } finally {
            if(var2 != null) {
                try {
                    var2.close();
                } catch (IOException var12) {
                    ;
                }
            }

        }

    }

    public void loadFromStream(InputStream var1) throws WTTException {
        try {
            Document var2 = new Document(var1);
            Element var3 = var2.getRootElement();
            this.encoding = var2.getEncoding();
            this.rootNode = new Node();
            this.rootNode.parseXMLNode(var3);
        } catch (Exception var4) {
            WTT.getLog().debug(var4);
            throw new WTTException(8, new String[]{"ERROR INPUT STREAM", var4.getMessage()});
        }
    }

    public void saveToStream(OutputStream var1, String var2) throws WTTException {
        Node var3 = this.getRootNode();
        if(var3 == null) {
            WTT.getLog().debug("No data exists in configuration,not saved!");
        } else {
            try {
                OutputStreamWriter var4 = new OutputStreamWriter(var1, var2);
                var4.write("<?xml version=\"1.0\" encoding=\"" + var2 + "\" ?>\n");
                StringBuffer var5 = new StringBuffer();
                var3.exportXMLString(var5, 0);
                var4.write(var5.toString());
                var4.flush();
            } catch (Exception var6) {
                WTT.getLog().debug(var6);
                throw new WTTException(10, new String[]{"ERROR INPUT STREAM"}, var6);
            }
        }
    }

    public void saveToFile(String var1, String var2) throws WTTException {
        FileOutputStream var3 = null;

        try {
            var3 = new FileOutputStream(var1);
            this.saveToStream(var3, var2);
            var3.close();
        } catch (FileNotFoundException var5) {
            WTT.getLog().debug(var5);
            throw new WTTException(9, new String[]{var1}, var5);
        } catch (WTTException var6) {
            WTT.getLog().debug(var6);
            throw new WTTException(10, new String[]{var1}, var6);
        } catch (IOException var7) {
            WTT.getLog().debug(var7);
        }

    }
}
