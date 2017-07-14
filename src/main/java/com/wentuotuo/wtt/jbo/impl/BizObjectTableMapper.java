//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.impl;

public interface BizObjectTableMapper {
    int CASE_MIX = 0;
    int CASE_UPPER = 1;
    int CASE_LOWER = 2;

    String getTable();

    String getColumnOfAttribute(String var1);

    String getFilterColumn();

    String getFilterValue();

    int getTableNameCase();

    int getColumnNameCase();

    char getIdentifierQuoteCharacter();
}
