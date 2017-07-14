//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.msg;

import com.wentuotuo.wtt.WTT;
import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SMTPDispatcher implements Dispatcher {
    Properties properties = new Properties();
    protected Session session = null;
    protected Transport globalTransport = null;
    private boolean useGlobalTransport = true;
    private String charset = null;
    private boolean initOk = false;

    public SMTPDispatcher() {
    }

    public void dispatchMessage(Message var1) {
        javax.mail.Message var2 = this.getMailMessage(var1, this.session);
        if(var2 == null) {
            WTT.getLog().debug("Dispatch message failed, create message failed!");
        } else {
            try {
                if(this.isUseGlobalTransport()) {
                    if(this.globalTransport == null || !this.globalTransport.isConnected()) {
                        throw new MessagingException("global transport not created or closed!");
                    }

                    this.globalTransport.sendMessage(var2, var2.getAllRecipients());
                } else {
                    Transport var3 = this.session.getTransport("smtp");
                    var3.connect(this.getMailAddress(var1.getSender()), this.getPassword(var1.getSender()));
                    var3.sendMessage(var2, var2.getAllRecipients());
                    var3.close();
                }

                WTT.getLog().debug("Dispatch message succesful!");
            } catch (MessagingException var4) {
                WTT.getLog().error("Dispatch message failed, send message failed!", var4);
            }

        }
    }

    private javax.mail.Message getMailMessage(Message var1, Session var2) {
        MimeMessage var3 = null;

        try {
            InternetAddress[] var4 = this.getMailTo(var1);
            if(var4.length > 0) {
                var3 = new MimeMessage(var2);
                var3.setFrom(new InternetAddress(this.getMailAddress(var1.getSender())));
                var3.setRecipients(RecipientType.TO, var4);
                var3.setSentDate(new Date());
                var3.setSubject(var1.getSubject());
                var3.setContent(var1.getBody(), this.getContentType(var1));
            }
        } catch (Exception var5) {
            WTT.getLog().debug(var5);
        }

        return var3;
    }

    private InternetAddress[] getMailTo(Message var1) throws AddressException {
        List var2 = var1.getRecipients();
        ArrayList var3 = new ArrayList();

        for(int var4 = 0; var4 < var2.size(); ++var4) {
            String var5 = this.getMailAddress((String)var2.get(var4));
            if(var5 != null && var5.indexOf(64) > 0 && var5.indexOf(64) < var5.length()) {
                var3.add(new InternetAddress(var5));
            } else {
                WTT.getLog().debug("Invalid Mail Adress. original: " + var2.get(var4) + ",converted: " + var5);
            }
        }

        return (InternetAddress[])var3.toArray(new InternetAddress[0]);
    }

    private String getContentType(Message var1) {
        if(this.charset == null) {
            this.charset = this.properties.getProperty("mail.mime.charset");
            if(this.charset == null) {
                this.charset = System.getProperty("file.encoding", "GBK");
            }
        }

        String var2 = var1.getMimeType();
        return (var2 == null?"text/plain":var2) + this.charset;
    }

    public void init(Properties var1) {
        Enumeration var2 = var1.keys();

        while(var2.hasMoreElements()) {
            String var3 = (String)var2.nextElement();
            if(var3.startsWith("mail.")) {
                this.properties.setProperty(var3, var1.getProperty(var3));
            }
        }

        if(WTT.getLog().isTraceEnabled()) {
            CharArrayWriter var4 = new CharArrayWriter();
            this.getProperties().list(new PrintWriter(var4));
            WTT.getLog().trace(var4.toString());
        }

        this.initSession(var1);
        this.setInitOk(true);
    }

    private void initSession(Properties var1) {
        this.session = Session.getInstance(var1);
        if(this.isUseGlobalTransport()) {
            String var2 = this.properties.getProperty("mail.smtp.user");
            String var3 = null;
            if(var2 == null) {
                var2 = this.properties.getProperty("mail.user");
                var3 = this.properties.getProperty("mail.password");
            } else {
                var3 = this.properties.getProperty("mail.smtp.password");
            }

            try {
                this.globalTransport = this.session.getTransport("smtp");
                this.globalTransport.connect(var2, var3);
            } catch (Exception var5) {
                WTT.getLog().warn("Create Gloabal Transport error, local transport enabled! ", var5);
                this.setUseGlobalTransport(false);
                this.globalTransport = null;
            }
        }

    }

    public void close() {
        if(this.globalTransport != null) {
            try {
                this.globalTransport.close();
            } catch (MessagingException var2) {
                WTT.getLog().debug(var2);
            }

            this.globalTransport = null;
        }

        this.session = null;
        this.setInitOk(false);
    }

    protected String getMailAddress(String var1) {
        return var1;
    }

    protected String getPassword(String var1) {
        return null;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public boolean isUseGlobalTransport() {
        return this.useGlobalTransport;
    }

    public void setUseGlobalTransport(boolean var1) {
        this.useGlobalTransport = var1;
    }

    public boolean isInitOk() {
        return this.initOk;
    }

    protected void setInitOk(boolean var1) {
        this.initOk = var1;
    }
}
