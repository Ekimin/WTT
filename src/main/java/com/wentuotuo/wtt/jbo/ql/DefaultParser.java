//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.ql;

import com.wentuotuo.wtt.jbo.BizObjectClass;
import com.wentuotuo.wtt.jbo.JBOException;
import com.wentuotuo.wtt.jbo.JBOFactory;
import com.wentuotuo.wtt.lang.DataElement;
import com.wentuotuo.wtt.lang.StringX;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultParser extends Parser {
    private int queryType = -1;
    private String statement = null;
    private JBOClass majorClass = null;
    private Element[] elementSequence = null;
    private BizObjectClass bizObjectClass = null;
    private Map queryClassesMap = null;
    private String[] attributeList = null;
    private Map attributeLabel = null;
    private static Pattern selectStar = Pattern.compile("^SELECT\\s+(?:O\\.){0,1}\\s*\\*\\s+", 2);

    public DefaultParser() {
    }

    public void parse(BizObjectClass var1, String var2) throws JBOException {
        this.resetResult();

        try {
            this.bizObjectClass = var1;
            if(StringX.isSpace(var2)) {
                this.statement = "SELECT \"*\" FROM O";
            } else {
                this.statement = StringX.trimAll(var2);
                Matcher var3 = selectStar.matcher(this.statement);
                StringBuffer var4 = new StringBuffer();
                if(var3.find()) {
                    var3.appendReplacement(var4, "SELECT \"O.*\" ");
                    var3.appendTail(var4);
                    this.statement = var4.toString();
                }

                var3 = null;
                var4 = null;
            }

            this.majorClass = Parser.createJBOClass(this.bizObjectClass.getAbsoluteName() + " O");
            this.queryClassesMap = new HashMap();
            this.queryClassesMap.put("O", this.majorClass);
            this.attributeLabel = new HashMap();
            List var10 = this.createBaseSequence(this.statement);
            this.queryType = this.parseQueryType(var10);
            this.elementSequence = this.createFinalSequence(var10);
        } catch (JBOException var8) {
            throw var8;
        } finally {
            this.clearWorkVar();
        }

    }

    private void resetResult() {
        this.queryType = -1;
        this.statement = null;
        this.majorClass = null;
        this.elementSequence = null;
        this.bizObjectClass = null;
    }

    private void clearWorkVar() {
        this.queryClassesMap = null;
        this.attributeList = null;
        this.attributeLabel = null;
    }

    private List createBaseSequence(String var1) throws JBOException {
        ArrayList var2 = new ArrayList();
        ElementIterator var3 = new ElementIterator(var1);

        while(var3.hasNext()) {
            Element var4 = var3.next();
            var2.add(var4);
            if(var4.getType() == 4) {
                JBOClass var5 = (JBOClass)var4;
                if(var5.isMajorClass()) {
                    if(var5.getName() == null) {
                        var5.setName(this.bizObjectClass.getAbsoluteName());
                    }
                } else {
                    BizObjectClass var6 = JBOFactory.getFactory().getClass(var5.getName());
                    if(var6 == null) {
                        throw JBOException.QLError(1412, var1, var5.getOfficalExpression());
                    }
                }

                if(!this.queryClassesMap.containsKey(var5.getAlias())) {
                    this.queryClassesMap.put(var5.getAlias(), var5);
                }
            }
        }

        return var2;
    }

    private int parseQueryType(List var1) throws JBOException {
        boolean var2 = true;
        if(var1.size() < 1) {
            throw new JBOException(1419);
        } else {
            Element var3 = (Element)var1.get(0);
            byte var5;
            if(var3.getType() == 0) {
                String var4 = var3.getOfficalExpression();
                if(var4.equals("SELECT")) {
                    var5 = 1;
                } else if(var4.equals("UPDATE")) {
                    var5 = 3;
                } else {
                    if(!var4.equals("DELETE")) {
                        throw JBOException.QLError(1415, this.statement, var4);
                    }

                    var5 = 4;
                }
            } else {
                var5 = 1;
                var1.add(0, Parser.createKeyWord("SELECT"));
                var1.add(1, Parser.createJBOAttribute("\"*\""));
                var1.add(2, Parser.createKeyWord("FROM"));
                var1.add(3, Parser.createJBOClass("O"));
                var1.add(4, Parser.createKeyWord("WHERE"));
            }

            return var5;
        }
    }

    private Element[] createFinalSequence(List var1) throws JBOException {
        ArrayList var2 = new ArrayList();
        byte var3 = 2;

        for(int var4 = 0; var4 < var1.size(); ++var4) {
            Element var5 = (Element)var1.get(var4);
            var2.add(var5);
            if(var5.getType() == 0) {
                String var6 = var5.getOfficalExpression();
                if(var6.equals("SELECT")) {
                    var3 = 0;
                } else if(var6.equals("ON")) {
                    var3 = 5;
                } else if(var6.equals("ORDER BY")) {
                    var3 = 3;
                } else if(var6.equals("GROUP BY")) {
                    var3 = 4;
                } else if(var6.equals("SET")) {
                    var3 = 1;
                } else if(var6.equals("WHERE")) {
                    var3 = 2;
                } else if(var6.equals("UNION")) {
                    this.queryType = 2;
                } else {
                    Element var7;
                    if(var6.equals("UPDATE")) {
                        var7 = (Element)var1.get(var4 + 1);
                        if(var7.getType() != 4) {
                            var2.add(Parser.createJBOClass("O"));
                        }
                    } else if(var6.equals("DELETE")) {
                        var7 = (Element)var1.get(var4 + 1);
                        if(!var7.getOfficalExpression().equals("FROM")) {
                            var2.add(Parser.createKeyWord("FROM"));
                            var2.add(Parser.createJBOClass("O"));
                        }
                    }
                }
            }

            if(var5.getType() == 5) {
                JBOAttribute var8 = (JBOAttribute)var5;
                var8.setUsedIn(var3);
                if(!this.isValidAttribute(var8)) {
                    throw JBOException.QLError(1413, this.statement, var5.getOfficalExpression());
                }

                int var9 = var8.getAttributeType();
                if(var9 == 0) {
                    var8.setJboClass(this.majorClass);
                } else if(var9 == 1) {
                    var8.setJboClass((JBOClass)this.queryClassesMap.get(var8.getClassAlias()));
                } else {
                    var8.setJboClass((JBOClass)null);
                }
            }
        }

        return (Element[])var2.toArray(new Element[0]);
    }

    private boolean isValidAttribute(JBOAttribute var1) throws JBOException {
        if(var1.isExpression()) {
            return true;
        } else if(var1.getAttributeType() == 2) {
            return true;
        } else {
            if(this.attributeList == null) {
                JBOFactory var2 = JBOFactory.getFactory();
                ArrayList var3 = new ArrayList();
                JBOClass[] var4 = (JBOClass[])this.queryClassesMap.values().toArray(new JBOClass[0]);

                for(int var5 = 0; var5 < var4.length; ++var5) {
                    DataElement[] var6;
                    int var7;
                    if(var4[var5].isMajorClass()) {
                        var6 = this.bizObjectClass.getAttributes();

                        for(var7 = 0; var7 < var6.length; ++var7) {
                            var3.add(var4[var5].getAlias() + "." + var6[var7].getName().toUpperCase());
                        }
                    } else {
                        var6 = var2.getClass(var4[var5].getName()).getAttributes();

                        for(var7 = 0; var7 < var6.length; ++var7) {
                            String var8 = var4[var5].getAlias() + "." + var6[var7].getName().toUpperCase();
                            var3.add(var8);
                            this.attributeLabel.put(var8, var6[var7].getLabel());
                        }
                    }
                }

                this.attributeList = (String[])var3.toArray(new String[0]);
                Arrays.sort(this.attributeList);
            }

            int var9 = Arrays.binarySearch(this.attributeList, var1.getOfficalExpression().toUpperCase());
            if(var9 >= 0) {
                if(var1.getAttributeType() == 1 && var1.getUsedIn() == 0 && var1.getLabel() == null) {
                    String var10 = (String)this.attributeLabel.get(this.attributeList[var9]);
                    if(var10 != null) {
                        var1.setLabel(var10);
                    }
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public BizObjectClass getBizObjectClass() {
        return this.bizObjectClass;
    }

    public Element[] getElementSequence() {
        return this.elementSequence;
    }

    public int getQueryType() {
        return this.queryType;
    }

    public String getStatement() {
        return this.statement;
    }

    public JBOClass getMajorClass() {
        return this.majorClass;
    }
}
