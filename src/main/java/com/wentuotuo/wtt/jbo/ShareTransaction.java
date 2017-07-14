//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import com.wentuotuo.wtt.security.AppContext;

public interface ShareTransaction {
    String getDatabase();

    void registerTransaction(JBOTransaction var1) throws JBOException;

    JBOTransaction getTransaction();

    void transactionComplete();

    void setAppContext(AppContext var1);
}
