package com.rocs.infirmary.application.service.dashboard;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

public class DateRange {
    private final Date startDate;
    private final Date endDate;

    private DateRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static DateRange daily() {
        LocalDate today = LocalDate.now();
        return new DateRange(
                java.sql.Date.valueOf(today),
                java.sql.Date.valueOf(today)
        );
    }

    public static DateRange weekly() {
        LocalDate today = LocalDate.now();
        LocalDate startOfTheWeek = today.with(DayOfWeek.MONDAY);
        return new DateRange(
                java.sql.Date.valueOf(startOfTheWeek),
                java.sql.Date.valueOf(today)
        );
    }

    public static DateRange monthly() {
        LocalDate today = LocalDate.now();
        LocalDate startOfTheMonth = LocalDate.now().withDayOfMonth(1);
        return new DateRange(
                java.sql.Date.valueOf(startOfTheMonth),
                java.sql.Date.valueOf(today)
        );
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
