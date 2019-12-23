package com.xxx.predictionofcurrentsignal.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDate GetThisWeekMonday()
    {
        LocalDate date = LocalDate.now();
        LocalDate firstDate = null;
        switch (date.getDayOfWeek())
        {
            case MONDAY:
                firstDate = date;
                break;
            case TUESDAY:
                firstDate = date.plusDays(-1);
                break;
            case WEDNESDAY:
                firstDate = date.plusDays(-2);
                break;
            case THURSDAY:
                firstDate = date.plusDays(-3);
                break;
            case FRIDAY:
                firstDate = date.plusDays(-4);
                break;
            case SATURDAY:
                firstDate = date.plusDays(-5);
                break;
            case SUNDAY:
                firstDate = date.plusDays(-6);
                break;
        }
        return firstDate;
    }

    public static LocalDate GetThisWeekSunday()
    {
        LocalDate date = null;
        LocalDate today = GetThisWeekMonday();
        date=today.plusDays(6);
        return date;
    }

}
