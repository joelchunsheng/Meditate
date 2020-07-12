package com.android.meditate.Notification;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.meditate.Login.LoginActivity;
import com.android.meditate.MainActivity;
import com.android.meditate.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Notification extends AppCompatActivity {

    static SharedPreferences notificationPref;
    SwitchCompat notificationSwitch;
    CardView wakeUpCard, bedTimeCard;
    static TextView wakeUpText, bedTimeText;
    Context context = this;
    int hour, min;
    Boolean switchPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationSwitch = (SwitchCompat) findViewById(R.id.notiSwitch);
        wakeUpCard = (CardView) findViewById(R.id.wakeUpCard);
        bedTimeCard = (CardView) findViewById(R.id.bedTimeCard);
        wakeUpText = (TextView) findViewById(R.id.wakeUpTimeText);
        bedTimeText = (TextView) findViewById(R.id.bedTimeText);

        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        notificationPref = this.getSharedPreferences("com.android.meditate.Notification", Context.MODE_PRIVATE);

        switchPref = notificationPref.getBoolean("Notification", false);

        //if switch was previously checked
        if (switchPref == true){
            notificationSwitch.setChecked(true);
            wakeUpCard.setClickable(true);
            bedTimeCard.setClickable(true);

        }
        //if switch was previously turned off
        else{
            //disable click
            notificationSwitch.setChecked(false);
            //make cards translucent
            wakeUpCard.setAlpha(.5f);
            bedTimeCard.setAlpha(.5f);
        }

        //configure switch
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    // Set sharedPreferences
                    notificationPref.edit().putBoolean("Notification", true).apply();

                    wakeUpCard.setClickable(true);
                    bedTimeCard.setClickable(true);
                    wakeUpCard.setAlpha(1);
                    bedTimeCard.setAlpha(1);
                    switchPref = true;
                }
                else{
                    // Set sharedPreferences
                    notificationPref.edit().putBoolean("Notification", false).apply();

                    wakeUpCard.setClickable(false);
                    bedTimeCard.setClickable(false);
                    wakeUpCard.setAlpha(.5f);
                    bedTimeCard.setAlpha(.5f);
                    switchPref = false;
                }
            }
        });

        wakeUpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchPref == true){
                    timePicker(context, hour, min, wakeUpText, "Wake Up");
                }
            }
        });

        bedTimeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchPref == true){
                    timePicker(context, hour, min, bedTimeText, "Bed Time");
                }
            }
        });

    }

    //time picker dialog
    public static void timePicker(final Context context, int hour, int min, final TextView textView, final String notificationName){
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                if (hourOfDay>=12){
                    //PM
                    if (minute < 10){ // If minute is less than 10, add a 0 in front to avoid this --> 6 : 3 PM
                        textView.setText((hourOfDay-12) + " : 0" + minute + " PM");
                    }
                    else{
                        textView.setText((hourOfDay-12) + " : " + minute + " PM");
                    }

                }
                else{
                    //AM
                    if (minute < 10){ // If minute is less than 10, add a 0 in front to avoid this --> 6 : 3 AM
                        textView.setText(hourOfDay + " : 0" + minute + " AM");
                    }
                    else{
                        textView.setText(hourOfDay + " : " + minute + " AM");
                    }
                }
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                setAlarm(calendar.getTimeInMillis(), context, notificationName);
            }
        },hour, min, false);
        timePickerDialog.show();
    }

    // Request Codes
    // 1 - Wake Up Notification
    // 2 - Bed Time Notification
    private static void setAlarm(long timeInMillis, Context context, String notificationName){
        Intent notifyIntent = new Intent(context, Receiver.class);
        notifyIntent.putExtra("NotificationName", notificationName);
        if (notificationName.equalsIgnoreCase("Wake Up")){
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else if (notificationName.equalsIgnoreCase("Bed Time")){
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else{
            return;
        }
    }

}
