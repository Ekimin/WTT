//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import javax.swing.filechooser.FileFilter;

public class FileFilterByExtension extends FileFilter implements java.io.FileFilter, FilenameFilter {
    private ArrayList extensions = null;
    private String description = null;
    private boolean directoryInclude = true;

    public boolean isDirectoryInclude() {
        return this.directoryInclude;
    }

    public void setDirectoryInclude(boolean var1) {
        this.directoryInclude = var1;
    }

    public FileFilterByExtension() {
        this.extensions = new ArrayList();
    }

    public FileFilterByExtension(String var1) {
        this.extensions = new ArrayList();
        this.description = var1;
    }

    public FileFilterByExtension(String var1, String[] var2) {
        this.extensions = new ArrayList();
        this.description = var1;
        if(var2 != null && var2.length > 0) {
            for(int var3 = 0; var3 < var2.length; ++var3) {
                this.extensions.add(var2[var3]);
            }
        }

    }

    public boolean accept(File var1) {
        if(var1.isDirectory()) {
            return this.isDirectoryInclude();
        } else if(this.extensions.isEmpty()) {
            return false;
        } else {
            String var2 = null;
            String var3 = var1.getName();
            int var4 = var3.lastIndexOf(46);
            if(var4 > 0 && var4 < var3.length() - 1) {
                var2 = var3.substring(var4 + 1).toLowerCase();
            }

            for(int var5 = 0; var5 < this.extensions.size(); ++var5) {
                String var6 = (String)this.extensions.get(var5);
                if(var6.equalsIgnoreCase(var2)) {
                    return true;
                }
            }

            return false;
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String var1) {
        this.description = var1;
    }

    public FileFilterByExtension addExtention(String var1) {
        this.extensions.add(var1);
        return this;
    }

    public boolean accept(File var1, String var2) {
        return this.accept(new File(var1, var2));
    }
}
