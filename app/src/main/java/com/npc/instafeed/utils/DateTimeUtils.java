package com.npc.instafeed.utils;

import android.content.Context;

import com.npc.instafeed.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by USER on 17/01/2017.
 */

public class DateTimeUtils {
    public static String getChatDisplayTime(Context context, long milliseconds) {

        milliseconds = milliseconds * 1000;
        Date today = new Date();
        Date serverTime = new Date(milliseconds);

        DateDiff diff = new DateDiff(today, serverTime);
        long seconds = diff.getSeconds();
        long minutes = diff.getMinutes();
        long days = diff.getDays();

        if (seconds < 60) {
            return context.getString(R.string.txt_just_now);
        } else if (minutes <= 30) {
            return String.format(context.getString(R.string.txt_minutes_ago), minutes, minutes >= 2 ? "s" : "");
        } else if (days < 1) {
            return dateToString(serverTime, "h:mm a");
        } else if (days <= 7) {
            return dateToString(serverTime, "E, MMM dd, h:mm a");
        } else if (days <= 365) {
            return dateToString(serverTime, "MMM dd, h:mm a");
        } else {
            return dateToString(serverTime, "MMM dd, yyyy");
        }
    }

    public static class DateDiff {
        private final Date date1;
        private final Date date2;

        private final Long millis;
        private int seconds = 0;
        private int minutes = 0;
        private int hours = 0;
        private int days = 0;

        public DateDiff(Date date1, Date date2) {
            this.date1 = date1;
            this.date2 = date2;

            this.millis = this.date1.getTime() - this.date2.getTime();
            if (this.millis > 1000) {
                this.seconds = Integer.valueOf(String.valueOf(millis / 1000));
                if (this.seconds > 60) this.minutes = seconds / 60;
                if (this.minutes > 60) this.hours = minutes / 60;
                if (this.hours > 24) this.days = hours / 24;
            }
        }

        public Long getTime() {
            return millis;
        }

        public int getSeconds() {
            return seconds;
        }

        public int getMinutes() {
            return minutes;
        }

        public int getHours() {
            return hours;
        }

        public int getDays() {
            return days;
        }
    }

    public static String dateToString(Date date, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(date);
    }

}
