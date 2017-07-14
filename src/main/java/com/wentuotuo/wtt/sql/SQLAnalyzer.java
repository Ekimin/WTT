//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql;

import com.wentuotuo.wtt.lang.StringX;

public class SQLAnalyzer {
    protected String originalSql = null;
    protected String[] sqlWords = null;
    protected String fineSql = null;
    protected byte sqlType = -1;
    private boolean analysed = false;

    public SQLAnalyzer() {
        this.originalSql = null;
        this.sqlWords = null;
        this.fineSql = null;
        this.sqlType = -1;
    }

    public SQLAnalyzer(String var1) {
        this.setOriginalSql(var1);
    }

    public boolean isAnalysed() {
        return this.analysed;
    }

    public void setAnalysed(boolean var1) {
        this.analysed = var1;
    }

    public final void doAnalyse() {
        if(!this.isAnalysed()) {
            this.analyse();
            this.setAnalysed(true);
        }

    }

    public final String getOriginalSql() {
        return this.originalSql;
    }

    public final void setOriginalSql(String var1) {
        if(this.originalSql == null || !this.originalSql.equals(var1)) {
            this.originalSql = var1;
            this.setAnalysed(false);
        }
    }

    public final byte getSqlType() {
        this.doAnalyse();
        return this.sqlType;
    }

    public final String[] getSqlWords() {
        return this.sqlWords;
    }

    public final String getFineSql() {
        this.doAnalyse();
        return this.fineSql;
    }

    protected void analyse() {
        this.sqlType = -1;
        String var1 = StringX.trimAll(this.originalSql).replaceAll("\\s*,\\s*", ",");
        this.fineSql = var1;
        this.sqlWords = var1.split(" ");
        if(this.sqlWords.length < 1) {
            this.sqlType = -1;
        } else if(this.sqlWords.length == 1) {
            if(this.sqlWords[0].equalsIgnoreCase("disconnect")) {
                this.sqlType = 101;
            } else if(!this.sqlWords[0].equalsIgnoreCase("?") && !this.sqlWords[0].equalsIgnoreCase("help")) {
                if(this.sqlWords[0].equalsIgnoreCase("commit")) {
                    this.sqlType = 7;
                } else if(this.sqlWords[0].equalsIgnoreCase("rollback")) {
                    this.sqlType = 8;
                } else {
                    this.sqlType = -1;
                }
            } else {
                this.sqlType = 120;
            }

        } else if(this.sqlWords[0].equalsIgnoreCase("select")) {
            this.sqlType = 0;
        } else if(this.sqlWords[0].equalsIgnoreCase("update")) {
            this.sqlType = 1;
        } else if(this.sqlWords[0].equalsIgnoreCase("insert")) {
            if(!this.sqlWords[1].equalsIgnoreCase("into")) {
                this.sqlType = -1;
            } else {
                this.sqlType = 2;
            }

        } else if(this.sqlWords[0].equalsIgnoreCase("delete")) {
            if(!this.sqlWords[1].equalsIgnoreCase("from")) {
                this.sqlType = -1;
            } else {
                this.sqlType = 3;
            }

        } else {
            if(this.sqlWords[0].equalsIgnoreCase("create")) {
                this.sqlType = 4;
            }

            if(this.sqlWords[0].equalsIgnoreCase("drop")) {
                this.sqlType = 5;
            }

            if(this.sqlWords[0].equalsIgnoreCase("alter")) {
                this.sqlType = 6;
            }

            if(this.sqlType != -1) {
                String var2 = this.sqlWords[1].toLowerCase();
                if(!var2.equals("table") && !var2.equals("index") && !var2.equals("procedure") && !var2.equals("function") && !var2.equals("view") && !var2.equals("database")) {
                    this.sqlType = -1;
                }

            } else if(this.sqlWords[0].equalsIgnoreCase("connect")) {
                if(this.sqlWords[1].equalsIgnoreCase("to") && this.sqlWords.length == 3) {
                    this.sqlType = 100;
                } else {
                    this.sqlType = -1;
                }

            } else {
                if(this.sqlWords[0].equalsIgnoreCase("set")) {
                    if(this.sqlWords[1].equalsIgnoreCase("autocommit")) {
                        if(this.sqlWords.length != 3) {
                            this.sqlType = -1;
                        } else if(!this.sqlWords[2].equalsIgnoreCase("true") && !this.sqlWords[2].equalsIgnoreCase("false")) {
                            this.sqlType = -1;
                        } else {
                            this.sqlType = 110;
                        }

                        return;
                    }

                    if(this.sqlWords[1].equalsIgnoreCase("transaction")) {
                        if(this.sqlWords.length != 4) {
                            this.sqlType = -1;
                        } else if(!this.sqlWords[2].equalsIgnoreCase("isolation") || !this.sqlWords[3].equalsIgnoreCase("none") && !this.sqlWords[3].equalsIgnoreCase("read_uncommitted") && !this.sqlWords[3].equalsIgnoreCase("read_committed") && !this.sqlWords[3].equalsIgnoreCase("repeatable_read") && !this.sqlWords[3].equalsIgnoreCase("serializable")) {
                            this.sqlType = -1;
                        } else {
                            this.sqlType = 111;
                        }

                        return;
                    }
                } else {
                    this.sqlType = -1;
                }

            }
        }
    }
}
