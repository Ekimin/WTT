//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.msg;

import com.wentuotuo.wtt.lang.StringX;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private int level = 5;
    private String id = null;
    private String sender = null;
    private List recipients = null;
    private String subject = null;
    private String body = null;
    private String mimeType = "text/plain";
    private Date createTime = null;
    private Date dispatchTime = null;
    private Date receiveTime = null;

    public Message() {
        this.createTime = new Date();
        this.recipients = new ArrayList();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String var1) {
        this.id = var1;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String var1) {
        this.sender = var1;
    }

    public List getRecipients() {
        return this.recipients;
    }

    public void setRecipients(List var1) {
        this.recipients = var1;
        if(var1 == null) {
            this.recipients = new ArrayList();
        }

    }

    public void setRecipients(String var1) {
        if(var1 == null) {
            this.recipients = new ArrayList();
        } else {
            this.recipients.clear();
        }

        if(var1 != null) {
            String var2 = "[,;；，]+";
            String[] var3 = StringX.trimAll(var1).split(var2);
            this.recipients.addAll(Arrays.asList(var3));
        }
    }

    public void addRecipient(String var1) {
        this.recipients.add(var1);
    }

    public void removeRecipient(String var1) {
        this.recipients.remove(var1);
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String var1) {
        this.subject = var1;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String var1) {
        this.body = var1;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String var1) {
        this.mimeType = var1;
    }

    public Date getReceiveTime() {
        return this.receiveTime;
    }

    public void setReceiveTime(Date var1) {
        this.receiveTime = var1;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Date getDispatchTime() {
        return this.dispatchTime;
    }

    public void setDispatchTime(Date var1) {
        this.dispatchTime = var1;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int var1) {
        this.level = var1;
    }
}
