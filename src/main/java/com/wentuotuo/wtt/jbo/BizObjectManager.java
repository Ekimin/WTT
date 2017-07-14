//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import com.wentuotuo.wtt.lang.DataElement;
import java.util.Properties;

public interface BizObjectManager extends ShareTransaction {
    String getId();

    String getDescribe();

    BizObjectClass getManagedClass();

    void setManagedClass(BizObjectClass var1);

    BizObjectKey getBizObjectKey() throws JBOException;

    BizObjectKey getKey() throws JBOException;

    BizObject getBizObject(BizObjectKey var1) throws JBOException;

    BizObject getObject(BizObjectKey var1) throws JBOException;

    BizObject getBizObject(Object var1) throws JBOException;

    BizObject getObject(Object var1) throws JBOException;

    BizObject newObject() throws JBOException;

    BizObject newObject(DataElement[] var1) throws JBOException;

    void saveObject(BizObject var1) throws JBOException;

    void deleteObject(BizObjectKey var1) throws JBOException;

    void deleteObject(BizObject var1) throws JBOException;

    BizObjectQuery createQuery(String var1) throws JBOException;

    Properties getQueryProperties();
}
