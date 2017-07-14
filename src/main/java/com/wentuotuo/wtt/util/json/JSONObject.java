//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.json;

import com.wentuotuo.wtt.jbo.BizObject;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.Element;
import com.wentuotuo.wtt.util.ElementList;
import java.util.List;

public class JSONObject extends ElementList {
    private static final long serialVersionUID = 1L;
    public static final int OBJECT = 0;
    public static final int ARRAY = 1;
    private int type = 0;

    protected JSONObject() {
        super(1);
    }

    protected JSONObject(int var1) {
        super(var1 == 1?0:1);
        this.type = var1 == 1?1:0;
    }

    public int getType() {
        return this.type;
    }

    public Object getValue(String var1, boolean var2) {
        JSONElement var3 = (JSONElement)super.getElementByName(var1, var2);
        return var3 != null && !var3.isNull()?var3.getValue():null;
    }

    public Object getValue(String var1) {
        return this.getValue(var1, true);
    }

    public Object getValue(int var1) {
        JSONElement var2 = (JSONElement)super.get(var1);
        return var2 != null && !var2.isNull()?var2.getValue():null;
    }

    public JSONObject appendElement(Element var1) {
        this.add(var1);
        return this;
    }

    public JSONObject subObject(ElementFilter var1) {
        JSONObject var2 = new JSONObject(this.type);
        List var3 = this.getElementTable();

        for(int var4 = 0; var4 < var3.size(); ++var4) {
            Element var5 = (Element)var3.get(var4);
            if(var1.accept(var5)) {
                var2.add(var5);
            }
        }

        return var2;
    }

    public JSONObject subObject(int var1, int var2) {
        JSONObject var3 = new JSONObject(this.type);

        for(int var4 = 0; var4 < var2; ++var4) {
            var3.add(this.get(var4));
        }

        return var3;
    }

    public static JSONObject createArray() {
        return new JSONObject(1);
    }

    public static JSONObject createObject() {
        return new JSONObject(0);
    }

    public static JSONObject createArray(BizObject var0) {
        return var0 == null?null:BizObject2JsonObject(var0, 1, (ElementFilter)null);
    }

    public static JSONObject createObject(BizObject var0) {
        return var0 == null?null:BizObject2JsonObject(var0, 0, (ElementFilter)null);
    }

    public static JSONObject createArray(BizObject var0, ElementFilter var1) {
        return var0 == null?null:BizObject2JsonObject(var0, 1, var1);
    }

    public static JSONObject createObject(BizObject var0, ElementFilter var1) {
        return var0 == null?null:BizObject2JsonObject(var0, 0, var1);
    }

    private static JSONObject BizObject2JsonObject(BizObject var0, int var1, ElementFilter var2) {
        JSONObject var3 = new JSONObject(var1);
        DataElement[] var4 = var0.getAttributes();

        for(int var5 = 0; var5 < var4.length; ++var5) {
            if(var2 == null || var2.accept(var4[var5])) {
                var3.add(JSONElement.valueOf(var4[var5]));
            }
        }

        return var3;
    }
}
