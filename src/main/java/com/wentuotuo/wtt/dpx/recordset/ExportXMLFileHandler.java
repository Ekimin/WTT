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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExportXMLFileHandler implements Handler {
    private String encoding = "UTF-8";
    private String version = "1.0";
    private String headerAttributes = null;
    private String fileName = null;
    private boolean appendMode = false;
    private String exportFields = null;
    private boolean useFieldFormat = false;
    private boolean strictFieldSize = false;
    private boolean escapeSpecialCharacters = true;
    private SimpleDateFormat df = null;
    private DecimalFormat nf = null;
    private BufferedWriter fileWriter = null;
    private String[] exportCols = null;
    private Pattern specChars = null;

    public ExportXMLFileHandler(String var1, String var2, boolean var3) {
        this.encoding = var2;
        this.fileName = var1;
        this.appendMode = var3;
    }

    public ExportXMLFileHandler(String var1, String var2) {
        this.encoding = var2;
        this.fileName = var1;
    }

    public ExportXMLFileHandler(String var1) {
        this.fileName = var1;
    }

    public ExportXMLFileHandler() {
    }

    public void start(RecordSet var1) throws RecordSetException {
        if(var1.getMetaData() == null) {
            this.setUseFieldFormat(false);
            this.setStrictFieldSize(false);
        }

        if(this.isUseFieldFormat()) {
            this.nf = new DecimalFormat();
            this.df = new SimpleDateFormat();
        }

        if(this.isEscapeSpecialCharacters()) {
            ;
        }

        if(this.getFileName() == null) {
            this.setFileName(var1.getName() + ".xml");
        }

        Record var2;
        if(this.exportFields != null && !this.exportFields.equals("*")) {
            this.exportCols = this.exportFields.split(",");
        } else {
            var2 = var1.getRecordTemplet();
            Field[] var3 = var2.getFields();
            this.exportCols = new String[var3.length];

            for(int var4 = 0; var4 < var3.length; ++var4) {
                this.exportCols[var4] = var3[var4].getName();
            }
        }

        try {
            var2 = null;
            OutputStreamWriter var8 = new OutputStreamWriter(new FileOutputStream(this.getFileName(), this.isAppendMode()), this.getEncoding());
            this.fileWriter = new BufferedWriter(var8);
        } catch (Exception var7) {
            throw new RecordSetException("Open file error！", var7);
        }

        if(!this.isAppendMode()) {
            try {
                this.fileWriter.write(this.getXmlHeader());
                this.fileWriter.newLine();
            } catch (IOException var6) {
                throw new RecordSetException("Write xml header error！", var6);
            }
        }

        this.beforeExport(var1);

        try {
            StringBuffer var9 = new StringBuffer();
            var9.append("<").append(this.getRecordSetNodeName(var1));
            var9.append(this.getRecordSetNodeAttributes(var1) == null?"":" " + this.getRecordSetNodeAttributes(var1));
            var9.append(">");
            this.fileWriter.write(var9.toString());
            this.fileWriter.newLine();
        } catch (IOException var5) {
            throw new RecordSetException("Write RecordSet node error！", var5);
        }
    }

    private String getXmlHeader() {
        StringBuffer var1 = new StringBuffer("<?xml ");
        var1.append("version=\"").append(this.getVersion()).append("\" ");
        var1.append("encoding=\"").append(this.getEncoding()).append("\"");
        var1.append(this.getHeaderAttributes() == null?"":" " + this.getHeaderAttributes());
        var1.append("?>");
        return var1.toString();
    }

    public void handleRecord(RecordSet var1, Record var2) throws RecordSetException {
        StringBuffer var3 = new StringBuffer();
        var3.append("<").append(this.getRecordNodeName(var1, var2));
        var3.append(this.getRecordNodeAttributes(var1, var2) == null?"":" " + this.getRecordNodeAttributes(var1, var2));
        var3.append(">");

        for(int var4 = 0; var4 < this.exportCols.length; ++var4) {
            Field var5 = var2.getField(this.exportCols[var4]);
            if(var5 == null) {
                WTT.getLog().warn("Field not exists--" + this.exportCols[var4]);
            } else {
                var3.append("<").append(this.getFieldNodeName(var1, var2, var5));
                var3.append(this.getFieldNodeAttributes(var1, var2, var5) == null?"":" " + this.getFieldNodeAttributes(var1, var2, var5));
                var3.append(">");
                var3.append(this.getFieldAsText(var5));
                var3.append("</").append(this.getFieldNodeName(var1, var2, var5)).append(">");
            }
        }

        var3.append("</").append(this.getRecordNodeName(var1, var2)).append(">");

        try {
            this.fileWriter.write(var3.toString());
            this.fileWriter.newLine();
        } catch (IOException var7) {
            try {
                this.fileWriter.close();
            } catch (IOException var6) {
                WTT.getLog().warn("Close file error!", var6);
            }

            throw new RecordSetException("Write file error！", var7);
        }
    }

    protected String getFieldAsText(Field var1) {
        String var2 = null;
        String var3 = null;
        if(var1 != null && !var1.isNull()) {
            if(!this.isUseFieldFormat()) {
                var2 = var1.getString();
            } else {
                switch(var1.getType()) {
                    case 0:
                    case 8:
                        var2 = var1.getString();
                        break;
                    case 1:
                        var3 = var1.getMetaData().getFormat();
                        if(var3 != null && !var3.equals("")) {
                            this.nf.applyPattern(var3);
                            var2 = this.nf.format((long)var1.getInt());
                        } else {
                            var2 = var1.getString();
                        }
                        break;
                    case 2:
                        var3 = var1.getMetaData().getFormat();
                        if(var3 != null && !var3.equals("")) {
                            this.nf.applyPattern(var3);
                            var2 = this.nf.format(var1.getLong());
                        } else {
                            var2 = var1.getString();
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
                            var2 = var1.getString();
                        }
                        break;
                    case 16:
                        var3 = var1.getMetaData().getFormat();
                        if(var3 != null && !var3.equals("")) {
                            this.df.applyPattern(var3);
                            Date var4 = var1.getDate();
                            var2 = this.df.format(var4);
                        } else {
                            var2 = var1.getString();
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

        if(this.isEscapeSpecialCharacters()) {
            var2 = this.escapeSpecialCharacters(var2);
        }

        return var2;
    }

    private String escapeSpecialCharacters(String var1) {
        if(var1 == null) {
            return var1;
        } else {
            if(this.specChars == null) {
                this.specChars = Pattern.compile("[(<)(>)(&)(')(\")]");
            }

            StringBuffer var2 = new StringBuffer();
            Matcher var3 = this.specChars.matcher(var1);

            for(String var4 = null; var3.find(); var3.appendReplacement(var2, var4)) {
                switch(var3.group().charAt(0)) {
                    case '"':
                        var4 = "&quot;";
                        break;
                    case '&':
                        var4 = "&amp;";
                        break;
                    case '\'':
                        var4 = "&apos;";
                        break;
                    case '<':
                        var4 = "&lt;";
                        break;
                    case '>':
                        var4 = "&gt;";
                        break;
                    default:
                        var4 = var3.group();
                }
            }

            var3.appendTail(var2);
            return var2.toString();
        }
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

                var4.insert(0, var5);
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
        try {
            StringBuffer var2 = new StringBuffer("</");
            var2.append(this.getRecordSetNodeName(var1)).append(">");
            this.fileWriter.write(var2.toString());
            this.fileWriter.newLine();
        } catch (IOException var4) {
            WTT.getLog().warn("Write reordset end error!", var4);
        }

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

    public String getEncoding() {
        return this.encoding == null?"UTF-8":this.encoding;
    }

    public void setEncoding(String var1) {
        this.encoding = var1;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String var1) {
        this.fileName = var1;
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
    }

    public boolean isStrictFieldSize() {
        return this.strictFieldSize;
    }

    public void setStrictFieldSize(boolean var1) {
        this.strictFieldSize = var1;
    }

    protected String getRecordSetNodeName(RecordSet var1) {
        return var1.getName();
    }

    protected String getRecordSetNodeAttributes(RecordSet var1) {
        return null;
    }

    protected String getRecordNodeName(RecordSet var1, Record var2) {
        return "ROW";
    }

    protected String getRecordNodeAttributes(RecordSet var1, Record var2) {
        return null;
    }

    protected String getFieldNodeName(RecordSet var1, Record var2, Field var3) {
        return var3.getName();
    }

    protected String getFieldNodeAttributes(RecordSet var1, Record var2, Field var3) {
        return null;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String var1) {
        this.version = var1;
    }

    public String getHeaderAttributes() {
        return this.headerAttributes;
    }

    public void setHeaderAttributes(String var1) {
        this.headerAttributes = var1;
    }

    public void setEscapeSpecialCharacters(boolean var1) {
        this.escapeSpecialCharacters = var1;
    }

    public boolean isEscapeSpecialCharacters() {
        return this.escapeSpecialCharacters;
    }

    protected BufferedWriter getWriter() {
        return this.fileWriter;
    }
}
