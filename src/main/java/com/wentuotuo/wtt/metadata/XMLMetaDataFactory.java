//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.metadata;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.sql.SQLConstants;
import com.wentuotuo.wtt.util.conf.Attributes;
import com.wentuotuo.wtt.util.conf.Configuration;
import com.wentuotuo.wtt.util.conf.Node;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class XMLMetaDataFactory extends MetaDataFactory {
    private static final String version = "1.0";
    private static final String provider = "Amarsoft";
    private static final String describe = "基于XML存储的元数据管理";
    private HashMap dataSources = new HashMap(4);
    private String schemas = null;

    public XMLMetaDataFactory() {
    }

    public DataSourceMetaData getInstance(String var1) {
        return (DataSourceMetaData)this.dataSources.get(var1.toUpperCase());
    }

    public void initMetaDataFactory() {
        String var1 = this.getSchemas();
        if(var1 != null) {
            var1 = var1.replaceAll("^\\s+", "").replaceAll("\\s+$", "").replaceAll("\\s*,\\s*", ",");
            String[] var2 = var1.split(",");

            for(int var3 = 0; var3 < var2.length; ++var3) {
                DataSourceMetaData var4 = this.buildDataSource(var2[var3]);
                if(var4 != null) {
                    this.dataSources.put(var4.getName().toUpperCase(), var4);
                }
            }
        }

    }

    private DataSourceMetaData buildDataSource(String var1) {
        Configuration var2 = new Configuration();

        try {
            var2.loadFromFile(var1);
        } catch (WTTException var14) {
            WTT.getLog().error(var14);
            return null;
        }

        Node var3 = var2.getRootNode();
        if(!var3.getName().equals("dataSource")) {
            WTT.getLog().error(new WTTException(14, new String[]{"dataSource@" + var1}));
            return null;
        } else {
            Node var4 = var3.getChild("name");
            if(var4 != null && !StringX.isSpace(var4.getValue())) {
                String var5 = var3.getChild("name").getValue();
                var4 = var3.getChild("encoding");
                String var6 = var3 == null?System.getProperty("file.encoding", "GB18030"):var4.getValue();
                DefaultDataSourceMetaData var7 = new DefaultDataSourceMetaData(var5, var6);
                var4 = var3.getChild("label");
                if(var4 != null) {
                    var7.setLabel(var4.getValue());
                }

                var4 = var3.getChild("describe");
                if(var4 != null) {
                    var7.setDescribe(var4.getValue());
                }

                Node var8 = var3.getChild("product");
                if(var8 != null) {
                    var4 = var8.getChild("name");
                    if(var4 != null) {
                        var7.setProductName(var4.getValue());
                    }

                    var4 = var8.getChild("version");
                    if(var4 != null) {
                        var7.setProductVersion(var4.getValue());
                    }

                    var4 = var8.getChild("provider");
                    if(var4 != null) {
                        var7.setProviderName(var4.getValue());
                    }
                }

                Iterator var9 = var3.getProperties().getProperties().iterator();

                while(var9.hasNext()) {
                    DataElement var10 = (DataElement)var9.next();
                    var7.setProperty(var10.getName(), var10.getString());
                }

                Node var15 = var3.getChild("tables");
                if(var15 != null) {
                    Iterator var11 = var15.getChildren("table").iterator();

                    while(var11.hasNext()) {
                        Node var12 = (Node)var11.next();
                        DefaultTableMetaData var13 = this.buildTable(var12);
                        if(var13 != null) {
                            var7.addTable(var13);
                        }
                    }
                }

                return var7;
            } else {
                WTT.getLog().error(new WTTException(14, new String[]{"name@" + var1}));
                return null;
            }
        }
    }

    private DefaultTableMetaData buildTable(Node var1) {
        List var2 = var1.getChildren("column");
        if(var2.size() < 1) {
            return null;
        } else {
            DefaultColumnMetaData[] var3 = new DefaultColumnMetaData[var2.size()];
            Attributes var4 = var1.getAttributes();
            String var5 = var4.getAttribute("name").getString().toUpperCase();
            String var6 = var4.getAttribute("type", "TABLE").getString().toUpperCase();
            int var7 = var3.length * (var3.length + 1) / 2;
            int var8 = 0;

            for(int var9 = 0; var9 < var3.length; ++var9) {
                Node var10 = (Node)var2.get(var9);
                var3[var9] = this.buildColumn(var9 + 1, var10);
                var8 += var3[var9].getIndex();
            }

            if(var8 != var7) {
                WTT.getLog().warn("Column index is't a sequence from 1 to " + var3.length + ",can't construct table " + var5 + " !");
                return null;
            } else {
                DefaultTableMetaData var13 = new DefaultTableMetaData(var5, var6, var3);
                var13.setLabel(var4.getAttribute("label", var5).getString());
                var13.setDescribe(var4.getAttribute("describe", var5).getString());
                Iterator var12 = var1.getProperties().getProperties().iterator();

                while(var12.hasNext()) {
                    DataElement var11 = (DataElement)var12.next();
                    var13.setProperty(var11.getName(), var11.getString());
                }

                return var13;
            }
        }
    }

    private DefaultColumnMetaData buildColumn(int var1, Node var2) {
        DefaultColumnMetaData var3 = new DefaultColumnMetaData(var1);
        Attributes var4 = var2.getAttributes();
        var3.setName(var4.getAttribute("name", "COLUMN_" + var1).getString());
        var3.setLabel(var4.getAttribute("label", "COLUMN_" + var1).getString());
        var3.setType(SQLConstants.getSQLDataType(var4.getAttribute("type", "CHAR").getString()));
        var3.setFormat(var4.getAttribute("format", "").getString());
        var3.setPrimaryKey(var4.getAttribute("primaryKey", "false").getBoolean());

        try {
            var3.setDisplaySize(var4.getAttribute("displaySize", "20").getInt());
        } catch (NumberFormatException var16) {
            var3.setDisplaySize(20);
        }

        try {
            var3.setPrecision(var4.getAttribute("precision", "14").getInt());
        } catch (NumberFormatException var15) {
            var3.setPrecision(14);
        }

        byte var5 = SQLConstants.SQLTypeToBaseType(var3.getType());
        if(var5 == 4 || var5 == 1 || var5 == 2) {
            try {
                var3.setScale(var4.getAttribute("scale", "4").getInt());
            } catch (NumberFormatException var14) {
                var3.setScale(4);
            }
        }

        DataElement var6 = var4.getAttribute("defaultValue");
        if(var6 != null) {
            switch(var5) {
                case 0:
                    var3.setDefaultValue(var6.getString());
                    break;
                case 1:
                    var3.setDefaultValue(var6.getInt());
                    break;
                case 2:
                    var3.setDefaultValue(var6.getLong());
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
                case 4:
                    var3.setDefaultValue(var6.getDouble());
                    break;
                case 8:
                    var3.setDefaultValue(var6.getBoolean());
                    break;
                case 16:
                    var3.setDefaultValue(var6.getDate());
            }
        }

        DataElement var7 = var4.getAttribute("index");
        if(!StringX.isSpace(var7)) {
            var3.setIndex(var7.getInt());
        }

        Iterator var8 = var2.getProperties().getProperties().iterator();

        while(var8.hasNext()) {
            DataElement var9 = (DataElement)var8.next();
            var3.setProperty(var9.getName(), var9.getString());
        }

        Node var17 = var2.getChild("uis");
        if(var17 != null) {
            List var10 = var17.getChildren("ui");

            for(int var11 = 0; var11 < var10.size(); ++var11) {
                Node var12 = (Node)var10.get(var11);
                String var13 = var12.getAttributes().getAttribute("name", "UI_" + String.valueOf(var11)).getString();
                var3.addUIMetaData(var13, this.buildColumnUI(var12));
            }
        }

        return var3;
    }

    private DefaultColumnUIMetaData buildColumnUI(Node var1) {
        DefaultColumnUIMetaData var2 = new DefaultColumnUIMetaData();
        Attributes var3 = var1.getAttributes();
        DataElement var4 = null;
        String var5 = null;
        var4 = var3.getAttribute("halignment");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            if(var5.equalsIgnoreCase("left")) {
                var2.setHAlignment(1);
            } else if(var5.equalsIgnoreCase("right")) {
                var2.setHAlignment(2);
            } else if(var5.equalsIgnoreCase("center")) {
                var2.setHAlignment(0);
            }
        }

        var4 = var3.getAttribute("valignment");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            if(var5.equalsIgnoreCase("top")) {
                var2.setVAlignment(3);
            } else if(var5.equalsIgnoreCase("bottom")) {
                var2.setVAlignment(4);
            } else if(var5.equalsIgnoreCase("center")) {
                var2.setVAlignment(0);
            }
        }

        var4 = var3.getAttribute("valueCharacter");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            if(var5.equalsIgnoreCase("list")) {
                var2.setValueCharacter(1);
            } else if(var5.equalsIgnoreCase("unlimited")) {
                var2.setValueCharacter(0);
            } else if(var5.equalsIgnoreCase("codetable")) {
                var2.setValueCharacter(2);
            } else if(var5.equalsIgnoreCase("range")) {
                var2.setValueCharacter(3);
            }
        }

        var4 = var3.getAttribute("inputMask");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            var2.setInputMask(var5);
        }

        var4 = var3.getAttribute("displayFormat");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            var2.setDisplayFormat(var5);
        }

        var4 = var3.getAttribute("css");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            var2.setCss(var5);
        }

        var4 = var3.getAttribute("isKey");
        if(var4 != null) {
            var2.setKey(var4.getBoolean());
        }

        var4 = var3.getAttribute("readOnly");
        if(var4 != null) {
            var2.setReadOnly(var4.getBoolean());
        }

        var4 = var3.getAttribute("required");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            var2.setRequried(var5.equalsIgnoreCase("true"));
        }

        var4 = var3.getAttribute("sortable");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            var2.setSortable(var5.equalsIgnoreCase("true"));
        }

        var4 = var3.getAttribute("visible");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            var2.setVisible(var5.equalsIgnoreCase("true"));
        }

        var4 = var3.getAttribute("valueCodetable");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            var2.setValueCodetable(var5);
        }

        var4 = var3.getAttribute("valueList");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            var2.setValueList(var5);
        }

        var4 = var3.getAttribute("valueRange");
        var5 = var4 == null?null:StringX.trimAll(var4.getString());
        if(!StringX.isEmpty(var5)) {
            var2.setValueRange(var5);
        }

        Iterator var6 = var1.getProperties().getProperties().iterator();

        while(var6.hasNext()) {
            DataElement var7 = (DataElement)var6.next();
            var2.setProperty(var7.getName(), var7.getString());
        }

        return var2;
    }

    public String getServiceProvider() {
        return "Amarsoft";
    }

    public String getServiceDescribe() {
        return "基于XML存储的元数据管理";
    }

    public String getServiceVersion() {
        return "1.0";
    }

    public void shutdown() {
    }

    public final String getSchemas() {
        return this.schemas;
    }

    public final void setSchemas(String var1) {
        this.schemas = var1;
    }

    public DataSourceMetaData[] getDataSources() {
        return (DataSourceMetaData[])this.dataSources.values().toArray(new DataSourceMetaData[0]);
    }

    public Properties getProperties() {
        return null;
    }

    public void setProperties(String var1) {
    }

    public void setProperties(Properties var1) {
    }
}
