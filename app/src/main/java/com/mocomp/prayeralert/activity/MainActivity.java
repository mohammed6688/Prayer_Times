package com.mocomp.prayeralert.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mocomp.prayeralert.AppController;
import com.mocomp.prayeralert.R;
import com.mocomp.prayeralert.Settings;
import com.mocomp.prayeralert.dal.ServerDAO;
import com.mocomp.prayeralert.model.Data;
import com.mocomp.prayeralert.model.Timing;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.mocomp.prayeralert.Theme.PREFS_NAME;

public class MainActivity extends AppCompatActivity {
    int NextSalat;
    String time2;
    String Output;
    String remaintimevalue;
    CardView card1, card2, card3, card4, card5, card6;
    String dayOfTheWeek;
    String Day;
    String monthString;
    String monthNumber;
    String year;
    String gregoryDate;
    TextView hijriDay, hijriMonth, Fajr, Zuhr, Asr, Majreb, isha, nextSalat, remainTime, day, gregoriDay, gregoriMonth, city, shorooq;
    Data data = ServerDAO.serverDAO.data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //to adjust the style when the app started
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        //"theme" is the same key 0 is the default value
        int theme = settings.getInt("theme", 0);

        setTheme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AppController.getPrefranceData("lang").equals("ar")) {
            Locale locale2 = new Locale("ar");
            Locale.setDefault(locale2);

            Configuration config2 = new Configuration();
            config2.locale = locale2;

            getBaseContext().getResources().updateConfiguration(
                    config2, getBaseContext().getResources().getDisplayMetrics());
        } else if (AppController.getPrefranceData("lang").equals("en")) {
            Locale locale2 = new Locale("en");
            Locale.setDefault(locale2);

            Configuration config2 = new Configuration();
            config2.locale = locale2;

            getBaseContext().getResources().updateConfiguration(
                    config2, getBaseContext().getResources().getDisplayMetrics());
        }


        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        initializeVariables();
        initializeListeners();
        setPrayerTimes();
        try {
            setDate();
            nextSala();
        } catch (ParseException e) {
            Log.e("error while parsing",e.toString());
            e.printStackTrace();
        }
//        getData();

    }

    private void nextSala() throws ParseException {
        if (checktimings(getCurrentTime(), data.getTiming().getFajr())) {
            NextSalat = 1;
            nextSalat.setText(getResources().getString(R.string.fajr_prayer) + data.getTiming().getFajr());
            time2 = data.getTiming().getFajr();
            card1.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("time", getCurrentTime());
        } else if (checktimings(getCurrentTime(), data.getTiming().getSunrise())) {

            NextSalat = 2;
            nextSalat.setText(getResources().getString(R.string.shurroq_prayer) + data.getTiming().getSunrise());
            time2 = data.getTiming().getSunrise();
            card2.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("time", "2");

        } else if (checktimings(getCurrentTime(), data.getTiming().getDhuhr())) {

            NextSalat = 3;
            nextSalat.setText(getResources().getString(R.string.zuhr_prayer) + data.getTiming().getDhuhr());
            time2 = data.getTiming().getDhuhr();
            card3.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("time", "3");
        } else if (checktimings(getCurrentTime(), data.getTiming().getAsr())) {

            NextSalat = 4;
            nextSalat.setText(getResources().getString(R.string.asr_prayer) + data.getTiming().getAsr());
            time2 = data.getTiming().getAsr();
            card4.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("time", "4");

        } else if (checktimings(getCurrentTime(), data.getTiming().getMaghrib())) {
            NextSalat = 5;
            nextSalat.setText(getResources().getString(R.string.magrib_prayer) + data.getTiming().getMaghrib());
            time2 = data.getTiming().getMaghrib();
            card5.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("time", "5");

        } else if (checktimings(getCurrentTime(), data.getTiming().getIsha())) {

            NextSalat = 6;
            nextSalat.setText(getResources().getString(R.string.isa_prayer) + data.getTiming().getIsha());
            time2 = data.getTiming().getIsha();
            card6.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("time", "6");

        } else {
            NextSalat = 1;
            Log.e("time", getCurrentTime());
        }


        if (time2.equals(data.getTiming().getFajr())) {
            Log.e("currentdatetime", currentdatetime());
            Log.e("timeeee", timediff(currentdatetime(), "04/26/2021 11:00 pm"));

            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
            final Date dateObj = sdf.parse(data.getTiming().getFajr());
            String newFajr = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);

            String value = monthNumber + "/" + (Integer.parseInt(Day) + 1) + "/" + year + " " + newFajr;

            Log.e("value", value);
            remaintimevalue = timediff(currentdatetime(), value);
            Log.e("remaining", remaintimevalue);

        } else {
            String value = monthNumber + "/" + Day + "/" + year + " " + time2;
            remaintimevalue = timediff(currentdatetime(), value);
        }

        remainTime.setText(remaintimevalue + getResources().getString(R.string.is_remaining));
    }

    private void setDate() throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dt1 = format1.parse(data.getDate().getGregorianDate().getDate());

        dayOfTheWeek = (String) DateFormat.format("EEEE", dt1); // Thursday
        Day = (String) DateFormat.format("dd", dt1); // 20
        monthString = (String) DateFormat.format("MMM", dt1); // Jun
        monthNumber = (String) DateFormat.format("MM", dt1); // 06
        year = (String) DateFormat.format("yyyy", dt1); // 2013



        @SuppressLint("SimpleDateFormat") SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dt2 = format2.parse(data.getDate().getHijriDate().getDate());
        String dayOfTheWeekHijri = (String) DateFormat.format("EEEE", dt2); // Thursday
        String DayHijri = (String) DateFormat.format("dd", dt2); // 20
        String monthStringHijri = (String) DateFormat.format("MMM", dt2); // Jun
        String monthNumberHijri = (String) DateFormat.format("MM", dt2); // 06
        String yearHijri = (String) DateFormat.format("yyyy", dt2); // 2013

        if (AppController.getPrefranceData("lang").equals("ar")) {
            hijriMonth.setText(" " + data.getDate().getHijriDate().getMonth().getAr()+" ");
        }else {
            hijriMonth.setText(" " + data.getDate().getHijriDate().getMonth().getEn()+" ");
        }

        hijriDay.setText(DayHijri);
        gregoriDay.setText(Day);
        gregoriMonth.setText(" " + monthString+" ");
        day.setText(dayOfTheWeek);
        city.setText(data.getMeta().getTimeZone());
    }

    private void initializeVariables() {
        shorooq = findViewById(R.id.shorooq);
        nextSalat = findViewById(R.id.nextSalat);
        remainTime = findViewById(R.id.remainTime);
        day = findViewById(R.id.day);
        gregoriDay = findViewById(R.id.dateDay);
        gregoriMonth = findViewById(R.id.dateMonth);
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

    }

    private void initializeListeners() {
        card1.setOnClickListener(v -> {

            if (data.getTiming().getFajr() != null) {
                try {
                    String value;
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                    final Date dateObj = sdf.parse(data.getTiming().getFajr());
                    String newFajr = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                    if (time2.equals(data.getTiming().getFajr())) {
                        value = nextDay(gregoryDate) + " " + newFajr;
                        //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                    } else {
                        value = monthNumber + "/" + (Integer.parseInt(Day)) + "/" + year + " " + newFajr;
                    }

                    Log.e("value", value);
                    remaintimevalue = timediff(currentdatetime(), value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, remaintimevalue, Toast.LENGTH_SHORT).show();
            }
        });
        card2.setOnClickListener(v -> {
            if (data.getTiming().getSunrise() != null) {
                try {
                    String value;
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                    final Date dateObj = sdf.parse(data.getTiming().getSunrise());
                    String newFajr = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                    if (time2.equals(data.getTiming().getFajr())) {
                        value = nextDay(gregoryDate) + " " + newFajr;
                        //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                    } else {
                        value = monthNumber + "/" + (Integer.parseInt(Day)) + "/" + year + " " + newFajr;
                    }
                    String remaintime = timediff(currentdatetime(), value);
                    Toast.makeText(MainActivity.this, remaintime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
        card3.setOnClickListener(v -> {
            if (data.getTiming().getDhuhr() != null) {
                try {
                    String value;
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                    final Date dateObj = sdf.parse(data.getTiming().getDhuhr());
                    String newFajr = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                    if (time2.equals(data.getTiming().getFajr())) {
                        value = nextDay(gregoryDate) + " " + newFajr;
                        //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                    } else {
                        value = monthNumber + "/" + (Integer.parseInt(Day)) + "/" + year + " " + newFajr;
                    }
                    String remaintime = timediff(currentdatetime(), value);
                    Toast.makeText(MainActivity.this, remaintime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        card4.setOnClickListener(v -> {
            if (data.getTiming().getAsr() != null) {
                try {
                    String value;
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                    final Date dateObj = sdf.parse(data.getTiming().getAsr());
                    String newFajr = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                    if (time2.equals(data.getTiming().getFajr())) {
                        value = nextDay(gregoryDate) + " " + newFajr;
                        //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                    } else {
                        value = monthNumber + "/" + (Integer.parseInt(Day)) + "/" + year + " " + newFajr;
                    }
                    String remaintime = timediff(currentdatetime(), value);
                    Toast.makeText(MainActivity.this, remaintime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        card5.setOnClickListener(v -> {
            if (data.getTiming().getMaghrib() != null) {
                try {
                    String value;
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                    final Date dateObj = sdf.parse(data.getTiming().getMaghrib());
                    String newFajr = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                    if (time2.equals(data.getTiming().getFajr())) {
                        value = nextDay(gregoryDate) + " " + newFajr;
                        //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                    } else {
                        value = monthNumber + "/" + (Integer.parseInt(Day)) + "/" + year + " " + newFajr;
                    }
                    String remaintime = timediff(currentdatetime(), value);
                    Toast.makeText(MainActivity.this, remaintime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        card6.setOnClickListener(v -> {
            if (data.getTiming().getIsha() != null) {
                try {
                    String value;
                    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                    final Date dateObj = sdf.parse(data.getTiming().getIsha());
                    String newFajr = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
                    if (time2.equals(data.getTiming().getFajr())) {
                        value = nextDay(gregoryDate) + " " + newFajr;
                        //value =monthNumber+"/"+(Integer.parseInt(Day)+1)+"/"+year+" "+newFajr;
                    } else {
                        value = monthNumber + "/" + (Integer.parseInt(Day)) + "/" + year + " " + newFajr;
                    }
                    String remaintime = timediff(currentdatetime(), value);
                    Toast.makeText(MainActivity.this, remaintime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
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

    private void setPrayerTimes() {
        if (AppController.getPrefranceDataBoolean("12")) {
            try {
                final SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
                final Date dateObj = sdf.parse(data.getTiming().getFajr());
                Fajr.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj));
                final Date dateObjzuhr = sdf.parse(data.getTiming().getDhuhr());
                Zuhr.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObjzuhr));
                final Date dateObjasr = sdf.parse(data.getTiming().getAsr());
                Asr.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObjasr));
                final Date dateObjmagrib = sdf.parse(data.getTiming().getMaghrib());
                Majreb.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObjmagrib));
                final Date dateObjisa = sdf.parse(data.getTiming().getIsha());
                isha.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObjisa));
                final Date dateObjshurooq = sdf.parse(data.getTiming().getSunrise());
                shorooq.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObjshurooq));
            } catch (final ParseException e) {
                e.printStackTrace();
            }

        } else {
            Fajr.setText(data.getTiming().getFajr());
            Zuhr.setText(data.getTiming().getDhuhr());
            Asr.setText(data.getTiming().getAsr());
            Majreb.setText(data.getTiming().getMaghrib());
            isha.setText(data.getTiming().getIsha());
            shorooq.setText(data.getTiming().getSunrise());
        }
    }

    public static String getCurrentTime() {
        final String DATE_FORMAT_1 = "H:mm";

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1, Locale.ENGLISH);
        //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);

    }


    private String currentdatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.ENGLISH);
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }


    private boolean checktimings(String time, String endtime) {

        String pattern = "H:mm";

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if (date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String timediff(String dateStart, String dateStop) throws ParseException {

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.ENGLISH);

        Date d1 = null;
        Date d2 = null;


        Log.e("time", dateStart);
        Log.e("timesec", dateStop);
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
        if (diffDays == 0 && diffHours != 0) {
            Output = diffHours + " hours, " + diffMinutes + " minutes";
        } else if (diffHours == 0 && diffDays == 0) {
            Output = diffMinutes + " minutes";
        } else if (diffHours == 0 && diffMinutes == 0) {
            Output = diffDays + " days";
        } else {
            Output = diffDays + " days, " + diffHours + " hours, " + diffMinutes + " minutes";
        }

        return Output;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {
            Intent setting = new Intent(MainActivity.this, Settings.class);
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
