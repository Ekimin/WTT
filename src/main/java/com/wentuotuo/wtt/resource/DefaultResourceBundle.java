//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.resource;

import com.wentuotuo.wtt.WTT;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DefaultResourceBundle extends ResourceBundle {
    private ResourceBundle internalBundle = null;

    public DefaultResourceBundle(Locale var1) {
        try {
            this.internalBundle = ResourceBundle.getBundle("com.wentuotuo.wtt.resource.WTTMessage", var1);
        } catch (MissingResourceException var3) {
            WTT.getLog().debug(var3);
            this.internalBundle = new WTTMessage_en();
        }

    }

    protected Object handleGetObject(String var1) {
        Object var2 = null;

        try {
            var2 = this.internalBundle.getObject(var1);
        } catch (MissingResourceException var4) {
            WTT.getLog().debug("Resouce  of" + var1 + " not exists in default resource boundle!", var4);
            var2 = var1;
        }

        if(var2 == null) {
            WTT.getLog().debug("Resouce  of" + var1 + " not exists in default resource boundle!");
            var2 = var1;
        }

        return var2;
    }

    public Enumeration getKeys() {
        return this.internalBundle.getKeys();
    }
}
