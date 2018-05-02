package edu.illinois.cs.cs125.binaryclock.lib;

import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

/**
 * Class that makes up a binary clock.
 */
public final class BinaryClock {

    /**
     * Extract JSON data into a string that returns the local time.
     *
     * @param jsonString the json string
     * @return the time
     */
    public static String getTime(final String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(jsonString).getAsJsonObject();
        String caption = result.get("formatted").getAsString();
        return caption.substring(11);
    }

    /**
     * Add one second to the time.
     *
     * @param timeString the current time
     * @return the time one second later
     */
    public static String addTime(final String timeString) {
        String hourString = timeString.substring(0, 2);
        String minuteString = timeString.substring(3, 5);
        String secondString = timeString.substring(6, 8);
        int hour = Integer.parseInt(hourString);
        int minute = Integer.parseInt(minuteString);
        int second = Integer.parseInt(secondString);
        second++;
        if (second >= 60) {
            second -= 60;
            minute++;
        }
        if (minute >= 60) {
            minute -= 60;
            hour++;
        }
        if (hour >= 24) {
            hour -= 24;
        }
        if (hour < 10) {
            hourString = "0" + Integer.toString(hour);
        } else {
            hourString = Integer.toString(hour);
        }
        if (minute < 10) {
            minuteString = "0" + Integer.toString(minute);
        } else {
            minuteString = Integer.toString(minute);
        }
        if (second < 10) {
            secondString = "0" + Integer.toString(second);
        } else {
            secondString = Integer.toString(second);
        }
        return hourString + ":" + minuteString + ":" + secondString;
    }

    /**
     * Converts the time into base 2.
     * @param timeString the time in base 10
     * @return the time in base 2
     */
    public static String convertToBinaryTime(String timeString) {
        String hourString = timeString.substring(0, 2);
        String minuteString = timeString.substring(3, 5);
        String secondString = timeString.substring(6, 8);
        int hour = Integer.parseInt(hourString);
        int minute = Integer.parseInt(minuteString);
        int second = Integer.parseInt(secondString);
        String binaryHour = Integer.toBinaryString(hour);
        while (binaryHour.length() < 5) {
            binaryHour = "0" + binaryHour;
        }
        String binaryMinute = Integer.toBinaryString(minute);
        while (binaryMinute.length() < 6) {
            binaryMinute = "0" + binaryMinute;
        }
        String binarySecond = Integer.toBinaryString(second);
        while (binarySecond.length() < 6) {
            binarySecond = "0" + binarySecond;
        }
        return binaryHour + ":" + binaryMinute + ":" + binarySecond;
    }
}
