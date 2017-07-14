//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

import com.wentuotuo.wtt.lang.StringX;
import java.util.ArrayList;
import java.util.List;

public class ReservedWordDictionary {
    private List dict = new ArrayList();

    public ReservedWordDictionary() {
    }

    public void registerWord(String var1) {
        if(!StringX.isSpace(var1)) {
            String var2 = StringX.trimAll(var1).toUpperCase();
            if(!this.dict.contains(var2)) {
                this.dict.add(var2);
            }

        }
    }

    public void removerWord(String var1) {
        int var2 = this.getWordId(var1);
        if(var2 >= 0) {
            this.dict.remove(var2);
        }

    }

    public void registerWords(List var1) {
        for(int var2 = 0; var2 < var1.size(); ++var2) {
            this.registerWord(var1.get(var2).toString());
        }

    }

    public void registerWords(String[] var1) {
        for(int var2 = 0; var2 < var1.length; ++var2) {
            this.registerWord(var1[var2]);
        }

    }

    public boolean isResrvedWord(String var1) {
        return this.getWordId(var1) >= 0;
    }

    public int getWordId(String var1) {
        String var2 = var1.toUpperCase();
        int var3 = -1;

        for(int var4 = 0; var4 < this.dict.size(); ++var4) {
            if(this.dict.get(var4).equals(var2)) {
                var3 = var4;
                break;
            }
        }

        return var3;
    }

    public String getWord(int var1) {
        return var1 >= 0 && var1 <= this.dict.size() - 1?(String)this.dict.get(var1):null;
    }
}
