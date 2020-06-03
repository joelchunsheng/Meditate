package com.android.meditate.Notification;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.meditate.R;

import java.util.Calendar;

public class Notification extends AppCompatActivity {

    SharedPreferences notificationPref;
    SwitchCompat notificationSwitch;
    CardView wakeUpCard, bedTimeCard;
    TextView wakeUpText, bedTimeText;
    Context context = this;
    int hour, min;


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

        Boolean switchPref = notificationPref.getBoolean("Notification", false);

        if (switchPref == true){
            notificationSwitch.setChecked(true);
        }else{
            notificationSwitch.setChecked(false);
            wakeUpCard.setAlpha(.5f);
            bedTimeCard.setAlpha(.5f);
        }

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    notificationPref.edit().putBoolean("Notification", true).apply();
                    wakeUpCard.setClickable(true);
                    bedTimeCard.setClickable(true);
                    wakeUpCard.setAlpha(1);
                    bedTimeCard.setAlpha(1);

                    wakeUpCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timePicker(context, hour, min, wakeUpText);
                        }
                    });

                    bedTimeCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timePicker(context, hour, min, bedTimeText);
                        }
                    });
                }
                else{
                    notificationPref.edit().putBoolean("Notification", false).apply();
                    wakeUpCard.setClickable(false);
                    bedTimeCard.setClickable(false);
                    wakeUpCard.setAlpha(.5f);
                    bedTimeCard.setAlpha(.5f);
                }
            }
        });

    }

    //time picker dialog
    public static void timePicker(Context context, int hour, int min, final TextView textView){
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay>=12){
                    //PM
                    textView.setText(hourOfDay + " : " + minute + " PM");
                }
                else{
                    //AM
                    textView.setText(hourOfDay + " : " + minute + " AM");
                }
            }
        },hour, min, false);
        timePickerDialog.show();
    }
}
