package com.mocomp.prayeralert.model;

import java.io.Serializable;

public class Date implements Serializable {
    String readable;
    String timeStamp;
    HijriDate hijriDate;
    GregorianDate gregorianDate;

    public Date(String readable, String timeStamp, HijriDate hijriDate, GregorianDate gregorianDate) {
        this.readable = readable;
        this.timeStamp = timeStamp;
        this.hijriDate = hijriDate;
        this.gregorianDate = gregorianDate;
    }

    public String getReadable() {
        return readable;
    }

    public void setReadable(String readable) {
        this.readable = readable;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public HijriDate getHijriDate() {
        return hijriDate;
    }

    public void setHijriDate(HijriDate hijriDate) {
        this.hijriDate = hijriDate;
    }

    public GregorianDate getGregorianDate() {
        return gregorianDate;
    }

    public void setGregorianDate(GregorianDate gregorianDate) {
        this.gregorianDate = gregorianDate;
    }

    @Override
    public String toString() {
        return "Date{" +
                "readable='" + readable + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", hijriDate=" + hijriDate +
                ", gregorianDate=" + gregorianDate +
                '}';
    }
}
