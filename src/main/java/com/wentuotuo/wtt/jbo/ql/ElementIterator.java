//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

import com.wentuotuo.wtt.jbo.JBOException;
import com.wentuotuo.wtt.lang.StringX;

public class ElementIterator {
    private char[] buffer = null;
    private int pointer = 0;
    private int elementStartIndex = -1;
    private Element currentElement = null;
    private int openedParenthesis = 0;

    public ElementIterator(String var1) {
        this.buffer = StringX.trimAll(var1).toCharArray();
    }

    public boolean hasNext() throws JBOException {
        this.currentElement = null;
        this.elementStartIndex = this.toNonWhiteChar();
        if(this.isEnd()) {
            if(this.openedParenthesis > 0) {
                throw JBOException.QLError(1403, new String(this.buffer), (String)null);
            } else if(this.openedParenthesis < 0) {
                throw JBOException.QLError(1402, new String(this.buffer), (String)null);
            } else {
                this.closeBuffer();
                return false;
            }
        } else {
            char var1 = this.buffer[this.elementStartIndex];
            int var2;
            String var3;
            if(var1 == 58) {
                var2 = this.toWordEnd();
                var3 = new String(this.buffer, this.elementStartIndex, var2 - this.elementStartIndex);
                if(!BaseElement.isValidJBOIdentfier(var3.substring(1))) {
                    throw JBOException.QLError(1410, new String(this.buffer), var3);
                } else {
                    this.currentElement = Parser.createParameter(var3);
                    return true;
                }
            } else if(Operator.isOperatorChar(var1)) {
                var2 = this.toOperatorEnd();
                var3 = new String(this.buffer, this.elementStartIndex, var2 - this.elementStartIndex);
                if(var3.equals(")")) {
                    --this.openedParenthesis;
                } else {
                    if(var2 == this.buffer.length) {
                        throw JBOException.QLError(1406, new String(this.buffer), var3);
                    }

                    if(var3.equals("(")) {
                        ++this.openedParenthesis;
                    }
                }

                this.currentElement = Parser.createOperator(var3);
                return true;
            } else if(var1 == 39) {
                var2 = this.toStringEnd();
                if(var2 < 0) {
                    throw JBOException.QLError(1404, new String(this.buffer), new String(this.buffer, var1, this.buffer.length));
                } else {
                    this.currentElement = Parser.createConstant(new String(this.buffer, this.elementStartIndex, var2 - this.elementStartIndex));
                    return true;
                }
            } else if(var1 == 34) {
                var2 = this.toAttributeExpressionEnd();
                if(var2 < 0) {
                    throw JBOException.QLError(1405, new String(this.buffer), new String(this.buffer, var1, this.buffer.length));
                } else {
                    var3 = new String(this.buffer, this.elementStartIndex, var2 - this.elementStartIndex);
                    this.currentElement = Parser.createJBOAttribute(var3);
                    return true;
                }
            } else if(Character.isDigit(var1)) {
                var2 = this.toNumberEnd();
                var3 = new String(this.buffer, this.elementStartIndex, var2 - this.elementStartIndex);

                try {
                    Double.parseDouble(var3);
                } catch (NumberFormatException var7) {
                    throw JBOException.QLError(1416, new String(this.buffer), var3);
                }

                this.currentElement = Parser.createConstant(var3);
                return true;
            } else {
                var2 = this.toWordEnd();
                var3 = new String(this.buffer, this.elementStartIndex, var2 - this.elementStartIndex);
                if(Parser.isKeyWord(var3)) {
                    if(this.isEnd() && !var3.equalsIgnoreCase("NULL") && !var3.equalsIgnoreCase("ASC") && !var3.equalsIgnoreCase("DESC")) {
                        throw JBOException.QLError(1407, new String(this.buffer), var3);
                    } else {
                        this.currentElement = Parser.createKeyWord(var3);
                        return true;
                    }
                } else if(Parser.isFunction(var3)) {
                    if(!this.isEnd() && this.buffer[var2] == 40) {
                        this.currentElement = Parser.createJBOFunction(var3);
                        return true;
                    } else {
                        throw JBOException.QLError(1417, new String(this.buffer), var3);
                    }
                } else if(var3.equalsIgnoreCase("O")) {
                    this.currentElement = Parser.createJBOClass(var3);
                    return true;
                } else if(!var3.startsWith("jbo.") && !var3.matches("[\\w]+\\.[\\w]+\\.[\\w]+[\\w\\.]*")) {
                    this.currentElement = Parser.createJBOAttribute(var3);
                    return true;
                } else {
                    int var4 = this.toNonWhiteChar();
                    if(this.isEnd()) {
                        throw JBOException.QLError(1418, new String(this.buffer), var3);
                    } else {
                        int var5 = this.toWordEnd();
                        String var6 = new String(this.buffer, var4, var5 - var4);
                        if(!BaseElement.isValidJBOIdentfier(var6)) {
                            throw JBOException.QLError(1411, new String(this.buffer), var6);
                        } else {
                            this.currentElement = Parser.createJBOClass(var3 + " " + var6);
                            return true;
                        }
                    }
                }
            }
        }
    }

    private int toAttributeExpressionEnd() {
        int var1 = -1;
        ++this.pointer;

        while(this.pointer < this.buffer.length && this.buffer[this.pointer] != 34) {
            ++this.pointer;
        }

        if(this.pointer < this.buffer.length) {
            ++this.pointer;
            var1 = this.pointer;
        }

        return var1;
    }

    private int toNonWhiteChar() {
        while(this.pointer < this.buffer.length && Character.isSpaceChar(this.buffer[this.pointer])) {
            ++this.pointer;
        }

        return this.pointer;
    }

    private int toOperatorEnd() {
        int var1 = this.pointer++;
        if(this.pointer < this.buffer.length && Operator.isOperator(new String(this.buffer, var1, this.pointer + 1 - var1))) {
            ++this.pointer;
        }

        return this.pointer;
    }

    private int toWordEnd() {
        ++this.pointer;

        while(this.pointer < this.buffer.length && !Character.isSpaceChar(this.buffer[this.pointer]) && !Operator.isOperatorChar(this.buffer[this.pointer])) {
            ++this.pointer;
        }

        return this.pointer;
    }

    private int toStringEnd() {
        int var1 = -1;
        ++this.pointer;

        while(this.pointer < this.buffer.length && this.buffer[this.pointer] != 39) {
            ++this.pointer;
        }

        if(this.pointer < this.buffer.length) {
            ++this.pointer;
            var1 = this.pointer;
        }

        return var1;
    }

    private int toNumberEnd() {
        ++this.pointer;

        while(this.pointer < this.buffer.length && (Character.isDigit(this.buffer[this.pointer]) || this.buffer[this.pointer] == 46)) {
            ++this.pointer;
        }

        return this.pointer;
    }

    private boolean isEnd() {
        return this.pointer >= this.buffer.length;
    }

    private void closeBuffer() {
        this.elementStartIndex = 0;
        this.pointer = 0;
        this.currentElement = null;
    }

    public Element next() {
        return this.currentElement;
    }
}
