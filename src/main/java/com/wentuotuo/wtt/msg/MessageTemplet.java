//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.msg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageTemplet {
    private String templet = null;
    private String pattern = "\\{\\$[^\\{\\}]+\\}";
    private String timeFormat = "yyyy-MM-dd";

    public MessageTemplet() {
    }

    public String formatMessage(Message var1) {
        String var2 = this.getTimeFormat();
        return this.formatMessage(var1, var2 == null?"yyyy-MM-dd":var2);
    }

    public String formatMessage(Message var1, String var2) {
        String var3 = var2 == null?"yyyy-MM-dd":var2;
        Pattern var4 = Pattern.compile(this.pattern);
        StringBuffer var5 = new StringBuffer();
        Matcher var6 = var4.matcher(this.templet);

        String var10;
        for(SimpleDateFormat var7 = new SimpleDateFormat(var3); var6.find(); var6.appendReplacement(var5, var10)) {
            String var8 = var6.group();
            String var9 = var8.substring(2, var8.length() - 1);
            var10 = null;
            if(var9.equalsIgnoreCase("SUBJECT")) {
                var10 = var1.getSubject();
            } else if(var9.equalsIgnoreCase("BODY")) {
                var10 = var1.getBody();
            } else if(var9.equalsIgnoreCase("SENDER")) {
                var10 = var1.getSender();
            } else if(var9.equalsIgnoreCase("CREATETIME")) {
                if(var1.getCreateTime() != null) {
                    var10 = var7.format(var1.getCreateTime());
                } else {
                    var10 = var7.format(new Date());
                }
            } else if(var9.equalsIgnoreCase("DISPATCHTIME")) {
                if(var1.getDispatchTime() != null) {
                    var10 = var7.format(var1.getDispatchTime());
                } else {
                    var10 = var7.format(new Date());
                }
            } else {
                var10 = var9;
            }
        }

        var6.appendTail(var5);
        return var5.toString();
    }

    public String getTimeFormat() {
        return this.timeFormat;
    }

    public void setTimeFormat(String var1) {
        this.timeFormat = var1;
    }

    public String getTemplet() {
        return this.templet;
    }

    public void setTemplet(String var1) {
        this.templet = var1;
    }
}
