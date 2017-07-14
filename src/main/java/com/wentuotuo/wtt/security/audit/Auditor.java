//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.security.audit;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.WTTException;
import com.wentuotuo.wtt.lang.ObjectX;
import com.wentuotuo.wtt.lang.StringX;
import java.util.ArrayList;
import java.util.List;

public class Auditor {
    private String name = null;
    private Filter filter = null;
    private List handlers = new ArrayList();
    protected static final String GLOBAL_AUDITOR = "_global_auditor";

    protected Auditor(String var1) {
        this.name = var1;
    }

    public String getName() {
        return this.name;
    }

    public Filter getFilter() {
        return this.filter;
    }

    public void setFilter(Filter var1) {
        this.filter = var1;
    }

    public void addHandler(Handler var1) {
        if(var1 != null && !this.handlers.contains(var1)) {
            this.handlers.add(var1);
        }
    }

    public void removeHandler(Handler var1) {
        if(var1 != null) {
            this.handlers.remove(var1);
        }
    }

    public Handler[] getHandlers() {
        return (Handler[])this.handlers.toArray(new Handler[0]);
    }

    public void audit(AuditRecord var1) {
        if(this.filter == null || this.filter.isAudit(var1)) {
            for(int var2 = 0; var2 < this.handlers.size(); ++var2) {
                Handler var3 = (Handler)this.handlers.get(var2);
                Filter var4 = var3.getFilter();
                if(var4 == null || var4.isAudit(var1)) {
                    var3.handle(var1);
                }
            }

        }
    }

    public static Auditor getAuditor() {
        return getAuditor("_global_auditor");
    }

    public static Auditor getAuditor(String var0) {
        AuditManager var1 = AuditManager.getManager();
        Auditor var2 = var1.getAuditor(var0);
        if(var2 == null) {
            var2 = new Auditor(var0);
            Filter var3 = createFilter((String)null, var1);
            if(var3 != null) {
                var2.setFilter(var3);
            }

            String var4 = var1.getProperty("handlers");
            if(!StringX.isSpace(var4)) {
                String[] var5 = var4.split(",");

                for(int var6 = 0; var6 < var5.length; ++var6) {
                    Handler var7 = createHandler(StringX.trimAll(var5[var6]), var1);
                    if(var7 != null) {
                        var2.addHandler(var7);
                    }
                }
            }

            var1.addAuditor(var2);
        }

        return var2;
    }

    private static Handler createHandler(String var0, AuditManager var1) {
        Handler var2 = null;

        try {
            var2 = (Handler)ObjectX.createObject(StringX.trimAll(var0));
            ObjectX.setProperties(var2, var1.getHandlerProperties(StringX.trimAll(var0)), true);
            Filter var3 = createFilter(var0, var1);
            if(var3 != null) {
                var2.setFilter(var3);
            }
        } catch (WTTException var4) {
            WTT.getLog().debug(var4);
        }

        return var2;
    }

    private static Filter createFilter(String var0, AuditManager var1) {
        String var2 = ".filter";
        if(!StringX.isSpace(var0)) {
            var2 = StringX.trimAll(var0) + var2;
        }

        String var3 = var1.getProperty(var2);
        Filter var4 = null;
        if(!StringX.isSpace(var3)) {
            try {
                var4 = (Filter)ObjectX.createObject(StringX.trimAll(var3));
                ObjectX.setProperties(var4, var1.getFilterProperties(StringX.trimAll(var0)), false);
            } catch (WTTException var6) {
                WTT.getLog().debug(var6);
            }
        }

        return var4;
    }
}
