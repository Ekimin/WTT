//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.dpx.recordset;

import com.wentuotuo.wtt.WTT;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportFileHandler implements Handler {
    public static final String LINESEPARATOR_DEFAULT = "_DEFAULT";
    public static final String LINESEPARATOR_CRLF = "\r\n";
    public static final String LINESEPARATOR_CR = "\r";
    public static final String LINESEPARATOR_LF = "\n";
    public static final String LINESEPARATOR_DOS = "\r\n";
    public static final String LINESEPARATOR_MAC = "\r";
    public static final String LINESEPARATOR_UNIX = "\n";
    private String charSet = null;
    private String fileName = null;
    private String splitToken = ",";
    private String lineSeparator = System.getProperty("line.Separator", "\n");
    private boolean includeHeader = false;
    private boolean appendMode = false;
    private String exportFields = null;
    private boolean useFieldFormat = false;
    private boolean strictFieldSize = false;
    private boolean includeSplitToken = true;
    private SimpleDateFormat df = null;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private DecimalFormat nf = null;
    private BufferedWriter fileWriter = null;
    private String[] exportCols = null;
    private int[] exportFieldsIndex = null;

    public ExportFileHandler(String var1, String var2, String var3, boolean var4, boolean var5) {
        this.charSet = var2;
        this.fileName = var1;
        this.splitToken = var3;
        this.includeHeader = var4;
        this.appendMode = var5;
    }

    public ExportFileHandler(String var1, String var2) {
        this.charSet = var2;
        this.fileName = var1;
    }

    public ExportFileHandler(String var1) {
        this.fileName = var1;
    }

    public ExportFileHandler() {
    }

    public void start(RecordSet var1) throws RecordSetException {
        if(var1.getMetaData() == null) {
            this.setUseFieldFormat(false);
            this.setStrictFieldSize(false);
        }

        Record var2 = var1.getRecordTemplet();
        Field[] var3;
        int var4;
        if(this.exportFields != null && !this.exportFields.equals("*")) {
            this.exportCols = this.exportFields.split(",");
            this.exportFieldsIndex = new int[this.exportCols.length];

            for(int var9 = 0; var9 < this.exportFieldsIndex.length; ++var9) {
                this.exportFieldsIndex[var9] = var2.indexOf(this.exportCols[var9]);
            }
        } else {
            var3 = var2.internalData();
            this.exportCols = new String[var3.length - 1];
            this.exportFieldsIndex = new int[this.exportCols.length];

            for(var4 = 0; var4 < this.exportCols.length; ++var4) {
                this.exportCols[var4] = var3[var4 + 1].getName();
                this.exportFieldsIndex[var4] = var4 + 1;
            }
        }

        try {
            var3 = null;
            OutputStreamWriter var10;
            if(this.getCharSet() == null) {
                var10 = new OutputStreamWriter(new FileOutputStream(this.getFileName(), this.isAppendMode()));
            } else {
                var10 = new OutputStreamWriter(new FileOutputStream(this.getFileName(), this.isAppendMode()), this.getCharSet());
            }

            this.fileWriter = new BufferedWriter(var10);
        } catch (Exception var8) {
            throw new RecordSetException("Open file error！", var8);
        }

        if(this.isIncludeHeader()) {
            StringBuffer var11 = new StringBuffer();

            for(var4 = 0; var4 < this.exportCols.length; ++var4) {
                String var5 = this.exportCols[var4] == null?"":this.exportCols[var4];
                if(this.isStrictFieldSize()) {
                    var5 = this.toFixSize(var5, 1, var1.getRecordTemplet().getField(this.exportCols[var4]).getMetaData().getDisplaySize());
                }

                var11.append(var5);
                if(this.isIncludeSplitToken() && this.splitToken != null && var4 != this.exportCols.length - 1) {
                    var11.append(this.splitToken);
                }
            }

            try {
                this.fileWriter.write(var11.toString());
                this.writeNewLine(this.fileWriter);
            } catch (IOException var7) {
                try {
                    this.fileWriter.close();
                } catch (IOException var6) {
                    WTT.getLog().debug("Write file error!", var6);
                }

                throw new RecordSetException("Write file error！", var7);
            }
        }

        this.beforeExport(var1);
    }

    public void handleRecord(RecordSet var1, Record var2) throws RecordSetException {
        StringBuffer var3 = new StringBuffer();

        for(int var4 = 0; var4 < this.exportCols.length; ++var4) {
            Field var5 = var2.getField(this.exportFieldsIndex[var4]);
            if(var5 == null) {
                WTT.getLog().warn("Field not exists--" + this.exportCols[var4]);
                var3.append("");
            } else {
                var3.append(this.getFieldAsText(var5));
            }

            if(this.isIncludeSplitToken() && this.splitToken != null && var4 != this.exportCols.length - 1) {
                var3.append(this.splitToken);
            }
        }

        try {
            this.fileWriter.write(var3.toString());
            this.writeNewLine(this.fileWriter);
        } catch (IOException var7) {
            try {
                this.fileWriter.close();
            } catch (IOException var6) {
                WTT.getLog().warn("Close file error!", var6);
            }

            throw new RecordSetException("Write file error！", var7);
        }
    }

    protected void writeNewLine(BufferedWriter var1) throws IOException {
        var1.write(this.getLineSeparator());
    }

    protected String getFieldAsText(Field var1) {
        String var2 = null;
        String var3 = null;
        if(var1 != null && !var1.isNull()) {
            Date var4;
            if(!this.useFieldFormat) {
                switch(var1.getType()) {
                    case 0:
                        var2 = var1.getString();
                        break;
                    case 1:
                        var2 = String.valueOf(var1.getInt());
                        break;
                    case 2:
                        var2 = String.valueOf(var1.getLong());
                        break;
                    case 3:
                    case 5:
                    case 6:
                    case 7:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    default:
                        var2 = var1.getString();
                        break;
                    case 4:
                        var2 = String.valueOf(var1.getDouble());
                        break;
                    case 8:
                        var2 = String.valueOf(var1.getBoolean());
                        break;
                    case 16:
                        var4 = var1.getDate();
                        if(var4 == null) {
                            var2 = "";
                        } else {
                            var2 = this.sdf.format(var4);
                        }
                }
            } else {
                switch(var1.getType()) {
                    case 0:
                        var2 = var1.getString();
                        break;
                    case 1:
                        var3 = var1.getMetaData().getFormat();
                        if(var3 != null && !var3.equals("")) {
                            this.nf.applyPattern(var3);
                            var2 = this.nf.format((long)var1.getInt());
                        } else {
                            var2 = String.valueOf(var1.getInt());
                        }
                        break;
                    case 2:
                        var3 = var1.getMetaData().getFormat();
                        if(var3 != null && !var3.equals("")) {
                            this.nf.applyPattern(var3);
                            var2 = this.nf.format(var1.getLong());
                        } else {
                            var2 = String.valueOf(var1.getLong());
                        }
                        break;
                    case 3:
                    case 5:
                    case 6:
                    case 7:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    default:
                        var2 = var1.getString();
                        break;
                    case 4:
                        var3 = var1.getMetaData().getFormat();
                        if(var3 != null && !var3.equals("")) {
                            this.nf.applyPattern(var3);
                            var2 = this.nf.format(var1.getDouble());
                        } else {
                            var2 = String.valueOf(var1.getDouble());
                        }
                        break;
                    case 8:
                        var2 = String.valueOf(var1.getBoolean());
                        break;
                    case 16:
                        var4 = var1.getDate();
                        if(var4 == null) {
                            var2 = "";
                        } else {
                            var3 = var1.getMetaData().getFormat();
                            if(var3 != null && !var3.equals("")) {
                                this.df.applyPattern(var3);
                                var2 = this.df.format(var4);
                            } else {
                                var2 = this.sdf.format(var1.getDate());
                            }
                        }
                }
            }
        } else {
            var2 = "";
        }

        if(this.isStrictFieldSize() && var1 != null) {
            boolean var6 = false;
            boolean var5 = false;
            int var7 = var1.getMetaData().getDisplaySize();
            byte var8 = var1.getType();
            if(var2.equals("")) {
                var2 = this.toFixSize(var2, 1, var7);
            } else if(var8 != 1 && var8 != 2 && var8 != 4) {
                var2 = this.toFixSize(var2, 1, var7);
            } else {
                var2 = this.toFixSize(var2, 0, var7);
            }
        }

        return var2;
    }

    private String toFixSize(String var1, int var2, int var3) {
        StringBuffer var4 = new StringBuffer(var1);
        if(var2 == 0) {
            char var5 = var1.charAt(0);
            if(var5 != 43 && var5 != 45) {
                while(var4.length() < var3) {
                    var4.insert(0, "0");
                }
            } else {
                while(var4.length() < var3) {
                    var4.insert(1, "0");
                }
            }
        } else {
            boolean var6 = false;

            for(int var7 = var4.toString().getBytes().length; var7 < var3; ++var7) {
                var4.append(' ');
            }
        }

        String var8 = var4.toString();
        return var8;
    }

    public void end(RecordSet var1) throws RecordSetException {
        this.afterExport(var1);

        try {
            this.fileWriter.close();
        } catch (IOException var3) {
            WTT.getLog().warn("Close file error!", var3);
        }

    }

    protected void beforeExport(RecordSet var1) throws RecordSetException {
    }

    protected void afterExport(RecordSet var1) throws RecordSetException {
    }

    public String getCharSet() {
        return this.charSet;
    }

    public void setCharSet(String var1) {
        this.charSet = var1;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String var1) {
        this.fileName = var1;
    }

    public boolean isIncludeHeader() {
        return this.includeHeader;
    }

    public void setIncludeHeader(boolean var1) {
        this.includeHeader = var1;
    }

    public String getSplitToken() {
        return this.splitToken;
    }

    public void setSplitToken(String var1) {
        this.splitToken = var1;
    }

    public boolean isAppendMode() {
        return this.appendMode;
    }

    public void setAppendMode(boolean var1) {
        this.appendMode = var1;
    }

    public String getExportFields() {
        return this.exportFields;
    }

    public void setExportFields(String var1) {
        String var2 = var1;
        if(var1 != null) {
            var2 = var1.replaceAll("[\\s]+", "");
        }

        this.exportFields = var2;
    }

    public boolean isUseFieldFormat() {
        return this.useFieldFormat;
    }

    public void setUseFieldFormat(boolean var1) {
        this.useFieldFormat = var1;
        if(var1) {
            if(this.nf == null) {
                this.nf = new DecimalFormat();
            }

            if(this.df == null) {
                this.df = new SimpleDateFormat();
            }
        } else {
            this.nf = null;
            this.df = null;
        }

    }

    public boolean isStrictFieldSize() {
        return this.strictFieldSize;
    }

    public void setStrictFieldSize(boolean var1) {
        this.strictFieldSize = var1;
    }

    protected BufferedWriter getWriter() {
        return this.fileWriter;
    }

    public boolean isIncludeSplitToken() {
        return this.includeSplitToken;
    }

    public void setIncludeSplitToken(boolean var1) {
        this.includeSplitToken = var1;
    }

    public String getLineSeparator() {
        return this.lineSeparator;
    }

    public void setLineSeparator(String var1) {
        if(var1 != null && !var1.equalsIgnoreCase("_DEFAULT")) {
            if(var1.equalsIgnoreCase("_DOS")) {
                this.lineSeparator = "\r\n";
            } else if(var1.equalsIgnoreCase("_UNIX")) {
                this.lineSeparator = "\n";
            } else if(var1.equalsIgnoreCase("_MAC")) {
                this.lineSeparator = "\r";
            } else if(var1.equalsIgnoreCase("_CRLF")) {
                this.lineSeparator = "\r\n";
            } else if(var1.equalsIgnoreCase("_LF")) {
                this.lineSeparator = "\n";
            } else if(var1.equalsIgnoreCase("_CR")) {
                this.lineSeparator = "\r";
            } else {
                this.lineSeparator = var1;
            }
        } else {
            this.lineSeparator = System.getProperty("line.Separator", "\n");
        }

    }
}
