package com.sim.reservation.data.reservation.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeUtils.java
 * 날짜 관련 유틸
 *
 * @author sgh
 * @since 2023.04.03
 */
public class DateTimeUtils {
    public static LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static LocalTime stringToLocalTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static boolean isBefore(String startDate, String endDate) {
        LocalDate start = stringToLocalDate(startDate);
        LocalDate end = stringToLocalDate(endDate);
        return start.isBefore(end);
    }

    public static boolean isDateAfterToday(String date) {
        LocalDate localDate = stringToLocalDate(date);
        return localDate.isAfter(LocalDate.now());
    }

}
