//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.security.audit;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.log.Log;
import com.wentuotuo.wtt.security.AppContext;
import com.wentuotuo.wtt.security.DefaultAppContext;

public class LogHandler extends Handler {
    private String logLevel = "TRACE";
    private int level = 1;

    public LogHandler() {
    }

    public void handle(AuditRecord var1) {
        Log var2 = WTT.getLog("com.wentuotuo.wtt.secutiy.audit");
        switch(this.level) {
            case 1:
                if(var2.isTraceEnabled()) {
                    var2.trace(this.getMessage(var1));
                }
                break;
            case 2:
                if(var2.isDebugEnabled()) {
                    var2.debug(this.getMessage(var1));
                }
                break;
            case 3:
                if(var2.isInfoEnabled()) {
                    var2.info(this.getMessage(var1));
                }
                break;
            case 4:
                if(var2.isWarnEnabled()) {
                    var2.warn(this.getMessage(var1));
                }
                break;
            case 5:
                if(var2.isErrorEnabled()) {
                    var2.error(this.getMessage(var1));
                }
                break;
            case 6:
                if(var2.isFatalEnabled()) {
                    var2.fatal(this.getMessage(var1));
                }
        }

    }

    private String getMessage(AuditRecord var1) {
        StringBuffer var2 = new StringBuffer("[AUDIT]");
        this.appendAppContext(var2, var1.getAppContext());
        this.appendAuditHeader(var2, var1);
        this.appendAuditData(var2, var1.getData());
        return var2.toString();
    }

    public void close() {
    }

    private void appendAppContext(StringBuffer var1, AppContext var2) {
        if(var2 == null) {
            var2 = new DefaultAppContext();
        }

        var1.append("{OPERATOR=").append(((AppContext)var2).getOperator());
        var1.append(",SERVER_ADDRESS=").append(((AppContext)var2).getServerAddress());
        var1.append(",CLIENT_ADDRESS=").append(((AppContext)var2).getClientAddress());
        var1.append(",APPLICATION=").append(((AppContext)var2).getApplication());
        var1.append(",ADDITIONAL_INFO=").append(((AppContext)var2).getAdditionalInfo());
        var1.append("}");
    }

    private void appendAuditHeader(StringBuffer var1, AuditRecord var2) {
        var1.append("{").append(var2.getType()).append("}");
        var1.append("{").append(var2.getAction()).append("}");
        var1.append("{").append(var2.getTarget()).append("}");
    }

    private void appendAuditData(StringBuffer var1, AuditData var2) {
        var1.append(var2 == null?"{}":var2.exportText());
    }

    public String getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(String var1) {
        this.logLevel = var1;
        if(var1 == null) {
            this.level = 0;
        }

        String[] var2 = new String[]{"TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL"};

        for(int var3 = 0; var3 < var2.length; ++var3) {
            if(var2[var3].equalsIgnoreCase(var1)) {
                this.level = var3 + 1;
                break;
            }
        }

    }
}
