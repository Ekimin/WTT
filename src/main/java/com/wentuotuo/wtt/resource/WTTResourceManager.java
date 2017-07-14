//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.resource;

import com.wentuotuo.wtt.WTT;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class WTTResourceManager {
    public WTTResourceManager() {
    }

    public static ResourceBundle getResourceBundle() {
        return getResourceBundle(Locale.getDefault());
    }

    public static ResourceBundle getResourceBundle(Locale var0) {
        Object var1 = null;

        try {
            var1 = ResourceBundle.getBundle(System.getProperty("WTT_MESSAGE", "com.wentuotuo.wtt.resource.WTTMessage"), var0);
        } catch (Exception var3) {
            WTT.getLog().warn("Get resource bundle error.", var3);
            var1 = new DefaultResourceBundle(var0);
        }

        return (ResourceBundle)var1;
    }

    public static String getResourceString(String var0) {
        if(var0 == null) {
            return "NULL";
        } else {
            try {
                return getResourceBundle().getString(var0);
            } catch (MissingResourceException var2) {
                return var0;
            }
        }
    }

    public static String getMessage(String var0, Object[] var1) {
        if(var0 == null) {
            return "NULL";
        } else {
            try {
                return var1 == null?getResourceBundle().getString(var0):MessageFormat.format(getResourceBundle().getString(var0), var1);
            } catch (MissingResourceException var3) {
                return var0;
            }
        }
    }

    public static String getResourceString(String var0, Locale var1) {
        if(var0 == null) {
            return "NULL";
        } else {
            try {
                return getResourceBundle(var1).getString(var0);
            } catch (MissingResourceException var3) {
                return var0;
            }
        }
    }

    public static String getMessage(String var0, Locale var1, Object[] var2) {
        if(var0 == null) {
            return "NULL";
        } else {
            try {
                return var2 == null?getResourceBundle(var1).getString(var0):MessageFormat.format(getResourceBundle(var1).getString(var0), var2);
            } catch (MissingResourceException var4) {
                return var0;
            }
        }
    }
}
