package com.f21ritchie.shiftscheduler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class Date {
    private String date;
    private String dayOfWeek;
    private String month;
    private int year;
    private String busy;


    public Date(String date, String dayOfWeek, String month, int year) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.month = month;
        this.year = year;
    }

    public Date(LocalDate localDate){
        CalendarUtils.selectedDate = localDate;
        this.date = CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        this.dayOfWeek = CalendarUtils.selectedDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.getDefault());
        this.month = CalendarUtils.formattedMonth(CalendarUtils.selectedDate);
        this.year = CalendarUtils.selectedDate.getYear();
        if (CalendarUtils.selectedDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                CalendarUtils.selectedDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            this.busy = "Y";
        }
        else {
            this.busy = "N";
        }
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getBusy() {
        return busy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Date date1 = (Date) o;
        return date.equals(date1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
