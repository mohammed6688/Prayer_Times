package com.mocomp.prayeralert.model;

import java.io.Serializable;

public class WeekDay implements Serializable {
    String en;
    String ar;

    public WeekDay() {
    }

    public WeekDay(String en, String ar) {
        this.en = en;
        this.ar = ar;
    }

    public WeekDay(String en) {
        this.en = en;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    @Override
    public String toString() {
        return "WeekDay{" +
                "en='" + en + '\'' +
                ", ar='" + ar + '\'' +
                '}';
    }
}
