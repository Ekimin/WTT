//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.json;

import com.wentuotuo.wtt.WTT;
import com.wentuotuo.wtt.lang.Element;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class NamePatternFilter implements ElementFilter {
    private String pattern = null;
    private Pattern compiledPattern = null;
    private boolean refuseList = true;

    public NamePatternFilter(String var1, boolean var2) {
        this.pattern = var1;
        this.compiledPattern = this.compilePattern(this.pattern);
        this.refuseList = var2;
    }

    public NamePatternFilter(String var1) {
        this.pattern = var1;
        this.compiledPattern = this.compilePattern(this.pattern);
    }

    public NamePatternFilter(boolean var1) {
        this.refuseList = var1;
    }

    public boolean accept(Element var1) {
        return var1 == null?false:(this.compiledPattern == null?false:(this.refuseList?this.compiledPattern.matcher(var1.getName()).matches():!this.compiledPattern.matcher(var1.getName()).matches()));
    }

    public final String getPattern() {
        return this.pattern;
    }

    public final void setPattern(String var1) {
        this.pattern = var1;
        this.compiledPattern = this.compilePattern(var1);
    }

    public boolean isRefuseList() {
        return this.refuseList;
    }

    public void setRefuseList(boolean var1) {
        this.refuseList = var1;
    }

    private Pattern compilePattern(String var1) {
        if(var1 == null) {
            return null;
        } else {
            Pattern var2 = null;

            try {
                var2 = Pattern.compile(var1);
            } catch (PatternSyntaxException var4) {
                WTT.getLog().warn("Invalid name pattern, always return false", var4);
            }

            return var2;
        }
    }
}
