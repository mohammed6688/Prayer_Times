package com.mocomp.prayeralert.models;

import java.io.Serializable;

public class PrayerTimes implements Serializable {
    String fajr;
    String shurooq;
    String zuhr;
    String asr;
    String magrib;
    String isa;
    String midnight;
    String hijriDate;
    String gregorianDate;
    String city;

    public PrayerTimes(String fajr, String shurooq, String zuhr, String asr, String magrib, String isa, String midnight, String hijriDate, String gregorianDate, String city) {
        this.fajr = fajr;
        this.shurooq = shurooq;
        this.zuhr = zuhr;
        this.asr = asr;
        this.magrib = magrib;
        this.isa = isa;
        this.midnight = midnight;
        this.hijriDate = hijriDate;
        this.gregorianDate = gregorianDate;
        this.city = city;
    }

    public PrayerTimes() {
    }

    public String getFajr() {
        return fajr;
    }

    public void setFajr(String fajr) {
        this.fajr = fajr;
    }

    public String getShurooq() {
        return shurooq;
    }

    public void setShurooq(String shurooq) {
        this.shurooq = shurooq;
    }

    public String getZuhr() {
        return zuhr;
    }

    public void setZuhr(String zuhr) {
        this.zuhr = zuhr;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getMagrib() {
        return magrib;
    }

    public void setMagrib(String magrib) {
        this.magrib = magrib;
    }

    public String getIsa() {
        return isa;
    }

    public void setIsa(String isa) {
        this.isa = isa;
    }

    public String getMidnight() {
        return midnight;
    }

    public void setMidnight(String midnight) {
        this.midnight = midnight;
    }

    public String getHijriDate() {
        return hijriDate;
    }

    public void setHijriDate(String hijriDate) {
        this.hijriDate = hijriDate;
    }

    public String getGregorianDate() {
        return gregorianDate;
    }

    public void setGregorianDate(String gregorianDate) {
        this.gregorianDate = gregorianDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
