//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import com.wentuotuo.wtt.security.AppContext;
import com.wentuotuo.wtt.sql.Connection;

public interface JBOTransaction {
    Connection getConnection(ShareTransaction var1) throws JBOException;

    void commit() throws JBOException;

    void rollback() throws JBOException;

    void join(ShareTransaction var1) throws JBOException;

    void setAppContext(AppContext var1);
}
