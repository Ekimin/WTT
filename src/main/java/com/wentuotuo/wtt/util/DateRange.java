//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.util;

import com.wentuotuo.wtt.lang.DateX;
import com.wentuotuo.wtt.lang.StringX;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateRange {
    public static final int RANGE_TODAY = 0;
    public static final int RANGE_WEEK = 3;
    public static final int RANGE_WEEK_TODAY = 4;
    public static final int RANGE_LAST_WEEK = 5;
    public static final int RANGE_MONTH = 6;
    public static final int RANGE_MONTH_TODAY = 7;
    public static final int RANGE_LAST_MONTH = 8;
    public static final int RANGE_QUARTER = 9;
    public static final int RANGE_QUARTER_TODAY = 10;
    public static final int RANGE_LAST_QUARTER = 11;
    public static final int RANGE_YEAR = 12;
    public static final int RANGE_YEAR_TODAY = 13;
    public static final int RANGE_LAST_YEAR = 14;
    private Date startDate;
    private Date endDate;
    private static SimpleDateFormat internal_formmater = null;

    public DateRange() {
    }

    public DateRange(Object var1, Object var2) {
        this.startDate = DateX.castDate(var1);
        this.endDate = DateX.castDate(var2);
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public String getEndDateString(String var1) {
        return formatDate(this.getEndDate(), var1);
    }

    public String getEndDateString() {
        return formatDate(this.getEndDate(), "yyyy/MM/dd");
    }

    public void setEndDate(Date var1) {
        this.endDate = var1;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public String getStartDateString(String var1) {
        return formatDate(this.getStartDate(), var1);
    }

    public String getStartDateString() {
        return formatDate(this.getStartDate(), "yyyy/MM/dd");
    }

    public void setStartDate(Date var1) {
        this.startDate = var1;
    }

    public boolean contains(Object var1) {
        if(this.startDate != null && this.endDate != null && var1 != null) {
            if(var1 instanceof DateRange) {
                return this.contains((DateRange)var1);
            } else {
                Date var2 = DateX.castDate(var1);
                return this.startDate.compareTo(var2) <= 0 && this.endDate.compareTo(var2) >= 0;
            }
        } else {
            return false;
        }
    }

    private boolean contains(DateRange var1) {
        return this.contains((Object)var1.startDate) && this.contains((Object)var1.endDate);
    }

    public int intervalYears(boolean var1) {
        if(this.startDate != null && this.endDate != null && !this.startDate.equals(this.endDate)) {
            Calendar var2 = Calendar.getInstance();
            Calendar var3 = Calendar.getInstance();
            byte var4 = 1;
            if(this.startDate.after(this.endDate)) {
                var2.setTime(this.endDate);
                var3.setTime(this.startDate);
                var4 = -1;
            } else {
                var2.setTime(this.startDate);
                var3.setTime(this.endDate);
            }

            var2.set(11, 0);
            var2.set(12, 0);
            var2.set(13, 0);
            var2.set(14, 0);
            var3.set(11, 0);
            var3.set(12, 0);
            var3.set(13, 0);
            var3.set(14, 0);
            if(var2.equals(var3)) {
                return 0;
            } else {
                int var5 = 0;
                var2.add(1, 1);

                while(var2.before(var3)) {
                    ++var5;
                    var2.add(1, 1);
                }

                return (var5 + (!var1 && !var2.equals(var3)?0:1)) * var4;
            }
        } else {
            return 0;
        }
    }

    public int intervalMonths(boolean var1) {
        if(this.startDate != null && this.endDate != null && !this.startDate.equals(this.endDate)) {
            Calendar var2 = Calendar.getInstance();
            Calendar var3 = Calendar.getInstance();
            byte var4 = 1;
            if(this.startDate.after(this.endDate)) {
                var2.setTime(this.endDate);
                var3.setTime(this.startDate);
                var4 = -1;
            } else {
                var2.setTime(this.startDate);
                var3.setTime(this.endDate);
            }

            var2.set(11, 0);
            var2.set(12, 0);
            var2.set(13, 0);
            var2.set(14, 0);
            var3.set(11, 0);
            var3.set(12, 0);
            var3.set(13, 0);
            var3.set(14, 0);
            if(var2.equals(var3)) {
                return 0;
            } else {
                int var5 = 0;
                var2.add(2, 1);

                while(var2.before(var3)) {
                    ++var5;
                    var2.add(2, 1);
                }

                return (var5 + (!var1 && !var2.equals(var3)?0:1)) * var4;
            }
        } else {
            return 0;
        }
    }

    public int intervalDays() {
        if(this.startDate != null && this.endDate != null) {
            long var1 = 86400000L;
            return this.startDate.before(this.endDate)?(int)((trimDate(this.endDate, false).getTime() - trimDate(this.startDate, true).getTime()) / var1) + 1:((int)((trimDate(this.startDate, false).getTime() - trimDate(this.endDate, true).getTime()) / var1) + 1) * -1;
        } else {
            return 0;
        }
    }

    public int getDays() {
        return this.intervalDays();
    }

    public String toString() {
        return "[" + this.startDate + "," + this.endDate + "]";
    }

    public boolean equals(Object var1) {
        if(var1 == null) {
            return false;
        } else if(!(var1 instanceof DateRange)) {
            return false;
        } else {
            DateRange var2 = (DateRange)var1;
            return var2.startDate.equals(this.startDate) && var2.endDate.equals(this.endDate);
        }
    }

    public int hashCode() {
        return this.startDate.hashCode() / 2 + this.endDate.hashCode() / 2;
    }

    public static DateRange getRange(int var0) {
        return getDefinedRange(var0, (Date)null);
    }

    public static DateRange getRange(int var0, Date var1) {
        return getDefinedRange(var0, var1);
    }

    public static DateRange getRange(String var0) {
        return StringX.isEmpty(var0)?getDefinedRange(99, (Date)null):(var0.equalsIgnoreCase("TODAY")?getRange(0):(var0.equalsIgnoreCase("WEEK")?getRange(3):(var0.equalsIgnoreCase("WEEK_TODAY")?getRange(4):(var0.equalsIgnoreCase("LAST_WEEK")?getRange(5):(var0.equalsIgnoreCase("MONTH")?getRange(6):(var0.equalsIgnoreCase("MONTH_TODAY")?getRange(7):(var0.equalsIgnoreCase("LAST_MONTH")?getRange(8):(var0.equalsIgnoreCase("QUARTER")?getRange(9):(var0.equalsIgnoreCase("QUARTER_TODAY")?getRange(10):(var0.equalsIgnoreCase("LAST_QUARTER")?getRange(11):(var0.equalsIgnoreCase("YEAR")?getRange(12):(var0.equalsIgnoreCase("YEAR_TODAY")?getRange(13):(var0.equalsIgnoreCase("LAST_YEAR")?getRange(14):getDefinedRange(99, (Date)null))))))))))))));
    }

    public static DateRange toMaximal(DateRange var0) {
        return new DateRange(var0.startDate == null?null:trimDate(var0.startDate, true), var0.endDate == null?null:trimDate(var0.endDate, false));
    }

    public static DateRange toMinimal(DateRange var0) {
        return new DateRange(var0.startDate == null?null:trimDate(var0.startDate, false), var0.endDate == null?null:trimDate(var0.endDate, true));
    }

    private static Date trimDate(Date var0, boolean var1) {
        Calendar var2 = Calendar.getInstance();
        var2.setTime(var0);
        if(var1) {
            var2.set(11, 0);
            var2.set(12, 0);
            var2.set(13, 0);
            var2.set(14, 0);
        } else {
            var2.set(11, 23);
            var2.set(12, 59);
            var2.set(13, 59);
            var2.set(14, 999);
        }

        return var2.getTime();
    }

    private static DateRange getDefinedRange(int var0, Date var1) {
        DateRange var2 = null;
        Date var3 = null;
        Date var4 = null;
        Date var5 = var1 == null?new Date():var1;
        Calendar var6 = Calendar.getInstance();
        var6.clear();
        var6.setTime(var5);
        switch(var0) {
            case 0:
                var3 = var5;
                var4 = var5;
                break;
            case 1:
            case 2:
            default:
                var3 = var5;
                var4 = var5;
                break;
            case 3:
                for(int var7 = var6.get(7); var7 != 2; var7 = var6.get(7)) {
                    var6.add(5, -1);
                }

                var3 = var6.getTime();
                var6.add(5, 6);
                var4 = var6.getTime();
                break;
            case 4:
                var2 = getDefinedRange(3, var1);
                var2.setEndDate(trimDate(var5, false));
                break;
            case 5:
                var6.add(5, -7);
                var2 = getDefinedRange(3, var6.getTime());
                break;
            case 6:
                var6.set(5, 1);
                var3 = var6.getTime();
                var6.add(2, 1);
                var6.add(5, -1);
                var4 = var6.getTime();
                break;
            case 7:
                var2 = getDefinedRange(6, var1);
                var2.setEndDate(trimDate(var5, false));
                break;
            case 8:
                var6.add(2, -1);
                var2 = getDefinedRange(6, var6.getTime());
                break;
            case 9:
                int var8 = var6.get(2) / 3;
                var6.set(2, var8 * 3);
                var6.set(5, 1);
                var3 = var6.getTime();
                var6.set(2, (var8 + 1) * 3);
                var6.set(5, 1);
                var6.add(5, -1);
                var4 = var6.getTime();
                break;
            case 10:
                var2 = getDefinedRange(9, var1);
                var2.setEndDate(trimDate(var5, false));
                break;
            case 11:
                var6.add(2, -3);
                var2 = getDefinedRange(9, var6.getTime());
                break;
            case 12:
                var6.set(2, 0);
                var6.set(5, 1);
                var3 = var6.getTime();
                var6.set(2, 11);
                var6.set(5, 31);
                var4 = var6.getTime();
                break;
            case 13:
                var2 = getDefinedRange(12, var1);
                var2.setEndDate(trimDate(var5, false));
                break;
            case 14:
                var6.add(1, -1);
                var2 = getDefinedRange(12, var6.getTime());
        }

        if(var2 == null) {
            var2 = new DateRange(trimDate(var3, true), trimDate(var4, false));
        }

        return var2;
    }

    private static String formatDate(Date var0, String var1) {
        if(var0 == null) {
            return null;
        } else {
            if(internal_formmater == null) {
                internal_formmater = new SimpleDateFormat();
            }

            internal_formmater.applyPattern(var1 == null?"yyyy/MM/dd":var1);
            return internal_formmater.format(var0);
        }
    }
}
