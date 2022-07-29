package com.mocomp.prayeralert.model;

import java.io.Serializable;

public class Designation implements Serializable {
    String abbreviated;
    String expanded;

    public Designation(String abbreviated, String expanded) {
        this.abbreviated = abbreviated;
        this.expanded = expanded;
    }

    public String getAbbreviated() {
        return abbreviated;
    }

    public void setAbbreviated(String abbreviated) {
        this.abbreviated = abbreviated;
    }

    public String getExpanded() {
        return expanded;
    }

    public void setExpanded(String expanded) {
        this.expanded = expanded;
    }

    @Override
    public String toString() {
        return "Designation{" +
                "abbreviated='" + abbreviated + '\'' +
                ", expanded='" + expanded + '\'' +
                '}';
    }
}
