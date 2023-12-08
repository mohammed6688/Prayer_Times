package com.mocomp.prayeralert.service;

import android.util.Log;

import com.mocomp.prayeralert.model.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Helper {

    /**
     * @param date: today's date
     * @return : tomorrows date
     * @throws ParseException: time parsing exception
     */
    public static String nextDay(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        c.setTime(Objects.requireNonNull(sdf.parse(date)));
        c.add(Calendar.DATE, 1);  // number of days to add
        date = sdf.format(c.getTime());  // date is now the new date
        SimpleDateFormat newSdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        return newSdf.format(c.getTime());
    }

    /**
     * @return : current date
     */
    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm", Locale.ENGLISH);
        //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

    /**
     * @return : returns current date and time
     */
    public static String currentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.ENGLISH);
        return sdf.format(new Date());
    }

    public static String stringBuilder(String... strings) {
        return strings[0] + strings[1];
    }

    public static boolean checkPrecedence(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("H:mm", Locale.ENGLISH);
        try {
            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);
            assert date1 != null;
            return date1.before(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
