//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.io;

import com.wentuotuo.wtt.lang.StringX;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;
import javax.swing.filechooser.FileFilter;

public class FileFilterByName extends FileFilter implements java.io.FileFilter, FilenameFilter {
    private String namePattern = null;
    private Pattern[] pattern = null;
    private String description = null;
    private boolean directoryInclude = true;

    public boolean isDirectoryInclude() {
        return this.directoryInclude;
    }

    public void setDirectoryInclude(boolean var1) {
        this.directoryInclude = var1;
    }

    public FileFilterByName() {
        this.namePattern = ".*";
    }

    public FileFilterByName(String var1) {
        this.namePattern = ".*";
        this.description = var1;
    }

    public FileFilterByName(String var1, String var2) {
        this.namePattern = var2;
        this.description = var1;
    }

    public boolean accept(File var1) {
        if(var1.isDirectory() && !this.isDirectoryInclude()) {
            return false;
        } else {
            if(this.pattern == null) {
                String[] var2 = this.namePattern.split("[;,]");
                this.pattern = new Pattern[var2.length];

                for(int var3 = 0; var3 < var2.length; ++var3) {
                    this.pattern[var3] = Pattern.compile(StringX.trimAll(var2[var3]));
                }
            }

            String var5 = var1.getName();
            if(this.pattern.length < 1) {
                return false;
            } else {
                boolean var6 = false;

                for(int var4 = 0; var4 < this.pattern.length; ++var4) {
                    if(this.pattern[var4].matcher(var5).matches()) {
                        var6 = true;
                        break;
                    }
                }

                return var6;
            }
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String var1) {
        this.description = var1;
    }

    public String getNamePattern() {
        return this.namePattern;
    }

    public void setNamePattern(String var1) {
        this.namePattern = var1;
        this.pattern = null;
    }

    public boolean accept(File var1, String var2) {
        return this.accept(new File(var1, var2));
    }
}
