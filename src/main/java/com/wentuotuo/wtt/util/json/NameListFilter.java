//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.json;

import com.wentuotuo.wtt.lang.Element;
import com.wentuotuo.wtt.lang.StringX;
import java.util.HashSet;
import java.util.List;

public class NameListFilter implements ElementFilter {
    private boolean refuseList;
    private HashSet nameList;

    public NameListFilter(List var1, boolean var2) {
        this.refuseList = false;
        this.nameList = new HashSet();
        this.refuseList = var2;
        if(var1 != null && var1.size() > 0) {
            this.addNames(var1);
        }

    }

    public NameListFilter(List var1) {
        this(var1, false);
    }

    public NameListFilter(String[] var1, boolean var2) {
        this.refuseList = false;
        this.nameList = new HashSet();
        this.refuseList = var2;
        if(var1 != null && var1.length > 0) {
            this.addNames(var1);
        }

    }

    public NameListFilter(String[] var1) {
        this(var1, false);
    }

    public NameListFilter(String var1) {
        this.refuseList = false;
        this.nameList = new HashSet();
        if(var1 != null) {
            this.addNames(var1);
        }

    }

    public NameListFilter(boolean var1) {
        this.refuseList = false;
        this.nameList = new HashSet();
        this.refuseList = var1;
    }

    public NameListFilter() {
        this.refuseList = false;
        this.nameList = new HashSet();
    }

    public NameListFilter addName(String var1) {
        if(var1 != null) {
            this.nameList.add(var1.toUpperCase());
        }

        return this;
    }

    public NameListFilter addNames(String[] var1) {
        if(var1 != null) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                this.addName(var1[var2]);
            }
        }

        return this;
    }

    public NameListFilter addNames(List var1) {
        if(var1 != null) {
            for(int var2 = 0; var2 < var1.size(); ++var2) {
                this.addName((String)var1.get(var2));
            }
        }

        return this;
    }

    public NameListFilter addNames(String var1) {
        return this.addNames(StringX.parseArray(var1));
    }

    public boolean isWhiteList() {
        return this.refuseList;
    }

    public void setWhiteList(boolean var1) {
        this.refuseList = var1;
    }

    public boolean accept(Element var1) {
        if(var1 == null) {
            return false;
        } else {
            boolean var2 = this.nameList.contains(var1.getName().toUpperCase());
            return this.refuseList?!var2:var2;
        }
    }
}
