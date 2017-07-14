//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.impl;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.io.FileFilterByName;
import com.wentuotuo.wtt.jbo.AttributeUIHints;
import com.wentuotuo.wtt.jbo.BizObjectClass;
import com.wentuotuo.wtt.jbo.BizObjectPackage;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.sql.SQLConstants;
import com.wentuotuo.wtt.util.conf.Attributes;
import com.wentuotuo.wtt.util.conf.Configuration;
import com.wentuotuo.wtt.util.conf.Node;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class DefaultXMLJBOFactory extends DefaultJBOFactory {
    private String configFile = null;

    public DefaultXMLJBOFactory() {
    }

    public String getConfigFile() {
        return this.configFile;
    }

    public void setConfigFile(String var1) {
        this.configFile = var1;
    }

    protected void loadFactoryPool(Map var1, Map var2, Map var3) throws WTTException {
        String var4 = this.configFile;
        if (var4 != null) {
            String[] var5 = var4.split("[,，]");

            for (int var6 = 0; var6 < var5.length; ++var6) {
                if (!StringX.isSpace(var5[var6])) {
                    File[] var7 = this.listFiles(StringX.trimAll(var5[var6]));
                    if (var7 != null && var7.length >= 1) {
                        for (int var8 = 0; var8 < var7.length; ++var8) {
                            this.loadFromXML(var7[var8].getAbsolutePath(), var1, var2, var3);
                        }
                    } else {
                        WTT.getLog().warn("No satisfied files of [" + var5[var6] + "]");
                    }
                }
            }
        }

    }

    protected File[] listFiles(String var1) {
        File var2 = new File(var1);
        String var3 = var2.getParent();
        File var4 = new File(var3);
        FileFilterByName var5 = new FileFilterByName();
        var5.setDirectoryInclude(false);
        String var6 = var2.getName().replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
        var5.setNamePattern(var6);
        return var4.listFiles((FilenameFilter) var5);
    }

    private void loadFromXML(String var1, Map var2, Map var3, Map var4) throws WTTException {
        Configuration var5 = new Configuration();
        var5.loadFromFile(var1);
        Node var6 = var5.getRootNode();
        Node var7 = var6.getChild("managers");
        Iterator var8;
        Node var9;
        if (var7 != null) {
            var8 = var7.getChildren("manager").iterator();

            while (var8.hasNext()) {
                var9 = (Node) var8.next();
                ManagerInfo var10 = this.buildManagerInfo(var9);
                if (var10 != null) {
                    var3.put(var10.getManagerId(), var10);
                }
            }
        }

        var8 = var6.getChildren("package").iterator();

        while (var8.hasNext()) {
            var9 = (Node) var8.next();
            this.buildClasses(var9, var2, var4);
        }

    }

    private ManagerInfo buildManagerInfo(Node var1) {
        ManagerInfo var2 = new ManagerInfo();
        Attributes var3 = var1.getAttributes();
        String var4 = var3.getAttribute("managerClass", (String) null).getString();
        if (var4 == null) {
            WTT.getLog().error("managerClass must provid!");
            return null;
        } else {
            var2.setManagerId(var3.getAttribute("id", var4).getString());
            var2.setManagerClass(var4);
            Node var5 = null;
            if ((var5 = var1.getChild("describe")) != null) {
                var2.setManagerDescribe(var5.getValue());
            }

            var2.getManagerProperties().putAll(var1.getProperties().getAsProperties(true));
            return var2;
        }
    }

    private void buildClasses(Node var1, Map var2, Map var3) {
        String var4 = var1.getAttributes().getAttribute("name", (String) null).getString();
        if (var4 == null) {
            WTT.getLog().error(new WTTException(15, new String[]{"package", "name"}));
        } else {
            var2.put(var4, new BizObjectPackage(var4, var1.getAttributes().getAttribute("label", (String) null).getString()));
            Iterator var5 = var1.getChildren("class").iterator();

            while (var5.hasNext()) {
                Node var6 = (Node) var5.next();
                BizObjectClass var7 = this.buildClass(var6, var4);
                if (var7 != null) {
                    var3.put(var7.getAbsoluteName(), var7);
                    Node var8 = var6.getChild("manager");
                    if (var8 != null) {
                        Properties var9 = this.buildRefManagerInfo(var8);
                        if (var9 != null) {
                            this.setClassManager(var7.getAbsoluteName(), var9);
                        }
                    }
                }
            }

        }
    }

    private BizObjectClass buildClass(Node var1, String var2) {
        Attributes var3 = var1.getAttributes();
        String var4 = var3.getAttribute("name", (String) null).getString();
        if (var4 == null) {
            WTT.getLog().error(new WTTException(15, new String[]{var1.getName(), "name"}));
            return null;
        } else {
            BizObjectClass var5 = new BizObjectClass(var2, var4);
            var5.setLabel(var3.getAttribute("label", (String) null).getString());
            var5.setKeyAttributes(var3.getAttribute("keyAttributes", (String) null).getString());
            boolean var6 = true;
            int var13;
            if ((var13 = var3.indexOf("briefAttributes")) >= 0) {
                var5.setBriefAttributes(var3.getAttribute(var13).getString());
            }

            Node var7 = var1.getChild("attributes");
            if (var7 == null) {
                WTT.getLog().error(new WTTException(14, new String[]{"attributes@" + var4}));
                return null;
            } else {
                Iterator var8 = var7.getChildren("attribute").iterator();

                while (var8.hasNext()) {
                    Node var9 = (Node) var8.next();
                    DataElement var10 = this.buildAttribute(var9);
                    if (var10 != null) {
                        var5.addAttribute(var10);
                        Node var11 = var9.getChild("uiHints");
                        if (var11 != null) {
                            AttributeUIHints var12 = this.buildUIHints(var11, var10);
                            if (var12 != null) {
                                var5.setAttributeUIHints(var10.getName(), var12);
                            }
                        }
                    }
                }

                return var5;
            }
        }
    }

    private DataElement buildAttribute(Node var1) {
        Attributes var2 = var1.getAttributes();
        DataElement var3 = new DataElement(var2.getAttribute("name", "").getString(), SQLConstants.getBaseDataType(var2.getAttribute("type", "STRING").getString()));
        var3.setLabel(var2.getAttribute("label", (String) null).getString());

        try {
            var3.setLength(var2.getAttribute("length", "20").getInt());
        } catch (NumberFormatException var6) {
            var3.setLength(20);
            WTT.getLog().debug("Invalid length of column " + var3.getName() + ", set to default 20 !", var6);
        }

        try {
            var3.setScale(var2.getAttribute("scale", "2").getInt());
        } catch (NumberFormatException var7) {
            if (var3.getType() == 4) {
                var3.setScale(2);
                WTT.getLog().debug("Invalid scale of column " + var3.getName() + ",set to default 2 ! ", var7);
            }
        }

        Iterator var4 = var1.getProperties().getProperties().iterator();

        while (var4.hasNext()) {
            DataElement var5 = (DataElement) var4.next();
            var3.setProperty(var5.getName(), var5.getString());
        }

        return var3;
    }

    private AttributeUIHints buildUIHints(Node var1, DataElement var2) {
        Attributes var3 = var1.getAttributes();
        AttributeUIHints var4 = BizObjectClass.createHints(var2.getType());
        boolean var5 = true;
        String var6 = null;
        int var12;
        if ((var12 = var3.indexOf("valueCharacter")) >= 0) {
            var6 = var3.getAttribute(var12).getString();
        }

        if (var6 != null) {
            var6 = StringX.trimAll(var6);
            if (var6.equalsIgnoreCase("boolean")) {
                var4.setValueCharacter(4);
            } else if (var6.equalsIgnoreCase("unlimited")) {
                var4.setValueCharacter(0);
            } else if (var6.equalsIgnoreCase("codetable")) {
                var4.setValueCharacter(1);
            } else if (var6.equalsIgnoreCase("range")) {
                var4.setValueCharacter(3);
            }
        }

        if ((var12 = var3.indexOf("inputMask")) >= 0) {
            var4.setInputMask(StringX.trimAll(var3.getAttribute(var12).getString()));
        }

        if ((var12 = var3.indexOf("displayFormat")) >= 0) {
            var4.setDisplayFormat(StringX.trimAll(var3.getAttribute(var12).getString()));
        }

        if ((var12 = var3.indexOf("css")) >= 0) {
            var4.setCss(StringX.trimAll(var3.getAttribute(var12).getString()));
        }

        if ((var12 = var3.indexOf("required")) >= 0) {
            var4.setRequired(var3.getAttribute(var12).getBoolean());
        }

        if ((var12 = var3.indexOf("displayOnly")) >= 0) {
            var4.setDisplayOnly(var3.getAttribute(var12).getBoolean());
        }

        if ((var12 = var3.indexOf("sortingSuitable")) >= 0) {
            var4.setSortingSuitable(var3.getAttribute(var12).getBoolean());
        }

        if ((var12 = var3.indexOf("filterSuitable")) >= 0) {
            var4.setFilterSuitable(var3.getAttribute(var12).getBoolean());
        }

        if ((var12 = var3.indexOf("codeTable")) >= 0) {
            var4.setCodeTable(StringX.trimAll(var3.getAttribute(var12).getString()));
        }

        var6 = var3.getAttribute("boundValues", (String) null).getString();
        if (var6 != null) {
            String[] var7 = StringX.parseArray(var6);
            if (var7.length > 1) {
                DataElement var8 = new DataElement("v0", var2.getType());
                DataElement var9 = new DataElement("v1", var2.getType());
                var8.setValue(var7[0]);
                var9.setValue(var7[1]);
                var4.setBoundValues(var8.getValue(), var9.getValue());
            }
        }

        Node var13 = var1.getChild("customerHints");
        if (var13 != null) {
            Iterator var14 = var13.getChildren("hint").iterator();
            byte var15 = 0;

            while (var14.hasNext()) {
                Node var10 = (Node) var14.next();
                Attributes var11 = var10.getAttributes();
                var4.setCustomerHint(var11.getAttribute("name", "CUSTOMERHINT_" + var15).getString(), var11.getAttribute("value", (String) null).getString());
            }
        }

        return var4;
    }

    private Properties buildRefManagerInfo(Node var1) {
        Properties var2 = new Properties();
        DataElement var3 = var1.getAttributes().getAttribute("id");
        if (var3 != null && !StringX.isSpace(var3)) {
            var2.setProperty("id", var3.getString());
        }

        var2.putAll(var1.getProperties().getAsProperties(false));
        return var2;
    }
}
