package com.mark.cheng.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author cheng
 * @since 2022/6/20 21:28
 **/
@Slf4j
@UtilityClass
public class DateUtils {

    private static LocalDateTime startOf(int unit) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        calendar.set(unit, calendar.getActualMinimum(unit));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }

    private static LocalDateTime endOf(int unit) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        calendar.set(unit, calendar.getActualMaximum(unit));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime startOfTheMonth() {
        return startOf(Calendar.DAY_OF_MONTH);
    }

    public static LocalDateTime endOfTheMonth() {
        return endOf(Calendar.DAY_OF_MONTH);
    }

    public static LocalDateTime startOfTheWeek() {
        return startOf(Calendar.DAY_OF_WEEK);
    }

    public static LocalDateTime endOfTheWeek() {
        return endOf(Calendar.DAY_OF_WEEK);
    }

    public static Date LdtToDate(LocalDateTime ldt) {
        ZonedDateTime zonedDateTime = ldt.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public static void main(String[] args) {
        LocalDateTime weekStart = DateUtils.startOfTheWeek();
        LocalDateTime weekEnd = DateUtils.endOfTheWeek();
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(weekStart) || now.isAfter(weekEnd)) {
            System.out.println("expired");
        } else {
            System.out.println("OK");
        }
        System.out.println("now = " + now);
        System.out.println("======================");
        System.out.println("startOfTheMonth() = " + startOfTheMonth());
        System.out.println("endOfTheMonth() = " + endOfTheMonth());
        System.out.println("startOfTheWeek() = " + startOfTheWeek());
        System.out.println("endOfTheWeek() = " + endOfTheWeek());
        System.out.println("======================");
    }
}
