//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.conf;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.util.xml.Document;
import com.wentuotuo.wtt.util.xml.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class XMLFileLoader implements ConfigurationLoader {
    private String fileName = null;

    public XMLFileLoader() {
    }

    public String getFileName() {
        return this.fileName;
    }

    public Configuration loadConfiguration(Object var1) throws WTTException {
        try {
            File var2 = null;
            if(var1 instanceof String) {
                var2 = new File((String)var1);
            } else {
                if(!(var1 instanceof File)) {
                    throw new WTTException(8, new String[]{"ERROR LOAD SOURCE", var1.toString()});
                }

                var2 = (File)var1;
            }

            this.fileName = var2.getAbsolutePath();
            Document var3 = new Document(var2);
            Element var4 = var3.getRootElement();
            Configuration var5 = new Configuration();
            var5.setEncoding(var3.getEncoding());
            Node var6 = new Node();
            var6.parseXMLNode(var4);
            var5.setRootNode(var6);
            return var5;
        } catch (Exception var7) {
            WTT.getLog().debug(var7);
            throw new WTTException(8, new String[]{"ERROR LOAD CONFIGURATION", var7.getMessage()});
        }
    }

    public void saveConfiguration(Configuration var1, Object var2) throws WTTException {
        File var3 = null;
        if(var2 instanceof String) {
            var3 = new File((String)var2);
        } else {
            if(!(var2 instanceof File)) {
                throw new WTTException(8, new String[]{"ERROR SAVE TARGET", var2.toString()});
            }

            var3 = (File)var2;
        }

        Node var4 = var1.getRootNode();
        if(var4 == null) {
            WTT.getLog().debug("No data exists in configuration,not saved!");
        } else {
            try {
                OutputStreamWriter var5 = new OutputStreamWriter(new FileOutputStream(var3), var1.getEncoding());
                var5.write("<?xml version=\"1.0\" encoding=\"" + var1.getEncoding() + "\" ?>\n");
                StringBuffer var6 = new StringBuffer();
                var4.exportXMLString(var6, 0);
                var5.write(var6.toString());
                var5.flush();
                var5.close();
            } catch (Exception var7) {
                WTT.getLog().debug(var7);
                throw new WTTException(10, new String[]{"ERROR SAVE CONFIGURATION"}, var7);
            }
        }
    }
}
