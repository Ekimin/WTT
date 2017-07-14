//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

import com.wentuotuo.wtt.lang.StringX;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringDecoratorHandler implements Handler {
    private boolean truncateSize = true;
    private String truncateFields = null;
    private String replaceFields = null;
    private LinkedHashMap regexes = new LinkedHashMap(4);
    private Pattern[] pattern = null;
    private String[] replacement = null;
    private int[] fieldSize = null;
    private boolean[] replaceFlag = null;

    public StringDecoratorHandler() {
    }

    public void end(RecordSet var1) throws RecordSetException {
    }

    public void handleRecord(RecordSet var1, Record var2) throws RecordSetException {
        Field[] var3 = var2.getFields();
        if(this.pattern != null) {
            this.replaceRegex(var3);
        }

        if(this.isTruncateSize()) {
            this.truncateSize(var3);
        }

    }

    public void start(RecordSet var1) throws RecordSetException {
        if(!this.regexes.isEmpty()) {
            this.pattern = new Pattern[this.regexes.size()];
            this.replacement = new String[this.regexes.size()];
            Iterator var2 = this.regexes.keySet().iterator();

            for(int var3 = 0; var2.hasNext(); ++var3) {
                String var4 = (String)var2.next();

                try {
                    this.pattern[var3] = Pattern.compile(var4);
                } catch (PatternSyntaxException var6) {
                    throw new RecordSetException("Regex syntax error: " + var4, var6);
                }

                this.replacement[var3] = (String)this.regexes.get(var4);
            }
        }

        Field[] var7 = var1.getRecordTemplet().getFields();
        String var8;
        int var9;
        if(this.isTruncateSize()) {
            var8 = this.getTruncateFields();
            this.fieldSize = new int[var7.length];
            if(var8 == null) {
                var8 = "*";
            }

            var8 = var8.replaceAll("\\s", "").toUpperCase();
            if(var8.equals("*")) {
                for(var9 = 0; var9 < var7.length; ++var9) {
                    if(var7[var9].getType() == 0) {
                        this.fieldSize[var9] = var7[var9].getMetaData().getDisplaySize();
                    } else {
                        this.fieldSize[var9] = 0;
                    }
                }
            } else {
                var8 = "," + var8 + ",";

                for(var9 = 0; var9 < var7.length; ++var9) {
                    if(var7[var9].getType() == 0 && var8.indexOf(var7[var9].getName()) >= 0) {
                        this.fieldSize[var9] = var7[var9].getMetaData().getDisplaySize();
                    } else {
                        this.fieldSize[var9] = 0;
                    }
                }
            }
        }

        if(this.pattern != null) {
            var8 = this.getReplaceFields();
            this.replaceFlag = new boolean[var7.length];
            if(var8 == null) {
                var8 = "*";
            }

            var8 = var8.replaceAll("\\s", "").toUpperCase();
            if(var8.equals("*")) {
                for(var9 = 0; var9 < var7.length; ++var9) {
                    this.replaceFlag[var9] = var7[var9].getType() == 0;
                }
            } else {
                var8 = "," + var8 + ",";

                for(var9 = 0; var9 < var7.length; ++var9) {
                    this.replaceFlag[var9] = var7[var9].getType() == 0 && var8.indexOf(var7[var9].getName()) >= 0;
                }
            }
        }

    }

    private void replaceRegex(Field[] var1) {
        for(int var2 = 0; var2 < var1.length; ++var2) {
            if(this.replaceFlag[var2]) {
                String var3 = var1[var2].getString();
                if(var3 != null) {
                    var1[var2].setValue(this.replace(var3));
                }
            }
        }

    }

    private String replace(String var1) {
        String var2 = var1;

        for(int var3 = 0; var3 < this.pattern.length; ++var3) {
            var2 = this.pattern[var3].matcher(var2).replaceAll(this.replacement[var3]);
        }

        return var2;
    }

    private void truncateSize(Field[] var1) {
        for(int var2 = 0; var2 < var1.length; ++var2) {
            if(this.fieldSize[var2] > 0) {
                String var3 = var1[var2].getString();
                if(var3 != null) {
                    var1[var2].setValue(this.truncateLength(var3, this.fieldSize[var2]));
                }
            }
        }

    }

    protected String truncateLength(String var1, int var2) {
        byte[] var3 = var1.getBytes();
        if(var3.length <= var2) {
            return var1;
        } else {
            byte[] var4 = new byte[var2];
            System.arraycopy(var3, 0, var4, 0, var2);
            int var5 = 0;

            for(int var6 = var4.length - 1; var6 >= 0 && var4[var6] < 0; --var6) {
                ++var5;
            }

            return var5 % 2 == 0?new String(var4):new String(var4, 0, var4.length - 1);
        }
    }

    public void setRegexReplace(String var1) {
        if(var1 != null) {
            String[] var2 = StringX.parseArray(var1);

            for(int var3 = 0; var3 < var2.length; ++var3) {
                if(!var2[var3].matches("\\s*")) {
                    int var4 = var2[var3].indexOf(44);
                    if(var4 >= 0) {
                        String var5 = var2[var3].substring(0, var4);
                        String var6 = var2[var3].substring(var4 + 1);
                        if(var5.length() >= 1) {
                            this.addRegexReplace(var5, var6);
                        }
                    }
                }
            }

        }
    }

    public boolean isTruncateSize() {
        return this.truncateSize;
    }

    public void setTruncateSize(boolean var1) {
        this.truncateSize = var1;
    }

    public void addRegexReplace(String var1, String var2) {
        if(var1 != null) {
            this.regexes.put(var1, var2 == null?"":var2);
        }
    }

    public void removeRegexReplace(String var1) {
        if(var1 != null) {
            this.regexes.remove(var1);
        }
    }

    public String getReplaceFields() {
        return this.replaceFields;
    }

    public void setReplaceFields(String var1) {
        this.replaceFields = var1;
    }

    public String getTruncateFields() {
        return this.truncateFields;
    }

    public void setTruncateFields(String var1) {
        this.truncateFields = var1;
    }
}
