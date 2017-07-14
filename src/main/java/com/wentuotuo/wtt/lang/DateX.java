//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.lang;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateX {
    public static final int YEAR = 0;
    public static final int YE = 0;
    public static final int MONTH = 1;
    public static final int MO = 1;
    public static final int DATE = 2;
    public static final int DA = 2;
    public static final int HOUR = 3;
    public static final int HO = 3;
    public static final int MINUTE = 4;
    public static final int MI = 4;
    public static final int SECOND = 5;
    public static final int SE = 5;
    public static final int MILLISECOND = 6;
    public static final int MS = 6;
    private Date date;
    private int[] fields;
    private Calendar calendar;

    public DateX(Object var1) {
        this.date = null;
        this.fields = null;
        this.calendar = null;
        this.date = castDate(var1);
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(this.date);
        this.setFields();
    }

    public DateX() {
        this(new Date());
    }

    public DateX(int var1, int var2, int var3) {
        this.date = null;
        this.fields = null;
        this.calendar = null;
        this.calendar = Calendar.getInstance();
        this.calendar.clear();
        this.calendar.set(var1, var2 - 1, var3);
        this.date = this.calendar.getTime();
        this.setFields();
    }

    private void setFields() {
        this.fields = new int[7];
        this.fields[0] = this.calendar.get(1);
        this.fields[1] = this.calendar.get(2) + 1;
        this.fields[2] = this.calendar.get(5);
        this.fields[3] = this.calendar.get(10);
        this.fields[4] = this.calendar.get(12);
        this.fields[5] = this.calendar.get(13);
        this.fields[6] = this.calendar.get(14);
    }

    public int getField(int var1) {
        return this.fields[var1];
    }

    public int getYear() {
        return this.fields[0];
    }

    public int getMonth() {
        return this.fields[1];
    }

    public int getDate() {
        return this.fields[2];
    }

    public int getHour() {
        return this.fields[3];
    }

    public int getMinute() {
        return this.fields[4];
    }

    public int getSecond() {
        return this.fields[5];
    }

    public int getMillisecond() {
        return this.fields[6];
    }

    public int[] getAllFields() {
        return (int[])this.fields.clone();
    }

    public boolean isEndMonthOfYear() {
        return this.fields[1] == 12;
    }

    public boolean isEndDayOfMonth() {
        boolean var1 = false;
        switch(this.fields[1]) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                var1 = this.fields[2] == 31;
                break;
            case 2:
                var1 = this.isLeapYear()?this.fields[2] == 29:this.fields[2] == 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                var1 = this.fields[2] == 30;
        }

        return var1;
    }

    public boolean isLeapYear() {
        return this.fields[0] % 100 == 0?this.fields[0] % 400 == 0:this.fields[0] % 4 == 0;
    }

    public Date getAdjustRelativeDate(int var1, int var2, boolean var3) {
        if(var2 == 0) {
            return this.date;
        } else if(var1 >= 0 && var1 <= 6) {
            Calendar var4 = Calendar.getInstance();
            var4.setTime(this.date);
            var4.add(this.getCalendarConst(var1), var2);
            if(var3 && this.isEndDayOfMonth()) {
                int var5 = var4.get(2);
                var4.set(var4.get(1), var5 + 1, 1);

                while(var4.get(2) != var5) {
                    var4.add(5, -1);
                }
            }

            return var4.getTime();
        } else {
            return null;
        }
    }

    public Date getAbsoluteRelativeDate(int var1, int var2) {
        if(var2 == 0) {
            return this.date;
        } else if(var1 >= 0 && var1 <= 6) {
            Calendar var3 = Calendar.getInstance();
            var3.setTime(this.date);
            if(var1 == 1) {
                var3.set(this.getCalendarConst(var1), this.fields[1] + var2 - 1);
            } else {
                var3.set(this.getCalendarConst(var1), this.fields[var1] + var2);
            }

            return var3.getTime();
        } else {
            return null;
        }
    }

    private int getCalendarConst(int var1) {
        return var1 == 0?1:(var1 == 1?2:(var1 == 2?5:(var1 == 3?11:(var1 == 4?12:(var1 == 5?13:(var1 == 6?14:var1))))));
    }

    public Date getTime() {
        return this.date;
    }

    public String getDateString() {
        return this.getDateString("yyyy/MM/dd");
    }

    public String getDateString(String var1) {
        return (new SimpleDateFormat(var1)).format(this.date);
    }

    public static String format(Date var0) {
        return format(var0, "yyyy/MM/dd");
    }

    public static String format(Date var0, String var1) {
        return (new SimpleDateFormat(var1)).format(var0);
    }

    public static Date today() {
        return new Date();
    }

    public static Date castDate(Object var0) {
        Date var1 = null;
        if(var0 instanceof Date) {
            var1 = (Date)var0;
        } else if(var0 instanceof DateX) {
            var1 = ((DateX)var0).getTime();
        } else if(var0 instanceof DataElement) {
            var1 = ((DataElement)var0).getDate();
        } else if(var0 instanceof Element) {
            var1 = castDate(((Element)var0).getValue());
        } else if(var0 instanceof Number) {
            var1 = new Date(((Number)var0).longValue());
        } else {
            var1 = StringX.parseDate(var0.toString());
        }

        return var1;
    }
}
