package com.mocomp.prayertimes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
        pDialog.show();
    }

    private void initializeListeners() {
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fajr != null){
                    try {
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                        final Date dateObj = sdf.parse(fajr);
                        String newFajr=new SimpleDateFormat("hh:mm a").format(dateObj);
                        String value =monthNumber+"/"+(Integer.parseInt(Day))+"/"+year+" "+newFajr;
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
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                        final Date dateObj = sdf.parse(shurooq);
                        String newFajr=new SimpleDateFormat("hh:mm a").format(dateObj);
                        String value =monthNumber+"/"+Day+"/"+year+" "+newFajr;
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
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                        final Date dateObj = sdf.parse(zuhr);
                        String newFajr=new SimpleDateFormat("hh:mm a").format(dateObj);
                        String value =monthNumber+"/"+Day+"/"+year+" "+newFajr;
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
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                        final Date dateObj = sdf.parse(asr);
                        String newFajr=new SimpleDateFormat("hh:mm a").format(dateObj);
                        String value =monthNumber+"/"+Day+"/"+year+" "+newFajr;
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
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                        final Date dateObj = sdf.parse(magrib);
                        String newFajr=new SimpleDateFormat("hh:mm a").format(dateObj);
                        String value =monthNumber+"/"+Day+"/"+year+" "+newFajr;
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
                        final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                        final Date dateObj = sdf.parse(isa);
                        String newFajr=new SimpleDateFormat("hh:mm a").format(dateObj);
                        String value =monthNumber+"/"+Day+"/"+year+" "+newFajr;
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
                            String hijriMonthTxt = response.getJSONObject("data").getJSONObject("date").getJSONObject("hijri").getJSONObject("month").get("en").toString();
                            String City = response.getJSONObject("data").getJSONObject("meta").get("timezone").toString();
                            String gregoryDate =  response.getJSONObject("data").getJSONObject("date").get("readable").toString();

                            Fajr.setText(fajr);
                            Zuhr.setText(zuhr);
                            Asr.setText(asr);
                            Majreb.setText(magrib);
                            isha.setText(isa);
                            city.setText(City);
                            shorooq.setText(shurooq);

                            @SuppressLint("SimpleDateFormat") SimpleDateFormat format1=new SimpleDateFormat("dd MMM yyy");
                            Date dt1=format1.parse(gregoryDate);
                            dayOfTheWeek = (String) DateFormat.format("EEEE", dt1); // Thursday
                            Day          = (String) DateFormat.format("dd",   dt1); // 20
                            monthString  = (String) DateFormat.format("MMM",  dt1); // Jun
                            monthNumber  = (String) DateFormat.format("MM",   dt1); // 06
                            year         = (String) DateFormat.format("yyyy", dt1); // 2013
                            dateDay.setText(Day);
                            dateMonth.setText(" "+monthString);
                            day.setText(dayOfTheWeek);


                            @SuppressLint("SimpleDateFormat") SimpleDateFormat format2=new SimpleDateFormat("dd-MM-yyyy");
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
                                nextSalat.setText("Fajr prayer: "+fajr);
                                time2 = fajr;
                            }else if (NextSalat == 2){
                                nextSalat.setText("Shurooq prayer: "+shurooq);
                                time2 = shurooq;
                            }else if (NextSalat == 3){
                                nextSalat.setText("Zuhr prayer: "+zuhr);
                                time2 = zuhr;
                            }else if (NextSalat == 4){
                                nextSalat.setText("Asr prayer: "+asr);
                                time2 = asr;
                            }else if (NextSalat == 5){
                                nextSalat.setText("Maghrib prayer: "+magrib);
                                time2 = magrib;
                            }else if (NextSalat == 6){
                                nextSalat.setText("Isa prayer: "+isa);
                                time2 = isa;
                            }

                            if (time2.equals(fajr)){
                                Log.e("currentdatetime",currentdatetime());
                                Log.e("timeeee",timediff(currentdatetime(),"04/26/2021 11:00 pm"));

                                final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                                final Date dateObj = sdf.parse(fajr);
                                String newFajr=new SimpleDateFormat("hh:mm a").format(dateObj);

                                String value =monthNumber+"/"+(Integer.parseInt(Day))+"/"+year+" "+newFajr;

                                Log.e("value",value);
                                remaintimevalue=timediff(currentdatetime(),value);
                                Log.e("remaining",remaintimevalue);

                            }else {
                                String value =monthNumber+"/"+Day+"/"+year+" "+time2;
                                remaintimevalue=timediff(currentdatetime(),value);
                            }

                            remainTime.setText(remaintimevalue+" is remaining");
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

    public static String getCurrentTime() {
        final String DATE_FORMAT_1 = "h:mm a";

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);

    }


    private String currentdatetime(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        return  currentDateandTime;
    }


    private boolean checktimings(String time, String endtime) {

        String pattern = "h:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

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

    public String addminuts(String myTime , int time) throws ParseException {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("h:mm a");
        Date d = df.parse(myTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, time);
        String newTime = df.format(cal.getTime());
        Log.e("the time",newTime);
        return newTime;
    }

    public  String timediff(String dateStart , String dateStop) throws ParseException {

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a");

        Date d1 = null;
        Date d2 = null;

        d1 = format.parse(dateStart);
        d2 = format.parse(dateStop);

        //in milliseconds
        long diff = d2.getTime() - d1.getTime();

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
}
