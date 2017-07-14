//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.jbo.util;

import com.wentuotuo.wtt.jbo.BizObject;
import com.wentuotuo.wtt.jbo.BizObjectQuery;
import com.wentuotuo.wtt.jbo.JBOException;
import java.util.List;

public class BizObjectIterator {
    private boolean forUpdate;
    private BizObjectQuery query;
    private int bufferSize;
    private int nextStartIndex;
    private int currentIndex;
    private List buffer;

    public BizObjectIterator(BizObjectQuery var1) {
        this(var1, false);
    }

    public BizObjectIterator(BizObjectQuery var1, boolean var2) {
        this.forUpdate = false;
        this.query = null;
        this.bufferSize = 800;
        this.nextStartIndex = 0;
        this.currentIndex = -1;
        this.buffer = null;
        this.query = var1;
    }

    public BizObject next() throws JBOException {
        ++this.currentIndex;
        if(this.buffer == null || this.currentIndex >= this.buffer.size()) {
            this.query.setFirstResult(this.nextStartIndex);
            this.query.setMaxResults(this.bufferSize);
            this.buffer = this.query.getResultList(this.isForUpdate());
            if(this.buffer.size() == 0) {
                this.currentIndex = -1;
            } else {
                this.nextStartIndex += this.buffer.size();
                this.currentIndex = 0;
            }
        }

        return this.currentIndex < 0?null:(BizObject)this.buffer.get(this.currentIndex);
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setBufferSize(int var1) {
        this.bufferSize = var1;
    }

    public boolean isForUpdate() {
        return this.forUpdate;
    }
}
