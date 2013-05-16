package org.onlinetaskforce.common.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.onlinetaskforce.common.enumerations.TijdEnum;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Utility class for a Date and Time.
 *
 * @author jordens
 * @since 01/05/2013
 */
public final class DateTimeUtil {
    private DateTimeUtil() {
    }

    /**
     * Returns the UTC time for the given GMT+1 time. When the parameter is null then null will be returned.
     * <p/>
     * ATTENTION! If you want to do this: DateTimeUtil.getUtcTime(new Date()), please use new DateMidnight(DateTimeZone.UTC).toDate() instead.
     * <p/>
     * This DOES NOT return a UTC date, it's a CEST date with the time changed to its UTC equivalent.
     *
     * @param time see description
     * @return see description
     */
    public static Date getUtcTime(Date time) {
        Date result = null;
        if (time != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.add(Calendar.MILLISECOND, -1 * TimeZone.getDefault().getOffset(time.getTime()));
            result = cal.getTime();
        }
        return result;
    }

    /**
     * Checks if the given periodes overlap eachother.
     * REMARK: the bouindaries are inclusive and time parts are ignored
     * @param from1 start of period1
     * @param to1 end of period 1
     * @param from2 start of period 2
     * @param to2 end of period 2
     * @return @ see description
     */
    public static boolean overlap(Date from1, Date to1, Date from2, Date to2) {
        Date start1 = resetTimeTozero(from1);
        Date end1 = resetTimeTozero(to1);
        Date start2 = resetTimeTozero(from2);
        Date end2 = resetTimeTozero(to2);
        if (start2.compareTo(start1) >= 0 && start2.compareTo(end1) <= 0) {
            return true;
        }
        if (end2.compareTo(start1) >= 0 && end2.compareTo(end1) <= 0) {
            return true;
        }

        return false;
    }

    /**
     * sets the time parts to O for the given date
     * @param date the date to change
     * @return @see description
     */
    private static Date resetTimeTozero(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Formats a date using the given pattern format. When either parameter is null then null will be returned
     *
     * @param date   see description
     * @param format see description
     * @return see description
     */
    public static String formatDate(Date date, String format) {
        if (format == null) {
            throw new IllegalArgumentException("format-pattern cannot be NULL when formatting a date");
        }
        return date != null ? DateFormatUtils.format(date, format) : null;
    }

    /**
     * Gives the date of tomorrow
     *
     * @return See Description
     */
    public static Date getTomorrow() {
        return DateUtils.addDays(new Date(), 1);
    }

    /**
     * Gives the date of yesterday
     *
     * @return See Description
     */
    public static Date getYesterday() {
        return DateUtils.addDays(new Date(), -1);
    }

    /**
     * Gives a date which lies an amount of days in the future
     *
     * @param days The amount of days
     * @return See Description
     */
    public static Date getXDaysInFuture(Integer days) {
        return DateUtils.addDays(new Date(), days);
    }

    /**
     * Gives a date which lies an amount of days in the past
     *
     * @param days The amount of days
     * @return See Description
     */
    public static Date getXDaysInPast(Integer days) {
        return DateUtils.addDays(new Date(), -days);
    }

    /**
     * Gives the date of exactly a year in the future
     *
     * @return See Description
     */
    public static Date getNextYear() {
        return DateUtils.addYears(new Date(), 1);
    }

    /**
     * Gives the date of exactly a year in the past
     *
     * @return See Description
     */
    public static Date getLastYear() {
        return DateUtils.addYears(new Date(), -1);
    }

    /**
     * Calculates the days between 2 dates.
     * We return an absolute value, so when the second date is before the first date, you get the number of days as a positive value.
     *
     * @param first  first date
     * @param second second date
     * @return the days between, or null when one of the two given dates is null
     */
    public static Integer getDaysBetween(Date first, Date second) {
        Integer daysBetween = null;
        if (first != null && second != null) {
            daysBetween = Math.abs(Days.daysBetween(new DateTime(first.getTime()), new DateTime(second.getTime())).getDays());
        }
        return daysBetween;
    }

    /**
     * Returns a date from the given day, month year. You can use the "human" int for the month,
     * this method will "convert it to a java month". time = midnight in the default timezone.
     *
     * @param day   the day
     * @param month the month
     * @param year  the year
     * @return the date
     */
    public static Date getDate(int day, int month, int year) {
        return new DateMidnight(year, month, day).toDate();
    }

    /**
     * Returns a date from the given day, month and year, time = midnight in the UTC timezone.
     *
     * @param day   the day
     * @param month the month
     * @param year  the year
     * @return the date
     */
    public static Date getDateUtc(int day, int month, int year) {
        return new DateMidnight(year, month, day, DateTimeZone.UTC).toDate();
    }

    /**
     * Returns a date from the given day, month year. The time is set to given hours, minutes and seconds in the default timezone.
     *
     * @param day   the day
     * @param month the month
     * @param year  the year
     * @return the date
     */
    @SuppressWarnings("MethodWithTooManyParameters")
    public static Date getDateTime(int day, int month, int year, int hours, int minutes, int seconds) {
        return new DateTime(year, month, day, hours, minutes, seconds).toDate();
    }

    /**
     * Returns a date from the given day, month, year. The time is set to given hours, minutes and seconds in the UTC timezone.
     *
     * @param day   the day
     * @param month the month
     * @param year  the year
     * @return the date
     */
    @SuppressWarnings("MethodWithTooManyParameters")
    public static Date getDateTimeUtc(int day, int month, int year, int hours, int minutes) {
        return new DateTime(year, month, day, hours, minutes, DateTimeZone.UTC).toDate();
    }

    /**
     * Converts the given day, month and year to string of format: dd-mm-yyyy.
     * Month and day are padded with "0" (so 20-5-1990 becomes 20-05-1990)
     * <p/>
     * When day is null, it is replaced with "00".
     * When month is null, it is replaced with "00".
     *
     * @param day   day
     * @param month month
     * @param year  year
     * @return the string representation of the given date.
     */
    public static String convertToString(Integer day, Integer month, Integer year) {
        String dayString = day == null ? "00" : StringUtils.leftPad(String.valueOf(day), 2, "0");
        String monthString = month == null ? "00" : StringUtils.leftPad(String.valueOf(month), 2, "0");

        String separator = "-";
        String[] elements = {dayString, monthString, String.valueOf(year)};
        return StringUtils.join(elements, separator);
    }

    public static int getUur(TijdEnum tijd) {
        String[] split = StringUtils.split(tijd.toString(), "_");
        return Integer.valueOf(split[1]);
    }

    public static int getMinuten(TijdEnum tijd) {
        String[] split = StringUtils.split(tijd.toString(), "_");
        return Integer.valueOf(split[2]);
    }

    public static TijdEnum getTijdEnum(GregorianCalendar calendar) {
        StringBuilder builder = new StringBuilder("TIJD_");
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        String hourStr = Integer.toString(hour);
        String minuteStr = Integer.toString(minute);
        builder.append(StringUtils.leftPad(hourStr, 2, "0"));
        builder.append("_");
        builder.append(StringUtils.leftPad(minuteStr, 2, "0"));

        return TijdEnum.valueOf(builder.toString());
    }
}
