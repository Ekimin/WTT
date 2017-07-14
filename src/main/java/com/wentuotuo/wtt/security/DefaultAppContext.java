//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.security;

import com.wentuotuo.wtt.WTT;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class DefaultAppContext implements AppContext {
    private String operator = null;
    private String userId = null;
    private String userName = null;
    private String serverAddress = null;
    private String clientAddress = null;
    private String application = null;
    private String additionalInfo = null;

    public DefaultAppContext() {
    }

    public String getOperator() {
        if(this.operator == null) {
            this.operator = this.getUserId();
        }

        return this.operator;
    }

    public void setOperator(String var1) {
        this.operator = var1;
    }

    public String getUserId() {
        if(this.userId == null) {
            this.userId = System.getProperty("user.name", "unknown");
        }

        return this.userId;
    }

    public void setUserId(String var1) {
        this.userId = var1;
    }

    public String getUserName() {
        if(this.userName == null) {
            this.userName = this.getUserId();
        }

        return this.userName;
    }

    public void setUserName(String var1) {
        this.userName = var1;
    }

    public String getServerAddress() {
        if(this.serverAddress == null) {
            try {
                this.serverAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException var2) {
                WTT.getLog().debug(var2);
            }
        }

        return this.serverAddress;
    }

    public void setServerAddress(String var1) {
        this.serverAddress = var1;
    }

    public String getClientAddress() {
        return this.clientAddress == null?this.getServerAddress():this.clientAddress;
    }

    public void setClientAddress(String var1) {
        this.clientAddress = var1;
    }

    public String getApplication() {
        return this.application == null?Thread.currentThread().toString():this.application;
    }

    public void setApplication(String var1) {
        this.application = var1;
    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
    }

    public void setAdditionalInfo(String var1) {
        this.additionalInfo = var1;
    }
}
