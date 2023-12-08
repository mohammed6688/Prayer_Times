package com.mocomp.prayeralert.utils;

import com.mocomp.prayeralert.model.Data;
import com.mocomp.prayeralert.model.Date;
import com.mocomp.prayeralert.model.Timing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static String calculateRemainingTime(String targetTimeString) {

        LocalTime targetTime = LocalTime.parse(targetTimeString, DateTimeFormatter.ofPattern("HH:mm"));



        // Get the current date and time in the default time zone
        ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.systemDefault());

        // Combine the current date with the target time to create the target date and time
        LocalDateTime targetDateTime = LocalDateTime.of(currentDateTime.toLocalDate(), targetTime);

        // If the target time is before or equal to the current time, it is on the next day
        if (!targetDateTime.toLocalTime().isAfter(currentDateTime.toLocalTime())) {
            targetDateTime = targetDateTime.plusDays(1);
        }

        // Get the target date and time in the default time zone
        ZonedDateTime targetZonedDateTime = targetDateTime.atZone(ZoneId.systemDefault());

        // Calculate the remaining time duration
        Duration remainingTime = Duration.between(currentDateTime, targetZonedDateTime);

        if (remainingTime.isNegative()) {
            return "Time has elapsed";
        }

        long days = remainingTime.toDays();
        long hours = remainingTime.toHoursPart();
        long minutes = remainingTime.toMinutesPart();
        long seconds = remainingTime.toSecondsPart();

        return String.format("%s%s%s%s",
                days > 0 ? days + " days, " : "",
                hours > 0 ? hours + " hours, " : "",
                minutes > 0 ? minutes + " minutes, " : "",
                seconds + " seconds");
    }
}
