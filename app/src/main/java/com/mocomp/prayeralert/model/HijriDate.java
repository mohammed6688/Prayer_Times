package com.mocomp.prayeralert.model;

import java.io.Serializable;
import java.util.Arrays;

public class HijriDate implements Serializable {
    String date;
    String format;
    String day;
    WeekDay weekDay;
    Month month;
    String year;
    Designation designation;
    String [] holidays;

    public HijriDate() {
    }

    public HijriDate(String date, String format, String day, WeekDay weekDay, Month month, String year, Designation designation) {
        this.date = date;
        this.format = format;
        this.day = day;
        this.weekDay = weekDay;
        this.month = month;
        this.year = year;
        this.designation = designation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Designation getDesignation() {
        return designation;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public String[] getHolidays() {
        return holidays;
    }

    public void setHolidays(String[] holidays) {
        this.holidays = holidays;
    }

    @Override
    public String toString() {
        return "HijriDate{" +
                "date='" + date + '\'' +
                ", format='" + format + '\'' +
                ", day='" + day + '\'' +
                ", weekDay=" + weekDay +
                ", month=" + month +
                ", year='" + year + '\'' +
                ", designation=" + designation +
                ", holidays=" + Arrays.toString(holidays) +
                '}';
    }
}
