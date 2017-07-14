//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.msg;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.io.FileTool;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MessengerManager {
    public static final String CONFIG_FILE_PROPERTY = "com.wentuotuo.wtt.msg.Messenger.configFile";
    private Properties properties = null;
    private static MessengerManager manager = null;

    protected MessengerManager() {
        this.properties = new Properties();
    }

    public void readConfiguration(InputStream var1) throws IOException {
        this.properties.clear();
        this.properties.load(var1);
    }

    public String getProperty(String var1) {
        return this.properties.getProperty(var1);
    }

    public Properties getProperties() {
        return this.properties;
    }

    public static MessengerManager getManager() {
        if(manager == null) {
            manager = new MessengerManager();
            String var0 = System.getProperty("com.wentuotuo.wtt.msg.Messenger.configFile");
            if(var0 == null) {
                var0 = System.getProperty("com.wentuotuo.wtt.msg.config.file");
            }

            if(var0 == null) {
                var0 = "etc/messenger.properties";
            }

            try {
                File var1 = FileTool.findFile(var0);
                if(var1 != null) {
                    manager.readConfiguration(new FileInputStream(var1));
                }
            } catch (IOException var2) {
                WTT.getLog().debug(var2);
            }
        }

        return manager;
    }
}
