//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

public interface Element {
    String VALID_JBO_IDENTIFIER = "[a-zA-Z]\\w*";
    int KEYWORD = 0;
    int CONSTANT = 1;
    int OPERATOR = 2;
    int FUNCTION = 3;
    int JBOCLASS = 4;
    int JBOATTRIBUTE = 5;
    int PARAMETER = 6;

    int getType();

    String getExpression();

    String getOfficalExpression();
}
