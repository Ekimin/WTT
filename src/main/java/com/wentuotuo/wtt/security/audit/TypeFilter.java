//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.security.audit;

import com.wentuotuo.wtt.lang.StringX;
import java.util.ArrayList;

public class TypeFilter implements Filter {
    private ArrayList acceptTypes = null;

    public TypeFilter() {
    }

    public void setAcceptTypes(String var1) {
        if(!StringX.isSpace(var1)) {
            String[] var2 = var1.split(",");

            for(int var3 = 0; var3 < var2.length; ++var3) {
                this.addAcceptType(var2[var3]);
            }

        }
    }

    public void addAcceptType(String var1) {
        if(this.acceptTypes == null) {
            this.acceptTypes = new ArrayList();
        }

        if(!StringX.isSpace(var1) && !this.acceptTypes.contains(var1)) {
            this.acceptTypes.add(var1);
        }
    }

    public boolean isAudit(AuditRecord var1) {
        return this.acceptTypes == null || this.acceptTypes.contains(var1.getType());
    }
}
