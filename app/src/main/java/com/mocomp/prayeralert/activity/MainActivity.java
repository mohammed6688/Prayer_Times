package com.mocomp.prayeralert.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
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

import com.mocomp.prayeralert.AppController;
import com.mocomp.prayeralert.R;
import com.mocomp.prayeralert.dal.ServerDAO;
import com.mocomp.prayeralert.model.Data;
import com.mocomp.prayeralert.service.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.mocomp.prayeralert.activity.Theme.PREFS_NAME;

public class MainActivity extends AppCompatActivity {
    String nextSalaTiming;
    String remainTimeValue;
    CardView fajrCard, sunriseCard, dhuhrCard, asrCard, maghribCard, ishaCard;
    String dayOfTheWeek;
    String Day;
    String monthString;
    String monthNumber;
    String year;
    TextView hijriDay, hijriMonth, Fajr, Zuhr, Asr, Majreb, isha, nextSalat, remainTime, day, gregoriDay, gregoriMonth, city, shorooq;
    Data data = ServerDAO.serverDAO.data;
    String gregoryDate = data.getDate().getGregorianDate().getDate();

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
            nextSalat();
        } catch (ParseException e) {
            Log.e("error while parsing",e.toString());
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeVariables();
        initializeListeners();
        setPrayerTimes();
        try {
            setDate();
            nextSalat();
        } catch (ParseException e) {
            Log.e("error while parsing", e.toString());
            e.printStackTrace();
        }
    }

    private void nextSalat() throws ParseException {
        if (Helper.checkPrecedence(Helper.getCurrentTime(), data.getTiming().getFajr())) {
            nextSalat.setText(Helper.stringBuilder(getResources().getString(R.string.fajr_prayer), data.getTiming().getFajr()));
            nextSalaTiming = data.getTiming().getFajr();
            fajrCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("nextSalat", Helper.getCurrentTime());
        } else if (Helper.checkPrecedence(Helper.getCurrentTime(), data.getTiming().getSunrise())) {
            nextSalat.setText(Helper.stringBuilder(getResources().getString(R.string.shurroq_prayer) , data.getTiming().getSunrise()));
            nextSalaTiming = data.getTiming().getSunrise();
            sunriseCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("nextSalat", "2");
        } else if (Helper.checkPrecedence(Helper.getCurrentTime(), data.getTiming().getDhuhr())) {
            nextSalat.setText(Helper.stringBuilder(getResources().getString(R.string.zuhr_prayer) , data.getTiming().getDhuhr()));
            nextSalaTiming = data.getTiming().getDhuhr();
            dhuhrCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("nextSalat", "3");
        } else if (Helper.checkPrecedence(Helper.getCurrentTime(), data.getTiming().getAsr())) {
            nextSalat.setText(Helper.stringBuilder(getResources().getString(R.string.asr_prayer) , data.getTiming().getAsr()));
            nextSalaTiming = data.getTiming().getAsr();
            asrCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("nextSalat", "4");
        } else if (Helper.checkPrecedence(Helper.getCurrentTime(), data.getTiming().getMaghrib())) {
            nextSalat.setText(Helper.stringBuilder(getResources().getString(R.string.magrib_prayer) , data.getTiming().getMaghrib()));
            nextSalaTiming = data.getTiming().getMaghrib();
            maghribCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("nextSalat", "5");
        } else if (Helper.checkPrecedence(Helper.getCurrentTime(), data.getTiming().getIsha())) {
            nextSalat.setText(Helper.stringBuilder(getResources().getString(R.string.isa_prayer) , data.getTiming().getIsha()));
            nextSalaTiming = data.getTiming().getIsha();
            ishaCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("nextSalat", "6");
        } else {
            nextSalat.setText(Helper.stringBuilder(getResources().getString(R.string.fajr_prayer), data.getTiming().getFajr()));
            nextSalaTiming = data.getTiming().getFajr();
            fajrCard.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            Log.e("time", Helper.getCurrentTime());
        }


        if (nextSalaTiming.equals(data.getTiming().getFajr())) {
            Log.e("currentdatetime", Helper.currentDateTime());
            Log.e("timeeee", Helper.timeDifferance(Helper.currentDateTime(), "08/4/2022 11:00 pm"));

            SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
            Date dateObj = sdf.parse(data.getTiming().getFajr());
            String newFajr = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
            String value = monthNumber + "/" + (Integer.parseInt(Day) + 1) + "/" + year + " " + newFajr;
            Log.e("value", value);
            remainTimeValue = Helper.timeDifferance(Helper.currentDateTime(), value);

        } else {
            String value = monthNumber + "/" + Day + "/" + year + " " + nextSalaTiming;
            remainTimeValue = Helper.timeDifferance(Helper.currentDateTime(), value);
        }

        Log.e("remaining", remainTimeValue);
        remainTime.setText(Helper.stringBuilder(remainTimeValue, getResources().getString(R.string.is_remaining)));
    }



    private void setDate() throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date dt1 = format1.parse(data.getDate().getGregorianDate().getDate());
        Log.e("Gregorian date: ", String.valueOf(dt1));
        dayOfTheWeek = (String) DateFormat.format("EEEE", dt1); // Thursday
        Day = (String) DateFormat.format("dd", dt1); // 20
        monthString = (String) DateFormat.format("MMM", dt1); // Jun
        monthNumber = (String) DateFormat.format("MM", dt1); // 06
        year = (String) DateFormat.format("yyyy", dt1); // 2013
        Log.e("dayOfTheWeek: ", dayOfTheWeek);



        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
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
        fajrCard = findViewById(R.id.card1);
        sunriseCard = findViewById(R.id.card2);
        dhuhrCard = findViewById(R.id.card3);
        asrCard = findViewById(R.id.card4);
        maghribCard = findViewById(R.id.card5);
        ishaCard = findViewById(R.id.card6);

    }

    private void initializeListeners() {
        fajrCard.setOnClickListener(v -> {
            if (data.getTiming().getFajr() != null) {
                try {
                    String remainTime=remainingTimeHelper(data.getTiming().getFajr(),data.getTiming().getFajr());
                    Toast.makeText(MainActivity.this, remainTime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        sunriseCard.setOnClickListener(v -> {
            if (data.getTiming().getSunrise() != null && data.getTiming().getFajr()!=null) {
                try {
                    String remainTime=remainingTimeHelper(data.getTiming().getSunrise(),data.getTiming().getFajr());
                    Toast.makeText(MainActivity.this, remainTime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        dhuhrCard.setOnClickListener(v -> {
            if (data.getTiming().getDhuhr() != null && data.getTiming().getFajr()!=null) {
                try {
                    String remainTime=remainingTimeHelper(data.getTiming().getDhuhr(),data.getTiming().getFajr());
                    Toast.makeText(MainActivity.this, remainTime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        asrCard.setOnClickListener(v -> {
            if (data.getTiming().getAsr() != null && data.getTiming().getFajr()!=null) {
                try {
                    String remainTime=remainingTimeHelper(data.getTiming().getAsr(),data.getTiming().getFajr());
                    Toast.makeText(MainActivity.this, remainTime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        maghribCard.setOnClickListener(v -> {
            if (data.getTiming().getMaghrib() != null && data.getTiming().getFajr()!=null) {
                try {
                    String remainTime=remainingTimeHelper(data.getTiming().getMaghrib(),data.getTiming().getFajr());
                    Toast.makeText(MainActivity.this, remainTime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        ishaCard.setOnClickListener(v -> {
            if (data.getTiming().getIsha() != null && data.getTiming().getFajr()!=null) {
                try {
                    String remainTime=remainingTimeHelper(data.getTiming().getIsha(),data.getTiming().getFajr());
                    Toast.makeText(MainActivity.this, remainTime, Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
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

    /**
     *
     * @param timing: the praying time that i want to calculate its remaining time
     * @param comparingTiming: the fajr timing
     * @throws ParseException: parse time
     */
    public String remainingTimeHelper(String timing, String comparingTiming) throws ParseException {
        String value;
        SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
        Date dateObj = sdf.parse(timing);
        assert dateObj != null;
        String timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(dateObj);
        if (nextSalaTiming.equals(comparingTiming)) { //if the next pray is fajr
            value = Helper.nextDay(gregoryDate) + " " + timeFormat;  // 2022-12-02 12:11 AM
        } else {
            value = monthNumber + "/" + (Integer.parseInt(Day)) + "/" + year + " " + timeFormat;
        }
        return Helper.timeDifferance(Helper.currentDateTime(), value);
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
