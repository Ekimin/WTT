//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.sql.proc;

import java.io.BufferedReader;
import java.io.IOException;

public class BatchProcess {
    public static final int ON_ERROR_CONTINUE = 11;
    public static final int ON_ERROR_COMMIT_CONTINUE = 21;
    public static final int ON_ERROR_ROLLBACK_CONTINUE = 31;
    public static final int ON_ERROR_BREAK = 12;
    public static final int ON_ERROR_COMMIT_BREAK = 22;
    public static final int ON_ERROR_ROLLBACK_BREAK = 32;
    private BufferedReader scriptReader = null;
    private int onErrorAction = 11;
    private ResultDisplay resultDisplay = null;
    private String errorSql = null;
    private int errorAction = 1;

    public BatchProcess() {
    }

    public int execute() throws IOException {
        if(this.scriptReader == null) {
            throw new IOException("sriptReader not set!");
        } else {
            if(this.resultDisplay == null) {
                this.resultDisplay = new PrintWriterResultDisplay();
            }

            SQLProcessor var1 = new SQLProcessor(this.resultDisplay);
            String var2 = null;
            int var3 = 0;

            while((var2 = this.readStatement()) != null) {
                if(var2.equalsIgnoreCase("exit")) {
                    var1.exit();
                    var1 = null;
                    break;
                }

                if(var1.execute(var2) < 0) {
                    ++var3;
                    if(this.errorSql != null) {
                        var1.execute(this.errorSql);
                    }

                    if(this.errorAction == 2) {
                        break;
                    }
                }
            }

            if(var1 != null) {
                var1.exit();
                var1 = null;
            }

            return var3;
        }
    }

    private String readStatement() throws IOException {
        StringBuffer var1 = null;
        String var2 = "";

        while(var2 != null && !var2.endsWith(";")) {
            var2 = this.scriptReader.readLine();
            if(var2 == null) {
                break;
            }

            var2 = var2.replaceAll("(?:^\\s*)|(?:\\s*$)", "");
            if(var2.length() != 0 && !var2.startsWith("--")) {
                if(var1 == null) {
                    var1 = new StringBuffer(var2);
                } else {
                    var1.append(" ").append(var2);
                }
            }
        }

        if(var1 != null && var1.charAt(var1.length() - 1) == 59) {
            var1.deleteCharAt(var1.length() - 1);
        }

        return var1 != null && var1.length() != 0?var1.toString().trim():null;
    }

    public int getOnErrorAction() {
        return this.onErrorAction;
    }

    public void setOnErrorAction(int var1) {
        this.onErrorAction = var1;
        int var2 = var1 / 10;
        if(var2 == 2) {
            this.errorSql = "commit";
        } else if(var2 == 3) {
            this.errorSql = "rollback";
        } else {
            this.errorSql = null;
        }

        this.errorAction = var1 % 10;
    }

    public BufferedReader getScriptReader() {
        return this.scriptReader;
    }

    public void setScriptReader(BufferedReader var1) {
        this.scriptReader = var1;
    }

    public ResultDisplay getResultDisplay() {
        return this.resultDisplay;
    }

    public void setResultDisplay(ResultDisplay var1) {
        this.resultDisplay = var1;
    }
}
