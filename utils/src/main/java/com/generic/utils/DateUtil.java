package com.generic.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * The type Date util.
 */
@Log4j2
public class DateUtil {
    private static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private final static String DEFAULT_SYSTEM_DATE_FORMAT = "yyyy-MM-dd";
    private final static DateTimeFormatter DEFAULT_DTF = DateTimeFormatter.ofPattern(DEFAULT_SYSTEM_DATE_FORMAT);

    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {
        {
            put("^\\d{8}$", "yyyyMMdd");
            put("^\\d{12}$", "yyyyMMddHHmm");
            put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
            put("^\\d{14}$", "yyyyMMddHHmmss");
            put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
            put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
            put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
            put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
            put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
            put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
            put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
            put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}:\\d{2}\\.\\d{2}[-+]\\d{2}:\\d{2}$", "yyyy-MM-dd'T'HH:mm:ss.SS");
            put("^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}:\\d{2}\\.\\d{2}[-+]\\d{2}:\\d{2}$", "yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        }
    };

    /**
     * TODO TIMESTAMP return
     **/

    public static final Timestamp getCurrentUtcTimestamp() {
        return new Timestamp(ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond() * 1000);
    }

    public static final Timestamp getCurrentTimestamp() {
        Date dateNow = new Date();
        Timestamp timeStampDate = new Timestamp(dateNow.getTime());
        return timeStampDate;
    }

    /**
     * TODO STRING return
     * **/

    /**
     * Gets cur mon year mmmmyyyy.
     *
     * @return the cur mon year mmmmyyyy
     */
    public static String getCurMonYearMMMMYYYY() {
        return getCurrDatePattern("MMMM yyyy");
    }

    /**
     * Gets curr date pattern.
     *
     * @param pattern the pattern
     * @return the curr date pattern
     */
    public static String getCurrDatePattern(String pattern) {
        return LocalDate.now().format(ofPattern(pattern));
    }

    /**
     * Gets prev curr local date pattern.
     *
     * @param monthsToSubstract the months to substract
     * @param pattern           the pattern
     * @return the prev curr local date pattern
     */
    public static String getPrevCurrLocalDatePattern(Integer monthsToSubstract, String pattern) {
        return formatDate(getPrevLocalDate(LocalDate.now(), monthsToSubstract), pattern);
    }

    public static String parseLocalDateToSystemDefault(LocalDate date) {
        return date.format(DEFAULT_DTF);
    }

    /**
     * Format date string.
     *
     * @param date    the date
     * @param pattern the pattern
     * @return the string
     */
    public static String formatDate(Date date, String pattern) {
        final DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * Format date string.
     *
     * @param date    the date
     * @param pattern the pattern
     * @return the string
     */
    public static String formatDate(String date, String pattern) {
        final DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(parseDate(date, pattern));
    }

    /**
     * Formate date string.
     *
     * @param localDateTime the local date time
     * @param pattern       the pattern
     * @return the string
     */
    public static String formatDate(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(ofPattern(pattern));
    }

    /**
     * Format date string.
     *
     * @param localDate the local date
     * @param pattern   the pattern
     * @return the string
     */
    public static String formatDate(LocalDate localDate, String pattern) {
        return localDate.format(ofPattern(pattern));
    }

    /**
     * Format date to default date time string.
     *
     * @param date     the date
     * @param withTime the with time
     * @return the string
     */
    public static String formatDateToDefaultDateTime(Date date, boolean withTime) {
        final DateFormat dateFormat = withTime ? new SimpleDateFormat(DEFAULT_DATE_TIME_PATTERN) :
            new SimpleDateFormat(DEFAULT_SYSTEM_DATE_FORMAT);
        return dateFormat.format(date);
    }

    /**
     * Format string pattern format string.
     *
     * @param date     the date
     * @param pattern  the pattern
     * @param withTime the with time
     * @return the string
     */
    public static String formatStringPatternFormat(String date, String pattern, boolean withTime) {
        return formatDateToDefaultDateTime(parseDate(date, pattern), withTime);
    }

    /**
     * TODO DATE return
     * **/

    /**
     * Parse date date.
     *
     * @param dateString  the date string
     * @param datePattern the date pattern
     * @return the date
     */
    public static Date parseDate(String dateString, String datePattern) {
        final DateFormat dateFormat = new SimpleDateFormat(datePattern);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            log.debug("Error in parsing Date '" + dateString + "' in pattern '" + datePattern + "' ");
            log.error("e: ", e);
        }
        return null;
    }

    /**
     * Gets first date.
     *
     * @param date the date
     * @return the first date
     */
    public static Date getFirstDate(Date date) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        Integer minDate = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, minDate);
        return cal.getTime();
    }

    /**
     * Gets last date.
     *
     * @param date the date
     * @return the last date
     */
    public static Date getLastDate(Date date) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(date);
        Integer maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, maxDate);
        return cal.getTime();
    }

    public static Date addDaysSkippingWeekendsToDate(LocalDate ldt, int days) {
        LocalDate tmpLdt = addDaysSkippingWeekends(ldt, days);
        return Date.from(tmpLdt.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Parse dadatete date.
     *
     * @param localDateTime the date string
     * @param datePattern   the date pattern
     * @return the date
     */
    public static Date parseLocalDateToDate(LocalDateTime localDateTime, String datePattern) {
        String dateString = formatDate(localDateTime, datePattern);
        return parseDate(dateString, datePattern);
    }

    /**
     * Parse dadatete date.
     *
     * @param dateString  the date string
     * @param datePattern the date pattern
     * @return the date
     */
    public static Date parseStringToDateUsingSystemPattern(String dateString, String datePattern) {
        LocalDateTime ldtFrom = formatLocalDateTime(dateString, DEFAULT_SYSTEM_DATE_FORMAT);
        Date date = DateUtil.parseLocalDateToDate(ldtFrom, datePattern);
        return date;
    }

    /**
     * TODO LOCALDATE return
     * **/

    /**
     * Gets month first date.
     *
     * @param localDate the local date
     * @return the month first date
     */
    public static LocalDate getMonthFirstDate(LocalDate localDate) {
        return localDate.withDayOfMonth(1);
    }

    /**
     * Gets month last date.
     *
     * @param localDate the local date
     * @return the month last date
     */
    public static LocalDate getMonthLastDate(LocalDate localDate) {
        return localDate.withDayOfMonth(localDate.lengthOfMonth());
    }

    /**
     * Gets curr mon first date.
     *
     * @return the curr mon first date
     */
    public static LocalDate getCurrMonFirstDate() {
        return getMonthFirstDate(LocalDate.now());
    }

    /**
     * Gets curr mon last date.
     *
     * @return the curr mon last date
     */
    public static LocalDate getCurrMonLastDate() {
        return getMonthLastDate(LocalDate.now());
    }

    /**
     * Gets prev local date.
     *
     * @param localDate        the local date
     * @param monthsToSubtract the months to subtract
     * @return the prev local date
     */
    public static LocalDate getPrevLocalDate(LocalDate localDate, Integer monthsToSubtract) {
        return localDate.minusMonths(monthsToSubtract);
    }

    /**
     * Parse local date local date.
     *
     * @param dateString  the date string
     * @param datePattern the date pattern
     * @return the local date
     */
    public static LocalDate parseLocalDate(String dateString, String datePattern) {
        return LocalDate.parse(dateString, ofPattern(datePattern));
    }

    /**
     * Convert date to local date local date.
     *
     * @param date the date
     * @return the local date
     */
    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(systemDefault()).toLocalDate();
    }

    public static LocalDate parseLocalDateToSystemDefault(String dateString) {
        return LocalDate.parse(dateString, DEFAULT_DTF);
    }

    public static LocalDate addDaysSkippingWeekends(LocalDate ldt, int days) {

        LocalDate tmpLdt = ldt;
        int addedDays = 0;

        while (addedDays < days) {
            tmpLdt = tmpLdt.plusDays(1);
            if (!isWeekend(tmpLdt)) {
                ++addedDays;
            }
        }
        return tmpLdt;
    }

    public static LocalDateTime getTimeStamp() {
        return LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
    public static LocalDateTime getLocalDateTimeNow() {
        return LocalDateTime.now();
    }
    public static String getLocalDateTimeNow(String format) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
    }


    /**
     * TODO LOCALDATETIME return
     * **/

    /**
     * Convert date to local date time local date time.
     *
     * @param date the date
     * @return the local date time
     */
    public static LocalDateTime convertDate(Date date) {
        return date.toInstant().atZone(systemDefault()).toLocalDateTime();
    }

    /**
     * Parse local date time local date time.
     *
     * @param dateString  the date string
     * @param datePattern the date pattern
     * @return the local date time
     */
    public static LocalDateTime formatLocalDateTime(String dateString, String datePattern) {
        return LocalDateTime.parse(dateString, ofPattern(datePattern));
    }

    /**
     * TODO validations return
     **/

    public static Boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

    public static boolean isValid(String dateStr, String datePattern) {
        try {
            LocalDate.parse(dateStr, ofPattern(datePattern));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * To Determine the pattern by the string date value
     *
     * @param dateString
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     */
    public static String getDateFormat(String dateString) {
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.matches(regexp) || dateString.toLowerCase().matches(regexp)) {
                return DATE_FORMAT_REGEXPS.get(regexp);
            }
        }
        return null;
    }


}


