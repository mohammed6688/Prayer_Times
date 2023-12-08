package com.mocomp.prayeralert;

public interface AppConfig {
    String defaultTimeCalculationMethod = "8";
    String backEndUrl = "https://api.aladhan.com/v1/timingsByCity?city=egypt&country=cairo&state=cairo&method=";
    String defaultBackEndUrl = backEndUrl + defaultTimeCalculationMethod;
}
