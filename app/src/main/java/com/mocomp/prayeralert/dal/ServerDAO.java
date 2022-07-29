package com.mocomp.prayeralert.dal;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mocomp.prayeralert.AppController;
import com.mocomp.prayeralert.interfaces.Dao;
import com.mocomp.prayeralert.model.Data;
import com.mocomp.prayeralert.model.Date;
import com.mocomp.prayeralert.model.Designation;
import com.mocomp.prayeralert.model.GregorianDate;
import com.mocomp.prayeralert.model.HijriDate;
import com.mocomp.prayeralert.model.Meta;
import com.mocomp.prayeralert.model.Month;
import com.mocomp.prayeralert.model.Timing;
import com.mocomp.prayeralert.model.WeekDay;

public class ServerDAO implements Dao {
    public int statusCode=0;
    private final String TAG = "RESPONSE";
    static String tag_json_obj = "json_obj_req";
    public Data data;
    public static ServerDAO serverDAO;

    public ServerDAO() {
        serverDAO=this;
    }

     public int getData(String url) {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                response -> {
                    Log.d(TAG, response.toString());
                    try {
                        statusCode=response.getInt("code");
                        String fajr = response.getJSONObject("data").getJSONObject("timings").get("Fajr").toString();
                        String shurooq = response.getJSONObject("data").getJSONObject("timings").get("Sunrise").toString();
                        String zuhr = response.getJSONObject("data").getJSONObject("timings").get("Dhuhr").toString();
                        String asr = response.getJSONObject("data").getJSONObject("timings").get("Asr").toString();
                        String magrib = response.getJSONObject("data").getJSONObject("timings").get("Maghrib").toString();
                        String isa = response.getJSONObject("data").getJSONObject("timings").get("Isha").toString();
                        String imsak = response.getJSONObject("data").getJSONObject("timings").get("Imsak").toString();
                        String midnight = response.getJSONObject("data").getJSONObject("timings").get("Midnight").toString();
                        String firstThird = response.getJSONObject("data").getJSONObject("timings").get("Firstthird").toString();
                        String lastThird = response.getJSONObject("data").getJSONObject("timings").get("Lastthird").toString();

                        Timing timing =new Timing(fajr, shurooq, zuhr, asr, magrib, isa, imsak,midnight,firstThird,lastThird);

                        String readable = response.getJSONObject("data").getJSONObject("date").get("readable").toString();
                        String timestamp = response.getJSONObject("data").getJSONObject("date").get("timestamp").toString();

                        String hijriDate = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").get("date").toString();
                        String hijriDateFormat = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").get("format").toString();
                        String hijriDay = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").get("day").toString();
                        String hijriWeekdayEn = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("weekday").get("en").toString();
                        String hijriWeekdayAn = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("weekday").get("ar").toString();
                        int hijriMonthNum = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("month").getInt("number");
                        String hijriMonthEn = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("month").get("en").toString();
                        String hijriMonthAr = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("month").get("ar").toString();
                        String hijriMonthYear = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").get("year").toString();
                        String hijriAbbreviated = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("designation").get("abbreviated").toString();
                        String hijriExpanded = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("designation").get("expanded").toString();


                        Designation hijriDesignation=new Designation(hijriAbbreviated,hijriExpanded);
                        Month hijriMonth=new Month(hijriMonthNum,hijriMonthEn,hijriMonthAr);
                        WeekDay hijriWeekDay=new WeekDay(hijriWeekdayEn,hijriWeekdayAn);
                        HijriDate hijriDateObj=new HijriDate(hijriDate,hijriDateFormat,hijriDay,hijriWeekDay,hijriMonth,hijriMonthYear,hijriDesignation);


                        String gregorianDate = response.getJSONObject("data").getJSONObject("date").getJSONObject("gregorian").get("date").toString();
                        String gregorianDateFormat = response.getJSONObject("data").getJSONObject("date").getJSONObject("gregorian").get("format").toString();
                        String gregorianDay = response.getJSONObject("data").getJSONObject("date").getJSONObject("gregorian").get("day").toString();
                        String gregorianWeekdayEn = response.getJSONObject("data").getJSONObject("date").getJSONObject("gregorian").getJSONObject("weekday").get("en").toString();
                        int gregorianMonthNum = response.getJSONObject("data").getJSONObject("date").getJSONObject("gregorian").getJSONObject("month").getInt("number");
                        String gregorianMonthEn = response.getJSONObject("data").getJSONObject("date").getJSONObject("gregorian").getJSONObject("month").get("en").toString();
                        String gregorianMonthYear = response.getJSONObject("data").getJSONObject("date").getJSONObject("gregorian").get("year").toString();
                        String gregorianAbbreviated = response.getJSONObject("data").getJSONObject("date").getJSONObject("gregorian").getJSONObject("designation").get("abbreviated").toString();
                        String gregorianExpanded = response.getJSONObject("data").getJSONObject("date").getJSONObject("gregorian").getJSONObject("designation").get("expanded").toString();

                        Designation gregorianDesignation=new Designation(gregorianAbbreviated,gregorianExpanded);
                        Month gregorianMonth=new Month(gregorianMonthNum,gregorianMonthEn);
                        WeekDay gregorianWeekDay=new WeekDay(gregorianWeekdayEn);

                        GregorianDate gregorianDateObj=new GregorianDate(gregorianDate,gregorianDateFormat,gregorianDay,gregorianWeekDay,gregorianMonth,gregorianMonthYear,gregorianDesignation);
                        Date date=new Date(readable,timestamp,hijriDateObj,gregorianDateObj);


                        long latitude = response.getJSONObject("data").getJSONObject("meta").getLong("latitude");
                        long longitude = response.getJSONObject("data").getJSONObject("meta").getLong("longitude");
                        String timezone = response.getJSONObject("data").getJSONObject("meta").get("timezone").toString();


                        Meta meta=new Meta(latitude,longitude,timezone);
                        data=new Data(timing,date,meta);


                        Log.e("code", String.valueOf(statusCode));
                    } catch (Exception e) {
                        Log.e("getData Exception", e.toString());
//                        e.printStackTrace();
                    }

                }, error -> {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        return statusCode;
    }
}
