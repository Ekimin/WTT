//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.Element;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class ElementList implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int KEY_ID = 0;
    public static final int KEY_NAME = 1;
    public static final int KEY_ID_AND_NAME = 2;
    private int keyType = 0;
    private String delimeter = "-";
    private List elementTable = new ArrayList();

    public ElementList(int var1, String var2) {
        this.keyType = var1;
        this.delimeter = var2 == null?"":var2;
    }

    public ElementList(int var1) {
        this.keyType = var1;
    }

    public ElementList() {
    }

    public final String getKey(Element var1) {
        return var1 == null?null:this.getKey(var1.getId(), var1.getName());
    }

    public String getKey(String var1, String var2) {
        return var1 != null && var2 != null?(this.keyType == 0?var1:(this.keyType == 1?var2:(this.keyType == 2?var1 + this.delimeter + var2:var1))):null;
    }

    public void add(Element var1) {
        int var2 = this.indexOf(var1);
        if(var2 < 0) {
            this.elementTable.add(var1);
        } else {
            this.elementTable.remove(var2);
            this.elementTable.add(var2, var1);
        }

    }

    public void add(int var1, Element var2) {
        if(this.getKey(var2) == null) {
            throw new IllegalArgumentException("ElementList.Element.key can't null");
        } else {
            int var3 = this.indexOf(var2);
            if(var3 >= 0) {
                this.elementTable.remove(var3);
            }

            if(var1 < 0) {
                var1 = 0;
            }

            if(var1 >= this.elementTable.size()) {
                var1 = this.elementTable.size();
            }

            this.elementTable.add(var1, var2);
        }
    }

    public int size() {
        return this.elementTable.size();
    }

    public int indexOf(String var1) {
        if(var1 == null) {
            return -1;
        } else {
            int var2;
            for(var2 = this.elementTable.size() - 1; var2 >= 0; --var2) {
                String var3 = this.getKey((Element)this.elementTable.get(var2));
                if(var3.equals(var1)) {
                    break;
                }
            }

            return var2;
        }
    }

    public int indexOf(Element var1) {
        return this.indexOf(this.getKey(var1));
    }

    public Element get(int var1) {
        return var1 >= 0 && var1 < this.elementTable.size()?(Element)this.elementTable.get(var1):null;
    }

    public Element get(String var1) {
        return this.get(this.indexOf(var1));
    }

    public void remove(int var1) {
        if(var1 >= 0 && var1 < this.elementTable.size()) {
            this.elementTable.remove(var1);
        }

    }

    public void remove(String var1) {
        this.remove(this.indexOf(var1));
    }

    public void remove(Element var1) {
        this.remove(this.indexOf(var1));
    }

    public void clear() {
        this.elementTable.clear();
    }

    public List getElementsById(String var1, boolean var2) {
        ArrayList var3 = new ArrayList();
        Pattern var4 = var2?Pattern.compile(var1, 2):Pattern.compile(var1);

        for(int var5 = 0; var5 < this.size(); ++var5) {
            Element var6 = (Element)this.elementTable.get(var5);
            if(var4.matcher(var6.getId()).matches()) {
                var3.add(var6);
            }
        }

        return var3;
    }

    public Element getElementById(String var1, boolean var2) {
        Element var3 = null;
        int var4 = 0;

        while(var4 < this.size()) {
            Element var5;
            label20: {
                var5 = (Element)this.elementTable.get(var4);
                if(var2) {
                    if(var5.getId().equalsIgnoreCase(var1)) {
                        break label20;
                    }
                } else if(var5.getId().equals(var1)) {
                    break label20;
                }

                ++var4;
                continue;
            }

            var3 = var5;
            break;
        }

        return var3;
    }

    public List getElementsByName(String var1, boolean var2) {
        ArrayList var3 = new ArrayList();
        Pattern var4 = var2?Pattern.compile(var1, 2):Pattern.compile(var1);

        for(int var5 = 0; var5 < this.size(); ++var5) {
            Element var6 = (Element)this.elementTable.get(var5);
            if(var4.matcher(var6.getName()).matches()) {
                var3.add(var6);
            }
        }

        return var3;
    }

    public Element getElementByName(String var1, boolean var2) {
        Element var3 = null;
        int var4 = 0;

        while(var4 < this.size()) {
            Element var5;
            label20: {
                var5 = (Element)this.elementTable.get(var4);
                if(var2) {
                    if(var5.getName().equalsIgnoreCase(var1)) {
                        break label20;
                    }
                } else if(var5.getName().equals(var1)) {
                    break label20;
                }

                ++var4;
                continue;
            }

            var3 = var5;
            break;
        }

        return var3;
    }

    public List getElementTable() {
        return this.elementTable;
    }

    public Properties getAsProperties(boolean var1) {
        Properties var2 = new Properties();

        for(int var3 = 0; var3 < this.elementTable.size(); ++var3) {
            Element var4 = (Element)this.elementTable.get(var3);
            if(!var4.isNull()) {
                String var5 = var4.getValue().toString();
                if(var5 != null && var1) {
                    var5 = WTT.replaceWTTTags(var5);
                }

                var2.put(this.getKey(var4), var5);
            }
        }

        return var2;
    }
}
