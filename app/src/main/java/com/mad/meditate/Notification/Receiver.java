package com.mad.meditate.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mad.meditate.R;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences notificationPref = context.getSharedPreferences("com.android.meditate.Notification", Context.MODE_PRIVATE);
        if (notificationPref.getBoolean("Notification", false) == false){
            // Do not proceed if notifications are turned off
            Log.i("Receiver", "Notification off");
        }else{
            String notificationName = intent.getStringExtra("NotificationName");
            if (notificationName.equalsIgnoreCase("Wake Up")){
                createNotification(context, "Rise and Shine!", "Good Morning! Start your day right with Meditate.", 1, 1);
            }
            else if (notificationName.equalsIgnoreCase("Bed Time")){
                createNotification(context, "Time to Wind Down!", "Rest and be Thankful!", 2, 2);
            }
            else{
                Log.i("Receiver", "No notification name");
            }
        }


    }


    private void createNotification(Context context, String title, String text, int requestCode, int id){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "MeditateChannel")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
//        Intent notifyIntent = new Intent(context, LoginActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getService(context, requestCode, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(id,  builder.build());
    }
}
