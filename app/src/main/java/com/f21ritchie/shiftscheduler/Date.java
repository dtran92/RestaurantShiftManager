package com.f21ritchie.shiftscheduler;

import java.util.Objects;

public class Date {
    private String dateMonthYear;
    private String dayOfWeek;

    public Date(String dateMonthYear, String dayOfWeek) {
        this.dateMonthYear = dateMonthYear;
        this.dayOfWeek = dayOfWeek;
    }

    public String getDateMonthYear() {
        return dateMonthYear;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return dateMonthYear.equals(date.dateMonthYear) && dayOfWeek.equals(date.dayOfWeek);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateMonthYear, dayOfWeek);
    }
}
