//package com.android.meditate.Notification;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import com.android.meditate.Login.LoginActivity;
//import com.android.meditate.R;
//
//public class IntentService extends android.app.IntentService {
//
//    private static final String TAG = "NotificationIntentService";
//
//    public IntentService() {
//        super("IntentService");
//    }
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        SharedPreferences notificationPref = this.getSharedPreferences("com.android.meditate.Notification", Context.MODE_PRIVATE);
//        if (notificationPref.getBoolean("Notification", false) == false){ // Do not proceed if notifications are turned off
//            return;
//        }
//
//        String notificationName = intent.getStringExtra("NotificationName");
//        if (notificationName.equalsIgnoreCase("Wake Up")){
//            createNotification("Rise and Shine!", "Good Morning!", 1, 1);
//        }
//        else if (notificationName.equalsIgnoreCase("Bed Time")){
//            createNotification("Time to Wind Down!", "Good Night!", 2, 2);
//        }
//    }
//
//    private void createNotification(String title, String text, int requestCode, int id){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MeditateChannel");
//        builder.setContentTitle(title);
//        builder.setContentText(text);
//        builder.setSmallIcon(R.drawable.logo);
//        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        builder.setAutoCancel(true);
//        Intent notifyIntent = new Intent(this, LoginActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getService(this, requestCode, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        builder.setContentIntent(pendingIntent);
//        Notification notification = builder.build();
////        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
//        managerCompat.notify(id, notification);
//    }
//}
