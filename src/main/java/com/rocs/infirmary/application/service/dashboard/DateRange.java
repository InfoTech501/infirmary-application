package com.rocs.infirmary.application.service.dashboard;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
/**
 * This class DateRange control the dates to display on the Dashboard Page.
 * */
public class DateRange {
    private final Date startDate;
    private final Date endDate;

    private DateRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    /**
     * This instructs to get the local date for today.
     * @return the local date today.
     */
    public static DateRange daily() {
        LocalDate today = LocalDate.now();
        return new DateRange(
                java.sql.Date.valueOf(today),
                java.sql.Date.valueOf(today)
        );
    }
    /**
     * This instructs to get the local date starting this week Monday to today.
     * @return the local date starting this week Monday to today.
     */
    public static DateRange weekly() {
        LocalDate today = LocalDate.now();
        LocalDate startOfTheWeek = today.with(DayOfWeek.MONDAY);
        return new DateRange(
                java.sql.Date.valueOf(startOfTheWeek),
                java.sql.Date.valueOf(today)
        );
    }
    /**
     * This instructs to get the local date starting this Month day 1 to today.
     * @return the local date starting this Month day 1 to today.
     */
    public static DateRange monthly() {
        LocalDate today = LocalDate.now();
        LocalDate startOfTheMonth = LocalDate.now().withDayOfMonth(1);
        return new DateRange(
                java.sql.Date.valueOf(startOfTheMonth),
                java.sql.Date.valueOf(today)
        );
    }
    /**
     * This instructs to get the local date starting this Year day 1 to today.
     * @return the local date starting this Year day 1 to today.
     */
    public static DateRange yearly() {
        LocalDate today = LocalDate.now();
        LocalDate startOfTheYear = LocalDate.of(today.getYear(), Month.JUNE, 1);
        LocalDate endOfTheYear = LocalDate.of(today.getYear() + 1, Month.MARCH, 31);
        return new DateRange(
                java.sql.Date.valueOf(startOfTheYear),
                java.sql.Date.valueOf(endOfTheYear)
        );
    }
    /**
     * This gets start date
     * @return the start date.
     */
    public Date getStartDate() {
        return startDate;
    }
    /**
     * This gets end date
     * @return the end date.
     */
    public Date getEndDate() {
        return endDate;
    }
}