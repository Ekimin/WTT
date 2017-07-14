//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.msg;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.StringX;
import com.wentuotuo.wtt.log.Log;
import java.util.List;
import java.util.Properties;

public class SMSDispatcher implements Dispatcher {
    private int retryTimes = 3;
    private boolean initOk = false;
    protected Log logger = WTT.getLog(this.getClass().getName());
    private String templet = "<{$SUBJECT}>{$BODY}[{$SENDER}]";

    public SMSDispatcher() {
    }

    public void dispatchMessage(Message var1) {
        List var2 = var1.getRecipients();
        if(var2.size() < 1) {
            this.logger.debug("Dispatch failed, No recipient!");
        } else {
            for(int var3 = 0; var3 < var2.size(); ++var3) {
                String var4 = this.getPhoneNumber((String)var2.get(var3));
                if(!this.checkPhoneNumber(var4)) {
                    WTT.getLog().debug("Invalid phone number: " + var4);
                } else {
                    int var5 = 1;

                    do {
                        if(this.logger.isTraceEnabled()) {
                            this.logger.trace("Try send message to " + var4 + " " + var5 + " times.");
                        }

                        if(this.sendMessage(StringX.trimAll(var4), this.createMessageContent(var1))) {
                            if(this.logger.isTraceEnabled()) {
                                this.logger.trace("Send message to " + var4 + " succesfull!");
                            }
                            break;
                        }

                        this.logger.debug("Send message to " + var4 + " failed!");
                        ++var5;
                    } while(var5 <= this.retryTimes);
                }
            }

        }
    }

    protected boolean checkPhoneNumber(String var1) {
        if(StringX.isSpace(var1)) {
            return false;
        } else {
            String var2 = StringX.trimAll(var1);
            return var2.matches("1\\d{11}");
        }
    }

    public void init(Properties var1) {
        this.setInitOk(true);
    }

    protected boolean sendMessage(String var1, String var2) {
        this.logger.trace(var1 + " " + var2);
        this.logger.warn("Mobile message dispatcher not implement, should remove from messenger configuration or implement it!");
        return true;
    }

    protected String createMessageContent(Message var1) {
        String var2 = null;
        if(this.getTemplet() != null) {
            MessageTemplet var3 = new MessageTemplet();
            var3.setTemplet(this.getTemplet());
            var2 = var3.formatMessage(var1);
        } else {
            var2 = var1.getBody();
        }

        return var2;
    }

    public void close() {
        this.setInitOk(false);
    }

    protected String getPhoneNumber(String var1) {
        return var1;
    }

    public int getRetryTimes() {
        return this.retryTimes;
    }

    public void setRetryTimes(int var1) {
        this.retryTimes = var1;
    }

    public boolean isInitOk() {
        return this.initOk;
    }

    protected void setInitOk(boolean var1) {
        this.initOk = var1;
    }

    public String getTemplet() {
        return this.templet;
    }

    public void setTemplet(String var1) {
        this.templet = var1;
    }
}
