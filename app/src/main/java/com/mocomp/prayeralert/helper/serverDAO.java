package com.mocomp.prayeralert.helper;

public class serverDAO {
    private static final String TAG = "error in server DAO";
    String tag_json_obj = "json_obj_req";

   /* public PrayerTimes getTimes(String url){
        PrayerTimes prayerTimes ;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                response -> {
                    Log.d(TAG, response.toString());
                    try {
                        String fajr = response.getJSONObject("data").getJSONObject("timings").get("Fajr").toString();
                        String zuhr = response.getJSONObject("data").getJSONObject("timings").get("Dhuhr").toString();
                        String asr = response.getJSONObject("data").getJSONObject("timings").get("Asr").toString();
                        String magrib = response.getJSONObject("data").getJSONObject("timings").get("Maghrib").toString();
                        String isa = response.getJSONObject("data").getJSONObject("timings").get("Isha").toString();
                        String shurooq = response.getJSONObject("data").getJSONObject("timings").get("Sunrise").toString();
                        String midnight = response.getJSONObject("data").getJSONObject("timings").get("Midnight").toString();
                        String hijriDate = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").get("date").toString();
                        String hijriMonthTxt;
                        if(AppController.getPrefranceData("lang").equals("ar")){
                            hijriMonthTxt = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("month").get("ar").toString();
                        }else {
                            hijriMonthTxt = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("month").get("en").toString();
                        }
                        String city = response.getJSONObject("data").getJSONObject("meta").get("timezone").toString();
                        String gregoryDate = response.getJSONObject("data").getJSONObject("date").getJSONObject("gregorian").get("date").toString();

                        prayerTimes = new PrayerTimes(fajr,shurooq,zuhr,asr,magrib,isa,midnight,hijriDate,gregoryDate, city);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }, error -> {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    // hide the progress dialog
                });

        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        return prayerTimes;
    }*/
}
