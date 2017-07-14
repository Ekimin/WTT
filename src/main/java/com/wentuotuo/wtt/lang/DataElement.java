//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.lang;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

public class DataElement implements Cloneable, Comparable, Serializable, Element {
    private static final long serialVersionUID = 1L;
    public static final byte STRING = 0;
    public static final byte INT = 1;
    public static final byte LONG = 2;
    public static final byte DOUBLE = 4;
    public static final byte BOOLEAN = 8;
    public static final byte DATE = 16;
    private String id = null;
    private String name;
    private byte type;
    private String label;
    private int length;
    private int scale;
    private Properties extendProperties;
    private int intValue;
    private double doubleValue;
    private Date dateValue;
    private String stringValue;
    private boolean booleanValue;
    private long longValue;
    private boolean isNull = true;
    private boolean converted;
    private byte setType = 0;
    private static int unnamedCount = 0;
    private static long idCount = 0L;

    public static final int unnamedInstanceCount() {
        return unnamedCount;
    }

    public DataElement() {
        this.type = 0;
        this.name = "de_" + String.valueOf(++unnamedCount);
        this.setNull();
    }

    public DataElement(String var1) {
        this.type = 0;
        this.name = var1 != null?var1:"de_" + String.valueOf(++unnamedCount);
        this.setNull();
    }

    public DataElement(String var1, byte var2) {
        this.name = var1 != null?var1:"de_" + String.valueOf(++unnamedCount);
        this.type = var2;
        this.setNull();
    }

    public String getId() {
        if(this.id == null) {
            this.id = String.valueOf((long)(idCount++));
        }

        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getLabel() {
        return this.label == null?this.name:this.label;
    }

    public final void setLabel(String var1) {
        this.label = var1;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int var1) {
        this.length = var1;
    }

    public int getScale() {
        return this.scale;
    }

    public void setScale(int var1) {
        this.scale = var1;
    }

    public final Date getDate() {
        if(!this.converted && this.setType != 16) {
            this.typeConvert();
            this.converted = true;
        }

        return this.dateValue;
    }

    public final boolean isNull() {
        return this.isNull;
    }

    public final void setNull() {
        this.intValue = 0;
        this.doubleValue = 0.0D;
        this.stringValue = "";
        this.dateValue = new Date(0L);
        this.booleanValue = false;
        this.longValue = 0L;
        this.converted = true;
        this.isNull = true;
    }

    public final double getDouble() {
        if(!this.converted && this.setType != 4) {
            this.typeConvert();
            this.converted = true;
        }

        return this.doubleValue;
    }

    public final String getString() {
        if(!this.converted && this.setType != 0) {
            this.typeConvert();
            this.converted = true;
        }

        return this.stringValue;
    }

    public final int getInt() {
        if(!this.converted && this.setType != 1) {
            this.typeConvert();
            this.converted = true;
        }

        return this.intValue;
    }

    public final long getLong() {
        if(!this.converted && this.setType != 2) {
            this.typeConvert();
            this.converted = true;
        }

        return this.longValue;
    }

    public final boolean getBoolean() {
        if(!this.converted && this.setType != 8) {
            this.typeConvert();
            this.converted = true;
        }

        return this.booleanValue;
    }

    public final String toString() {
        return this.getString();
    }

    public Object clone() {
        DataElement var1 = null;

        try {
            var1 = (DataElement)super.clone();
        } catch (CloneNotSupportedException var3) {
            ;
        }

        return var1;
    }

    public final byte getType() {
        return this.type;
    }

    public final void setValue(String var1) {
        this.stringValue = var1;
        this.isNull = var1 == null;
        this.setType = 0;
        this.converted = false;
    }

    public final void setValue(int var1) {
        this.intValue = var1;
        this.isNull = false;
        this.setType = 1;
        this.converted = false;
    }

    public final void setValue(long var1) {
        this.longValue = var1;
        this.isNull = false;
        this.setType = 2;
        this.converted = false;
    }

    public final void setValue(double var1) {
        this.doubleValue = var1;
        this.isNull = false;
        this.setType = 4;
        this.converted = false;
    }

    public final void setValue(boolean var1) {
        this.booleanValue = var1;
        this.isNull = false;
        this.setType = 8;
        this.converted = false;
    }

    public final void setValue(Date var1) {
        this.dateValue = var1;
        this.isNull = var1 == null;
        this.setType = 16;
        this.converted = false;
    }

    public final void setValue(DataElement var1) {
        if(var1 != null && !var1.isNull()) {
            switch(this.type) {
                case 0:
                    this.setValue(var1.getString());
                    break;
                case 1:
                    this.setValue(var1.getInt());
                    break;
                case 2:
                    this.setValue(var1.getLong());
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
                    this.setValue(var1.getString());
                    break;
                case 4:
                    this.setValue(var1.getDouble());
                    break;
                case 8:
                    this.setValue(var1.getBoolean());
                    break;
                case 16:
                    this.setValue(var1.getDate());
            }

        } else {
            this.setNull();
        }
    }

    public final Object getValue() {
        Object var1 = null;
        if(this.isNull()) {
            return var1;
        } else {
            switch(this.type) {
                case 0:
                    var1 = this.isNull()?(String)null:this.getString();
                    break;
                case 1:
                    var1 = new Integer(this.getInt());
                    break;
                case 2:
                    var1 = new Long(this.getLong());
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
                    var1 = this.getString();
                    break;
                case 4:
                    var1 = new Double(this.getDouble());
                    break;
                case 8:
                    var1 = new Boolean(this.getBoolean());
                    break;
                case 16:
                    var1 = this.getDate();
            }

            return var1;
        }
    }

    public final void setValue(Object var1) {
        if(var1 == null) {
            this.setNull();
        } else {
            if(var1 instanceof String) {
                this.setValue((String)var1);
            } else if(var1 instanceof Integer) {
                this.setValue(((Integer)var1).intValue());
            } else if(var1 instanceof Long) {
                this.setValue(((Long)var1).longValue());
            } else if(var1 instanceof Double) {
                this.setValue(((Double)var1).doubleValue());
            } else if(var1 instanceof Boolean) {
                this.setValue(((Boolean)var1).booleanValue());
            } else if(var1 instanceof Date) {
                this.setValue((Date)var1);
            } else if(var1 instanceof DateX) {
                this.setValue(((DateX)var1).getTime());
            } else if(var1 instanceof Element) {
                this.setValue(((Element)var1).getValue());
            } else {
                this.setValue(var1.toString());
            }

        }
    }

    private final void typeConvert() {
        if(this.isNull) {
            this.setNull();
        } else {
            switch(this.setType) {
                case 0:
                    this.string2other();
                    break;
                case 1:
                    this.stringValue = String.valueOf(this.intValue);
                    this.longValue = (long)this.intValue;
                    this.doubleValue = (double)this.intValue;
                    this.booleanValue = this.intValue != 0;
                    this.dateValue = new Date((long)this.intValue);
                    break;
                case 2:
                    this.stringValue = String.valueOf(this.longValue);
                    this.intValue = (int)this.longValue;
                    this.doubleValue = (double)this.longValue;
                    this.booleanValue = this.longValue != 0L;
                    this.dateValue = new Date(this.longValue);
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
                    this.string2other();
                    break;
                case 4:
                    this.stringValue = String.valueOf(this.doubleValue);
                    this.intValue = (int)this.doubleValue;
                    this.longValue = (long)this.doubleValue;
                    this.booleanValue = this.doubleValue != 0.0D;
                    this.dateValue = new Date(this.longValue);
                    break;
                case 8:
                    this.stringValue = String.valueOf(this.booleanValue);
                    this.intValue = this.booleanValue?1:0;
                    this.longValue = (long)this.intValue;
                    this.doubleValue = (double)this.intValue;
                    this.dateValue = new Date(this.longValue);
                    break;
                case 16:
                    this.stringValue = DateX.format(this.dateValue);
                    this.longValue = this.dateValue.getTime();
                    this.intValue = (int)this.longValue;
                    this.doubleValue = (double)this.longValue;
                    this.booleanValue = this.longValue != 0L;
            }

        }
    }

    private final void string2other() {
        Double var1;
        switch(this.type) {
            case 0:
                this.booleanValue = StringX.parseBoolean(this.stringValue);
                this.dateValue = StringX.parseDate(this.stringValue);

                try {
                    var1 = Double.valueOf(this.stringValue.replaceAll(",", ""));
                    this.intValue = var1.intValue();
                    this.longValue = var1.longValue();
                    this.doubleValue = var1.doubleValue();
                    if(this.dateValue == null) {
                        this.dateValue = new Date(this.longValue);
                    }
                } catch (NumberFormatException var5) {
                    if(this.dateValue != null) {
                        this.longValue = this.dateValue.getTime();
                        this.intValue = (int)this.longValue;
                        this.doubleValue = (double)this.longValue;
                    } else {
                        this.longValue = 0L;
                        this.intValue = 0;
                        this.doubleValue = 0.0D;
                    }
                }
                break;
            case 1:
            case 2:
            case 4:
                try {
                    var1 = Double.valueOf(this.stringValue.replaceAll(",", ""));
                    this.intValue = var1.intValue();
                    this.longValue = var1.longValue();
                    this.doubleValue = var1.doubleValue();
                    this.dateValue = new Date(this.longValue);
                } catch (NumberFormatException var2) {
                    this.intValue = 0;
                    this.longValue = 0L;
                    this.doubleValue = 0.0D;
                    this.dateValue = null;
                }

                this.booleanValue = this.intValue != 0;
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
                break;
            case 8:
                this.booleanValue = StringX.parseBoolean(this.stringValue);
                this.dateValue = StringX.parseDate(this.stringValue);

                try {
                    var1 = Double.valueOf(this.stringValue.replaceAll(",", ""));
                    this.intValue = var1.intValue();
                    this.longValue = var1.longValue();
                    this.doubleValue = var1.doubleValue();
                    if(this.dateValue == null) {
                        this.dateValue = new Date(this.longValue);
                    }
                } catch (NumberFormatException var4) {
                    this.intValue = this.booleanValue?1:0;
                    this.longValue = (long)this.intValue;
                    this.doubleValue = (double)this.intValue;
                }
                break;
            case 16:
                this.dateValue = StringX.parseDate(this.stringValue);
                if(this.dateValue == null) {
                    try {
                        var1 = Double.valueOf(this.stringValue.replaceAll(",", ""));
                        this.intValue = var1.intValue();
                        this.longValue = var1.longValue();
                        this.doubleValue = var1.doubleValue();
                        this.dateValue = new Date(this.longValue);
                    } catch (NumberFormatException var3) {
                        this.intValue = 0;
                        this.longValue = 0L;
                        this.doubleValue = 0.0D;
                        this.dateValue = null;
                    }
                } else {
                    this.longValue = this.dateValue.getTime();
                    this.intValue = (int)this.longValue;
                    this.doubleValue = (double)this.longValue;
                }

                this.booleanValue = this.dateValue != null;
        }

    }

    public int compareTo(Object var1) {
        if(!(var1 instanceof DataElement)) {
            return this.isNull()?-1:this.getString().compareTo(var1.toString());
        } else {
            DataElement var2 = (DataElement)var1;
            if(this.isNull() && var2.isNull()) {
                return 0;
            } else if(this.isNull() && !var2.isNull()) {
                return -2147483648;
            } else if(!this.isNull() && var2.isNull()) {
                return 2147483647;
            } else if(this.type != var2.type) {
                return this.getString().compareTo(var2.getString());
            } else {
                boolean var3 = false;
                int var14;
                switch(this.type) {
                    case 1:
                        int var4 = this.getInt();
                        int var5 = var2.getInt();
                        if(var4 > var5) {
                            var14 = 1;
                        } else if(var4 < var5) {
                            var14 = -1;
                        } else {
                            var14 = 0;
                        }
                        break;
                    case 2:
                        long var6 = this.getLong();
                        long var8 = var2.getLong();
                        if(var6 > var8) {
                            var14 = 1;
                        } else if(var6 < var8) {
                            var14 = -1;
                        } else {
                            var14 = 0;
                        }
                        break;
                    case 4:
                        double var10 = this.getDouble();
                        double var12 = var2.getDouble();
                        if(var10 > var12) {
                            var14 = 1;
                        } else if(var10 < var12) {
                            var14 = -1;
                        } else {
                            var14 = 0;
                        }
                        break;
                    case 8:
                        var14 = this.getInt() - var2.getInt();
                        break;
                    case 16:
                        var14 = this.getDate().compareTo(var2.getDate());
                        break;
                    default:
                        var14 = this.getString().compareTo(var2.getString());
                }

                return var14;
            }
        }
    }

    public boolean equals(Object var1) {
        return this.compareTo(var1) == 0;
    }

    public String getProperty(String var1) {
        return this.extendProperties != null && var1 != null?this.extendProperties.getProperty(var1):null;
    }

    public void setProperty(String var1, String var2) {
        if(this.extendProperties == null) {
            this.extendProperties = new Properties();
        }

        this.extendProperties.setProperty(var1, var2);
    }

    public String[] getProperties() {
        String[] var1 = new String[0];
        if(this.extendProperties != null) {
            var1 = (String[])this.extendProperties.keySet().toArray(var1);
        }

        return var1;
    }

    public static DataElement valueOf(String var0, int var1) {
        DataElement var2 = new DataElement(var0, (byte)1);
        var2.setValue(var1);
        return var2;
    }

    public static DataElement valueOf(String var0, long var1) {
        DataElement var3 = new DataElement(var0, (byte)2);
        var3.setValue(var1);
        return var3;
    }

    public static DataElement valueOf(String var0, double var1) {
        DataElement var3 = new DataElement(var0, (byte)4);
        var3.setValue(var1);
        return var3;
    }

    public static DataElement valueOf(String var0, Date var1) {
        DataElement var2 = new DataElement(var0, (byte)16);
        var2.setValue(var1);
        return var2;
    }

    public static DataElement valueOf(String var0, boolean var1) {
        DataElement var2 = new DataElement(var0, (byte)8);
        var2.setValue(var1);
        return var2;
    }

    public static DataElement valueOf(String var0, String var1) {
        DataElement var2 = new DataElement(var0);
        var2.setValue(var1);
        return var2;
    }

    public static DataElement valueOf(int var0) {
        return valueOf((String)null, var0);
    }

    public static DataElement valueOf(long var0) {
        return valueOf((String)null, var0);
    }

    public static DataElement valueOf(double var0) {
        return valueOf((String)null, var0);
    }

    public static DataElement valueOf(Date var0) {
        return valueOf((String)null, (Date)var0);
    }

    public static DataElement valueOf(String var0) {
        return valueOf((String)null, (String)var0);
    }

    public static DataElement valueOf(boolean var0) {
        return valueOf((String)null, var0);
    }
}
