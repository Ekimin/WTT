//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.msg;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.StringX;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Messenger {
    private List dispatchers = null;

    public static Messenger getMessenger() {
        Messenger var0 = new Messenger();
        MessengerManager var1 = MessengerManager.getManager();
        var0.init(var1);
        return var0;
    }

    protected Messenger() {
        this.dispatchers = new ArrayList();
    }

    protected void init(MessengerManager var1) {
        String var2 = var1.getProperty("dispatchers");
        if(!StringX.isEmpty(var2)) {
            String[] var3 = var2.split(",");

            for(int var4 = 0; var4 < var3.length; ++var4) {
                String var5 = StringX.trimAll(var3[var4]);

                try {
                    Class var6 = Class.forName(var5);
                    Dispatcher var7 = (Dispatcher)var6.newInstance();
                    var7.init(var1.getProperties());
                    this.addDispatcher(var7);
                } catch (Exception var8) {
                    WTT.getLog().debug(var8);
                }
            }

        }
    }

    public void sendMessage(Message var1) {
        var1.setDispatchTime(new Date());
        this.dispatchMessage(var1);
    }

    public void sendMessage(String var1, String var2, String var3, String var4) {
        Message var5 = new Message();
        var5.setSender(var1);
        var5.setRecipients(var2);
        var5.setSubject(var3);
        var5.setBody(var4);
        this.dispatchMessage(var5);
    }

    public void sendMessage(String var1, String var2, String var3) {
        Message var4 = new Message();
        var4.setRecipients(var1);
        var4.setSubject(var2);
        var4.setBody(var3);
        this.dispatchMessage(var4);
    }

    public void addDispatcher(Dispatcher var1) {
        this.dispatchers.add(var1);
    }

    public void removeDispatcher(Dispatcher var1) {
        this.dispatchers.remove(var1);
    }

    public Dispatcher[] getDispatchers() {
        return (Dispatcher[])this.dispatchers.toArray(new Dispatcher[0]);
    }

    public void close() {
        for(int var1 = 0; var1 < this.dispatchers.size(); ++var1) {
            Dispatcher var2 = (Dispatcher)this.dispatchers.get(var1);
            var2.close();
        }

        this.dispatchers.clear();
    }

    private void dispatchMessage(Message var1) {
        for(int var2 = 0; var2 < this.dispatchers.size(); ++var2) {
            Dispatcher var3 = (Dispatcher)this.dispatchers.get(var2);
            if(var3.isInitOk()) {
                var3.dispatchMessage(var1);
            } else {
                WTT.getLog().trace(var3 + " is not initialized!");
            }
        }

    }

    protected void finalize() throws Throwable {
        super.finalize();
        if(this.dispatchers.size() > 0) {
            for(int var1 = 0; var1 < this.dispatchers.size(); ++var1) {
                Dispatcher var2 = (Dispatcher)this.dispatchers.get(var1);
                var2.close();
            }

            this.dispatchers.clear();
        }

    }
}
