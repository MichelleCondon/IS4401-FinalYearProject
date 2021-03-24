package com.michelle_condon.is4401_finalyearproject.GoogleMaps;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.michelle_condon.is4401_finalyearproject.R;

import java.util.Random;

public class NotificationHelper extends ContextWrapper {

    //Code below is based on the Youtube Video "Geofencing | The ultimate tutorial | Create and monitor geofences", yoursTruly, https://www.youtube.com/watch?v=nmAtMqljH9M

    //Ensuring the device is running the correct version to support notifications
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    //Declare variables
    private final String CHANNEL_ID = "Channel ID 123";

    //Ensures the device is running the necessary version
    @RequiresApi(api = Build.VERSION_CODES.O)
    //Creating the notification channel
    private void createChannels() {
        //Declaring Variables
        String CHANNEL_NAME = "High priority channel";
        //Setting notification details
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("New Work Notification");
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
    }

    //Creating the high priority notification method
    public void sendHighPriorityNotification(String title, String body, Class activityName) {

        Intent intent = new Intent(this, activityName);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 267, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Building a notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_company_logo_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().setSummaryText("Work Notification").setBigContentTitle(title).bigText(body))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat.from(this).notify(new Random().nextInt(), notification);
    }
}

//End