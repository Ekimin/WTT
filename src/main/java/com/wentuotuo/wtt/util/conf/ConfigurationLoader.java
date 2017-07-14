//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.conf;

import com.wentuotuo.wtt.WTTException;

public interface ConfigurationLoader {
    Configuration loadConfiguration(Object var1) throws WTTException;

    void saveConfiguration(Configuration var1, Object var2) throws WTTException;
}
