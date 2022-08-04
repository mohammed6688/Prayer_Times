package com.mocomp.prayeralert.model;

import java.io.Serializable;

public class Meta implements Serializable {
    long lat;
    long lon;
    String timeZone;

    public Meta() {
    }

    public Meta(long lat, long lon, String timeZone) {
        this.lat = lat;
        this.lon = lon;
        this.timeZone = timeZone;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return "Meta{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
