//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.json;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.dpx.recordset.Field;
import com.wentuotuo.wtt.dpx.recordset.Record;
import com.wentuotuo.wtt.jbo.BizObject;
import com.wentuotuo.wtt.jbo.BizObjectClass;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.DateX;
import com.wentuotuo.wtt.lang.Element;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.util.DateRange;
import com.wentuotuo.wtt.util.ElementList;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JSONEncoder {
    public JSONEncoder() {
    }

    public static String encode(Object var0) {
        if(var0 == null) {
            return "null";
        } else if(var0 instanceof EncodeJson) {
            return ((EncodeJson)var0).toJson();
        } else if(var0 instanceof Byte) {
            return encode(((Byte)var0).byteValue());
        } else if(var0 instanceof Short) {
            return encode(((Short)var0).shortValue());
        } else if(var0 instanceof Integer) {
            return encode(((Integer)var0).intValue());
        } else if(var0 instanceof Long) {
            return encode(((Long)var0).longValue());
        } else if(var0 instanceof Float) {
            return encode((double)((Float)var0).floatValue());
        } else if(var0 instanceof Double) {
            return encode(((Double)var0).doubleValue());
        } else if(var0 instanceof Character) {
            return encode(((Character)var0).charValue());
        } else if(var0 instanceof String) {
            return encode((String)var0);
        } else if(var0 instanceof Boolean) {
            return encode(((Boolean)var0).booleanValue());
        } else if(var0 instanceof Date) {
            return encode((Date)var0);
        } else if(var0 instanceof DateRange) {
            return encode((DateRange)var0);
        } else if(var0 instanceof DataElement) {
            return encode((DataElement)var0);
        } else if(var0 instanceof JSONElement) {
            return encode((Element)((JSONElement)var0));
        } else if(var0 instanceof Element) {
            return encode((Element)var0);
        } else if(var0 instanceof BizObjectClass) {
            return encode((BizObjectClass)var0);
        } else if(var0 instanceof Object[]) {
            return encode((Object[])var0);
        } else if(var0 instanceof byte[]) {
            return encode((short[])var0);
        } else if(var0 instanceof short[]) {
            return encode((byte[])var0);
        } else if(var0 instanceof int[]) {
            return encode((int[])var0);
        } else if(var0 instanceof long[]) {
            return encode((long[])var0);
        } else if(var0 instanceof float[]) {
            return encode((float[])var0);
        } else if(var0 instanceof double[]) {
            return encode((double[])var0);
        } else if(var0 instanceof char[]) {
            return encode((char[])var0);
        } else if(var0 instanceof boolean[]) {
            return encode((boolean[])var0);
        } else if(var0 instanceof List) {
            return encode((List)var0);
        } else if(var0 instanceof Iterator) {
            return encode((Iterator)var0);
        } else if(var0 instanceof Map) {
            return encode((Map)var0);
        } else if(var0 instanceof BizObject) {
            return encode((BizObject)var0);
        } else if(var0 instanceof Record) {
            return encode((Record)var0);
        } else if(var0 instanceof JSONObject) {
            JSONObject var11 = (JSONObject)var0;
            return var11.getType() == 0?encodeElementListAsObject(var11):encodeElementListAsArray(var11);
        } else if(var0 instanceof ElementList) {
            return encodeElementListAsObject((ElementList)var0);
        } else {
            List var1 = getGetMethod(var0.getClass());
            StringBuffer var2 = new StringBuffer(var1.size() * 10);
            var2.append('{');
            int var3 = 0;

            for(int var4 = var1.size(); var3 < var4; ++var3) {
                Method var5 = (Method)var1.get(var3);
                String var6 = var5.getName();
                String var7 = var6.startsWith("get")?var6.substring(3):var6.substring(2);
                var7 = getFieldName(var7);
                Object var8 = null;

                try {
                    var8 = var5.invoke(var0, (Object[])null);
                    if(var3 > 0) {
                        var2.append(',');
                    }

                    var2.append("\"").append(var7).append("\"").append(':').append(encode(var8));
                } catch (Exception var10) {
                    WTT.getLog().error(var10);
                }
            }

            var2.append('}');
            return var2.toString();
        }
    }

    public static void encode(Object var0, Writer var1) throws IOException {
        if(var0 == null) {
            var1.write("null");
        } else if(var0 instanceof EncodeJson) {
            ((EncodeJson)var0).toJson(var1);
        } else if(var0 instanceof Byte) {
            var1.write(encode(((Byte)var0).byteValue()));
        } else if(var0 instanceof Short) {
            var1.write(encode(((Short)var0).shortValue()));
        } else if(var0 instanceof Integer) {
            var1.write(encode(((Integer)var0).intValue()));
        } else if(var0 instanceof Long) {
            var1.write(encode(((Long)var0).longValue()));
        } else if(var0 instanceof Float) {
            var1.write(encode((double)((Float)var0).floatValue()));
        } else if(var0 instanceof Double) {
            var1.write(encode(((Double)var0).doubleValue()));
        } else if(var0 instanceof Character) {
            var1.write(encode(((Character)var0).charValue()));
        } else if(var0 instanceof String) {
            var1.write(encode((String)var0));
        } else if(var0 instanceof Boolean) {
            var1.write(encode(((Boolean)var0).booleanValue()));
        } else if(var0 instanceof Date) {
            var1.write(encode((Date)var0));
        } else if(var0 instanceof DateRange) {
            var1.write(encode((DateRange)var0));
        } else if(var0 instanceof DataElement) {
            var1.write(encode((DataElement)var0));
        } else if(var0 instanceof Element) {
            encode((Element)var0, var1);
        } else if(var0 instanceof BizObjectClass) {
            var1.write(encode((BizObjectClass)var0));
        } else if(var0.getClass().isArray()) {
            encodeArray(var0, var1);
        } else if(var0 instanceof List) {
            encode((List)var0, var1);
        } else if(var0 instanceof Iterator) {
            encode((Iterator)var0, var1);
        } else if(var0 instanceof Map) {
            encode((Map)var0, var1);
        } else if(var0 instanceof BizObject) {
            var1.write(encode((BizObject)var0));
        } else if(var0 instanceof Record) {
            var1.write(encode((Record)var0));
        } else if(var0 instanceof JSONObject) {
            JSONObject var11 = (JSONObject)var0;
            if(var11.getType() == 0) {
                encodeElementListAsObject(var11, var1);
            } else {
                encodeElementListAsArray(var11, var1);
            }

        } else if(var0 instanceof ElementList) {
            encodeElementListAsObject((ElementList)var0, var1);
        } else {
            List var2 = getGetMethod(var0.getClass());
            var1.write(123);
            int var3 = 0;

            for(int var4 = var2.size(); var3 < var4; ++var3) {
                Method var5 = (Method)var2.get(var3);
                String var6 = var5.getName();
                String var7 = var6.startsWith("get")?var6.substring(3):var6.substring(2);
                var7 = getFieldName(var7);
                Object var8 = null;

                try {
                    var8 = var5.invoke(var0, (Object[])null);
                    if(var3 > 0) {
                        var1.write(44);
                    }

                    var1.write("\"");
                    var1.write(var7);
                    var1.write("\":");
                    encode(var8, var1);
                } catch (Exception var10) {
                    WTT.getLog().error(var10);
                }
            }

            var1.write(125);
        }
    }

    private static String encode(byte var0) {
        return String.valueOf(var0);
    }

    private static String encode(short var0) {
        return String.valueOf(var0);
    }

    private static String encode(int var0) {
        return String.valueOf(var0);
    }

    private static String encode(long var0) {
        return String.valueOf(var0);
    }

    private static String encode(double var0) {
        return String.valueOf(var0);
    }

    private static String encode(boolean var0) {
        return String.valueOf(var0);
    }

    private static String encode(char var0) {
        return '"' + escape(String.valueOf(var0)) + '"';
    }

    private static String encode(String var0) {
        return var0 == null?"null":'"' + escape(var0) + '"';
    }

    private static String encode(Date var0) {
        return var0 == null?"null":'"' + DateX.format(var0) + '"';
    }

    private static String encode(DataElement var0) {
        if(StringX.isEmpty(var0)) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(20);
            var1.append('{');
            var1.append("\"id\":").append(encode(var0.getId()));
            var1.append(",\"name\":").append(encode(var0.getName()));
            var1.append(",\"label\":").append(encode(var0.getLabel()));
            var1.append(",\"type\":").append(encode(var0.getType()));
            var1.append(",\"length\":").append(encode(var0.getLength()));
            var1.append(",\"scale\":").append(encode(var0.getScale()));
            var1.append(",\"value\":");
            byte var2 = var0.getType();
            if(var2 == 0) {
                var1.append(encode(var0.getString()));
            } else if(var2 == 16) {
                var1.append(encode(var0.getDate()));
            } else {
                var1.append(var0.getString());
            }

            var1.append('}');
            return var1.toString();
        }
    }

    private static String encode(Element var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(20);
            var1.append('{');
            var1.append("\"id\":").append(encode(var0.getId()));
            var1.append(",\"name\":").append(encode(var0.getName()));
            var1.append(",\"label\":").append(encode(var0.getLabel()));
            var1.append(",\"value\":").append(encode(var0.getValue()));
            var1.append('}');
            return var1.toString();
        }
    }

    private static void encode(Element var0, Writer var1) throws IOException {
        if(var0 == null) {
            var1.write("null");
        } else {
            var1.write(123);
            var1.write("\"id\":");
            var1.write(var0.getId());
            var1.write(",\"name\":");
            var1.write(encode(var0.getName()));
            var1.write(",\"label\":");
            var1.write(encode(var0.getLabel()));
            var1.write(",\"value\":");
            encode(var0.getValue(), var1);
            var1.write(125);
        }
    }

    private static String toAttribute(DataElement var0) {
        if(var0 == null) {
            return "null";
        } else {
            byte var1 = var0.getType();
            StringBuffer var2 = new StringBuffer(20);
            var2.append("\"").append(var0.getName()).append("\":");
            if(var0.isNull()) {
                var2.append("null");
            } else if(var1 == 0) {
                var2.append(encode(var0.getString()));
            } else if(var1 == 16) {
                var2.append(encode(var0.getDate()));
            } else {
                var2.append(var0.getString());
            }

            return var2.toString();
        }
    }

    private static String toAttribute(Element var0) {
        StringBuffer var1 = new StringBuffer(20);
        var1.append("\"").append(var0.getName()).append("\":");
        if(var0.isNull()) {
            var1.append("null");
        } else {
            var1.append(encode(var0.getValue()));
        }

        return var1.toString();
    }

    private static String encode(DateRange var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(18);
            var1.append("{\"startDate\":").append('"').append(var0.getStartDateString()).append('"');
            var1.append(",\"endDate\":").append('"').append(var0.getEndDateString()).append('"').append('}');
            return var1.toString();
        }
    }

    private static String encode(Map var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer();
            var1.append('{');
            boolean var2 = true;
            Iterator var3 = var0.keySet().iterator();

            while(var3.hasNext()) {
                Object var4 = var3.next();
                Object var5 = var0.get(var4);
                if(var4 != null) {
                    if(var2) {
                        var2 = false;
                    } else {
                        var1.append(',');
                    }

                    var1.append("\"").append(var4.toString()).append("\"").append(":");
                    var1.append(encode(var5));
                }
            }

            return var1.append('}').toString();
        }
    }

    private static void encode(Map var0, Writer var1) throws IOException {
        if(var0 == null) {
            var1.write("null");
        } else {
            var1.write("{");
            boolean var2 = true;
            Iterator var3 = var0.entrySet().iterator();

            while(var3.hasNext()) {
                Entry var4 = (Entry)var3.next();
                Object var5 = var4.getKey();
                Object var6 = var4.getValue();
                if(var5 != null) {
                    if(var2) {
                        var2 = false;
                    } else {
                        var1.write(44);
                    }

                    var1.write("\"");
                    var1.write(var5.toString());
                    var1.write("\":");
                    encode(var6, var1);
                }
            }

            var1.write("}");
        }
    }

    private static String encode(BizObjectClass var0) {
        if(var0 == null) {
            return "null";
        } else {
            DataElement[] var1 = var0.getAttributes();
            StringBuffer var2 = new StringBuffer(var1.length * 20);
            var2.append('{');
            var2.append("\"className\":\"").append(var0.getAbsoluteName()).append("\",\"classLabel\":\"").append(var0.getLabel()).append("\",\"attributes\":{");

            for(int var3 = 0; var3 < var1.length; ++var3) {
                if(var3 > 0) {
                    var2.append(',');
                }

                var2.append("\"").append(var1[var3].getName()).append("\":\"").append(var1[var3].getLabel()).append("\"");
            }

            var2.append("}}");
            return var2.toString();
        }
    }

    private static String encode(BizObject var0) {
        if(var0 == null) {
            return "null";
        } else {
            DataElement[] var1 = var0.getAttributes();
            StringBuffer var2 = new StringBuffer(var1.length * 20);
            var2.append('{');

            for(int var3 = 0; var3 < var1.length; ++var3) {
                if(var3 > 0) {
                    var2.append(',');
                }

                var2.append(toAttribute(var1[var3]));
            }

            var2.append('}');
            return var2.toString();
        }
    }

    private static String encodeElementListAsObject(ElementList var0) {
        if(var0 == null) {
            return "null";
        } else {
            List var1 = var0.getElementTable();
            StringBuffer var2 = new StringBuffer(var1.size() * 20);
            var2.append('{');
            int var3 = 0;

            for(int var4 = var1.size(); var3 < var4; ++var3) {
                if(var3 > 0) {
                    var2.append(',');
                }

                var2.append(toAttribute((Element)var1.get(var3)));
            }

            var2.append('}');
            return var2.toString();
        }
    }

    private static String encodeElementListAsArray(ElementList var0) {
        if(var0 == null) {
            return "null";
        } else {
            List var1 = var0.getElementTable();
            StringBuffer var2 = new StringBuffer(var1.size() * 20);
            var2.append('[');
            int var3 = 0;

            for(int var4 = var1.size(); var3 < var4; ++var3) {
                if(var3 > 0) {
                    var2.append(',');
                }

                Element var5 = (Element)var1.get(var3);
                var2.append(encode(var5.getValue()));
            }

            var2.append(']');
            return var2.toString();
        }
    }

    private static void encodeElementListAsObject(ElementList var0, Writer var1) throws IOException {
        if(var0 == null) {
            var1.write("null");
        } else {
            List var2 = var0.getElementTable();
            var1.write(123);
            int var3 = 0;

            for(int var4 = var2.size(); var3 < var4; ++var3) {
                if(var3 > 0) {
                    var1.write(44);
                }

                Element var5 = (Element)var2.get(var3);
                var1.write("\"");
                var1.write(var5.getName());
                var1.write("\":");
                encode(var5.getValue(), var1);
            }

            var1.write(125);
        }
    }

    private static void encodeElementListAsArray(ElementList var0, Writer var1) throws IOException {
        if(var0 == null) {
            var1.write("null");
        } else {
            List var2 = var0.getElementTable();
            var1.write(91);
            int var3 = 0;

            for(int var4 = var2.size(); var3 < var4; ++var3) {
                if(var3 > 0) {
                    var1.write(44);
                }

                Element var5 = (Element)var2.get(var3);
                encode(var5.getValue(), var1);
            }

            var1.write(93);
        }
    }

    private static String encode(Record var0) {
        if(var0 == null) {
            return "null";
        } else {
            Field[] var1 = var0.getFields();
            StringBuffer var2 = new StringBuffer(var1.length * 20);
            var2.append('{');

            for(int var3 = 0; var3 < var1.length; ++var3) {
                if(var3 > 0) {
                    var2.append(',');
                }

                var2.append(toAttribute((DataElement)var1[var3]));
            }

            var2.append('}');
            return var2.toString();
        }
    }

    private static List getGetMethod(Class var0) {
        Method[] var1 = var0.getMethods();
        ArrayList var2 = new ArrayList();

        for(int var3 = 0; var3 < var1.length; ++var3) {
            if(var1[var3].getParameterTypes().length <= 0) {
                String var4 = var1[var3].getName();
                if(!var4.equals("getClass") && (var4.startsWith("is") && var4.length() > 2 || var4.startsWith("get") && var4.length() > 3)) {
                    var2.add(var1[var3]);
                }
            }
        }

        return var2;
    }

    private static void encodeArray(Object var0, Writer var1) throws IOException {
        if(var0 == null) {
            var1.write("null");
        } else {
            var1.write(91);

            for(int var2 = 0; var2 < Array.getLength(var0); ++var2) {
                if(var2 > 0) {
                    var1.write(44);
                }

                encode(Array.get(var0, var2), var1);
            }

            var1.write(93);
        }
    }

    private static String encode(short[] var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(var0.length * 6 + 2);
            var1.append('[');

            for(int var2 = 0; var2 < var0.length; ++var2) {
                if(var2 > 0) {
                    var1.append(',');
                }

                var1.append(encode(var0[var2]));
            }

            var1.append(']');
            return var1.toString();
        }
    }

    private static String encode(int[] var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(var0.length * 6 + 2);
            var1.append('[');

            for(int var2 = 0; var2 < var0.length; ++var2) {
                if(var2 > 0) {
                    var1.append(',');
                }

                var1.append(encode(var0[var2]));
            }

            var1.append(']');
            return var1.toString();
        }
    }

    private static String encode(long[] var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(var0.length * 6 + 2);
            var1.append('[');

            for(int var2 = 0; var2 < var0.length; ++var2) {
                if(var2 > 0) {
                    var1.append(',');
                }

                var1.append(encode(var0[var2]));
            }

            var1.append(']');
            return var1.toString();
        }
    }

    private static String encode(double[] var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(var0.length * 6 + 2);
            var1.append('[');

            for(int var2 = 0; var2 < var0.length; ++var2) {
                if(var2 > 0) {
                    var1.append(',');
                }

                var1.append(encode(var0[var2]));
            }

            var1.append(']');
            return var1.toString();
        }
    }

    private static String encode(float[] var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(var0.length * 6 + 2);
            var1.append('[');

            for(int var2 = 0; var2 < var0.length; ++var2) {
                if(var2 > 0) {
                    var1.append(',');
                }

                var1.append(encode((double)var0[var2]));
            }

            var1.append(']');
            return var1.toString();
        }
    }

    private static String encode(char[] var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(var0.length * 3 + 2);
            var1.append('[');

            for(int var2 = 0; var2 < var0.length; ++var2) {
                if(var2 > 0) {
                    var1.append(',');
                }

                var1.append(encode(var0[var2]));
            }

            var1.append(']');
            return var1.toString();
        }
    }

    private static String encode(byte[] var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(var0.length * 5 + 2);
            var1.append('[');

            for(int var2 = 0; var2 < var0.length; ++var2) {
                if(var2 > 0) {
                    var1.append(',');
                }

                var1.append(encode(var0[var2]));
            }

            var1.append(']');
            return var1.toString();
        }
    }

    private static String encode(boolean[] var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(var0.length * 6 + 2);
            var1.append('[');

            for(int var2 = 0; var2 < var0.length; ++var2) {
                if(var2 > 0) {
                    var1.append(',');
                }

                var1.append(encode(var0[var2]));
            }

            var1.append(']');
            return var1.toString();
        }
    }

    private static String encode(Object[] var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(var0.length * 20 + 2);
            var1.append('[');

            for(int var2 = 0; var2 < var0.length; ++var2) {
                if(var2 > 0) {
                    var1.append(',');
                }

                var1.append(encode(var0[var2]));
            }

            var1.append(']');
            return var1.toString();
        }
    }

    private static String encode(List var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(var0.size() * 20 + 2);
            var1.append('[');
            int var2 = 0;

            for(int var3 = var0.size(); var2 < var3; ++var2) {
                if(var2 > 0) {
                    var1.append(',');
                }

                var1.append(encode(var0.get(var2)));
            }

            var1.append(']');
            return var1.toString();
        }
    }

    private static void encode(List var0, Writer var1) throws IOException {
        if(var0 == null) {
            var1.write("null");
        } else {
            var1.write(91);

            for(int var2 = 0; var2 < var0.size(); ++var2) {
                if(var2 > 0) {
                    var1.write(44);
                }

                encode(var0.get(var2), var1);
            }

            var1.write(93);
        }
    }

    private static String encode(Iterator var0) {
        if(var0 == null) {
            return "null";
        } else {
            StringBuffer var1 = new StringBuffer(256);
            var1.append('[');

            while(var0.hasNext()) {
                var1.append(encode(var0.next())).append(',');
            }

            var1.replace(var1.length() - 1, var1.length(), "]");
            return var1.toString();
        }
    }

    private static void encode(Iterator var0, Writer var1) throws IOException {
        if(var0 == null) {
            var1.write("null");
        } else {
            var1.write(91);

            for(int var2 = 0; var0.hasNext(); ++var2) {
                if(var2 > 0) {
                    var1.write(44);
                }

                encode(var0.next(), var1);
            }

            var1.write(93);
        }
    }

    private static String getFieldName(String var0) {
        return StringX.isEmpty(var0)?"null":var0.substring(0, 1).toLowerCase() + var0.substring(1);
    }

    private static String escape(String var0) {
        if(var0 == null) {
            return var0;
        } else {
            StringBuffer var1 = new StringBuffer();

            for(int var2 = 0; var2 < var0.length(); ++var2) {
                char var3 = var0.charAt(var2);
                switch(var3) {
                    case '\b':
                        var1.append("\\b");
                        continue;
                    case '\t':
                        var1.append("\\t");
                        continue;
                    case '\n':
                        var1.append("\\n");
                        continue;
                    case '\f':
                        var1.append("\\f");
                        continue;
                    case '\r':
                        var1.append("\\r");
                        continue;
                    case '"':
                        var1.append("\\\"");
                        continue;
                    case '\'':
                        var1.append("\\'");
                        continue;
                    case '/':
                        var1.append("\\/");
                        continue;
                    case '\\':
                        var1.append("\\\\");
                        continue;
                }

                if(var3 >= 0 && var3 <= 31 || var3 >= 127 && var3 <= 159 || var3 >= 8192 && var3 <= 8447) {
                    String var4 = Integer.toHexString(var3);
                    var1.append("\\u");

                    for(int var5 = 0; var5 < 4 - var4.length(); ++var5) {
                        var1.append('0');
                    }

                    var1.append(var4.toUpperCase());
                } else {
                    var1.append(var3);
                }
            }

            return var1.toString();
        }
    }
}
