//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.msg;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.log.Log;
import java.util.Properties;

public class LogTraceDispatcher implements Dispatcher {
    private Log logger = null;

    public LogTraceDispatcher() {
    }

    public void dispatchMessage(Message var1) {
        if(!this.isInitOk()) {
            this.logMessage(var1);
        }

    }

    private void logMessage(Message var1) {
        StringBuffer var2 = new StringBuffer("WTT MESSAGE:\n");
        var2.append("CreateTime: ").append(var1.getCreateTime()).append("\n");
        var2.append("Sender: ").append(var1.getSender()).append("\n");
        var2.append("Recipient: ").append(var1.getCreateTime()).append("\n");
        var2.append("Recipients: ").append(var1.getCreateTime()).append("\n");
        this.logger.trace(var2);
    }

    public void init(Properties var1) {
        this.logger = WTT.getLog();
    }

    public void close() {
    }

    public boolean isInitOk() {
        return this.logger != null;
    }
}
