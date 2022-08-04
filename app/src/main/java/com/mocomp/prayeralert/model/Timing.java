package com.mocomp.prayeralert.model;

import android.util.Log;

import java.io.Serializable;


public class Timing implements Serializable {
    String Fajr;
    String Sunrise;
    String Dhuhr;
    String Asr;
    String Maghrib;
    String Isha;
    String Imsak;
    String midnight;
    String firstThird;
    String lastThird;

    public Timing(String fajr, String sunrise, String dhuhr, String asr, String maghrib, String isha, String imsak, String midnight, String firstThird, String lastThird) {
        this.Fajr = fajr;
        this.Sunrise = sunrise;
        this.Dhuhr = dhuhr;
        this.Asr = asr;
        this.Maghrib = maghrib;
        this.Isha = isha;
        this.Imsak = imsak;
        this.midnight = midnight;
        this.firstThird = firstThird;
        this.lastThird = lastThird;
    }

    public Timing() {
    }

    public String getFajr() {
        Log.e("fajt: ",Fajr);
        return Fajr;
    }

    public void setFajr(String fajr) {
        this.Fajr = fajr;
    }

    public String getSunrise() {
        return Sunrise;
    }

    public void setSunrise(String sunrise) {
        this.Sunrise = sunrise;
    }

    public String getDhuhr() {
        return Dhuhr;
    }

    public void setDhuhr(String dhuhr) {
        this.Dhuhr = dhuhr;
    }

    public String getAsr() {
        return Asr;
    }

    public void setAsr(String asr) {
        this.Asr = asr;
    }

    public String getMaghrib() {
        return Maghrib;
    }

    public void setMaghrib(String maghrib) {
        this.Maghrib = maghrib;
    }

    public String getIsha() {
        return Isha;
    }

    public void setIsha(String isha) {
        this.Isha = isha;
    }

    public String getMidnight() {
        return midnight;
    }

    public void setMidnight(String midnight) {
        this.midnight = midnight;
    }

    public String getImsak() {
        return Imsak;
    }

    public void setImsak(String imsak) {
        Imsak = imsak;
    }

    public String getFirstThird() {
        return firstThird;
    }

    public void setFirstThird(String firstThird) {
        this.firstThird = firstThird;
    }

    public String getLastThird() {
        return lastThird;
    }

    public void setLastThird(String lastThird) {
        this.lastThird = lastThird;
    }

    @Override
    public String toString() {
        return "Timing{" +
                "Fajr='" + Fajr + '\'' +
                ", Sunrise='" + Sunrise + '\'' +
                ", Dhuhr='" + Dhuhr + '\'' +
                ", Asr='" + Asr + '\'' +
                ", Maghrib='" + Maghrib + '\'' +
                ", Isha='" + Isha + '\'' +
                ", Imsak='" + Imsak + '\'' +
                ", midnight='" + midnight + '\'' +
                ", firstThird='" + firstThird + '\'' +
                ", lastThird='" + lastThird + '\'' +
                '}';
    }
}
