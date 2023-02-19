package com.mocomp.prayeralert.service;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Helper {

    /**
     *
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

    public static String stringBuilder(String...strings){
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

    public static String timeDifferance(String startTime, String endTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy h:mm", Locale.ENGLISH);       //HH converts hour in 24 hours format (0-23), day calculation
        String Output;
        Log.e("startTime", startTime);
        Log.e("endTime", endTime);

        //in milliseconds
        long diff = format.parse(endTime).getTime() - format.parse(startTime).getTime();

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
}
