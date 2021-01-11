/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author armen.arzumanyan@gmail.com
 */
public class DateTimeProvider {

//    public LocalDate convertToLocalDateJava9(Date dateToConvert) {
//        return LocalDate.ofInstant(
//                dateToConvert.toInstant(), ZoneId.systemDefault());
//    }
    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return LocalDateTime.ofInstant(
                dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    public Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public static List<LocalDate> getDatesBetween(
            LocalDate startDate, LocalDate endDate) {

        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }

    public static int getDayNumberNew(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getValue();
    }

    public static DayOfWeek getDayOfWeekFromLocalDate(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day;
    }

    public static int getFirstDayOfMonth() {

        //System.out.println("WoW");
        return 1;
    }

    public static int getLastDayOfMonth(int year, Month month) {

        int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

        return lastDayOfMonth;
    }
    
    

    public static int getLastDayOfCurrentMonth(int year) {

        Month currentMonth = getCurrentMonth();

        int lastDayOfMonth = YearMonth.of(year, currentMonth).lengthOfMonth();

        return lastDayOfMonth;
    }

    public static LocalDate getCurrenDate() {

        return LocalDate.now();
    }

    public static int getCurrentDayOfMonth() {

        return LocalDate.now().getDayOfMonth();
    }

    public static Month getCurrentMonth() {

        return LocalDate.now().getMonth();
    }

    public static int getCurrentYear() {

        return LocalDate.now().getYear();
    }

    public static LocalDateTime getNextDayOfWeek(LocalDateTime localDateTime, int month, DayOfWeek mayOfWeek) {

        LocalDateTime nextDay = localDateTime.with(TemporalAdjusters.dayOfWeekInMonth(month, mayOfWeek));

        return nextDay;
    }

    public static int getLocalWeekNumber(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        int weekNumber = date.get(weekFields.dayOfWeek());
        return weekNumber;
    }

    public static int getWeekDaysInMonth(LocalDate date) {
        return weekDaysInMonth(YearMonth.from(date));
    }

    private static int weekDaysInMonth(YearMonth yearMonth) {
        int len = yearMonth.lengthOfMonth(); // 28-31, supporting leap year
        int dow = yearMonth.atDay(1).getDayOfWeek().getValue(); // 1=Mon, 7=Sun
        return (dow <= 5 ? Math.min(len - 8, 26 - dow) : Math.max(len + dow - 16, 20));
    }

    public static int getBusinessDaysInMonth(final LocalDate ld) {

        int weekDays = 0;
        LocalDate date = ld.withDayOfMonth(1);
        final int intendedMonthValue = ld.getMonthValue();
        do {
            final DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                weekDays++;
            }

            date = date.plusDays(1);
        } while (date.getMonthValue() == intendedMonthValue);

        return weekDays;
    }

    public static Date convertLocalTimeToDate(LocalTime localTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(0, 0, 0, localTime.getHour(), localTime.getMinute(), localTime.getSecond());
        return calendar.getTime();
    }

    public static LocalDateTime convertDateToLocalDateTime(Date dateToConvert) {

        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static LocalTime getTime10Am() {
        return LocalTime.of(10, 00, 00);
    }

    public static LocalTime getTime9Am() {
        return LocalTime.of(9, 00, 00);
    }

    private LocalDateTime previousDay8Pm() {
        return LocalDateTime.now().minusDays(1).withHour(20).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime previousDay11Pm() {
        return LocalDateTime.now().minusDays(1).withHour(23).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime today1Pm() {
        return LocalDateTime.now().withHour(13).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime theDayAfter3Pm() {
        return LocalDateTime.now().plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime today6Pm() {
        return LocalDateTime.now().withHour(18).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime nextDay9Am() {
        return LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime nextDay11Am() {
        return LocalDateTime.now().plusDays(1).withHour(11).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime fourDaysLater3pm() {
        return LocalDateTime.now().plusDays(4).withHour(15).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime sevenDaysLater0am() {
        return LocalDateTime.now().plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    private LocalDateTime eightDaysLater0am() {
        return LocalDateTime.now().plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public LocalDate getInitialDate() {
        return LocalDate.now().plusDays(1);
    }

    public static LocalDate convertDateAsLocalDate(java.util.Date date) {
        return asLocalDate(date, ZoneId.systemDefault());
    }

    public static LocalDateTime convertDateAsLocalDateTime(java.util.Date date) {
        return asLocalDateTime(date, ZoneId.systemDefault());
    }

    public static Integer calculateMonthsDifferences(Date startDate, Date finishDate) {

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar finish = Calendar.getInstance();
        finish.setTime(finishDate);
        int startYear = start.get(Calendar.YEAR);
        int finishYear = finish.get(Calendar.YEAR);
        int startMonth = start.get(Calendar.MONTH);
        int finishMonth = finish.get(Calendar.MONTH);
        int months = (finishYear - startYear) * 12 + finishMonth - startMonth + 1;

        return months;
    }

    public static int getDaysDiff(final Date startDate, final Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();

        return (int) (diff / (24 * 60 * 60 * 1000));
    }

    public static int getDayCountBetewenDates(Date date1, Date date2) {
        try {
            Calendar c1 = new GregorianCalendar();
            c1.setTime(date1);

            Calendar c2 = new GregorianCalendar();
            c2.setTime(date2);

            int numberBeetwenDates = (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / (1000 * 60 * 60 * 24));

            return numberBeetwenDates + 1; //because even when I will pass to this method 2 equal dates - it has to return 1 and not 0
        } catch (Exception e) {
            return -1;
        }
    }

    public static Date getDateAfterGivenMonthNumber(Date date, int monthNumber) {
        if (monthNumber == 0) {
            return date;
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monthNumber);

        return calendar.getTime();
    }

    public static Date getDateBeforeGivenDayNumber(Date date, int dayNumber) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1 * dayNumber);

        return calendar.getTime();
    }

    public static Date getDateWithFirstOrLastDayOfGivenMonth(Date date, boolean firstDay) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, firstDay ? 1 : calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    public static Date getDateWithLastDayHourOfGivenMonth(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.AM_PM, Calendar.PM);
        calendar.set(Calendar.HOUR, 11);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

    public static Date setStartEndTimeForDate(Date date, boolean startTime) {
        if (date == null) {
            return null;
        }

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if (startTime) {
            calendar.set(Calendar.AM_PM, Calendar.AM);
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        } else {
            calendar.set(Calendar.AM_PM, Calendar.PM);
            calendar.set(Calendar.HOUR, 11);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
        }

        return calendar.getTime();
    }

    public static Date setTimeForDate(Date date, int[] hourMinute, boolean startTime) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        int hour = hourMinute[0];
        int minute = hourMinute[1];

        calendar.set(Calendar.AM_PM, (hour <= 12) ? Calendar.AM : Calendar.PM);
        calendar.set(Calendar.HOUR, (hour <= 12) ? hour : hour - 12);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, startTime ? 0 : 59);
        calendar.set(Calendar.MILLISECOND, startTime ? 0 : 999);

        return calendar.getTime();
    }

    public static Date setCurrentTimeToDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        Calendar currentDate = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR, currentDate.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, currentDate.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, currentDate.get(Calendar.SECOND));

        return calendar.getTime();
    }

    public static boolean areDatesInSameMonthYear(Date date1, Date date2) {
        Calendar calendar1 = new GregorianCalendar();
        calendar1.setTime(date1);

        Calendar calendar2 = new GregorianCalendar();
        calendar2.setTime(date2);

        return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)) && (calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH));
    }

    public static int getYearOfNextMonth() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        int nextMonth = calendar.get(Calendar.MONTH) + 1;

        return (nextMonth == 12) ? calendar.get(Calendar.YEAR) + 1 : calendar.get(Calendar.YEAR);
    }

    public static int getNextMonth() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        int nextMonth = calendar.get(Calendar.MONTH) + 1;

        return (nextMonth == 12) ? 0 : nextMonth;
    }

    public static int getCurrentMonthViaGregorianCalendar() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        return calendar.get(Calendar.MONTH);
    }

    public static int getDayCountOfNextMonth() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getDayCountOfCurrentMonth() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static Date getDateAfterGivenDayNumber(Date date, int dayNumber) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayNumber);

        return calendar.getTime();
    }

    public static int getCurrentYearViaGregorianCalendar() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        return calendar.get(Calendar.YEAR);
    }

    public static int getYearOfDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }

    static public Integer calculateAge(Date birthdate) {
        if (birthdate == null) {
            return null;
        }

        Calendar birthdate_calendar = new GregorianCalendar();
        Calendar thisdate_calendar = new GregorianCalendar();
        Integer age = null;

        try {
            birthdate_calendar.setTime(birthdate);
            thisdate_calendar.setTime(new Date());

            age = thisdate_calendar.get(Calendar.YEAR) - birthdate_calendar.get(Calendar.YEAR);

            if ((birthdate_calendar.get(Calendar.MONTH) > thisdate_calendar.get(Calendar.MONTH))
                    || (birthdate_calendar.get(Calendar.MONTH) == thisdate_calendar.get(Calendar.MONTH) && birthdate_calendar.get(Calendar.DAY_OF_MONTH) > thisdate_calendar.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }

        } catch (ArrayIndexOutOfBoundsException ex) {
        }

        return age;
    }

    public static int getDayCountBetewenDatesProper(Date date1, Date date2) {
        Calendar c1 = new GregorianCalendar();
        c1.setTime(toDayStart(date1));

        Calendar c2 = new GregorianCalendar();
        c2.setTime(toDayStart(date2));

        int numberBeetwenDates = (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / (1000 * 60 * 60 * 24));

        return numberBeetwenDates;
    }

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date stringToDate(String strDate, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        format.setLenient(false);

        return format.parse(strDate);
    }

    private static Date toDayStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private static Date toDayEnd(Date date) {
        Date nextDay = addDays(date, 1);
        return toDayStart(nextDay);
    }

    private static Date addDays(Date date, int numberOfDaysToAdd) {
        long origTime = date.getTime();
        Date ret = new Date(origTime + numberOfDaysToAdd * 24l * 60l * 60l * 1000l);
        return ret;
    }

    private static LocalDateTime asLocalDateTime(java.util.Date date, ZoneId zone) {
        if (date == null) {
            return null;
        }

        if (date instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) date).toLocalDateTime();
        } else {
            return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDateTime();
        }
    }

    public static Date toYearStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date toYearEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public static Date toNextYearStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, 1);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date toMonthStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date lastDayOfDate(Date date) {
        if (date == null) {
            return null;
        }

        Date retDate = null;

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.setTime(date);
            int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), day);

            retDate = calendar.getTime();

        } catch (Exception ex) {
        }

        return retDate;
    }

    public static int get(Date date, int field) {
        Calendar cal = Calendar.getInstance();
        return cal.get(field);
    }

    public static Date addMonths(Date date, int numberOfMonths) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, numberOfMonths);
        return cal.getTime();
    }

    public static Date[] convertToTimePeriod(Integer year, Integer filePeriod) {
        if (year == null) {
            return null;
        }

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);
        Date startDate = null, endDate = null;
        if (filePeriod <= 12) {
            nullify(calendar);
            calendar.set(Calendar.MONTH, filePeriod - 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            startDate = calendar.getTime();
            nullify(calendar);
            calendar.add(Calendar.MONTH, 1);
            endDate = calendar.getTime();
        } else {
            nullify(calendar);
            calendar.set(Calendar.MONTH, (filePeriod - 20) * 3 - 3);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            startDate = calendar.getTime();
            nullify(calendar);
            calendar.add(Calendar.MONTH, 3);
            endDate = calendar.getTime();
        }
        return new Date[]{startDate, endDate};
    }

    public static void nullify(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    public static DatePeriod getRangeFromPeriod(String taxYear, String filePeriod) {
        int year = 0;
        try {
            year = Integer.parseInt(taxYear);
        } catch (NumberFormatException e) {

            /* Tax year is not provided */
            return null;
        }

        DatePeriod range = new DatePeriod();

        int period = 0;
        try {
            period = Integer.parseInt(filePeriod);
        } catch (NumberFormatException e) {

            /* File period is not provided */
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        if (period >= 1 && period <= 12) {

            /* start date */
            cal.set(Calendar.MONTH, period - 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            range.setStart(toDayStart(cal.getTime()));

            /* end date */
            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            range.setEnd(toDayEnd(cal.getTime()));
        } else if (period >= 21 && period <= 24) {

            /* start date */
            cal.set(Calendar.MONTH, (period - 20) * 3 - 3);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            range.setStart(toDayStart(cal.getTime()));

            /* end date */
            cal.add(Calendar.MONTH, 3);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            range.setEnd(toDayEnd(cal.getTime()));
        } else {

            /* start date */
            cal.set(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            range.setStart(toDayStart(cal.getTime()));

            /* end date */
            cal.add(Calendar.YEAR, 1);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            range.setEnd(toDayEnd(cal.getTime()));
        }

        return range;
    }

    public static boolean isNullDate(Date date) {
        if (date == null) {
            return true;
        }
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -100);
        return cal.getTime().after(date);
    }

    public static Date getDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return c.getTime();
    }

    public static Date getDateStartEnd(int year, int month, int day, boolean isStart) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        if (isStart) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
        } else {
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 999);
        }
        return c.getTime();
    }

    public static boolean isTheSameDate(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    private static LocalDate asLocalDate(java.util.Date date, ZoneId zone) {
        if (date == null) {
            return null;
        }

        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        } else {
            return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDate();
        }
    }
}
