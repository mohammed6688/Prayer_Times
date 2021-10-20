package com.mocomp.prayertimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.mocomp.prayertimes.Theme.PREFS_NAME;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="tag" ;
    String tag_json_obj = "json_obj_req";
    int NextSalat;
    String time2;
    long difference;
    String Output;
    String remaintimevalue;
    String fajr,zuhr,asr,magrib,isa,shurooq;
    CardView card1,card2,card3,card4,card5,card6;
    String dayOfTheWeek;
    String Day;
    String monthString;
    String monthNumber;
    String year;
    String gregoryDate;
    String City;
    String hijriMonthTxt;
    String url = "https://api.aladhan.com/v1/timingsByCity?city=egypt&country=cairo&method=8";

    //String url = "https://muslimsalat.com/cairo/Qalyubia/toukh/daily/1.json?key=a8e72d680845a42fbb23891020d968ca";

    ProgressDialog pDialog;
    TextView hijriDay,hijriMonth ,Fajr,Zuhr,Asr,Majreb,isha,nextSalat,remainTime,day,dateDay,dateMonth,city,shorooq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //to adjust the style when the app started
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        //"theme" is the same key 0 is the default value
        int theme = settings.getInt("theme", 0);

        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(AppController.getPrefranceData("lang").equals("ar")){
            Locale locale2 = new Locale("ar");
            Locale.setDefault(locale2);

            Configuration config2 = new Configuration();
            config2.locale = locale2;

            getBaseContext().getResources().updateConfiguration(
                    config2,getBaseContext().getResources().getDisplayMetrics());
        }else if (AppController.getPrefranceData("lang").equals("en")){
            Locale locale2 = new Locale("en");
            Locale.setDefault(locale2);

            Configuration config2 = new Configuration();
            config2.locale = locale2;

            getBaseContext().getResources().updateConfiguration(
                    config2,getBaseContext().getResources().getDisplayMetrics());
        }


        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        initializeVariables();
        initializeListeners();
        getMethod();
        getData();


    }

    private void initializeVariables() {
        shorooq = findViewById(R.id.shorooq);
        nextSalat = findViewById(R.id.nextSalat);
        remainTime = findViewById(R.id.remainTime);
        day = findViewById(R.id.day);
        dateDay = findViewById(R.id.dateDay);
        dateMonth = findViewById(R.id.dateMonth);
        city = findViewById(R.id.city);
        hijriDay = findViewById(R.id.hijriDay);
        hijriMonth = findViewById(R.id.hijriMonth);
        Fajr = findViewById(R.id.fajr);
        Zuhr = findViewById(R.id.zuhr);
        Asr = findViewById(R.id.asr);
        Majreb = findViewById(R.id.majreb);
        isha = findViewById(R.id.isha);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void initializeListeners() {
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fajr != null){
                    try {
                        String value;
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                        final Date dateObj = sdf.parse(fajr);
                        String newFajr=new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                        if (time2.equals(fajr)){
                            value =nextDay(gregoryDate)+" "+newFajr;
                            //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                        }else {
                            value =monthNumber+"/"+(Integer.parseInt(Day))+"/"+year+" "+newFajr;
                        }

                        Log.e("value",value);
                        remaintimevalue=timediff(currentdatetime(),value);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, remaintimevalue, Toast.LENGTH_SHORT).show();
                }
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shurooq != null){
                    try {
                        String value;
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                        final Date dateObj = sdf.parse(shurooq);
                        String newFajr=new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                        if (time2.equals(fajr)){
                            value =nextDay(gregoryDate)+" "+newFajr;
                            //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                        }else {
                            value =monthNumber+"/"+(Integer.parseInt(Day))+"/"+year+" "+newFajr;
                        }
                        String remaintime=timediff(currentdatetime(),value);
                        Toast.makeText(MainActivity.this, remaintime, Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zuhr != null){
                    try {
                        String value;
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                        final Date dateObj = sdf.parse(zuhr);
                        String newFajr=new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                        if (time2.equals(fajr)){
                            value =nextDay(gregoryDate)+" "+newFajr;
                            //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                        }else {
                            value =monthNumber+"/"+(Integer.parseInt(Day))+"/"+year+" "+newFajr;
                        }
                        String remaintime=timediff(currentdatetime(),value);
                        Toast.makeText(MainActivity.this, remaintime, Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asr != null){
                    try {
                        String value;
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                        final Date dateObj = sdf.parse(asr);
                        String newFajr=new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                        if (time2.equals(fajr)){
                            value =nextDay(gregoryDate)+" "+newFajr;
                            //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                        }else {
                            value =monthNumber+"/"+(Integer.parseInt(Day))+"/"+year+" "+newFajr;
                        }
                        String remaintime=timediff(currentdatetime(),value);
                        Toast.makeText(MainActivity.this, remaintime, Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (magrib != null){
                    try {
                        String value;
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                        final Date dateObj = sdf.parse(magrib);
                        String newFajr=new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                        if (time2.equals(fajr)){
                            value =nextDay(gregoryDate)+" "+newFajr;
                            //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                        }else {
                            value =monthNumber+"/"+(Integer.parseInt(Day))+"/"+year+" "+newFajr;
                        }
                        String remaintime=timediff(currentdatetime(),value);
                        Toast.makeText(MainActivity.this, remaintime, Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isa != null){
                    try {
                        String value;
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                        final Date dateObj = sdf.parse(isa);
                        String newFajr=new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                        if (time2.equals(fajr)){
                            value =nextDay(gregoryDate)+" "+newFajr;
                            //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                        }else {
                            value =monthNumber+"/"+(Integer.parseInt(Day))+"/"+year+" "+newFajr;
                        }
                        String remaintime=timediff(currentdatetime(),value);
                        Toast.makeText(MainActivity.this, remaintime, Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void getMethod() {
        String method =AppController.getPrefranceData("method");
        if (!method.isEmpty()){
            url="https://api.aladhan.com/v1/timingsByCity?city=egypt&country=cairo&method="+method;
        }
    }

    private String nextDay(String dt) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(dt));
        c.add(Calendar.DATE, 1);  // number of days to add
        dt = sdf.format(c.getTime());  // dt is now the new date
        SimpleDateFormat newSdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String newDt = newSdf.format(c.getTime());
        return newDt;
    }

    private void getData() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            fajr = response.getJSONObject("data").getJSONObject("timings").get("Fajr").toString();
                            zuhr = response.getJSONObject("data").getJSONObject("timings").get("Dhuhr").toString();
                            asr = response.getJSONObject("data").getJSONObject("timings").get("Asr").toString();
                            magrib = response.getJSONObject("data").getJSONObject("timings").get("Maghrib").toString();
                            isa = response.getJSONObject("data").getJSONObject("timings").get("Isha").toString();
                            shurooq = response.getJSONObject("data").getJSONObject("timings").get("Sunrise").toString();
                            String hijriDate = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").get("date").toString();
                            if(AppController.getPrefranceData("lang").equals("ar")){
                                hijriMonthTxt = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("month").get("ar").toString();

                            }else {
                                hijriMonthTxt = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("month").get("en").toString();

                            }
                            City = response.getJSONObject("data").getJSONObject("meta").get("timezone").toString();
                            gregoryDate =  response.getJSONObject("data").getJSONObject("date").getJSONObject("gregorian").get("date").toString();

                            city.setText(City);

                            setPrayerTimes();


                            @SuppressLint("SimpleDateFormat") SimpleDateFormat format1=new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            Date dt1=format1.parse(gregoryDate);
                            dayOfTheWeek = (String) DateFormat.format("EEEE", dt1); // Thursday
                            Day          = (String) DateFormat.format("dd",   dt1); // 20
                            monthString  = (String) DateFormat.format("MMM",  dt1); // Jun
                            monthNumber  = (String) DateFormat.format("MM",   dt1); // 06
                            year         = (String) DateFormat.format("yyyy", dt1); // 2013
                            dateDay.setText(Day);
                            dateMonth.setText(" "+monthString);
                            day.setText(dayOfTheWeek);


                            @SuppressLint("SimpleDateFormat") SimpleDateFormat format2=new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            Date dt2=format2.parse(hijriDate);
                            String dayOfTheWeekHijri = (String) DateFormat.format("EEEE", dt2); // Thursday
                            String DayHijri          = (String) DateFormat.format("dd",   dt2); // 20
                            String monthStringHijri  = (String) DateFormat.format("MMM",  dt2); // Jun
                            String monthNumberHijri  = (String) DateFormat.format("MM",   dt2); // 06
                            String yearHijri         = (String) DateFormat.format("yyyy", dt2); // 2013

                            hijriDay.setText(DayHijri);
                            hijriMonth.setText(" "+hijriMonthTxt);

                            if (checktimings(getCurrentTime(),fajr))
                            {
                                NextSalat = 1;
                                Log.e("time", getCurrentTime());
                            } else if (checktimings(getCurrentTime(),shurooq)){

                                NextSalat = 2;
                                Log.e("time","2");

                            } else if (checktimings(getCurrentTime(),zuhr)){

                                NextSalat = 3;
                                Log.e("time","3");
                            } else if (checktimings(getCurrentTime(),asr)){

                                NextSalat = 4;
                                Log.e("time","4");

                            }else if (checktimings(getCurrentTime(),magrib)){
                                NextSalat = 5;
                                Log.e("time","5");

                            }else if (checktimings(getCurrentTime(),isa)){

                                NextSalat = 6;
                                Log.e("time","6");

                            }else {
                                NextSalat = 1;
                                Log.e("time", getCurrentTime());
                            }
                            if (NextSalat == 1){
                                nextSalat.setText(getResources().getString(R.string.fajr_prayer)+fajr);
                                time2 = fajr;
                            }else if (NextSalat == 2){
                                nextSalat.setText(getResources().getString(R.string.shurroq_prayer)+shurooq);
                                time2 = shurooq;
                            }else if (NextSalat == 3){
                                nextSalat.setText(getResources().getString(R.string.zuhr_prayer)+zuhr);
                                time2 = zuhr;
                            }else if (NextSalat == 4){
                                nextSalat.setText(getResources().getString(R.string.asr_prayer)+asr);
                                time2 = asr;
                            }else if (NextSalat == 5){
                                nextSalat.setText(getResources().getString(R.string.magrib_prayer)+magrib);
                                time2 = magrib;
                            }else if (NextSalat == 6){
                                nextSalat.setText(getResources().getString(R.string.isa_prayer)+isa);
                                time2 = isa;
                            }

                            if (time2.equals(fajr)){
                                Log.e("currentdatetime",currentdatetime());
                                Log.e("timeeee",timediff(currentdatetime(),"04/26/2021 11:00 pm"));

                                final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                                final Date dateObj = sdf.parse(fajr);
                                String newFajr=new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);

                                String value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;

                                Log.e("value",value);
                                remaintimevalue=timediff(currentdatetime(),value);
                                Log.e("remaining",remaintimevalue);

                            }else {
                                String value =monthNumber+"/"+Day+"/"+year+" "+time2;
                                remaintimevalue=timediff(currentdatetime(),value);
                            }

                            remainTime.setText(remaintimevalue+getResources().getString(R.string.is_remaining));
                            Log.e("remaining",remaintimevalue);

                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                pDialog.hide();
                getData();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void setPrayerTimes() {
        if (AppController.getPrefranceDataBoolean("12")){

            try {
                final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                final Date dateObj = sdf.parse(fajr);
                Fajr.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj));
                final Date dateObjzuhr = sdf.parse(zuhr);
                Zuhr.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObjzuhr));
                final Date dateObjasr = sdf.parse(asr);
                Asr.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObjasr));
                final Date dateObjmagrib = sdf.parse(magrib);
                Majreb.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObjmagrib));
                final Date dateObjisa = sdf.parse(isa);
                isha.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObjisa));
                final Date dateObjshurooq = sdf.parse(shurooq);
                shorooq.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObjshurooq));
            } catch (final ParseException e) {
                e.printStackTrace();
            }

        }else {
            Fajr.setText(fajr);
            Zuhr.setText(zuhr);
            Asr.setText(asr);
            Majreb.setText(magrib);
            isha.setText(isa);

            shorooq.setText(shurooq);
        }
    }

    public static String getCurrentTime() {
        final String DATE_FORMAT_1 = "H:mm";

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1, Locale.ENGLISH);
        //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);

    }


    private String currentdatetime(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
        return  currentDateandTime;
    }


    private boolean checktimings(String time, String endtime) {

        String pattern = "H:mm";

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if(date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
        return false;
    }


    public  String timediff(String dateStart , String dateStop) throws ParseException {

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.ENGLISH);

        Date d1 = null;
        Date d2 = null;


        Log.e("time",dateStart);
        Log.e("timesec",dateStop);
        d1 = format.parse(dateStart);
        d2 = format.parse(dateStop);

        //in milliseconds
        long diff = d2.getTime() - d1.getTime();

        Log.e("diff", String.valueOf(diff));
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        System.out.print(diffDays + " days, ");
        System.out.print(diffHours + " hours, ");
        System.out.print(diffMinutes + " minutes, ");
        System.out.print(diffSeconds + " seconds.");
        if (diffDays==0 && diffHours!=0){
            Output=diffHours + " hours, "+diffMinutes + " minutes";
        }else if (diffHours == 0 && diffDays==0){
            Output=diffMinutes + " minutes";
        }else if (diffHours == 0 && diffMinutes ==0){
            Output=diffDays+ " days";
        }else {
            Output=diffDays+ " days, "+diffHours + " hours, "+diffMinutes + " minutes";
        }

        return Output;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting){
            Intent setting = new Intent(MainActivity.this,Settings.class);
            startActivity(setting);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
