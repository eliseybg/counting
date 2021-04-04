package com.counting.breaktime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class NotificationsSettings extends MainActivity {

    private static final String PREF_HOURS = "hours";
    private static final String PREF_MINUTES = "minutes";

    NotificationsSettings(SaveAndLoadSettings mSaveAndLoadSettings, AlarmManager alarm, Context context){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, mSaveAndLoadSettings.getData(PREF_HOURS));
        c.set(Calendar.MINUTE, mSaveAndLoadSettings.getData(PREF_MINUTES));
        c.set(Calendar.SECOND, 0);
        startAlarm(c, alarm, context);
    }

    NotificationsSettings(AlarmManager alarmManager, Context context){
        cancelAlarm(alarmManager, context);
    }

    private void startAlarm(Calendar c, AlarmManager alarmManager, Context context) {
        cancelAlarm(alarmManager, context);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC,c.getTimeInMillis(),86400000, pendingIntent);
    }

    private static void cancelAlarm(AlarmManager alarmManager, Context context) {
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }

}
