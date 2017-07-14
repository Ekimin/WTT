//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo;

import com.wentuotuo.wtt.lang.DataElement;
import java.util.Date;
import java.util.List;

public interface BizObjectQuery extends ShareTransaction {
    int QUERY_TYPE_SELECT = 1;
    int QUERY_TYPE_UNION = 2;
    int QUERY_TYPE_UPDATE = 3;
    int QUERY_TYPE_DELETE = 4;

    BizObjectManager getBizObjectManager();

    List getResultList() throws JBOException;

    List getResultList(boolean var1) throws JBOException;

    BizObject getSingleResult() throws JBOException;

    BizObject getSingleResult(boolean var1) throws JBOException;

    int executeUpdate() throws JBOException;

    BizObjectQuery setMaxResults(int var1);

    int getMaxResults();

    BizObjectQuery setFirstResult(int var1);

    int getFirstResult();

    BizObjectQuery setParameter(DataElement var1);

    BizObjectQuery setParameter(String var1, int var2);

    BizObjectQuery setParameter(String var1, long var2);

    BizObjectQuery setParameter(String var1, double var2);

    BizObjectQuery setParameter(String var1, String var2);

    BizObjectQuery setParameter(String var1, Date var2);

    BizObjectQuery setParameter(String var1, boolean var2);

    DataElement[] getParameters();

    BizObjectClass getBizObjectClass();

    int getQueryType();

    int getTotalCount() throws JBOException;

    double getSumOf(String var1) throws JBOException;
}
