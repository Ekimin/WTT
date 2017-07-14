//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.msg;

import java.util.Properties;

public interface Dispatcher {
    boolean isInitOk();

    void init(Properties var1);

    void dispatchMessage(Message var1);

    void close();
}
