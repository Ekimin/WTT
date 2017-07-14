//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util.json;

import com.wentuotuo.wtt.lang.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompositeFilter implements ElementFilter {
    private List filters = new ArrayList();

    public CompositeFilter(List var1) {
        if(var1 != null) {
            this.filters.addAll(var1);
        }

    }

    public CompositeFilter(ElementFilter[] var1) {
        if(var1 != null) {
            this.filters.addAll(Arrays.asList(var1));
        }

    }

    public CompositeFilter() {
    }

    public CompositeFilter add(ElementFilter var1) {
        this.filters.add(var1);
        return this;
    }

    public List getFilters() {
        return this.filters;
    }

    public boolean accept(Element var1) {
        boolean var2 = true;

        for(int var3 = 0; var3 < this.filters.size() && (var2 = ((ElementFilter)this.filters.get(var3)).accept(var1)); ++var3) {
            ;
        }

        return var2;
    }
}
