//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.lang;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

public class ObjectX {
    public ObjectX() {
    }

    public static boolean setProperty(Object var0, String var1, int var2, boolean var3) {
        if(var0 == null) {
            return false;
        } else {
            try {
                setObjectProperty(var0, var1, Integer.TYPE, new Integer(var2), var3);
                return true;
            } catch (Exception var5) {
                return false;
            }
        }
    }

    public static boolean setProperty(Object var0, String var1, long var2, boolean var4) {
        if(var0 == null) {
            return false;
        } else {
            try {
                setObjectProperty(var0, var1, Long.TYPE, new Long(var2), var4);
                return true;
            } catch (Exception var6) {
                return false;
            }
        }
    }

    public static boolean setProperty(Object var0, String var1, double var2, boolean var4) {
        if(var0 == null) {
            return false;
        } else {
            try {
                setObjectProperty(var0, var1, Double.TYPE, new Double(var2), var4);
                return true;
            } catch (Exception var6) {
                return false;
            }
        }
    }

    public static boolean setProperty(Object var0, String var1, char var2, boolean var3) {
        if(var0 == null) {
            return false;
        } else {
            try {
                setObjectProperty(var0, var1, Character.TYPE, new Character(var2), var3);
                return true;
            } catch (Exception var5) {
                return false;
            }
        }
    }

    public static boolean setProperty(Object var0, String var1, byte var2, boolean var3) {
        if(var0 == null) {
            return false;
        } else {
            try {
                setObjectProperty(var0, var1, Character.TYPE, new Byte(var2), var3);
                return true;
            } catch (Exception var5) {
                return false;
            }
        }
    }

    public static boolean setProperty(Object var0, String var1, boolean var2, boolean var3) {
        if(var0 == null) {
            return false;
        } else {
            try {
                setObjectProperty(var0, var1, Boolean.TYPE, Boolean.valueOf(var2), var3);
                return true;
            } catch (Exception var5) {
                return false;
            }
        }
    }

    public static boolean setProperty(Object var0, String var1, Date var2, boolean var3) {
        if(var0 == null) {
            return false;
        } else {
            try {
                setObjectProperty(var0, var1, Date.class, var2, var3);
                return true;
            } catch (Exception var5) {
                return false;
            }
        }
    }

    public static boolean setProperty(Object var0, String var1, String var2, boolean var3) {
        if(var0 == null) {
            return false;
        } else {
            try {
                setObjectProperty(var0, var1, String.class, var2, var3);
                return true;
            } catch (Exception var5) {
                return false;
            }
        }
    }

    private static void setObjectProperty(Object var0, String var1, Class var2, Object var3, boolean var4) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String var5 = null;
        Class var6 = var0.getClass();
        if(var4) {
            if(!var1.startsWith(var6.getName() + ".")) {
                throw new IllegalArgumentException("Class not match: " + var1);
            }

            var5 = var1.substring(var6.getName().length() + 1);
        } else {
            var5 = var1;
        }

        String var7 = "set" + var5.substring(0, 1).toUpperCase() + var5.substring(1);
        Method var8 = var6.getMethod(var7, new Class[]{var2});
        var8.invoke(var0, new Object[]{var3});
    }

    public static boolean setPropertyX(Object var0, String var1, String var2, boolean var3) {
        if(var3) {
            String var4 = var0.getClass().getName() + ".";
            if(!var1.startsWith(var4)) {
                return false;
            }
        }

        try {
            setObjectProperty(var0, var1, String.class, var2, var3);
            return true;
        } catch (Exception var12) {
            try {
                setObjectProperty(var0, var1, Date.class, StringX.parseDate(var2), var3);
                return true;
            } catch (Exception var11) {
                try {
                    setObjectProperty(var0, var1, Boolean.TYPE, new Boolean(StringX.parseBoolean(var2)), var3);
                    return true;
                } catch (Exception var10) {
                    if(var2 != null && !var2.equals("")) {
                        try {
                            setObjectProperty(var0, var1, Integer.TYPE, Integer.valueOf(var2), var3);
                            return true;
                        } catch (Exception var9) {
                            try {
                                setObjectProperty(var0, var1, Long.TYPE, Long.valueOf(var2), var3);
                                return true;
                            } catch (Exception var8) {
                                try {
                                    setObjectProperty(var0, var1, Byte.TYPE, Byte.valueOf(var2), var3);
                                    return true;
                                } catch (Exception var7) {
                                    try {
                                        setObjectProperty(var0, var1, Character.TYPE, new Character(var2.charAt(0)), var3);
                                        return true;
                                    } catch (Exception var6) {
                                        try {
                                            setObjectProperty(var0, var1, Double.TYPE, Double.valueOf(var2), var3);
                                            return true;
                                        } catch (Exception var5) {
                                            return false;
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    public static void setPropertyX(Object var0, Element var1) {
        Object var2 = var1.getValue();

        try {
            setObjectProperty(var0, var1.getName(), var2.getClass(), var2, false);
        } catch (Exception var4) {
            WTT.getLog().debug(var4);
        }

    }

    public static void setProperties(Object var0, Properties var1, boolean var2) {
        Enumeration var3 = var1.keys();

        while(var3.hasMoreElements()) {
            String var4 = (String)var3.nextElement();
            String var5 = var1.getProperty(var4);
            setPropertyX(var0, var4, var5, var2);
        }

    }

    public static void setProperties(Object var0, com.wentuotuo.wtt.util.conf.Properties var1) {
        Iterator var2 = var1.getProperties().iterator();

        while(var2.hasNext()) {
            setPropertyX(var0, (Element)var2.next());
        }

    }

    public static Object createObject(String var0) throws WTTException {
        Object var1 = null;
        Class var2 = null;
        if(StringX.isSpace(var0)) {
            throw new WTTException(21);
        } else {
            try {
                var2 = Class.forName(StringX.trimAll(var0));
            } catch (ClassNotFoundException var6) {
                WTT.getLog().debug("Create object Failed, Class not found!", var6);
                throw new WTTException(22, new String[]{var0}, var6);
            }

            try {
                var1 = var2.newInstance();
                return var1;
            } catch (InstantiationException var4) {
                WTT.getLog().debug("Create object Failed, construct Object error!", var4);
                throw new WTTException(23, new String[]{var0}, var4);
            } catch (IllegalAccessException var5) {
                WTT.getLog().debug("Create object Failed, illegal access!", var5);
                throw new WTTException(24, new String[]{var0}, var5);
            }
        }
    }

    public static boolean equals(Object var0, Object var1) {
        return var0 == null && var1 == null?true:((var0 != null || var1 == null) && (var1 != null || var0 == null)?var0.equals(var1):false);
    }
}
