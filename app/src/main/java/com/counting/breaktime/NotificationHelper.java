package com.counting.breaktime;


import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;


public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        Intent intent;
        String text = "";
        switch ((int) (Math.random() * 4)) {
            case 0:
                text = getString(R.string.can_you_count_quickly);
                intent = new Intent(this, FastCount.class);
                break;
            case 1:
                text = getString(R.string.time_to_practise_math);
                intent = new Intent(this, FastCount.class);
                break;
            case 2:
                text = getString(R.string.can_you_figure_it_out);
                intent = new Intent(this, SignSelection.class);
                break;
            case 3:
                text = getString(R.string.solve_the_equation);
                intent = new Intent(this, SignSelection.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + (int) (Math.random() * 2));
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo_break_time)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
}