package com.mocomp.prayeralert.model;

import java.io.Serializable;

public class Data implements Serializable {
    Timing timing;
    Date date;
    Meta meta;

    public Data() {
    }

    public Data(Timing timing, Date date, Meta meta) {
        this.timing = timing;
        this.date = date;
        this.meta = meta;
    }

    public Timing getTiming() {
        return timing;
    }

    public void setTiming(Timing timing) {
        this.timing = timing;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "Data{" +
                "timing=" + timing +
                ", date=" + date +
                ", meta=" + meta +
                '}';
    }
}
