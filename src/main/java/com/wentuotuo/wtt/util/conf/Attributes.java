//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.conf;

import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import java.util.ArrayList;
import java.util.List;

public class Attributes {
    private List attributes = new ArrayList();

    public Attributes() {
    }

    public void addAttribute(DataElement var1) {
        int var2 = this.indexOf(var1);
        if(var2 < 0) {
            this.attributes.add(var1);
        } else {
            this.attributes.remove(var2);
            this.attributes.add(var2, var1);
        }

    }

    public void addAttribute(int var1, DataElement var2) {
        int var3 = this.indexOf(var2);
        if(var3 >= 0) {
            this.attributes.remove(var3);
        }

        if(var1 < 0) {
            var1 = 0;
        }

        if(var1 >= this.attributes.size()) {
            var1 = this.attributes.size();
        }

        this.attributes.add(var1, var2);
    }

    public void addAttribute(String var1, String var2) {
        if(!StringX.isSpace(var1)) {
            this.attributes.add(DataElement.valueOf(StringX.trimAll(var1), var2));
        }

    }

    public void addAttribute(int var1, String var2, String var3) {
        if(!StringX.isSpace(var2)) {
            this.attributes.add(var1, DataElement.valueOf(StringX.trimAll(var2), var3));
        }

    }

    public int size() {
        return this.attributes.size();
    }

    public int indexOf(String var1) {
        int var2;
        for(var2 = this.attributes.size() - 1; var2 >= 0; --var2) {
            DataElement var3 = (DataElement)this.attributes.get(var2);
            if(var3.getName().equals(var1)) {
                break;
            }
        }

        return var2;
    }

    public int indexOf(DataElement var1) {
        return this.indexOf(var1.getName());
    }

    public DataElement getAttribute(int var1) {
        return var1 >= 0 && var1 < this.attributes.size()?(DataElement)this.attributes.get(var1):null;
    }

    public DataElement getAttribute(String var1) {
        return this.getAttribute(this.indexOf(var1));
    }

    public DataElement getAttribute(String var1, String var2) {
        int var3 = this.indexOf(var1);
        return var3 < 0?DataElement.valueOf(var1, var2):this.getAttribute(var3);
    }

    public void removeAttribute(int var1) {
        if(var1 >= 0 && var1 < this.attributes.size()) {
            this.attributes.remove(var1);
        }

    }

    public void removeAttribute(String var1) {
        this.removeAttribute(this.indexOf(var1));
    }

    public void removeAttribute(DataElement var1) {
        this.removeAttribute(this.indexOf(var1));
    }

    public void clear() {
        this.attributes.clear();
    }

    public List getAttributes() {
        return this.attributes;
    }
}
